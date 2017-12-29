package kfoenix.notification


import java.lang.ref.WeakReference
import java.util.*

import javafx.animation.ParallelTransition
import javafx.animation.Timeline
import javafx.animation.Transition
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.geometry.Pos
import javafx.scene.Node
import javafx.scene.image.ImageView
import javafx.stage.Popup
import javafx.stage.PopupWindow
import javafx.stage.Screen
import javafx.stage.Window
import javafx.util.Duration
import tornadofx.keyframe
import kotlin.properties.Delegates

/**
 * we do not allow instantiation of the Notifications class directly - users
 * must go via the builder API (that is, calling create())
 */
class KFXNotifications private constructor() {

    /***************************************************************************
     *                         * Private fields *
     **************************************************************************/

    private var title = ""

    private var text = ""

    private var typeMessage = "MESSAGE"

    private var graphic: Node = ImageView(KFXNotifications::class.java.getResource("/img/dialog-message.png").toExternalForm())

    private var position = Pos.BOTTOM_RIGHT

    private var hideAfterDuration = Duration.seconds(5.0)

    private var hideCloseButton = false

    private var onAction = EventHandler<ActionEvent> {}

    private var owner: Window? = null

    private var screen = Screen.getPrimary()

    private val styleClass = ArrayList<String>()

    /**
     * {@link StandardKt#also} is a convenience function for return the instance of this class.
     * Useful for Builder pattern.
     */

    /**
     * Specify the text to show in the notification.
     */
    fun text(text: String): KFXNotifications = also { this.text = text }

    /**
     * Specify the title to show in the notification.
     */
    fun title(title: String): KFXNotifications = also {  this.title = title }

    /**
     * Specify the graphic to show in the notification.
     */
    fun graphic(graphic: Node): KFXNotifications = also { this.graphic = graphic }

    /**
     * Specify the position of the notification on screen, by default it is
     * [bottom-right][Pos.BOTTOM_RIGHT].
     */
    fun position(position: Pos): KFXNotifications = also { this.position = position }

    /**
     * The dialog window owner - which can be [Screen], [Window]
     * or [Node]. If specified, the notifications will be inside
     * the owner, otherwise the notifications will be shown within the whole
     * primary (default) screen.
     */
    fun owner(owner: Any): KFXNotifications = also {
        if (owner is Screen) {
            this.screen = owner
        } else {
            this.owner = getWindow(owner)
        }
    }

    /**
     * Specify the duration that the notification should show, after which it
     * will be hidden.
     */
    fun hideAfter(duration: Duration): KFXNotifications = also {  this.hideAfterDuration = duration }

    /**
     * Specify what to do when the user clicks on the notification (in addition
     * to the notification hiding, which happens whenever the notification is
     * clicked on).
     */
    fun onAction(onAction: EventHandler<ActionEvent>): KFXNotifications = also { this.onAction = onAction }

    /**
     * Specify that the close button in the top-right corner of the notification
     * should not be shown.
     */
    fun hideCloseButton(): KFXNotifications = also { this.hideCloseButton = true }

    /**
     * Instructs the notification to be shown, and that it should use the
     * built-in 'warning' graphic.
     */
    fun showMessage() {
        graphic(ImageView(KFXNotifications::class.java.getResource("/img/dialog-message.png").toExternalForm()))
        typeMessage = "MESSAGE"
        show()
    }

    /**
     * Instructs the notification to be shown, and that it should use the
     * built-in 'information' graphic.
     */
    fun showSuccess() {
        graphic(ImageView(KFXNotifications::class.java.getResource("/img/dialog-success.png").toExternalForm()))
        typeMessage = "SUCCESS"
        show()
    }

    /**
     * Instructs the notification to be shown, and that it should use the
     * built-in 'error' graphic.
     */
    fun showError() {
        graphic(ImageView(KFXNotifications::class.java.getResource("/img/dialog-error.png").toExternalForm()))
        typeMessage = "ERROR"
        show()
    }

    /**
     * Instructs the notification to be shown, and that it should use the
     * built-in 'confirm' graphic.
     */
    fun showWarning() {
        graphic(ImageView(KFXNotifications::class.java.getResource("/img/dialog-warning.png").toExternalForm()))
        typeMessage = "WARNING"
        show()
    }

    /**
     * Instructs the notification to be shown.
     */
    fun show() {
        KFXNotifications.NotificationPopupHandler.show(this)
    }

    /***************************************************************************
     *                         * Private Support Classes *
     **************************************************************************/

    private object NotificationPopupHandler {

        private var startX = 0.0
        private var startY = 0.0
        private var screenWidth = 0.0
        private var screenHeight = 0.0

        private val popupsMap = HashMap<Pos, MutableList<Popup>>()
        private val padding = 15.0

