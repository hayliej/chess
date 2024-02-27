package service;
import dataAccess.*;
import requests.AuthData;
import requests.LoginRequest;
import requests.RegisterResult;
import requests.UserData;

import java.util.UUID;

public class AuthService {

    private static UserDAO UDataAccess = new MemUserDAO();
    private static AuthDAO ADataAccess = new MemAuthDAO();


    public AuthService() {
        this.UDataAccess = UDataAccess;
        this.ADataAccess = ADataAccess;
    }

    public static RegisterResult login(LoginRequest logReq) throws DataAccessException {
        if (logReq.username() ==null || logReq.password()==null){
            return new RegisterResult("Error: unauthorized", null, null);
        } else if (!(UDataAccess.returnUsers().containsKey(logReq.username()))){
            return new RegisterResult("Error: unauthorized", null, null);
        } else if (UDataAccess.returnUsers().containsKey(logReq.username())){
            if (!(UDataAccess.returnUsers().get(logReq.username()).password().equals(logReq.password()))) {
            return new RegisterResult("Error: unauthorized", null, null);
            }
        }
        String authToken = UUID.randomUUID().toString();
        ADataAccess.addAuth(new AuthData(authToken, logReq.username()));
        return new RegisterResult(null, logReq.username(), authToken);
    }

    //THINGS SERVICE NEEDS TO DO:
    //createAuth
    //getAuth
    //clearDB
    public void clear() {
        UDataAccess.clear();
        ADataAccess.clear();
    }
    //^NEED TO THROW EXCEPTIONS?
}

