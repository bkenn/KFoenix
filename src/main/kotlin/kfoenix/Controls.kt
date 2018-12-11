package kfoenix

import com.jfoenix.controls.*
import com.jfoenix.controls.JFXButton.ButtonType.*
import javafx.beans.property.Property
import javafx.beans.value.ObservableValue
import javafx.event.EventTarget
import javafx.geometry.Orientation
import javafx.scene.Node
import javafx.scene.control.ButtonBar
import javafx.scene.control.ToggleGroup
import javafx.scene.control.ToolBar
import javafx.scene.layout.Pane
import javafx.scene.layout.Region
import javafx.util.StringConverter
import tornadofx.*
import java.time.LocalDate

/**
 * Badge
 */
fun EventTarget.jfxbadge(op: JFXBadge.() -> Unit = {}) = opcr(this, JFXBadge(), op)

/**
 * Buttons
 */
fun ButtonBar.jfxbutton(value: String? = null,
                        btnType: JFXButton.ButtonType = FLAT,
                        graphic: Node? = null,
                        type: ButtonBar.ButtonData? = null,
                        op: JFXButton.() -> Unit = {}): JFXButton {
    val button = JFXButton(value)
    button.buttonType = btnType
    if(graphic != null) button.graphic = graphic
    if(type != null)    ButtonBar.setButtonData(button, type)
    buttons.add(button)
    return button.also(op)
}

fun ButtonBar.jfxbutton(property :ObservableValue<String>,
                        btnType: JFXButton.ButtonType = FLAT,
                        graphic: Node? = null,
                        type: ButtonBar.ButtonData? = null,
                        op: JFXButton.() -> Unit = {}): JFXButton
        = jfxbutton(btnType = btnType, graphic = graphic, type = type, op = op).apply { bind(property) }

fun EventTarget.jfxbutton(value :String? = null,
                          btnType: JFXButton.ButtonType = FLAT,
                          graphic: Node? = null,
                          op: JFXButton.() -> Unit = {}): JFXButton {
    val button = JFXButton(value)
    button.buttonType = btnType
    if(graphic != null) button.graphic = graphic
    return opcr(this, button, op)
}

fun EventTarget.jfxbutton(property :ObservableValue<String>,
                          btnType: JFXButton.ButtonType = FLAT,
                          graphic: Node? = null,
                          op: JFXButton.() -> Unit = {}): JFXButton
        = jfxbutton(btnType = btnType, graphic = graphic, op = op).apply { bind(property) }

fun ToolBar.jfxbutton(value: String? = null,
                      btnType: JFXButton.ButtonType = FLAT,
                      graphic: Node? = null, op: JFXButton.() -> Unit = {}): JFXButton {
    val button = JFXButton(value)
    button.buttonType = btnType
    if(graphic != null) button.graphic = graphic
    add(button)
    return button.also(op)
}

fun ToolBar.jfxbutton(property :ObservableValue<String>,
                      btnType: JFXButton.ButtonType = FLAT,
                      graphic: Node? = null,
                      op: JFXButton.() -> Unit = {}): JFXButton
        = jfxbutton(btnType = btnType, graphic = graphic, op = op).apply { bind(property) }

/**
 * CheckBox
 */
fun EventTarget.jfxcheckbox(value: String? = null,
                            op: JFXCheckBox.() -> Unit = {}): JFXCheckBox
        = opcr(this, JFXCheckBox(value), op)

fun EventTarget.jfxcheckbox(property: ObservableValue<Boolean>,
                            value: String? = null,
                            op: JFXCheckBox.() -> Unit = {}): JFXCheckBox
        = jfxcheckbox(value = value, op = op).apply { bind(property) }

/**
 * ColorPicker
 */
fun EventTarget.jfxcolorpicker(op: JFXColorPicker.() -> Unit = {}) = opcr(this, JFXColorPicker(), op)

/**
 * DatePicker
 */
fun EventTarget.jfxdatepicker(op: JFXDatePicker.() -> Unit = {}) : JFXDatePicker = opcr(this, JFXDatePicker(), op)

fun EventTarget.jfxdatepicker(property: Property<LocalDate>, op: JFXDatePicker.() -> Unit = {}): JFXDatePicker
        = jfxdatepicker(op).apply { bind(property) }

/**
 * Hamburger
 */
fun EventTarget.jfxhamburger(op: JFXHamburger.() -> Unit = {}): JFXHamburger = opcr(this, JFXHamburger(), op)

/**
 *  NodeList
 */
fun EventTarget.jfxnodeslist(op: JFXNodesList.() -> Unit = {}) : JFXNodesList {
    val nodeList = JFXNodesList()
    val interceptor = object: ChildInterceptor {
        override fun invoke(parent: EventTarget, node: Node, index: Int?): Boolean {
            if(parent is JFXNodesList && node is Region) {
                parent.addAnimatedNode(node)
                return true
            }
            return false
        }
    }
    FX.addChildInterceptor(interceptor)
    nodeList.op()
    FX.removeChildInterceptor(interceptor)
    addChildIfPossible(nodeList)
    return nodeList
}

/**
 * Passwordfield
 */
fun EventTarget.jfxpasswordfield(promptText: String? = null, op: JFXPasswordField.() -> Unit = {}): JFXPasswordField  {
    val passwordField = JFXPasswordField()
    if(promptText != null) passwordField.promptText = promptText
    return opcr(this, passwordField, op)
}

