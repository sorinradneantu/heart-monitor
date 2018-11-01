package ro.upt.sma.heart.presenters.monitor

import ro.upt.sma.heart.model.HeartMeasurement

interface HeartMonitorView {
    fun showLastMeasurement(heartMeasurement: HeartMeasurement)
}
