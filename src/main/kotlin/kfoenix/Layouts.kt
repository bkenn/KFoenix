package kfoenix

import com.jfoenix.controls.JFXDecorator
import com.jfoenix.controls.JFXMasonryPane
import com.jfoenix.controls.JFXToolbar
import javafx.event.EventTarget
import javafx.scene.Node
import tornadofx.*
import kotlin.reflect.KClass


/**
 * You should treat the decorator as a view container. See Test Apps for examples on how to manage view transitions.
 *
 * Warning: The JFXDecorator should not be used more than once in an application's life cycle.
 * You will receive the following exception: java.lang.IllegalStateException: Cannot set style once stage has been set visible
 *
 */
fun <T: UIComponent> View.jfxdecorator(uiComponent: T, op: JFXDecorator.() -> Unit = {})
        = opcr(this, JFXDecorator(primaryStage, uiComponent.root), op)

fun View.jfxdecorator(node: Node, fullScreen: Boolean = true, max: Boolean = true, min: Boolean = true, op: JFXDecorator.() -> Unit = {})
        = opcr(this, JFXDecorator(primaryStage, node, fullScreen, max, min), op)

fun View.jfxdecorator(uiComponent: KClass<out UIComponent>, op: JFXDecorator.() -> Unit = {}) = jfxdecorator(find(uiComponent), op)

fun EventTarget.jfxtoolbar(vararg nodes: Node, op: JFXToolbar.() -> Unit = {}): JFXToolbar {
    val toolbar = JFXToolbar()
    if(nodes.isNotEmpty()) toolbar.leftItems.addAll(nodes)
    return opcr(this, toolbar, op)
}

fun JFXToolbar.leftSide(op: JFXToolbar.() -> Unit = {}) {
    val interceptor = object: ChildInterceptor {
        override fun invoke(parent: EventTarget, node: Node, index: Int?): Boolean {
            if(parent is JFXToolbar) {
                parent.leftItems += node
                return true
            }
            return false
        }
    }
    FX.addChildInterceptor(interceptor)
    op()
    FX.removeChildInterceptor(interceptor)
}

fun JFXToolbar.rightSide(op: JFXToolbar.() -> Unit = {}) {
    val interceptor = object: ChildInterceptor {
        override fun invoke(parent: EventTarget, node: Node, index: Int?): Boolean {
            if(parent is JFXToolbar) {
                parent.rightItems += node
                return true
            }
            return false
        }
    }
    FX.addChildInterceptor(interceptor)
    op()
    FX.removeChildInterceptor(interceptor)
}

fun EventTarget.jfxmasonrypane(op: JFXMasonryPane.() -> Unit = {}) = opcr(this, JFXMasonryPane(), op)
