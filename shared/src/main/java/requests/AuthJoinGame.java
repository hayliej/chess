package requests;

//used for joinGame
public record AuthJoinGame(String authToken, String playerColor, Integer gameID) {
}
