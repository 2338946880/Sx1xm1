package com.example.home.viewmodle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.home.homeintent.DetailsIntent
import com.example.home.homeintent.HomeSimpleVideoIntent
import com.example.home.homestate.DetailsState
import com.example.home.homestate.HomeSimpleVideoState
import com.example.home.repository.DetailsRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class DetailsViewModel:ViewModel() {
    val detailsRepository = DetailsRepository()
    val channel = Channel<DetailsIntent>(Channel.UNLIMITED)
    private val detail_state = MutableStateFlow<DetailsState>(DetailsState.Init)
    val state:StateFlow<DetailsState>
        get() = detail_state

    init {
        handlerIntent()
    }

    private fun handlerIntent() {
        viewModelScope.launch {
            channel.consumeAsFlow().collect {
                when(it){
                    //is HomeSimpleVideoIntent.getSimpleType -> getSimpleType()
                    is DetailsIntent.getCommentByUserId -> getCommentByUserId(it.datatype,it.itemid)
                    is DetailsIntent.getRecommendSimpleVideo -> getRecommendSimpleVideo(it.page,it.pagesize)
                    is DetailsIntent.focuse-> getFocuse(it.focuseUserid,it.focusedUserid)
                    is DetailsIntent.unFocuse-> getUnFocuse(it.focuseUserid,it.focusedUserid)
                }
            }
        }
    }

    private fun getFocuse(focuseUserid: Int, focusedUserid: Int) {
        viewModelScope.launch {
            val focuse = detailsRepository.focuse(focuseUserid, focusedUserid)
            if (focuse.code==0){
                detail_state.value = DetailsState.focuse(focuse)
            }else{
                detail_state.value =DetailsState.RecommendFailed
            }
        }
    }

    private fun getUnFocuse(focuseUserid: Int, focusedUserid: Int) {
        viewModelScope.launch {
            val focuse = detailsRepository.unFocuse(focuseUserid, focusedUserid)
            if (focuse.code==0){
                detail_state.value = DetailsState.unFocuse(focuse)
            }else{
                detail_state.value =DetailsState.RecommendFailed
            }
        }
    }

    fun getCommentByUserId(datatype:Int,itemid:String){
        viewModelScope.launch{
            val commentByUserId = detailsRepository.getCommentByUserId(datatype, itemid)
            if (commentByUserId.code == 0){
                detail_state.value = DetailsState.getCommentByUserId(commentByUserId.data)
            }else{
                detail_state.value = DetailsState.RecommendFailed
            }
        }
    }

    fun getRecommendSimpleVideo(page:Int,pagesize:Int){
        viewModelScope.launch {
            val data = detailsRepository.getRecommendSimpleVideo(page, pagesize)
            if (data.code == 0){
                detail_state.value = DetailsState.RecommendSimpleVideos(data.data)
            }else{
                detail_state.value = DetailsState.RecommendFailed
            }
        }

    }
}