package com.example.lab08

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.annotation.RequiresApi

class MainActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var mSensor: Sensor? = null
    private val gravity = floatArrayOf(0f, 0f, 0f)
    private val linear_acceleration = FloatArray(3)
    private lateinit var textViewX: TextView
    private lateinit var textViewY: TextView
    private lateinit var textViewZ: TextView
    private lateinit var textViewCL: TextView

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        mSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)
        sensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL, SensorManager.SENSOR_DELAY_UI)
        textViewX = findViewById(R.id.textViewX)
        textViewY = findViewById(R.id.textViewY)
        textViewZ = findViewById(R.id.textViewZ)
        textViewCL = findViewById(R.id.textViewCL)
    }

    override fun onSensorChanged(event: SensorEvent?) {

        val x = event!!.values[0]
        val y = event.values[1]
        val z = event.values[2]

        textViewX.text = "%.3f".format(x)
        textViewY.text = "%.3f".format(y)

        if(y < -0.45 || y > 0.45){
            textViewCL.text = "EN CAIDA LIBRE"
        } else {
            textViewCL.text = ""
        }
        textViewZ.text = "%.3f".format(z)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }
}