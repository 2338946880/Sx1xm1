package com.example.home.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.repofitutils.entity.CommentEntity
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.home.R
import java.text.ParseException
import java.text.SimpleDateFormat

class PingLunAdapter(layoutResId: Int) :
    BaseQuickAdapter<CommentEntity, BaseViewHolder>(layoutResId) {
    override fun convert(baseViewHolder: BaseViewHolder, commentEntity: CommentEntity) {
        baseViewHolder.setText(R.id.tv_message, commentEntity.content)
        try {
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
            val date = simpleDateFormat.parse(commentEntity.createtime)
            val time = date.time
            val l = System.currentTimeMillis() - time
            if (l < 3 * 60 * 1000) {
                baseViewHolder.setText(R.id.tv_time, "刚刚")
            } else if (l < 60 * 60 * 1000) {
                baseViewHolder.setText(R.id.tv_time, (l / 1000 / 60).toString() + "分钟前")
            } else if (l < 60 * 60 * 1000 * 24) {
                baseViewHolder.setText(R.id.tv_time, (l / 1000 / 60 / 60).toString() + "小时前")
            } else if (l < 60 * 60 * 1000 * 24 * 2) {
                baseViewHolder.setText(R.id.tv_time, (l / 1000 / 60 / 60 / 24).toString() + "天前")
            } else {
                baseViewHolder.setText(R.id.tv_time, commentEntity.createtime)
            }
        } catch (e: ParseException) {
            e.printStackTrace()
        }
    }
}