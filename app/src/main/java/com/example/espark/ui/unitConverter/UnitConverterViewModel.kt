package com.example.espark.ui.unitConverter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UnitConverterViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is Unit Converter"
    }
    val text: LiveData<String> = _text
}