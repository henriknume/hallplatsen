package ex.nme.hallplatsen.services;

import java.io.IOException;

import ex.nme.hallplatsen.Constants;
import ex.nme.hallplatsen.models.responses.TokenResponse;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by nume on 2017-07-05
 */

class TokenStorage {

    //public static final String TOKEN_PREFERENCES_FILE = "ex.nme.hallplatsen.token.preferences.file";
    //public static final String BEARER_TOKEN = "ex.nme.hallplatsen.bearer.token";

    private static String bearerToken = "uninitialized";

    public static void setToken(String newToken){
        bearerToken = newToken;
    }

    public static String getToken(){
        return bearerToken;
    }

    public static String refreshToken(){
        Call<TokenResponse> call = Reseplaneraren.getInstance().getService().generateToken(Constants.AUTH, "client_credentials", Constants.DEVICE_ID);
        try {
            //Syncronous call
            Response<TokenResponse> response =  call.execute();
            setToken(response.body().getAccessToken());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bearerToken;
    }
}
