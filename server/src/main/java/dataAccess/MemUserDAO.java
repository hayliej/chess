package dataAccess;

import requests.UserData;

import java.util.HashMap;
import java.util.Map;

public class MemUserDAO implements UserDAO {
    //make  map of users
    private static Map<String,UserData> users = new HashMap<>();
    //clear
    public void clear() { users.clear(); }

    @Override
    public void addUser(UserData userdata) throws DataAccessException {
        users.put(userdata.getUsername(), userdata);
    }

    //return map
    public Map returnUsers(){
        return users;
    }
}
