package com.example.videohall.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.repofitutils.entity.IUnionVideoMutilEntity
import com.example.videohall.databinding.ItemVdeobyidBinding

class MutilVideoAdapter(val context: Context,var data:ArrayList<IUnionVideoMutilEntity>,val clickListener:onClickListener):RecyclerView.Adapter<MutilVideoAdapter.MyViewHolder>() {

    lateinit var binding: ItemVdeobyidBinding

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title = binding.tvName
        var iv = binding.ivVideo
        var desc = binding.tvDescr

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
         binding = ItemVdeobyidBinding.inflate(LayoutInflater.from(context), parent, false)
        return MyViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val video = data.get(position)
        holder.title.text = video.title
        Glide.with(context).load(video.videomainimag)
            .apply(RequestOptions().transform(CenterCrop(), RoundedCorners(20))).into(holder.iv)
        holder.desc.text = video.description

        holder.itemView.setOnClickListener {
            clickListener.onClick(position,video)
        }

    }

    override fun getItemCount(): Int {
        return data.size
    }
    interface onClickListener{
        fun onClick(position: Int,entity: IUnionVideoMutilEntity)
    }


}
