package com.example.videohall.view

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.ToastUtils
import com.bumptech.glide.Glide
import com.example.MutilIntent
import com.example.MutilViewModel
import com.example.repofitutils.entity.IUnionVideoMutilEntity
import com.example.videohall.adapter.RecommVideoAdapter
import com.example.videohall.databinding.ActivityMutilXqVideoBinding
import com.example.videohall.state.MutilUIState
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.umeng.socialize.ShareAction
import com.umeng.socialize.UMShareListener
import com.umeng.socialize.bean.SHARE_MEDIA
import com.umeng.socialize.media.UMImage
import com.umeng.socialize.media.UMVideo
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import top.littlefogcat.danmakulib.danmaku.Danmaku
import top.littlefogcat.danmakulib.danmaku.DanmakuManager

@Route(path = "/videohall/MutilXqVideoActivity")
class MutilXqVideoActivity : AppCompatActivity() {


    @Autowired
    lateinit var video: IUnionVideoMutilEntity
    var vm=MutilViewModel()
    lateinit var recommVideoAdapter: RecommVideoAdapter
    var recomm= arrayListOf<IUnionVideoMutilEntity>()
    var binding:ActivityMutilXqVideoBinding?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMutilXqVideoBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        vm=ViewModelProvider(this).get(MutilViewModel::class.java)


        ARouter.getInstance().inject(this)
        //赋值
        binding!!.tvTitle.text=video.subtitle
        Log.d("===", "onCreate: "+video)
        val gsy = binding!!.gsy
        //添加数据源
        gsy.setUp(video.videopath,true,"")

        //视频图片展示
        var iv=ImageView(this)
        Glide.with(this).load(video.videomainimag).into(iv)
        gsy.thumbImageView=iv
        gsy.backButton.setOnClickListener {
            finish()
        }
        //横屏播放
        gsy.fullscreenButton.setOnClickListener {
            gsy.isShowFullAnimation=true
            gsy.isLockLand=true
            gsy.isRotateViewAuto=true
            if (video.videopath!=null){
                gsy.startWindowFullscreen(this,false,false)
            }
        }
        lifecycleScope.launch {
            vm.uiState.collect { uiState ->
                when(uiState){
                    is MutilUIState.Loading->{
                        Log.d("===", "加载中---")
                    }
                    is MutilUIState.RecommendMutilVideo->{
                        getRecommendMutilVideo(uiState)
                    }
                    else -> {}
                }

            }
        }


        recomm= arrayListOf()
        recommVideoAdapter= RecommVideoAdapter(this,recomm)
        binding!!.rvLike.adapter=recommVideoAdapter
        binding!!.rvLike.layoutManager=StaggeredGridLayoutManager(3,LinearLayoutManager.VERTICAL)

        lifecycleScope.launch {
            //发送请求列表的意图
            vm.channel.send(MutilIntent.getRecommendMutilVideo(3,12))
        }
    }
    //赋值操作
    private fun getRecommendMutilVideo(uiState: MutilUIState.RecommendMutilVideo) {
        if (uiState.list==null){
            ToastUtils.showLong("数据为空")
            return
        }
        //适配器刷新
        recommVideoAdapter.data= uiState.list as ArrayList<IUnionVideoMutilEntity>
        recommVideoAdapter.notifyDataSetChanged()


        //点击分享
        binding!!.ivFx.setOnClickListener {
            // QQ设置
            val video1: UMVideo = UMVideo(video.videopath)
            video1.setTitle("This is music title") //视频的标题
            var images:UMImage=UMImage(this,video.videomainimag)
            video1.setThumb(images) //视频的缩略图

            video1.setDescription("my description") //视频的描述

            //友盟
            var shar: Unit = ShareAction(this)
                .withMedia(images)//加载视频
                .setDisplayList(SHARE_MEDIA.SINA,SHARE_MEDIA.QQ,SHARE_MEDIA.WEIXIN)//分享方式
                .setCallback(object :UMShareListener{//回调监听
                    override fun onStart(p0: SHARE_MEDIA?) {
                        //开始分享
                    ToastUtils.showLong("开始分享")
                    }

                    override fun onResult(p0: SHARE_MEDIA?) {

                        //分享成功
                        ToastUtils.showLong("分享成功")
                    }

                    override fun onError(p0: SHARE_MEDIA?, p1: Throwable?) {

                        //分享失败
                        ToastUtils.showLong("分享失败")
                    }

                    override fun onCancel(p0: SHARE_MEDIA?) {

                        //取消分享
                        ToastUtils.showLong("取消分享")
                    }

                }).open();
        }
        binding!!.dmSw.setOnClickListener {
            if (binding!!.dmSw.isChecked==true){
                DanmakuManager.getInstance().init(this,binding!!.dmView)//初始化
                var danmu=Danmaku()
                danmu.size=50
                danmu.text="收到"
                danmu.color="#ff5722"
                danmu.mode=Danmaku.Mode.scroll
                DanmakuManager.getInstance().send(danmu)
            }else{
                return@setOnClickListener
            }

        }


    }


    override fun onDestroy() {
        super.onDestroy()
        GSYVideoManager.releaseAllVideos()
    }

    override fun onPause() {
        super.onPause()
        GSYVideoManager.onPause()
    }

    override fun onRestart() {
        super.onRestart()
        GSYVideoManager.onResume()
    }
}