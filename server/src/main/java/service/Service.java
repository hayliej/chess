package service;
import dataAccess.GameDAO;
import dataAccess.UserDAO;
import dataAccess.AuthDAO;
import server.Server;
import dataAccess.DataAccessException;

public class Service {
    //what do we do with this??
    private final DataAccessException dataAccess;

    public Service(DataAccessException dataAccess) {
        this.dataAccess = dataAccess;
    }

    //THINGS SERVICE NEEDS TO DO: ???
    //getUser
    //createUser
    //createAuth
    //checkValidUser
    //getAuth
    //get/listGames
    //getGame
    //createGame
    //clearDB
    public void clear(){
        UserDAO.clear();
        GameDAO.clear();
        AuthDAO.clear();
    }

    //^NEED TO THROW EXCEPTIONS?
}
