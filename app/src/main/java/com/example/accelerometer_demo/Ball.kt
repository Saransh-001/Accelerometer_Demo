package com.example.accelerometer_demo

import android.R.attr.screenSize
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import com.example.accelerometer_demo.viewmodel.SensorViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

@Composable
fun Ball(
    viewModel: SensorViewModel
) {
    LaunchedEffect(viewModel.isRunning) {
        while (isActive && viewModel.isRunning) {
            withFrameNanos {
                viewModel.updateBallPosition()
            }
        }
    }

    val ballRadius = viewModel.ballRadius
    var ballPosition = viewModel.ballPosition

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .onGloballyPositioned {
                val size = it.size
                viewModel.screenSize = Size(size.width.toFloat(), size.height.toFloat())

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
            }) {
                Text(text = "Start")
            }
            Button(onClick = {
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
                    ballPosition.x + ballRadius,
                    ballPosition.y + ballRadius
                )
            )
        }
    }
}