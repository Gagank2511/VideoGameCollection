import java.util.ArrayList;

import videogameCollection.AbstractGame;
import videogameCollection.GameGenre;
import videogameCollection.GamePlatform;
import videogameCollection.Multiplayer;
import videogameCollection.SinglePlayer;
import videogameCollection.UserProfile;
import videogameCollection.GameLibrary;

/**
 * Unit tests for the Video Game Collection application.
 * Tests the core functionality of the application classes.
 */
public class VideoGameCollectionTest {

    /**
     * Main method to run the tests.
     *
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        System.out.println("Running Video Game Collection Tests...");

        testGameGenreEnum();
        testGamePlatformEnum();
        testAbstractGame();
        testSinglePlayer();
        testMultiplayer();
        testUserProfile();
        testGameLibrary();

        System.out.println("\nAll tests completed!");
    }

    /**
     * Tests the GameGenre enum.
     */
    private static void testGameGenreEnum() {
        System.out.println("\n=== Testing GameGenre Enum ===");

        // Test fromString method
        assert GameGenre.fromString("Action") == GameGenre.ACTION : "Failed to match 'Action' genre";
        assert GameGenre.fromString("action") == GameGenre.ACTION : "Failed case-insensitive match";
        assert GameGenre.fromString("NonExistent") == GameGenre.OTHER : "Failed to default to OTHER";

        // Test toString method
        assert GameGenre.ACTION.toString().equals("Action") : "toString() failed for ACTION";
        assert GameGenre.ROLE_PLAYING.toString().equals("Role-Playing") : "toString() failed for ROLE_PLAYING";

        System.out.println("GameGenre tests passed!");
    }

    /**
     * Tests the GamePlatform enum.
     */
    private static void testGamePlatformEnum() {
        System.out.println("\n=== Testing GamePlatform Enum ===");

        // Test fromString method
        assert GamePlatform.fromString("PC") == GamePlatform.PC : "Failed to match 'PC' platform";
        assert GamePlatform.fromString("pc") == GamePlatform.PC : "Failed case-insensitive match";
        assert GamePlatform.fromString("NonExistent") == GamePlatform.OTHER : "Failed to default to OTHER";

        // Test toString method
        assert GamePlatform.PC.toString().equals("PC") : "toString() failed for PC";
        assert GamePlatform.NINTENDO_SWITCH.toString().equals("Nintendo Switch") : "toString() failed for NINTENDO_SWITCH";

        System.out.println("GamePlatform tests passed!");
    }

    /**
     * Tests the AbstractGame class through its concrete implementations.
     */
    private static void testAbstractGame() {
        System.out.println("\n=== Testing AbstractGame ===");

        // Test constructor validation
        try {
            new SinglePlayer(null, GameGenre.ACTION, GamePlatform.PC, 2020, "Test Developer", 10);
            assert false : "Should throw exception for null title";
        } catch (IllegalArgumentException e) {
            // Expected
        }

        try {
            new SinglePlayer("Test Game", null, GamePlatform.PC, 2020, "Test Developer", 10);
            assert false : "Should throw exception for null genre";
        } catch (IllegalArgumentException e) {
            // Expected
        }

        try {
            new SinglePlayer("Test Game", GameGenre.ACTION, null, 2020, "Test Developer", 10);
            assert false : "Should throw exception for null platform";
        } catch (IllegalArgumentException e) {
            // Expected
        }

        try {
            new SinglePlayer("Test Game", GameGenre.ACTION, GamePlatform.PC, 1900, "Test Developer", 10);
            assert false : "Should throw exception for invalid year";
        } catch (IllegalArgumentException e) {
            // Expected
        }

        try {
            new SinglePlayer("Test Game", GameGenre.ACTION, GamePlatform.PC, 2020, null, 10);
            assert false : "Should throw exception for null developer";
        } catch (IllegalArgumentException e) {
            // Expected
        }

        // Test valid construction
        AbstractGame game = new SinglePlayer("Test Game", GameGenre.ACTION, GamePlatform.PC, 2020, "Test Developer", 10);
        assert game.getTitle().equals("Test Game") : "getTitle() failed";
        assert game.getGenre() == GameGenre.ACTION : "getGenre() failed";
        assert game.getPlatform() == GamePlatform.PC : "getPlatform() failed";
        assert game.getReleaseYear() == 2020 : "getReleaseYear() failed";
        assert game.getDeveloper().equals("Test Developer") : "getDeveloper() failed";

        // Test compatibility methods
        assert game.getGenreString().equals("Action") : "getGenreString() failed";
        assert game.getPlatformString().equals("PC") : "getPlatformString() failed";

        System.out.println("AbstractGame tests passed!");
    }

