package cz.jacktech.dbr.library.communication;

import android.util.Base64;

import cz.jacktech.dbr.library.communication.data.User;
import retrofit.RequestInterceptor;

/**
 * Created by toor on 20.7.14.
 */
public class CustomRequestInterceptor implements RequestInterceptor {

    private User user;

    @Override
    public void intercept(RequestFacade requestFacade) {

        if (user != null) {
            final String authorizationValue = encodeCredentialsForBasicAuthorization();
            requestFacade.addHeader("Authorization", authorizationValue);
        }
    }

    private String encodeCredentialsForBasicAuthorization() {
        final String userAndPassword = user.getUsername() + ":" + user.getPassword();
        final int flags = 0;
        return "Basic " + Base64.encodeToString(userAndPassword.getBytes(), flags);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
