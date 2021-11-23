package com.example.roguelikeexample

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.roguelikeexample.databinding.GridTileBinding

class MainFrameAdapter(val dungeon: Array<IntArray>) :
    RecyclerView.Adapter<MainFrameAdapter.ViewHolder>() {
    val tileSize = 7
    var tileIndex = 0
    var beforeHeroPosition = 0



    inner class ViewHolder(val binding: GridTileBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Int) {
            if (data != 0) {
                binding.imageView.setBackgroundResource(R.drawable.tile_on)
            } else {
                binding.imageView.setBackgroundResource(R.drawable.tile_off)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainFrameAdapter.ViewHolder {
        val binding = GridTileBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return tileSize * tileSize;
    }

    override fun onBindViewHolder(holder: MainFrameAdapter.ViewHolder, position: Int) {

        if(beforeHeroPosition != Utils.hero_position){
            tileIndex = 0
            beforeHeroPosition = Utils.hero_position
        }

        var hero_Position_X = 0
        var hero_Position_Y = 0
        var dungeon_x = 0
        var dungeon_y = 0

        hero_Position_X = Utils.hero_position / 25
        hero_Position_Y = Utils.hero_position % 25

        if (position % 7 == 0 && position >= 7) tileIndex += 1

        dungeon_x = hero_Position_X - 3 + tileIndex
        dungeon_y = hero_Position_Y - 3


        if (position > 7) {
            dungeon_y += position % 7
        } else {
            dungeon_y += position
        }

        if (dungeon_x >= dungeon.size || dungeon_y >= dungeon.size
            || dungeon_x <= 0 || dungeon_y <= 0) {
            holder.bind(0)
        } else {
            holder.bind(dungeon[dungeon_x][dungeon_y])
        }

        Log.d("hero_Position", "${hero_Position_X}, ${hero_Position_Y}")
        Log.d("dungeon", "${dungeon_x}, ${dungeon_y}")

    }
}
