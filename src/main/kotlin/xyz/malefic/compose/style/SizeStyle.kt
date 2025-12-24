package xyz.malefic.compose.style

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize

/**
 * A class that defines size-related styles for a component.
 *
 * @property modifier The modifier to apply the size styling to.
 */
@Suppress("unused")
class SizeStyle(
    var modifier: Modifier = Modifier,
) {
    /**
     * The width of the component.
     */
    var width: Dp? = null
        set(value) {
            field = value
            modifier = modifier.width(value!!)
        }

    /**
     * The height of the component.
     */
    var height: Dp? = null
        set(value) {
            field = value
            modifier = modifier.height(value!!)
        }

    /**
     * The maximum height of the component as a fraction of the parent's height.
     */
    var fillMaxHeight: Float? = null
        set(value) {
            field = value
            modifier = modifier.fillMaxHeight(value!!)
        }

    /**
     * Sets the maximum height to 100% of the parent's height.
     */
    fun fillMaxHeight() = apply { fillMaxHeight = 1f }

    /**
     * The maximum width of the component as a fraction of the parent's width.
     */
    var fillMaxWidth: Float? = null
        set(value) {
            field = value
            modifier = modifier.fillMaxWidth(value!!)
        }

    /**
     * Sets the maximum width to 100% of the parent's width.
     */
    fun fillMaxWidth() = apply { fillMaxWidth = 1f }

    /**
     * The maximum size of the component as a fraction of the parent's size.
     */
    var fillMaxSize: Float? = null
        set(value) {
            field = value
            modifier = modifier.fillMaxSize(value!!)
        }

    /**
     * Sets the maximum size to 100% of the parent's size.
     */
    fun fillMaxSize() = apply { fillMaxSize = 1f }

    /**
     * The wrap content size of the component.
     *
     * The wrap content size can be defined using the following:
     *
     * - `Alignment` to specify the alignment of the wrapped content.
     * - `Boolean` to specify if the content should be unbounded.
     * - `Alignment to Boolean` to specify both alignment and unbounded.
     */
    var wrapContentSize: Any? = null
        set(value) {
            field = value
            modifier = modifier.buildWrapContentSize()
        }

    /**
     * Builds the wrap content size for the component.
     *
     * @receiver The current instance of Modifier.
     * @return The modified Modifier with the wrap content size applied.
     */
    private fun Modifier.buildWrapContentSize(): Modifier =
        when (val wrapContentSizeValue = wrapContentSize) {
            is Alignment -> {
                wrapContentSize(wrapContentSizeValue)
            }

            is Boolean -> {
                wrapContentSize(unbounded = wrapContentSizeValue)
            }

            is Pair<*, *> -> {
                val (alignment, unbounded) = wrapContentSizeValue
                wrapContentSize(alignment as Alignment, unbounded as Boolean)
            }

            else -> {
                this
            }
        }

    /**
     * The size of the component.
     *
     * The size can be defined using the following:
     *
     * - `Dp` to specify a single dimension.
     * - `DpSize` to specify both width and height.
     * - `Dp to Dp` to specify width and height separately.
     */
    var size: Any? = null
        set(value) {
            field = value
            modifier = modifier.buildSize()
        }

    /**
     * Builds the size for the component.
     *
     * @receiver The current instance of Modifier.
     * @return The modified Modifier with the size applied.
     */
    private fun Modifier.buildSize(): Modifier =
        when (val sizeValue = size) {
            is Dp -> {
                size(sizeValue)
            }

            is DpSize -> {
                size(sizeValue)
            }

            is Pair<*, *> -> {
                val (width, height) = sizeValue
                size(width as Dp, height as Dp)
            }

            else -> {
                this
            }
        }
}
