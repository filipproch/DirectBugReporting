package cz.jacktech.dbr.sample;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import cz.jacktech.dbr.library.android.IssueDialog;
import cz.jacktech.dbr.library.services.GithubService;
import cz.jacktech.dbr.library.services.RedmineService;


public class SampleActivity extends Activity {

    private RedmineService redmineService;
    private GithubService githubService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        redmineService = new RedmineService(5);//setting project id
        redmineService.create("http://bugs.jacktech.cz");//settings server url
        Button b = (Button) findViewById(R.id.redmine_report_button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IssueDialog.Builder builder = new IssueDialog.Builder(SampleActivity.this);
                builder.setService(redmineService)
                .show();
            }
        });

        githubService = new GithubService("DirectBugReporting");//setting github repository
        githubService.create(null);//for github always pass null
        b = (Button) findViewById(R.id.github_report_button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IssueDialog.Builder builder = new IssueDialog.Builder(SampleActivity.this);
                builder.setService(githubService)
                        .show();
            }
        });

    }
}
