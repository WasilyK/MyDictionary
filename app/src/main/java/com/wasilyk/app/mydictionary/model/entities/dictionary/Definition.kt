package com.wasilyk.app.mydictionary.model.entities.dictionary

import com.google.gson.annotations.SerializedName

data class Definition(
    @SerializedName("definition")
    val definition: String
)
