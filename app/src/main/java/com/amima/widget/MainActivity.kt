package com.amima.widget

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.constraintlayout.motion.widget.OnSwipe
import androidx.constraintlayout.widget.ConstraintLayout

class MainActivity : AppCompatActivity(), OnChangeNumberListener {

    private lateinit var view: Widget

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        view = findViewById<Widget>(R.id.myView)
        view.setChangeNumberListener(this)
        println(view.getNumber())
    }

    override fun onChangeNumber() {
        println(view.getNumber())
    }
}