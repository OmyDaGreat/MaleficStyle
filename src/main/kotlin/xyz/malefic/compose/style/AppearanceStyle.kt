package xyz.malefic.compose.style

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import xyz.malefic.extensions.tuple.Quadruple

@Suppress("UNCHECKED_CAST")
class AppearanceStyle(
    var modifier: Modifier = Modifier,
) {
    /**
     * Padding values to be applied to the element.
     *
     * The padding can be:
     * - `PaddingValues` for custom padding values
     * - `Dp` for uniform padding on all sides
     * - `Dp to Dp` for horizontal and vertical padding
     * - `Dp to Dp to Dp to Dp` for start, top, end, bottom padding
     */
    var padding: Any? = null
        set(value) {
            field = value
            modifier = modifier.buildPadding()
        }

    /**
     * Applies the padding values defined in the `MaleficStyle` to the `Modifier`.
     *
     * @receiver The `Modifier` to which the padding values will be applied.
     * @return A `Modifier` with the applied padding values.
     */
    private fun Modifier.buildPadding(): Modifier =
        when (val paddingVal = padding) {
            is PaddingValues -> {
                padding(paddingVal)
            }

            is Dp -> {
                padding(paddingVal)
            }

            is Pair<*, *> -> {
                val (horizontal, vertical) = paddingVal as Pair<Dp, Dp>
                padding(horizontal, vertical)
            }

            is Quadruple<*, *, *, *> -> {
                val quad = paddingVal as Quadruple<Dp, Dp, Dp, Dp>
                padding(quad.first, quad.second, quad.third, quad.fourth)
            }

            else -> {
                this
            }
        }

    /**
     * Background color and potentially shape to be applied to the element.
     *
     * The background can be:
     * - `Color` for solid color background
     * - `Color to Shape` for shaped color background
     */
    var background: Any? = null
        set(value) {
            field = value
            modifier = modifier.buildBackground()
        }

    /**
     * Applies the background defined in the `MaleficStyle` to the `Modifier`.
     *
     * @receiver The `Modifier` to which the background will be applied.
     * @return A `Modifier` with the applied background.
     */
    private fun Modifier.buildBackground(): Modifier =
        when (val bg = background) {
            is Color -> {
                background(bg)
            }

            is Pair<*, *> -> {
                val (color, shape) = bg
                background(color as Color, shape as Shape)
            }

            else -> {
                this
            }
        }

    /**
     * Border values to be applied to the element.
     *
     * The border can be:
     * - `BorderStroke` for basic border
     * - `Dp to Color` for width and color
     * - `BorderStroke to Shape` for shaped border
     * - `Dp to Color|Brush to Shape` for width, color/brush, and shape
     */
    var border: Any? = null
        set(value) {
            field = value
            modifier = modifier.buildBorder()
        }

    /**
     * Applies the border values defined in the `MaleficStyle` to the `Modifier`.
     *
     * @receiver The `Modifier` to which the border values will be applied.
     * @return A `Modifier` with the applied border values.
     */
    private fun Modifier.buildBorder(): Modifier =
        when (val borderValue = border) {
            is BorderStroke -> {
                border(borderValue)
            }

            is Pair<*, *> -> {
                val (first, second) = borderValue
                when (first) {
                    is Dp -> border(first, second as Color)
                    is BorderStroke -> border(first, second as Shape)
                    else -> this
                }
            }

            is Triple<*, *, *> -> {
                val (width, fillOrBrush, shape) = borderValue
                when (fillOrBrush) {
                    is Color -> border(width as Dp, fillOrBrush, shape as Shape)
                    is Brush -> border(width as Dp, fillOrBrush, shape as Shape)
                    else -> this
                }
            }

            else -> {
                this
            }
        }
}
