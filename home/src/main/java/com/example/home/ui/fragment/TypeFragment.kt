package com.example.home.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.alibaba.android.arouter.launcher.ARouter
import com.example.home.adapter.HomeVideoAdapter
import com.example.home.databinding.FragmentTypeBinding
import com.example.home.homeintent.HomeSimpleVideoIntent
import com.example.home.homestate.HomeSimpleVideoState
import com.example.home.viewmodle.HomeViewModel
import com.example.repofitutils.entity.IUnionVideoSimpleEntity
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import com.umeng.socialize.ShareAction
import com.umeng.socialize.bean.SHARE_MEDIA
import com.umeng.socialize.media.UMWeb
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class TypeFragment : Fragment() {
    lateinit var binding:FragmentTypeBinding
    var homeVideoAdapter: HomeVideoAdapter? = null
    lateinit var viewModel: HomeViewModel
    var page: Int = 0
    val pagesize: Int = 10
    var channelId: String = ""
    val TAG = "TypeFragment"
    private val dataSource: MutableList<IUnionVideoSimpleEntity> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTypeBinding.inflate(layoutInflater, container, false)
        dataSource.clear()
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        channelId = arguments?.getString("id").toString()
        lifecycleScope.launch{
            viewModel.state.collect { state ->
                when(state) {
                    is HomeSimpleVideoState.Init ->{
                        Log.d(TAG, "onCreateView: 初始化")
                    }
                    is HomeSimpleVideoState.SimpleVideos ->{
                        dataSource.addAll(state.list!!)
                        homeVideoAdapter?.notifyDataSetChanged()
                    }
                    is HomeSimpleVideoState.Failed ->{
                        dataSource.clear()
                        homeVideoAdapter?.notifyDataSetChanged()
                        Log.d(TAG, "onCreateView: 加载数据失败")
                    }
                    else -> {}
                }
            }
        }
        channelId = arguments?.getString("id").toString()
        getsmart()
        getvideo()
        updateVideoUI()
        return binding.root
    }



    private fun getsmart() {
        binding.smart.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener{
            override fun onRefresh(refreshLayout: RefreshLayout) {
                dataSource.clear()
                page = 0
                getvideo()
                binding.smart.finishRefresh()
            }

            override fun onLoadMore(refreshLayout: RefreshLayout) {
                page++
                getvideo()
                binding.smart.finishLoadMore()
            }
        })
    }

    fun getvideo(){
        lifecycleScope.launch {
            viewModel.channel.send(HomeSimpleVideoIntent.getSimpleVideoByChannelId(channelId, page, pagesize))
        }
    }

    private fun updateVideoUI() {
        homeVideoAdapter  = HomeVideoAdapter(requireContext(),dataSource ,object :
            HomeVideoAdapter.SubscibeOnClickListener{
            override fun onClick(position: Int, entity: IUnionVideoSimpleEntity) {
                Toast.makeText(context, "zs", Toast.LENGTH_SHORT).show()
            }
        },object : HomeVideoAdapter.SharedOnClickListener{
            override fun onClick(position: Int, entity: IUnionVideoSimpleEntity) {
//                val web: UMWeb = UMWeb(entity.videopath)
//                web.title = entity.title //标题
//                web.description = entity.description//描述
//                ShareAction(context as Activity?)
//                    .setDisplayList(SHARE_MEDIA.QQ,SHARE_MEDIA.WEIXIN)
//                    .open()
                val web =  UMWeb(entity.videopath)
                web.title =  entity.title//标题
                ShareAction(activity)
                    .withMedia(web)
                    .setDisplayList(SHARE_MEDIA.QQ, SHARE_MEDIA.WEIXIN,SHARE_MEDIA.SINA)
                    .open()
            }
        })

        homeVideoAdapter!!.setPlayOnClickListener(object : HomeVideoAdapter.PlayOnClickListener{
            override fun onClick(entity: IUnionVideoSimpleEntity) {
                ARouter.getInstance().build("/home/HomeVideoDetailsActivity").withSerializable("entity",entity).navigation()
            }
        })
        binding.rvHome.adapter = homeVideoAdapter
        binding.rvHome.layoutManager = StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL)

    }


}