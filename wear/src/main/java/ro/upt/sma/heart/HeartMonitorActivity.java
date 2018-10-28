package ro.upt.sma.heart;

import android.Manifest.permission;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.wearable.activity.WearableActivity;
import android.widget.TextView;
import android.widget.Toast;
import java.text.MessageFormat;
import ro.upt.sma.heart.data.SensorDataStore;
import ro.upt.sma.heart.data.SensorDataStore.HeartRateListener;
import ro.upt.sma.heart.model.HeartMeasurement;
import ro.upt.sma.heart.model.HeartMeasurementRepository;
import ro.upt.upt.sma.heart.injection.Injection;

public class HeartMonitorActivity extends WearableActivity {

  private TextView tvHeartRate;

  private SensorDataStore sensorDataStore;
  private HeartRateListener heartRateListener;
  private HeartMeasurementRepository heartMeasurementRepository;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_heart_rate);

    checkPermission();

    tvHeartRate = findViewById(R.id.tv_heart_value);
    TextView tvMonitorCode = findViewById(R.id.tv_heart_code_value);

    String userId = Secure.getString(getContentResolver(), Secure.ANDROID_ID).substring(0, 4);
    tvMonitorCode.setText(userId);

    this.sensorDataStore = new SensorDataStore(this);

    // TODO: add presenter/viewmodel
    this.heartMeasurementRepository = Injection.provideHeartMeasurementRepository(userId);
  }

  private void checkPermission() {
    if (ContextCompat.checkSelfPermission(this, permission.BODY_SENSORS)
        != PackageManager.PERMISSION_GRANTED) {
      ActivityCompat.requestPermissions(this, new String[]{permission.BODY_SENSORS}, 112);
    }
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    if (requestCode == 112) {
      if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
        Toast.makeText(this, getString(R.string.permission_not_granted), Toast.LENGTH_LONG)
            .show();
      }
    }
  }

  @Override
  protected void onResume() {
    super.onResume();

    initListener();
  }

  private void initListener() {
    this.heartRateListener = new SensorDataStore.HeartRateListener() {
      @Override
      public void onValueChanged(int value) {
        tvHeartRate.setText(MessageFormat.format("{0} bpm", value));
        heartMeasurementRepository.post(new HeartMeasurement(System.currentTimeMillis(), value));
      }
    };

    sensorDataStore.registerHeartRateListener(heartRateListener);
  }

  @Override
  protected void onPause() {
    super.onPause();

    destroyListener();
  }

  private void destroyListener() {
    if (heartRateListener != null) {
      sensorDataStore.unregisterHeartRateListener(heartRateListener);
    }
  }

}
