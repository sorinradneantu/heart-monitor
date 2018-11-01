package ro.upt.upt.sma.heart.injection

import android.content.Context
import ro.upt.sma.heart.firebase.FirebaseHeartMeasurementDataStore
import ro.upt.sma.heart.model.HeartMeasurementRepository
import ro.upt.sma.heart.model.HeartSensorRepository
import ro.upt.sma.heart.presenters.assistant.HeartAssistantPresenter
import ro.upt.sma.heart.presenters.assistant.HeartAssistantPresenterImpl
import ro.upt.sma.heart.presenters.monitor.HeartMonitorPresenter
import ro.upt.sma.heart.presenters.monitor.HeartMonitorPresenterImpl
import ro.upt.sma.heart.sensor.HeartSensorDataStore

object Injection {

    fun provideHeartAssistantPresenter(code: String): HeartAssistantPresenter {
        return HeartAssistantPresenterImpl(provideHeartMeasurementRepository(code))
    }

    fun provideHeartMonitorPresenter(context: Context, code : String) : HeartMonitorPresenter {
        return HeartMonitorPresenterImpl(provideHeartSensorRepository(context), provideHeartMeasurementRepository(code))
    }

    private fun provideHeartMeasurementRepository(code: String): HeartMeasurementRepository {
        return FirebaseHeartMeasurementDataStore(code)
    }

    private fun provideHeartSensorRepository(context: Context) : HeartSensorRepository {
        return HeartSensorDataStore(context)
    }

}
