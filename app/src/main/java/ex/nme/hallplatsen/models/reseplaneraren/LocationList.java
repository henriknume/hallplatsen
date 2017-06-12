
package ex.nme.hallplatsen.models.reseplaneraren;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LocationList {

    @SerializedName("noNamespaceSchemaLocation")
    @Expose
    private String noNamespaceSchemaLocation;

    @SerializedName("servertime")
    @Expose
    private String servertime;

    @SerializedName("serverdate")
    @Expose
    private String serverdate;

    @SerializedName("StopLocation")
    @Expose
    private List<StopLocation> stopLocation = null;

    @SerializedName("CoordLocation")
    @Expose
    private List<CoordLocation> coordLocation = null;

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

    public List<StopLocation> getStopLocation() {
        return stopLocation;
    }

    public void setStopLocation(List<StopLocation> stopLocation) {
        this.stopLocation = stopLocation;
    }

    public List<CoordLocation> getCoordLocation() {
        return coordLocation;
    }

    public void setCoordLocation(List<CoordLocation> coordLocation) {
        this.coordLocation = coordLocation;
    }

}
