package dataAccess;

import requests.AuthData;

import java.util.HashMap;
import java.util.Map;

public class MemAuthDAO implements AuthDAO {
    //make map of auths
    private static Map<String,String> auths = new HashMap<>();

    //clear
    public void clear() {
        Map<String,String> auths = new HashMap<>();
    }

    @Override
    public void addAuth(AuthData authdata) {
        auths.put(authdata.authToken(), authdata.username());
    }

    //return map
    public Map returnAuths(){
        return auths;
    }

}
