package com.example.espark.ui.warrantyManager

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WarrantyManagerViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is warranty Fragment"
    }
    val text: LiveData<String> = _text
}