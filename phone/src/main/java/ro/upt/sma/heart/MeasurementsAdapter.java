package ro.upt.sma.heart;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import ro.upt.sma.heart.MeasurementsAdapter.HeartMeasurementHolder;
import ro.upt.sma.heart.model.HeartMeasurement;

public class MeasurementsAdapter extends RecyclerView.Adapter<HeartMeasurementHolder> {

  private final List<HeartMeasurement> heartMeasurements;

  MeasurementsAdapter(List<HeartMeasurement> heartMeasurements) {
    this.heartMeasurements = heartMeasurements;
  }

  @Override
  public HeartMeasurementHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new HeartMeasurementHolder(
        LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_measurement, parent, false));
  }

  @Override
  public void onBindViewHolder(HeartMeasurementHolder holder, int position) {
    HeartMeasurement heartMeasurement = heartMeasurements.get(position);
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String formattedDate = df.format(new Date(heartMeasurement.timestamp));
    holder.bind(heartMeasurement.value + "bpm", formattedDate);
  }

  @Override
  public int getItemCount() {
    return heartMeasurements.size();
  }

  class HeartMeasurementHolder extends RecyclerView.ViewHolder {

    TextView tvValue;
    TextView tvDate;

    HeartMeasurementHolder(View itemView) {
      super(itemView);

      tvValue = itemView.findViewById(R.id.tv_measurement_item_value);
      tvDate = itemView.findViewById(R.id.tv_measurement_item_date);
    }

    void bind(String value, String date) {
      tvValue.setText(value);
      tvDate.setText(date);
    }

  }

}
