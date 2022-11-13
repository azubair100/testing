package com.zubair.test.screen

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.zubair.test.databinding.ActivityMainBinding
import com.zubair.test.util.buildErrorDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).apply { setContentView(root) }
        observeViewModelFlow()
        setUpSearchView()
        setUpRecycleView()
    }

    private fun observeViewModelFlow() {
        lifecycleScope.launchWhenStarted {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.bookState.collectLatest { state ->
                    when (state.currentState) {
                        is MainViewModel.BookState.BookLoadingState -> handleViewVisibility(
                            showProgressbar = true
                        )
                        is MainViewModel.BookState.EmptyList -> handleViewVisibility(
                            showEmptyListText = true
                        )
                        is MainViewModel.BookState.BookList -> {
                            handleViewVisibility(showList = true)
                            (binding.recyclerView.adapter as BookAdapter).submitList(
                                (state.currentState as MainViewModel.BookState.BookList).list
                            )
                        }
                        else -> handleViewVisibility()
                    }

                }
            }
        }

        lifecycleScope.launchWhenStarted {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.bookErrorState.collectLatest { state ->

                    when (state.currentState) {
                        is MainViewModel.BookState.ErrorState -> {
                            this@MainActivity.buildErrorDialog()
                        }
                        else -> {}
                    }
                }
            }
        }


    }

    private fun setUpSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!binding.searchView.query.isNullOrEmpty()) {
                    viewModel.searchBook(query!!)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun setUpRecycleView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = BookAdapter()
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
    }

    private fun handleViewVisibility(
        showList: Boolean = false,
        showEmptyListText: Boolean = false,
        showProgressbar: Boolean = false
    ) {
        with(binding) {
            recyclerView.isVisible = showList
            progressbarList.isVisible = showProgressbar
            emptyListText.isVisible = showEmptyListText
        }
    }
}