package kfoenix

import tornadofx.*

class JFXTabPaneJFXSnackBarTestApp : App(Main::class, MyStyles::class) {

    class Main: View() {

        override val root = jfxtabpane {
            setPrefSize(800.0, 600.0)
            tab(First::class)
            tab(Second::class)
            tab(Third::class)
        }
    }

    class First: View("First View") {
        override val root = vbox {
            label(title)
            button("Show Message") {
                action {
                    jfxsnackbar("Good Morning")
                }
            }
        }
    }

    /**
     * Example of how to target the root node from nested node
     */
    class Second: View("Second View") {
        override val root = vbox {
            label(title)
            hbox {
                button("Show Message") {
                   action {
                       jfxsnackbar("Good Evening", this@vbox)
                   }
                }
            }
        }
    }

    class Third: View("Third View") {
        override val root = vbox {
            label(title)
        }
    }


    class MyStyles: Stylesheet()
}