        // for animating in the notifications
        private val parallelTransition = ParallelTransition()

        var isShowing = false

        fun show(notification: KFXNotifications) {
            var window: Window by Delegates.notNull()
            if (notification.owner == null) {
                /*
                 * If the owner is not set, we work with the whole screen.
                 */
                notification.screen.visualBounds.let { sb ->
                    startX = sb.minX
                    startY = sb.minY
                    screenWidth = sb.width
                    screenHeight = sb.height
                }

                window = getWindow(null)
            } else {
                /*
                 * If the owner is set, we will make the notifications popup
                 * inside its window.
                 */
                notification.owner?.let {
                    startX = it.x
                    startY = it.y
                    screenWidth = it.width
                    screenHeight = it.height
                    window = it
                }

            }
            show(window, notification)
        }

        private fun show(owner: Window, notification: KFXNotifications) {
            // Stylesheets which are added to the scene of a popup aren't
            // considered for styling. For this reason, we need to find the next
            // window in the hierarchy which isn't a popup.
            var ownerWindow = owner

            while (ownerWindow is PopupWindow) {
                ownerWindow = ownerWindow.ownerWindow
            }
            // need to install our CSS
            val ownerScene = ownerWindow.scene

            if (ownerScene != null) {
                val stylesheetUrl = KFXNotifications::class.java.getResource("/css/notificationpopup.css").toExternalForm()
                if (!ownerScene.stylesheets.contains(stylesheetUrl)) {
                    // The stylesheet needs to be added at the beginning so that
                    // the styling can be adjusted with custom stylesheets.
                    ownerScene.stylesheets.add(0, stylesheetUrl)
                }
            }

            val popup = Popup()
            popup.isAutoFix = false

            val p = notification.position

            val notificationBar = object : KFXNotificationsBar() {
                override val title: String
                    get() = notification.title

                override val text: String
                    get() = notification.text

                override val typeNotification: String
                    get() = notification.typeMessage

                override val graphic: Node
                    get() = notification.graphic

                override var isShowing: Boolean = false
                    get() = this@NotificationPopupHandler.isShowing

                override val isShowFromTop: Boolean
                    get() = this@NotificationPopupHandler.isShowFromTop(notification.position)

                override val isCloseButtonVisible: Boolean
                    get() = !notification.hideCloseButton

                override val containerHeight: Double
                    get() = startY + screenHeight

                override fun computeMinWidth(height: Double): Double
                        = if (text.isEmpty()) graphic.minWidth(height) else 400.0

                override fun computeMinHeight(width: Double): Double
                        = if (text.isEmpty()) graphic.minHeight(width) else 100.0

                override fun hide() {
                    isShowing = false

                    // this would slide the notification bar out of view,
                    // but I prefer the fade out below
                    //doHide()

                    // animate out the popup by fading it
                    createHideTimeline(popup, this, p, Duration.ZERO)
                }
                // this allows for us to slide the notification upwards
                override fun relocateInParent(x: Double, y: Double) = when (p) {
                    Pos.BOTTOM_LEFT,
                    Pos.BOTTOM_CENTER,
                    Pos.BOTTOM_RIGHT -> popup.anchorY = y - this@NotificationPopupHandler.padding
                    else -> { }
                }
            }

            notificationBar.styleClass.addAll(notification.styleClass)

            notificationBar.setOnMouseClicked { _ ->
                val actionEvent = ActionEvent(notificationBar, notificationBar)
                notification.onAction.handle(actionEvent)

                // animate out the popup
                createHideTimeline(popup, notificationBar, p, Duration.ZERO)
            }

            popup.content.add(notificationBar)
            popup.show(owner, 0.0, 0.0)

            // determine location for the popup
            val barWidth = notificationBar.width
            val barHeight = notificationBar.height

            // get anchorX
            popup.anchorX = when (p) {
                Pos.TOP_LEFT,
                Pos.CENTER_LEFT,
                Pos.BOTTOM_LEFT -> padding + startX

                Pos.TOP_CENTER,
                Pos.CENTER,
                Pos.BOTTOM_CENTER -> startX + screenWidth / 2.0 - barWidth / 2.0 - padding / 2.0

                Pos.TOP_RIGHT,
                Pos.CENTER_RIGHT,
                Pos.BOTTOM_RIGHT -> startX + screenWidth - barWidth - padding

                else -> startX + screenWidth - barWidth - padding
            }

            // get anchorY
            popup.anchorY = when (p) {
                Pos.TOP_LEFT,
                Pos.TOP_CENTER,
                Pos.TOP_RIGHT -> padding + startY

                Pos.CENTER_LEFT,
                Pos.CENTER,
                Pos.CENTER_RIGHT -> startY + screenHeight / 2.0 - barHeight / 2.0 - padding / 2.0

                Pos.BOTTOM_LEFT,
                Pos.BOTTOM_CENTER,
                Pos.BOTTOM_RIGHT -> startY + screenHeight - barHeight - padding

                else -> startY + screenHeight - barHeight - padding
            }

            isShowing = true
            notificationBar.doShow()

            addPopupToMap(p, popup)

            // begin a timeline to get rid of the popup
            createHideTimeline(popup, notificationBar, p, notification.hideAfterDuration)
        }

