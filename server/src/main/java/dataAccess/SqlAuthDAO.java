package dataAccess;

import requests.AuthData;

import java.util.Map;

public class SqlAuthDAO implements AuthDAO{


    @Override
    public void clear() {

    }

    @Override
    public void addAuth(AuthData authdata) throws DataAccessException {

    }

    @Override
    public void removeAuth(String authToken) {

    }

    @Override
    public Map<Object, Object> returnAuths() {
        return null;
    }

    @Override
    public String getVal(String auth) {
        return null;
    }


}
