package kfoenix

import javafx.geometry.Pos
import tornadofx.*

class JFXTextfieldTestApp: App(Main::class, MyStyles::class) {

    class Main: View() {

        val user = UserModel(User("John Doe", "johnd", "oe", "jdoe@fake.io"))

        override val root = vbox {
            addClass(MyStyles.box)
            form {
                fieldset("User Information") {
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
    }

    class MyStyles: Stylesheet() {
        companion object {
            val box by cssclass()
        }

        init {
            box {
                prefHeight = 600.px
                prefWidth = 800.px
                alignment = Pos.CENTER
            }
        }
    }

}