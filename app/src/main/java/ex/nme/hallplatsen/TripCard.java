package ex.nme.hallplatsen;

import java.util.ArrayList;
import java.util.List;

import ex.nme.hallplatsen.models.reseplaneraren.StopLocation;
import ex.nme.hallplatsen.models.reseplaneraren.Trip;

/**
 * Created by nm2 on 2017-07-12
 */

public class TripCard {

    private static TripCard instance;
    private String timeLastUpdated = "";
    private StopLocation from;
    private StopLocation to;
    private List<Trip> tripList;

    public TripCard() {
        timeLastUpdated = "";
        from = null;
        to = null;
        tripList = new ArrayList<>();
    }

    public static TripCard getInstance(){
        if(instance == null){
            instance = new TripCard();
        }
        return instance;
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
            return from.getName();
        }
        return "unselected";
    }

    public String getFromId() {
        if(from == null){
            throw new NullPointerException("From Location is not initialized");
        }
        return from.getId();
    }

    public String getToName() {
        if (to != null) {
            return to.getName();
        }
        return "unselected";
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

}
