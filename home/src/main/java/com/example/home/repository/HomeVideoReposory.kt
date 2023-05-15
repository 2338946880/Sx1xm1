package com.example.home.repository

import com.example.repofitutils.entity.IUnionVideoSimpleEntity
import com.example.repofitutils.entity.ReporfitData
import com.example.repofitutils.entity.VideoTypeEntity
import com.example.repofitutils.model.ApiService
import com.example.repofitutils.model.RetrofitManage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HomeVideoReposory {
    val homeApi:ApiService
    init {
        homeApi = RetrofitManage().getApiService()
    }

    /**
     * 根据ChannelId获取对应的适配信息
     */
    suspend fun getSimpleVideoByChannelId(channelId:String, page:Int, pagesize:Int):ReporfitData<List<IUnionVideoSimpleEntity>>{
        return withContext(Dispatchers.IO){
            val simpleVideoByChannelId = homeApi.getSimpleVideoByChannelId(channelId, page, pagesize)
            simpleVideoByChannelId
        }
    }

    /**
     * 获取Simple视频类型信息
     */
    suspend fun getSimpleType():ReporfitData<List<VideoTypeEntity>>{
        return withContext(Dispatchers.IO){
            val simpleType = homeApi.getSimpleType()
            simpleType
        }
    }



}