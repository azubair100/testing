package com.zubair.test.network

import com.zubair.test.model.BookResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class BookRepository @Inject constructor(private val api: BookApi) {
    fun getCountries(query: String): Flow<BookResponse> = flow {
        emit(api.getBooksByTitle(query))
    }.flowOn(Dispatchers.IO)
}