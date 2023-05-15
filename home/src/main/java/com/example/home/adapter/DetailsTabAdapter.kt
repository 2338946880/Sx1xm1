package com.example.home.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter
import com.example.home.ui.fragment.CommentFragment
import com.example.home.ui.fragment.DetailsFragment
import com.example.repofitutils.entity.IUnionVideoSimpleEntity

class DetailsTabAdapter(val entity: IUnionVideoSimpleEntity, val list:List<String>, val fragmentManager: FragmentManager):FragmentStatePagerAdapter(fragmentManager) {
    private val fragments:MutableList<Fragment>
    init {
        fragments = mutableListOf()
        var detailsFragment= DetailsFragment()
        var commentFragment= CommentFragment()
        var bundle = Bundle()
        bundle.putSerializable("entity", entity)
        detailsFragment.arguments = bundle
        commentFragment.arguments = bundle
        fragments.add(detailsFragment)
        fragments.add(commentFragment)
    }
    override fun getCount(): Int {
        return fragments.size
    }

    override fun getItem(position: Int): Fragment {
        return fragments.get(position)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return list.get(position)
    }

    override fun getItemPosition(`object`: Any): Int {
        return PagerAdapter.POSITION_NONE
    }
}