package com.zubair.test.model

import com.google.gson.annotations.SerializedName

data class Book(
    @SerializedName("title") val title: String? = null,
    @SerializedName("author_name") val authorName: List<String>? = null,
    @SerializedName("first_publish_year") val firstPublishedYear: String? = null
)

data class BookResponse(
    @SerializedName("docs") val result: List<Book>? = null
)