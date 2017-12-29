package kfoenix.notification

import com.jfoenix.effects.JFXDepthManager
import javafx.animation.Animation.Status
import javafx.animation.Interpolator
import javafx.animation.Timeline
import javafx.beans.property.DoubleProperty
import javafx.beans.property.SimpleDoubleProperty
import javafx.event.Event
import javafx.event.EventType
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Node
import javafx.scene.control.ScrollPane
import javafx.scene.image.ImageView
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.util.Duration
import kfoenix.jfxbutton
import tornadofx.*
import kotlin.math.max


abstract class KFXNotificationsBar : Region() {

    private val pane: ScrollPane
    // Need reference for styling color
    private var paneImage: VBox by singleAssign()
    private var paneBody: HBox by singleAssign()

    var transition: DoubleProperty = object : SimpleDoubleProperty() {
        override fun invalidated() {
            requestContainerLayout()
        }
    }

    open val title: String get() = ""

    open val isCloseButtonVisible: Boolean get() = true

    abstract val text: String

    abstract val typeNotification: String

    abstract val graphic: Node

    abstract val isShowing: Boolean

    abstract val isShowFromTop: Boolean

    abstract val containerHeight: Double


    // --- animation timeline code
    private val TRANSITION_DURATION = Duration(350.0)
    private var timeline: Timeline? = null
    private var transitionStartValue: Double = 0.toDouble()


    fun requestContainerLayout() {
        layoutChildren()
    }

    abstract fun hide()

    abstract fun relocateInParent(x: Double, y: Double)

    init {
        styleClass.add("notification-bar")

        isVisible = isShowing

        pane = scrollpane(true, true) {
            addClass("pane")
            hbarPolicy = ScrollPane.ScrollBarPolicy.NEVER
            vbarPolicy = ScrollPane.ScrollBarPolicy.NEVER

            hbox {
                paneImage = vbox {
                    alignment = Pos.CENTER
                    padding = Insets(0.0, 10.0, 0.0, 10.0)
                    this += (graphic as ImageView).apply {
                        fitHeight = 45.0
                        fitWidth = 45.0
                        JFXDepthManager.setDepth(this, 2)
                    }
                }

                paneBody = hbox {
                    hboxConstraints {
                        hgrow = Priority.ALWAYS
                    }
                    padding = Insets(10.0)

                    vbox(10.0){
                        hboxConstraints {
                            hgrow = Priority.ALWAYS
                        }
                        minHeight = 50.0
                        prefWidth = 300.0

                        if(title.isNotEmpty()) {
                            label(title) {
                                isWrapText = true
                                addClass("title")
                            }
                        }

                        if(text.isNotEmpty()) {
                            label(text) {
                                isWrapText = true
                                addClass("message")
                            }
                        }
                    }

                    vbox {
                        jfxbutton {
                            setMinSize(20.0, 20.0)
                            setPrefSize(20.0, 20.0)
                            textFill = Color.WHITE
                            ripplerFill = Color.WHITE
                            style = "-fx-background-radius: 20;"
                            addClass("close-button")
                            graphic = stackpane().addClass("graphic")
                            action(::hide)
                            requestFocus()
                            requestLayout()
                        }
                    }
                }
            }
        }

        children.setAll(pane)

        when (typeNotification) {
            "SUCCESS" -> {
                paneImage.style = "-fx-background-color: #78A840;"
                paneBody.style = "-fx-background-color: #8BC34A;"
            }
            "MESSAGE" -> {
                paneImage.style = "-fx-background-color: #0392D3;"
                paneBody.style = "-fx-background-color: #03A9F4;"
            }
            "ERROR" -> {
                paneImage.style = "-fx-background-color: #D33A2F;"
                paneBody.style = "-fx-background-color: #F44336;"
            }
            "WARNING" -> {
                paneImage.style = "-fx-background-color: #FFC107;"
                paneBody.style = "-fx-background-color: #DCA706;"
            }
        }
    }


    override fun layoutChildren() {
        val w = width
        val h = computePrefHeight(-1.0)

        val notificationBarHeight = prefHeight(w)
        val notificationMinHeight = minHeight(w)

        if (isShowFromTop) {
            // place at top of area
            pane.resize(w, h)
            relocateInParent(0.0, (transition.get() - 1) * notificationMinHeight)
        } else {
            // place at bottom of area
            pane.resize(w, notificationBarHeight)
            relocateInParent(0.0, containerHeight - notificationBarHeight)
        }
    }

    override fun computeMinHeight(width: Double): Double
            = max(super.computePrefHeight(width), MIN_HEIGHT)

    override fun computePrefHeight(width: Double): Double
            = max(pane.prefHeight(width), minHeight(width)) * transition.get()


    fun doShow() {
        transitionStartValue = 0.0
        doAnimationTransition()
    }

    fun doHide() {
        transitionStartValue = 1.0
        doAnimationTransition()
    }

    private fun doAnimationTransition() {
        var duration: Duration

        if (timeline != null && timeline!!.status != Status.STOPPED) {
            duration = timeline!!.currentTime

            // fix for #70 - the notification pane freezes up as it has zero
            // duration to expand / contract
            duration = if (duration === Duration.ZERO) TRANSITION_DURATION else duration
            transitionStartValue = transition.get()
            // --- end of fix

            timeline!!.stop()
        } else {
            duration = TRANSITION_DURATION
        }

        timeline = tornadofx.timeline {
            cycleCount = 1
            if(isShowing) {
                keyframe(Duration.ZERO) {
                    isCache = true
                    isVisible = true
                    pane.fireEvent(Event(ON_SHOWING))
                    keyvalue(transition, transitionStartValue)
                }
                keyframe(duration) {
                    pane.isCache = false
                    pane.fireEvent(Event(ON_SHOWN))
                    keyvalue(transition, 1, Interpolator.EASE_OUT)
                }
            } else {
                keyframe(Duration.ZERO) {
                    pane.isCache = true
                    pane.fireEvent(Event(ON_HIDING))
                    keyvalue(transition, transitionStartValue)
                }
                keyframe(duration) {
                    isCache = false
                    isVisible = false
                    pane.fireEvent(Event(ON_HIDDEN))
                    keyvalue(transition, 0, Interpolator.EASE_IN)
                }
            }
        }
    }

    companion object {

        private val MIN_HEIGHT = 40.0

        private val ON_SHOWING = EventType(Event.ANY, "NOTIFICATION_PANE_ON_SHOWING")

        /**
         * Called when the NotificationPane shows.
         */
        private val ON_SHOWN = EventType(Event.ANY, "NOTIFICATION_PANE_ON_SHOWN")

        /**
         * Called when the NotificationPane **will** be hidden.
         */
        private val ON_HIDING = EventType(Event.ANY, "NOTIFICATION_PANE_ON_HIDING")

        /**
         * Called when the NotificationPane is hidden.
         */
        private val ON_HIDDEN = EventType(Event.ANY, "NOTIFICATION_PANE_ON_HIDDEN")
    }

}
