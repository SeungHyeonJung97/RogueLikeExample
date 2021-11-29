package com.example.roguelikeexample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.roguelikeexample.Settings.loadData
import kotlinx.android.synthetic.main.activity_title.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.yesButton

class TitleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_title)


        btn_new_game.setOnClickListener {
            startActivity<MainActivity>()
            finish()
        }

        btn_load.setOnClickListener {
            if (loadData(this)) {

                Settings.createMap()
                Settings.randomSpawn()

                startActivity<MainActivity>()
                finish()
            } else {
                alert("저장된 데이터가 없습니다. \n게임을 새로 시작하시겠습니까 ?", "저장된 게임 불러오기") {
                    yesButton {
                        startActivity<MainActivity>()
                        finish()
                    }
                    noButton {}
                    isCancelable = false
                }.show()
            }
        }
    }
}