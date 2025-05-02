package videogameCollection;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles data persistence for the video game collection application.
 * Provides methods to save and load game and user profile data.
 */
public class DataManager {
    private static final String GAME_FILE = "gamedata.ser";
    private static final String PROFILE_FILE = "profiledata.ser";
    private static final Logger LOGGER = Logger.getLogger(DataManager.class.getName());

    /**
     * Saves game library and user profile data to files.
     *
     * @param games The list of games to save
     * @param profile The user profile to save
     * @return true if the data was saved successfully, false otherwise
     */
    public static boolean saveData(List<AbstractGame> games, UserProfile profile) {
        boolean success = true;

        // Save games
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(GAME_FILE))) {
            oos.writeObject(games);
            LOGGER.info("Games saved successfully to " + GAME_FILE);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error saving games", e);
            success = false;
        }

        // Save profile
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(PROFILE_FILE))) {
            oos.writeObject(profile);
            LOGGER.info("Profile saved successfully to " + PROFILE_FILE);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error saving profile", e);
            success = false;
        }

        return success;
    }

    /**
     * Loads game library and user profile data from files.
     *
     * @return An array containing the games list at index 0 and the user profile at index 1
     */
    public static Object[] loadData() {
        List<AbstractGame> games = new ArrayList<>();
        UserProfile profile = new UserProfile("Guest", GamePlatform.OTHER);

        // Load games
        File gameFile = new File(GAME_FILE);
        if (gameFile.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(gameFile))) {
                games = (List<AbstractGame>) ois.readObject();
                LOGGER.info("Loaded " + games.size() + " games from " + GAME_FILE);
            } catch (IOException | ClassNotFoundException e) {
                LOGGER.log(Level.SEVERE, "Error loading games", e);
            }
        } else {
            LOGGER.info("Game file not found. Starting with empty game library.");
        }

        // Load profile
        File profileFile = new File(PROFILE_FILE);
        if (profileFile.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(profileFile))) {
                profile = (UserProfile) ois.readObject();
                LOGGER.info("Loaded profile for user: " + profile.getUsername());
            } catch (IOException | ClassNotFoundException e) {
                LOGGER.log(Level.SEVERE, "Error loading profile", e);
            }
        } else {
            LOGGER.info("Profile file not found. Starting with default profile.");
        }

        return new Object[] { games, profile };
    }

    /**
     * Deletes all saved data files.
     *
     * @return true if all files were deleted successfully, false otherwise
     */
    public static boolean deleteAllData() {
        boolean success = true;

        File gameFile = new File(GAME_FILE);
        if (gameFile.exists() && !gameFile.delete()) {
            LOGGER.warning("Failed to delete game file");
            success = false;
        }

        File profileFile = new File(PROFILE_FILE);
        if (profileFile.exists() && !profileFile.delete()) {
            LOGGER.warning("Failed to delete profile file");
            success = false;
        }

        return success;
    }
}
