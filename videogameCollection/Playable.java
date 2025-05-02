package videogameCollection;

/**
 * Interface for objects that can track gameplay progress.
 * Implemented by games to provide a standardized way to update and retrieve progress.
 */
public interface Playable {
    /**
     * Updates the progress of the game.
     *
     * @param progressData A string representation of the progress data
     * @throws IllegalArgumentException if the progress data is invalid
     */
    void updateProgress(String progressData);

    /**
     * Gets a string representation of the current progress.
     *
     * @return A string showing the current progress
     */
    String getProgress();
}
