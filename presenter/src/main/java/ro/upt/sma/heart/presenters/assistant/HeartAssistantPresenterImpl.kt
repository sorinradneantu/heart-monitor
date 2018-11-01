package ro.upt.sma.heart.presenters.assistant


import ro.upt.sma.heart.model.HeartMeasurement
import ro.upt.sma.heart.model.HeartMeasurementRepository
import ro.upt.sma.heart.model.HeartMeasurementRepository.HeartChangedListener
import ro.upt.sma.heart.model.HeartMeasurementRepository.HeartListLoadedListener

class HeartAssistantPresenterImpl(private val repository: HeartMeasurementRepository) : HeartAssistantPresenter {

    private var view: HeartAssistantView? = null

    override fun bind(view: HeartAssistantView) {
        this.view = view
        repository.observe(object : HeartChangedListener {
            override fun onHeartChanged(heartMeasurement: HeartMeasurement) {
                view.showLast(heartMeasurement)
            }
        })
        repository.retrieveAll(object : HeartListLoadedListener {
            override fun onHeartListLoaded(heartMeasurements: List<HeartMeasurement>) {
                view.showList(heartMeasurements)
            }
        })
    }

    override fun unbind() {
        //TODO: cleanup resources
    }
}
