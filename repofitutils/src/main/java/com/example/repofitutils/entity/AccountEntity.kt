package com.example.repofitutils.entity

data class AccountEntity(
    val accountamount:Double,
    val cards:List<BankCardEntity>,
    val coinamount:Double,
    val createtime:String,
    val id:Int,
    val userid:Int,
    val wxaccount:String,
    val zfbaccount:String
)