package kfoenix

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.*
import tornadofx.getValue
import tornadofx.setValue
import java.time.LocalDate

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
