package clientTests;

import org.junit.jupiter.api.*;
import results.RegisterResult;
import server.Server;
import ui.ServerFacade;

import static org.junit.jupiter.api.Assertions.*;


public class ServerFacadeTests {

    private static Server server;
    ServerFacade facade = new ServerFacade("http://localhost:8080");

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(8080);
        System.out.println("Started test HTTP server on " + port);
        ServerFacade facade = new ServerFacade("http://localhost:8080");
        facade.register("username", "password", "email");
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }


    //login positive
    @Test
    public void loginPositive() {
        RegisterResult res = facade.login("username", "password");
        String user = res.username();
        assertEquals("username", user);
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
