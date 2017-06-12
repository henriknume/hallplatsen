
package ex.nme.hallplatsen.models.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import ex.nme.hallplatsen.models.reseplaneraren.LocationList;

public class LocationNameResponse {

    @SerializedName("LocationList")
    @Expose
    private LocationList locationList;

    public LocationList getLocationList() {
        return locationList;
    }

    public void setLocationList(LocationList locationList) {
        this.locationList = locationList;
    }

}
