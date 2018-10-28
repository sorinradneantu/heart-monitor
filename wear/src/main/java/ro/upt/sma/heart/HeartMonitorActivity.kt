package ro.upt.sma.heart

import android.Manifest.permission
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Settings.Secure
import android.support.wearable.activity.WearableActivity
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_heart_rate.*
import ro.upt.sma.heart.data.SensorDataStore
import ro.upt.sma.heart.data.SensorDataStore.HeartRateListener
import ro.upt.sma.heart.model.HeartMeasurement
import ro.upt.sma.heart.model.HeartMeasurementRepository
import ro.upt.upt.sma.heart.injection.Injection
import java.text.MessageFormat

class HeartMonitorActivity : WearableActivity() {

    private var sensorDataStore: SensorDataStore? = null
    private lateinit var heartRateListener: HeartRateListener
    private var heartMeasurementRepository: HeartMeasurementRepository? = null

    @SuppressLint("HardwareIds")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_heart_rate)

        checkPermission()

        val userId = Secure.getString(contentResolver, Secure.ANDROID_ID).substring(0, 4)
        tv_heart_code_value.text = userId

        this.sensorDataStore = SensorDataStore(this)

        // TODO: add presenter/viewmodel
        this.heartMeasurementRepository = Injection.provideHeartMeasurementRepository(userId)
    }

    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(this, permission.BODY_SENSORS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(permission.BODY_SENSORS), 112)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 112) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, getString(R.string.permission_not_granted), Toast.LENGTH_LONG)
                        .show()
            }
        }
    }

    override fun onResume() {
        super.onResume()

        registerListener()
    }

    override fun onPause() {
        super.onPause()

        unregisterListener()
    }

    private fun registerListener() {
        this.heartRateListener = object : HeartRateListener {
            override fun onValueChanged(value: Int) {
                tv_heart_value.text = MessageFormat.format("{0} bpm", value)
                heartMeasurementRepository!!.post(HeartMeasurement(System.currentTimeMillis(), value))
            }
        }

        sensorDataStore!!.registerHeartRateListener(heartRateListener)
    }

    private fun unregisterListener() {
        sensorDataStore?.unregisterHeartRateListener(heartRateListener)
    }

}
