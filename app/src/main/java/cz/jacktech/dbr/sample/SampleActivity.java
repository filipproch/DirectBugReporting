package cz.jacktech.dbr.sample;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import cz.jacktech.dbr.library.ReportingService;
import cz.jacktech.dbr.library.android.IssueDialog;
import cz.jacktech.dbr.library.services.RedmineService;


public class SampleActivity extends Activity {

    private ReportingService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        service = new RedmineService(5);//setting project id
        service.create("http://bugs.jacktech.cz");//settings server url
        Button b = (Button) findViewById(R.id.report_button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IssueDialog.Builder builder = new IssueDialog.Builder(SampleActivity.this);
                builder.setService(service)
                .show();
            }
        });
    }
}
