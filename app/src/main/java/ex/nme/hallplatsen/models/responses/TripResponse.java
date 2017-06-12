package ex.nme.hallplatsen.models.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import ex.nme.hallplatsen.models.reseplaneraren.TripList;

public class TripResponse {

    @SerializedName("TripList")
    @Expose
    private TripList tripList;

    public TripList getTripList() {
        return tripList;
    }

    public void setTripList(TripList tripList) {
        this.tripList = tripList;
    }

}