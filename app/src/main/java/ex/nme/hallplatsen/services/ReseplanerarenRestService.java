package ex.nme.hallplatsen.services;

import ex.nme.hallplatsen.models.responses.LocationNameResponse;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by nm2 on 2017-06-12
 */

public interface ReseplanerarenRestService {
    @GET("/qod.json")
    Call<LocationNameResponse> getLocationsByName(String name);
}
