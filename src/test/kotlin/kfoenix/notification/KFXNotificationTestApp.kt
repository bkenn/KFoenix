package kfoenix.notification

import javafx.geometry.Pos
import tornadofx.*

class KFXNotificationTestApp : App(First::class) {

    class First: View() {
        override val root = vbox {
            button("JFXNotification") {
                action {
                    KFXNotifications.create()
                            .title("Message")
                            .text("This works!")
                            .position(Pos.BOTTOM_RIGHT)
                            .show()
                }
            }
        }
    }
}

fun main(args: Array<String>) {
    launch<KFXNotificationTestApp>(args)
}