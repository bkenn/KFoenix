package kfoenix

import tornadofx.*
import kotlin.reflect.KClass

class ReplaceContentEvent<T: UIComponent>(val uiComponent: KClass<T>, scope: Scope = DefaultScope): FXEvent(scope=scope)
