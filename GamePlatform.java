/**
 * Enum representing different gaming platforms.
 * This provides type safety and standardization for game platforms.
 */
public enum GamePlatform {
    PC("PC"),
    PLAYSTATION_4("PlayStation 4"),
    PLAYSTATION_5("PlayStation 5"),
    XBOX_ONE("Xbox One"),
    XBOX_SERIES_X("Xbox Series X"),
    NINTENDO_SWITCH("Nintendo Switch"),
    MOBILE("Mobile"),
    MULTIPLE("Multiple Platforms"),
    OTHER("Other");

    private final String displayName;

    /**
     * Constructor for GamePlatform enum.
     * 
     * @param displayName The display name of the platform
     */
    GamePlatform(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Gets the display name of the platform.
     * 
     * @return The display name
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Finds a platform by its display name (case-insensitive).
     * 
     * @param name The display name to search for
     * @return The matching GamePlatform or OTHER if no match is found
     */
    public static GamePlatform fromString(String name) {
        for (GamePlatform platform : GamePlatform.values()) {
            if (platform.displayName.equalsIgnoreCase(name)) {
                return platform;
            }
        }
        return OTHER;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
