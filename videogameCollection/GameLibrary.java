package videogameCollection;

import java.util.ArrayList;
import java.util.List;

import videogameCollection.game.AbstractGame;
import videogameCollection.game.Multiplayer;
import videogameCollection.game.SinglePlayer;

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
        System.out.println("Game added to library: " + game.getTitle());
    }

    public static List<AbstractGame> getGames() {
        return new ArrayList<>(games);
    }

    public static void setGames(List<AbstractGame> list) {
        games = new ArrayList<>(list);
    }

    public static void remove(AbstractGame game) {
        games.remove(game);
    }

}
