package com.dicoding.faprayyy.githubuser.view.userdetail

import android.util.Log
import java.math.BigDecimal
import java.math.RoundingMode

fun convertIntValue(value: Int): String{
    val convertValue = value.toDouble()

    if(value >= 1000000){
        val decimalVal = convertValue/1000000
        val decimal = BigDecimal(decimalVal).setScale(2, RoundingMode.HALF_EVEN)
        return "${decimal}M"
    }
    else if(value>=1000)
    {
        val decimalVal = convertValue/1000
        val decimal = BigDecimal(decimalVal).setScale(2, RoundingMode.HALF_EVEN)
        return "${decimal}K"
    }
    return value.toString()
}