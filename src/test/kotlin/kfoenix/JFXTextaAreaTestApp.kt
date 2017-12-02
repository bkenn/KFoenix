package kfoenix

import javafx.geometry.Pos
import tornadofx.*

class JFXTextAreaTestApp : App(Main::class, MyStyles::class) {

    class Main: View() {

        val user = UserModel(User("John Doe", "johnd", "oe", "jdoe@fake.io", "The is a comment about the user."))

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
                    field("User Comment") {
                        jfxtextarea(user.comment)
                    }
                }
            }
        }
    }

    class MyStyles: Stylesheet() {

        companion object {
            val bar by cssclass()
            val box by cssclass()
        }

        init {
            bar {
                alignment = Pos.CENTER_RIGHT
                spacing = 10.px
            }

            box {
                prefHeight = 600.px
                prefWidth = 800.px
            }
        }
    }
}