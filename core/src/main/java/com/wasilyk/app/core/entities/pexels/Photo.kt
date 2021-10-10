package com.wasilyk.app.core.entities.pexels

import com.google.gson.annotations.SerializedName

data class Photo(
    @SerializedName("src")
    val src: Src
)