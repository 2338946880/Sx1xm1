package com.example.search.state

import com.example.repofitutils.entity.IUnionVideoMutilEntity

sealed class RecommendUIState {

    data class Success(var list: List<IUnionVideoMutilEntity>?):RecommendUIState()

    data class Error(var ex:Throwable?):RecommendUIState()

    data class Fail(var msg:String?):RecommendUIState()

    object Loading:RecommendUIState()


}