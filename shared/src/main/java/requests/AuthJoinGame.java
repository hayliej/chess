package requests;

//used for joinGame
public record AuthJoinGame(String authToken, String color, String gameID) {
}
