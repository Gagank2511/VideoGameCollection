import java.util.ArrayList;
import java.util.List;

public class GameLibrary {

    private static List<AbstractGame> games = new ArrayList<>();
    public static void add(SinglePlayer sPgame1) {
        games.add(sPgame1);
    }

    public static void add(Multiplayer mPgame3) {
        games.add(mPgame3);
    }

    public static void add(AbstractGame game) {
        games.add(game);
    }

    public static List<AbstractGame> getGames() {
        return games;
    
    }

    public static void setGames(List<AbstractGame> list) {
        return;
    }

}
