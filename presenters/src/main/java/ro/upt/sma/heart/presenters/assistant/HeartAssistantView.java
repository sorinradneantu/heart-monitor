package ro.upt.sma.heart.presenters.assistant;

import java.util.List;
import ro.upt.sma.heart.model.HeartMeasurement;

public interface HeartAssistantView {

  void showLast(HeartMeasurement heartMeasurement);

  void showList(List<HeartMeasurement> heartMeasurementList);

}
