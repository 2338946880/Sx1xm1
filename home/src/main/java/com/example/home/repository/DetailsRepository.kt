package com.example.home.repository

import com.example.repofitutils.entity.*
import com.example.repofitutils.model.ApiService
import com.example.repofitutils.model.RetrofitManage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.http.Query

class DetailsRepository {
    val detailsApi:ApiService
    init {
        detailsApi = RetrofitManage().getApiService()
    }

    /**
     * 获取推荐的Simple视频信息
     */
    suspend fun getRecommendSimpleVideo(page:Int,pagesize:Int): ReporfitData<List<IUnionVideoSimpleEntity>> {
        return withContext(Dispatchers.IO){
            val recommendSimpleVideo = detailsApi.getRecommendSimpleVideo(page, pagesize)
            recommendSimpleVideo
        }
    }

    /**
     *     获取评论信息
     */
    suspend fun getCommentByUserId(datatype:Int,itemid:String):ReporfitData<List<CommentEntity>>{
        return withContext(Dispatchers.IO){
            val commentByUserId = detailsApi.getCommentByUserId(datatype, itemid)
            commentByUserId
        }
    }

    suspend fun focuse(focuseUserid:Int,focusedUserid:Int):ReporfitData<FocuseEntity>{
        return withContext(Dispatchers.IO){
            val focuse = detailsApi.focuse(focuseUserid, focusedUserid)
            focuse
        }
    }

    suspend fun unFocuse(focuseUserid:Int,focusedUserid:Int):ReporfitData<Boolean>{
        return withContext(Dispatchers.IO){
            val unFocuse = detailsApi.unFocuse(focuseUserid, focusedUserid)
            unFocuse
        }
    }


}