package dataAccess;

import java.util.HashMap;
import java.util.Map;

public class MemAuthDAO implements AuthDAO {
    //make map of auths
    Map<String,String> games = new HashMap<>();

    //clear
    public void clear() {
        Map<String,String> games = new HashMap<>();
    }
}
