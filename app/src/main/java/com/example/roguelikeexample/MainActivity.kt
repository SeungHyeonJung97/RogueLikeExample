package com.example.roguelikeexample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.example.roguelikeexample.Utils.createMap
import com.example.roguelikeexample.databinding.ActivityMainBinding
import java.util.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var dungeon: Array<IntArray>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.btnOpenMap.setOnClickListener {
            var intent = Intent(this, OpenMapActivity::class.java)
            intent.putExtra("dungeon", dungeon)
            startActivity(intent)
        }
        dungeon = createMap()
    }
}