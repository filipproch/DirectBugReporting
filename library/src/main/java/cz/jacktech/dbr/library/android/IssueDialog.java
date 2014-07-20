package cz.jacktech.dbr.library.android;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import cz.jacktech.dbr.library.R;
import cz.jacktech.dbr.library.ReportingService;

/**
 * Created by toor on 20.7.14.
 */
public class IssueDialog extends AlertDialog{

    private ReportingService service;
    private OnClickListener positiveListener;
    private OnClickListener negativeListener;
    private OnClickListener defaultNegativeListener = new OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            if(negativeListener != null)
                negativeListener.onClick(dialog, which);
            dialog.dismiss();
        }
    };
    private OnClickListener defaultPositiveListener = new OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            new ReportSender(dialog).execute();
        }
    };

    public IssueDialog(Context context) {
        super(context);
    }

    public ReportingService getService() {
        return service;
    }

    public void setService(ReportingService service) {
        this.service = service;
    }

    public void setPositiveListener(OnClickListener positiveListener) {
        this.positiveListener = positiveListener;
    }

    public void setNegativeListener(OnClickListener negativeListener) {
        this.negativeListener = negativeListener;
    }

    private class ReportSender extends AsyncTask<Void, Void, Boolean>{

        private DialogInterface dialog;

        public ReportSender(DialogInterface dialog) {
            this.dialog = dialog;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            String username = ((EditText)findViewById(R.id.username)).getText().toString();
            String password = ((EditText)findViewById(R.id.password)).getText().toString();
            String issueName = ((EditText)findViewById(R.id.report_title)).getText().toString();
            String issueText = ((EditText)findViewById(R.id.report_text)).getText().toString();
            if(service.auth(username, password)){
                if(service.report(issueName, issueText)){
                    return true;
                }
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if(success){
                if(positiveListener != null)
                    positiveListener.onClick(dialog, 0);
                Toast.makeText(getContext(), R.string.send_successfull, Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(getContext(), R.string.send_failed, Toast.LENGTH_LONG).show();
            }
        }
    }

    public static class Builder{
        private IssueDialog dialog;
        private Context context;

        public Builder(Context context){
            this.context = context;
            dialog = new IssueDialog(context);
            setTitle(R.string.default_title);
        }

        public Builder setService(ReportingService service) {
            dialog.setService(service);
            return this;
        }

        public Builder setTitle(String title){
            dialog.setTitle(title);
            return this;
        }

        public Builder setTitle(int titleResource){
            dialog.setTitle(titleResource);
            return this;
        }

        public Builder setSendButton(String text, final OnClickListener listener){
            dialog.setButton(DialogInterface.BUTTON_POSITIVE, text, dialog.defaultPositiveListener);
            return this;
        }

        public Builder setCancelButton(String text, final OnClickListener listener){
            dialog.setButton(DialogInterface.BUTTON_NEGATIVE, text, dialog.defaultNegativeListener);
            return this;
        }

        public IssueDialog build(){
            if(dialog.getButton(DialogInterface.BUTTON_POSITIVE) == null){
                dialog.setButton(DialogInterface.BUTTON_POSITIVE, context.getString(R.string.send_report), dialog.defaultPositiveListener);
            }
            if(dialog.getButton(DialogInterface.BUTTON_NEGATIVE) == null){
                dialog.setButton(DialogInterface.BUTTON_NEGATIVE, context.getString(R.string.cancel), dialog.defaultNegativeListener);
            }
            dialog.setView(((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.default_reporting_dialog,null));
            return dialog;
        }

        public IssueDialog show(){
            IssueDialog dialog = build();
            dialog.show();
            return dialog;
        }
    }

}
