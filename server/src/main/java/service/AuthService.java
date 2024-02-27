package service;
import dataAccess.*;

public class AuthService {
    //what do we do with this??
    private MemAuthDAO dataAccess = new MemAuthDAO();

    public AuthService() {
        this.dataAccess = dataAccess;
    }

    //THINGS SERVICE NEEDS TO DO:
    //createAuth
    //getAuth
    //clearDB
    public void clear(){ dataAccess.clear(); }

    //^NEED TO THROW EXCEPTIONS?
}

