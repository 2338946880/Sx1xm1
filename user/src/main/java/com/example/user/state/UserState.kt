package com.example.user.state

import com.example.repofitutils.entity.ReporfitData
import com.example.repofitutils.entity.UserEntity

sealed class UserState {
    /**
     * 登录成功
     */
    data class login(val user: UserEntity?):UserState()

    /**
     * 注册成功
     */
    data class register(val user: UserEntity?):UserState()

    /**
     * 失败
     */
    data class Failed(val msg:String) :UserState()

    /**
     * 初始状态
     */
    object Init:UserState()
}