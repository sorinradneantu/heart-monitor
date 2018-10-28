package ro.upt.sma.heart;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.widget.TextView;
import java.text.MessageFormat;
import java.util.List;
import ro.upt.sma.heart.model.HeartMeasurement;
import ro.upt.sma.heart.presenters.assistant.HeartAssistantPresenter;
import ro.upt.sma.heart.presenters.assistant.HeartAssistantView;
import ro.upt.upt.sma.heart.injection.Injection;

public class HeartAssistantActivity extends AppCompatActivity implements HeartAssistantView {

  private static final String TAG = HeartAssistantActivity.class.getSimpleName();

  private TextView tvInstantValue;
  private TextView tvInstantDate;
  private RecyclerView rvMeasurements;

  private HeartAssistantPresenter presenter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_heart_assistant);

    tvInstantValue = findViewById(R.id.tv_heart_assistant_instant_value);
    tvInstantDate = findViewById(R.id.tv_heart_assistant_instant_date);
    rvMeasurements = findViewById(R.id.rv_heart_assistant_measurement_list);
    rvMeasurements.setLayoutManager(new LinearLayoutManager(this));

    String code = "3c8c";

    this.presenter = Injection.provideHeartAssistantPresenter(code);
  }

  @Override
  protected void onResume() {
    super.onResume();

    presenter.bind(this);
  }

  @Override
  protected void onPause() {
    super.onPause();

    presenter.unbind();
  }

  @Override
  public void showLast(HeartMeasurement heartMeasurement) {
    Log.d(TAG, "showLast: " + heartMeasurement);

    tvInstantValue.setText(MessageFormat.format("{0} bpm", heartMeasurement.value));
    tvInstantDate.setText(DateUtils
        .getRelativeTimeSpanString(heartMeasurement.timestamp, System.currentTimeMillis(),
            DateUtils.SECOND_IN_MILLIS));
  }

  @Override
  public void showList(List<HeartMeasurement> heartMeasurementList) {
    Log.d(TAG, "showList: " + heartMeasurementList.size());

    rvMeasurements.setAdapter(new MeasurementsAdapter(heartMeasurementList));
  }

}
