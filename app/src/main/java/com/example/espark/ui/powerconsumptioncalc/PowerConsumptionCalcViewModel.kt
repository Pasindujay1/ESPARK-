package com.example.espark.ui.powerconsumptioncalc

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PowerConsumptionCalcViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is power consumption calculator Fragment"
    }
    val text: LiveData<String> = _text
}