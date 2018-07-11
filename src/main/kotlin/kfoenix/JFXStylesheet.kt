package kfoenix

import com.jfoenix.controls.JFXDialog
import com.jfoenix.controls.JFXRippler
import javafx.scene.paint.Paint
import javafx.scene.text.Font
import tornadofx.*

open class JFXStylesheet : Stylesheet() {

    companion object {

        /* ***************************************************************************************
                                             JFXAlert
         *************************************************************************************** */

        val jfxAlertContentContainer by cssclass()

        val jfxAlertOverlay by cssclass()


        /* ***************************************************************************************
                                        JFXAutoCompletePopup
         *************************************************************************************** */

        val jfxAutoCompletePopup by cssclass()
        /*
         * Default: 10
         */
        val jfxCellLimit by cssproperty<Int>("-jfx-cell-limit")

        /*
         * Default 24
         */
        val fxFixedCellSize by cssproperty<Int>("-jfx-fixed-cell-size")

        val jfxBadge by cssclass()


        /* ***************************************************************************************
                                            JFXButton
         *************************************************************************************** */

        val jfxButton by cssclass()

        val jfxButtonRaised by cssclass()

        /*
         *  Options
         *  1. FLAT
         *  2. RAISED
         */
        val jfxButtonType by cssproperty<String>("-jfx-button-type")

        // JFXButton, JFXCheckBox
        val jfxDisableVisualFocus by cssproperty<Boolean>("-jfx-disable-visual-focus")


        /* ***************************************************************************************
                                            JFXCheckBox
         *************************************************************************************** */

        val jfxCheckBox by cssclass()

        val jfxCheckedColor by cssproperty<Paint>("-jfx-checked-color")

        val jfxUnCheckedColor by cssproperty<Paint>("-jfx-unchecked-color")

        /* ***************************************************************************************
                                             JFXChip
        *************************************************************************************** */

        val jfxChip by cssclass()

        val jfxChipView by cssclass()

        val closeButton by cssclass()

        /* ***************************************************************************************
                                            JFXColorPicker
        *************************************************************************************** */

        val jfxColorPicker by cssclass()

        /* ***************************************************************************************
                                            JFXCombox
        *************************************************************************************** */

        val jfxComboBox by cssclass()

        val jfxUnfocusColor by cssproperty<Paint>("-jfx-unfocus-color")

        val jfxFocusColor by cssproperty<Paint>("-jfx-focus-color")

        val jfxLabelFloat by cssproperty<Boolean>("-jfx-label-float")

        /* ***************************************************************************************
                                          JFXDatePicker
        *************************************************************************************** */

        val jfxDatePicker by cssclass()

        val jfxDefaultColor by cssproperty<Paint>("-jfx-default-color")

        val jfxOverlay by cssproperty<Boolean>("-jfx-overlay")


        /* ***************************************************************************************
                                          JFXDecorator
        *************************************************************************************** */

        val jfxDecorator by cssclass()

        // Full, Close, Min, Max
        val jfxDecoratorButton by cssclass()

        // default background color is black
        val jfxDecoratorButtonsContainer by cssclass()

        val jfxDecoratorTitle by cssclass()

        val jfxDecoratorTitleContainer by cssclass()

        val jfxDecoratorContainer by cssclass()

        val jfxDecoratorContentContainer by cssclass()

        val resizeBorder by cssclass()


        /* ***************************************************************************************
                                          JFXDialog
        *************************************************************************************** */

        val jfxDialog by cssclass()

        val jfxDialogOverlayPane by cssclass()

        val jfxDialogTransition by cssproperty<JFXDialog.DialogTransition>("-jfx-dialog-transition")


        /* ***************************************************************************************
                                          JFXDrawer
        *************************************************************************************** */

        val jfxDrawer by cssclass()

        val jfxDrawerOverlayPane by cssclass()

        val jfxDrawerSidePane by cssclass()

        /* ***************************************************************************************
                                          JFXDialog
        *************************************************************************************** */

        val jfxHamburger by cssclass()

        /* ***************************************************************************************
                                          JFXListCell
        *************************************************************************************** */

        val jfxListCell by cssclass()

        val sublistContainer by cssclass()

        val sublistItem by cssclass()

        val sublistHeader by cssclass()

        val dropIcon by cssclass()

        /* ***************************************************************************************
                                            JFXList
        *************************************************************************************** */

        val jfxListView by cssclass()

        val jfxVerticalGap by cssproperty<Number>("-jfx-vertical-gap")

        val jfxExpanded by cssproperty<Boolean>("-jfx-expanded")

        /* ***************************************************************************************
                                            JFXNodesList
        *************************************************************************************** */

        val jfxNodesList by cssclass()

        val subNode by cssclass()

        /* ***************************************************************************************
                                            JFXPassword
        *************************************************************************************** */

        val jfxPasswordField by cssclass()

        /* ***************************************************************************************
                                            JFXPopup
        *************************************************************************************** */

        val jfxPopup by cssclass()

        /* ***************************************************************************************
                                            JFXProgressBar
        *************************************************************************************** */

        val jfxProgressBar by cssclass()

        /* ***************************************************************************************
                                            JFXRadioButton
        *************************************************************************************** */

        val jfxRadioButton by cssclass()

        val jfxSelectedColor by cssproperty<Paint>("-jfx-selected-color")

        val jfxUnselectedColor by cssproperty<Paint>("-jfx-unselected-color")

        val jfxDisableAnimation by cssproperty<Boolean>("-jfx-disable-animation")

        // disableVisualFocus

        /* ***************************************************************************************
                                            JFXRippler
         *************************************************************************************** */

        val jfxRippler by cssclass()

        val jfxRipplerOverlay by cssclass()

        val jfxRipplerRecenter by cssproperty<Boolean>("-jfx-rippler-recenter")

        val jfxRipplerDisabled by cssproperty<Boolean>("-jfx-rippler-disabled")

        val jfxRipplerFill by cssproperty<Paint>("-jfx-rippler-fill")

        val jfxRipplerRadius by cssproperty<Number>("-jfx-rippler-radius")

        val jfxRipplerMaskType by cssproperty<JFXRippler.RipplerMask>("-jfx-rippler-mask-type")

        /* ***************************************************************************************
                                          JFXToggleButton
         *************************************************************************************** */

        val jfxTogglerButton by cssclass()

        val jfxToggleColor by cssproperty<Paint>("-jfx-toggle-color")

        val jfxUnToggleColor by cssproperty<Paint>("-jfx-untoggle-color")

        // jfxDisableAnimation

        /* ***************************************************************************************
                                          JFXToolbar
         *************************************************************************************** */

        val jfxToolbar by cssclass()
    }

    init {

        jfxButtonRaised {
            jfxButtonType.value = "RAISED"
        }

    }

}