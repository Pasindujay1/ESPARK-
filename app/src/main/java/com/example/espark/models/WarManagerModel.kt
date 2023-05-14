package com.example.espark.models

import java.util.concurrent.TimeUnit

data class WarManagerModel (
    var wmId:String? = null,
    var ProName:String? = null,
    var BraName:String? = null,
    var PurchDate:String? = null,
    var WarPeriod:String? = null,
    var Price:String? = null,
    var PurchLocation:String? = null,
    var PhoneNo:String? = null,
    var Email:String? = null,
    var Other:String? = null,
    val itemImg:String?= null,


    )
//{
//    val expiryDate: Long
//        get() = PurchDate + TimeUnit.DAYS.toMillis(WarPeriod.toLong())
//
//    val isActive: Boolean
//        get() = expiryDate > System.currentTimeMillis()
//}