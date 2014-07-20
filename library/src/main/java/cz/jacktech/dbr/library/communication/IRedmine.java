package cz.jacktech.dbr.library.communication;

import cz.jacktech.dbr.library.communication.data.redmine.RedmineRequestBody;
import cz.jacktech.dbr.library.communication.data.redmine.RedmineResponse;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;

/**
 * Created by toor on 20.7.14.
 */
public interface IRedmine {

    @POST("/issues.json")
    public RedmineResponse.IssueCreation createIssue(@Body RedmineRequestBody body);

    @GET("/users/current.json")
    public RedmineResponse.UserAuthentication authUser();

}
