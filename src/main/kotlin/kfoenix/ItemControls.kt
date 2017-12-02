package kfoenix

import com.jfoenix.controls.JFXComboBox
import com.jfoenix.controls.JFXListView
import com.jfoenix.controls.JFXSpinner
import javafx.beans.property.Property
import javafx.beans.property.ReadOnlyListProperty
import javafx.beans.value.ObservableValue
import javafx.collections.ObservableList
import javafx.event.EventTarget
import tornadofx.*

fun <T> EventTarget.jfxcombobox(property: Property<T>? = null, values: List<T>? = null, op: JFXComboBox<T>.() -> Unit = {}): JFXComboBox<T> {
    val comboBox = JFXComboBox<T>().apply {
        if(values != null) items = values as? ObservableList<T> ?: values.observable()
        if(property != null) bind(property)
    }
    return opcr(this, comboBox, op)
}

fun <T> EventTarget.jfxlistview(items: ObservableList<T>? = null, op: JFXListView<T>.() -> Unit = {}): JFXListView<T> {
    val listView = JFXListView<T>()
    if (items != null) {
        if (items is SortedFilteredList<T>) items.bindTo(listView)
        else listView.items = items
    }
    return opcr(this, listView, op)
}

fun <T> EventTarget.jfxlistview(values: ReadOnlyListProperty<T>, op: JFXListView<T>.() -> Unit = {}): JFXListView<T>
        = jfxlistview(values as ObservableValue<ObservableList<T>>, op)

fun <T> EventTarget.jfxlistview(values: ObservableValue<ObservableList<T>>,
                                op: JFXListView<T>.() -> Unit = {}): JFXListView<T> = opcr(this, JFXListView<T>().apply {
    fun rebinder() { (items as? SortedFilteredList<T>)?.bindTo(this) }
    itemsProperty().bind(values)
    rebinder()
    itemsProperty().onChange { rebinder() }
}, op)

fun EventTarget.jfxspinner(property: ObservableValue<Boolean>? = null, op: JFXSpinner.() -> Unit = {}): JFXSpinner {
    val spinner = JFXSpinner()
    if(property != null) spinner.visibleProperty().bind(property)
    return opcr(this, spinner, op)
}