        private fun hide(popup: Popup, p: Pos) {
            popup.hide()
            removePopupFromMap(p, popup)
        }

        private fun createHideTimeline(popup: Popup, bar: KFXNotificationsBar, p: Pos, startDelay: Duration): Timeline {
            return tornadofx.timeline {
                delay = startDelay
                keyframe(Duration.ZERO) {
                    keyvalue(bar.opacityProperty(), 1.0)
                }
                keyframe(Duration.millis(500.0)) {
                    keyvalue(bar.opacityProperty(), 0.0)
                }

                setOnFinished { hide(popup, p) }
            }
        }

        private fun addPopupToMap(p: Pos, popup: Popup) {
            val popups: MutableList<Popup>
            if (!popupsMap.containsKey(p)) {
                popups = LinkedList()
                popupsMap.put(p, popups)
            } else {
                popups = popupsMap[p]!!
            }

            doAnimation(p, popup)

            // add the popup to the list so it is kept in memory and can be
            // accessed later on
            popups.add(popup)
        }

        private fun removePopupFromMap(p: Pos, popup: Popup) {
            popupsMap[p]?.remove(popup)
        }

        private fun doAnimation(p: Pos, changedPopup: Popup) {
            val popups = popupsMap[p] ?: return

            val newPopupHeight = changedPopup.content[0].boundsInParent.height

            parallelTransition.stop()
            parallelTransition.children.clear()

            val isShowFromTop = isShowFromTop(p)

            // animate all other popups in the list upwards so that the new one
            // is in the 'new' area.
            // firstly, we need to determine the target positions for all popups
            var sum = 0.0
            val targetAnchors = DoubleArray(popups.size)
            for (i in popups.indices.reversed()) {
                val _popup = popups[i]

                val popupHeight = _popup.content[0].boundsInParent.height

                if (isShowFromTop) {
                    if (i == popups.size - 1) {
                        sum = startY + newPopupHeight + padding
                    } else {
                        sum += popupHeight
                    }
                    targetAnchors[i] = sum
                } else {
                    if (i == popups.size - 1) {
                        sum = changedPopup.anchorY - popupHeight
                    } else {
                        sum -= popupHeight
                    }

                    targetAnchors[i] = sum
                }
            }

            // then we set up animations for each popup to animate towards the
            // target
            for (i in popups.indices.reversed()) {
                val _popup = popups[i]
                val anchorYTarget = targetAnchors[i]
                if (anchorYTarget < 0) {
                    _popup.hide()
                }
                val oldAnchorY = _popup.anchorY
                val distance = anchorYTarget - oldAnchorY

                val t = KFXNotifications.NotificationPopupHandler.CustomTransition(_popup, oldAnchorY, distance)
                t.cycleCount = 1
                parallelTransition.children.add(t)
            }
            parallelTransition.play()
        }

        private fun isShowFromTop(p: Pos): Boolean = when (p) {
            Pos.TOP_LEFT, Pos.TOP_CENTER, Pos.TOP_RIGHT -> true
            else -> false
        }

        class CustomTransition(popup: Popup, private val oldAnchorY: Double, private val distance: Double) : Transition() {

            private val popupWeakReference: WeakReference<Popup> = WeakReference(popup)

            init {
                cycleDuration = Duration.millis(350.0)
            }

            override fun interpolate(frac: Double) {
                popupWeakReference.get()?.let { it.anchorY = oldAnchorY + distance * frac }
            }

        }
    }

    companion object {

        /***************************************************************************
         *                         * Public API *
         **************************************************************************/

        /**
         * Call this to begin the process of building a notification to show.
         */
        @JvmStatic fun create(): KFXNotifications = KFXNotifications()

        private fun getWindow(owner: Any?): Window {
            var window: Window by Delegates.notNull()
            return when (owner) {
                null -> {
                    Window.impl_getWindows().forEach {
                        window = it
                        if(window.isFocused && window !is PopupWindow) {
                            return@forEach
                        }
                    }
                    window
                }
                is Window -> owner
                is Node   -> owner.scene.window
                else -> { throw IllegalArgumentException("??") }
            }
        }
    }
}

