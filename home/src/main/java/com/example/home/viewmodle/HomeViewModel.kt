package com.example.home.viewmodle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.home.homeintent.HomeSimpleVideoIntent
import com.example.home.homestate.HomeSimpleVideoState
import com.example.home.repository.HomeVideoReposory
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class HomeViewModel:ViewModel() {
    var homeVideoReposory=HomeVideoReposory()
    val channel=Channel<HomeSimpleVideoIntent>(Channel.UNLIMITED)
    private val home_state = MutableStateFlow<HomeSimpleVideoState>(HomeSimpleVideoState.Init)
    val state:StateFlow<HomeSimpleVideoState>
    get() = home_state

    init {
        handlerIntent()
    }

    private fun handlerIntent() {
        viewModelScope.launch {
            channel.consumeAsFlow().collect {
                when(it){
                    //is HomeSimpleVideoIntent.getSimpleType -> getSimpleType()
                    is HomeSimpleVideoIntent.getSimpleVideoByChannelId -> getSimpleVideos(it.channelId,it.page,it.pagesize)
                    is HomeSimpleVideoIntent.getSimpleType -> getSimpleType()
                    else -> {}
                }
            }
        }
    }

    private fun getSimpleType() {
        viewModelScope.launch {
            val data = homeVideoReposory.getSimpleType()
            if (data.code ==0){
                home_state.value = HomeSimpleVideoState.SimpleType(data.data)
            }else{
                home_state.value = HomeSimpleVideoState.Failed
            }
        }
    }

    private fun getSimpleVideos(channelId:String, page:Int, pagesize:Int){
        viewModelScope.launch {
            val data = homeVideoReposory.getSimpleVideoByChannelId(channelId, page, pagesize)
            if (data.code == 0){
                home_state.value = HomeSimpleVideoState.SimpleVideos(data.data)
            }else{
                home_state.value = HomeSimpleVideoState.Failed
            }
        }
    }
}