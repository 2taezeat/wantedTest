package com.example.wantedtest.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wantedtest.model.BookInfo
import com.example.wantedtest.model.RetrofitClient
import com.example.wantedtest.utils.ProgressBarUtil
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainViewModel : ViewModel(){

    var _searchWord = MutableLiveData<String>()
    var searchWord: LiveData<String> = _searchWord

    private val _totalResult = MutableLiveData<String>()
    val totalResult: LiveData<String> = _totalResult

    private val _bookSearchResult = MutableLiveData<ArrayList<BookInfo>>()
    val bookSearchResult: LiveData<ArrayList<BookInfo>> = _bookSearchResult

    private val _isApiResultFail = MutableLiveData<Boolean>()
    val isApiResultFail: LiveData<Boolean> = _isApiResultFail

    private val _startIndex = MutableLiveData<Int>()


    init {
        _bookSearchResult.value = arrayListOf(
        )
        _searchWord.value = ""
        _totalResult.value = "0"
        _startIndex.value = 0
        _isApiResultFail.value = false

    }

    fun getBookInfo(searchBookWord: String, isScroll : Boolean) {
        val service = RetrofitClient.searchUserService

        CoroutineScope(Dispatchers.IO).launch {
            ProgressBarUtil._progressBarDialogFlag.postValue(true)
            val response = service.bookInfoService(searchBookWord,10, _startIndex.value!!)

            withContext(Dispatchers.Main) {
                if (response.code() == 200) {
                    _isApiResultFail.postValue(false)

                    val result = response.body()
                    if (!isScroll) {
                        val totalItems = result!!.get("totalItems")
                        _totalResult.postValue(totalItems.toString())
                    }
                    val getItems = result!!.getAsJsonArray("items")

                    if (getItems != null) {
                        for (getItem in getItems) {
                            val jsonObject = getItem as JsonObject
                            val volumeInfo = jsonObject.get("volumeInfo") as JsonObject
                            val title = volumeInfo.get("title")?.toString()?.drop(1)?.dropLast(1)
                            val subtitle = volumeInfo.get("subtitle")?.toString()?.drop(1)?.dropLast(1)
                            val getAuthors = volumeInfo.get("authors")
                            val authorList = arrayListOf<String?>()

                            getAuthors?.let{
                                val authors = getAuthors as JsonArray
                                for (a in authors) {
                                    authorList.add(a.toString().drop(1).dropLast(1))
                                }
                            }
                            val publishedDate = volumeInfo.get("publishedDate")?.toString()?.drop(1)?.dropLast(1)
                            val description = volumeInfo.get("description")?.toString()?.drop(1)?.dropLast(1)
                            val imageLinks = volumeInfo.get("imageLinks")?.let{it as JsonObject}
                            val thumbnail = imageLinks?.get("smallThumbnail")?.toString()?.drop(1)?.dropLast(1)
                            _bookSearchResult.value!!.add(BookInfo(title = title, publishedDate = publishedDate, thumbnail = thumbnail, authorList = authorList, subTitle = subtitle, description = description))
                        }
                    } else {
                        _isApiResultFail.postValue(true)
                        _totalResult.postValue("0")
                    }

                } else {
                    _isApiResultFail.postValue(true)
                    _totalResult.postValue("0")
                }
            }
            ProgressBarUtil._progressBarDialogFlag.postValue(false)
        }
    }

    fun setStartIndexZero(){
        _startIndex.value = 0
    }


    fun clearBookResult(){
        _bookSearchResult.value = arrayListOf(
        )
    }

    fun startIndexPlus(){
        _startIndex.value = _startIndex.value!! + 10
    }



}