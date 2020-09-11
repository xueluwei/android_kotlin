package com.example.xlwapp.utils.customviews

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.accessibility.AccessibilityNodeInfo
import androidx.core.view.AccessibilityDelegateCompat
import androidx.core.view.ViewCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import com.example.xlwapp.R
import java.lang.Math.*

private enum class FanSpeed(val label:Int) {
    OFF(R.string.fan_off),
    LOW(R.string.fan_low),
    MEDIUM(R.string.fan_medium),
    HIGH(R.string.fan_high);

    fun next() = when(this) {
        OFF -> LOW
        LOW -> MEDIUM
        MEDIUM -> HIGH
        HIGH -> OFF
    }
}

private const val RADIUS_OFFSET_LABEL = 30
private const val RADIUS_OFFSET_INDICATOR = -35

private var fanSpeedOffColor = 0
private var fanSpeedLowColor = 0
private var fanSpeedMediumColor = 0
private var fanSpeedHighColor = 0

class DialView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var radius = 0.0f  //radius of circle
    private var fanSpeed = FanSpeed.OFF //the action selection
    private val pointPosition: PointF = PointF(0.0f, 0.0f) //be used to draw things
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
        textSize = 55.0f
        typeface = Typeface.create("", Typeface.BOLD)
    }

    init {
        isClickable = true

        val typedArray = context.obtainStyledAttributes(attrs,R.styleable.DialView)
        fanSpeedOffColor = typedArray.getColor(R.styleable.DialView_fancoloroff,0)
        fanSpeedLowColor = typedArray.getColor(R.styleable.DialView_fancolor1,0)
        fanSpeedMediumColor = typedArray.getColor(R.styleable.DialView_fancolor2,0)
        fanSpeedHighColor = typedArray.getColor(R.styleable.DialView_fancolor3,0)
        typedArray.recycle()

        updateContentDescription()

        ViewCompat.setAccessibilityDelegate(this, object : AccessibilityDelegateCompat(){
            override fun onInitializeAccessibilityNodeInfo(host: View?, info: AccessibilityNodeInfoCompat?) {
                super.onInitializeAccessibilityNodeInfo(host, info)
                val customClick = AccessibilityNodeInfoCompat.AccessibilityActionCompat(
                        AccessibilityNodeInfo.ACTION_CLICK,
                        context.getString(if (fanSpeed != FanSpeed.HIGH) R.string.change
                        else R.string.reset)
                )
                info?.addAction(customClick)

            }
        })
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        radius = (min(w, h) / 2.0 * 0.8).toFloat()
    }

    private fun PointF.computeXYForSpeed(pos: FanSpeed, radius: Float){
        val startAngle = Math.PI * (9 / 8.0)
        val angle = startAngle + pos.ordinal * (Math.PI / 4)
        x = (radius * cos(angle)).toFloat() + width / 2
        y = (radius * sin(angle)).toFloat() + height / 2
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        paint.color = when (fanSpeed) {
            FanSpeed.OFF -> fanSpeedOffColor
            FanSpeed.LOW -> fanSpeedLowColor
            FanSpeed.MEDIUM -> fanSpeedMediumColor
            FanSpeed.HIGH -> fanSpeedHighColor
        }
        //draw dial
        canvas.drawCircle((width / 2).toFloat(), (height / 2).toFloat(), radius, paint)
        //draw  indicator circle edge
        val edgeRadius = radius + RADIUS_OFFSET_INDICATOR
        for (i in FanSpeed.values()) {
            pointPosition.computeXYForSpeed(i, edgeRadius)
            paint.color = Color.WHITE
            canvas.drawCircle(pointPosition.x, pointPosition.y, radius / 11, paint)
        }
        //draw indicator circle
        val markerRadius = radius + RADIUS_OFFSET_INDICATOR
        pointPosition.computeXYForSpeed(fanSpeed, markerRadius)
        paint.color = Color.BLUE
        canvas.drawCircle(pointPosition.x, pointPosition.y, radius / 12, paint)
        //draw text label
        val labelRadius = radius + RADIUS_OFFSET_LABEL
        paint.color = Color.BLACK
        for (i in FanSpeed.values()) {
            pointPosition.computeXYForSpeed(i, labelRadius)
            val label = resources.getString(i.label)
            canvas.drawText(label, pointPosition.x, pointPosition.y, paint)
        }
    }

    override fun performClick(): Boolean {
        //super.performClick() must happen fist,
        // which enables accessibility events as well as calls onClickListener().
        if (super.performClick()) return true

        fanSpeed = fanSpeed.next()
        contentDescription = resources.getString(fanSpeed.label)

        updateContentDescription()

        invalidate()
        return true
    }

    fun updateContentDescription() {
        contentDescription = resources.getString(fanSpeed.label)

    }
}