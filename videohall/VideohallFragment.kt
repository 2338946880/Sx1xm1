package com.example.videohall

import android.os.Binder
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.ToastUtils
import com.example.MutilIntent
import com.example.MutilViewModel
import com.example.repofitutils.entity.IUnionVideoMutilEntity
import com.example.repofitutils.entity.VideoTypeEntity
import com.example.videohall.adapter.MutilByTypeIdAdapter
import com.example.videohall.adapter.MutilVideoAdapter
import com.example.videohall.adapter.MutiltypeAdapter
import com.example.videohall.databinding.FragmentVideohallBinding
import com.example.videohall.state.MutilUIState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.io.Serializable

@Route(path = "/videohall/VideohallFragment")
class VideohallFragment : Fragment() {

    lateinit var binding:FragmentVideohallBinding
    var vm=MutilViewModel()
    lateinit var mutiltypeAdapter: MutiltypeAdapter
    var list= arrayListOf<VideoTypeEntity>()
    lateinit var mutilByTypeIdAdapter: MutilByTypeIdAdapter
    var videobyid= arrayListOf<VideoTypeEntity>()
    lateinit var mutilVideoAdapter: MutilVideoAdapter
    var video= arrayListOf<IUnionVideoMutilEntity>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentVideohallBinding.inflate(layoutInflater,container,false)
        vm=ViewModelProvider(this).get(MutilViewModel::class.java)

        lifecycleScope.launch {
            vm.uiState.collect { uiState ->
                when(uiState){
                    is MutilUIState.Loading->{
                        Log.d("===", "加载中---")
                    }
                    is MutilUIState.MutilType->{
                        getmutiltype(uiState)
                    }
                    is MutilUIState.MutilTypeByPid->{
                        getmutiltypebypid(uiState)
                    }
                    is MutilUIState.MutilVideoByTypeId->{
                        getmutilvideobytypeId(uiState)
                    }
                    else -> {}
                }
            }
        }
        //type
        list= arrayListOf()
        mutiltypeAdapter=MutiltypeAdapter(requireContext(),list,object :MutiltypeAdapter.onClickListener{
            override fun onClick(position: Int, entity: VideoTypeEntity) {

                if (position==0||position==1||position==2){
                    lifecycleScope.launch {
                        vm.channel.send(MutilIntent.getMutilTypeByPid(position))
                    }
                }else{
                    ToastUtils.showLong("暂时无数据")
                }
            }
        })
        binding!!.rvType.adapter=mutiltypeAdapter
        binding!!.rvType.layoutManager=StaggeredGridLayoutManager(1,LinearLayoutManager.HORIZONTAL)
        //类型
        lifecycleScope.launch {
            vm.channel.send(MutilIntent.getMutilType)
        }

        //根据id展示类别
       mutilByTypeIdAdapter= MutilByTypeIdAdapter(requireContext(),videobyid,object :MutilByTypeIdAdapter.onClickListener{
           override fun onClick(position: Int, entity: VideoTypeEntity) {
               Log.d("===", "on: "+position+"sss"+entity.id)
               if (entity.id>=20&&entity.id<=32)
               lifecycleScope.launch {
                   vm.channel.send(MutilIntent.getMutilVideoByTypeId(1,10,entity.id))
               }else{
                   ToastUtils.showLong("暂时无数据")
               }
           }
       })
        binding!!.rvTypeid.adapter=mutilByTypeIdAdapter
        binding!!.rvTypeid.layoutManager=StaggeredGridLayoutManager(4,LinearLayoutManager.HORIZONTAL)
        //类型id
        lifecycleScope.launch {
            vm.channel.send(MutilIntent.getMutilTypeByPid(1))
        }

        //根据类别展示数据
        video= arrayListOf()
        mutilVideoAdapter= MutilVideoAdapter(requireContext(),video,object :MutilVideoAdapter.onClickListener{
            override fun onClick(position: Int, entity: IUnionVideoMutilEntity) {
                Log.d("===", "onClick: "+entity.id+"ddd"+position)

                //传值
                ARouter.getInstance().build("/videohall/MutilXqVideoActivity")
                    //通过点击的item把数据传递
                    .withSerializable("video",entity)
                    .navigation()

            }

        })
        binding!!.rvMuvideo.adapter=mutilVideoAdapter
        binding!!.rvMuvideo.layoutManager=StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL)
        //展示数据
        lifecycleScope.launch {
            vm.channel.send(MutilIntent.getMutilVideoByTypeId(1,10,20))
        }
        return binding!!.root
    }
    ////根据视频分类获取对应的视频信息
    private fun getmutilvideobytypeId(uiState: MutilUIState.MutilVideoByTypeId) {
        if (uiState.list==null){
            ToastUtils.showLong("数据为空")
            return
        }
        mutilVideoAdapter.data= (uiState.list as ArrayList<IUnionVideoMutilEntity>?)!!
        mutilVideoAdapter.notifyDataSetChanged()

    }
    //根据上级id获取Mutil视频类型信息
    private fun getmutiltypebypid(uiState: MutilUIState.MutilTypeByPid) {
        if (uiState.list==null){
            ToastUtils.showLong("数据为空")
            return
        }
        mutilByTypeIdAdapter.data= (uiState.list as ArrayList<VideoTypeEntity>?)!!
        mutilByTypeIdAdapter.notifyDataSetChanged()
    }
    //获取MutilType视频类型信息
    private fun getmutiltype(uiState: MutilUIState.MutilType) {
        if (uiState.list==null){
            ToastUtils.showLong("数据为空")
            return
        }
        mutiltypeAdapter.data= (uiState.list as ArrayList<VideoTypeEntity>?)!!
        mutiltypeAdapter.notifyDataSetChanged()
    }
}