package ro.upt.sma.heart

import android.Manifest.permission
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Settings.Secure
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.wearable.activity.WearableActivity
import android.widget.TextView
import android.widget.Toast
import java.text.MessageFormat
import ro.upt.sma.heart.data.SensorDataStore
import ro.upt.sma.heart.data.SensorDataStore.HeartRateListener
import ro.upt.sma.heart.model.HeartMeasurement
import ro.upt.sma.heart.model.HeartMeasurementRepository
import ro.upt.upt.sma.heart.injection.Injection

class HeartMonitorActivity : WearableActivity() {

    private var tvHeartRate: TextView? = null

    private var sensorDataStore: SensorDataStore? = null
    private var heartRateListener: HeartRateListener? = null
    private var heartMeasurementRepository: HeartMeasurementRepository? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_heart_rate)

        checkPermission()

        tvHeartRate = findViewById(R.id.tv_heart_value)
        val tvMonitorCode = findViewById<TextView>(R.id.tv_heart_code_value)

        val userId = Secure.getString(contentResolver, Secure.ANDROID_ID).substring(0, 4)
        tvMonitorCode.setText(userId)

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

        initListener()
    }

    private fun initListener() {
        this.heartRateListener = HeartRateListener { value ->
            tvHeartRate!!.text = MessageFormat.format("{0} bpm", value)
            heartMeasurementRepository!!.post(HeartMeasurement(System.currentTimeMillis(), value))
        }

        sensorDataStore!!.registerHeartRateListener(heartRateListener)
    }

    override fun onPause() {
        super.onPause()

        destroyListener()
    }

    private fun destroyListener() {
        if (heartRateListener != null) {
            sensorDataStore!!.unregisterHeartRateListener(heartRateListener)
        }
    }

}
