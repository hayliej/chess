package service;
import dataAccess.*;
import requests.UserData;


public class UserService {
    //what do we do with this??
    private static MemUserDAO dataAccess = new MemUserDAO();

    public UserService() {
        this.dataAccess = dataAccess;
    }

    //THINGS SERVICE NEEDS TO DO: ???
    //getUser
    public static Object getUser(String username){
        Object user = dataAccess.returnUsers().get(username);

        return user;
    }

    //createUser
    public static Object createUser(UserData userData){
        dataAccess.users.put(userData.getUsername(), String.valueOf(userData));
        return "";
    }

    //clearDB
    public void clear() { dataAccess.clear();}

    //^NEED TO THROW EXCEPTIONS?
}
