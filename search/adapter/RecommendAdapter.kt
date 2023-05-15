package com.example.search.adapter


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
import com.example.search.databinding.ItemRecommendBinding


class RecommendAdapter(val context: Context,var data: ArrayList<IUnionVideoMutilEntity>):RecyclerView.Adapter<RecommendAdapter.MyViewHolder>() {

    lateinit var binding: ItemRecommendBinding
    inner class MyViewHolder(view:View):RecyclerView.ViewHolder(view){
        var tiitle=binding.simpleTv
        var image=binding.simpleIv


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
         binding=ItemRecommendBinding.inflate(LayoutInflater.from(context),parent,false)
        return MyViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       var data=data.get(position)
        holder.tiitle.text=data.title
        Glide.with(context).load(data.videomainimag).apply(RequestOptions().transform(CenterCrop(),RoundedCorners(20))).into(holder.image)
    }

    override fun getItemCount(): Int {
        return data.size
    }


}