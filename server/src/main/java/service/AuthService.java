package service;
import dataAccess.*;
import requests.*;
import results.LogoutResult;
import results.RegisterResult;

import java.util.Map;
import java.util.UUID;

public class AuthService {

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


    public static RegisterResult login(LoginRequest logReq) throws DataAccessException {
        if (logReq.username() ==null || logReq.password()==null){
            return new RegisterResult("Error: unauthorized", null, null);
        } else if (!(uDataAccess.returnUsers().containsKey(logReq.username()))){
            return new RegisterResult("Error: unauthorized", null, null);
        } else if (uDataAccess.returnUsers().containsKey(logReq.username())){
            if (!(uDataAccess.returnUsers().get(logReq.username()).password().equals(logReq.password()))) {
            return new RegisterResult("Error: unauthorized", null, null);
            }
        }
        String authToken = UUID.randomUUID().toString();
        aDataAccess.addAuth(new AuthData(authToken, logReq.username()));
        return new RegisterResult(null, logReq.username(), authToken);
    }

    public static LogoutResult logout(String authTok) throws DataAccessException {
        if (!(aDataAccess.returnAuths().containsKey(authTok))){
            return new LogoutResult("Error: unauthorized");
        }
        aDataAccess.removeAuth(authTok);
        return new LogoutResult("");
    }

    public void clear() throws DataAccessException {
        uDataAccess.clear();
        aDataAccess.clear();
    }

    //for unit test
    public Map<Object, Object> getMap() throws DataAccessException {
        return aDataAccess.returnAuths();
    }
}

