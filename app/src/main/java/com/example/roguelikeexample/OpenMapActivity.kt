package com.example.roguelikeexample

import android.app.Activity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.example.roguelikeexample.databinding.ActivityOpenMapBinding

class OpenMapActivity: Activity() {
    private lateinit var binding: ActivityOpenMapBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_open_map)
        binding.gridView.layoutManager = GridLayoutManager(this,25)

        val adapter = GridAdapter(Settings.dungeon)
        binding.gridView.adapter = adapter

        binding.btnCancel.setOnClickListener{
            finish()
        }
    }
}