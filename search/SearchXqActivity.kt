package com.example.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.search.databinding.ActivitySearchBinding
import com.example.search.databinding.ActivitySearchXqBinding

@Route(path = "/search/SearchXqActivity")
class SearchXqActivity : AppCompatActivity() {

    lateinit var binding:ActivitySearchXqBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySearchXqBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
    }
}