fun EventTarget.jfxpasswordfield(property: ObservableValue<String>,
                                 promptText: String? = null,
                                 op: JFXPasswordField.() -> Unit = {})
        = jfxpasswordfield(promptText, op).apply { textProperty().bind(property) }

fun EventTarget.jfxpasswordfield(property: Property<String>,
                                 promptText: String? = null,
                                 op: JFXPasswordField.() -> Unit = {}): JFXPasswordField
        = jfxpasswordfield(promptText, op).apply { textProperty().bindBidirectional(property) }

/**
 * Popup
 */
fun EventTarget.jfxpopup(op: JFXPopup.() -> Unit = {}) = JFXPopup().apply(op)

/**
 * Progressbar
 */
fun EventTarget.jfxprogressbar(op: JFXProgressBar.() -> Unit = {}): JFXProgressBar
        = opcr(this, JFXProgressBar(), op)

/**
 * RadioButton
 */
fun Node.jfxradiobutton(text: String? = null,
                        group: ToggleGroup? = getToggleGroup(),
                        value: Any? = null,
                        op: JFXRadioButton.() -> Unit = {}) = opcr(this, JFXRadioButton().apply {
    this.text = if (value != null && text == null) value.toString() else text ?: ""
    properties["tornadofx.toggleGroupValue"] = value ?: text
    if (group != null) toggleGroup = group
}, op)

/**
 * Rippler
 */
fun Node.jfxrippler(op: JFXRippler.() -> Unit = {}): JFXRippler {
    val rippler = JFXRippler(this)
    rippler.op()
    return rippler
}

/**
 * TextArea
 */
fun EventTarget.jfxtextarea(value: String? = null, op: JFXTextArea.() -> Unit = {}): JFXTextArea
        = opcr(this, JFXTextArea(value), op)

fun EventTarget.jfxtextarea(property: ObservableValue<String>, op: JFXTextArea.() -> Unit = {}): JFXTextArea
        = jfxtextarea(op=op).apply { bind(property) }

fun <T> EventTarget.jfxtextarea(property: Property<T>,
                                converter: StringConverter<T>,
                                op: JFXTextArea.() -> Unit = {}) : JFXTextArea = jfxtextarea().apply {
    textProperty().bindBidirectional(property, converter)
    ViewModel.register(textProperty(), property)
    op(this)
}

/*
// possible implementation that gives more control over snackbar
fun View.jfxsnackbar(message: String, timeout: Long = -1, op: JFXSnackbar.() -> Unit = {}) {
    if(root is Pane) {
        val bar = JFXSnackbar(root as Pane)
        bar.op()
        bar.show(message, timeout)
    }
}
 */

/**
 * SnackBar
 */
fun <T: Pane> jfxsnackbar(message: String, pane: T, op: JFXSnackbar.() -> Unit = {})
        = JFXSnackbar(pane).also(op).enqueue(JFXSnackbar.SnackbarEvent(JFXSnackbarLayout(message)))

fun Pane.jfxsnackbar(message: String, op: JFXSnackbar.() -> Unit = {}) = jfxsnackbar(message,this, op)

/**
 * Slider
 */
fun EventTarget.jfxslider(min: Double? = null,
                          orientation: Orientation = Orientation.HORIZONTAL,
                          indicatorPosition: JFXSlider.IndicatorPosition = JFXSlider.IndicatorPosition.LEFT,
                          op: JFXSlider.() -> Unit = {}): JFXSlider {
    val slider = JFXSlider()

    slider.indicatorPosition = indicatorPosition

    if(orientation == Orientation.HORIZONTAL) {
        if(min != null) slider.minWidth = min
    } else {
        if(min != null) slider.minHeight = min
    }

    return opcr(this, slider, op)
}

/**
 * TabPane
 */
fun EventTarget.jfxtabpane(op: JFXTabPane.() -> Unit = {}): JFXTabPane = opcr(this, JFXTabPane(), op)

/**
 * Textfield
 */
fun EventTarget.jfxtextfield(value: String? = null,
                             promptText: String? = null,
                             labelFloat: Boolean = false,
                             op: JFXTextField.() -> Unit = {}): JFXTextField {
    val textfield = JFXTextField(value)
    textfield.isLabelFloat = labelFloat
    if(promptText != null) textfield.promptText = promptText
    return opcr(this,textfield,op)
}

fun EventTarget.jfxtextfield(property: ObservableValue<String>,
                             promptText: String? = null,
                             labelFloat: Boolean = false,
                             op: JFXTextField.() -> Unit = {}) : JFXTextField
        = jfxtextfield(promptText = promptText, labelFloat = labelFloat, op = op).apply { bind(property) }


/**
 * ToggleButton
 */
fun Node.jfxtogglebutton(text: String? = null,
                         group: ToggleGroup? = getToggleGroup(),
                         selectFirst: Boolean = true,
                         value: Any? = null,
                         op: JFXToggleButton.() -> Unit = {}) =
        opcr(this, JFXToggleButton().apply {
            this.text = if (value != null && text == null) value.toString() else text ?: ""
            properties["tornadofx.toggleGroupValue"] = value ?: text
            if (group != null) toggleGroup = group
            if (toggleGroup?.selectedToggle == null && selectFirst) isSelected = true
        }, op)
