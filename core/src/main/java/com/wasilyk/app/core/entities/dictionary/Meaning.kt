package com.wasilyk.app.core.entities.dictionary

import com.google.gson.annotations.SerializedName

data class Meaning(
    @SerializedName("definitions")
    val definitions: List<Definition>
)
