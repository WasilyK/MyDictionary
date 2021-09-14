package com.wasilyk.app.mydictionary.model.entities

import com.google.gson.annotations.SerializedName

data class Definition(
    @SerializedName("definition")
    val definition: String
)
