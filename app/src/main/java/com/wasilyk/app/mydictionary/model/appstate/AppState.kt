package com.wasilyk.app.mydictionary.model.appstate

sealed class AppState

class Success(val wordDefinition: String): AppState()
class Error(val throwable: Throwable): AppState()
object Loading: AppState()
