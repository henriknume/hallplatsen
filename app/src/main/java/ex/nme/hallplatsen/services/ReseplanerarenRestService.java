package ex.nme.hallplatsen.services;

import ex.nme.hallplatsen.models.responses.LocationNameResponse;
import ex.nme.hallplatsen.models.responses.TokenResponse;
import ex.nme.hallplatsen.models.responses.TripResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by nm2 on 2017-06-12
 */

public interface ReseplanerarenRestService {

    public static final String BASE = "bin/rest.exe/v2";

    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @POST("token")
    Call<TokenResponse> generateToken(
            @Header("Authorization") String authorization,           // "Basic base64(key:secret)"
            @Field("grant_type") String grantType,          // "client_credentials"
            @Field("scope") String scope);                  // "device_id"

    @GET(BASE + "/location.name")
    Call<LocationNameResponse> getLocationsByName(
            @Header("Authorization") String token,
            @Query("input") String name,
            @Query("format") String format
    );

    @GET(BASE + "/trip")
    Call<TripResponse> getTrip(
            @Header("Authorization") String token,
            @Query("originId") String originId,
            @Query("destId") String destId,
            @Query("date") String date,             // YYYY-MM-DD
            @Query("time") String time,             // HH:MM
            @Query("format") String format          // json
    );


}
