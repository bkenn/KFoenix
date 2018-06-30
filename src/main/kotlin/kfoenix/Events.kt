package kfoenix

import tornadofx.*
import kotlin.reflect.KClass

/**
 * @param uiComponent View or Fragment to find.
 * @param findScope the scope for the uiComponent to use
 */
class ReplaceContentEvent<T: UIComponent>(val uiComponent: KClass<T>, val findScope: Scope = DefaultScope): FXEvent()
