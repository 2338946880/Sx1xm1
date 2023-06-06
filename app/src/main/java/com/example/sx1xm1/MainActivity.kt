package com.example.sx1xm1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.example.home.App
import com.example.home.ui.fragment.HomeFragment
import com.example.repofitutils.RepofitApp
import com.example.repofitutils.utils.ACache
import com.example.sx1xm.adapter.PageAdapter
import com.example.sx1xm1.databinding.ActivityMainBinding
import com.example.user.MyFragment
import com.google.android.material.navigation.NavigationBarView
import com.jaeger.library.StatusBarUtil
@Route(path = "/app/MainActivity")
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var frag = ArrayList<Fragment>()
    lateinit var pageAdapter: PageAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        StatusBarUtil.setTranslucentForImageView(this, 0, null)
        frag.add(HomeFragment())
        frag.add(BlankFragment())
        frag.add(MyFragment())
        pageAdapter = PageAdapter(supportFragmentManager,FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,frag);
        binding.vp.adapter = pageAdapter
        //supportFragmentManager.beginTransaction().replace(R.id.cl,navigation).commit()
        //ARouter.getInstance().build("/home/HomeActivity").navigation()
        binding.botnav.setOnItemSelectedListener(object :NavigationBarView.OnItemSelectedListener{
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when(item.itemId) {
                    R.id.item_home ->{
                        binding.vp.currentItem = 0
                    }
                    R.id.item_video ->{
                        binding.vp.currentItem = 1
                    }
                    R.id.item_release ->{

                    }
                    R.id.item_sinatv ->{
                        binding.vp.currentItem = 2
                    }
                    R.id.item_my ->{
                        val token = ACache.get(RepofitApp.context).getAsString("token")
                        if (TextUtils.isEmpty(token)){
                            ARouter.getInstance().build("/user/LoginActivity").navigation()
                        }
                        binding.vp.currentItem = 3
                    }
                }
                return true
            }
        })
        val a=1
        binding.vp.addOnPageChangeListener(object :ViewPager.OnPageChangeListener{
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int,
            ) {

            }

            override fun onPageSelected(position: Int) {
                binding.botnav.menu.getItem(position).isChecked =true
            }

            override fun onPageScrollStateChanged(state: Int) {
            }

        })
    }
}
