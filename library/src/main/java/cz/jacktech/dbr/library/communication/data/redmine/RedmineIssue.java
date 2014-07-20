package cz.jacktech.dbr.library.communication.data.redmine;

import com.google.gson.annotations.SerializedName;

/**
 * Created by toor on 20.7.14.
 */
public class RedmineIssue {

    public RedmineIssue(int projectId, String subject, String description, int priorityId){
        this.projectId = projectId;
        this.subject = subject;
        this.description = description;
        this.priorityId = priorityId;
    }

    @SerializedName("project_id")
    public int projectId;

    /*@SerializedName("tracker_id")
    public int trackerId;

    @SerializedName("status_id")
    public int statusId;*/

    @SerializedName("priority_id")
    public int priorityId;

    public String subject;

    public String description;

    /*@SerializedName("category_id")
    public int categoryId;

    @SerializedName("fixed_version_id")
    public int fixedVersionId;

    @SerializedName("assigned_to_id")
    public int assignedToId;

    @SerializedName("parent_issue_id")
    public int parentIssueId;

    //todo: add custom fields support*/

}
