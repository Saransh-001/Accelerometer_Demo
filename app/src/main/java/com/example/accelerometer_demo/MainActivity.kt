package com.example.accelerometer_demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.accelerometer_demo.ui.theme.Accelerometer_DemoTheme
import com.example.accelerometer_demo.viewmodel.SensorViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Accelerometer_DemoTheme {
                val viewModel: SensorViewModel = hiltViewModel()
                Ball(viewModel = viewModel)
            }
        }
    }
}
