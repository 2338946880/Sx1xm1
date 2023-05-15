package com.example.home.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter
import com.example.home.ui.fragment.TypeFragment
import com.example.repofitutils.entity.VideoTypeEntity

/**
 * Home页面ViewPager适配器
 */
class HomePageAdapter(val types:List<VideoTypeEntity>, val manager: FragmentManager): FragmentStatePagerAdapter(manager) {
    private val fragments:MutableList<Fragment>
    init {
        fragments = mutableListOf()
        for (entity in types){
            val fragment= TypeFragment()
            val bundle= Bundle()
            bundle.putString("id",entity.channelid)
            fragment.arguments=bundle
            fragments.add(fragment)
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return types.get(position).typename
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getItem(position: Int): Fragment {
        return fragments.get(position)
    }

    override fun getItemPosition(`object`: Any): Int {
        return PagerAdapter.POSITION_NONE
    }
}