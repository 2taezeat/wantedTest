package com.example.wantedtest.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BookInfo(
    val title : String?,
    val thumbnail : String?,
    val authorList : ArrayList<String?>,
    val publishedDate : String?,

    val subTitle : String?,
    val description : String?
): Parcelable
