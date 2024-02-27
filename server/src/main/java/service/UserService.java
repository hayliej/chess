package service;
import dataAccess.*;


public class UserService {
    //what do we do with this??
    private MemUserDAO dataAccess = new MemUserDAO();

    public UserService() {
        this.dataAccess = dataAccess;
    }

    //THINGS SERVICE NEEDS TO DO: ???
    //getUser
    //createUser
    //clearDB
    public void clear() { dataAccess.clear();}

    //^NEED TO THROW EXCEPTIONS?
}
