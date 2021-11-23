package com.example.roguelikeexample

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.roguelikeexample.databinding.GridItemBinding

class GridAdapter(val dungeon: Array<IntArray>) : RecyclerView.Adapter<GridAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridAdapter.ViewHolder {
        val binding = GridItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dungeon.size * dungeon.size;
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dungeon_x = position / 25 ?: 0
        val dungeon_y = position % 25 ?: 0

        holder.bind(dungeon[dungeon_x][dungeon_y])
    }

    class ViewHolder(val binding: GridItemBinding) : RecyclerView.ViewHolder(binding.root){
        @SuppressLint("ResourceAsColor")
        fun bind(data: Int){

            when(data){
                0 -> binding.imageView.setImageResource(R.color.white)
                1 -> binding.imageView.setImageResource(R.color.black)
                3 -> binding.imageView.setImageResource(R.color.green)
            }
        }
    }
}
