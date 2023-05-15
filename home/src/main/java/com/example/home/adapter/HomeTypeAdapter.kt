package com.example.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.home.databinding.ItemSimpleInfoBinding
import com.example.home.databinding.ItemTypeBinding
import com.example.repofitutils.entity.VideoTypeEntity

class HomeTypeAdapter(val context: Context, val data: List<VideoTypeEntity>, val serOnItemClickListener: setOnItemClickListener):RecyclerView.Adapter<HomeTypeAdapter.HomeTypeViewHolder>() {


    lateinit var binding: ItemTypeBinding
    inner class HomeTypeViewHolder(val view: View):RecyclerView.ViewHolder(view){
        val title = binding.tvItemSimpleInfoTitle
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeTypeViewHolder {
        binding = ItemTypeBinding.inflate(LayoutInflater.from(context),parent,false)
        return HomeTypeViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: HomeTypeViewHolder, position: Int) {
        val typeEntity = data.get(position)
        holder.title.text = typeEntity.typename
        holder.view.setOnClickListener {
            serOnItemClickListener.OnItemClickListener(typeEntity,position)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }


    interface setOnItemClickListener{
        fun OnItemClickListener(entity:VideoTypeEntity,position:Int)
    }



}