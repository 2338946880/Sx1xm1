package com.example.home.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.alibaba.android.arouter.launcher.ARouter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.example.home.R
import com.example.home.adapter.TuiJianAdapter
import com.example.home.databinding.FragmentCommentBinding
import com.example.home.databinding.FragmentDetailsBinding
import com.example.home.homeintent.DetailsIntent
import com.example.home.homestate.DetailsState
import com.example.home.viewmodle.DetailsViewModel
import com.example.repofitutils.RepofitApp
import com.example.repofitutils.entity.IUnionVideoSimpleEntity
import com.example.repofitutils.utils.ACache
import com.example.repofitutils.utils.GlideUtils
import com.umeng.commonsdk.debug.I
import com.umeng.socialize.ShareAction
import com.umeng.socialize.bean.SHARE_MEDIA
import com.umeng.socialize.media.UMWeb
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class DetailsFragment : Fragment() {
    var entity: IUnionVideoSimpleEntity? = null
    lateinit var binding:FragmentDetailsBinding
    var tuiJianAdapter: TuiJianAdapter? = null
    val TAG = "DetailsFragment"
    private val dataSource: MutableList<IUnionVideoSimpleEntity> = mutableListOf()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        entity = arguments?.getSerializable("entity") as IUnionVideoSimpleEntity?
        binding = FragmentDetailsBinding.inflate(layoutInflater, container, false)
        GlideUtils.getInstance().loadRounded(context, entity!!.avatar_url,binding.detailIv)
        binding.detailNameTv.text = entity!!.name
        binding.detailTitleTv.text = entity!!.title
        dataSource.clear()
        tuiJianAdapter = TuiJianAdapter(R.layout.item_tuij)
        val viewModel = ViewModelProvider(this).get(DetailsViewModel::class.java)
        lifecycleScope.launch {
            viewModel.state.collect { state ->
                when(state){
                    is DetailsState.Init ->{
                        Log.d(TAG, "onCreateView: 初始化")
                    }
                    is DetailsState.RecommendSimpleVideos ->{
                        dataSource.addAll(state.list)
                        tuiJianAdapter!!.data.addAll(dataSource)
                        tuiJianAdapter?.notifyDataSetChanged()
                    }
                    is DetailsState.focuse ->{
                        binding.gzTv.text = "已关注"
                    }
                    is DetailsState.RecommendFailed ->{

                    }
                    else -> {}
                }
            }
        }
        binding.gzTv.setOnClickListener {
            val focuseUserid:Int = ACache.get(RepofitApp.context).getAsObject("focuseUserid") as Int
            Toast.makeText(context,"关注",Toast.LENGTH_LONG).show()
            lifecycleScope.launch {
                viewModel.channel.send(DetailsIntent.focuse(focuseUserid, entity!!.id))
            }
        }

        binding.detailShare.setOnClickListener {
            val web =  UMWeb(entity!!.videopath)
            web.title =  entity!!.title//标题
            ShareAction(activity)
                .withMedia(web)
                .setDisplayList(SHARE_MEDIA.QQ, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.SINA)
                .open()
        }

        lifecycleScope.launch{
            viewModel.channel.send(DetailsIntent.getRecommendSimpleVideo(1,10))
        }
        binding.gzTv.setOnClickListener {

        }
        getVideo()
        return binding.root
    }

    private fun getVideo() {
        binding.rvViewRecommend.adapter = tuiJianAdapter
        binding.rvViewRecommend.layoutManager = GridLayoutManager(context,3)
        tuiJianAdapter!!.setOnItemClickListener(object : OnItemClickListener{
            override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
                ARouter.getInstance().build("/home/HomeVideoDetailsActivity").withSerializable("entity",
                    tuiJianAdapter!!.data.get(position)).navigation()
            }
        })
    }



}