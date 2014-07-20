package cz.jacktech.dbr.library.services;

import junit.framework.TestCase;

import java.util.Random;

public class RedmineServiceTest extends TestCase {

    private RedmineService service;

    public RedmineServiceTest(){
        service = new RedmineService(5);
        service.create("http://bugs.jacktech.cz");
    }

    public void testReport() throws Exception {
        Random r = new Random();

        assertTrue(service.auth("test","12345678"));
        assertTrue(service.report("test issue "+r.nextLong(), "lorem ipsum dolor sir amet..."));
        //todo: check if report created by resting issue list api... /issues.json?project_id=2
    }

    public void testAuth() throws Exception {
        assertTrue(service.auth("test","12345678"));
    }
}