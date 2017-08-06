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
    private Station from;
    private Station to;
    private List<Trip> tripList;
    private boolean loading;

    public TripCard() {
        this.timeLastUpdated = "";
        this.from = null;
        this.to = null;
        this.tripList = new ArrayList<>();
        this.loading = false;
    }

    public TripCard(Station from, Station to) {
        this.timeLastUpdated = "";
        this.from = from;
        this.to = to;
        this.tripList = new ArrayList<>();
        this.loading = false;
    }

    public boolean isLoading() {
        return loading;
    }

    public void startProgressBar() {
        loading = true;
    }

    public void stopProgressBar() {
        loading = false;
    }

    public void setTimeLastUpdated(String time) {
        this.timeLastUpdated = time;
    }

    public void setFromLocation(Station from) {
        this.from = from;
    }

    public void setToLocation(Station to) {
        this.to = to;
    }

    public void setTripList(List<Trip> list) {
        tripList.clear();
        tripList.addAll(list);
    }

    public void clearTripList() {
        tripList.clear();
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
        Station temp = from;
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