    /**
     * Tests the SinglePlayer class.
     */
    private static void testSinglePlayer() {
        System.out.println("\n=== Testing SinglePlayer ===");

        // Test constructor validation
        try {
            new SinglePlayer("Test Game", GameGenre.ACTION, GamePlatform.PC, 2020, "Test Developer", 0);
            assert false : "Should throw exception for invalid total levels";
        } catch (IllegalArgumentException e) {
            // Expected
        }

        // Test valid construction
        SinglePlayer game = new SinglePlayer("Test Game", GameGenre.ACTION, GamePlatform.PC, 2020, "Test Developer", 10);
        assert game.getTotalLevels() == 10 : "getTotalLevels() failed";
        assert game.getLevelsCompleted() == 0 : "Initial levelsCompleted should be 0";

        // Test progress update
        game.updateProgress("5");
        assert game.getLevelsCompleted() == 5 : "updateProgress() failed";
        assert game.getProgress().equals("5/10 Levels completed") : "getProgress() failed";

        // Test completion percentage
        assert game.getCompletionPercentage() == 50.0 : "getCompletionPercentage() failed";

        // Test progress validation
        try {
            game.updateProgress("-1");
            assert false : "Should throw exception for negative levels";
        } catch (IllegalArgumentException e) {
            // Expected
        }

        try {
            game.updateProgress("11");
            assert false : "Should throw exception for levels > totalLevels";
        } catch (IllegalArgumentException e) {
            // Expected
        }

        try {
            game.updateProgress("abc");
            assert false : "Should throw exception for non-numeric input";
        } catch (NumberFormatException e) {
            // Expected
        }

        System.out.println("SinglePlayer tests passed!");
    }

    /**
     * Tests the Multiplayer class.
     */
    private static void testMultiplayer() {
        System.out.println("\n=== Testing Multiplayer ===");

        // Test valid construction
        Multiplayer game = new Multiplayer("Test Game", GameGenre.BATTLE_ROYALE, GamePlatform.PC, 2020, "Test Developer");
        assert game.getWins() == 0 : "Initial wins should be 0";
        assert game.getLosses() == 0 : "Initial losses should be 0";

        // Test progress update
        game.updateProgress("10/5");
        assert game.getWins() == 10 : "updateProgress() failed for wins";
        assert game.getLosses() == 5 : "updateProgress() failed for losses";
        assert game.getProgress().equals("W/L: 10/5") : "getProgress() failed";
        assert Math.abs(game.getWinRate() - 66.67) < 0.01 : "getWinRate() failed";

        // Test completion percentage (should be same as win rate for multiplayer)
        assert Math.abs(game.getCompletionPercentage() - 66.67) < 0.01 : "getCompletionPercentage() failed";

        // Test progress validation
        try {
            game.updateProgress("-1/5");
            assert false : "Should throw exception for negative wins";
        } catch (IllegalArgumentException e) {
            // Expected
        }

        try {
            game.updateProgress("10/-5");
            assert false : "Should throw exception for negative losses";
        } catch (IllegalArgumentException e) {
            // Expected
        }

        try {
            game.updateProgress("10");
            assert false : "Should throw exception for invalid format";
        } catch (IllegalArgumentException e) {
            // Expected
        }

        try {
            game.updateProgress("abc/def");
            assert false : "Should throw exception for non-numeric input";
        } catch (IllegalArgumentException e) {
            // Expected
        }

        System.out.println("Multiplayer tests passed!");
    }

