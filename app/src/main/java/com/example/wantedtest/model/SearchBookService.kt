package com.example.wantedtest.model

import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchBookService {
    @GET("volumes")
    suspend fun bookInfoService(
        @Query("q") searchBookWord : String,
        @Query("maxResults") maxResults : Int,
        @Query("startIndex") startIndex : Int
        ) : Response<JsonObject>
}