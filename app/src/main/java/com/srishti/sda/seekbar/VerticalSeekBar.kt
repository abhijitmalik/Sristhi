package com.srishti.sda.seekbar



import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatSeekBar

class VerticalSeekBar : AppCompatSeekBar {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onDraw(canvas: Canvas) {
        // Rotate the canvas to make the SeekBar vertical
        canvas.rotate(-90f)
        // Move the canvas to the correct position
        canvas.translate(-height.toFloat(), 0f)
        super.onDraw(canvas)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        // Swap width and height
        super.onMeasure(heightMeasureSpec, widthMeasureSpec)
        setMeasuredDimension(measuredHeight, measuredWidth)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (!isEnabled) {
            return false
        }

        when (event.action) {
            MotionEvent.ACTION_DOWN,
            MotionEvent.ACTION_MOVE,
            MotionEvent.ACTION_UP -> {
                // Convert the touch position for a vertical orientation
                val i = max - (max * event.y / width).toInt()
                progress = i
                onSizeChanged(width, height, 0, 0)
            }
        }
        return true
    }

    // Add animation methods
    fun animateProgress(targetProgress: Int, duration: Long) {
        val animator = android.animation.ValueAnimator.ofInt(progress, targetProgress)
        animator.duration = duration
        animator.addUpdateListener { animation ->
            progress = animation.animatedValue as Int
        }
        animator.start()
    }
}