    /**
     * Tests the UserProfile class.
     */
    private static void testUserProfile() {
        System.out.println("\n=== Testing UserProfile ===");

        // Test constructor validation
        try {
            new UserProfile(null, GamePlatform.PC);
            assert false : "Should throw exception for null username";
        } catch (IllegalArgumentException e) {
            // Expected
        }

        try {
            new UserProfile("", GamePlatform.PC);
            assert false : "Should throw exception for empty username";
        } catch (IllegalArgumentException e) {
            // Expected
        }

        // Test valid construction
        UserProfile profile = new UserProfile("TestUser", GamePlatform.PC);
        assert profile.getUsername().equals("TestUser") : "getUsername() failed";
        assert profile.getPreferredPlatform() == GamePlatform.PC : "getPreferredPlatform() failed";
        assert profile.getGamesOwned().isEmpty() : "Initial games list should be empty";

        // Test adding games
        SinglePlayer game1 = new SinglePlayer("Game 1", GameGenre.ACTION, GamePlatform.PC, 2020, "Developer 1", 10);
        Multiplayer game2 = new Multiplayer("Game 2", GameGenre.BATTLE_ROYALE, GamePlatform.PLAYSTATION_4, 2019, "Developer 2");

        profile.addGame(game1);
        profile.addGame(game2);
        assert profile.getGamesOwned().size() == 2 : "addGame() failed";

        // Test duplicate prevention
        profile.addGame(game1);
        assert profile.getGamesOwned().size() == 2 : "Should prevent duplicate games";

        // Test rating and reviewing
        profile.rateGame(game1, 5);
        profile.reviewGame(game1, "Great game!");

        assert profile.getGameRating(game1) == 5 : "getGameRating() failed";
        assert profile.getGameReview(game1).equals("Great game!") : "getGameReview() failed";
        assert profile.getGameRating(game2) == null : "getGameRating() should return null for unrated games";
        assert profile.getGameReview(game2) == null : "getGameReview() should return null for unreviewed games";

        // Test rating validation
        try {
            profile.rateGame(game1, 0);
            assert false : "Should throw exception for rating < 1";
        } catch (IllegalArgumentException e) {
            // Expected
        }

        try {
            profile.rateGame(game1, 6);
            assert false : "Should throw exception for rating > 5";
        } catch (IllegalArgumentException e) {
            // Expected
        }

        // Test removing games
        assert profile.removeGame(game1) : "removeGame() failed";
        assert profile.getGamesOwned().size() == 1 : "Game was not removed";
        assert profile.getGameRating(game1) == null : "Rating was not removed";
        assert profile.getGameReview(game1) == null : "Review was not removed";

        // Test searching
        profile.addGame(new SinglePlayer("Action Game", GameGenre.ACTION, GamePlatform.PC, 2020, "Developer", 10));
        profile.addGame(new SinglePlayer("Another Action Game", GameGenre.ACTION, GamePlatform.NINTENDO_SWITCH, 2018, "Developer", 15));

        assert profile.searchGamesByTitle("Action").size() == 2 : "searchGamesByTitle() failed";
        assert profile.searchGamesByGenre(GameGenre.ACTION).size() == 2 : "searchGamesByGenre() failed";
        assert profile.searchGamesByPlatform(GamePlatform.PC).size() == 1 : "searchGamesByPlatform() failed";

        // Test sorting
        profile.rateGame(profile.getGamesOwned().get(0), 3);
        profile.rateGame(profile.getGamesOwned().get(1), 5);

        assert profile.getGamesSortedByTitle(true).get(0).getTitle().startsWith("A") : "getGamesSortedByTitle() ascending failed";
        assert profile.getGamesSortedByTitle(false).get(0).getTitle().startsWith("G") : "getGamesSortedByTitle() descending failed";

        assert profile.getGamesSortedByReleaseYear(true).get(0).getReleaseYear() == 2018 : "getGamesSortedByReleaseYear() ascending failed";
        assert profile.getGamesSortedByReleaseYear(false).get(0).getReleaseYear() == 2020 : "getGamesSortedByReleaseYear() descending failed";

        assert profile.getGamesSortedByRating(true).get(0) == profile.getGamesOwned().get(0) : "getGamesSortedByRating() ascending failed";
        assert profile.getGamesSortedByRating(false).get(0) == profile.getGamesOwned().get(1) : "getGamesSortedByRating() descending failed";

        // Test average rating
        assert Math.abs(profile.getAverageRating() - 4.0) < 0.01 : "getAverageRating() failed";

        System.out.println("UserProfile tests passed!");
    }

    /**
     * Tests the GameLibrary class.
     */
    private static void testGameLibrary() {
        System.out.println("\n=== Testing GameLibrary ===");

        // Clear the library
        GameLibrary.setGames(new ArrayList<>());
        assert GameLibrary.getGames().isEmpty() : "Library should be empty after setGames()";

        // Add games
        SinglePlayer game1 = new SinglePlayer("Game 1", GameGenre.ACTION, GamePlatform.PC, 2020, "Developer 1", 10);
        Multiplayer game2 = new Multiplayer("Game 2", GameGenre.BATTLE_ROYALE, GamePlatform.PLAYSTATION_4, 2019, "Developer 2");

        GameLibrary.add(game1);
        GameLibrary.add(game2);
        assert GameLibrary.getGames().size() == 2 : "add() failed";

        System.out.println("GameLibrary tests passed!");
    }
}
