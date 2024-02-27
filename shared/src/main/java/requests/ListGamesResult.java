package requests;
import java.util.Map;

public record ListGamesResult(String message, Map<Object, Object> games) {
}
