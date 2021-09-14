package com.wasilyk.app.mydictionary.view

interface MainView {
    fun showWordDefinition(definition: String)
    fun showToast(message: String)
    fun getWord(): String
    fun showErrorText()
}