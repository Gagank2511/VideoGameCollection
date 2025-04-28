/**
 * Enum representing different video game genres.
 * This provides type safety and standardization for game genres.
 */
public enum GameGenre {
    ACTION("Action"),
    ADVENTURE("Adventure"),
    ACTION_ADVENTURE("Action-Adventure"),
    ROLE_PLAYING("Role-Playing"),
    SIMULATION("Simulation"),
    STRATEGY("Strategy"),
    SPORTS("Sports"),
    PUZZLE("Puzzle"),
    IDLE("Idle"),
    BATTLE_ROYALE("Battle Royale"),
    SHOOTER("Shooter"),
    RACING("Racing"),
    FIGHTING("Fighting"),
    HORROR("Horror"),
    PLATFORMER("Platformer"),
    OPEN_WORLD("Open World"),
    SURVIVAL("Survival"),
    MMORPG("MMORPG"),
    OTHER("Other");

    private final String displayName;

    /**
     * Constructor for GameGenre enum.
     * 
     * @param displayName The display name of the genre
     */
    GameGenre(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Gets the display name of the genre.
     * 
     * @return The display name
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Finds a genre by its display name (case-insensitive).
     * 
     * @param name The display name to search for
     * @return The matching GameGenre or OTHER if no match is found
     */
    public static GameGenre fromString(String name) {
        for (GameGenre genre : GameGenre.values()) {
            if (genre.displayName.equalsIgnoreCase(name)) {
                return genre;
            }
        }
        return OTHER;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
