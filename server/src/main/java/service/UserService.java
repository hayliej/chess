package service;
import dataAccess.*;
import requests.*;

import java.util.UUID;


public class UserService {
    //what do we do with this??
    private static UserDAO UDataAccess = new MemUserDAO();
    private static AuthDAO ADataAccess = new MemAuthDAO();


    public UserService() {
        this.UDataAccess = UDataAccess;
        this.ADataAccess = ADataAccess;
    }

    //THINGS SERVICE NEEDS TO DO: ???
    //getUser
    public static RegisterResult getUser(UserData u) throws DataAccessException {
        String username = u.getUsername();
//        Object user = UDataAccess.returnUsers().get(username);
        if (u.username() ==null || u.password()==null || u.email()==null){
            return new RegisterResult("Error: bad request", null, null);
        }
        else if (!(UDataAccess.returnUsers().containsKey(username))) {
            createUser(u);
            String authToken = UUID.randomUUID().toString();
            ADataAccess.addAuth(new AuthData(authToken, username));
//            String authToken = ADataAccess.returnAuths().get(username);
            return new RegisterResult(null, username, authToken);
        } else if (UDataAccess.returnUsers().containsKey(username)) {
            return new RegisterResult("Error: already taken", null, null);
        } //else if ()
        return new RegisterResult(null, null, null);
    }

    //createUser
    public static Object createUser(UserData userData){
        try {
            UDataAccess.addUser(userData);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        return "";
    }

    //clearDB
    public void clear() { UDataAccess.clear();}

    //^NEED TO THROW EXCEPTIONS?
}
