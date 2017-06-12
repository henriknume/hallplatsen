
package ex.nme.hallplatsen.models.reseplaneraren;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CoordLocation {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("lon")
    @Expose
    private String lon;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("idx")
    @Expose
    private String idx;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIdx() {
        return idx;
    }

    public void setIdx(String idx) {
        this.idx = idx;
    }

}
