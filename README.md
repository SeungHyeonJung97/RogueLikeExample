# ๐ฎ  RogueLike
<p align="center">
  <img src="./img/2.png"  width="150"> &nbsp;
  <img src="./img/3.png"  width="150"> &nbsp;
  <img src="./img/4.png"  width="150"> &nbsp;
  <img src="./img/6.png"  width="150">
</p>
<br>

> ๊ฒ์ ์ฅ๋ฅด ์ค ํ๋์ธ ๋ก๊ทธ๋ผ์ดํฌ๋ฅผ ๊ธฐ๋ฐ์ผ๋ก ๋ง๋ค์ด๋ณธ ๊ฒ์

### ๐ญ Introduction
- Kotlin์ ๊ณต๋ถํ๊ณ , ๋ณต์ต ๊ฒธ ์ฒ์์ผ๋ก ๋ง๋ค์ด๋ณธ ์ฑ์ผ๋ก, Kotlin์ ๋ํด ๋ง์ด ์ต์ํด์ง๋ ๊ณ๊ธฐ๊ฐ ๋์๋ ํ๋ก์ ํธ์ด๋ค.
  
### ๐ Detail
- Game๋ด์์ Map์ ๋๋ฅผ ๊ฒฝ์ฐ, Map์ ๋ณด๋ฅผ ํ์ธํ  ์ ์๋ค.
- ์บ๋ฆญํฐ๊ฐ ์ด๋ํ๋ค๊ฐ ๋ชฌ์คํฐ๋ฅผ ๋ง์ฃผ์น๊ฒ ๋  ๊ฒฝ์ฐ ๋ชฌ์คํฐ๋ ์บ๋ฆญํฐ๋ฅผ ์ซ์์ค๊ฒ ๋๋ค.
- ์ ํฌ์์ ์น๋ฆฌํ  ๊ฒฝ์ฐ, ๊ฒฝํ์น์ ์์ดํ์ ํ๋ํด ์์ดํ์ ๊ฐํ์ํค๋ฉด์ ์บ๋ฆญํฐ๋ฅผ ์ก์ฑํ์ฌ ์ต์ข ๋ณด์ค๋ฅผ ์ก์ผ๋ฉด ๊ฒ์์ด ๋์ด๋๋ค.


#### ๐บ๏ธ Map Algorithm
 ๋งต์ 25 * 25 ํฌ๊ธฐ๋ก ๋งค ์ธต๋ง๋ค ๋ฌด์์๋ก ์์ฑ๋๋ฉฐ, ๋งต์์ ๊ฐ ๋ฐฉ์ ํฌ๊ธฐ๋ 2~5 * 2~5๋ก ๋๋คํ๊ฒ ์ ์ ๋์ด, ๊ฐ์๋ 3~7๊ฐ ์ค ๋๋ค์ผ๋ก ์์ฑ๋๋ค.
```Kotlin
        // dungeon ํฌ๊ธฐ๋ 25 * 25
        dungeon = Array(25) { IntArray(25) }

        // dungeon ๋ค์ด๊ฐ ๋ฐฉ์ ๊ฐฏ์๋ 3 ~ 7
        numberOfRooms = Random.nextInt(5)

        if (numberOfRooms < 2) numberOfRooms = numberOfRooms + 5
        createdRoom = Array(numberOfRooms + 1) { IntArray(2) }

        for (i in 0..numberOfRooms) {

            // ๋ฐฉ ๊ต์ฐจ / ์ค๋ณต ๊ฒ์ฌ
            if (roomOverlapPrevent()) {
                for (x in 0..room_x) {
                    for (y in 0..room_y) {
                        dungeon[dungeon_x + x][dungeon_y + y] = 1
                    }

                }
            }

            createdRoom[i][0] = dungeon_x
            createdRoom[i][1] = dungeon_y
          }
```
<br>
๋งต์์ ์์ฑ๋ ๋ฐฉ์ ๊ต์ฐจ๋๊ฑฐ๋ ์ค๋ณต๋์ง ์๊ฒ ์ด๋ฏธ ์์ฑ๋ ๋ฐฉ๊ณผ์ ๊ฑฐ๋ฆฌ๊ฐ 1 ์ดํ๋ผ๋ฉด ๋ค์ ๊ทธ๋ฆฌ๋๋ก ์๊ณ ๋ฆฌ์ฆ์ ๊ตฌ์ฑํ์๋ค.

