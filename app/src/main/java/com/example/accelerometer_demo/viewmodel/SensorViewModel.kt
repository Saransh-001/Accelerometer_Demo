package com.example.accelerometer_demo.viewmodel

import android.R.attr.screenSize
import android.R.attr.x
import android.R.attr.y
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.accelerometer_demo.baseclass.MeasurableSensor
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject

@HiltViewModel
class SensorViewModel @Inject constructor(
    private val accelerometerSensor: MeasurableSensor
): ViewModel() {

    var isRunning by mutableStateOf(false)
        private set

    var x by mutableFloatStateOf(0f)
    var y by mutableFloatStateOf(0f)

    val ballRadius = 125f
    var screenSize by mutableStateOf(Size.Zero)
    var ballPosition by mutableStateOf(Offset.Zero)

    fun updateBallPosition() {
        val dx = -x * 5
        val dy = y * 5

        val maxX = maxOf(0f, screenSize.width - ballRadius * 2)
        val maxY = maxOf(0f, screenSize.height - ballRadius * 2)

        val newX = (ballPosition.x + dx).coerceIn(0f, maxX)
        val newY = (ballPosition.y + dy).coerceIn(0f, maxY)

        ballPosition = Offset(newX, newY)
    }


    fun startListening() {
        accelerometerSensor.startListening()
        accelerometerSensor.setOnSensorValuesChangedListener { values ->
            x = values[0]
            y = values[1]
        }
        isRunning = true
    }

    fun stopListening() {
        accelerometerSensor.stopListening()
        isRunning = false
    }

}