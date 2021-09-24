package com.wasilyk.app.mydictionary.model.entities.pexels

import com.google.gson.annotations.SerializedName

data class PexelResponse(
    @SerializedName("photos")
    val photos: List<Photo>
)