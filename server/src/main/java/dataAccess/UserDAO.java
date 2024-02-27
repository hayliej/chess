package dataAccess;
import requests.AuthData;
import requests.UserData;

import java.util.Map;

public interface UserDAO {
    //make  map of users
    //clear
    public void clear();

    //createUser
    public void addUser(UserData userdata) throws DataAccessException;

    //getUser

    //return map
    Map<Object, Object> returnUsers();
}
