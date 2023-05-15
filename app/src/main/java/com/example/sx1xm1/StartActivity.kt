package com.example.sx1xm1

import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.launcher.ARouter
import com.example.sx1xm1.databinding.ActivityStartBinding
import com.jaeger.library.StatusBarUtil

class StartActivity : AppCompatActivity() {
    lateinit var binding:ActivityStartBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        StatusBarUtil.setColorNoTranslucent(this, Color.WHITE)

    }

    override fun onResume() {
        super.onResume()
        binding.tvSplashAppversion.text ="当前版本：${BuildConfig.VERSION_NAME}"

        object :CountDownTimer(5000,1000){
            override fun onTick(millisUntilFinished: Long) {
                binding.tvSplashTime.text = "倒计时${millisUntilFinished/1000}S"
            }

            override fun onFinish() {
                ARouter.getInstance().build("/app/MainActivity").navigation()
                finish()
            }
        }.start()
    }
} 