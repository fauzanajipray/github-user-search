package com.dicoding.faprayyy.githubuser.utils

fun convertNullToString(string: String): String{
    if (string == "null"){
        return "-"
    }
    return string
}