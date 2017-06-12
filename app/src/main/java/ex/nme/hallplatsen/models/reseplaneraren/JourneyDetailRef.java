package ex.nme.hallplatsen.models.reseplaneraren;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JourneyDetailRef {

    @SerializedName("ref")
    @Expose
    private String ref;

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

}