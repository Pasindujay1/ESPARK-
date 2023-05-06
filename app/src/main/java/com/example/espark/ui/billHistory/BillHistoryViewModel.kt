package com.example.espark.ui.billHistory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BillHistoryViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is Bill History Fragment"
    }
    val text: LiveData<String> = _text
}