
package ex.nme.hallplatsen.models.reseplaneraren;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Destination {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("routeIdx")
    @Expose
    private String routeIdx;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("track")
    @Expose
    private String track;
    @SerializedName("rtTime")
    @Expose
    private String rtTime;
    @SerializedName("rtDate")
    @Expose
    private String rtDate;
    @SerializedName("Notes")
    @Expose
    private Notes notes;
    @SerializedName("$")
    @Expose
    private String $;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRouteIdx() {
        return routeIdx;
    }

    public void setRouteIdx(String routeIdx) {
        this.routeIdx = routeIdx;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTrack() {
        return track;
    }

    public void setTrack(String track) {
        this.track = track;
    }

    public String getRtTime() {
        return rtTime;
    }

    public void setRtTime(String rtTime) {
        this.rtTime = rtTime;
    }

    public String getRtDate() {
        return rtDate;
    }

    public void setRtDate(String rtDate) {
        this.rtDate = rtDate;
    }

    public Notes getNotes() {
        return notes;
    }

    public void setNotes(Notes notes) {
        this.notes = notes;
    }

    public String get$() {
        return $;
    }

    public void set$(String $) {
        this.$ = $;
    }

}
