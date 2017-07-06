package ex.nme.hallplatsen.services;

import ex.nme.hallplatsen.Constants;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by nume on 2017-07-03
 */

public class Reseplaneraren {

    private static final String TAG = "Reseplaneraren";

    private static Reseplaneraren instance;
    private static ReseplanerarenRestService  mService;

    private  Reseplaneraren() {
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new AppendTokenInterceptor())
                .authenticator(new TokenAuthenticator())
                .build();

        Retrofit retrofit  = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();

        mService = retrofit.create(ReseplanerarenRestService.class);
    }

    public static ReseplanerarenRestService getService(){
        if (instance == null || mService == null) {
            instance = new Reseplaneraren();
        }
        return mService;
    }

}
