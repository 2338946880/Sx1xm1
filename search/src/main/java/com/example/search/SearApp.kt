package com.example.search

import android.app.Application
import android.content.Context
import com.alibaba.android.arouter.launcher.ARouter
import com.umeng.commonsdk.UMConfigure
import com.umeng.socialize.PlatformConfig

class SearApp :Application() {


    companion object{
        @JvmField
        var content:Context?=null
    }
    override fun onCreate() {
        super.onCreate()
        content=applicationContext
        ARouter.openDebug()
        ARouter.openLog()
        ARouter.init(this)//注意！！！init放在后面
        UMConfigure.init(this,"64462852ba6a5259c441567c"
            ,"umeng",UMConfigure.DEVICE_TYPE_PHONE,"");//58edcfeb310c93091c000be2 5965ee00734be40b580001a0

        // 微信设置
        PlatformConfig.setWeixin("wxdc1e388c3822c80b","3baf1193c85774b3fd9d18447d76cab0");
// QQ设置
        PlatformConfig.setQQZone("101830139","5d63ae8858f1caab67715ccd6c18d7a5");
    }
}