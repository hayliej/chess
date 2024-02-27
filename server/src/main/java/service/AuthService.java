package service;
import dataAccess.*;

public class AuthService {
    //what do we do with this??
    private final MemAuthDAO dataAccess;

    public AuthService(MemAuthDAO dataAc) {
        this.dataAccess = dataAc;
    }

    //THINGS SERVICE NEEDS TO DO:
    //createAuth
    //getAuth
    //clearDB
    public void clear(){ MemAuthDAO.clear(); }

    //^NEED TO THROW EXCEPTIONS?
}

