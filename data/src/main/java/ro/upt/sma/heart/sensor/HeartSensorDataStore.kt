package ro.upt.sma.heart.sensor

import android.content.Context.SENSOR_SERVICE

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import ro.upt.sma.heart.model.HeartSensorRepository
import java.util.HashMap

class HeartSensorDataStore(context: Context) : HeartSensorRepository {

    private val sensorManager: SensorManager = context.getSystemService(SENSOR_SERVICE) as SensorManager
    private val heartRateSensor = sensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE)

    private val listeners = HashMap<HeartSensorRepository.HeartRateListener, SensorEventListener>()

    override fun registerHeartRateListener(listener: HeartSensorRepository.HeartRateListener) {
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

    override fun unregisterHeartRateListener(listener: HeartSensorRepository.HeartRateListener) {
        if (listeners.containsKey(listener)) {
            val eventListener = listeners[listener]
            sensorManager.unregisterListener(eventListener)
            listeners.remove(listener)
        }
    }

}
