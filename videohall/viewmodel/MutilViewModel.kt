package com.example

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.videohall.repository.MutilRepo
import com.example.videohall.state.MutilUIState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

sealed class MutilIntent{
    object getMutilType:MutilIntent()

    data class getMutilTypeByPid(var pid:Int):MutilIntent()

    data class getMutilVideoByTypeId(var page:Int,var pagesize:Int,var typeid:Int):MutilIntent()

    data class getRecommendMutilVideo(var page: Int,var pagesize: Int):MutilIntent()

}
class MutilViewModel :ViewModel(){
    var mutilRepo=MutilRepo()


    var channel=Channel<MutilIntent>(Channel.UNLIMITED)
    private var _uistate=MutableStateFlow<MutilUIState>(MutilUIState.Loading)
    val uiState:MutableStateFlow<MutilUIState>
    get() = _uistate


    init {
        handlerinitent()

    }

    private fun handlerinitent() {
        viewModelScope.launch {
            channel.consumeAsFlow().collect {
                when(it){
                    //根据获取列表意图，去获取列表
                    is  MutilIntent.getMutilType -> getMutilType()

                    is MutilIntent.getMutilTypeByPid -> getMutilTypeByPid(it.pid)

                    is MutilIntent.getMutilVideoByTypeId -> getMutilVideoByTypeId(it.page,it.pagesize,it.typeid)

                    is MutilIntent.getRecommendMutilVideo -> getRecommendMutilVideo(it.page,it.pagesize)

                    else -> {}
                }
            }
        }

    }

    //获取推荐视频信息
    private fun getRecommendMutilVideo(page: Int, pagesize: Int) {
        viewModelScope.launch {
            val recommendMutilVideo = mutilRepo.getRecommendMutilVideo(page, pagesize)
            //是否请求成功
            if (recommendMutilVideo.code==0){
                _uistate.value=MutilUIState.RecommendMutilVideo(recommendMutilVideo?.data)
            }else{
                _uistate.value=MutilUIState.Fail(recommendMutilVideo?.msg)
            }
        }

    }

    ////根据视频分类获取对应的视频信息
    private fun getMutilVideoByTypeId(page: Int, pagesize: Int, typeid: Int) {

        viewModelScope.launch {
            val mutilVideoByTypeId = mutilRepo.getMutilVideoByTypeId(page, pagesize, typeid)
            //是否请求成功
            if (mutilVideoByTypeId.code==0){
                _uistate.value=MutilUIState.MutilVideoByTypeId(mutilVideoByTypeId?.data)
            }else{
                _uistate.value=MutilUIState.Fail(mutilVideoByTypeId?.msg)
            }
        }
    }

    //根据上级id获取Mutil视频类型信息
    private fun getMutilTypeByPid(pid:Int) {

        viewModelScope.launch {
            val mutilTypeByPid = mutilRepo.getMutilTypeByPid(pid)
            //是否请求成功
            if (mutilTypeByPid.code==0){
                _uistate.value=MutilUIState.MutilTypeByPid(mutilTypeByPid?.data)

            }else{
                _uistate.value=MutilUIState.Fail(mutilTypeByPid?.msg)
            }
        }
    }
    //获取MutilType视频类型信息
    private fun getMutilType() {
        viewModelScope.launch {
            val mutilType = mutilRepo.getMutilType()
            //是否请求成功
            if (mutilType.code==0){
                _uistate.value=MutilUIState.MutilType(mutilType?.data)
            }else{
                _uistate.value=MutilUIState.Fail(mutilType?.msg)
            }

        }

    }

}