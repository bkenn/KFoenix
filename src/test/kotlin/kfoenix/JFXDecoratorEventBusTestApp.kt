package kfoenix

import com.jfoenix.controls.JFXButton
import javafx.geometry.Pos
import javafx.scene.paint.Color
import javafx.scene.text.FontWeight
import tornadofx.*

class JFXDecoratorEventBusTestApp : App(Main::class, MyStyles::class) {

    class Main: View() {
        override val root = jfxdecorator(First::class) {
            subscribe<ReplaceContentEvent<UIComponent>> {
                setContent(find(it.uiComponent, it.findScope).root) // receive component and what scope to find it in
            }
        }
    }

    class First: View() {
        override val root = vbox {
            addClass(MyStyles.box)
            label("First").addClass(MyStyles.title)
            jfxbutton("Switch") {
                addClass(MyStyles.switchBtn)
                action { fire(ReplaceContentEvent(Second::class)) } // fire event to replace decorators content
            }
        }
    }

    class Second: View() {
        override val root = vbox {
            addClass(MyStyles.box)
            label("Second").addClass(MyStyles.title)
            jfxbutton("Switch", btnType = JFXButton.ButtonType.RAISED) {
                addClass(MyStyles.switchBtn)
                action { fire(ReplaceContentEvent(First::class)) } // fire event to replace decorators content
            }
        }
    }

    class MyStyles: JFXStylesheet() {
        companion object {
            val box by cssclass()
            val switchBtn by cssclass()
            val title by cssclass()

            val defaultColor = Color.web("#4059a9")
        }

        init {
            box {
                alignment = Pos.TOP_CENTER
                spacing = 10.px
            }

            jfxDecoratorContentContainer {
                prefHeight = 300.px
                prefWidth = 400.px
            }

            switchBtn {
                backgroundColor += defaultColor
                textFill = Color.WHITE
            }

            title {
                fontSize = 18.px
                fontWeight = FontWeight.BOLD
            }
        }
    }

}