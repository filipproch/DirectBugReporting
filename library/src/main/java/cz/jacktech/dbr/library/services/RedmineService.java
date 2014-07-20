package cz.jacktech.dbr.library.services;

import android.util.Log;

import com.google.gson.Gson;

import cz.jacktech.dbr.library.ReportingService;
import cz.jacktech.dbr.library.communication.IRedmine;
import cz.jacktech.dbr.library.communication.data.redmine.RedmineRequestBody;
import cz.jacktech.dbr.library.communication.data.redmine.RedmineResponse;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by toor on 20.7.14.
 */
public class RedmineService implements ReportingService, Callback<RedmineResponse> {

    private static final String TAG = RedmineService.class.getSimpleName();
    private IRedmine redmine;
    private int projectId;

    public RedmineService(int projectId){
        this.projectId = projectId;
    }

    @Override
    public void create(String serverUrl) {
        RestAdapter.Builder builder = new RestAdapter.Builder();
        builder.setEndpoint(serverUrl);
        RestAdapter adapter = builder.build();
        redmine = adapter.create(IRedmine.class);
    }

    @Override
    public void report(String title, String text) {
        report(title, text, 4); //4 is default Normal priority
    }

    /**
     *
     * @param title
     * @param text
     * @param priorityId
     */
    private void report(String title, String text, int priorityId){
        //todo: find how to get priority ids, otherwise they must be defined in configuration...
        Gson gson = new Gson();
        String body = gson.toJson(new RedmineRequestBody(projectId, title, text, priorityId));
        redmine.createIssue(body, this);
    }

    @Override
    public void auth(String username, String password) {

    }

    @Override
    public void success(RedmineResponse redmineResponse, Response response) {
        //todo: report succesfull send to the user, with link to the created issue
        Log.v(TAG, "issue creation request send successfully");
    }

    @Override
    public void failure(RetrofitError error) {
        //todo: report send failure to the user
        Log.w(TAG, "issue creation request send failed, networkError:"+error.isNetworkError());
    }
}
