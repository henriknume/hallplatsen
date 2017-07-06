package ex.nme.hallplatsen.services;

import android.media.session.MediaSession;

import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

/**
 * Created by nume on 2017-07-06
 */

public class TokenAuthenticator implements Authenticator {
    @Override
    public Request authenticate(Route route, Response response) throws IOException {
        // Refresh your access_token using a synchronous api request
        String token = TokenStorage.refreshToken();

        // Add new header to rejected request and retry it
        return response.request().newBuilder()
                .header("Authorization", "Bearer " + token)
                .build();
    }
}