package com.example.videohall.state

import com.example.repofitutils.entity.IUnionVideoMutilEntity
import com.example.repofitutils.entity.VideoTypeEntity

sealed class MutilUIState {
    //获取MutilType视频类型信息
    data class MutilType(var list:List<VideoTypeEntity>?):MutilUIState()
    //根据上级id获取Mutil视频类型信息
    data class MutilTypeByPid(var list: List<VideoTypeEntity>?):MutilUIState()
    ////根据视频分类获取对应的视频信息
    data class MutilVideoByTypeId(var list: List<IUnionVideoMutilEntity>?):MutilUIState()

    //获取推荐视频信息
    data class RecommendMutilVideo(var list: List<IUnionVideoMutilEntity>?):MutilUIState()

    data class Eorror(var ex:Throwable?):MutilUIState()
    data class Fail(var msg:String?):MutilUIState()
    object Loading:MutilUIState()
}