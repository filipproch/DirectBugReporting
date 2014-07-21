package cz.jacktech.dbr.library.services;

import android.os.Bundle;
import android.util.Log;

import cz.jacktech.dbr.library.ReportingService;
import cz.jacktech.dbr.library.communication.CustomRequestInterceptor;
import cz.jacktech.dbr.library.communication.IRedmine;
import cz.jacktech.dbr.library.communication.data.User;
import cz.jacktech.dbr.library.communication.data.redmine.RedmineRequestBody;
import cz.jacktech.dbr.library.communication.data.redmine.RedmineResponse;
import retrofit.RestAdapter;
import retrofit.RetrofitError;

/**
 * Created by toor on 20.7.14.
 */
public class RedmineService extends ReportingService {

    private static final String TAG = RedmineService.class.getSimpleName();
    private IRedmine redmine;
    private int projectId;

    public RedmineService(int projectId){
        this.projectId = projectId;
    }

    @Override
    public void create(String serverUrl) {
        super.create(serverUrl);
        redmine = adapter.create(IRedmine.class);
    }

    @Override
    public Bundle report(String title, String text) {
        return report(title, text, 3); //4 is default Normal priority
    }

    /**
     *
     * @param title
     * @param text
     * @param priorityId
     */
    private Bundle report(String title, String text, int priorityId){
        //todo: find how to get priority ids, otherwise they must be defined in configuration...
        Bundle b = new Bundle();
        try {
            RedmineResponse.IssueCreation issueCreation = redmine.createIssue(new RedmineRequestBody(projectId, title, text, priorityId));
            b.putBoolean(ReportingService.ACTION_SUCCESS, true);
            b.putString(ReportingService.RESPONSE_ISSUE_URL, "TODO");
        }catch (RetrofitError e){
            Log.e(TAG, "report - retrofit error; network:"+e.isNetworkError()+"; "+e.getLocalizedMessage());
            b.putBoolean(ReportingService.ACTION_SUCCESS, false);
            b.putString(ReportingService.RESPONSE_MESSAGE, e.getLocalizedMessage());
        }
        return b;
    }

    @Override
    public Bundle auth(String username, String password) {
        Bundle b = new Bundle();
        requestInterceptor.setUser(new User(username, password));
        try {
            RedmineResponse.UserAuthentication authResponse = redmine.authUser();
            b.putBoolean(ReportingService.ACTION_SUCCESS, true);
            b.putString(ReportingService.RESPONSE_MESSAGE, "TODO");
        }catch (RetrofitError e){
            Log.e(TAG, "auth - retrofit error; network:"+e.isNetworkError()+"; "+e.getLocalizedMessage());
            b.putBoolean(ReportingService.ACTION_SUCCESS, false);
            b.putString(ReportingService.RESPONSE_MESSAGE, e.getLocalizedMessage());
        }
        return b;
    }

}
