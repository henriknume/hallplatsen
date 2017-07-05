package ex.nme.hallplatsen.services;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by nume on 2017-07-05
 */

public class AppendTokenInterceptor implements Interceptor {

    private TokenStorage tokenStorage;

    public AppendTokenInterceptor(TokenStorage tokenStorage){
        this.tokenStorage = tokenStorage;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        String token = tokenStorage.getToken();
        Request newRequest = request.newBuilder()
                .addHeader("Authorization", "Bearer " + token)
                .build();
        return chain.proceed(newRequest);
    }
}
