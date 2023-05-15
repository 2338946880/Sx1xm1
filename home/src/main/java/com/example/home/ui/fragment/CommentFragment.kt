package com.example.home.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.home.R
import com.example.home.adapter.PingLunAdapter
import com.example.home.databinding.FragmentCommentBinding
import com.example.home.databinding.FragmentTypeBinding
import com.example.home.homeintent.DetailsIntent
import com.example.home.homestate.DetailsState
import com.example.home.viewmodle.DetailsViewModel
import com.example.repofitutils.entity.CommentEntity
import com.example.repofitutils.entity.IUnionVideoSimpleEntity
import com.example.repofitutils.entity.ReporfitData
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class CommentFragment : Fragment() {
    lateinit var binding:FragmentCommentBinding
    var entity: IUnionVideoSimpleEntity? = null
    val TAG = "CommentFragment"
    var pingLunAdapter:PingLunAdapter?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        entity = arguments?.getSerializable("entity") as IUnionVideoSimpleEntity?
        binding = FragmentCommentBinding.inflate(layoutInflater, container, false)
        val viewModel = ViewModelProvider(this).get(DetailsViewModel::class.java)

        lifecycleScope.launch{
            viewModel.state.collect { state ->
                when(state){
                    is DetailsState.Init ->{
                        Log.d(TAG, "onCreateView: 初始化")
                    }
                    is DetailsState.getCommentByUserId ->{
                        initMesage(state.data)
                    }
                    else -> {}
                }
            }
        }

        lifecycleScope.launch {
            viewModel.channel.send(DetailsIntent.getCommentByUserId(0, entity!!.group_id))
        }

        return binding.root
    }

    private fun initMesage(data:List<CommentEntity>) {
        pingLunAdapter= PingLunAdapter(R.layout.item_pinglun)
        pingLunAdapter!!.data.addAll(data.reversed())
        pingLunAdapter!!.notifyDataSetChanged()
        binding.tvMessage.adapter = pingLunAdapter
        binding.tvMessage.layoutManager = LinearLayoutManager(context)
        Log.d(TAG, "initMesage: 评论")
    }

}