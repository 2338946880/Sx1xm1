package com.example.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.user.databinding.ActivityRegisterBinding
import com.example.user.intent.UserIntent
import com.example.user.state.UserState
import com.example.user.viewmodel.UserViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@Route(path = "/user/RegisterActivity")
class RegisterActivity : AppCompatActivity() {
    val TAG = "RegisterActivity"
    lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val viewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        lifecycleScope.launch{
            viewModel.state.collect { state ->
                when(state){
                    is UserState.Init -> {
                        Log.d(TAG, "onCreate: 初始化")
                    }
                    is UserState.register -> {
                        register()
                    }
                    is UserState.Failed -> {
                        TODO(state.msg)
                    }
                    else -> {}
                }

            }
        }

        binding.btRegister.setOnClickListener {
            val user = binding.etUser.text.toString()
            val pass1 = binding.etPass1.text.toString()
            val pass2 = binding.etPass2.text.toString()
            if (TextUtils.isEmpty(user)||TextUtils.isEmpty(pass1)||TextUtils.isEmpty(pass2)){
                Toast.makeText(this,"输入不能为空",Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (!pass1.equals(pass2)){
                Toast.makeText(this,"密码不一致，请重新输入",Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            lifecycleScope.launch {
                viewModel.channel.send(UserIntent.register(user, pass1))
            }
        }


    }



    private fun register() {
        finish()
    }
}