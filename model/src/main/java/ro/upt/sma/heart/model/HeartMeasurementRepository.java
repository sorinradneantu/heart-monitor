package ro.upt.sma.heart.model;

import java.util.List;

public interface HeartMeasurementRepository {

  interface HeartChangedListener {

    void onHeartChanged(HeartMeasurement heartMeasurement);
  }

  interface HeartListLoadedListener {

    void onHeartListLoaded(List<HeartMeasurement> heartMeasurements);
  }

  void post(HeartMeasurement heartMeasurement);

  void observe(HeartChangedListener listener);

  void retrieveAll(HeartListLoadedListener listener);

}
