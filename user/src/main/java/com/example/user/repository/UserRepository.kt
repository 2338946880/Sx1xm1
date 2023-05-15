package com.example.user.repository

import com.example.repofitutils.entity.ReporfitData
import com.example.repofitutils.entity.UserEntity
import com.example.repofitutils.model.ApiService
import com.example.repofitutils.model.RetrofitManage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository {
    val userApi:ApiService
    init {
        userApi = RetrofitManage().getApiService()
    }

    suspend fun login(username:String, password:String):ReporfitData<UserEntity>{
        return withContext(Dispatchers.IO){
            val login = userApi.login(username, password)
            login
        }
    }

    suspend fun register(username:String, password:String):ReporfitData<UserEntity>{
        return withContext(Dispatchers.IO){
            val register = userApi.register(username, password)
            register
        }
    }
}