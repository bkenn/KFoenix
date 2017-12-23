package kfoenix


import tornadofx.*

class JFXCheckBoxTableViewTestApp : App(Main::class) {

    class Main: View() {
        val user = mutableListOf(User("John Doe", "johnd", "oe", "jdoe@fake.io")).observable()

        override val root = vbox {
            setPrefSize(500.0, 500.0)
            tableview(user) {
                column("Name", User::nameProperty).useTextField()
                column("Login", User::loginProperty)
                column("Password", User::passwordProperty)
                column("Valid", User::validProperty).useJFXCheckBox()
            }
        }
    }

}