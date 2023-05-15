package com.example.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.home.databinding.ActivityHomeBinding
import com.example.home.ui.fragment.HomeFragment
import com.umeng.socialize.PlatformConfig

class HomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportFragmentManager.beginTransaction().replace(R.id.home_act, HomeFragment()).commit()
        PlatformConfig.setWeixin("wxdc1e388c3822c80b","3baf1193c85774b3fd9d18447d76cab0");
    }
}