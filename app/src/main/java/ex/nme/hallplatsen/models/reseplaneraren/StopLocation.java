
package ex.nme.hallplatsen.models.reseplaneraren;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StopLocation {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("lon")
    @Expose
    private String lon;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("id")
    @Expose
    private String id;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdx() {
        return idx;
    }

    public void setIdx(String idx) {
        this.idx = idx;
    }

    @Override
    public String toString(){
        return "name:" + name +
                "\nlon:" + lon +
                "\nlat:" + lat +
                "\nid:" + id +
                "\nidx:" + idx;
    }

}
