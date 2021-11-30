package com.example.roguelikeexample

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roguelikeexample.Settings.HeroArmor
import com.example.roguelikeexample.Settings.HeroCurrentHP
import com.example.roguelikeexample.Settings.HeroEXP
import com.example.roguelikeexample.Settings.HeroEXP_MAX
import com.example.roguelikeexample.Settings.HeroLevel
import com.example.roguelikeexample.Settings.HeroMaxHP
import com.example.roguelikeexample.Settings.HeroPower
import com.example.roguelikeexample.Settings.armor
import com.example.roguelikeexample.Settings.haveItem
import com.example.roguelikeexample.Settings.isWornArmor
import com.example.roguelikeexample.Settings.isWornWeapon
import com.example.roguelikeexample.Settings.weapon
import com.example.roguelikeexample.Settings.wornArmor
import com.example.roguelikeexample.Settings.wornWeapon
import kotlinx.android.synthetic.main.activity_status.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.yesButton

class StatusActivity : AppCompatActivity() {
    var adapter = BagAdapter(this)
    private lateinit var changeHandler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_status)

        Log.d("bag", "${haveItem()}")

        rv_bag.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv_bag.adapter = adapter

        tv_status_level.text = resources.getString(R.string.status_level, HeroLevel)
        tv_status_hp.text = resources.getString(R.string.status_hp, HeroCurrentHP, HeroMaxHP)
        tv_status_weapon.text = resources.getString(R.string.status_power, HeroPower)
        tv_status_armor.text = resources.getString(R.string.status_armor, HeroArmor)
        tv_status_exp.text = resources.getString(R.string.status_exp, HeroEXP, HeroEXP_MAX)

        if (isWornWeapon != null) {
            when (wornWeapon) {
                5 -> {
                    iv_weapon.setImageResource(R.drawable.weapon1)
                }
                10 -> {
                    iv_weapon.setImageResource(R.drawable.weapon2)
                }
                25 -> {
                    iv_weapon.setImageResource(R.drawable.weapon3)
                }
                50 -> {
                    iv_weapon.setImageResource(R.drawable.weapon4)
                }
            }
            iv_weapon.isClickable = true
        }

        iv_weapon.setOnClickListener {
            if (iv_weapon.isClickable) {

                alert("장비를 해제하시겠습니까 ?", "착용 해제") {
                    yesButton {
                        Toast.makeText(
                            this@StatusActivity,
                            "착용하신 아이템이 해제되었습니다.",
                            Toast.LENGTH_SHORT
                        ).show()
                        isWornWeapon = null
                        iv_weapon.isClickable = false
                        when (wornWeapon) {
                            5 -> {
                                weapon[0]++
                            }
                            10 -> {
                                weapon[1]++
                            }
                            25 -> {
                                weapon[2]++
                            }
                            50 -> {
                                weapon[3]++
                            }
                        }
                        HeroPower -= wornWeapon
                        wornWeapon = 0
                        tv_status_weapon.text = resources.getString(R.string.status_power, HeroPower)
                        iv_weapon.setImageResource(R.drawable.empty_image)
                        changeHandler.sendEmptyMessage(0)
                    }
                    noButton {}
                    isCancelable = false
                }.show()
            }
        }



        if (isWornArmor != null) {
            when (wornArmor) {
                3 -> {
                    iv_armor.setImageResource(R.drawable.armor1)
                }
                5 -> {
                    iv_armor.setImageResource(R.drawable.armor1)
                }
                10 -> {
                    iv_armor.setImageResource(R.drawable.armor1)
                }
                18 -> {
                    iv_armor.setImageResource(R.drawable.armor1)
                }
            }
            iv_armor.isClickable = true
        }

        iv_armor.setOnClickListener {
            if (iv_armor.isClickable) {
                alert("장비를 해제하시겠습니까 ?", "착용 해제") {
                    yesButton {
                        Toast.makeText(
                            this@StatusActivity,
                            "착용하신 아이템이 해제되었습니다.",
                            Toast.LENGTH_SHORT
                        ).show()
                        isWornArmor = null
                        iv_armor.isClickable = false
                        when (wornArmor) {
                            3 -> {
                                armor[0]++
                            }
                            5 -> {
                                armor[1]++
                            }
                            10 -> {
                                armor[2]++
                            }
                            18 -> {
                                armor[3]++
                            }
                        }
                        iv_armor.setImageResource(R.drawable.empty_image)
                        wornArmor = 0
                        HeroArmor -= wornArmor
                        tv_status_armor.text = resources.getString(R.string.status_armor, HeroArmor)
                        changeHandler.sendEmptyMessage(0)

                    }
                    noButton {}
                    isCancelable = false
                }.show()
            }
        }


        btn_finish.setOnClickListener {
            startActivity<MainActivity>()
            finish()
        }

        changeHandler = @SuppressLint("HandlerLeak")
            object : Handler() {
                override fun handleMessage(msg: Message) {
                    super.handleMessage(msg)
                    adapter.setData(haveItem())
                }
            }
    }
}