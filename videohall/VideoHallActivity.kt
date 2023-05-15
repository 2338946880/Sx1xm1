package com.example.videohall

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.launcher.ARouter
import com.example.videohall.databinding.ActivityVideoHallBinding
import com.umeng.socialize.UMShareAPI


class VideoHallActivity : AppCompatActivity() {

    lateinit var binding: ActivityVideoHallBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityVideoHallBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        var str= arrayOf(//权限
            "android.permission.VIBRATE",
            "android.permission.RECORD_AUDIO" ,
            "android.permission.CAMERA",
            "android.permission.ACCESS_NETWORK_STATE",
            "android.permission.WRITE_EXTERNAL_STORAGE" ,
            "android.permission.ACCESS_FINE_LOCATION",
            "android.permission.GET_TASKS" ,
            "android.permission.ACCESS_WIFI_STATE" ,
            "android.permission.CHANGE_WIFI_STATE" ,
            "android.permission.WAKE_LOCK",
            "android.permission.MODIFY_AUDIO_SETTINGS",
            "android.permission.READ_PHONE_STATE",
            "android.permission.RECEIVE_BOOT_COMPLETED",
            "android.permission.FOREGROUND_SERVICE",
            "android.permission.ACCESS_COARSE_LOCATION")
        requestPermissions( str,100)
        var navigation = ARouter.getInstance().build("/videohall/VideohallFragment").navigation() as VideohallFragment

        supportFragmentManager.beginTransaction().replace(R.id.content,navigation).commit()
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data)
    }
}