package com.example.videohall

import android.app.Application
import android.content.Context
import com.alibaba.android.arouter.launcher.ARouter
import com.umeng.commonsdk.UMConfigure
import com.umeng.socialize.PlatformConfig

class VideohallApp:Application() {

    companion object{
        @JvmField
        var context: Context? = null
    }
    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        ARouter.openDebug();
        ARouter.openLog();
        ARouter.init(this);//注意！！！init放在后面
        //友盟初始化
        UMConfigure.init(this,"645a3540ba6a5259c44d7091"
            ,"umeng",UMConfigure.DEVICE_TYPE_PHONE,"");//58edcfeb310c93091c000be2 5965ee00734be40b580001a0

        // QQ设置
        PlatformConfig.setQQZone("101830139","5d63ae8858f1caab67715ccd6c18d7a5");
       // PlatformConfig.setQQFileProvider("com.tencent.sample2.fileprovider");
    }

}