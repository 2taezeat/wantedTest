package com.example.wantedtest.view

import androidx.recyclerview.widget.DiffUtil
import com.example.wantedtest.model.BookInfo

class BooksPreviewDiffCallback : DiffUtil.ItemCallback<BookInfo>() {
    override fun areItemsTheSame(oldItem: BookInfo, newItem: BookInfo): Boolean {
        return oldItem.hashCode() == newItem.hashCode()
    }

    override fun areContentsTheSame(oldItem: BookInfo, newItem: BookInfo): Boolean {
        return oldItem == newItem
    }


}