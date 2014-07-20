package cz.jacktech.dbr.library.communication.data.github;

/**
 * Created by toor on 20.7.14.
 */
public class GithubIssue {

    public String title;
    public String body;

    public GithubIssue(String title, String body) {
        this.title = title;
        this.body = body;
    }
}
