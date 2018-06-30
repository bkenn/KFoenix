package kfoenix

import javafx.geometry.Pos
import javafx.scene.paint.Color
import tornadofx.*

class JFXButtonTestApp2 : App(Main::class, CustomStyles::class)

class Main : View() {

    override val root = vbox {
        addClass(CustomStyles.container)
        jfxbutton("Flat")
        jfxbutton("Raised").addClass(JFXStylesheet.jfxButtonRaised)
        jfxbutton("Raised w/ Yellow Fill").addClass(CustomStyles.jfxButtonFill)
    }
}

class CustomStyles : JFXStylesheet() {

    companion object {
        val container by cssclass()
        val jfxButtonFill by cssclass()
    }

    init {
        container {
            prefHeight = 300.px
            prefWidth = 400.px
            alignment = Pos.CENTER
            spacing = 20.px
        }

        jfxButton {
            backgroundColor += Color.web("#4059a9")
            textFill = Color.WHITE
            prefWidth = 200.px
            prefHeight = 50.px
        }

        jfxButtonFill {
            jfxRippler {
                jfxRipplerFill.value = Color.YELLOW
            }
        }
    }
}