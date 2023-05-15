package com.example.home.homestate

import com.example.repofitutils.entity.CommentEntity
import com.example.repofitutils.entity.FocuseEntity
import com.example.repofitutils.entity.IUnionVideoSimpleEntity
import com.example.repofitutils.entity.ReporfitData

sealed class DetailsState {

    /**
     * 关注
     */
    data class focuse(val data: ReporfitData<FocuseEntity>):DetailsState()
    /**
     * 取消关注
     */
    data class unFocuse(val data: ReporfitData<Boolean>):DetailsState()

    /**
     * 获取评论
     */
    data class getCommentByUserId(val data: List<CommentEntity>):DetailsState()

    /**
     * 成功获取推荐视频信息
     */
    data class RecommendSimpleVideos(val list:List<IUnionVideoSimpleEntity>):DetailsState()
    /**
     * 初始状态
     */
    object Init:DetailsState()
    /**
     * 获取推荐视频信息失败
     */
    object RecommendFailed:DetailsState()




}