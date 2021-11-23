package com.example.roguelikeexample

import android.util.Log
import java.lang.Math.abs
import java.lang.Math.random
import java.util.*
import kotlin.random.Random

object Utils {
    lateinit var dungeon: Array<IntArray>
    var dungeon_x = 0
    var dungeon_y = 0
    var room_x = 0
    var room_y = 0
    val minRoomSize = 2
    val borderSize = 2
    var numberOfRooms = 0
    lateinit var createdRoom: Array<IntArray>
    var hero_position = 0

    init {
        createMap()
        randomSpawn()
    }

    fun createMap(): Array<IntArray> {
        // dungeon 크기는 25 * 25
        dungeon = Array<IntArray>(25) { IntArray(25) }

        // dungeon 들어갈 방의 갯수는 3 ~ 7
        numberOfRooms = Random.nextInt(5)
        // 방이 생성되었을 때 생성된 방의 좌표를 저장하기 위한 변수

        if (numberOfRooms < 2) numberOfRooms = numberOfRooms + 5
        Log.d("방 개수", "" + numberOfRooms);
        createdRoom = Array<IntArray>(numberOfRooms + 1) { IntArray(2) }

        for (i in 0..numberOfRooms) {

            // 방 교차 / 중복 검사
            if (roomOverlapPrevent()) {
                for (x in 0..room_x) {
                    for (y in 0..room_y) {
                        dungeon[dungeon_x + x][dungeon_y + y] = 1
                    }

                }
            }

            createdRoom[i][0] = dungeon_x
            createdRoom[i][1] = dungeon_y

            Log.d("만들어진 방 path ", "${createdRoom[i][0]},${createdRoom[i][1]}")

        }

        createPath()

        return dungeon
    }

    private fun createPath() {
        for (j in 0..numberOfRooms) {
            var X_DIRECTION: String = ""
            var Y_DIRECTION: String = ""
            val x_distance: Int
            val y_distance: Int

            var comparisonValue = j+1
            if(comparisonValue > numberOfRooms) comparisonValue = 0
            if (createdRoom[j][0] < createdRoom[comparisonValue][0]) X_DIRECTION = "MINUS"
            x_distance = abs(createdRoom[j][0] - createdRoom[comparisonValue][0])

            if (createdRoom[j][1] < createdRoom[comparisonValue][1]) Y_DIRECTION = "MINUS"
            y_distance = abs(createdRoom[j][1] - createdRoom[comparisonValue][1])

            Log.d("distance ", "${x_distance},${y_distance}")

            if (x_distance != 0) {
                for (x in 0..x_distance) {
                    if (X_DIRECTION.equals("MINUS")) {
                        dungeon[createdRoom[comparisonValue][0] - x][createdRoom[comparisonValue][1]] = 1
                    } else {
                        dungeon[createdRoom[comparisonValue][0] + x][createdRoom[comparisonValue][1]] = 1
                    }
                }
            }

            if (y_distance != 0) {
                for (y in 0..y_distance) {
                    if (Y_DIRECTION.equals("MINUS")) {
                        dungeon[createdRoom[j][0]][createdRoom[j][1] + y] = 1
                    } else {
                        dungeon[createdRoom[j][0]][createdRoom[j][1] - y] = 1
                    }
                }
            }

        }
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


            // 방이 아직 생성이 안된 곳일 경우
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

    fun randomSpawn(){
        var isOk = false
        while(!isOk){
            val randomPosition = Random.nextInt(dungeon.size * dungeon.size)
            Log.d("size","${dungeon.size}")

            val x = randomPosition / 25
            val y = randomPosition % 25

            if(dungeon[x][y] != 0){
                hero_position = randomPosition
                dungeon[x][y] = 3
                isOk = true
            }
        }
    }
}