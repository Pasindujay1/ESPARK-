package com.example.espark.models

import java.time.LocalDate
import java.time.Month
import java.time.Year

//class to store bill data
data class BillData(
    val billId : String? = null,
    val billYear: String? = null,
    val billMonth: String? = null,
    val startDate: String? = null,
    val endDate: String? = null,
    val consumedUnits: String? = null,
    val totAmount: String? = null
)
