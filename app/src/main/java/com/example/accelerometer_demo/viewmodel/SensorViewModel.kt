package com.example.accelerometer_demo.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.accelerometer_demo.baseclass.MeasurableSensor
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject

@HiltViewModel
class SensorViewModel @Inject constructor(
    private val accelerometerSensor: MeasurableSensor
): ViewModel() {

    var x by mutableFloatStateOf(0f)
    var y by mutableFloatStateOf(0f)

    fun startListening() {
        accelerometerSensor.startListening()
        accelerometerSensor.setOnSensorValuesChangedListener { values ->
            x = values[0]
            y = values[1]
        }
    }

    fun stopListening() {
        accelerometerSensor.stopListening()
    }

}