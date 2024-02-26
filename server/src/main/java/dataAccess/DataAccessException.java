package dataAccess;

/**
 * Indicates there was an error connecting to the database
 */
public class DataAccessException extends Exception{
    //WHAT IS THIS SUPPOSED TO DO??? IDK YET
    public DataAccessException(String message) {
        super(message);
    }
}
