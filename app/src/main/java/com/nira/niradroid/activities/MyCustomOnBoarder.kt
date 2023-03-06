package com.nira.niradroid.activities

import android.Manifest
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.aemerse.onboard.OnboardAdvanced
import com.aemerse.onboard.OnboardFragment
import com.aemerse.onboard.OnboardPageTransformerType
import com.nira.niradroid.R

//Written in Kotlin
class MyCustomOnBoarder : OnboardAdvanced() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        // Onboarding slides
        addSlide(OnboardFragment.newInstance(
                title = getString(R.string.onboard_screen1_title),
                titleColor = ContextCompat.getColor(this, R.color.black),
                resourceId = R.drawable.nira_logo_full,
                /*description = getString(R.string.onboard_screen1_desc)*/
                descriptionColor = ContextCompat.getColor(this, R.color.black),
                backgroundDrawable = R.drawable.onboarding_background
        ))

        addSlide(OnboardFragment.newInstance(
                title = getString(R.string.onboard_screen2_title),
                titleColor = ContextCompat.getColor(this, R.color.black),
                resourceId = R.drawable.onboarding_image_2,
                description = getString(R.string.onboard_screen2_desc),
                descriptionColor = ContextCompat.getColor(this, R.color.black),
                backgroundDrawable = R.drawable.onboarding_background
        ))

        addSlide(OnboardFragment.newInstance(
                title = getString(R.string.onboard_screen3_title),
                titleColor = ContextCompat.getColor(this, R.color.black),
                resourceId = R.raw.teen,
                isLottie = true,
                description = getString(R.string.onboard_screen3_desc),
                descriptionColor = ContextCompat.getColor(this, R.color.black),
                backgroundDrawable = R.drawable.onboarding_background
        ))

        addSlide(OnboardFragment.newInstance(
            title = getString(R.string.onboard_screen4_title),
            titleColor = ContextCompat.getColor(this, R.color.black),
            description = getString(R.string.onboard_screen4_desc),
            descriptionColor = ContextCompat.getColor(this, R.color.black),
            backgroundDrawable = R.drawable.onboarding_background,
            resourceId = R.drawable.report_onboarding4
        ))

        setTransformer(OnboardPageTransformerType.Parallax())
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

            Log.d(TAG, "Android version is 33, permisson popup not working, idk why")

        } else {
            askForPermissions(
                permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                slideNumber = 4,
                required = true
            )

        }

    }

    override fun onSkipPressed(currentFragment: Fragment?) {
        super.onSkipPressed(currentFragment)
        startActivity(Intent(this@MyCustomOnBoarder, MainActivity::class.java))
    }

    override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)
        startActivity(Intent(this@MyCustomOnBoarder, MainActivity::class.java))
    }
}