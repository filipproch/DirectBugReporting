package cz.jacktech.dbr.library.services;

import cz.jacktech.dbr.library.ReportingService;

/**
 * Created by toor on 20.7.14.
 */
public class GithubService implements ReportingService {

    @Override
    public void create(String serverUrl) {

    }

    @Override
    public boolean report(String title, String text) {
        return false;
    }

    @Override
    public boolean auth(String username, String password) {
        return false;
    }
}
