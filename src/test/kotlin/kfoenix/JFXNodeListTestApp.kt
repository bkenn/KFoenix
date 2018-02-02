package kfoenix

import javafx.geometry.Pos
import javafx.scene.paint.Color
import tornadofx.*


class JFXNodeListTestApp : App(Main::class, MyStyles::class) {

    class Main: View() {

        override val root = vbox {
            addClass(MyStyles.box)

            jfxnodeslist {
                addClass(MyStyles.jfxNodeList1)
                listOf("B1", "B2", "B3").forEach { name ->
                    jfxbutton(name) {
                        addClass(MyStyles.animatedOptionButton, MyStyles.animatedSub1OptionButton)
                    }
                }

                jfxnodeslist {
                    addClass(MyStyles.jfxNodeList2)
                    listOf("G1", "G2", "G3").forEach { name ->
                        jfxbutton(name) {
                            addClass(MyStyles.animatedOptionButton, MyStyles.animatedSub2OptionButton)
                        }
                    }

                    jfxnodeslist {
                        addClass(MyStyles.jfxNodeList3)
                        listOf("S1", "S2", "S3").forEach { name ->
                            jfxbutton(name) {
                                addClass(MyStyles.animatedOptionButton, MyStyles.animatedSub3OptionButton)
                            }
                        }

                        jfxnodeslist {
                            addClass(MyStyles.jfxNodeList4)
                            listOf("D1", "D2", "D3").forEach { name ->
                                jfxbutton(name) {
                                    addClass(MyStyles.animatedOptionButton, MyStyles.animatedSub4OptionButton)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    open class MyStyles: Stylesheet() {

        companion object {
            val box by cssclass()
            val jfxButton by cssclass()
            val jfxRippler by cssclass()

            val jfxNodeList1 by cssclass()
            val jfxNodeList2 by cssclass()
            val jfxNodeList3 by cssclass()
            val jfxNodeList4 by cssclass()

            val animatedOptionButton by cssclass()

            val animatedSub1OptionButton by cssclass()
            val animatedSub2OptionButton by cssclass()
            val animatedSub3OptionButton by cssclass()
            val animatedSub4OptionButton by cssclass()

            val defaultColor = Color.web("#4059a9")
            val buttonType by cssproperty<String>("-jfx-button-type")
        }

        init {
            /* Must place default styling first. Will overwrite more specific styling */
            jfxButton {
                backgroundColor += defaultColor
                fontSize = 14.px
                padding = box(0.7.em, 0.57.em)
                textFill = Color.WHITE
                buttonType.value =  "RAISED"
            }

            box {
                prefHeight = 600.px
                prefWidth = 800.px
                alignment = Pos.CENTER
            }

            jfxNodeList1 {
                spacing = 10.px
            }

            jfxNodeList2 {
                spacing = 10.px
                rotate = (90).deg
            }

            jfxNodeList3 {
                spacing = 10.px
                rotate = 180.deg
            }

            jfxNodeList4 {
                spacing = 10.px
                rotate = 90.deg
            }

            animatedOptionButton {
                backgroundColor += Color.web("#44B449")
                backgroundRadius += box(50.px)
                borderColor += box(Color.WHITE)
                borderRadius += box(50.px)
                borderWidth += box(4.px)
                fontSize = 14.px
                padding = box(0.7.em, 0.57.em)
                prefHeight = 50.px
                prefWidth = 50.px
                textFill = Color.WHITE
                spacing = 20.px
            }

            animatedSub1OptionButton {
                backgroundColor += Color.web("#43609C")
            }

            animatedSub2OptionButton {
                backgroundColor += Color.rgb(203, 104, 96)
            }

            animatedSub3OptionButton {
                backgroundColor += Color.web("#f9a825")
            }

            animatedSub4OptionButton {
                backgroundColor += Color.web("#c2185b")
            }
        }
    }

}