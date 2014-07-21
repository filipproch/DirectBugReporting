package cz.jacktech.dbr.library.communication;

import cz.jacktech.dbr.library.communication.data.github.GithubIssue;
import cz.jacktech.dbr.library.communication.data.github.GithubResponse;
import cz.jacktech.dbr.library.communication.data.redmine.RedmineResponse;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by toor on 21.7.14.
 */
public interface IGithubApi {

    @POST("/repos/{user}/{repo}/issues")
    public GithubResponse.IssueCreation createIssue(@Path("user")String user, @Path("repo")String repo, @Body GithubIssue body);

    @GET("/")
    public GithubResponse.UserAuthentication authUser();

}
