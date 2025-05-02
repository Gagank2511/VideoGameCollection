package videogameCollection;

/**
 * Represents a multiplayer game with win/loss record tracking.
 */
public class Multiplayer extends AbstractGame {
    private static final long serialVersionUID = 1L;

    private int wins;
    private int losses;

    /**
     * Constructor for Multiplayer game.
     *
     * @param title The title of the game
     * @param genre The genre of the game
     * @param platform The platform the game is on
     * @param releaseYear The year the game was released
     * @param developer The developer of the game
     */
    public Multiplayer(String title, GameGenre genre, GamePlatform platform, int releaseYear, String developer) {
        super(title, genre, platform, releaseYear, developer);
        this.wins = 0;
        this.losses = 0;
    }

    /**
     * Constructor that accepts String genre and platform for backward compatibility.
     *
     * @param title The title of the game
     * @param genreStr The genre of the game as a string
     * @param platformStr The platform the game is on as a string
     * @param releaseYear The year the game was released
     * @param developer The developer of the game
     */
    public Multiplayer(String title, String genreStr, String platformStr, int releaseYear, String developer) {
        this(title,
             GameGenre.fromString(genreStr),
             GamePlatform.fromString(platformStr),
             releaseYear,
             developer);
    }

    /**
     * Gets the number of wins.
     *
     * @return The number of wins
     */
    public int getWins() {
        return wins;
    }

    /**
     * Gets the number of losses.
     *
     * @return The number of losses
     */
    public int getLosses() {
        return losses;
    }

    /**
     * Updates the player's win/loss record.
     *
     * @param progressData A string in the format "wins/losses"
     * @throws IllegalArgumentException if the format is invalid or values are negative
     */
    @Override
    public void updateProgress(String progressData) {
        try {
            String[] parts = progressData.split("/");
            if (parts.length != 2) {
                throw new IllegalArgumentException("Progress data must be in format 'wins/losses'");
            }

            int newWins = Integer.parseInt(parts[0]);
            int newLosses = Integer.parseInt(parts[1]);

            if (newWins < 0 || newLosses < 0) {
                throw new IllegalArgumentException("Wins and losses cannot be negative");
            }

            wins = newWins;
            losses = newLosses;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Wins and losses must be valid integers");
        }
    }

    /**
     * Gets a string representation of the player's win/loss record.
     *
     * @return A string showing the win/loss record
     */
    @Override
    public String getProgress() {
        return "W/L: " + wins + "/" + losses;
    }

    /**
     * Calculates the win rate as a percentage.
     *
     * @return The win rate percentage, or 0 if no games played
     */
    public double getWinRate() {
        int totalGames = wins + losses;
        if (totalGames == 0) {
            return 0.0;
        }
        return (double) wins / totalGames * 100.0;
    }

    @Override
    public String toString() {
        return super.toString() + " - Multiplayer - " + getProgress();
    }
}
