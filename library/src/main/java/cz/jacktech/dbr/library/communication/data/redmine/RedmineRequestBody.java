package cz.jacktech.dbr.library.communication.data.redmine;

import com.google.gson.annotations.SerializedName;

/**
 * Created by toor on 20.7.14.
 */

public class RedmineRequestBody {

    public RedmineRequestBody(int projectId, String subject, String description, int priorityId){
        issue = new RedmineIssue(projectId, subject, description, priorityId);
    }

    public RedmineIssue issue;

}
