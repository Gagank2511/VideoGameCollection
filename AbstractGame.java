import java.io.Serializable;

/**
 * Abstract base class for all game types in the video game collection.
 * Implements Serializable for persistence and Playable for game progress tracking.
 */
public abstract class AbstractGame implements Serializable, Playable {
    private static final long serialVersionUID = 1L;

    protected String title;
    protected GameGenre genre;
    protected GamePlatform platform;
    protected int releaseYear;
    protected String developer;

    /**
     * Constructor for AbstractGame.
     *
     * @param title The title of the game
     * @param genre The genre of the game
     * @param platform The platform the game is on
     * @param releaseYear The year the game was released
     * @param developer The developer of the game
     */
    public AbstractGame(String title, GameGenre genre, GamePlatform platform, int releaseYear, String developer) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty");
        }
        if (genre == null) {
            throw new IllegalArgumentException("Genre cannot be null");
        }
        if (platform == null) {
            throw new IllegalArgumentException("Platform cannot be null");
        }
        if (releaseYear < 1950 || releaseYear > 2100) {
            throw new IllegalArgumentException("Release year must be between 1950 and 2100");
        }
        if (developer == null || developer.trim().isEmpty()) {
            throw new IllegalArgumentException("Developer cannot be null or empty");
        }

        this.title = title;
        this.genre = genre;
        this.platform = platform;
        this.releaseYear = releaseYear;
        this.developer = developer;
    }

    /**
     * Gets the title of the game.
     *
     * @return The game title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets the genre of the game.
     *
     * @return The game genre
     */
    public GameGenre getGenre() {
        return genre;
    }

    /**
     * Gets the platform of the game.
     *
     * @return The game platform
     */
    public GamePlatform getPlatform() {
        return platform;
    }

    /**
     * Gets the release year of the game.
     *
     * @return The release year
     */
    public int getReleaseYear() {
        return releaseYear;
    }

    /**
     * Gets the developer of the game.
     *
     * @return The game developer
     */
    public String getDeveloper() {
        return developer;
    }

    /**
     * For backward compatibility with String-based genre.
     *
     * @return The genre display name
     */
    public String getGenreString() {
        return genre.getDisplayName();
    }

    /**
     * For backward compatibility with String-based platform.
     *
     * @return The platform display name
     */
    public String getPlatformString() {
        return platform.getDisplayName();
    }

    @Override
    public String toString() {
        return "Game: " + title + " (" + releaseYear + ") by " + developer +
               " - Genre: " + genre + ", Platform: " + platform;
    }
}