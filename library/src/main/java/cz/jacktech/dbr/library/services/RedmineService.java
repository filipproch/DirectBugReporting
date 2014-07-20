package cz.jacktech.dbr.library.services;

import android.util.Log;

import com.google.gson.Gson;

import cz.jacktech.dbr.library.ReportingService;
import cz.jacktech.dbr.library.communication.IRedmine;
import cz.jacktech.dbr.library.communication.RedmineRequestInterceptor;
import cz.jacktech.dbr.library.communication.data.User;
import cz.jacktech.dbr.library.communication.data.redmine.RedmineRequestBody;
import cz.jacktech.dbr.library.communication.data.redmine.RedmineResponse;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by toor on 20.7.14.
 */
public class RedmineService implements ReportingService {

    private static final String TAG = RedmineService.class.getSimpleName();
    private IRedmine redmine;
    private int projectId;
    private RedmineRequestInterceptor requestInterceptor;

    public RedmineService(int projectId){
        this.projectId = projectId;
    }

    @Override
    public void create(String serverUrl) {
        RestAdapter.Builder builder = new RestAdapter.Builder();
        builder.setRequestInterceptor(requestInterceptor = new RedmineRequestInterceptor());
        builder.setEndpoint(serverUrl);
        RestAdapter adapter = builder.build();
        redmine = adapter.create(IRedmine.class);
    }

    @Override
    public boolean report(String title, String text) {
        return report(title, text, 4); //4 is default Normal priority
    }

    /**
     *
     * @param title
     * @param text
     * @param priorityId
     */
    private boolean report(String title, String text, int priorityId){
        //todo: find how to get priority ids, otherwise they must be defined in configuration...
        try {
            RedmineResponse.IssueCreation issueCreation = redmine.createIssue(new RedmineRequestBody(projectId, title, text, priorityId));
        }catch (RetrofitError e){
            Log.e(TAG, "retrofit error; network:"+e.isNetworkError()+"; "+e.getLocalizedMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean auth(String username, String password) {
        requestInterceptor.setUser(new User(username, password));
        try {
            RedmineResponse.UserAuthentication authResponse = redmine.authUser();
        }catch (RetrofitError e){
            return false;
        }
        return true;
    }

}
