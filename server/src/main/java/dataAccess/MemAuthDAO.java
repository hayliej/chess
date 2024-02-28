package dataAccess;

import requests.AuthData;

import java.util.HashMap;
import java.util.Map;

public class MemAuthDAO implements AuthDAO {
    //make map of auths
    private static Map<String,String> auths = new HashMap<>();

    //clear
    public void clear() {
        auths.clear();
    }

    @Override
    public void addAuth(AuthData authdata) {
        auths.put(authdata.authToken(), authdata.username());
    }

    @Override
    public void removeAuth(String authToken) {
        auths.remove(authToken);
    }

    //get value
    public String getVal(String auth) { return auths.get(auth); }

    //return map
    public Map returnAuths(){
        return auths;
    }

}
