package com.example.home.adapter

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.home.databinding.ItemTuijBinding
import com.example.repofitutils.entity.IUnionVideoSimpleEntity
import com.example.repofitutils.utils.GlideUtils


class TuiJianAdapter(layoutResId: Int) : BaseQuickAdapter<IUnionVideoSimpleEntity?, BaseViewHolder>(layoutResId) {

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        DataBindingUtil.bind<ViewDataBinding>(holder.itemView)
        super.onBindViewHolder(holder, position)
    }
    
    override fun convert(helper: BaseViewHolder, item: IUnionVideoSimpleEntity?) {
        val binding = DataBindingUtil.getBinding<ItemTuijBinding>(helper.itemView)
        if (item != null) {
            if (binding != null) {
                GlideUtils.getInstance().loadNormal(context,item.videomainimag,binding.tuijIv)
                binding.tuijTv.text = item.title
            }
        }
    }

}