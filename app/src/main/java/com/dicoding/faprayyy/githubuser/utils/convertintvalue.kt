package com.dicoding.faprayyy.githubuser.view.userdetail

import android.util.Log
import java.math.BigDecimal
import java.math.RoundingMode

fun convertintvalue(value: Int): String{
    val convertValue = value

    if(value >= 1000000){
        val cumacek = BigDecimal(35710000.toDouble()/1000000).setScale(2, RoundingMode.HALF_EVEN)
        Log.d(DetailUserFragment.TAG, cumacek.toString())
        val decimalVal = convertValue.toDouble()/1000000
        val decimal = BigDecimal(decimalVal).setScale(2, RoundingMode.HALF_EVEN)
        return "${decimal}M"
    }
    else if(value>=1000)
    {
        val decimalVal = convertValue.toDouble()/1000
        val decimal = BigDecimal(decimalVal).setScale(2, RoundingMode.HALF_EVEN)
        return "${decimal}K"
    }
    return convertValue.toString()
}