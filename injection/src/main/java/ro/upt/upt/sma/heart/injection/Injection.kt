package ro.upt.upt.sma.heart.injection

import ro.upt.sma.heart.firebase.FirebaseHeartMeasurementDataStore
import ro.upt.sma.heart.model.HeartMeasurementRepository
import ro.upt.sma.heart.presenters.assistant.HeartAssistantPresenter
import ro.upt.sma.heart.presenters.assistant.HeartAssistantPresenterImpl

object Injection {

    fun provideHeartAssistantPresenter(code: String): HeartAssistantPresenter {
        return HeartAssistantPresenterImpl(provideHeartMeasurementRepository(code))
    }

    fun provideHeartMeasurementRepository(code: String): HeartMeasurementRepository {
        return FirebaseHeartMeasurementDataStore(code)
    }

}
