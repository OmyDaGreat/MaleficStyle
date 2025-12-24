package xyz.malefic.compose.style

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.onClick
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.unit.dp
import xyz.malefic.extensions.tuple.to
import kotlin.test.Test
import kotlin.test.assertTrue

/**
 * Test class for MaleficStyle.
 */
class MaleficStyleTest {
    /**
     * Test function to verify the equality of modifiers.
     */
    @OptIn(ExperimentalFoundationApi::class)
    @Test
    fun test() {
        val mod =
            malefic {
                appearance {
                    padding = 15.dp to 16.dp to 16.dp to 16.dp
                    background = Color.Blue to CircleShape
                    border = 2.dp to Color.Red to CircleShape
                }
                size {
                    width = 100.dp
                    height = 50.dp
                    fillMaxSize = 1f
                }
                interact {
                    focusable = true
                    onClick {
                        println("Clicked!")
                    }
                    onKeyEvent {
                        println("Key event!")
                        true
                    }
                }
            }

        val expectedModifier =
            Modifier
                .padding(15.dp, 16.dp, 16.dp, 16.dp)
                .background(Color.Blue, CircleShape)
                .border(2.dp, Color.Red, CircleShape)
                .width(100.dp)
                .height(50.dp)
                .fillMaxSize(1f)
                .focusable(true)
                .onClick { println("Clicked!") }
                .onKeyEvent {
                    println("Key event!")
                    true
                }

        assertTrue { areModifiersEqual(mod, expectedModifier) }
    }
}

/**
 * Function to compare two Modifier instances for equality.
 *
 * @param modifier1 The first Modifier to compare.
 * @param modifier2 The second Modifier to compare.
 * @return True if the modifiers are equal, false otherwise.
 */
fun areModifiersEqual(
    modifier1: Modifier,
    modifier2: Modifier,
): Boolean {
    val elements1 =
        modifier1.foldIn(mutableListOf<Modifier.Element>()) { acc, element ->
            acc.apply { add(element) }
        }
    val elements2 =
        modifier2.foldIn(mutableListOf<Modifier.Element>()) { acc, element ->
            acc.apply { add(element) }
        }

    if (elements1.size != elements2.size) {
        println("Modifiers have different sizes.")
        return false
    }

    elements1.zip(elements2).forEachIndexed { index, (e1, e2) ->
        val class1 = e1::class.simpleName
        val class2 = e2::class.simpleName

        if (class1 != class2) {
            println("Elements at position $index have different types:")
            println("Element1: $class1")
            println("Element2: $class2")
            return false
        }

        // Exceptions because they can't be equivalent
        if (!listOf("ComposedModifier", "KeyInputElement").contains(class1) && e1 != e2) {
            println("Elements at position $index are not equal:")
            println("Element1: $e1")
            println("Element2: $e2")
            return false
        }
    }
    return true
}
