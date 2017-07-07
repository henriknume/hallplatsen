package ex.nme.hallplatsen.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import ex.nme.hallplatsen.Constants;
import ex.nme.hallplatsen.models.reseplaneraren.Leg;
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

        // Custom gson for handling conversion of json-object to java-array.
        Type legListType = new TypeToken<List<Leg>>() {}.getType();
        Gson customGson = new GsonBuilder()
                .registerTypeAdapter(legListType, new LegTypeAdapter())
                .create();

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new AppendTokenInterceptor())
                .authenticator(new TokenAuthenticator())
                .build();

        Retrofit retrofit  = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(customGson))
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
