package com.google.samples.propertyanimation

import android.animation.*
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.LinearInterpolator
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView

class MainActivity : AppCompatActivity() {
    private lateinit var star: ImageView
    private lateinit var rotateButton: Button
    private lateinit var translateButton: Button
    private lateinit var scaleButton: Button
    private lateinit var fadeButton: Button
    private lateinit var colorizeButton: Button
    private lateinit var showerButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        star = findViewById(R.id.star)
        rotateButton = findViewById(R.id.rotateButton)
        translateButton = findViewById(R.id.translateButton)
        scaleButton = findViewById(R.id.scaleButton)
        fadeButton = findViewById(R.id.fadeButton)
        colorizeButton = findViewById(R.id.colorizeButton)
        showerButton = findViewById(R.id.showerButton)

        rotateButton.setOnClickListener {
            rotater()
        }

        translateButton.setOnClickListener {
            translater()
        }

        scaleButton.setOnClickListener {
            scaler()
        }

        fadeButton.setOnClickListener {
            fader()
        }

        colorizeButton.setOnClickListener {
            colorizer()
        }

        showerButton.setOnClickListener {
            shower()
        }
    }

    private fun rotater() {
        val animation = ObjectAnimator.ofFloat(star, View.ROTATION, -360f, 0f)
        animation.apply {
            duration = 1000
            disableViewDuringAnimation(rotateButton)
            start()
        }
    }

    private fun translater() {
        val animator = ObjectAnimator.ofFloat(star, View.TRANSLATION_X, 200f)
        animator.apply {
            repeatCount = 1
            repeatMode = ObjectAnimator.REVERSE
            disableViewDuringAnimation(translateButton)
            start()
        }
    }

    private fun scaler() {
        val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 4f)
        val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 4f)

        val animator = ObjectAnimator.ofPropertyValuesHolder(star, scaleX, scaleY)
        animator.apply {
            repeatCount = 1
            repeatMode = ObjectAnimator.REVERSE
            disableViewDuringAnimation(scaleButton)
            start()
        }
    }

    private fun fader() {
        val animator = ObjectAnimator.ofFloat(star, View.ALPHA, 0f)
        animator.apply {
            repeatCount = 1
            repeatMode = ObjectAnimator.REVERSE
            disableViewDuringAnimation(fadeButton)
            start()
        }
    }

    private fun colorizer() {
//        var animator = ObjectAnimator.ofInt(star.parent, "backgroundColor", Color.BLACK, Color.RED).start()
        val animator = ObjectAnimator.ofArgb(star.parent, "backgroundColor", Color.BLACK, Color.RED)
        animator.apply {
            duration = 500
            repeatCount = 1
            repeatMode = ObjectAnimator.REVERSE
            disableViewDuringAnimation(colorizeButton)
            start()
        }
    }

    private fun shower() {
        val container = star.parent as ViewGroup
        val containerW = container.width
        val containerH = container.height
        var starW: Float = star.width.toFloat()
        var starH: Float = star.height.toFloat()

        val newStar = AppCompatImageView(this)
        newStar.apply {
            scaleX = Math.random().toFloat() * 1.5f + .1f
            scaleY = scaleX
            setImageResource(R.drawable.ic_star)
            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
            )
        }

        starH *= newStar.scaleX
        starW *= newStar.scaleY

        newStar.translationX = Math.random().toFloat() * containerW - starW / 2

        container.addView(newStar)

        val mover = ObjectAnimator.ofFloat(newStar, View.TRANSLATION_Y, -starH, containerH + starH)
        mover.interpolator = AccelerateInterpolator(1f)

        val rotator =
            ObjectAnimator.ofFloat(newStar, View.ROTATION, (Math.random() * 1080).toFloat())
        rotator.interpolator = LinearInterpolator()

        val animatorSet = AnimatorSet()
        animatorSet.apply {
            playTogether(mover, rotator)
            duration = (Math.random() * 1500 + 500).toLong()
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    container.removeView(newStar)
                }
            })
            start()
        }
    }

    private fun ObjectAnimator.disableViewDuringAnimation(view: View) {
        addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator?) {
                view.isEnabled = false
            }

            override fun onAnimationEnd(animation: Animator?) {
                view.isEnabled = true
            }
        })
    }

}
