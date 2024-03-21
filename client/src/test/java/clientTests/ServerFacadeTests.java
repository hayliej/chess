package clientTests;

import org.junit.jupiter.api.*;
import server.Server;
import ui.ServerFacade;


public class ServerFacadeTests {

    private static Server server;

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(8080);
        System.out.println("Started test HTTP server on " + port);
        ServerFacade facade = new ServerFacade(String.valueOf(port));
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }


    //login positive
    @Test
    public void loginPositive() {

        //Assertions.assertTrue(true);
    }
    //login negative
    @Test
    public void loginNegative() {

        //Assertions.assertTrue(true);
    }


    //register positive
    @Test
    public void registerPositive() {

        //Assertions.assertTrue(true);
    }
    //register negative
    @Test
    public void registerNegative() {

        //Assertions.assertTrue(true);
    }


    //logout positive
    @Test
    public void logoutPositive() {

        //Assertions.assertTrue(true);
    }
    //logout negative
    @Test
    public void logoutNegative() {

        //Assertions.assertTrue(true);
    }


    //createGame positive
    @Test
    public void createGamePositive() {

        //Assertions.assertTrue(true);
    }
    //createGame negative
    @Test
    public void createGameNegative() {

        //Assertions.assertTrue(true);
    }


    //joinGame positive
    @Test
    public void joinGamePositive() {

        //Assertions.assertTrue(true);
    }
    //joinGame negative
    @Test
    public void joinGameNegative() {

        //Assertions.assertTrue(true);
    }


    //observeGame positive
    @Test
    public void observeGamePositive() {

        //Assertions.assertTrue(true);
    }
    //observeGame negative
    @Test
    public void observeGameNegative() {

        //Assertions.assertTrue(true);
    }

}
