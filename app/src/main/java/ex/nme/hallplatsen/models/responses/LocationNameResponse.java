
package ex.nme.hallplatsen.models.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import ex.nme.hallplatsen.models.reseplaneraren.LocationList;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "LocationList"
})
public class LocationNameResponse {

    @JsonProperty("LocationList")
    private LocationList locationList;

    @JsonProperty("LocationList")
    public LocationList getLocationList() {
        return locationList;
    }

    @JsonProperty("LocationList")
    public void setLocationList(LocationList locationList) {
        this.locationList = locationList;
    }

}
