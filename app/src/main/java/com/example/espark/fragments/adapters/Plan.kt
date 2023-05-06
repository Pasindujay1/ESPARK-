package com.example.espark.fragments.adapters

data class Plan(
    var planName: String? = null,
    val amount: String? = null,
    val additems: MutableList<AddItem>,

    )
