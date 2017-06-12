package ex.nme.hallplatsen.models.reseplaneraren;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "noNamespaceSchemaLocation",
    "servertime",
    "serverdate",
    "StopLocation",
    "CoordLocation"
})
public class LocationList {

    @JsonProperty("noNamespaceSchemaLocation")
    private String noNamespaceSchemaLocation;
    @JsonProperty("servertime")
    private String servertime;
    @JsonProperty("serverdate")
    private String serverdate;
    @JsonProperty("StopLocation")
    private List<StopLocation> stopLocation = null;
    @JsonProperty("CoordLocation")
    private List<CoordLocation> coordLocation = null;

    @JsonProperty("noNamespaceSchemaLocation")
    public String getNoNamespaceSchemaLocation() {
        return noNamespaceSchemaLocation;
    }

    @JsonProperty("noNamespaceSchemaLocation")
    public void setNoNamespaceSchemaLocation(String noNamespaceSchemaLocation) {
        this.noNamespaceSchemaLocation = noNamespaceSchemaLocation;
    }

    @JsonProperty("servertime")
    public String getServertime() {
        return servertime;
    }

    @JsonProperty("servertime")
    public void setServertime(String servertime) {
        this.servertime = servertime;
    }

    @JsonProperty("serverdate")
    public String getServerdate() {
        return serverdate;
    }

    @JsonProperty("serverdate")
    public void setServerdate(String serverdate) {
        this.serverdate = serverdate;
    }

    @JsonProperty("StopLocation")
    public List<StopLocation> getStopLocation() {
        return stopLocation;
    }

    @JsonProperty("StopLocation")
    public void setStopLocation(List<StopLocation> stopLocation) {
        this.stopLocation = stopLocation;
    }

    @JsonProperty("CoordLocation")
    public List<CoordLocation> getCoordLocation() {
        return coordLocation;
    }

    @JsonProperty("CoordLocation")
    public void setCoordLocation(List<CoordLocation> coordLocation) {
        this.coordLocation = coordLocation;
    }

}
