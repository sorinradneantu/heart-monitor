package ro.upt.sma.heart.presenters.monitor

import ro.upt.sma.heart.model.HeartMeasurement
import ro.upt.sma.heart.model.HeartMeasurementRepository
import ro.upt.sma.heart.model.HeartSensorRepository

class HeartMonitorPresenterImpl(private val heartSensorRepository: HeartSensorRepository,
                                private val heartMeasurementRepository: HeartMeasurementRepository
) : HeartMonitorPresenter {

    private lateinit var heartRateListener: HeartSensorRepository.HeartRateListener

    override fun bind(view: HeartMonitorView) {
        heartRateListener = object : HeartSensorRepository.HeartRateListener {
            override fun onValueChanged(value: Int) {
                view.showLastMeasurement(HeartMeasurement(System.currentTimeMillis(),value))
                heartMeasurementRepository.post(HeartMeasurement(System.currentTimeMillis(), value))
            }
        }

        heartSensorRepository.registerHeartRateListener(heartRateListener)
    }

    override fun unbind() {
        heartSensorRepository.unregisterHeartRateListener(heartRateListener)
    }

}
