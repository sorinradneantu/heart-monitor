package ro.upt.upt.sma.heart.injection;

import ro.upt.sma.heart.firebase.FirebaseHeartMeasurementDataStore;
import ro.upt.sma.heart.model.HeartMeasurementRepository;
import ro.upt.sma.heart.presenters.assistant.HeartAssistantPresenter;
import ro.upt.sma.heart.presenters.assistant.HeartAssistantPresenterImpl;

public class Injection {

  public static HeartAssistantPresenter provideHeartAssistantPresenter(String code) {
    return new HeartAssistantPresenterImpl(provideHeartMeasurementRepository(code));
  }

  public static HeartMeasurementRepository provideHeartMeasurementRepository(String code) {
    return new FirebaseHeartMeasurementDataStore(code);
  }

}
