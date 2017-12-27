package kfoenix

import javafx.event.Event
import javafx.event.EventType
import javafx.scene.control.ScrollPane
import javafx.scene.layout.HBox
import javafx.scene.layout.Region
import javafx.scene.layout.VBox

abstract class KFXNotificationBar: Region() {

    companion object {
        val MIN_HEIGHT = 40
        private val ON_SHOWING = EventType(Event.ANY, "NOTIFICATION_PANE_ON_SHOWING")
        private val ON_SHOWN = EventType(Event.ANY, "NOTIFICATION_PANE_ON_SHOWN")

    }

    private val pane = ScrollPane()
    private val paneGeneral = HBox()
    private val paneImage = VBox()
    private val paneBody = HBox()

}