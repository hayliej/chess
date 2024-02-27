package service;
import dataAccess.*;


public class UserService {
    //what do we do with this??
    private final MemUserDAO dataAccess;

    public UserService(MemUserDAO dataAc) {
        this.dataAccess = dataAc;
    }

    //THINGS SERVICE NEEDS TO DO: ???
    //getUser
    //createUser
    //clearDB
    public void clear(){ MemUserDAO.clear();}

    //^NEED TO THROW EXCEPTIONS?
}
