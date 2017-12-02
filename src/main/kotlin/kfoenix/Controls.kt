package kfoenix

import com.jfoenix.controls.*
import com.jfoenix.controls.JFXButton.ButtonType.*
import javafx.beans.property.ObjectProperty
import javafx.beans.property.Property
import javafx.beans.value.ObservableValue
import javafx.event.EventTarget
import javafx.scene.Node
import javafx.scene.control.ButtonBar
import javafx.scene.control.ToolBar
import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import javafx.util.StringConverter
import tornadofx.*
import java.time.LocalDate

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

fun EventTarget.jfxcheckbox(value: String? = null,
                            op: JFXCheckBox.() -> Unit = {}): JFXCheckBox
        = opcr(this, JFXCheckBox(value), op)

fun EventTarget.jfxcheckbox(property: ObservableValue<Boolean>,
                            value: String? = null,
                            op: JFXCheckBox.() -> Unit = {}): JFXCheckBox
        = jfxcheckbox(value = value, op = op).apply { bind(property) }

fun EventTarget.jfxdatepicker(op: JFXDatePicker.() -> Unit = {}) : JFXDatePicker = opcr(this, JFXDatePicker(), op)

fun EventTarget.jfxdatepicker(property: Property<LocalDate>, op: JFXDatePicker.() -> Unit = {}): JFXDatePicker
        = jfxdatepicker(op).apply { bind(property) }

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

fun EventTarget.jfxradiobutton(value: String? = null,
                               selectedColor: ObservableValue<Color>? = null,
                               unselectedColor: ObservableValue<Color>? = null,
                               op: JFXRadioButton.() -> Unit = {}): JFXRadioButton {
    val radioButton = JFXRadioButton(value)
    if(selectedColor != null)   radioButton.selectedColorProperty().bind(selectedColor)
    if(unselectedColor != null) radioButton.selectedColorProperty().bind(unselectedColor)
    return opcr(this, radioButton, op)
}

fun EventTarget.jfxradiobutton(value: String? = null,
                               property: ObservableValue<Boolean>,
                               selectedColor: ObservableValue<Color>? = null,
                               unselectedColor: ObservableValue<Color>? = null,
                               op: JFXRadioButton.() -> Unit = {}): JFXRadioButton
        = jfxradiobutton(value, selectedColor, unselectedColor, op).apply { selectedProperty().bind(property) }


fun EventTarget.jfxradiobutton(value: String? = null,
                               property: Property<Boolean>,
                               selectedColor: ObservableValue<Color>? = null,
                               unselectedColor: ObservableValue<Color>? = null,
                               op: JFXRadioButton.() -> Unit = {}): JFXRadioButton
        = jfxradiobutton(value, selectedColor, unselectedColor, op).apply { selectedProperty().bindBidirectional(property) }


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

fun <T: Pane> jfxsnackbar(message: String, pane: T, op: JFXSnackbar.() -> Unit = {})
        = JFXSnackbar(pane).also(op).enqueue(JFXSnackbar.SnackbarEvent(message))

fun Pane.jfxsnackbar(message: String, op: JFXSnackbar.() -> Unit = {}) = jfxsnackbar(message,this, op)

fun EventTarget.jfxtabpane(op: JFXTabPane.() -> Unit = {}): JFXTabPane = opcr(this, JFXTabPane(), op)

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


fun EventTarget.jfxtogglebutton(value: String? = null,
                                color: ObjectProperty<Color>? = null,
                                op: JFXToggleButton.() -> Unit = {}): JFXToggleButton {
    val toggleButton = JFXToggleButton()
    if(value != null) toggleButton.text = value
    if(color != null) toggleButton.toggleColorProperty().bind(color)
    return opcr(this, toggleButton, op)
}

fun EventTarget.jfxtogglebutton(property: ObjectProperty<Boolean>,
                                value: String? = null,
                                color: ObjectProperty<Color>? = null,
                                op: JFXToggleButton.() -> Unit = {}): JFXToggleButton
        = jfxtogglebutton(value, color, op).apply { bind(property) }
