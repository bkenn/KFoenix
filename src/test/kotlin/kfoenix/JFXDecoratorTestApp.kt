package kfoenix

import javafx.geometry.Pos
import javafx.scene.paint.Color
import tornadofx.*

class JFXDecoratorTestApp: App(Main::class, MyStyles::class) {

    class Main: View() {
        override val root = jfxdecorator(First::class)
    }

    class MainController: Controller() {

        private val main by inject<Main>()
        private val first by inject<First>()
        private val second by inject<Second>()

        fun switchToFirst() {
            main.root.setContent(first.root)
        }

        fun switchToSecond() {
            main.root.setContent(second.root)
        }
    }


    class First: View() {
        private val ctrl by inject<MainController>()
        override val root = vbox {
            addClass(MyStyles.box)
            label("First")
            jfxbutton("Switch") {
                addClass(MyStyles.switchBtn)
                action { ctrl.switchToSecond() }
            }
        }
    }

    class Second: View() {
        private val ctrl by inject<MainController>()
        override val root = vbox {
            addClass(MyStyles.box)
            label("Second")
            jfxbutton("Switch") {
                addClass(MyStyles.switchBtn)
                action { ctrl.switchToFirst() }
            }
        }
    }

    class MyStyles: Stylesheet() {
        companion object {
            val box by cssclass()
            val jfxDecoratorContentContainer by cssclass()
            val switchBtn by cssclass()

            val defaultColor = Color.web("#4059a9")
        }

        init {
            box {
                alignment = Pos.TOP_CENTER
            }

            jfxDecoratorContentContainer {
                prefHeight = 300.px
                prefWidth = 400.px
            }

            switchBtn {
                backgroundColor += defaultColor
                textFill = Color.WHITE
            }
        }
    }

}