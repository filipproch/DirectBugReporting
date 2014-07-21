package cz.jacktech.dbr.library.services;

import android.os.Bundle;

import cz.jacktech.dbr.library.ReportingService;

/**
 * Created by toor on 20.7.14.
 */
public class BugzillaService extends ReportingService {

    @Override
    public void create(String serverUrl) {
        super.create(serverUrl);
    }

    @Override
    public Bundle report(String title, String text) {
        return null;
    }

    @Override
    public Bundle auth(String username, String password) {
        return null;
    }
}
