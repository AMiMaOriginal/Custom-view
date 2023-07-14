package com.amima.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.TextView

@SuppressLint("AppCompatCustomView", "Recycle")
class Widget(context: Context?, attrs: AttributeSet?) : TextView(context, attrs), View.OnTouchListener {
    private var minValue: Int
    private var maxValue: Int
    private var nextValue: Int
    private var previousValue: Int
    private var currentValue: Int
    private var myCanvas: Canvas? = null
    private var coordinateY = 0F
    private val minSwipeDistance = 5F
    private val textX = 50F
    private val lineStartX = 30F
    private val lineEndX = 90F
    private val firstLineY = 60F
    private val secondLineY = 130F
    private val nextNumberY = 40F
    private val currentNumberY = 110F
    private val previousNumberY = 180F

    init {
        val attribute = context?.obtainStyledAttributes(attrs, R.styleable.Widget)!!
        maxValue = attribute.getInt(R.styleable.Widget_maxValue, 0)
        minValue = attribute.getInt(R.styleable.Widget_minValue, 0)
        if (minValue > maxValue)
            throw ValueException("Variable \"minValue\" cannot be greater than \"maxValue\"!")
        currentValue = minValue
        nextValue = currentValue + 1
        previousValue = maxValue
    }

    private fun changeNumbers(swipeUp: Boolean) {
        if (swipeUp) {
            currentValue++
            if (currentValue > maxValue)
                currentValue = minValue
        }
        else {
            currentValue--
            if (currentValue < minValue)
                currentValue = maxValue
        }

        nextValue = currentValue + 1
        if (nextValue > maxValue)
            nextValue = minValue

        previousValue = currentValue - 1
        if (previousValue < minValue)
            previousValue = maxValue

        invalidate()
    }

    override fun onTouch(view: View?, event: MotionEvent?): Boolean {
        when(event?.action) {
            MotionEvent.ACTION_DOWN -> coordinateY = event.y
            MotionEvent.ACTION_MOVE -> swipe(event.y)
        }
        return true
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var width = (textSize * 3).toInt()
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)

        if (widthMode == MeasureSpec.EXACTLY)
            width = widthSize
        else if (widthMode == MeasureSpec.AT_MOST)
            width = width.coerceAtMost(widthSize)


        var height = (textSize * 6).toInt()
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        if (heightMode == MeasureSpec.EXACTLY)
            height = heightSize
        else if (heightMode == MeasureSpec.AT_MOST)
            height = height.coerceAtMost(heightSize)

        Log.d("log", "height: $height, width: $width")
        setMeasuredDimension(width, height)
    }

    private fun swipe(nowY: Float) {
        if (coordinateY < nowY && (nowY - coordinateY) > minSwipeDistance)
            changeNumbers(true)
        else if (coordinateY > nowY && (coordinateY - nowY) > minSwipeDistance)
            changeNumbers(false)
        coordinateY = nowY
    }

    public fun getNumber(): Int {return currentValue}

    override fun onDraw(canvas: Canvas?) {
        myCanvas = canvas
        myCanvas?.drawColor(Color.WHITE)
        paint.color = Color.BLACK

        myCanvas?.drawText(nextValue.toString(), textX, nextNumberY, paint)
        myCanvas?.drawText(previousValue.toString(), textX, previousNumberY, paint)

        paint.color = Color.CYAN
        myCanvas?.drawText(currentValue.toString(), textX, currentNumberY, paint).apply { setOnTouchListener(this@Widget) }

        paint.color = Color.GRAY
        myCanvas?.drawLine(lineStartX, firstLineY, lineEndX, firstLineY, paint)
        myCanvas?.drawLine(lineStartX, secondLineY, lineEndX, secondLineY, paint)
        super.onDraw(canvas)
    }

    class ValueException(error: String) : Exception(error)
}