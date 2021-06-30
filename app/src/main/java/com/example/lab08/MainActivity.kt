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
    private val gravity = floatArrayOf(0.0f, 9.8f, 0.0f)
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
        val alpha: Float = 0.8f

        gravity[0] = alpha * gravity[0] + (1 - alpha) * event!!.values[0]
        gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1]
        gravity[2] = alpha * gravity[2] + (1 - alpha) * event.values[2]

        linear_acceleration[0] = event.values[0] - gravity[0]
        linear_acceleration[1] = event.values[1] - gravity[1]
        linear_acceleration[2] = event.values[2] - gravity[2]

        val x = linear_acceleration[0]
        val y = linear_acceleration[1]
        val z = linear_acceleration[2]

        if(x > 0.2 || x < -0.2) {
            textViewX.text = "%.3f".format(x)
        } else {
            textViewX.text = "0.0"
        }
        if(y > 0.2 || y < -0.2) {
            textViewY.text = "%.3f".format(y)
            if(y < -0.5){
                textViewCL.text = "EN CAIDA LIBRE"
            } else {
                textViewCL.text = ""
            }
        }else {
            textViewY.text = "0.0"
        }
        if(z > 0.2 || z < -0.2) {
            textViewZ.text = "%.3f".format(z)
        }else {
            textViewZ.text = "0.0"
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }
}