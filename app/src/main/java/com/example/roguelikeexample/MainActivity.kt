package com.example.roguelikeexample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
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
            startActivityForResult(intent, 1)
        }
        createMap()
    }

    fun createMap() {
        /***
         * map에서의 위치 : map[x][y]가 있다고 가정하면,
         * (x * 25) + y
         */
        // dungeon 크기는 우선 25 * 25
        dungeon = Array<IntArray>(25) {
            IntArray(25)
        }

        // dungeon 들어갈 방의 갯수는 5 ~ 7
        var numberOfRooms = Random.nextInt(5)
        if (numberOfRooms < 3) numberOfRooms = numberOfRooms + 5
        Log.d("방 개수", "" + numberOfRooms);

        for (i in 0..numberOfRooms) {
            // 방을 만들기 위한 point
            var isExist = true
            var dungeon_x = 0
            var dungeon_y = 0
            var room_x = 0
            var room_y = 0


            createLoof@while (isExist) {
                val pickedPoint = Random.nextInt(dungeon.size * dungeon.size)

                dungeon_x = pickedPoint / 25
                dungeon_y = pickedPoint % 25

                if (dungeon[dungeon_x][dungeon_y] == 0) {
                    room_x = 2 + (Random.nextInt(4))
                    room_y = 2 + (Random.nextInt(4))

                    if (dungeon_x + room_x >= dungeon.size
                        || dungeon_y + room_y >= dungeon.size
                    ) {
                        continue@createLoof
                    }

                    for (j in 0 until room_x) {
                        if(dungeon_y != 0 && dungeon[dungeon_x + j][dungeon_y-1] == 1){
                            continue@createLoof
                        }

                        for (k in 0 until room_y) {
                            if(dungeon_x != 0 && dungeon[dungeon_x - 1][dungeon_y + k] == 1){
                                continue@createLoof
                            }

                            if (dungeon[dungeon_x + j][dungeon_y + k] == 1) {
                                Log.d(
                                    "Room Exists!", "X:" + dungeon_x + room_x + " Y:" + dungeon_y + room_y
                                )
                                continue@createLoof
                            }
                            isExist = false
                        }
                    }

                }
            }
            for (x in 0..room_x) {
                for (y in 0..room_y) {
                    dungeon[dungeon_x + x][dungeon_y + y] = 1
                }
            }
            Log.d("방 생성",""+dungeon_x+" "+dungeon_y)
        }
    }
}