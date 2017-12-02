package kfoenix

import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.paint.Color
import tornadofx.*

class JFXToolbarTestApp : App(Main::class, MyStyles::class) {

    class Main: View() {
        val user = UserModel(User("John Doe", "johnd", "oe", "jdoe@fake.io"))

        override val root = vbox {
            addClass(MyStyles.box)
            jfxtoolbar {
                vboxConstraints { margin = Insets(10.0) }
                leftSide {
                    jfxbutton("Left Bar Button")
                    jfxbutton(user.name)
                }
                rightSide {
                    jfxbutton("Right Bar Button")
                    jfxbutton(user.name)
                }
            }
            form {
                fieldset("User Information") {
                    field("Name") {
                        jfxtextfield(user.name)
                    }
                    field("Login") {
                        jfxtextfield(user.login)
                    }
                    field("Password") {
                        jfxtextfield(user.password)
                    }
                    field("Email") {
                        jfxtextfield(user.email)
                    }
                }
            }
        }
    }

    class MyStyles: Stylesheet() {

        companion object {
            val bar by cssclass()
            val box by cssclass()
            val customerBtn by cssclass()
            val jfxButton by cssclass()
            val jfxToolBar by cssclass()
            val toolBarRightBox by cssclass()
            val toolBarLeftBox by cssclass()
            val jfxRippler by cssclass()

            val defaultColor = Color.web("#4059a9")
        }

        init {
            /* Must place default styling first. Will overwrite more specific styling */
            jfxButton {
                backgroundColor += defaultColor
                textFill = Color.WHITE
            }

            jfxToolBar {
                toolBarLeftBox {
                    spacing = 10.px
                }
                toolBarRightBox {
                    spacing = 10.px
                }
            }

            bar {
                alignment = Pos.CENTER_RIGHT
                spacing = 10.px
            }

            box {
                prefHeight = 600.px
                prefWidth = 800.px
            }

            customerBtn {
                backgroundColor += Color.TRANSPARENT
                jfxRippler {
                    unsafe("-jfx-rippler-fill", Color.GRAY)
                }
            }
        }
    }

}