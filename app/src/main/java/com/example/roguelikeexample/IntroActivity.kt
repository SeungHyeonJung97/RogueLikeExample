package com.example.roguelikeexample

import android.animation.Animator
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_battle.*
import kotlinx.android.synthetic.main.activity_intro.*
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity

class IntroActivity: AppCompatActivity() {
    var repeat = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)


        lottie_intro.repeatCount = 2
        lottie_intro.playAnimation()

        lottie_intro.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                startActivity<TitleActivity>()
                finish()
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationRepeat(animation: Animator?) {
                textView3.text = resources.getString(R.string.tip2)
            }

        })
    }
}