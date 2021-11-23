package com.example.roguelikeexample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.example.roguelikeexample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var dungeon = Utils.dungeon


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.btnOpenMap.setOnClickListener {
            var intent = Intent(this, OpenMapActivity::class.java)
            startActivity(intent)
        }

        binding.rvMainframe.layoutManager = GridLayoutManager(this, 7)

        Log.d("hero_position", "${Utils.hero_position}")
        val adapter = MainFrameAdapter(dungeon)
        binding.rvMainframe.adapter = adapter

        binding.ivHero.bringToFront()

        binding.ivArrowTop.setOnClickListener {
            Utils.hero_position -= dungeon.size
            adapter.notifyDataSetChanged()
        }
        binding.ivArrowBottom.setOnClickListener {
            Utils.hero_position += dungeon.size
            adapter.notifyDataSetChanged()
        }
        binding.ivArrowLeft.setOnClickListener {
            Utils.hero_position -= 1
            adapter.notifyDataSetChanged()
        }
        binding.ivArrowRight.setOnClickListener {
            Utils.hero_position += 1
            adapter.notifyDataSetChanged()
        }
    }
}