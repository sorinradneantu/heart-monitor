package ro.upt.sma.heart.model;

public class HeartMeasurement {

  public final long timestamp;
  public final int value;

  public HeartMeasurement(long timestamp, int value) {
    this.timestamp = timestamp;
    this.value = value;
  }

  @Override
  public String toString() {
    return "HeartMeasurement{" +
        "timestamp=" + timestamp +
        ", value=" + value +
        '}';
  }

}
