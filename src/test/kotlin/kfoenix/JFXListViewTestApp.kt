package kfoenix

import javafx.scene.paint.Color
import tornadofx.*

class JFXListViewTestApp : App(Main::class, MyStyles::class) {

    class Main: View() {

        val user = UserModel(User("John Doe", "johnd", "oe", "jdoe@fake.io"))
        var statesMod = mutableListOf("PA", "NJ", "CA", "NV", "ND", "WA", "FL").observable()
        // Will throw an UnsupportedOperationException if you attempt to add to list.
        val statesNonMod = listOf("PA", "NJ", "CA", "NV", "ND", "WA", "FL").observable()

        override val root = pane {
            addClass(MyStyles.box)
            jfxlistview(statesMod) {
                layoutXProperty().bind(this@pane.widthProperty() / 15)
                layoutYProperty().bind(this@pane.heightProperty() / 15)
                depth = 3
            }
            jfxlistview(statesNonMod) {
                layoutXProperty().bind(this@pane.widthProperty() / 2)
                layoutYProperty().bind(this@pane.heightProperty() / 15)
                depth = 3
            }
            jfxbutton("Add State") {
                layoutX = 20.0
                layoutYProperty().bind(this@pane.heightProperty() - heightProperty() - 20)
                action {
                    statesMod.add("WA")
                }
            }
        }
    }

    class MyStyles: Stylesheet() {
        companion object {
            val box by cssclass()
            val jfxButton by cssclass()
            val defaultColor = Color.web("#4059a9")
        }

        init {
            box {
                prefHeight = 600.px
                prefWidth = 800.px
            }

            jfxButton {
                backgroundColor += defaultColor
                textFill = Color.WHITE
            }
        }
    }

}