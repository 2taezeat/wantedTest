package com.example.wantedtest.view

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wantedtest.databinding.ActivityMainBinding
import com.example.wantedtest.utils.ProgressBarUtil
import com.example.wantedtest.viewmodel.MainViewModel

class MainActivity : AppCompatActivity(), BooksPreviewOnclickListener {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    lateinit var mainViewModel: MainViewModel
    private val bookPreviewAdapter by lazy {
        BooksPreviewAdapter(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding.lifecycleOwner = this
        binding.mainVM = mainViewModel
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Please wait ...")
        progressDialog.setCancelable(false)
        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Horizontal)

        mainViewModel.isApiResultFail.observe(this, {
            if(it){
                Toast.makeText(this, "there is no such book, or connection Fail", Toast.LENGTH_SHORT).show()
            }
        })

        ProgressBarUtil.progressBarDialogFlag.observe(this, { progressBarDialogFlag ->
            if (progressBarDialogFlag) {
                progressDialog.show()
            } else {
                progressDialog.dismiss()
                bookPreviewAdapter.submitList(mainViewModel.bookSearchResult.value!!.toMutableList())

            }
        })

        binding.searchImageView.setOnClickListener {
            mainViewModel.clearBookResult()
            mainViewModel.setStartIndexZero()
            mainViewModel.getBookInfo(mainViewModel.searchWord.value!!, false)
        }


        binding.mainRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
            adapter = bookPreviewAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener(){
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if(!canScrollVertically(1)){
                        mainViewModel.startIndexPlus()
                        mainViewModel.getBookInfo(mainViewModel.searchWord.value!!, true)
                    }
                }
            })
        }
    }


    override fun onClickContainerView(view: View, position: Int) {
        val intent = Intent(this, DetailsActivity::class.java)
        val bookPositionValue = mainViewModel.bookSearchResult.value!![position]
        intent.putExtra("bookPositionValue",bookPositionValue)
        ContextCompat.startActivity(this, intent, null)
    }
}