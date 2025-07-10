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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
    val screenSize = remember { mutableStateOf(Size.Zero) }
    val ballPosition = remember { mutableStateOf(Offset.Zero) }

    val x = viewModel.x
    val y = viewModel.y

    val dx = -x * 5
    val dy = y * 5

    val maxX = maxOf(0f, screenSize.value.width - ballRadius * 2)
    val maxY = maxOf(0f, screenSize.value.height - ballRadius * 2)

    val newX = (ballPosition.value.x + dx).coerceIn(0f, maxX)
    val newY = (ballPosition.value.y + dy).coerceIn(0f, maxY)

    ballPosition.value = Offset(newX, newY)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .onGloballyPositioned {
                val size = it.size
                screenSize.value = Size(size.width.toFloat(), size.height.toFloat())

                if (ballPosition.value == Offset.Zero) {
                    ballPosition.value = Offset(
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
            Button( onClick = {
                viewModel.startListening()
            }) {
                Text(text = "Start")
            }
            Button( onClick = {
                viewModel.stopListening()
            }) {
                Text(text = "Stop")
            }
        }
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(
                color = Color.Cyan,
                radius = ballRadius,
                center = Offset(
                    ballPosition.value.x + ballRadius,
                    ballPosition.value.y + ballRadius
                )
            )
        }
    }
}