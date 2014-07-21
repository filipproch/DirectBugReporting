package cz.jacktech.dbr.library.communication;

import cz.jacktech.dbr.library.communication.data.github.GithubIssue;
import cz.jacktech.dbr.library.communication.data.github.GithubResponse;
import retrofit.http.Body;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by toor on 20.7.14.
 */
public interface IGithub {

    @POST("/repos/{user}/{repo}/issues")
    public GithubResponse.IssueCreation createIssue(@Path("user")String user, @Path("repo")String repo, @Body GithubIssue body);

}