``` Kotlin
fun roomOverlapPrevent(): Boolean {
        var isExist = true
        DuplicateCheck@ while (isExist) {
            // 25 * 25 ํฌ๊ธฐ ์ค ๋๋ค์ผ๋ก ํ ์ ์ ์ ํ
            val pickedPoint =
                Random.nextInt((dungeon.size - borderSize) * (dungeon.size - borderSize))

            // ์ ํํ ์ ์ x, y์ถ์ ๋งค์นญ
            dungeon_x = (pickedPoint / 23) + 1
            dungeon_y = (pickedPoint % 23) + 1


            // ๋ฐฉ์ด ์์ง ์์ฑ์ด ์๋ ๊ณณ์ผ ๊ฒฝ์ฐ
            if (dungeon[dungeon_x][dungeon_y] .isZero()) {

                // ๋ฐฉ์ ํฌ๊ธฐ๋ 2 ~ 6
                room_x = minRoomSize + (Random.nextInt(4))
                room_y = minRoomSize + (Random.nextInt(4))

                // ๋์ ์ ํฌ๊ธฐ๋ณด๋ค ์ปค์ง๊ฒฝ์ฐ
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
```

- #### ๐ Monster Algorithm

์บ๋ฆญํฐ๊ฐ ์์ง์ด๋ค๊ฐ, ๋ชฌ์คํฐ๊ฐ ๋งต์ ๋ณด์ผ ๊ฒฝ์ฐ, ๋ชฌ์คํฐ๊ฐ ์์ง์ด๊ธฐ ์์ํ๋ค.
```Kotlin
  fun checkHeroBoundary(): Boolean {
        val heroX = hero_position / 25
        val heroY = hero_position % 25

        for (i in (heroX - 3)..(heroX + 3)) {
            for (j in (heroY - 3)..(heroY + 3)) {
                if (i >= dungeon.size || i < 0
                    || j >= dungeon.size || j < 0
                ) {
                    break
                } else if (dungeon[i][j] == MONSTER) {
                    val focusposition = (i * 25) + j
                    focusIndex = monster_position.indexOf(focusposition)
                    return true
                }
            }
        }
        return false
    }
```

<br>

๋ชฌ์คํฐ๋ ์ฐ์ ์ ์ผ๋ก ์บ๋ฆญํฐ์ x์ถ ๊ฐ์ ์ซ์๊ฐ๋ฉฐ, x์ถ ๊ฐ์ด ๋๊ฐ์์ง ๊ฒฝ์ฐ y์ถ์ผ๋ก ์ด๋์ ์์ํ๋ค.
์ด ๋, ์ด๋ํ๋ค๊ฐ ๊ธธ์ด ๋งํ์์ ๊ฒฝ์ฐ ์บ๋ฆญํฐ์ ๊ฐ์ฅ ๊ฐ๊น์์ง ์ ์๋ ์ฐจ์ ์ฑ์ ์ ํํ๋๋ก ํ๋ค.
```Kotlin
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

        // plus -> ๋ชฌ์คํฐ๊ฐ ์๋ ์์ด์ ์๋ก ์ฌ๋ผ๊ฐ์ผํจ
        // minus -> ๋ชฌ์คํฐ๊ฐ ์์ ์์ด์ ์๋๋ก ๋ด๋ ค๊ฐ์ผํจ
        if (monster_position[focusIndex] < hero_position) {
            DIRECTION = "MINUS"
        } else if (hero_position < monster_position[focusIndex]) {
            DIRECTION = "PLUS"
        }

        when {
            DIRECTION.contains("MINUS") -> {
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

            }
            DIRECTION.contains("PLUS") -> {
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
            }
        }

        monster_position[focusIndex] = (afterMonsterX * 25) + afterMonsterY

        dungeon[beforeMonsterX][beforeMonsterY] = 1

        if (stair_respawn) dungeon[beforeMonsterX][beforeMonsterY] = STAIR
        stair_respawn = dungeon[afterMonsterX][afterMonsterY] == STAIR

        dungeon[afterMonsterX][afterMonsterY] = MONSTER


        if (abs(monster_position[focusIndex] - hero_position) == 1
            || abs(monster_position[focusIndex] - hero_position) .isZero()
            || abs(monster_position[focusIndex] - hero_position) == dungeon.size
        ) {
            return true
        }
        return false
    }
```


#### โ Languages
<p>
  <img src="https://img.shields.io/badge/Android-3DDC84?style=flat-square&logo=Android&logoColor=white"/>
  <img src="https://img.shields.io/badge/Kotlin-0095D5?style=flat-square&logo=Kotlin&logoColor=white"/>
</p>
