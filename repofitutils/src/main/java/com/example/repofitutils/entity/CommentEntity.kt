package com.example.repofitutils.entity

data class CommentEntity(
    val agreenum:Int,
    val content:String,
    val createtime:String,
    val datatype:Int,
    val id:Int,
    val itemid:String,
    val nickname:String,
    val replyList:List<ReplyEntity>,
    val replytotal:Int,
    val userid:Int,
    val userlogo:String
)
