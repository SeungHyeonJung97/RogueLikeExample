package com.example.roguelikeexample

import android.util.Log
import kotlin.random.Random

object Utils {
    lateinit var dungeon: Array<IntArray>
    var dungeon_x = 0
    var dungeon_y = 0
    var room_x = 0
    var room_y = 0
    val minRoomSize = 2
    val borderSize = 2

    fun createMap(): Array<IntArray> {
        // dungeon 크기는 우선 25 * 25
        dungeon = Array<IntArray>(25) {
            IntArray(25)
        }

        // dungeon 들어갈 방의 갯수는 3 ~ 7
        var numberOfRooms = Random.nextInt(4)
        if (numberOfRooms < 3) numberOfRooms = numberOfRooms + 4
        Log.d("방 개수", "" + numberOfRooms);

        for (i in 0..numberOfRooms) {
            // 방 교차 검사

            if(roomOverlapPrevent()){
                for (x in 0..room_x) {
                    for (y in 0..room_y) {
                        dungeon[dungeon_x + x][dungeon_y + y] = 1
                    }
                }
            }
        }
        return dungeon
    }

    fun roomOverlapPrevent(): Boolean {
        var isExist = true
        DuplicateCheck@ while (isExist) {
            // 25 * 25 크기 중 랜덤으로 한 점을 선택
            val pickedPoint =
                Random.nextInt((dungeon.size - borderSize) * (dungeon.size - borderSize))

            // 선택한 점에 x, y축을 매칭
            dungeon_x = (pickedPoint / 23) + 1
            dungeon_y = (pickedPoint % 23) + 1
            Log.d("dungeon_X", dungeon_x.toString())
            Log.d("dungeon_y", dungeon_y.toString())


            // dungeon이 점이 안찍힌 곳일 경우
            if (dungeon[dungeon_x][dungeon_y] == 0) {

                // 방의 크기는 2 ~ 6
                room_x = minRoomSize + (Random.nextInt(4))
                room_y = minRoomSize + (Random.nextInt(4))

                // 던전의 크기보다 커질경우
                if (dungeon_x + room_x >= dungeon.size - borderSize
                    || dungeon_y + room_y >= dungeon.size - borderSize
                ) {
                    continue@DuplicateCheck
                }

                for (j in 0..room_x) {
                    if (j == 0) {
                        if (dungeon[dungeon_x - 1][dungeon_y] != 0) {
                            continue@DuplicateCheck
                        }
                    }
                    if (dungeon[dungeon_x + j][dungeon_y - 1] != 0) {
                        continue@DuplicateCheck
                    }
                    if (dungeon[dungeon_x + j][dungeon_y] != 0) {
                        continue@DuplicateCheck
                    }

                    for (k in 0..room_y) {
                        if (k == 0) {
                            if (dungeon[dungeon_x][dungeon_y - 1] != 0) {
                                continue@DuplicateCheck
                            }
                        }
                        if (dungeon[dungeon_x][dungeon_y + k] != 0) {
                            continue@DuplicateCheck
                        }

                        if (dungeon[dungeon_x - 1][dungeon_y + k] != 0) {
                            continue@DuplicateCheck
                        }

                        if (dungeon_x + j < dungeon.size) {
                            if (j == room_x && dungeon[dungeon_x + j + 1][dungeon_y + k] != 0) {
                                continue@DuplicateCheck
                            }
                        }
                        if (k == room_y && dungeon_y + k < dungeon.size) {
                            if (dungeon[dungeon_x + j][dungeon_y + k + 1] != 0) {
                                continue@DuplicateCheck
                            }
                        }
                    }
                }
                isExist = false
            }
        }
        return true
    }
}