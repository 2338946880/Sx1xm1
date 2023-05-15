package com.example.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.example.home.adapter.DetailsTabAdapter
import com.example.home.databinding.ActivityHomeVideoDetailsBinding
import com.example.repofitutils.entity.IUnionVideoSimpleEntity
import com.shuyu.gsyvideoplayer.GSYVideoManager

@Route(path = "/home/HomeVideoDetailsActivity")
class HomeVideoDetailsActivity : AppCompatActivity() {
    val TAG = "HomeVideoDetailsActivity"
    lateinit var binding:ActivityHomeVideoDetailsBinding
    lateinit var detailsTabAdapter: DetailsTabAdapter
    @JvmField
    @Autowired
    var entity: IUnionVideoSimpleEntity? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeVideoDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ARouter.getInstance().inject(this)
        Log.d(TAG, "onCreate: ${entity.toString()}")
        binding.gsyHome.setUp(entity?.videopath,true,"")
        binding.gsyHome.startPlayLogic()
        binding.gsyHome.isLockLand =false
        binding.gsyHome.isRotateViewAuto =true
        binding.gsyHome.isShowFullAnimation =true
        binding.gsyHome.fullscreenButton.setOnClickListener {
            binding.gsyHome.startWindowFullscreen(this,false,false)
        }
        binding.gsyHome.backButton.setOnClickListener {
            finish()
        }
        getTab()

    }

    private fun getTab() {
        var  list:MutableList<String> = mutableListOf()
        list.add("简介")
        list.add("评论")
        detailsTabAdapter = entity?.let { DetailsTabAdapter(it,list,supportFragmentManager) }!!
        binding.vpDetails.adapter = detailsTabAdapter
        binding.tabDetails.setupWithViewPager(binding.vpDetails)
    }

    override fun onRestart() {
        super.onRestart()
        GSYVideoManager.onResume(true)
    }

    override fun onPause() {
        super.onPause()
        GSYVideoManager.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        GSYVideoManager.releaseAllVideos()
    }
}