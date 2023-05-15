package com.example.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider

import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.example.repofitutils.entity.IUnionVideoMutilEntity
import com.example.search.adapter.RecommendAdapter
import com.example.search.databinding.ActivitySearchBinding
import com.example.search.db.DBInstance

import com.example.search.db.SearchEntity
import com.example.search.state.RecommendUIState
import com.example.search.viewmodel.SearViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@Route(path = "/search/SearchActivity")
class SearchActivity : AppCompatActivity() {
    var binding:ActivitySearchBinding?=null
    lateinit var searchAdapter: SearchAdapter
    lateinit var SearchEntity: SearchEntity
    lateinit var vm:SearViewModel
    var recommendAdapter: RecommendAdapter? = null
    var list= arrayListOf<SearchEntity>()
    var recomm= arrayListOf<IUnionVideoMutilEntity>()
    var recomm2= arrayListOf<IUnionVideoMutilEntity>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        vm=ViewModelProvider(this).get(SearViewModel::class.java)

       binding!!.searIv.setOnClickListener {
           val query = binding!!.search.text.toString()
           Log.d("===", "onCreate: "+query)
           if (query!=null&&!query.equals("")){
               SearchEntity= SearchEntity(query)
               DBInstance.getDatabase().searchDao().insert(SearchEntity)
           }
           getsearch()

           for (datum in recommendAdapter?.data!!) {
               Log.d("===", "onCreataopsuhgfpaoshfasde: ")
               if (datum.title.contains(query)){
                   recomm2.add(datum)
               }
           }



           if (query==null&&query.equals("")){
               recomm2.clear()
               recommendAdapter!!.data=recomm
               recommendAdapter!!.notifyDataSetChanged()
           }else{
               recomm.clear()
               recommendAdapter!!.data=recomm2
               recommendAdapter!!.notifyDataSetChanged()
           }
       }


        //历史列表
        searchAdapter= SearchAdapter(this,list)
        binding!!.rvLs.adapter=searchAdapter
        binding!!.rvLs.layoutManager=StaggeredGridLayoutManager(2,LinearLayoutManager.HORIZONTAL)
        getsearch()
        binding!!.tvSc.setOnClickListener {

            DBInstance.getDatabase().searchDao().delete()

            getsearch()
        }
        recomm= arrayListOf()
        //推荐列表
        recommendAdapter=RecommendAdapter(this,recomm)

        binding!!.rvTj.adapter=recommendAdapter
        binding!!.rvTj.layoutManager=StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL)


        lifecycleScope.launch {
            vm.uiState.collect { uiState ->
                when(uiState){
                    is RecommendUIState.Loading ->{
                        Log.d("===", "加载中---")
                    }
                    is RecommendUIState.Success->{
                        updateUI(uiState)
                    }
                    else -> {}
                }
            }
        }
        //推荐数据
        lifecycleScope.launch {
            vm.channel.send(SearViewModel.RecommendInter.getRecommend(1,10))
        }
    }


    //推荐数据
    private fun updateUI(uiState: RecommendUIState.Success) {
     if (uiState.list==null){
         return
     }

        recommendAdapter?.data= (uiState.list as ArrayList<IUnionVideoMutilEntity>?)!!
        recommendAdapter?.notifyDataSetChanged()


    }


    //历史刷新
    private fun getsearch() {
        val search1 = DBInstance.getDatabase().searchDao().search
        searchAdapter.data= search1 as ArrayList<SearchEntity>
        searchAdapter.notifyDataSetChanged()
    }
}