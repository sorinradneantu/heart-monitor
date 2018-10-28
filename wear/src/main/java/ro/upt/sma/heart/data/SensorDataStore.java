package ro.upt.sma.heart.data;

import static android.content.Context.SENSOR_SERVICE;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import java.util.HashMap;

public class SensorDataStore {

  public interface HeartRateListener {
    void onValueChanged(int value);
  }

  private final SensorManager sensorManager;
  private final Sensor heartRateSensor;
  private final HashMap<HeartRateListener, SensorEventListener> listeners = new HashMap<>();

  public SensorDataStore(Context context) {
    this.sensorManager = ((SensorManager) context.getSystemService(SENSOR_SERVICE));
    this.heartRateSensor = sensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE);
  }

  public void registerHeartRateListener(final HeartRateListener listener) {
    SensorEventListener eventListener = new SensorEventListener() {
      @Override
      public void onSensorChanged(SensorEvent sensorEvent) {
        listener.onValueChanged((int) sensorEvent.values[0]);
      }

      @Override
      public void onAccuracyChanged(Sensor sensor, int i) {
      }
    };

    if (heartRateSensor != null) {
      sensorManager.registerListener(eventListener, heartRateSensor, SensorManager.SENSOR_DELAY_FASTEST);
      listeners.put(listener, eventListener);
    }
  }

  public void unregisterHeartRateListener(HeartRateListener listener) {
    if (listeners.containsKey(listener)) {
      SensorEventListener eventListener = listeners.get(listener);
      sensorManager.unregisterListener(eventListener);
      listeners.remove(listener);
    }
  }

}
