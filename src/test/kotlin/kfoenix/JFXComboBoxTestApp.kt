package kfoenix

import javafx.geometry.Pos
import javafx.scene.paint.Color
import tornadofx.*

class JFXComboBoxTestApp : App(Main::class, MyStyles::class) {

    class Main : View() {

        val user = UserModel(User("John Doe", "johnd", "oe", "jdoe@fake.io"))
        val states = listOf("PA", "NJ", "CA", "NV", "ND", "WA", "FL")

        override val root = vbox {
            addClass(MyStyles.box)
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
                    field("State") {
                        jfxcombobox(user.state, states)
                    }
                }
                buttonbar {
                    jfxbutton("Print User").action {
                        user.commit()
                        println(user.item)
                    }
                }
            }
        }
    }

    class MyStyles : Stylesheet() {

        companion object {
            val bar by cssclass()
            val box by cssclass()
            val customerBtn by cssclass()
            val jfxButton by cssclass()
            val jfxRippler by cssclass()

            val defaultColor = Color.web("#4059a9")
        }

        init {
            /* Must place default styling first. Will overwrite more specific styling */
            jfxButton {
                backgroundColor += defaultColor
                textFill = Color.WHITE
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