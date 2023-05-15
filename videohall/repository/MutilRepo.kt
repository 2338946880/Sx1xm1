package com.example.videohall.repository

import com.example.repofitutils.entity.IUnionVideoMutilEntity
import com.example.repofitutils.entity.ReporfitData
import com.example.repofitutils.entity.VideoTypeEntity
import com.example.repofitutils.model.RetrofitManage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MutilRepo {

    var apiserver=RetrofitManage.getApiService()

    //获取MutilType视频类型信息
    suspend fun getMutilType():ReporfitData<MutableList<VideoTypeEntity>>{
        return withContext(Dispatchers.IO){
            val mutilType = apiserver.getMutilType()
            mutilType
        }
    }
    //根据上级id获取Mutil视频类型信息
    suspend fun getMutilTypeByPid(pid:Int):ReporfitData<MutableList<VideoTypeEntity>>{
        return withContext(Dispatchers.IO){
            val mutilTypeByPid = apiserver.getMutilTypeByPid(pid)
            mutilTypeByPid
        }
    }
    ////根据视频分类获取对应的视频信息
    suspend fun getMutilVideoByTypeId(page:Int,pagesize:Int,typeid:Int):ReporfitData<List<IUnionVideoMutilEntity>>{
        return withContext(Dispatchers.IO){
            val mutilVideoByTypeId = apiserver.getMutilVideoByTypeId(page, pagesize, typeid)
            mutilVideoByTypeId
        }
    }
    //猜你喜欢/getRecommendMutilVideo
    suspend fun getRecommendMutilVideo(page: Int,pagesize: Int):ReporfitData<List<IUnionVideoMutilEntity>>{
        return withContext(Dispatchers.IO){
            val recommendMutilVideo = apiserver.getRecommendMutilVideo(page, pagesize)
            recommendMutilVideo
        }
    }

}