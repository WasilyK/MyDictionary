package com.wasilyk.app.mydictionary.model.entities

import com.google.gson.annotations.SerializedName

data class Meaning(
    @SerializedName("definitions")
    val definitions: List<Definition>
)
