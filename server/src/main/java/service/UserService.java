package service;
import dataAccess.*;
import requests.*;

import java.util.UUID;


public class UserService {
    //what do we do with this??
    private static MemUserDAO UDataAccess = new MemUserDAO();
    private static MemAuthDAO ADataAccess = new MemAuthDAO();


    public UserService() {
        this.UDataAccess = UDataAccess;
        this.ADataAccess = ADataAccess;
    }

    //THINGS SERVICE NEEDS TO DO: ???
    //getUser
    public static Object getUser(UserData u){
        String username = u.getUsername();
        Object user = UDataAccess.returnUsers().get(username);
        if (user.equals(null)) {
            createUser(u);
            ADataAccess.auths.put(username, UUID.randomUUID().toString());
            String authToken = ADataAccess.auths.get(username);
            return new Result("{ \"username\":\""+username +"\", \"authToken\":\""+authToken+"\" }");
        } //put ifs in service class
        return user;
    }

    //createUser
    public static Object createUser(UserData userData){
        UDataAccess.users.put(userData.getUsername(), String.valueOf(userData));
        return "";
    }

    //clearDB
    public void clear() { UDataAccess.clear();}

    //^NEED TO THROW EXCEPTIONS?
}
