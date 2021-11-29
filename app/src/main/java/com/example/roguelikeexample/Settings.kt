package com.example.roguelikeexample

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import kotlinx.android.synthetic.main.list_item.view.*
import java.lang.Math.abs
import java.util.*
import kotlin.random.Random

object Settings {
    lateinit var dungeon: Array<IntArray>
    var dungeon_x = 0
    var dungeon_y = 0
    var room_x = 0
    var room_y = 0
    var floor = 1
    val minRoomSize = 2
    val borderSize = 2
    var numberOfRooms = 0
    lateinit var createdRoom: Array<IntArray>
    var hero_position = 0
    var monster_position: Array<Int> = Array(7, { 0 })
    var beforeHeroPosition = 0
    var monsterDistance = 0
    var stair_respawn = false
    var focusIndex = 0

    var HeroLevel = 1
    var HeroMaxHP = 100
    var HeroCurrentHP = HeroMaxHP
    var HeroPower = 23
    var HeroEXP = 0
    var HeroEXP_MAX = 100
    var HeroArmor = 2

    var advancedPotion = 0
    var potion = 0
    var weapon: Array<Int> = Array(4,{0})
    var armor: Array<Int> = Array(4,{0})

    var wornWeapon = 0
    var isWornWeapon: Int? = null
    var wornArmor = 0
    var isWornArmor: Int? = null



    init {
        createMap()
        randomSpawn()
    }

    fun restart(){
        floor = 1
        HeroLevel = 1
        HeroMaxHP = 100
        HeroCurrentHP = HeroMaxHP
        HeroPower = 30
        HeroArmor = 2
        HeroEXP = 0
        HeroEXP_MAX = 100
    }

    fun saveData(context: Context){
        val prefs: SharedPreferences = context.getSharedPreferences(
            "GameData",
            Context.MODE_PRIVATE
        )
        val editor : SharedPreferences.Editor = prefs.edit()
        editor.putInt("level", HeroLevel)
        editor.putInt("floor", floor)
        editor.commit()
    }

