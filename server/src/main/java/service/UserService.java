package service;
import dataAccess.*;
import requests.*;
import results.RegisterResult;

import java.util.Map;
import java.util.UUID;


public class UserService {
    //what do we do with this??
    private static UserDAO uDataAccess;

    static {
        try {
            uDataAccess = new SqlUserDAO();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static AuthDAO aDataAccess;

    static {
        try {
            aDataAccess = new SqlAuthDAO();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }


    //getUser
    public static RegisterResult getUser(UserData u) throws DataAccessException {
        String username = u.getUsername();
//        Object user = UDataAccess.returnUsers().get(username);
        if (u.username() ==null || u.password()==null || u.email()==null){
            return new RegisterResult("Error: bad request", null, null);
        }
        else if (!(uDataAccess.returnUsers().containsKey(username))) {
            createUser(u);
            String authToken = UUID.randomUUID().toString();
            aDataAccess.addAuth(new AuthData(authToken, username));
//            String authToken = ADataAccess.returnAuths().get(username);
            return new RegisterResult(null, username, authToken);
        } else if (uDataAccess.returnUsers().containsKey(username)) {
            return new RegisterResult("Error: already taken", null, null);
        } //else if ()
        return new RegisterResult(null, null, null);
    }

    //createUser
    public static Object createUser(UserData userData){
        try {
            if (userData.username() !=null && userData.password()!=null && userData.email()!=null){
                uDataAccess.addUser(userData);
            }
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        return "";
    }

    //clearDB
    public void clear() throws DataAccessException {
        uDataAccess.clear();
        aDataAccess.clear();
    }

    //for unit test
    public Map<String, UserData> getMap() throws DataAccessException {
        return uDataAccess.returnUsers();
    }

}
