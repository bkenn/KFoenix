package kfoenix

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.geometry.Pos
import javafx.scene.paint.Color
import tornadofx.*
import java.time.LocalDate

class JFXCheckBoxTestApp : App(Main::class, MyStyles::class) {

    class Main: View() {
        val isValid = SimpleBooleanProperty(true)
        val user = UserModel(User("John Doe", "johnd", "password", "jdoe@fake.io"))

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
                    field("Valid") {
                        jfxcheckbox(isValid) // use of JFXCheckBox
                    }
                }
            }
        }
    }

    class MyStyles: JFXStylesheet() {

        companion object {
            val bar by cssclass()
            val box by cssclass()
            val customerBtn by cssclass()
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
                    jfxRipplerFill.value = Color.GRAY
                }
            }
        }
    }

    class User(name: String, login: String, password: String, email: String, comment: String = "", valid: Boolean = true) {

        val nameProperty = SimpleStringProperty(name)
        var name by nameProperty

        val loginProperty = SimpleStringProperty(login)
        var login by loginProperty

        val passwordProperty = SimpleStringProperty(password)
        var password by passwordProperty

        val emailProperty = SimpleStringProperty(email)
        var email by emailProperty

        val stateProperty = SimpleStringProperty("")
        var state by stateProperty

        val commentProperty = SimpleStringProperty(comment)
        var comment by commentProperty

        val dobProperty = SimpleObjectProperty<LocalDate>(LocalDate.now())
        var dob by dobProperty

        val validProperty = SimpleBooleanProperty(valid)
        var valid by validProperty

        override fun toString() = "User {name=$name; login=$login; password=$password; email=$email; state=$state}"
    }

    class UserModel(value: User? = null): ItemViewModel<User>(value) {
        val name = bind(User::nameProperty)
        val login = bind(User::loginProperty)
        val password = bind(User::passwordProperty)
        val email = bind(User::emailProperty)
        val state = bind(User::stateProperty)
        val comment = bind(User::commentProperty)
        val dob = bind(User::dobProperty)
    }


}