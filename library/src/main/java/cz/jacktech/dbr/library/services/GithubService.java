package cz.jacktech.dbr.library.services;

import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStreamReader;

import cz.jacktech.dbr.library.ReportingService;
import cz.jacktech.dbr.library.communication.CustomErrorHandler;
import cz.jacktech.dbr.library.communication.IGithubApi;
import cz.jacktech.dbr.library.communication.data.User;
import cz.jacktech.dbr.library.communication.data.github.GithubIssue;
import cz.jacktech.dbr.library.communication.data.github.GithubResponse;
import retrofit.RetrofitError;

/**
 * Created by toor on 20.7.14.
 */
public class GithubService extends ReportingService {

    private static final String TAG = GithubService.class.getSimpleName();
    private String projectOwner;
    private String projectRepo;
    private IGithubApi githubApi;

    public GithubService(String projectOwner, String projectRepo){
        this.projectOwner = projectOwner;
        this.projectRepo = projectRepo;
    }

    @Override
    public void create(String serverUrl) {
        super.create("https://api.github.com");
        githubApi = adapter.create(IGithubApi.class);
    }

    @Override
    public Bundle report(String title, String text) {
        Log.v(TAG, "sending report");
        Bundle b = new Bundle();
        try {
            GithubResponse.IssueCreation issueCreation = githubApi.createIssue(projectOwner,projectRepo,new GithubIssue(title, text));
            b.putBoolean(ReportingService.ACTION_SUCCESS, true);
            b.putString(RESPONSE_ISSUE_URL, issueCreation.html_url);
        }catch (Exception e){
            if(e.getCause() instanceof CustomErrorHandler.UnauthorizedException){
                Log.e(TAG, "report - retrofit error; network:" + ((CustomErrorHandler.UnauthorizedException) e).getRetrofitError().isNetworkError() + "; " + e.getLocalizedMessage());
                b.putBoolean(ReportingService.ACTION_SUCCESS, false);
                b.putString(ReportingService.RESPONSE_MESSAGE, "Unauthorized");
            }else if(e.getCause() instanceof RetrofitError){
                Log.e(TAG, "report - retrofit error; network:" + ((RetrofitError) e).isNetworkError() + "; " + e.getLocalizedMessage());
                b.putBoolean(ReportingService.ACTION_SUCCESS, false);
                b.putString(ReportingService.RESPONSE_MESSAGE, "Internal error");
            }else{
                Log.e(TAG, "report - error; "+e.getLocalizedMessage());
                e.printStackTrace();
            }
        }
        return b;
    }

    @Override
    public Bundle auth(String username, String password) {
        Bundle b = new Bundle();
        requestInterceptor.setUser(new User(username, password));
        try{
            GithubResponse.UserAuthentication authentication = githubApi.authUser();
            Log.v(TAG, "auth - response; " + authentication.message);
            b.putBoolean(ReportingService.ACTION_SUCCESS, true);
        }catch (Exception e){
            Log.e(TAG, "auth - exception: "+e.getLocalizedMessage());
            b.putBoolean(ReportingService.ACTION_SUCCESS, false);
            b.putString(ReportingService.RESPONSE_MESSAGE, "Authentication error");
            if(e.getCause() instanceof CustomErrorHandler.UnauthorizedException){
                Log.e(TAG, "auth - retrofit error; network:" + ((CustomErrorHandler.UnauthorizedException) e).getRetrofitError().isNetworkError() + "; " + e.getLocalizedMessage());
                try {
                    GithubResponse.UserAuthentication authentication = new Gson().fromJson(new InputStreamReader(((CustomErrorHandler.UnauthorizedException) e).getRetrofitError().getResponse().getBody().in()), GithubResponse.UserAuthentication.class);
                    b.putBoolean(ReportingService.ACTION_SUCCESS, false);
                    b.putString(ReportingService.RESPONSE_MESSAGE, authentication.message);
                    Log.v(TAG, "response message: "+authentication.message);
                } catch (IOException e1) {}
            }
        }
        return b;
    }
}
