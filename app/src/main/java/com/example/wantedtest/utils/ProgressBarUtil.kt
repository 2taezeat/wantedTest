package com.example.wantedtest.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

object ProgressBarUtil {

    val _progressBarDialogFlag = MutableLiveData<Boolean>()
    val progressBarDialogFlag : LiveData<Boolean> = _progressBarDialogFlag
}