package videogameCollection;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles file operations for enum classes in the video game collection application.
 * Provides methods to save and load custom enum values from files.
 */
public class EnumFileHandler {
    private static final String GENRE_FILE = "genres.txt";
    private static final String PLATFORM_FILE = "platforms.txt";
    private static final Logger LOGGER = Logger.getLogger(EnumFileHandler.class.getName());

    /**
     * Saves the list of game genres to a file.
     * 
     * @param genres The list of genre display names to save
     * @return true if the genres were saved successfully, false otherwise
     */
    public static boolean saveGenres(List<String> genres) {
        return saveToFile(genres, GENRE_FILE, "genres");
    }

    /**
     * Loads the list of game genres from a file.
     * If the file doesn't exist or can't be read, returns the default genres.
     * 
     * @return The list of genre display names
     */
    public static List<String> loadGenres() {
        List<String> defaultGenres = new ArrayList<>();
        for (GameGenre genre : GameGenre.values()) {
            defaultGenres.add(genre.getDisplayName());
        }
        
        return loadFromFile(GENRE_FILE, defaultGenres, "genres");
    }

    /**
     * Saves the list of game platforms to a file.
     * 
     * @param platforms The list of platform display names to save
     * @return true if the platforms were saved successfully, false otherwise
     */
    public static boolean savePlatforms(List<String> platforms) {
        return saveToFile(platforms, PLATFORM_FILE, "platforms");
    }

    /**
     * Loads the list of game platforms from a file.
     * If the file doesn't exist or can't be read, returns the default platforms.
     * 
     * @return The list of platform display names
     */
    public static List<String> loadPlatforms() {
        List<String> defaultPlatforms = new ArrayList<>();
        for (GamePlatform platform : GamePlatform.values()) {
            defaultPlatforms.add(platform.getDisplayName());
        }
        
        return loadFromFile(PLATFORM_FILE, defaultPlatforms, "platforms");
    }

    /**
     * Saves a list of strings to a file.
     * 
     * @param items The list of strings to save
     * @param fileName The name of the file to save to
     * @param itemType The type of items being saved (for logging)
     * @return true if the items were saved successfully, false otherwise
     */
    private static boolean saveToFile(List<String> items, String fileName, String itemType) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (String item : items) {
                writer.write(item);
                writer.newLine();
            }
            LOGGER.info("Saved " + items.size() + " " + itemType + " to " + fileName);
            return true;
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error saving " + itemType + " to " + fileName, e);
            return false;
        }
    }

    /**
     * Loads a list of strings from a file.
     * 
     * @param fileName The name of the file to load from
     * @param defaultItems The default items to return if the file can't be read
     * @param itemType The type of items being loaded (for logging)
     * @return The list of strings loaded from the file, or the default items if the file can't be read
     */
    private static List<String> loadFromFile(String fileName, List<String> defaultItems, String itemType) {
        File file = new File(fileName);
        if (!file.exists()) {
            LOGGER.info(itemType + " file not found. Using default " + itemType + ".");
            return defaultItems;
        }

        List<String> items = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    items.add(line.trim());
                }
            }
            LOGGER.info("Loaded " + items.size() + " " + itemType + " from " + fileName);
            return items;
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error loading " + itemType + " from " + fileName, e);
            return defaultItems;
        }
    }

    /**
     * Deletes the genre and platform files.
     * 
     * @return true if all files were deleted successfully, false otherwise
     */
    public static boolean deleteEnumFiles() {
        boolean success = true;

        File genreFile = new File(GENRE_FILE);
        if (genreFile.exists() && !genreFile.delete()) {
            LOGGER.warning("Failed to delete genre file");
            success = false;
        }

        File platformFile = new File(PLATFORM_FILE);
        if (platformFile.exists() && !platformFile.delete()) {
            LOGGER.warning("Failed to delete platform file");
            success = false;
        }

        return success;
    }
}
