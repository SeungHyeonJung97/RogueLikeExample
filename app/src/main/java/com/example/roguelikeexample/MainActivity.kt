package com.example.roguelikeexample

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.example.roguelikeexample.Settings.advancedPotion
import com.example.roguelikeexample.Settings.armor
import com.example.roguelikeexample.Settings.checkHeroBoundary
import com.example.roguelikeexample.Settings.monsterMove
import com.example.roguelikeexample.Settings.potion
import com.example.roguelikeexample.Settings.saveData
import com.example.roguelikeexample.Settings.weapon
import com.example.roguelikeexample.databinding.ActivityMainBinding
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.yesButton

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var moveDelay: Long = 0
    private var dungeon = Settings.dungeon
    private lateinit var delayHandler: Handler
    private lateinit var adapter: MainFrameAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.btnOpenMap.setOnClickListener {
            startActivity<OpenMapActivity>()
        }

        armor[0] = 10
        weapon[0] = 10
        potion = 2
        advancedPotion = 2

        binding.textView.text = resources.getString(R.string.stage, Settings.floor)
        binding.rvMainframe.layoutManager = GridLayoutManager(this, 7)

        Log.d("hero_position", "${Settings.hero_position}")
        adapter = MainFrameAdapter(dungeon)
        binding.rvMainframe.adapter = adapter

        binding.ivHero.bringToFront()

        moveListener()

        binding.btnSave.setOnClickListener {
            alert("현재 데이터를 저장하시겠습니까 ? \n레벨과 던전의 층수만 저장됩니다 !", "저장") {
                yesButton {
                    saveData(this@MainActivity)
                    Toast.makeText(this@MainActivity,"데이터가 저장되었습니다.",Toast.LENGTH_SHORT).show()
                }
                noButton {}
                isCancelable = false
            }.show()
        }

        binding.btnStatus.setOnClickListener {
            startActivity<StatusActivity>()
        }
    }

    private fun moveListener() {
        binding.ivArrowTop.setOnClickListener {
            Settings.hero_position -= dungeon.size
            binding.ivHero.setImageResource(R.drawable.hero_top_move)
            if (!Settings.move().isZero()) {
                if (checkHeroBoundary()) {
                    if (monsterMove()) {
                        startBattle()
                    }
                }
                moveDelay = System.currentTimeMillis()
                delayHandler.sendEmptyMessageDelayed(0, 250)
                adapter.notifyDataSetChanged()
                if (Settings.move() == 4) {
                    nextStage()
                }
            }
        }
        binding.ivArrowBottom.setOnClickListener {
            Settings.hero_position += dungeon.size
            binding.ivHero.setImageResource(R.drawable.hero_bottom_move)
            if (!Settings.move().isZero()) {
                if (checkHeroBoundary()) {
                    if (monsterMove()) {
                        startBattle()
                    }
                }
                moveDelay = System.currentTimeMillis()
                delayHandler.sendEmptyMessageDelayed(1, 250)
                adapter.notifyDataSetChanged()
                if (Settings.move() == 4) {
                    nextStage()
                }
            }
        }
        binding.ivArrowLeft.setOnClickListener {
            Settings.hero_position -= 1
            binding.ivHero.setImageResource(R.drawable.hero_left_move)
            if (!Settings.move() .isZero()) {
                if (checkHeroBoundary()) {
                    if (monsterMove()) {
                        startBattle()
                    }
                }
                moveDelay = System.currentTimeMillis()
                delayHandler.sendEmptyMessageDelayed(2, 250)
                adapter.notifyDataSetChanged()
                if (Settings.move() == 4) {
                    nextStage()
                }
            }
        }
        binding.ivArrowRight.setOnClickListener {
            Settings.hero_position += 1
            binding.ivHero.setImageResource(R.drawable.hero_right_move)
            if (!Settings.move() .isZero()) {
                if (checkHeroBoundary()) {
                    if (monsterMove()) {
                        startBattle()
                    }
                }
                moveDelay = System.currentTimeMillis()
                delayHandler.sendEmptyMessageDelayed(3, 250)
                adapter.notifyDataSetChanged()
                if (Settings.move() == 4) {
                    nextStage()
                }
            }
        }

        delayHandler = @SuppressLint("HandlerLeak")
        object : Handler() {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                when (msg.what) {
                    0 -> {
                        binding.ivHero.setImageResource(R.drawable.hero_top)
                    }
                    1 -> {
                        binding.ivHero.setImageResource(R.drawable.hero_bottom)
                    }
                    2 -> {
                        binding.ivHero.setImageResource(R.drawable.hero_left)
                    }
                    3 -> {
                        binding.ivHero.setImageResource(R.drawable.hero_right)
                    }
                }
            }
        }
    }

    private fun startBattle() {
        startActivity<BattleActivity>()
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)

        finish()
    }

    fun nextStage() {
        startActivity<NextStageActivity>()
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)

        finish()
    }
}