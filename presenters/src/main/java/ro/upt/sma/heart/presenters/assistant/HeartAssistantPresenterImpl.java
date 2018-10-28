package ro.upt.sma.heart.presenters.assistant;


import java.util.List;
import ro.upt.sma.heart.model.HeartMeasurement;
import ro.upt.sma.heart.model.HeartMeasurementRepository;
import ro.upt.sma.heart.model.HeartMeasurementRepository.HeartChangedListener;
import ro.upt.sma.heart.model.HeartMeasurementRepository.HeartListLoadedListener;

public class HeartAssistantPresenterImpl implements HeartAssistantPresenter {

  private HeartAssistantView view;
  private final HeartMeasurementRepository repository;

  public HeartAssistantPresenterImpl(HeartMeasurementRepository repository) {
    this.repository = repository;
  }

  @Override
  public void bind(final HeartAssistantView view) {
    this.view = view;
    repository.observe(new HeartChangedListener() {
      @Override
      public void onHeartChanged(HeartMeasurement heartMeasurement) {
        view.showLast(heartMeasurement);
      }
    });
    repository.retrieveAll(new HeartListLoadedListener() {
      @Override
      public void onHeartListLoaded(List<HeartMeasurement> heartMeasurements) {
        view.showList(heartMeasurements);
      }
    });
  }

  @Override
  public void unbind() {
    //TODO: cleanup resources
  }
}
