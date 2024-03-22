package clientTests;

import org.junit.jupiter.api.*;
import results.CreateGameResult;
import results.ListGamesResult;
import results.LogoutResult;
import results.RegisterResult;
import server.Server;
import dataAccess.DataAccessException;
import ui.ServerFacade;
import java.util.ArrayList;
import java.util.List;

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

    @BeforeEach
    public void clear() throws DataAccessException {
        facade.clear();
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }


    //login positive
    @Test
    public void loginPositive() throws DataAccessException {
        facade.register("username", "password", "email");
        RegisterResult res = facade.login("username", "password");
        String user = res.username();
        assertEquals("username", user);
    }
    //login negative
    @Test
    public void loginNegative() throws DataAccessException {
        assertThrows(DataAccessException.class, () -> facade.login(null, "password"));
    }


    //register positive
    @Test
    public void registerPositive() throws DataAccessException {
        RegisterResult res = facade.register("newUsername", "password", "email");
        String user = res.username();
        assertEquals("newUsername", user);
    }

    //register negative
    @Test
    public void registerNegative() throws DataAccessException {
        assertThrows(DataAccessException.class, () -> facade.register(null, "password", "email"));
    }


    //logout positive
    @Test
    public void logoutPositive() throws DataAccessException {
        RegisterResult res = facade.register("newUsername", "password", "email");
        LogoutResult lres = facade.logout(res.authToken());
        String message = lres.message();
        assertEquals("",message);
    }
    //logout negative
    @Test
    public void logoutNegative() throws DataAccessException {
        assertThrows(DataAccessException.class, () -> facade.logout(null));
    }


    //createGame positive
    @Test
    public void createGamePositive() throws DataAccessException {
        RegisterResult res = facade.register("newUsername", "password", "email");
        CreateGameResult cres = facade.createGame(res.authToken(), "name");
        String message = cres.message();
        assertNull(message);
    }

    //createGame negative
    @Test
    public void createGameNegative() throws DataAccessException {
        facade.register("newUsername", "password", "email");
        assertThrows(DataAccessException.class, () -> facade.createGame(null, null));
    }


    //joinGame positive
    @Test
    public void joinGamePositive() throws DataAccessException {
        RegisterResult res = facade.register("newUsername", "password", "email");
        CreateGameResult cres = facade.createGame(res.authToken(), "name");
        LogoutResult jres = facade.joinGame(res.authToken(), cres.gameID(), "WHITE");
        String message = jres.message();
        assertNull(message);
    }
    //joinGame negative
    @Test
    public void joinGameNegative() throws DataAccessException {
        RegisterResult res = facade.register("newUsername", "password", "email");
        CreateGameResult cres = facade.createGame(res.authToken(), "name");
        assertThrows(DataAccessException.class, () -> facade.joinGame(null, null, "WHITE"));
    }


    //observeGame positive
    @Test
    public void observeGamePositive() throws DataAccessException {
        RegisterResult res = facade.register("newUsername", "password", "email");
        CreateGameResult cres = facade.createGame(res.authToken(), "name");
        LogoutResult ores = facade.observeGame(res.authToken(), cres.gameID());
        String message = ores.message();
        assertNull(message);
    }
    //observeGame negative
    @Test
    public void observeGameNegative() throws DataAccessException {
        RegisterResult res = facade.register("newUsername", "password", "email");
        CreateGameResult cres = facade.createGame(res.authToken(), "name");
        assertThrows(DataAccessException.class, () -> facade.observeGame(null, null));
    }


    //list positive
    @Test
    public void listGamesPositive() throws DataAccessException {
        RegisterResult res = facade.register("newUsername", "password", "email");
        facade.createGame(res.authToken(), "name");
        ListGamesResult lres = facade.listGames(res.authToken());
        String message = lres.message();
        assertNull(message);
    }

    //list negative
    @Test
    public void listGamesNegative() throws DataAccessException {
        facade.register("newUsername", "password", "email");
        ListGamesResult lres = facade.listGames(null);
        List games = lres.games();
        List list = new ArrayList();
        assertEquals(list, games);
    }
}
