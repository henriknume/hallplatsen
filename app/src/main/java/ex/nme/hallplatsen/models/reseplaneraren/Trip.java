
package ex.nme.hallplatsen.models.reseplaneraren;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Trip {

    @SerializedName("Leg")
    @Expose
    private List<Leg> leg = new ArrayList<Leg>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public Trip() {
    }

    /**
     * 
     * @param leg
     */
    public Trip(List<Leg> leg) {
        super();
        this.leg = leg;
    }

    public List<Leg> getLeg() {
        return leg;
    }

    public void setLeg(List<Leg> leg) {
        this.leg = leg;
    }

    public Trip withLeg(List<Leg> leg) {
        this.leg = leg;
        return this;
    }

}
