package com.sami.weathertest.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sami.weathertest.R
import com.sami.weathertest.ui.home.HomeFragment
import com.sami.weathertest.util.replaceFragment
import kotlinx.android.synthetic.main.custom_toolbar_layout.*


class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)

        replaceFragment(
            supportFragmentManager,
            HomeFragment(), false
        )

        img_back.setOnClickListener {
            onBackPressed()
        }
    }
}