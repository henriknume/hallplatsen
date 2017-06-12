package ex.nme.hallplatsen.models.reseplaneraren;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Trip {

    @SerializedName("Leg")
    @Expose
    private Leg leg;

    public Leg getLeg() {
        return leg;
    }

    public void setLeg(Leg leg) {
        this.leg = leg;
    }

}