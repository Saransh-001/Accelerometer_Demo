package com.example.accelerometer_demo

import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor
import com.example.accelerometer_demo.baseclass.AndroidSensor

class AccelerometerSensor(
    context: Context
): AndroidSensor(
    context = context,
    sensorFeature = PackageManager.FEATURE_SENSOR_ACCELEROMETER,
    sensorType = Sensor.TYPE_ACCELEROMETER
)