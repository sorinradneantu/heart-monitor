package ro.upt.sma.heart.data

import android.content.Context.SENSOR_SERVICE

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import java.util.HashMap

class SensorDataStore(context: Context) {

    private val sensorManager: SensorManager = context.getSystemService(SENSOR_SERVICE) as SensorManager
    private val heartRateSensor = sensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE)

    private val listeners = HashMap<HeartRateListener, SensorEventListener>()

    interface HeartRateListener {
        fun onValueChanged(value: Int)
    }

    fun registerHeartRateListener(listener: HeartRateListener) {
        val eventListener = object : SensorEventListener {
            override fun onSensorChanged(sensorEvent: SensorEvent) {
                listener.onValueChanged(sensorEvent.values[0].toInt())
            }

            override fun onAccuracyChanged(sensor: Sensor, i: Int) {}
        }

        if (heartRateSensor != null) {
            sensorManager.registerListener(eventListener, heartRateSensor, SensorManager.SENSOR_DELAY_FASTEST)
            listeners[listener] = eventListener
        }
    }

    fun unregisterHeartRateListener(listener: HeartRateListener) {
        if (listeners.containsKey(listener)) {
            val eventListener = listeners[listener]
            sensorManager.unregisterListener(eventListener)
            listeners.remove(listener)
        }
    }

}
