package kfoenix

import com.jfoenix.controls.JFXButton
import com.jfoenix.controls.JFXButton.ButtonType.*
import javafx.geometry.Pos
import javafx.scene.paint.Color
import javafx.scene.paint.Paint
import tornadofx.*

class JFXButtonTestApp2 : App(Main::class, CustomStyles::class) {

    class Main: View() {

        override val root = hbox {
            addClass(CustomStyles.container)
            this += JFXButton("Flat")
            this += JFXButton("Raised").addClass(CustomStyles.customButton)
        }
    }


    class CustomStyles : Stylesheet() {

        companion object {
            val container by cssclass()
            val jfxButton by cssclass()
            val jfxRippler by cssclass()
            val jfxRipplerFill by cssproperty<Paint>("-jfx-rippler-fill")
            val jfxButtonType by cssproperty<String>("-jfx-button-type")

            val customButton by cssclass()
        }

        init {
            container {
                prefHeight = 400.px
                prefWidth = 400.px
                alignment = Pos.CENTER
                spacing = 20.px
            }
            jfxButton {
                backgroundColor += Color.web("#4059a9")
                textFill = Color.WHITE
                jfxButtonType.value = FLAT.name.toUpperCase()
                jfxRippler {
                    jfxRipplerFill.value = Color.BLACK
                }
            }
            customButton {
                jfxButtonType.value = RAISED.name.toUpperCase()
            }
        }
    }
}