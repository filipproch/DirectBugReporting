package cz.jacktech.dbr.library;

/**
 * Created by toor on 20.7.14.
 */
public interface ReportingService {

    public void create(String serverUrl);
    public boolean report(String title, String text);
    public boolean auth(String username, String password);

}
