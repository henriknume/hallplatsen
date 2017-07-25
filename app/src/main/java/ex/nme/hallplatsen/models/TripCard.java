package ex.nme.hallplatsen.models;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ex.nme.hallplatsen.models.reseplaneraren.StopLocation;
import ex.nme.hallplatsen.models.reseplaneraren.Trip;

/**
 * Created by nm2 on 2017-07-12
 */

public class TripCard {

    private static final String TAG = "TripCard";

    private String timeLastUpdated;
    private StopLocation from;
    private StopLocation to;
    private List<Trip> tripList;

    public TripCard() {
        timeLastUpdated = "";
        from = null;
        to = null;
        tripList = new ArrayList<>();
    }

    public void setTimeLastUpdated(String time) {
        this.timeLastUpdated = time;
    }

    public void setFromLocation(StopLocation from) {
        this.from = from;
    }

    public void setToLocation(StopLocation to) {
        this.to = to;
    }

    public void setTripList(List<Trip> list) {
        this.tripList = list;
    }

    public String getTimeLastUpdated() {
        return timeLastUpdated;
    }

    public String getFromName() {
        if (from != null) {
            return trimTextAfterAndInclComma(from.getName());
        }
        return "<Unselected>"; //TODO: fix this
    }

    public String getFromId() {
        if(from == null){
            throw new NullPointerException("From Location is not initialized");
        }
        return from.getId();
    }

    public String getToName() {
        if (to != null) {
            return trimTextAfterAndInclComma(to.getName());
        }
        return "<Unselected>"; //TODO: fix this
    }

    public String getToId() {
        if(to == null){
            throw new NullPointerException("To Location is not initialized");
        }
        return to.getId();
    }

    public List<Trip> getTripList() {
        return tripList;
    }

    public void switchToAndFromLocations() {
        // Switch
        StopLocation temp = from;
        from = to;
        to = temp;
    }

    public boolean isLocationsSelected() {
        // TODO: Also check the ID strings inside each location;
        return (to != null && from != null);
    }

    private String trimTextAfterAndInclComma(String text) {
        if (text.contains(",")){
            String[] parts = text.split(",");
            return parts[0];
        } else {
            return text;
        }
    }

}
