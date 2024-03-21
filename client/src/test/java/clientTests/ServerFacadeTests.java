package clientTests;

import org.junit.jupiter.api.*;
import results.LogoutResult;
import results.RegisterResult;
import server.Server;
import ui.ServerFacade;

import static org.junit.jupiter.api.Assertions.*;


public class ServerFacadeTests {

    private static Server server;
    static ServerFacade facade;

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
        facade = new ServerFacade("http://localhost:" + String.valueOf(port));
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
    public void loginNegative() {   //NOT WORKING
        RegisterResult res = facade.login(null, "password");
        String message = res.message();
        assertNotNull(message);
    }


    //register positive
    @Test
    public void registerPositive() {
        RegisterResult res = facade.register("newUsername", "password", "email");
        String user = res.username();
        assertEquals("newUsername", user);
    }

    //register negative
    @Test
    public void registerNegative() {
        RegisterResult res = facade.register(null, "password", "email");
        String message = res.message();
        assertNotNull(message);
    }


    //logout positive
    @Test
    public void logoutPositive() {
        RegisterResult res = facade.register("newUsername", "password", "email");
        LogoutResult lres = facade.logout(res.authToken());
        String message = lres.message();
        assertNotNull(message);
    }
    //logout negative
    @Test
    public void logoutNegative() {
        RegisterResult res = facade.register("newUsername", "password", "email");
        LogoutResult lres = facade.logout(null);
        String message = lres.message();
        assertTrue(message.startsWith("Error"));
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
