package cz.jacktech.dbr.library.android;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cz.jacktech.dbr.library.R;
import cz.jacktech.dbr.library.ReportingService;

/**
 * Created by toor on 20.7.14.
 */
public class IssueDialog extends AlertDialog{

    private ReportingService service;
    private View.OnClickListener positiveListener;
    private View.OnClickListener negativeListener;
    private View.OnClickListener defaultNegativeListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if(negativeListener != null)
                negativeListener.onClick(v);
            dismiss();
        }

    };
    private View.OnClickListener defaultPositiveListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            new ReportSender().execute();
        }
    };
    private OnShowListener showListener = new OnShowListener() {
        @Override
        public void onShow(DialogInterface dialog) {
            Button b = getButton(AlertDialog.BUTTON_POSITIVE);
            b.setOnClickListener(defaultPositiveListener);
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

    public void setPositiveListener(View.OnClickListener positiveListener) {
        this.positiveListener = positiveListener;
    }

    public void setNegativeListener(View.OnClickListener negativeListener) {
        this.negativeListener = negativeListener;
    }

    private class ReportSender extends AsyncTask<Void, Void, Bundle>{

        @Override
        protected Bundle doInBackground(Void... params) {
            String username = ((EditText)findViewById(R.id.username)).getText().toString();
            String password = ((EditText)findViewById(R.id.password)).getText().toString();
            String issueName = ((EditText)findViewById(R.id.report_title)).getText().toString();
            String issueText = ((EditText)findViewById(R.id.report_text)).getText().toString();
            Bundle b = service.auth(username, password);
            if(b.getBoolean(ReportingService.ACTION_SUCCESS)){
                b = service.report(issueName, issueText);
            }
            return b;
        }

        @Override
        protected void onPostExecute(Bundle bundle) {
            if(bundle.getBoolean(ReportingService.ACTION_SUCCESS)){
                if(positiveListener != null)
                    positiveListener.onClick(null);
                Toast.makeText(getContext(), getContext().getString(R.string.send_successfull)+", url: "+bundle.getString(ReportingService.RESPONSE_ISSUE_URL), Toast.LENGTH_LONG).show();
                dismiss();
            }else{
                if(bundle.containsKey(ReportingService.RESPONSE_MESSAGE))
                    Toast.makeText(getContext(), getContext().getString(R.string.send_failed)+", "+bundle.getString(ReportingService.RESPONSE_MESSAGE), Toast.LENGTH_LONG).show();
                else
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
            dialog.setButton(DialogInterface.BUTTON_POSITIVE, text, (OnClickListener)null);
            return this;
        }

        public Builder setCancelButton(String text, final OnClickListener listener){
            dialog.setButton(DialogInterface.BUTTON_NEGATIVE, text, (OnClickListener)null);
            return this;
        }

        public IssueDialog build(){
            if(dialog.getButton(DialogInterface.BUTTON_POSITIVE) == null){
                dialog.setButton(DialogInterface.BUTTON_POSITIVE, context.getString(R.string.send_report), (OnClickListener)null);
            }
            if(dialog.getButton(DialogInterface.BUTTON_NEGATIVE) == null){
                dialog.setButton(DialogInterface.BUTTON_NEGATIVE, context.getString(R.string.cancel), (OnClickListener)null);
            }
            dialog.setView(((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.default_reporting_dialog,null));
            dialog.setOnShowListener(dialog.showListener);
            return dialog;
        }

        public IssueDialog show(){
            IssueDialog dialog = build();
            dialog.show();
            return dialog;
        }
    }

}
