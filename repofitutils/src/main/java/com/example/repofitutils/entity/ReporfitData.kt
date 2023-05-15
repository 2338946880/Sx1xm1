package com.example.repofitutils.entity

data class ReporfitData<T>(
    val code:Int,
    val data:T,
    val msg:String
)