import java.util.ArrayList;
import java.util.List;

public class gameLibrary {

    private static List<AbstractGame> games = new ArrayList<>();
    public static void add(SinglePlayer sPgame1) {
        games.add(sPgame1);
    }

    public static void add(Multiplayer mPgame3) {
        games.add(mPgame3);
    }

}
