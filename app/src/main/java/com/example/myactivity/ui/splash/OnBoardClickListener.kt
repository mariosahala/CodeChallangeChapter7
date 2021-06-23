package com.example.myactivity.ui.splash

import android.animation.ArgbEvaluator
import android.app.Activity
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.ViewModel
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.example.myactivity.R
import com.example.myactivity.databinding.ActivityOnboardBinding
import com.example.myactivity.ui.splash.utils.utils.Companion.changeStatusBarColor


class OnBoardClickListener : ViewModel() {
    fun onPageClickListener(binding: ActivityOnboardBinding, argbEvaluator: ArgbEvaluator, colorList: IntArray, indicators: Array<ImageView>, myActivityReference: Activity?) {
        binding.onBoardingVp.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                val colorUpdate = argbEvaluator.evaluate(positionOffset, colorList[position], colorList[if (position == 2) position else position + 1]) as Int
                binding.onBoardingVp.setBackgroundColor(colorUpdate)
            }

            override fun onPageSelected(position: Int) {
                updateIndicators(position, indicators)
                when (position) {
                    0 -> {
                        binding.onBoardingVp.setBackgroundColor(colorList[0])
                        changeStatusBarColor(colorList[0], myActivityReference!!)
                    }
                    1 -> {
                        binding.onBoardingVp.setBackgroundColor(colorList[1])
                        changeStatusBarColor(colorList[1], myActivityReference!!)
                    }
                    2 -> {
                        binding.onBoardingVp.setBackgroundColor(colorList[2])
                        changeStatusBarColor(colorList[2], myActivityReference!!)
                    }
                }
                binding.onBoardingBtnNext.visibility = if (position == 2) View.GONE else View.VISIBLE
                binding.onBoardingBtnGetStarted.visibility = if (position == 2) View.VISIBLE else View.GONE

                binding.onBoardingBtnSkip.visibility = if (position == 0) View.VISIBLE else View.GONE
                binding.onBoardingBtnBack.visibility = if (position >= 1) View.VISIBLE else View.GONE


            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
    }

    fun updateIndicators(position: Int, indicators: Array<ImageView>) {
        for (i in indicators.indices) {
            indicators[i].setBackgroundResource(
                if (i == position) R.drawable.indicator_selected else R.drawable.indicator_unselected
            )
        }
    }

    companion object {
        var instance: OnBoardClickListener? = null
            get() {
                if (field == null) {
                    synchronized(OnBoardClickListener::class.java) {
                        if (field == null) {
                            field = OnBoardClickListener()
                        }
                    }
                }
                return field
            }
            private set
    }
}