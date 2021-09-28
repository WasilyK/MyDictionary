package com.wasilyk.app.main.retrofit.pexel

import com.wasilyk.app.core.entities.pexels.PexelResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface PexelsApi {
    @GET("v1/search")
    fun getPexelsResponse(
        @Header("Authorization") autorization: String,
        @Query("query") word: String)
    : Call<PexelResponse>
}