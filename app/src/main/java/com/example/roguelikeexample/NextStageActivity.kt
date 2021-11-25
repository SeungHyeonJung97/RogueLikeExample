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
        textView2.text = resources.getString(R.string.stage,Settings.floor)

        Settings.createMap()
        Settings.randomSpawn()

        button.setOnClickListener{
            startActivity<MainActivity>()
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)

            finish()
        }
    }
}