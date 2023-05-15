package com.example.search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.search.databinding.ItemSearchBinding
import com.example.search.db.SearchEntity

class SearchAdapter(val content:Context,var data:ArrayList<SearchEntity>) :RecyclerView.Adapter<SearchAdapter.MyViewHolder>(){

    lateinit var binding: ItemSearchBinding
    inner class MyViewHolder(view:View):RecyclerView.ViewHolder(view){
        var title=binding.tvSearch
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        binding=ItemSearchBinding.inflate(LayoutInflater.from(content),parent,false)
        return MyViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val search = data.get(position)
        holder.title.text=search.title.toString()

    }

    override fun getItemCount(): Int {
      return  data.size
    }




}