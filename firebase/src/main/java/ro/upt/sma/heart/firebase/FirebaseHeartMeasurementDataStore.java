package ro.upt.sma.heart.firebase;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import ro.upt.sma.heart.model.HeartMeasurement;
import ro.upt.sma.heart.model.HeartMeasurementRepository;

public class FirebaseHeartMeasurementDataStore implements HeartMeasurementRepository {

  private final DatabaseReference reference;

  public FirebaseHeartMeasurementDataStore(String userId) {
    this.reference = FirebaseDatabase.getInstance().getReference().child(userId);
  }

  @Override
  public void post(HeartMeasurement heartMeasurement) {
    reference.child(heartMeasurement.timestamp + "").setValue(heartMeasurement.value);
  }

  @Override
  public void observe(final HeartChangedListener listener) {
    reference.addChildEventListener(new ChildEventListener() {
      @Override
      public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        listener.onHeartChanged(toHeartMeasurement(dataSnapshot));
      }

      @Override
      public void onChildChanged(DataSnapshot dataSnapshot, String s) {
      }

      @Override
      public void onChildRemoved(DataSnapshot dataSnapshot) {
      }

      @Override
      public void onChildMoved(DataSnapshot dataSnapshot, String s) {
      }

      @Override
      public void onCancelled(DatabaseError databaseError) {
      }
    });
  }

  @Override
  public void retrieveAll(final HeartListLoadedListener listener) {
    reference.addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
        if (dataSnapshot.hasChildren()) {
          listener.onHeartListLoaded(toHeartMeasurements(dataSnapshot.getChildren()));
        } else {
          listener.onHeartListLoaded(Collections.<HeartMeasurement>emptyList());
        }
      }

      @Override
      public void onCancelled(DatabaseError databaseError) {

      }
    });
  }


  private List<HeartMeasurement> toHeartMeasurements(Iterable<DataSnapshot> children) {
    List<HeartMeasurement> heartMeasurementList = new ArrayList<>();

    for (DataSnapshot child : children) {
      heartMeasurementList.add(toHeartMeasurement(child));
    }

    return heartMeasurementList;
  }

  private static HeartMeasurement toHeartMeasurement(DataSnapshot dataSnapshot) {
    return new HeartMeasurement(
        Long.valueOf(dataSnapshot.getKey()),
        dataSnapshot.getValue(Integer.class));
  }

}
