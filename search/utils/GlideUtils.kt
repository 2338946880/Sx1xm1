package com.example.search.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object GlideUtils {

    @JvmStatic
    @BindingAdapter("android:url")
    fun images(iv:ImageView,url:String){
        Glide.with(iv).load(url).into(iv)
    }
}