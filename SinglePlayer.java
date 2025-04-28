/**
 * Represents a single-player game with level-based progress tracking.
 */
public class SinglePlayer extends AbstractGame {
    private static final long serialVersionUID = 1L;

    private int levelsCompleted;
    private int totalLevels;

    /**
     * Constructor for SinglePlayer game.
     *
     * @param title The title of the game
     * @param genre The genre of the game
     * @param platform The platform the game is on
     * @param releaseYear The year the game was released
     * @param developer The developer of the game
     * @param totalLevels The total number of levels in the game
     */
    public SinglePlayer(String title, GameGenre genre, GamePlatform platform, int releaseYear, String developer, int totalLevels) {
        super(title, genre, platform, releaseYear, developer);

        if (totalLevels <= 0) {
            throw new IllegalArgumentException("Total levels must be greater than zero");
        }

        this.totalLevels = totalLevels;
        this.levelsCompleted = 0;
    }

    /**
     * Constructor that accepts String genre and platform for backward compatibility.
     *
     * @param title The title of the game
     * @param genreStr The genre of the game as a string
     * @param platformStr The platform the game is on as a string
     * @param releaseYear The year the game was released
     * @param developer The developer of the game
     * @param totalLevels The total number of levels in the game
     */
    public SinglePlayer(String title, String genreStr, String platformStr, int releaseYear, String developer, int totalLevels) {
        this(title,
             GameGenre.fromString(genreStr),
             GamePlatform.fromString(platformStr),
             releaseYear,
             developer,
             totalLevels);
    }

    /**
     * Gets the total number of levels in the game.
     *
     * @return The total number of levels
     */
    public int getTotalLevels() {
        return totalLevels;
    }

    /**
     * Gets the number of completed levels.
     *
     * @return The number of completed levels
     */
    public int getLevelsCompleted() {
        return levelsCompleted;
    }

    /**
     * Updates the player's progress in the game.
     *
     * @param progressData A string representing the number of completed levels
     * @throws NumberFormatException if the progress data is not a valid integer
     * @throws IllegalArgumentException if the progress is negative or exceeds total levels
     */
    @Override
    public void updateProgress(String progressData) {
        try {
            int levels = Integer.parseInt(progressData);

            if (levels < 0) {
                throw new IllegalArgumentException("Completed levels cannot be negative");
            }

            if (levels > totalLevels) {
                throw new IllegalArgumentException("Completed levels cannot exceed total levels");
            }

            this.levelsCompleted = levels;
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Progress data must be a valid integer");
        }
    }

    /**
     * Gets a string representation of the player's progress.
     *
     * @return A string showing completed levels out of total levels
     */
    @Override
    public String getProgress() {
        return levelsCompleted + "/" + totalLevels + " Levels completed";
    }

    @Override
    public String toString() {
        return super.toString() + " - Single Player - " + getProgress();
    }
}
