package dataAccess;

import requests.AuthData;

import java.util.Map;

public interface AuthDAO {
    //make map of auths

    //clear
    public void clear() throws DataAccessException;

    //createAuth
    public void addAuth(AuthData authdata) throws DataAccessException;

    //remove auth
    public void removeAuth(String authToken);
    //return map
    Map<Object, Object> returnAuths();

    //getAuth
    String getVal(String auth);

    //deleteAuth
}
