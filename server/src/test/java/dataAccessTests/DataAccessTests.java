package dataAccessTests;

import dataAccess.*;
import org.junit.jupiter.api.BeforeEach;
import requests.AuthData;
import requests.UserData;

import java.util.UUID;

public class DataAccessTests {
    static GameDAO gDAO;

    static {
        try {
            gDAO = new SqlGameDAO();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    static AuthDAO aDAO;

    static {
        try {
            aDAO = new SqlAuthDAO();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    static UserDAO uDAO;

    static {
        try {
            uDAO = new SqlUserDAO();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeEach
    public void setup() throws DataAccessException {
        gDAO.clear();
        aDAO.clear();
        uDAO.clear();

        UserData user = new UserData("username", "password", "email@byu.edu");
        try {
            uDAO.addUser(user);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }

        String authToken = UUID.randomUUID().toString();
        AuthData auth = new AuthData(authToken, user.username());
        try {
            aDAO.addAuth(auth);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    //CLEAR


    //USER
    //

    //AUTH


    //GAME
}
