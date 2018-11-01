package ro.upt.sma.heart.presenters.assistant

import ro.upt.sma.heart.model.HeartMeasurement

interface HeartAssistantView {
    fun showLast(heartMeasurement: HeartMeasurement)
    fun showList(heartMeasurementList: List<HeartMeasurement>)
}
