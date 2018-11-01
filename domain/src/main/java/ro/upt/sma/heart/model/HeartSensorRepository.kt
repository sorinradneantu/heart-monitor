package ro.upt.sma.heart.model

interface HeartSensorRepository {

    interface HeartRateListener {
        fun onValueChanged(value: Int)
    }

    fun registerHeartRateListener(listener: HeartSensorRepository.HeartRateListener)
    fun unregisterHeartRateListener(listener: HeartSensorRepository.HeartRateListener)

}