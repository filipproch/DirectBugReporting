package cz.jacktech.dbr.library.communication;

import cz.jacktech.dbr.library.communication.data.github.GithubIssue;
import cz.jacktech.dbr.library.communication.data.github.GithubResponse;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by toor on 20.7.14.
 */
public interface IGithub {

    @POST("/repos/{user}/{repo}/issues")
    public GithubResponse.IssueCreation createIssue(String repo, @Body GithubIssue body);

}
