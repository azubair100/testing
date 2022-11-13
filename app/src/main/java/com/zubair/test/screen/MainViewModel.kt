package com.zubair.test.screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zubair.test.model.Book
import com.zubair.test.network.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: BookRepository) : ViewModel() {
    private val _bookState = MutableStateFlow(ScreenState())
    val bookState = _bookState.asStateFlow()

    fun searchBook(query: String) {
        viewModelScope.launch {
            _bookState.emit(ScreenState(BookState.BookLoadingState))
            try {
                repository.getCountries(query).collectLatest { response ->
                    val list = response.result
                    if (list.isNullOrEmpty()) {
                        _bookState.emit(ScreenState(BookState.EmptyList))
                    } else {
                        _bookState.emit(ScreenState(BookState.BookList(list)))
                    }
                }

            } catch (error: Throwable) {
                _bookState.emit(ScreenState(BookState.ErrorState))
                Log.e("error", "Error occurred ${error.localizedMessage}")
            }
        }
    }

    data class ScreenState(var currentState: BookState? = null)

    sealed class BookState {
        object BookLoadingState : BookState()
        object ErrorState : BookState()
        object EmptyList : BookState()
        class BookList(val list: List<Book>) : BookState()
    }

}