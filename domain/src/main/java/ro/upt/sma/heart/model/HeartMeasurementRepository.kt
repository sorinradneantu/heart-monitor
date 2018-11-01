package ro.upt.sma.heart.model

interface HeartMeasurementRepository {

    interface HeartChangedListener {
        fun onHeartChanged(heartMeasurement: HeartMeasurement)
    }

    interface HeartListLoadedListener {
        fun onHeartListLoaded(heartMeasurements: List<HeartMeasurement>)
    }

    fun post(heartMeasurement: HeartMeasurement)

    fun observe(listener: HeartChangedListener)

    fun retrieveAll(listener: HeartListLoadedListener)

}
