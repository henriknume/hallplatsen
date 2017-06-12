package ex.nme.hallplatsen.models.reseplaneraren;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Leg {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("sname")
    @Expose
    private String sname;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("direction")
    @Expose
    private String direction;
    @SerializedName("fgColor")
    @Expose
    private String fgColor;
    @SerializedName("bgColor")
    @Expose
    private String bgColor;
    @SerializedName("stroke")
    @Expose
    private String stroke;
    @SerializedName("accessibility")
    @Expose
    private String accessibility;
    @SerializedName("Origin")
    @Expose
    private Origin origin;
    @SerializedName("Destination")
    @Expose
    private Destination destination;
    @SerializedName("JourneyDetailRef")
    @Expose
    private JourneyDetailRef journeyDetailRef;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
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

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getFgColor() {
        return fgColor;
    }

    public void setFgColor(String fgColor) {
        this.fgColor = fgColor;
    }

    public String getBgColor() {
        return bgColor;
    }

    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }

    public String getStroke() {
        return stroke;
    }

    public void setStroke(String stroke) {
        this.stroke = stroke;
    }

    public String getAccessibility() {
        return accessibility;
    }

    public void setAccessibility(String accessibility) {
        this.accessibility = accessibility;
    }

    public Origin getOrigin() {
        return origin;
    }

    public void setOrigin(Origin origin) {
        this.origin = origin;
    }

    public Destination getDestination() {
        return destination;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    public JourneyDetailRef getJourneyDetailRef() {
        return journeyDetailRef;
    }

    public void setJourneyDetailRef(JourneyDetailRef journeyDetailRef) {
        this.journeyDetailRef = journeyDetailRef;
    }

}