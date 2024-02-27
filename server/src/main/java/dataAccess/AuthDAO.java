package dataAccess;

import requests.AuthData;

import java.util.Map;

public interface AuthDAO {
    //make map of auths

    //clear
    public void clear();

    //createAuth
    public void addAuth(AuthData authdata) throws DataAccessException;

    //return map
    Map<Object, Object> returnAuths();

    //getAuth

    //deleteAuth
}
