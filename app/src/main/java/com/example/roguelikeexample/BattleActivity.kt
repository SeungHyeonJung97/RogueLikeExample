package com.example.roguelikeexample

import android.animation.Animator
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.ContextThemeWrapper
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.roguelikeexample.Settings.HeroCurrentHP
import com.example.roguelikeexample.Settings.HeroLevel
import com.example.roguelikeexample.Settings.HeroMaxHP
import com.example.roguelikeexample.Settings.HeroPower
import com.example.roguelikeexample.Settings.statusUpdate
import com.example.roguelikeexample.databinding.ActivityBattleBinding
import kotlinx.android.synthetic.main.activity_battle.*
import org.jetbrains.anko.*
import org.jetbrains.anko.custom.style

class BattleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBattleBinding
    val monster = Settings.monsterStatus(Settings.focusIndex)!!
    var monsterCurrentHP = monster.monsterMaxHp
    private lateinit var battleHandler: Handler
    val MONSTER = 0
    val HERO = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_battle)

        skillByLevelSettings()
        battleSettings()
        listenerSettings()

        battleHandler = @SuppressLint("HandlerLeak")
        object : Handler() {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                when (msg.what) {
                    MONSTER -> {
                        if (monsterCurrentHP >= 0) {
                            monsterCurrentHP -= msg.arg1
                            if (monsterCurrentHP <= 0) {
                                monsterCurrentHP = 0
                                statusUpdate(monster.monsterEXP)

                                alert("획득한 경험치 : ${monster.monsterEXP}exp", "승리 !") {
                                    yesButton {
                                        startActivity<MainActivity>()
                                        finish()
                                    }
                                    isCancelable = false
                                }.show()
                            }
                            binding.pbMonsterHp.incrementProgressBy(-msg.arg1)
                            binding.tvMonsterHp.text =
                                resources.getString(
                                    R.string.hp,
                                    monsterCurrentHP,
                                    monster.monsterMaxHp
                                )
                        }
                    }
                    HERO -> {
                        binding.heroHit.visibility = View.VISIBLE
                        binding.heroHit.playAnimation()
                        HeroCurrentHP -= msg.arg1
                        binding.pbHeroHp.incrementProgressBy(-msg.arg1)
                        if (HeroCurrentHP <= 0) {
                            HeroCurrentHP = 0
                            alert("더 강해져서 도전하세요 ..", "패배 !") {
                                yesButton {
                                    startActivity<MainActivity>()
                                    finish()
                                }
                                isCancelable = false
                            }.show()

                        }
                        binding.tvHeroHp.text =
                            resources.getString(R.string.hp, HeroCurrentHP, HeroMaxHP)
                    }
                }


            }
        }
    }

    private fun listenerSettings() {

        binding.btnAttack.setOnClickListener {
            val message_monster = Message.obtain()
            message_monster.what = MONSTER
            message_monster.arg1 = HeroPower
            monster_hit.visibility = View.VISIBLE
            monster_hit.playAnimation()

            battleHandler.sendMessage(message_monster)

            val message_hero = Message.obtain()
            message_hero.what = HERO
            message_hero.arg1 = monster.monsterPower
            battleHandler.sendMessageDelayed(message_hero, 2000)
        }

        binding.btnSkill1.setOnClickListener {
            val message_monster = Message.obtain()
            message_monster.what = MONSTER
            message_monster.arg1 = HeroPower
            monster_hit.visibility = View.VISIBLE
            monster_hit.setAnimation(R.raw.counter)
            monster_hit.playAnimation()

            battleHandler.sendMessage(message_monster)

            val message_hero = Message.obtain()
            message_hero.what = HERO
            message_hero.arg1 = monster.monsterPower
            battleHandler.sendMessageDelayed(message_hero, 2000)
        }
        binding.btnSkill2.setOnClickListener {
            val message_monster = Message.obtain()
            message_monster.what = MONSTER
            message_monster.arg1 = HeroPower
            monster_hit.visibility = View.VISIBLE
            monster_hit.setAnimation(R.raw.firestorm)
            monster_hit.playAnimation()

            battleHandler.sendMessage(message_monster)

            val message_hero = Message.obtain()
            message_hero.what = HERO
            message_hero.arg1 = monster.monsterPower
            battleHandler.sendMessageDelayed(message_hero, 2000)
        }
        binding.btnSkill3.setOnClickListener {
            val message_monster = Message.obtain()
            binding.btnAttack.isClickable = false
            message_monster.what = MONSTER
            message_monster.arg1 = HeroPower
            monster_hit.visibility = View.VISIBLE
            monster_hit.setAnimation(R.raw.thunder)
            monster_hit.playAnimation()

            battleHandler.sendMessage(message_monster)

            val message_hero = Message.obtain()
            message_hero.what = HERO
            message_hero.arg1 = monster.monsterPower
            battleHandler.sendMessageDelayed(message_hero, 2000)
        }


        monster_hit.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {
                binding.btnAttack.isClickable = false
                binding.btnSkill1.isClickable = false
                binding.btnSkill2.isClickable = false
                binding.btnSkill3.isClickable = false
            }

            override fun onAnimationEnd(animation: Animator?) {
                monster_hit.visibility = View.GONE
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationRepeat(animation: Animator?) {
            }

        })

        hero_hit.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {

            }

            override fun onAnimationEnd(animation: Animator?) {
                hero_hit.visibility = View.GONE
                skillByLevelSettings()
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationRepeat(animation: Animator?) {
            }

        })


    }

    private fun skillByLevelSettings() {
        binding.btnAttack.isClickable = true
        binding.btnSkill1.isClickable = false
        binding.btnSkill2.isClickable = false
        binding.btnSkill3.isClickable = false

        if (HeroLevel >= 5) {
            binding.btnSkill1.isClickable = true
            binding.btnSkill1.text = "카운터"
            binding.btnSkill1.setBackgroundResource(R.color.teal_200)
        }
        if (HeroLevel >= 7) {
            binding.btnSkill2.isClickable = true
            binding.btnSkill2.text = "파이어 스톰"
            binding.btnSkill2.setBackgroundResource(R.color.red)
        }
        if (HeroLevel > 7) {
            binding.btnSkill3.isClickable = true
            binding.btnSkill3.text = "번개"
            binding.btnSkill3.setBackgroundResource(R.color.yellow)
        }
    }

    private fun battleSettings() {

        binding.ivMonsterMain.setImageResource(monster.monsterMainImage)
        binding.ivMonster.setImageResource(monster.monsterImage)
        binding.tvMonsterHp.text =
            resources.getString(R.string.hp, monsterCurrentHP, monster.monsterMaxHp)
        binding.tvMonsterName.text = monster.monsterName
        binding.tvMonsterLevel.text = resources.getString(R.string.level, monster.monsterLevel)
        binding.pbMonsterHp.max = monster.monsterMaxHp
        binding.pbMonsterHp.progress = monster.monsterMaxHp


        binding.tvHeroHp.text = resources.getString(R.string.hp, HeroCurrentHP, HeroMaxHP)
        binding.tvHeroLevel.text = resources.getString(R.string.level, HeroLevel)
        binding.pbHeroHp.max = HeroMaxHP
        binding.pbHeroHp.progress = HeroCurrentHP
    }
}