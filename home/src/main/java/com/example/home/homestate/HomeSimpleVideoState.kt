package com.example.home.homestate

import com.example.repofitutils.entity.IUnionVideoSimpleEntity
import com.example.repofitutils.entity.VideoTypeEntity

sealed class HomeSimpleVideoState {
    /**
     * 成功获取视频信息
     */
    data class SimpleVideos(val list: List<IUnionVideoSimpleEntity>?):HomeSimpleVideoState()

    /**
     * 获取视频类型
     */
    data class SimpleType(val list: List<VideoTypeEntity>?):HomeSimpleVideoState()

    data class Error(val ex:Throwable?) : HomeSimpleVideoState()
    /**
     * 获取视频信息失败
     */
    object Failed :HomeSimpleVideoState()


    /**
     * 初始状态
     */
    object Init:HomeSimpleVideoState()
}