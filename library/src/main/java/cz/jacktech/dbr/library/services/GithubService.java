package cz.jacktech.dbr.library.services;

import android.util.Log;

import cz.jacktech.dbr.library.ReportingService;
import cz.jacktech.dbr.library.communication.CustomRequestInterceptor;
import cz.jacktech.dbr.library.communication.IGithub;
import cz.jacktech.dbr.library.communication.IRedmine;
import cz.jacktech.dbr.library.communication.data.User;
import cz.jacktech.dbr.library.communication.data.github.GithubIssue;
import cz.jacktech.dbr.library.communication.data.github.GithubResponse;
import cz.jacktech.dbr.library.communication.data.redmine.RedmineRequestBody;
import cz.jacktech.dbr.library.communication.data.redmine.RedmineResponse;
import retrofit.RestAdapter;
import retrofit.RetrofitError;

/**
 * Created by toor on 20.7.14.
 */
public class GithubService implements ReportingService {

    private static final String TAG = GithubService.class.getSimpleName();
    private String projectOwner;
    private String projectRepo;
    private CustomRequestInterceptor requestInterceptor;
    private IGithub github;

    public GithubService(String projectOwner, String projectRepo){
        this.projectOwner = projectOwner;
        this.projectRepo = projectRepo;
    }

    @Override
    public void create(String serverUrl) {
        RestAdapter.Builder builder = new RestAdapter.Builder();
        builder.setRequestInterceptor(requestInterceptor = new CustomRequestInterceptor());
        builder.setEndpoint("https://github.com");
        RestAdapter adapter = builder.build();
        github = adapter.create(IGithub.class);
    }

    @Override
    public boolean report(String title, String text) {
        try {
            GithubResponse.IssueCreation issueCreation = github.createIssue(projectOwner,projectRepo,new GithubIssue(title, text));
        }catch (RetrofitError e){
            Log.e(TAG, "retrofit error; network:" + e.isNetworkError() + "; " + e.getLocalizedMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean auth(String username, String password) {
        requestInterceptor.setUser(new User(username, password));
        return true;
    }
}
