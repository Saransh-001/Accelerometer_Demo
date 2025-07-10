package com.example.accelerometer_demo.di

import android.app.Application
import com.example.accelerometer_demo.AccelerometerSensor
import com.example.accelerometer_demo.baseclass.MeasurableSensor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SensorModule {

    @Provides
    @Singleton
    fun provideLightSensor(app: Application): MeasurableSensor {
        return AccelerometerSensor(app)
    }

}