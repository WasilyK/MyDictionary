package com.wasilyk.app.mydictionary.model.entities

import com.google.gson.annotations.SerializedName

data class WordDefinition(
    @SerializedName("meanings")
    val meanings: List<Meaning>
)
