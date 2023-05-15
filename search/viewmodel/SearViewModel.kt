package com.example.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.search.repository.SearchSimpleRepo
import com.example.search.state.RecommendUIState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch


class SearViewModel :ViewModel() {

    var searchSimpleRepo=SearchSimpleRepo()
    val channel=Channel<RecommendInter>(Channel.UNLIMITED)
    private val _uistate=MutableStateFlow<RecommendUIState>(RecommendUIState.Loading)

    val uiState:MutableStateFlow<RecommendUIState>
    get() = _uistate

    init {
        handlerinitent()
    }

    private fun handlerinitent() {
        viewModelScope.launch {
            channel.consumeAsFlow().collect {
                when(it){
                    is RecommendInter.getRecommend -> getRecommend(it.page,it.pagesize)
                }
            }
        }
    }

    private fun getRecommend(page: Int,pagesize: Int) {
       viewModelScope.launch {
           val recommendMutilVideo = searchSimpleRepo.getRecommendMutilVideo(page, pagesize)

           if (recommendMutilVideo.code==0){
               _uistate.value=RecommendUIState.Success(recommendMutilVideo.data)
           }else{
               _uistate.value=RecommendUIState.Fail(recommendMutilVideo.msg)
           }
       }
    }
    sealed class RecommendInter{
        data class getRecommend(var page:Int,var pagesize:Int):RecommendInter()
    }
}
