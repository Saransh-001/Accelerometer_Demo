package com.example.accelerometer_demo

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import com.example.accelerometer_demo.viewmodel.SensorViewModel

@Composable
fun Ball(
    viewModel: SensorViewModel
) {
    val ballRadius = 125f
    var screenSize by remember { mutableStateOf(Size.Zero) }
    var ballPosition by remember { mutableStateOf(Offset.Zero) }
    var isStopped by remember { mutableStateOf(false) }

    val x = viewModel.x
    val y = viewModel.y

    val dx = -x * 5
    val dy = y * 5

    val maxX = maxOf(0f, screenSize.width - ballRadius * 2)
    val maxY = maxOf(0f, screenSize.height - ballRadius * 2)

    if (!isStopped) {
        val newX = (ballPosition.x + dx).coerceIn(0f, maxX)
        val newY = (ballPosition.y + dy).coerceIn(0f, maxY)
        ballPosition = Offset(newX, newY)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .onGloballyPositioned {
                val size = it.size
                screenSize = Size(size.width.toFloat(), size.height.toFloat())

                if (ballPosition == Offset.Zero) {
                    ballPosition = Offset(
                        (size.width - ballRadius * 2) / 2f,
                        (size.height - ballRadius * 2) / 2f
                    )
                }
            }
    ) {
        Row(
            modifier = Modifier
                .align(Alignment.TopStart)
                .fillMaxWidth()
                .padding(top = 30.dp, start = 10.dp, end = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = {
                viewModel.startListening()
                isStopped = false
            }) {
                Text(text = "Start")
            }
            Button(onClick = {
                viewModel.stopListening()
                isStopped = true
            }) {
                Text(text = "Stop")
            }
        }
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(
                color = Color.Cyan,
                radius = ballRadius,
                center = Offset(
                    ballPosition.x + ballRadius,
                    ballPosition.y + ballRadius
                )
            )
        }
    }
}