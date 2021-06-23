package com.example.myactivity.ui.splash

import android.animation.ArgbEvaluator
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.myactivity.R
import com.example.myactivity.databinding.ActivityOnboardBinding
import com.example.myactivity.ui.splash.utils.BaseView
import com.example.myactivity.ui.splash.utils.utils


class OnbOardActivity : AppCompatActivity(), BaseView {

    private lateinit var onBoardBinding: ActivityOnboardBinding
    private var argbEvaluator: ArgbEvaluator? = null
    private var statusBarColorArray: IntArray? = null
    private var currentOnBoardPage = 0 //  to track page position
    private var indicatorsImgArray: Array<ImageView>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        setListeners()
    }

    override fun init() {
        onBoardBinding = ActivityOnboardBinding.inflate(layoutInflater)
        setContentView(onBoardBinding.root)

        val color1 = ContextCompat.getColor(this, R.color.white)
        val color2 = ContextCompat.getColor(this, R.color.white)
        val color3 = ContextCompat.getColor(this, R.color.white)

        statusBarColorArray = intArrayOf(color1, color2, color3)
        utils.changeStatusBarColor(
            color1,
            this@OnbOardActivity
        )

        argbEvaluator = ArgbEvaluator()
        val mSectionsPagerAdapter = SectionPagerAdapter(supportFragmentManager)

        indicatorsImgArray = arrayOf(
            onBoardBinding.introIndicator0,
            onBoardBinding.introIndicator1,
            onBoardBinding.introIndicator2
        )

        // Set up the ViewPager with the sections adapter.

        // Set up the ViewPager with the sections adapter.
        onBoardBinding.onBoardingVp.adapter = mSectionsPagerAdapter

        onBoardBinding.onBoardingVp.currentItem = currentOnBoardPage
        OnBoardClickListener.instance?.updateIndicators(currentOnBoardPage, indicatorsImgArray!!)
    }

    override fun setValues() {
        TODO("Not yet implemented")
    }

    override fun setListeners() {
        OnBoardClickListener.instance?.onPageClickListener(
            onBoardBinding,
            argbEvaluator!!,
            statusBarColorArray!!,
            indicatorsImgArray!!,
            this
        )

        onBoardBinding.onBoardingBtnNext.setOnClickListener { v ->
            currentOnBoardPage += 1
            onBoardBinding.onBoardingVp.setCurrentItem(currentOnBoardPage, true)
        }

        onBoardBinding.onBoardingBtnBack.setOnClickListener { v ->
            currentOnBoardPage--
            onBoardBinding.onBoardingVp.setCurrentItem(currentOnBoardPage, true)
        }

        onBoardBinding.onBoardingBtnSkip.setOnClickListener { v ->

            utils.showSnackBar(onBoardBinding.parentLayout, "Skip")
        }

        onBoardBinding.onBoardingBtnGetStarted.setOnClickListener { v ->
            utils.showSnackBar(onBoardBinding.parentLayout, "Get Started")

        }
    }
}