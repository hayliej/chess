package service;
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

    //^NEED TO THROW EXCEPTIONS?
}
