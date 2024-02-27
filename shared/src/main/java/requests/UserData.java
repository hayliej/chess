package requests;

//used for register
public record UserData(String username, String password, String email) {
    public String getUsername(){
        return username;
    }
}
