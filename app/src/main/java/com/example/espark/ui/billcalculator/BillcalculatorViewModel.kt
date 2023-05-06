package com.example.espark.ui.billcalculator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BillcalculatorViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is Bill calculator Fragment"
    }
    val text: LiveData<String> = _text
}