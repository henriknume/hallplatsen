package ex.nme.hallplatsen.services;

import android.content.Context;
import android.media.session.MediaSession;

import java.io.IOException;

import ex.nme.hallplatsen.Constants;
import okhttp3.Authenticator;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.Route;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by nume on 2017-07-03
 */

public class Reseplaneraren {

    private static final String TAG = "Reseplaneraren";

    private static Reseplaneraren instance;
    private ReseplanerarenRestService mService;

    private  Reseplaneraren(Context context) {

        // Logging interceptor
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        TokenStorage tokenStorage = new TokenStorage(context);
        AppendTokenInterceptor tokenInterceptor = new AppendTokenInterceptor(tokenStorage);

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .authenticator(new Authenticator() {
                    @Override
                    public Request authenticate(Route route, Response response) throws IOException {
                        System.out.println("Authenticating for response: " + response);
                        System.out.println("Challenges: " + response.challenges());
                        String credential = Constants.AUTH;

                        RequestBody formBody = new FormBody.Builder()
                                .add("grant_type", "client_credentials")
                                .add("device_id", Constants.DEVICE_ID)
                                .build();

                        return response.request().newBuilder()
                                .header("Content-Type", "application/x-www-form-urlencoded")
                                .header("Authorization", credential)
                                .url(Constants.BASE_URL + "token")
                                .post(formBody)
                                .build();
                    }
                })
                .addInterceptor(tokenInterceptor)
                .addInterceptor(logging)
                .build();

        Retrofit retrofit  = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();

        mService = retrofit.create(ReseplanerarenRestService.class);
    }

    public static Reseplaneraren getInstance(Context context) {
        if (instance == null) {
            instance = new Reseplaneraren(context);
        }
        return instance;
    }

    public ReseplanerarenRestService getService(){
        return mService;
    }

}
