public class Main {
    public static void main(String[] args) throws UnsupportedOperationException{
        Main app = new Main();
        app.initializeGames();

}

    private void initializeGames() {
        SinglePlayer sPgame1 = new SinglePlayer("The Legend of Zelda: Breath of the Wild", "Action-Adventure", "Nintendo Switch", 2017, "Nintendo", 120);
        SinglePlayer sPgame2 = new SinglePlayer("God of War", "Action-Adventure", "PlayStation 4", 2018, "Santa Monico Studio", 26);

        Multiplayer mPgame3 = new Multiplayer("Fortnite", "Battle Royale", "Multiple Platforms", 2017, "Epic Games");
        Multiplayer mPgame4 = new Multiplayer("Call of Duty: Warzone", "Battle Royale", "Multiple Platforms", 2020, "Infinity Ward");

        // Adding games to the game library
        gameLibrary.add(sPgame1);
        gameLibrary.add(sPgame2);
        gameLibrary.add(mPgame3);
        gameLibrary.add(mPgame4);

    }
}
