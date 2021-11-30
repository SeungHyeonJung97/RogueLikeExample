package com.example.roguelikeexample

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_next_stage.*
import org.jetbrains.anko.startActivity

class NextStageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_next_stage)

        Settings.floor += 1
        if(Settings.floor == Settings.last_floor){
            textView2.text = "마지막 보스에게 도전하시겠습니까 ?"
            button.text = "도전하기"
        }else{
            textView2.text = resources.getString(R.string.stage,Settings.floor)
            Settings.createMap()
            Settings.randomSpawn()
        }

        button.setOnClickListener{
            if(Settings.floor == Settings.last_floor){
                startActivity<BattleActivity>()
                finish()
            }else{
                startActivity<MainActivity>()
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out)

                finish()
            }

        }
    }
}