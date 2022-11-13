package com.zubair.test

import assertk.assertThat
import com.zubair.test.model.Book
import com.zubair.test.model.BookResponse
import com.zubair.test.network.BookApi
import com.zubair.test.network.BookRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test

class MainViewModelTest {

    private val dispatcher = TestCoroutineDispatcher()

    private val testCoroutineScope = createTestCoroutineScope(
        TestCoroutineDispatcher() + TestCoroutineExceptionHandler() + dispatcher)


    @MockK
    lateinit var bookApi: BookApi

    @MockK
    lateinit var bookRepository: BookRepository

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        MockKAnnotations.init(this, relaxUnitFun = true)
        bookApi = mockk(relaxed = true)
        bookRepository = BookRepository(bookApi)
    }

    @After
    fun cleanUp() {
        testCoroutineScope.cleanupTestCoroutines()
        dispatcher.cleanupTestCoroutines()
    }

    private fun returnListOfBooks() =
        BookResponse(
            result = listOf(
                Book("test book name", listOf("test author name"), "1999")
            )
        )

    private fun returnEmptyListOfBooks() = BookResponse(result = emptyList())

    @Test
    fun successfulListTest() {
        val response = returnListOfBooks()
        val query = "test"

        coEvery { bookApi.getBooksByTitle(query) } returns response

        testCoroutineScope.launch {
            bookRepository.getCountries(query).collectLatest {
                assertThat(it.result?.size?.equals(response.result?.size))
            }
        }
    }

    @Test
    fun successfulEmptyListTest() {
        val response = returnEmptyListOfBooks()
        val query = "test"

        coEvery { bookApi.getBooksByTitle(query) } returns response

        testCoroutineScope.launch {
            bookRepository.getCountries(query).collectLatest {
                assertThat(it.result?.size?.equals(response.result?.size))
            }
        }
    }



}