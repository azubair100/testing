package com.zubair.test.screen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.zubair.test.databinding.ListItemBookBinding
import com.zubair.test.model.Book

class BookAdapter : RecyclerView.Adapter<BookAdapter.BookItemViewHolder>() {
    private val NOT_APPLICABLE = "N/A"

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Book>() {
        override fun areItemsTheSame(oldItem: Book, newItem: Book) =
            oldItem.title == newItem.title

        override fun areContentsTheSame(oldItem: Book, newItem: Book) =
            oldItem.title == newItem.title
    }

    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)

    inner class BookItemViewHolder(private val binding: ListItemBookBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Book) {
            with(binding) {
                bookTitle.text = item.title ?: NOT_APPLICABLE
                authorName.text = item.authorName?.joinToString { it }
                publishedYear.text = item.firstPublishedYear ?: NOT_APPLICABLE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookItemViewHolder =
        BookItemViewHolder(
            ListItemBookBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )


    override fun onBindViewHolder(holder: BookItemViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount() = differ.currentList.size

    fun submitList(list: List<Book>) {
        differ.submitList(list)
    }
}