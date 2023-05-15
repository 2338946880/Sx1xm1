package com.example.user

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.example.repofitutils.RepofitApp
import com.example.repofitutils.entity.ReporfitData
import com.example.repofitutils.entity.UserEntity
import com.example.repofitutils.utils.ACache
import com.example.user.databinding.ActivityLoginBinding
import com.example.user.intent.UserIntent
import com.example.user.state.UserState
import com.example.user.viewmodel.UserViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@Route(path = "/user/LoginActivity")
class LoginActivity : AppCompatActivity() {
    val TAG = "LoginActivity"
    lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val viewModel = ViewModelProvider(this).get(UserViewModel::class.java)


        lifecycleScope.launch {
            viewModel.state.collect { state ->
                when(state){
                    is UserState.Init ->{
                        Log.d(TAG, "onCreate: 初始化")
                    }
                    is UserState.login ->{
                        state.user?.let { login(it) }
                    }
                    is UserState.Failed ->{
                        sb(state.msg)
                    }
                    else -> {}
                }
            }
        }

        binding.btLogin.setOnClickListener {
            val user = binding.etUser.text.toString()
            val pass = binding.etPass.text.toString()
            if (TextUtils.isEmpty(user)|| TextUtils.isEmpty(pass)){
                Toast.makeText(this,"输入不能为空",Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            lifecycleScope.launch {
                viewModel.channel.send(UserIntent.login(user, pass))
            }

        }

        binding.tvRegister.setOnClickListener {
            ARouter.getInstance().build("/user/RegisterActivity").navigation()
        }

    }

    private fun sb(msg: String) {
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show()
    }

    fun login(user:UserEntity){
        ACache.get(RepofitApp.context).put("token",user.token)
        ACache.get(RepofitApp.context).put("focuseUserid",user.id)
        finish()
    }

}