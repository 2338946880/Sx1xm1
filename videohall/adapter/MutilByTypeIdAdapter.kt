package com.example.videohall.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.repofitutils.entity.VideoTypeEntity
import com.example.videohall.databinding.ItemBytypeidBinding

class MutilByTypeIdAdapter(var context: Context,var data:ArrayList<VideoTypeEntity>,var clickListener: onClickListener) :RecyclerView.Adapter<MutilByTypeIdAdapter.MyViewHolder>() {
    lateinit var binding: ItemBytypeidBinding
    inner class MyViewHolder(view:View) : RecyclerView.ViewHolder(view){
        var title=binding.tvTypeid
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        binding=ItemBytypeidBinding.inflate(LayoutInflater.from(context),parent,false)
        return MyViewHolder(binding.root)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val videoid = data.get(position)
        holder.title.text=videoid.typename
        holder.itemView.setOnClickListener {
            clickListener.onClick(position,videoid)
        }

    }

    override fun getItemCount(): Int {
        return data.size
    }
    interface onClickListener{
        fun onClick(position: Int,entity: VideoTypeEntity)
    }
}