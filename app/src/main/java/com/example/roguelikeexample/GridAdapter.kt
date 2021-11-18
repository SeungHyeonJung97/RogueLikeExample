package com.example.roguelikeexample

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.roguelikeexample.databinding.GridItemBinding

class GridAdapter(val context: Context, val dungeon: Array<IntArray>) : RecyclerView.Adapter<GridAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridAdapter.ViewHolder {
        val binding = GridItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dungeon.size * dungeon.size;
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        Log.d("position",""+position)
//        Log.d("dungeon.size",""+dungeon.size)

        val dungeon_x = position / 25 ?: 0
        val dungeon_y = position % 25 ?: 0

        holder.bind(dungeon[dungeon_x][dungeon_y])
    }

    class ViewHolder(val binding: GridItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(data: Int){

            binding.imageView.setImageDrawable(null)
            if(data!=0){
//                Log.d("map_on", ""+data);
                binding.imageView.setImageResource(R.drawable.map_on)
            }else{
//                Log.d("map_off", ""+data);
                binding.imageView.setImageResource(R.drawable.map_off)
            }
        }
    }
}
