package cz.jacktech.dbr.library;

import android.os.Bundle;

import cz.jacktech.dbr.library.communication.CustomErrorHandler;
import cz.jacktech.dbr.library.communication.CustomRequestInterceptor;
import retrofit.RestAdapter;

/**
 * Created by toor on 20.7.14.
 */
public abstract class ReportingService {

    public static final String ACTION_SUCCESS = "success";
    public static final String RESPONSE_MESSAGE = "message";
    public static final String RESPONSE_ISSUE_URL = "issue_url";

    protected CustomRequestInterceptor requestInterceptor;
    protected CustomErrorHandler errorHandler;
    protected RestAdapter adapter;

    public void create(String serverUrl){
        if(requestInterceptor == null)
            requestInterceptor = new CustomRequestInterceptor();
        if(errorHandler == null)
            errorHandler = new CustomErrorHandler();
        adapter = new RestAdapter.Builder()
                .setRequestInterceptor(requestInterceptor)
                .setErrorHandler(errorHandler)
                .setEndpoint(serverUrl)
                .build();
    }
    public abstract Bundle report(String title, String text);
    public abstract Bundle auth(String username, String password);

}
