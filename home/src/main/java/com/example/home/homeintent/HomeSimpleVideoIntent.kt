package com.example.home.homeintent

sealed class HomeSimpleVideoIntent{
    /**
     * 根据ChannelId获取对应的适配信息
     */
    data class getSimpleVideoByChannelId(val channelId:String,val page:Int,val pagesize:Int):HomeSimpleVideoIntent()
    /**
     * 获取Simple视频类型信息
     */
    object getSimpleType:HomeSimpleVideoIntent()
}
