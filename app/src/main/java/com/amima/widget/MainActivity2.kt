package com.amima.widget

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity2 constructor(k: Int): AppCompatActivity() {
    private var c = 0;
    init {
        c = k
    }

    constructor() : super() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        var a = findViewById<TextView>(R.id.textView)
        a.text = c.toString()
        println("affffffffffffffffffffffffffffffffffffffffffffffffffffffff " + c)
    }
}