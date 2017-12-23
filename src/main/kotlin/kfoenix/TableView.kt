package kfoenix

import com.jfoenix.controls.JFXCheckBox
import javafx.geometry.Pos
import javafx.scene.control.TableCell
import tornadofx.*

class JFXCheckBoxCell<S>(val makeEditable: Boolean): TableCell<S, Boolean?>() {
    val checkbox: JFXCheckBox by lazy {
        JFXCheckBox().apply {
            if(makeEditable) {
                selectedProperty().bindBidirectional(itemProperty())
                setOnAction {
                    tableView.edit(index, tableColumn)
                    commitEdit(!isSelected)
                }
            } else {
                isDisable = true
                selectedProperty().bind(itemProperty())
            }
        }
    }
    init {
        if(makeEditable) {
            isEditable = true
            tableView?.isEditable = true
        }
    }

    override fun updateItem(item: Boolean?, empty: Boolean) {
        super.updateItem(item, empty)
        style { alignment = Pos.CENTER }
        graphic = if (empty || item == null) null else checkbox
    }
}