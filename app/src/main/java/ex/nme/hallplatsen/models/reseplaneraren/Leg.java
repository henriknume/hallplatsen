
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

    /**
     * No args constructor for use in serialization
     * 
     */
    public Leg() {
    }

    /**
     * 
     * @param bgColor
     * @param fgColor
     * @param id
     * @param stroke
     * @param journeyDetailRef
     * @param direction
     * @param name
     * @param origin
     * @param type
     * @param sname
     * @param destination
     * @param accessibility
     */
    public Leg(String name, String sname, String type, String id, String direction, String fgColor, String bgColor, String stroke, String accessibility, Origin origin, Destination destination, JourneyDetailRef journeyDetailRef) {
        super();
        this.name = name;
        this.sname = sname;
        this.type = type;
        this.id = id;
        this.direction = direction;
        this.fgColor = fgColor;
        this.bgColor = bgColor;
        this.stroke = stroke;
        this.accessibility = accessibility;
        this.origin = origin;
        this.destination = destination;
        this.journeyDetailRef = journeyDetailRef;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Leg withName(String name) {
        this.name = name;
        return this;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public Leg withSname(String sname) {
        this.sname = sname;
        return this;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Leg withType(String type) {
        this.type = type;
        return this;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Leg withId(String id) {
        this.id = id;
        return this;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public Leg withDirection(String direction) {
        this.direction = direction;
        return this;
    }

    public String getFgColor() {
        return fgColor;
    }

    public void setFgColor(String fgColor) {
        this.fgColor = fgColor;
    }

    public Leg withFgColor(String fgColor) {
        this.fgColor = fgColor;
        return this;
    }

    public String getBgColor() {
        return bgColor;
    }

    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }

    public Leg withBgColor(String bgColor) {
        this.bgColor = bgColor;
        return this;
    }

    public String getStroke() {
        return stroke;
    }

    public void setStroke(String stroke) {
        this.stroke = stroke;
    }

    public Leg withStroke(String stroke) {
        this.stroke = stroke;
        return this;
    }

    public String getAccessibility() {
        return accessibility;
    }

    public void setAccessibility(String accessibility) {
        this.accessibility = accessibility;
    }

    public Leg withAccessibility(String accessibility) {
        this.accessibility = accessibility;
        return this;
    }

    public Origin getOrigin() {
        return origin;
    }

    public void setOrigin(Origin origin) {
        this.origin = origin;
    }

    public Leg withOrigin(Origin origin) {
        this.origin = origin;
        return this;
    }

    public Destination getDestination() {
        return destination;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    public Leg withDestination(Destination destination) {
        this.destination = destination;
        return this;
    }

    public JourneyDetailRef getJourneyDetailRef() {
        return journeyDetailRef;
    }

    public void setJourneyDetailRef(JourneyDetailRef journeyDetailRef) {
        this.journeyDetailRef = journeyDetailRef;
    }

    public Leg withJourneyDetailRef(JourneyDetailRef journeyDetailRef) {
        this.journeyDetailRef = journeyDetailRef;
        return this;
    }

}
