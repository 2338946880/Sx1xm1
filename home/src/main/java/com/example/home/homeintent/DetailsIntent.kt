package com.example.home.homeintent

sealed class DetailsIntent {
    /**
     * 获取推荐的Simple视频信息
     */
    data class getRecommendSimpleVideo(val page:Int,val pagesize:Int):DetailsIntent()
    /**
     *     获取评论信息
     */
    data class getCommentByUserId(val datatype:Int,val itemid:String):DetailsIntent()


    /**
     *    关注
     */

    data class focuse(val focuseUserid:Int,val focusedUserid:Int):DetailsIntent()

    /**
     * 取消关注
     */
    data class unFocuse(val focuseUserid:Int,val focusedUserid:Int):DetailsIntent()
}