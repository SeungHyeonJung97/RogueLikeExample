package com.example.roguelikeexample

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.roguelikeexample.Settings.hero_position
import com.example.roguelikeexample.Settings.monsterDistance
import com.example.roguelikeexample.Settings.monsterStatus
import com.example.roguelikeexample.Settings.monster_position
import com.example.roguelikeexample.databinding.GridTileBinding

class MainFrameAdapter(val dungeon: Array<IntArray>) :
    RecyclerView.Adapter<MainFrameAdapter.ViewHolder>() {
    val tileSize = 7
    var tileIndex = 0
    var beforeHeroPosition = 0
    var monsterIndex = 0



    inner class ViewHolder(val binding: GridTileBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Int) {


            Log.d("data", "${data}")

            binding.imageView.setImageBitmap(null)
            if (!data.isZero()) {
                binding.imageView.setBackgroundResource(R.drawable.tile_on)
            } else {
                binding.imageView.setBackgroundResource(R.drawable.tile_off)
            }
            when (data) {
                2 -> {
                    when(monsterIndex){
                        0 -> {
                            when (monsterDistance) {
                                0 -> {

                                    binding.imageView.setImageResource(R.drawable.monster1_bottom)
                                }
                                1 -> {
                                    binding.imageView.setImageResource(R.drawable.monster1_top)
                                }
                                2 -> {
                                    binding.imageView.setImageResource(R.drawable.monster1_left)
                                }
                                3 -> {
                                    binding.imageView.setImageResource(R.drawable.monster1_right)
                                }
                            }
                        }
                        1 -> {
                            when (monsterDistance) {
                                0 -> {
                                    binding.imageView.setImageResource(R.drawable.monster2_bottom)
                                }
                                1 -> {
                                    binding.imageView.setImageResource(R.drawable.monster2_top)
                                }
                                2 -> {
                                    binding.imageView.setImageResource(R.drawable.monster2_left)
                                }
                                3 -> {
                                    binding.imageView.setImageResource(R.drawable.monster2_right)
                                }
                            }
                        }
                        2 -> {
                            when (monsterDistance) {
                                0 -> {
                                    binding.imageView.setImageResource(R.drawable.monster3_bottom)
                                }
                                1 -> {
                                    binding.imageView.setImageResource(R.drawable.monster3_top)
                                }
                                2 -> {
                                    binding.imageView.setImageResource(R.drawable.monster3_left)
                                }
                                3 -> {
                                    binding.imageView.setImageResource(R.drawable.monster3_right)
                                }
                            }
                        }
                        3 -> {
                            when (monsterDistance) {
                                0 -> {
                                    binding.imageView.setImageResource(R.drawable.monster4_bottom)
                                }
                                1 -> {
                                    binding.imageView.setImageResource(R.drawable.monster4_top)
                                }
                                2 -> {
                                    binding.imageView.setImageResource(R.drawable.monster4_left)
                                }
                                3 -> {
                                    binding.imageView.setImageResource(R.drawable.monster4_right)
                                }
                            }
                        }
                        4 -> {
                            when (monsterDistance) {
                                0 -> {
                                    binding.imageView.setImageResource(R.drawable.monster5_bottom)
                                }
                                1 -> {
                                    binding.imageView.setImageResource(R.drawable.monster5_top)
                                }
                                2 -> {
                                    binding.imageView.setImageResource(R.drawable.monster5_left)
                                }
                                3 -> {
                                    binding.imageView.setImageResource(R.drawable.monster5_right)
                                }
                            }
                        }
                        5 -> {
                            when (monsterDistance) {
                                0 -> {
                                    binding.imageView.setImageResource(R.drawable.monster6_bottom)
                                }
                                1 -> {
                                    binding.imageView.setImageResource(R.drawable.monster6_top)
                                }
                                2 -> {
                                    binding.imageView.setImageResource(R.drawable.monster6_left)
                                }
                                3 -> {
                                    binding.imageView.setImageResource(R.drawable.monster6_right)
                                }
                            }
                        }
                        6 -> {
                            when (monsterDistance) {
                                0 -> {
                                    binding.imageView.setImageResource(R.drawable.monster7_bottom)
                                }
                                1 -> {
                                    binding.imageView.setImageResource(R.drawable.monster7_top)
                                }
                                2 -> {
                                    binding.imageView.setImageResource(R.drawable.monster7_left)
                                }
                                3 -> {
                                    binding.imageView.setImageResource(R.drawable.monster7_right)
                                }
                            }
                        }
                    }
                }
                4 -> binding.imageView.setImageResource(R.drawable.stair)
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

        if (beforeHeroPosition != hero_position) {
            tileIndex = 0
            beforeHeroPosition = hero_position
        }

        var hero_Position_X = 0
        var hero_Position_Y = 0
        var dungeon_x = 0
        var dungeon_y = 0

        hero_Position_X = hero_position / 25
        hero_Position_Y = hero_position % 25

        if ((position % 7).isZero() && position >= 7) tileIndex += 1

        dungeon_x = hero_Position_X - 3 + tileIndex
        dungeon_y = hero_Position_Y - 3


        if (position >= 7) {
            dungeon_y += position % 7
        } else {
            dungeon_y += position
        }
        if (dungeon_x >= dungeon.size || dungeon_x <= 0 || dungeon_y <= 0
        ) {
            dungeon_x = 0
            holder.bind(0)
        } else if (dungeon_y >= dungeon.size || dungeon_y <= 0) {
            dungeon_y = 0
            holder.bind(0)
        } else {
            if(dungeon[dungeon_x][dungeon_y] == 2){
                monsterIndex = monster_position.indexOf((dungeon_x * 25) + dungeon_y)
            }
            holder.bind(dungeon[dungeon_x][dungeon_y])
        }


        Log.d("heroPosition", "${Settings.hero_position}")
        Log.d("dungeon", "${dungeon_x}, ${dungeon_y}")

    }

}
