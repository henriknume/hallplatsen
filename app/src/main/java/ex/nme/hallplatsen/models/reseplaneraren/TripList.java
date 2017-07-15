
package ex.nme.hallplatsen.models.reseplaneraren;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TripList {

    @SerializedName("noNamespaceSchemaLocation")
    @Expose
    private String noNamespaceSchemaLocation;
    @SerializedName("servertime")
    @Expose
    private String servertime;
    @SerializedName("serverdate")
    @Expose
    private String serverdate;
    @SerializedName("Trip")
    @Expose
    private List<Trip> trip = new ArrayList<Trip>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public TripList() {
    }

    /**
     * 
     * @param serverdate
     * @param servertime
     * @param trip
     * @param noNamespaceSchemaLocation
     */
    public TripList(String noNamespaceSchemaLocation, String servertime, String serverdate, List<Trip> trip) {
        super();
        this.noNamespaceSchemaLocation = noNamespaceSchemaLocation;
        this.servertime = servertime;
        this.serverdate = serverdate;
        this.trip = trip;
    }

    public String getNoNamespaceSchemaLocation() {
        return noNamespaceSchemaLocation;
    }

    public void setNoNamespaceSchemaLocation(String noNamespaceSchemaLocation) {
        this.noNamespaceSchemaLocation = noNamespaceSchemaLocation;
    }

    public TripList withNoNamespaceSchemaLocation(String noNamespaceSchemaLocation) {
        this.noNamespaceSchemaLocation = noNamespaceSchemaLocation;
        return this;
    }

    public String getServertime() {
        return servertime;
    }

    public void setServertime(String servertime) {
        this.servertime = servertime;
    }

    public TripList withServertime(String servertime) {
        this.servertime = servertime;
        return this;
    }

    public String getServerdate() {
        return serverdate;
    }

    public void setServerdate(String serverdate) {
        this.serverdate = serverdate;
    }

    public TripList withServerdate(String serverdate) {
        this.serverdate = serverdate;
        return this;
    }

    public List<Trip> getTrip() {
        return trip;
    }

    public void setTrip(List<Trip> trip) {
        this.trip = trip;
    }

    public TripList withTrip(List<Trip> trip) {
        this.trip = trip;
        return this;
    }

}
