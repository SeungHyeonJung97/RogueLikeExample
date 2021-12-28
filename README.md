# 🎮  RogueLike
<p align="center">
  <img src="./img/2.png"  width="150"> &nbsp;
  <img src="./img/3.png"  width="150"> &nbsp;
  <img src="./img/4.png"  width="150"> &nbsp;
  <img src="./img/6.png"  width="150">
</p>
<br>

> 게임 장르 중 하나인 로그라이크를 기반으로 만들어본 게임

### 💭 Introduction
- Kotlin을 공부하고, 복습 겸 처음으로 만들어본 앱으로, Kotlin에 대해 많이 익숙해지는 계기가 되었던 프로젝트이다.
  
### 📖 Detail

- #### 🗺️ Map Algorithm
 맵은 25 * 25 크기로 매 층마다 무작위로 생성되며, 맵에서 각 방의 크기는 2~5 * 2~5로 랜덤하게 선정되어, 개수는 3~7개 중 랜덤으로 생성된다.
```Kotlin
        // dungeon 크기는 25 * 25
        dungeon = Array(25) { IntArray(25) }

        // dungeon 들어갈 방의 갯수는 3 ~ 7
        numberOfRooms = Random.nextInt(5)

        if (numberOfRooms < 2) numberOfRooms = numberOfRooms + 5
        createdRoom = Array(numberOfRooms + 1) { IntArray(2) }

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
          }
```
<br>
맵에서 생성된 방은 교차되거나 중복되지 않게 이미 생성된 방과의 거리가 1 이하라면 다시 그리도록 알고리즘을 구성하였다.

``` Kotlin
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
```

- Game내에서 Map을 누를 경우, Map정보를 확인할 수 있다.
- 캐릭터가 이동하다가 몬스터를 마주치게 될 경우 몬스터는 캐릭터를 쫓아오게 된다.
- 전투에서 승리할 경우, 경험치와 아이템을 획득해 아이템을 강화시키면서 캐릭터를 육성하여 최종 보스를 잡으면 게임이 끝이난다.


#### Languages
<p>
  <img src="https://img.shields.io/badge/Android-3DDC84?style=flat-square&logo=Android&logoColor=white"/>
  <img src="https://img.shields.io/badge/Kotlin-0095D5?style=flat-square&logo=Kotlin&logoColor=white"/>
</p>
