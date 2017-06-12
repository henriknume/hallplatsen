package ex.nme.hallplatsen.models.reseplaneraren;

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
    private List<Trip> trip = null;

    public String getNoNamespaceSchemaLocation() {
        return noNamespaceSchemaLocation;
    }

    public void setNoNamespaceSchemaLocation(String noNamespaceSchemaLocation) {
        this.noNamespaceSchemaLocation = noNamespaceSchemaLocation;
    }

    public String getServertime() {
        return servertime;
    }

    public void setServertime(String servertime) {
        this.servertime = servertime;
    }

    public String getServerdate() {
        return serverdate;
    }

    public void setServerdate(String serverdate) {
        this.serverdate = serverdate;
    }

    public List<Trip> getTrip() {
        return trip;
    }

    public void setTrip(List<Trip> trip) {
        this.trip = trip;
    }

}