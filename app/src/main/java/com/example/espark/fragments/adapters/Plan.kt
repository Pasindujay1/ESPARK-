package com.example.espark.fragments.adapters

data class Plan(//Plans data class
    var planName: String? = null,
    val amount: String? = null,
    val additems: MutableList<AddItem>,

    )
