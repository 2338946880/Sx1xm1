package com.example.user.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.user.intent.UserIntent
import com.example.user.repository.UserRepository
import com.example.user.state.UserState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class UserViewModel:ViewModel() {
    var userRepository = UserRepository()
    val channel= Channel<UserIntent>(Channel.UNLIMITED)
    private val user_state = MutableStateFlow<UserState>(UserState.Init)
    val state: StateFlow<UserState>
        get() = user_state

    init {
        handlerIntent()
    }

    private fun handlerIntent() {
        viewModelScope.launch {
            channel.consumeAsFlow().collect {
                when(it){
                    //is HomeSimpleVideoIntent.getSimpleType -> getSimpleType()
                    is UserIntent.login -> login(it.user,it.pass)
                    is UserIntent.register -> register(it.user,it.pass)
                }
            }
        }
    }

    fun login(user:String,pass:String){
        viewModelScope.launch{
            val login = userRepository.login(user, pass)
            if (login.code==0){
                user_state.value = UserState.login(login.data)
            }else {
                user_state.value = UserState.Failed(login.msg)
            }
        }
    }


    fun register(user:String,pass:String){
        viewModelScope.launch{
            val register = userRepository.register(user, pass)
            if (register.code==0){
                user_state.value = UserState.register(register.data)
            }else {
                user_state.value = UserState.Failed(register.msg)
            }
        }
    }

}