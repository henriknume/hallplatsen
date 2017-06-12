package ex.nme.hallplatsen.models.reseplaneraren;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "name",
    "lon",
    "lat",
    "id",
    "idx"
})
public class StopLocation {

    @JsonProperty("name")
    private String name;
    @JsonProperty("lon")
    private String lon;
    @JsonProperty("lat")
    private String lat;
    @JsonProperty("id")
    private String id;
    @JsonProperty("idx")
    private String idx;

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("lon")
    public String getLon() {
        return lon;
    }

    @JsonProperty("lon")
    public void setLon(String lon) {
        this.lon = lon;
    }

    @JsonProperty("lat")
    public String getLat() {
        return lat;
    }

    @JsonProperty("lat")
    public void setLat(String lat) {
        this.lat = lat;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("idx")
    public String getIdx() {
        return idx;
    }

    @JsonProperty("idx")
    public void setIdx(String idx) {
        this.idx = idx;
    }

}
