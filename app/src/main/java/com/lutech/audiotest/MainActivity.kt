package com.lutech.audiotest

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.view.MotionEvent
import android.view.ViewTreeObserver
import androidx.appcompat.app.AppCompatActivity
import com.lutech.audiotest.crollerTest.Croller
import com.lutech.audiotest.databinding.ActivityMainBinding
import kotlin.math.acos
import kotlin.math.roundToInt
import kotlin.math.sqrt


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var croller: Croller
    private lateinit var mPaint: Paint
    private var posX = 0f
    private var posY = 0f
    private var posXChange = 0f
    private var posYChange = 0f
    private var viewRotation = 0f
    private var limitRotate = 125

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        croller = binding.croller
        initCroller()
        cusKnob()
        onClick()
    }

    private fun onClick() {
        binding.btnLeft.setOnClickListener {
            binding.imvKnob.rotation = binding.imvKnob.rotation - 5
        }
        binding.btnRight.setOnClickListener {
            binding.imvKnob.rotation = binding.imvKnob.rotation + 5
        }
        binding.btnReset.setOnClickListener {
            binding.imvKnob.rotation = 0f
            viewRotation = binding.imvKnob.rotation
        }

    }

    private fun initCroller() {
        croller.setOnProgressChangedListener {
            croller.label = it.toString()
        }

        mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mPaint.style = Paint.Style.STROKE
        mPaint.color = Color.RED
    }
    private var centreX = 0f
    private var centreY = 0f

    @SuppressLint("ClickableViewAccessibility")
    private fun cusKnob() {
        binding.imvKnob.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                binding.imvKnob.viewTreeObserver.removeOnGlobalLayoutListener(this)
                centreX = binding.imvKnob.x + binding.imvKnob.width / 2
                centreY = binding.imvKnob.y + binding.imvKnob.height / 2
            }
        })

        binding.fl.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    posX = event.x
                    posY = event.y

                    binding.fl.setStartXA(centreX)
                    binding.fl.setStartYA(centreY)
                    binding.fl.setEndXA(posX)
                    binding.fl.setEndYA(posY)

                    binding.fl.setStartXC(centreX)
                    binding.fl.setStartYC(centreY)
                    binding.fl.setEndXC(posX)
                    binding.fl.setEndYC(posY)

                    binding.fl.setStartXB(posX)
                    binding.fl.setStartYB(posY)
                    binding.fl.setEndXB(posX)
                    binding.fl.setEndYB(posY)

                    binding.fl.invalidate()
                    true
                }
                MotionEvent.ACTION_MOVE -> {
                    if (posXChange != event.x && posYChange != event.y) {
                        posXChange = event.x
                        posYChange = event.y

                        binding.fl.setEndXB(posXChange)
                        binding.fl.setEndYB(posYChange)
                        binding.fl.setEndXC(posXChange)
                        binding.fl.setEndYC(posYChange)
                        th1()
                        binding.fl.invalidate()
                    }
                    true
                }

                MotionEvent.ACTION_UP -> {
                    viewRotation = binding.imvKnob.rotation
                    true
                }

                else -> {
                    false
                }
            }
        }
    }

    private fun th1() {
        val point = Point(posX, posY)
        val point2 = Point(posXChange, posYChange)
        val pointCentre = Point(centreX, centreY)
        val angle = calculateAngle(point, point2, pointCentre)

        if (!angle.isNaN()) {
            var x = ((angle + viewRotation) * 10).roundToInt() /10
            if (x > 360) x -= 360
            if (x <= limitRotate || x >= 360 - limitRotate) {
                binding.imvKnob.rotation = x.toFloat()
            }
            binding.tvCount.text = binding.imvKnob.rotation .toString()
        }
    }

    data class Point(val x: Float, val y: Float)

    private fun calculateAngle(A: Point, C: Point, B: Point): Float {
        val vectorAB = Point(B.x - A.x, B.y - A.y)
        val vectorBC = Point(C.x - B.x, C.y - B.y)
        // Tính độ dài vector
        val magnitudeAB = sqrt(vectorAB.x * vectorAB.x + vectorAB.y * vectorAB.y)
        val magnitudeBC = sqrt(vectorBC.x * vectorBC.x + vectorBC.y * vectorBC.y)
        // Tính tích vô hướng
        val dotProduct = vectorAB.x * vectorBC.x + vectorAB.y * vectorBC.y
        // Tính cos(B)
        val cosB = dotProduct / (magnitudeAB * magnitudeBC)
        // Tính góc B (đơn vị radian)
        val angleB = acos(cosB)
        // Chuyển đổi góc sang độ

//        val angleBInDegrees =  180 -Math.toDegrees(angleB.toDouble())
        val angleBInDegrees =  Math.toDegrees(angleB.toDouble())
        val determinant = vectorAB.x * vectorBC.y - vectorAB.y * vectorBC.x
        // Xác định hướng orientation true = bên phải
        val orientation = determinant <= 0

        val result = if (orientation) {
            angleBInDegrees
        } else {
            0 - angleBInDegrees
        }

//        return if (binding.fl.rotation > 0 && binding.fl.rotation < (360/2)) {
//            if (180 - result + viewRotation > limitRotate) {
//                limitRotate.toFloat()
//            } else {
//                180 - result.toFloat()
//            }
//        } else {
//            if (180 - result + viewRotation > 360 - limitRotate) {
//                360 - limitRotate.toFloat()
//            } else {
//                180 - result.toFloat()
//            }
//        }


        return 180 - result.toFloat()
//        return result.toFloat()
    }


}