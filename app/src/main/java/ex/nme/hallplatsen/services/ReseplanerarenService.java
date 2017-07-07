package ex.nme.hallplatsen.services;

import ex.nme.hallplatsen.Constants;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by nume on 2017-07-03
 */

public class ReseplanerarenService {

    private static final String TAG = "ReseplanerarenService";

    private static ReseplanerarenService instance;
    private static ReseplanerarenRestApi mService;

    private ReseplanerarenService() {
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new AppendTokenInterceptor())
                .authenticator(new TokenAuthenticator())
                .build();

        Retrofit retrofit  = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();

        mService = retrofit.create(ReseplanerarenRestApi.class);
    }

    public static ReseplanerarenRestApi getService(){
        if (instance == null || mService == null) {
            instance = new ReseplanerarenService();
        }
        return mService;
    }

}
