package com.example.videohall.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.repofitutils.entity.VideoTypeEntity
import com.example.videohall.VideohallFragment
import com.example.videohall.databinding.ItemMutiltypeBinding

class MutiltypeAdapter(val context: Context, var data:ArrayList<VideoTypeEntity>,val clickListener:onClickListener):RecyclerView.Adapter<MutiltypeAdapter.MyViewHolder>() {

    lateinit var binding: ItemMutiltypeBinding
    inner class MyViewHolder(view:View):RecyclerView.ViewHolder(view){
        var title=binding.tvType
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        binding=ItemMutiltypeBinding.inflate(LayoutInflater.from(context),parent,false)
        return MyViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val video = data.get(position)
        holder.title.text=video.typename


        holder.itemView.setOnClickListener {
            clickListener.onClick(position,video)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
    interface onClickListener{
        fun onClick(position: Int,entity: VideoTypeEntity)
    }


}