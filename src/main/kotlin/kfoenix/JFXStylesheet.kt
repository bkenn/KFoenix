package kfoenix

import javafx.scene.paint.Paint
import tornadofx.*

open class JFXStylesheet : Stylesheet() {

    /****************************************************************************************
                                          JFXButton
     ****************************************************************************************/

    val jfxButton by cssclass()

    /**
     * Options
     *  1. FLAT
     *  2. RAISED
     */
    val jfxButtonType by cssproperty<String>("-jfx-button-type")

    val jfxDisableVisualFocus by cssproperty<Boolean>("-jfx-disable-visual-focus")




    /****************************************************************************************
                                          JFXCheckBox
     ****************************************************************************************/

    val jfxCheckBox by cssclass()

    val jfxCheckedColor by cssproperty<Paint>("-jfx-checked-color")

    val jfxUnCheckedColor by cssproperty<Paint>("-jfx-unchecked-color")

    /****************************************************************************************
                                           JFXRippler
     ****************************************************************************************/

    val jfxRippler by cssclass()

    val jfxRipplerFill by cssproperty<Paint>("-jfx-rippler-fill")

    /****************************************************************************************
                                          JFXToggleButton
     ****************************************************************************************/

    val jfxTogglerButton by cssclass()

    val jfxToggleColor by cssproperty<Paint>("-jfx-toggle-color")
    val jfxUnToggleColor by cssproperty<Paint>("-jfx-untoggle-color")

}