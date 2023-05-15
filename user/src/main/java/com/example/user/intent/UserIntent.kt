package com.example.user.intent

sealed class UserIntent {
    /**
     * 登录
     */
    data class login(val user:String,val pass:String) : UserIntent()

    /**
     * 注册
     */
    data class register(val user:String,val pass:String) : UserIntent()

}