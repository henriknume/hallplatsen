package ex.nme.hallplatsen.services;

import ex.nme.hallplatsen.models.responses.LocationNameResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by nm2 on 2017-06-12
 */

public interface ReseplanerarenRestService {
    @GET("location.name?input=kviberg&format=json")
    Call<LocationNameResponse> getLocationsByName(
            @Query("input") String name,
            @Query("format") String format);
}
