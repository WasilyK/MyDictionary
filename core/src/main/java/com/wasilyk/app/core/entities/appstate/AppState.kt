package com.wasilyk.app.core.entities.appstate

sealed class AppState

class Success<T>(val value: T): AppState()
class Error(val throwable: Throwable): AppState()
object Loading: AppState()
