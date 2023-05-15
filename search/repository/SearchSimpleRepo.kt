package com.example.search.repository

import com.example.repofitutils.entity.IUnionVideoMutilEntity
import com.example.repofitutils.entity.ReporfitData
import com.example.repofitutils.model.ApiService
import com.example.repofitutils.model.RetrofitManage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SearchSimpleRepo {
    val apiserver: ApiService
    init {
        apiserver = RetrofitManage.getApiService()
    }

    suspend fun getRecommendMutilVideo(page:Int,pagesize:Int):ReporfitData<List<IUnionVideoMutilEntity>>{
        return withContext(Dispatchers.IO){
            val recommendMutilVideo = apiserver.getRecommendMutilVideo(page, pagesize)
            recommendMutilVideo
        }
    }
}