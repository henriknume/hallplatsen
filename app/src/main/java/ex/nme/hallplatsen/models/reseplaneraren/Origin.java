
package ex.nme.hallplatsen.models.reseplaneraren;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Origin {

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
    @SerializedName("$")
    @Expose
    private String $;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Origin() {
    }

    /**
     * 
     * @param routeIdx
     * @param id
     * @param rtDate
     * @param time
     * @param $
     * @param name
     * @param track
     * @param rtTime
     * @param date
     * @param type
     */
    public Origin(String name, String type, String id, String routeIdx, String time, String date, String track, String rtTime, String rtDate, String $) {
        super();
        this.name = name;
        this.type = type;
        this.id = id;
        this.routeIdx = routeIdx;
        this.time = time;
        this.date = date;
        this.track = track;
        this.rtTime = rtTime;
        this.rtDate = rtDate;
        this.$ = $;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Origin withName(String name) {
        this.name = name;
        return this;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Origin withType(String type) {
        this.type = type;
        return this;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Origin withId(String id) {
        this.id = id;
        return this;
    }

    public String getRouteIdx() {
        return routeIdx;
    }

    public void setRouteIdx(String routeIdx) {
        this.routeIdx = routeIdx;
    }

    public Origin withRouteIdx(String routeIdx) {
        this.routeIdx = routeIdx;
        return this;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Origin withTime(String time) {
        this.time = time;
        return this;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Origin withDate(String date) {
        this.date = date;
        return this;
    }

    public String getTrack() {
        return track;
    }

    public void setTrack(String track) {
        this.track = track;
    }

    public Origin withTrack(String track) {
        this.track = track;
        return this;
    }

    public String getRtTime() {
        return rtTime;
    }

    public void setRtTime(String rtTime) {
        this.rtTime = rtTime;
    }

    public Origin withRtTime(String rtTime) {
        this.rtTime = rtTime;
        return this;
    }

    public String getRtDate() {
        return rtDate;
    }

    public void setRtDate(String rtDate) {
        this.rtDate = rtDate;
    }

    public Origin withRtDate(String rtDate) {
        this.rtDate = rtDate;
        return this;
    }

    public String get$() {
        return $;
    }

    public void set$(String $) {
        this.$ = $;
    }

    public Origin with$(String $) {
        this.$ = $;
        return this;
    }

}
