
package ex.nme.hallplatsen.models.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import ex.nme.hallplatsen.models.reseplaneraren.TripList;

public class TripResponse {

    @SerializedName("TripList")
    @Expose
    private TripList tripList;

    /**
     * No args constructor for use in serialization
     * 
     */
    public TripResponse() {
    }

    /**
     * 
     * @param tripList
     */
    public TripResponse(TripList tripList) {
        super();
        this.tripList = tripList;
    }

    public TripList getTripList() {
        return tripList;
    }

    public void setTripList(TripList tripList) {
        this.tripList = tripList;
    }

    public TripResponse withTripList(TripList tripList) {
        this.tripList = tripList;
        return this;
    }

}
