package com.example.wantedtest.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.wantedtest.R
import com.example.wantedtest.databinding.BooksItemsBinding
import com.example.wantedtest.model.BookInfo

class BooksPreviewAdapter(booksPreviewOnclickListener: BooksPreviewOnclickListener) : ListAdapter<BookInfo, RecyclerView.ViewHolder>(BooksPreviewDiffCallback()){

    private var booksPreviewOnclickListener : BooksPreviewOnclickListener? = null


    init {
        this.booksPreviewOnclickListener = booksPreviewOnclickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return BookPreviewViewHolder( BooksItemsBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ), this.booksPreviewOnclickListener!!
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as BookPreviewViewHolder
        val bookItem = getItem(position) as BookInfo
        holder.bind(bookItem)
    }


    class BookPreviewViewHolder(private val booksItemsBinding: BooksItemsBinding, booksPreviewOnclickListener: BooksPreviewOnclickListener) : RecyclerView.ViewHolder(booksItemsBinding.root), View.OnClickListener {
        private var booksPreviewOnclickListener : BooksPreviewOnclickListener? = null
        private val maxBodyLength = 30

        init {
            booksItemsBinding.bookItemsCardview.setOnClickListener(this)
            this.booksPreviewOnclickListener = booksPreviewOnclickListener
        }

        fun bind(bookInfo: BookInfo) {
            val bookTitle = bookInfo.title
            val bookAuthors = bookInfo.authorList.joinToString(", ")
            bookTitle?.let {
                if (it.length > maxBodyLength) {
                    val subTitle = it.substring(0,maxBodyLength) + " ..."
                    booksItemsBinding.bookItemsBookName.text = subTitle
                } else {
                    booksItemsBinding.bookItemsBookName.text = it
                }
            }

            bookAuthors.let {
                if (it.length > maxBodyLength) {
                    val subAuthors = it.substring(0,maxBodyLength) + " ..."
                    booksItemsBinding.bookItemsBookAuthor.text = subAuthors
                } else {
                    booksItemsBinding.bookItemsBookAuthor.text = it
                }
            }

            with(booksItemsBinding) {
                bookItemsBookDate.text = bookInfo.publishedDate
            }

            bookInfo.thumbnail?.let {
                Glide.with(booksItemsBinding.root.context)
                    .load(bookInfo.thumbnail)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.post_loading)
                    .into(booksItemsBinding.bookItemsImage)
            }
        }

        override fun onClick(view: View?) {
            this.booksPreviewOnclickListener?.onClickContainerView(view = view!!, position = adapterPosition)
        }
    }

}