    fun loadData(context: Context): Boolean{
        val prefs: SharedPreferences = context.getSharedPreferences(
            "GameData",
            Context.MODE_PRIVATE
        )
        val level = prefs.getInt("level",0)
        val _floor = prefs.getInt("floor",0)

        if(level.isZero() || _floor .isZero()){
            return false
        }else{
            HeroLevel = level
            floor = _floor
        }
        return true
    }
    fun createMap(): Array<IntArray> {
        // dungeon 크기는 25 * 25
        dungeon = Array<IntArray>(25) { IntArray(25) }

        // dungeon 들어갈 방의 갯수는 3 ~ 7
        numberOfRooms = Random.nextInt(5)
        // 방이 생성되었을 때 생성된 방의 좌표를 저장하기 위한 변수

        if (numberOfRooms < 2) numberOfRooms = numberOfRooms + 5
        // Log.d("방 개수", "" + numberOfRooms)
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

            // Log.d("만들어진 방 path ", "${createdRoom[i][0]},${createdRoom[i][1]}")

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

            var comparisonValue = j + 1
            if (comparisonValue > numberOfRooms) comparisonValue = 0
            if (createdRoom[j][0] < createdRoom[comparisonValue][0]) X_DIRECTION = "MINUS"
            x_distance = abs(createdRoom[j][0] - createdRoom[comparisonValue][0])

            if (createdRoom[j][1] < createdRoom[comparisonValue][1]) Y_DIRECTION = "MINUS"
            y_distance = abs(createdRoom[j][1] - createdRoom[comparisonValue][1])

            // Log.d("distance ", "${x_distance},${y_distance}")

            if (!x_distance .isZero()) {
                for (x in 0..x_distance) {
                    if (X_DIRECTION.equals("MINUS")) {
                        dungeon[createdRoom[comparisonValue][0] - x][createdRoom[comparisonValue][1]] =
                            1
                    } else {
                        dungeon[createdRoom[comparisonValue][0] + x][createdRoom[comparisonValue][1]] =
                            1
                    }
                }
            }

            if (!y_distance.isZero()) {
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
            if (dungeon[dungeon_x][dungeon_y] .isZero()) {

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
                    if (j .isZero()) {
                        if (!dungeon[dungeon_x - 1][dungeon_y] .isZero()) {
                            continue@DuplicateCheck
                        }
                    }
                    if (!dungeon[dungeon_x + j][dungeon_y - 1] .isZero()) {
                        continue@DuplicateCheck
                    }
                    if (!dungeon[dungeon_x + j][dungeon_y] .isZero()) {
                        continue@DuplicateCheck
                    }

                    for (k in 0..room_y) {
                        if (k .isZero()) {
                            if (!dungeon[dungeon_x][dungeon_y - 1] .isZero()) {
                                continue@DuplicateCheck
                            }
                        }
                        if (!dungeon[dungeon_x][dungeon_y + k] .isZero()) {
                            continue@DuplicateCheck
                        }

                        if (!dungeon[dungeon_x - 1][dungeon_y + k] .isZero()) {
                            continue@DuplicateCheck
                        }

                        if (dungeon_x + j < dungeon.size) {
                            if (j == room_x && !dungeon[dungeon_x + j + 1][dungeon_y + k].isZero()) {
                                continue@DuplicateCheck
                            }
                        }
                        if (k == room_y && dungeon_y + k < dungeon.size) {
                            if (!dungeon[dungeon_x + j][dungeon_y + k + 1] .isZero()) {
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

    fun randomSpawn() {
        var heroSpawnOk = false
        while (!heroSpawnOk) {
            val randomPosition = Random.nextInt(dungeon.size * dungeon.size)

            val x = randomPosition / 25
            val y = randomPosition % 25

            if (dungeon[x][y] == 1) {
                hero_position = randomPosition
                beforeHeroPosition = hero_position
                dungeon[x][y] = 3
                heroSpawnOk = true
            }
        }

        var stairSpawnOk = false
        while (!stairSpawnOk) {
            val randomPosition = Random.nextInt(dungeon.size * dungeon.size)

            val x = randomPosition / 25
            val y = randomPosition % 25

            if (dungeon[x][y] == 1) {
                dungeon[x][y] = 4
                stairSpawnOk = true
            }
        }

        for (i in 0 until floor) {
            var monsterSpawnOk = false
            while (!monsterSpawnOk) {
                val randomPosition = Random.nextInt(dungeon.size * dungeon.size)

                val x = randomPosition / 25
                val y = randomPosition % 25

                if (dungeon[x][y] == 1) {
                    Log.d("Test", "${floor},${i}")
                    monster_position[i] = randomPosition
                    dungeon[x][y] = 2
                    monsterSpawnOk = true
                }
            }
        }
    }

    fun move(): Int {
        val POSSIBLETOMOVE = 1
        val IMPOSSIBLETOMOVE = 0
        val NEXTSTAGE = 4
        if (beforeHeroPosition != hero_position) {
            val beforeX = beforeHeroPosition / 25
            val beforeY = beforeHeroPosition % 25

            val afterX = hero_position / 25
            val afterY = hero_position % 25

            if (dungeon[afterX][afterY] == 4) {
                return NEXTSTAGE
            }
            if (dungeon[afterX][afterY] != 1 && dungeon[afterX][afterY] != 4) {
                hero_position = beforeHeroPosition
                return IMPOSSIBLETOMOVE
            }

            dungeon[beforeX][beforeY] = 1
            dungeon[afterX][afterY] = 3
        }
        beforeHeroPosition = hero_position

        return POSSIBLETOMOVE
    }

    fun checkHeroBoundary(): Boolean {
        val heroX = hero_position / 25
        val heroY = hero_position % 25

        for (i in (heroX - 3)..(heroX + 3)) {
            for (j in (heroY - 3)..(heroY + 3)) {
                if (i >= dungeon.size || i < 0
                    || j >= dungeon.size || j < 0
                ) {
                    break
                } else if (dungeon[i][j] == 2) {
                    val focusposition = (i * 25) + j
                    focusIndex = monster_position.indexOf(focusposition)
                    Log.d("checkHeroBoundary", "작동")
                    return true
                }
            }
        }
        return false
    }

    fun monsterMove(): Boolean {
        val MOVE_TOP = 0
        val MOVE_BOTTOM = 1
        val MOVE_LEFT = 2
        val MOVE_RIGHT = 3

        var beforeMonsterX = monster_position[focusIndex] / 25
        var beforeMonsterY = monster_position[focusIndex] % 25

        val heroPositionX = hero_position / 25
        val heroPositionY = hero_position % 25

        var afterMonsterX = beforeMonsterX
        var afterMonsterY = beforeMonsterY

        var DIRECTION: String = ""

        // plus -> 몬스터가 아래 있어서 위로 올라가야함
        // minus -> 몬스터가 위에 있어서 아래로 내려가야함
        if (monster_position[focusIndex] < hero_position) {
            DIRECTION = "MINUS"
        } else if (hero_position < monster_position[focusIndex]) {
            DIRECTION = "PLUS"
        }

        when {
            DIRECTION.contains("MINUS") -> {
                Log.d("Hero In", "MINUS")
                if (beforeMonsterX < heroPositionX) {
                    if (!dungeon[beforeMonsterX + 1][beforeMonsterY].isZero()) {
                        afterMonsterX++
                        monsterDistance = MOVE_TOP
                    } else if (heroPositionY > afterMonsterY &&
                        !dungeon[beforeMonsterX][beforeMonsterY + 1].isZero()
                    ) {
                        afterMonsterY++
                        monsterDistance = MOVE_RIGHT
                    } else {
                        afterMonsterY--
                        monsterDistance = MOVE_LEFT
                    }
                } else if (beforeMonsterX == heroPositionX) {
                    if(!dungeon[beforeMonsterX][beforeMonsterY + 1].isZero()){
                        afterMonsterY++
                        monsterDistance = MOVE_RIGHT
                    }else{
                        afterMonsterX++
                        monsterDistance = MOVE_TOP
                    }
                }
                Log.d("HeroPosition", "${afterMonsterY}")
                Log.d("HeroPosition", "${afterMonsterX}")

            }
            DIRECTION.contains("PLUS") -> {
                Log.d("Hero In", "PLUS")
                if (heroPositionX < beforeMonsterX) {
                    if (!dungeon[beforeMonsterX - 1][beforeMonsterY].isZero()) {
                        afterMonsterX--
                        monsterDistance = MOVE_BOTTOM
                    } else if (heroPositionY > afterMonsterY &&
                        !dungeon[beforeMonsterX][beforeMonsterY - 1].isZero()
                    ) {
                        afterMonsterY++
                        monsterDistance = MOVE_RIGHT
                    } else {
                        afterMonsterY--
                        monsterDistance = MOVE_LEFT
                    }
                } else if (beforeMonsterX == heroPositionX) {
                    if (!dungeon[beforeMonsterX][beforeMonsterY - 1].isZero()) {
                        afterMonsterY--
                        monsterDistance = MOVE_LEFT
                    } else {
                        afterMonsterX--
                        monsterDistance = MOVE_BOTTOM
                    }
                }
                Log.d("HeroPosition", "${afterMonsterY}")
                Log.d("HeroPosition", "${afterMonsterX}")
            }
        }


        Log.d(
            "Hero",
            "${heroPositionX},${heroPositionY}, Monster : ${afterMonsterX},${afterMonsterY}"
        )

        monster_position[focusIndex] = (afterMonsterX * 25) + afterMonsterY

        dungeon[beforeMonsterX][beforeMonsterY] = 1

        if (stair_respawn) dungeon[beforeMonsterX][beforeMonsterY] = 4
        stair_respawn = dungeon[afterMonsterX][afterMonsterY] == 4

        dungeon[afterMonsterX][afterMonsterY] = 2


        if (abs(monster_position[focusIndex] - hero_position) == 1
            || abs(monster_position[focusIndex] - hero_position) .isZero()
            || abs(monster_position[focusIndex] - hero_position) == dungeon.size
        ) {
            return true
        }
        return false
    }

    fun statusUpdate(monsterEXP: Int) {
        val x = monster_position[focusIndex] / 25
        val y = monster_position[focusIndex] % 25
        var beforeLevel = HeroLevel

        dungeon[x][y] = 1

        HeroEXP += monsterEXP

        if(HeroEXP >= HeroEXP_MAX){
            HeroEXP = HeroEXP - HeroEXP_MAX
            HeroLevel++
        }
        HeroEXP_MAX = HeroLevel * 70 + 30
        HeroMaxHP = HeroLevel * 30 + 70
        HeroPower = HeroLevel * 8 + 15 + wornWeapon
        HeroArmor = HeroLevel * 2 + wornArmor

        if(beforeLevel != HeroLevel){
            HeroCurrentHP = HeroLevel * 30 + 70
        }

    }
    fun monsterStatus(monsterId: Int): Monster? {
        var monsterName = ""
        var monsterLevel = 0
        var monsterHp = 0
        var monsterPower = 0
        var monsterImage = 0
        var monsterMainImage = 0
        var monsterEXP = 0

        var monster: Monster? = null


        when (monsterId) {
            0 -> {
                monsterName = "지옥의 카서스"
                monsterLevel = 1
                monsterHp = 100
                monsterPower = 10
                monsterImage = R.drawable.monster1_bottom
                monsterMainImage = R.drawable.battle_monster_1
                monsterEXP = 60

                monster = Monster(
                    monsterName,
                    monsterLevel,
                    monsterHp,
                    monsterPower,
                    monsterImage,
                    monsterMainImage,
                    monsterEXP
                )
            }
            1 -> {
                monsterName = "꼬끼오"
                monsterLevel = 2
                monsterHp = 150
                monsterPower = 15
                monsterImage = R.drawable.monster2_bottom
                monsterMainImage = R.drawable.battle_monster_2
                monsterEXP = 90

                monster = Monster(
                    monsterName,
                    monsterLevel,
                    monsterHp,
                    monsterPower,
                    monsterImage,
                    monsterMainImage,
                    monsterEXP
                )
            }
            2 -> {
                monsterName = "꿀잼 티모"
                monsterLevel = 3
                monsterHp = 200
                monsterPower = 30
                monsterImage = R.drawable.monster3_bottom
                monsterMainImage = R.drawable.battle_monster_3
                monsterEXP = 120

                monster = Monster(
                    monsterName,
                    monsterLevel,
                    monsterHp,
                    monsterPower,
                    monsterImage,
                    monsterMainImage,
                    monsterEXP
                )
            }
            3 -> {
                monsterName = "우주 전갈 스카너"
                monsterLevel = 4
                monsterHp = 300
                monsterPower = 25
                monsterImage = R.drawable.monster4_bottom
                monsterMainImage = R.drawable.battle_monster_4
                monsterEXP = 150

                monster = Monster(
                    monsterName,
                    monsterLevel,
                    monsterHp,
                    monsterPower,
                    monsterImage,
                    monsterMainImage,
                    monsterEXP
                )
            }
            4 -> {
                monsterName = "카시오페아"
                monsterLevel = 5
                monsterHp = 400
                monsterPower = 35
                monsterImage = R.drawable.monster5_bottom
                monsterMainImage = R.drawable.battle_monster_5
                monsterEXP = 180

                monster = Monster(
                    monsterName,
                    monsterLevel,
                    monsterHp,
                    monsterPower,
                    monsterImage,
                    monsterMainImage,
                    monsterEXP
                )
            }
            5 -> {
                monsterName = "엘리스"
                monsterLevel = 6
                monsterHp = 400
                monsterPower = 45
                monsterImage = R.drawable.monster6_bottom
                monsterMainImage = R.drawable.battle_monster_6
                monsterEXP = 210

                monster = Monster(
                    monsterName,
                    monsterLevel,
                    monsterHp,
                    monsterPower,
                    monsterImage,
                    monsterMainImage,
                    monsterEXP
                )
            }
            6 -> {
                monsterName = "벨코즈"
                monsterLevel = 7
                monsterHp = 450
                monsterPower = 50
                monsterImage = R.drawable.monster7_bottom
                monsterMainImage = R.drawable.battle_monster_7
                monsterEXP = 240

                monster = Monster(
                    monsterName,
                    monsterLevel,
                    monsterHp,
                    monsterPower,
                    monsterImage,
                    monsterMainImage,
                    monsterEXP
                )
            }
        }

        return monster
    }

    fun getItem(): Bag {
        return Bag(advancedPotion, potion, weapon, armor)
    }

    fun haveItem(): ArrayList<String> {
        val itemList = ArrayList<String>()
        val data = getItem()

        if(data.advancedPotion.isHave() > 0){
            itemList.add("advancedPotion")
        }
        if(data.potion.isHave() > 0){
            itemList.add("potion")
        }
        for (i in weapon.indices) {
            if (!weapon[i].isHave().isZero()) {
                when (i) {
                    0 -> {
                       itemList.add("weapon${i}")
                    }
                    1 -> {
                        itemList.add("weapon${i}")
                    }
                    2 -> {
                        itemList.add("weapon${i}")
                    }
                    3 -> {
                        itemList.add("weapon${i}")
                    }
                }
            }
        }
        for (i in armor.indices) {
            if (!armor[i].isHave().isZero()) {
                when (i) {
                    0 -> {
                        itemList.add("armor${i}")
                    }
                    1 -> {
                        itemList.add("armor${i}")
                    }
                    2 -> {
                        itemList.add("armor${i}")
                    }
                    3 -> {
                        itemList.add("armor${i}")
                    }
                }
            }
        }
        return itemList
    }

    data class Monster(
        var monsterName: String, val monsterLevel: Int, val monsterMaxHp: Int, val monsterPower: Int,
        val monsterImage: Int, val monsterMainImage: Int, val monsterEXP: Int
    )

    data class Bag(
        var advancedPotion: Int,
        var potion: Int,
        var weapon: Array<Int>,
        var armor: Array<Int>,
    )
}