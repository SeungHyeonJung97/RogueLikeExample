package com.example.roguelikeexample

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.example.roguelikeexample.Settings.checkHeroBoundary
import com.example.roguelikeexample.Settings.monsterMove
import com.example.roguelikeexample.databinding.ActivityMainBinding
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var moveDelay: Long = 0
    private var dungeon = Settings.dungeon
    private lateinit var delayHandler: Handler
    lateinit var message: Message
    private lateinit var adapter: MainFrameAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.btnOpenMap.setOnClickListener {
            startActivity<OpenMapActivity>()
        }

        binding.textView.text = resources.getString(R.string.stage, Settings.floor)
        binding.rvMainframe.layoutManager = GridLayoutManager(this, 7)

        Log.d("hero_position", "${Settings.hero_position}")
        adapter = MainFrameAdapter(dungeon)
        binding.rvMainframe.adapter = adapter

        binding.ivHero.bringToFront()

        moveListener()
    }

    private fun moveListener() {
        binding.ivArrowTop.setOnClickListener {
            Settings.hero_position -= dungeon.size
            binding.ivHero.setImageResource(R.drawable.hero_top_move)
            if (Settings.move() != 0) {
                if(checkHeroBoundary()){
                    if(monsterMove()){
                        startBattle()
                    }
                }
                moveDelay = System.currentTimeMillis()
                delayHandler.sendEmptyMessageDelayed(0, 250)
                adapter.notifyDataSetChanged()
                if(Settings.move() == 4){
                    nextStage()
                }
            }
        }
        binding.ivArrowBottom.setOnClickListener {
            Settings.hero_position += dungeon.size
            binding.ivHero.setImageResource(R.drawable.hero_bottom_move)
            if (Settings.move() != 0) {
                if(checkHeroBoundary()){
                    if(monsterMove()){
                        startBattle()
                    }
                }
                moveDelay = System.currentTimeMillis()
                delayHandler.sendEmptyMessageDelayed(1, 250)
                adapter.notifyDataSetChanged()
                if(Settings.move() == 4){
                    nextStage()
                }
            }
        }
        binding.ivArrowLeft.setOnClickListener {
            Settings.hero_position -= 1
            binding.ivHero.setImageResource(R.drawable.hero_left_move)
            if (Settings.move() != 0) {
                if(checkHeroBoundary()){
                    if(monsterMove()){
                        startBattle()
                    }
                }
                moveDelay = System.currentTimeMillis()
                delayHandler.sendEmptyMessageDelayed(2, 250)
                adapter.notifyDataSetChanged()
                if(Settings.move() == 4){
                    nextStage()
                }
            }
        }
        binding.ivArrowRight.setOnClickListener {
            Settings.hero_position += 1
            binding.ivHero.setImageResource(R.drawable.hero_right_move)
            if (Settings.move() != 0) {
                if(checkHeroBoundary()){
                    if(monsterMove()){
                        startBattle()
                    }
                }
                moveDelay = System.currentTimeMillis()
                delayHandler.sendEmptyMessageDelayed(3, 250)
                adapter.notifyDataSetChanged()
                if(Settings.move() == 4){
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

    fun nextStage(){
        startActivity<NextStageActivity>()
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)

        finish()
    }
}