package com.zubair.test.network

import com.zubair.test.model.BookResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface BookApi {
    @GET("search.json?")
    suspend fun getBooksByTitle(
        @Query("title") query: String? = null
    ): BookResponse
}