package com.example.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.home.R
import com.example.home.databinding.ItemSimpleInfoBinding
import com.example.repofitutils.entity.FocuseEntity
import com.example.repofitutils.entity.IUnionVideoSimpleEntity
import com.example.repofitutils.entity.VideoTypeEntity
import com.example.repofitutils.utils.DateUtils
import com.example.repofitutils.utils.GlideUtils

class HomeVideoAdapter(val context: Context, var list: List<IUnionVideoSimpleEntity>,val subscribeListener:SubscibeOnClickListener,val sharedListener:SharedOnClickListener):RecyclerView.Adapter<HomeVideoAdapter.HomeVideoViewHolder>() {

    inner class HomeVideoViewHolder(val binding: ItemSimpleInfoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val headerImg = binding.ivItemSimpleInfoHeader
        val author = binding.tvItemSimpleInfoAuthor
        val publishTime = binding.tvItemSimpleInfoPublishtime
        val primaryImg = binding.ivItemSimpleInfoPrimaryimage
        val title = binding.tvItemSimpleInfoTitle
        val shared = binding.llItemSimpleInfoShared
        val commentNum = binding.llItemSimpleInfoCommentnum
        val ivagreeNum = binding.ivItemSimpleInfoAgree
        val agreeNum = binding.llItemSimpleInfoAgreenum
        val subscibe = binding.btnItemSimpleInfoSubscibe
        val txtCommentNum = binding.tvItemSimpleInfoCommentnum
        val txtAgreeNum = binding.tvItemSimpleInfoAgreenum
        val ivPlay=binding.ivItemSimpleInfoPlay
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeVideoViewHolder {
        val binding = ItemSimpleInfoBinding.inflate(LayoutInflater.from(context), parent, false)
        return HomeVideoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeVideoViewHolder, position: Int,payloads: MutableList<Any>) {
        super.onBindViewHolder(holder, position, payloads)
        if(payloads.isEmpty())return
        val entity = payloads.get(0) as FocuseEntity
        if(entity != null){
            holder.subscibe.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    private lateinit var playOnClickListener:PlayOnClickListener
    fun setPlayOnClickListener(_playOnClickListener:PlayOnClickListener){
        playOnClickListener=_playOnClickListener
    }

    interface PlayOnClickListener{
        fun onClick(entity:IUnionVideoSimpleEntity)
    }

    interface SubscibeOnClickListener{
        fun onClick(position:Int,entity:IUnionVideoSimpleEntity)
    }

    interface SharedOnClickListener{
        fun onClick(position:Int,entity:IUnionVideoSimpleEntity)
    }

    override fun onBindViewHolder(holder: HomeVideoViewHolder, position: Int) {
        val entity = list.get(position)
        GlideUtils.getInstance().loadCircle(context,entity.avatar_url,holder.headerImg)
        holder.author.text = entity.name
        holder.publishTime.text = String.format(context.resources.getString(R.string.home_simple_videoinfo_publishtime), DateUtils.diffDateFromUTC(entity.ctime))
        GlideUtils.getInstance().loadNormal(context,entity.videomainimag,holder.primaryImg)
        holder.title.text = entity.title
        holder.txtCommentNum.text = entity.commentnum.toString()
        holder.txtAgreeNum.text = entity.playnum.toString()

        holder.ivPlay.setOnClickListener {
            if (null!=playOnClickListener){
                playOnClickListener.onClick(entity)
            }
        }

        holder.subscibe.setOnClickListener {
            subscribeListener.onClick(position,entity)
        }

        holder.ivagreeNum.setOnClickListener {
            if (holder.ivagreeNum.tag==null || holder.ivagreeNum.tag==false){
                holder.ivagreeNum.setImageDrawable(context.getDrawable(R.drawable.ic_thumb_up_check))
                holder.ivagreeNum.tag=true
            }
            else{
                holder.ivagreeNum.setImageDrawable(context.getDrawable(R.drawable.ic_thumb_up))
                holder.ivagreeNum.tag=false
            }

        }

        holder.shared.setOnClickListener {
            sharedListener.onClick(position,entity)
        }
    }
}