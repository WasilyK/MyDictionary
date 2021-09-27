package com.wasilyk.app.mydictionary.model.entities.dictionary

import com.google.gson.annotations.SerializedName

data class DictionaryResponse(
    @SerializedName("meanings")
    val meanings: List<Meaning>
)
