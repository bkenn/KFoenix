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
                        jfxcombobox(user.state, states) // use of JFXComboBox
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

    class MyStyles : JFXStylesheet() {

        companion object {
            val bar by cssclass()
            val box by cssclass()
            val customerBtn by cssclass()

            val defaultColor = Color.web("#4059a9")
        }

        init {
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
                    jfxRipplerFill.value = Color.GRAY
                }
            }
        }
    }

}