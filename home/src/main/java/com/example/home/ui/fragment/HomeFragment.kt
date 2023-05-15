package com.example.home.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.example.home.adapter.HomePageAdapter
import com.example.home.adapter.HomeTypeAdapter
import com.example.home.databinding.FragmentHomeBinding
import com.example.home.homeintent.HomeSimpleVideoIntent
import com.example.home.homestate.HomeSimpleVideoState
import com.example.home.viewmodle.HomeViewModel
import com.example.repofitutils.entity.VideoTypeEntity
import com.superluo.textbannerlibrary.ITextBannerItemClickListener
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@Route(path = "/home/HomeFragment")
class HomeFragment : Fragment() {
    lateinit var binding:FragmentHomeBinding
    lateinit var viewModel: HomeViewModel
    lateinit var homeTypeAdapter: HomeTypeAdapter
    var TAG = "HomeFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)



        lifecycleScope.launch{
            viewModel.state.collect { state ->
                when(state) {
                    is HomeSimpleVideoState.Init ->{
                        Log.d(TAG, "onCreateView: 初始化")
                    }
                    is HomeSimpleVideoState.SimpleType ->{
                        getTab(state.list)
                    }
                    else -> {}
                }
            }
        }

        lifecycleScope.launch {
            viewModel.channel.send(HomeSimpleVideoIntent.getSimpleType)
        }

        binding.etSearch.setOnClickListener {
            ARouter.getInstance().build("/search/SearchActivity").navigation()
        }
        return binding.root
    }


    private fun getTab(list: List<VideoTypeEntity>?) {
        val adapter= list?.let { HomePageAdapter(it,this.childFragmentManager) }
        binding.homeVp.adapter=adapter
        binding.tbHomeType.setupWithViewPager(binding.homeVp)
        binding.homeVp.adapter?.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
        binding.tvBanner.visibility = View.VISIBLE
        val list: MutableList<String> = mutableListOf()
        list.add("视频资讯1")
        list.add("视频资讯2")
        list.add("视频资讯3")
        binding.tvBanner.setDatas(list)
        binding.tvBanner.setItemOnClickListener(object : ITextBannerItemClickListener{
            override fun onItemClick(data: String?, position: Int) {
                binding.tvBanner.visibility = View.GONE
                Log.d(TAG, "onItemClick: $position")
                binding.etSearch.hint=list.get(position)
            }
        })
    }


}