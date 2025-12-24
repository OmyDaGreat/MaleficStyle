package xyz.malefic.compose.style

import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.pointer.AwaitPointerEventScope
import androidx.compose.ui.input.pointer.PointerEvent
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.input.key.KeyEvent as Key

/**
 * Class representing an interactive style that can be applied to a UI element.
 *
 * @property modifier The `Modifier` to which the interactive styles will be applied.
 */
@Suppress("UNCHECKED_CAST")
class InteractStyle(
    var modifier: Modifier = Modifier,
) {
    /**
     * Focusable property to be applied to the element.
     *
     * The focusable property can be:
     * - `Boolean` to enable or disable focus
     * - `MutableInteractionSource` for custom interaction source
     * - `Boolean to MutableInteractionSource` for enabled state and custom interaction source
     */
    var focusable: Any? = null
        set(value) {
            field = value
            modifier = modifier.buildFocusable()
        }

    /**
     * Applies the focusable property defined in the `MaleficStyle` to the `Modifier`.
     *
     * @receiver The `Modifier` to which the focusable property will be applied.
     * @return A `Modifier` with the applied focusable property.
     */
    private fun Modifier.buildFocusable(): Modifier =
        when (val focusableValue = focusable) {
            is Boolean -> {
                focusable(focusableValue)
            }

            is MutableInteractionSource -> {
                focusable(interactionSource = focusableValue)
            }

            is Pair<*, *> -> {
                val (enabled, interactionSource) = focusableValue
                focusable(enabled as Boolean, interactionSource as MutableInteractionSource)
            }

            else -> {
                this
            }
        }

    /**
     * Clickable action to be applied to the element.
     */
    var onClick: Clickable? = null
        set(value) {
            field = value
            modifier = modifier.buildClickable()
        }

    /**
     * Operator function to set the `onClick` action.
     *
     * @param block The block of code to be executed when the element is clicked.
     */
    operator fun Clickable?.invoke(block: () -> Unit) {
        onClick = Clickable { block() }
    }

    /**
     * Applies the clickable action defined in the `MaleficStyle` to the `Modifier`.
     *
     * @receiver The `Modifier` to which the clickable action will be applied.
     * @return A `Modifier` with the applied clickable action.
     */
    private fun Modifier.buildClickable(): Modifier = onClick?.let { clickable(onClick = it) } ?: this

    /**
     * FocusRequester property to be applied to the element.
     *
     * The focusRequester property can be used to request focus for the element.
     */
    @Suppress("unused")
    var focusRequester: FocusRequester? = null
        set(value) {
            field = value?.also { modifier = modifier.focusRequester(it) }
        }

    /**
     * Property to handle pointer events.
     *
     * The `onPointerEvent` property can be:
     * - `PointerEventType to (AwaitPointerEventScope.(PointerEvent) -> Unit)` for handling pointer events with a specific type.
     * - `PointerEventType to PointerEventPass to (AwaitPointerEventScope.(PointerEvent) -> Unit)` for handling pointer events with a specific type and pass.
     */
    var onPointerEvent: Any? = null
        set(value) {
            field = value
            modifier = modifier.buildPointerEvent()
        }

    /**
     * Applies the pointer event handling defined in the `MaleficStyle` to the `Modifier`.
     *
     * @receiver The `Modifier` to which the pointer event handling will be applied.
     * @return A `Modifier` with the applied pointer event handling.
     */
    @OptIn(ExperimentalComposeUiApi::class)
    private fun Modifier.buildPointerEvent(): Modifier =
        when (val onPointerEventValue = onPointerEvent) {
            is Pair<*, *> -> {
                onPointerEvent(onPointerEventValue.first as PointerEventType) { event ->
                    (onPointerEventValue.second as (AwaitPointerEventScope.(PointerEvent) -> Unit)).invoke(this, event)
                }
            }

            is Triple<*, *, *> -> {
                onPointerEvent(onPointerEventValue.first as PointerEventType, onPointerEventValue.second as PointerEventPass) { event ->
                    (onPointerEventValue.third as (AwaitPointerEventScope.(PointerEvent) -> Unit)).invoke(this, event)
                }
            }

            else -> {
                this
            }
        }

    /**
     * Property to handle key events.
     *
     * The `onKeyEvent` property can be used to define custom key event handling logic.
     */
    var onKeyEvent: KeyEvent? = null
        set(value) {
            field = value
            modifier = modifier.buildKeyEvent()
        }

    /**
     * Operator function to set the `onKeyEvent` action.
     *
     * @param block The block of code to be executed when a key event occurs.
     */
    operator fun KeyEvent?.invoke(block: (Key) -> Boolean) {
        onKeyEvent = KeyEvent { key -> block(key) }
    }

    /**
     * Applies the key event handling defined in the `MaleficStyle` to the `Modifier`.
     *
     * @receiver The `Modifier` to which the key event handling will be applied.
     * @return A `Modifier` with the applied key event handling.
     */
    private fun Modifier.buildKeyEvent(): Modifier = onKeyEvent?.let { this.onKeyEvent(onKeyEvent = it) } ?: this
}

/**
 * Interface representing a clickable action.
 * Implement this interface to define custom click behavior.
 */
fun interface Clickable : () -> Unit

/**
 * Interface representing a key event handler.
 * Implement this interface to define custom key event handling logic.
 */
fun interface KeyEvent : (Key) -> Boolean
