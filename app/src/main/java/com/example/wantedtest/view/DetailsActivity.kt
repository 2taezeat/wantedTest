package com.example.wantedtest.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.wantedtest.R
import com.example.wantedtest.databinding.ActivityDetailsBinding
import com.example.wantedtest.model.BookInfo


class DetailsActivity : AppCompatActivity() {

    private var _binding: ActivityDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.lifecycleOwner = this

        val passedIntent = intent
        val intentBookInfo = passedIntent.getParcelableExtra<BookInfo>("bookPositionValue")

        binding.bookDetailBackButton.setOnClickListener {
            onBackPressed()
        }

        intentBookInfo!!.thumbnail?.let {
            Glide.with(this)
                .load(intentBookInfo.thumbnail)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.post_loading)
                .into(binding.bookDetailBookImageView)
        }


        binding.bookDetailTitleTV.text = intentBookInfo.title
        binding.bookDetailSubtitleTV.text = intentBookInfo.subTitle
        binding.bookDetailAuthorsTV.text = intentBookInfo.authorList.joinToString(", ")
        binding.bookDetailPublishedDateTV.text = intentBookInfo.publishedDate
        binding.bookDetailDescriptionTV.text = intentBookInfo.description

    }
}