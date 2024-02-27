package dataAccess;

import java.util.HashMap;
import java.util.Map;

public class MemUserDAO implements UserDAO {
    //make  map of users
    Map<String,String> users = new HashMap<>();
    //clear
    public void clear() { Map<String,String> users = new HashMap<>(); }
}
