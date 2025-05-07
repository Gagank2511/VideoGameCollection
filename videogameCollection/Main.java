package videogameCollection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import videogameCollection.game.AbstractGame;

/**
 * Main application class for the Video Game Collection application.
 * Provides a console-based user interface for managing a video game collection.
 */
public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    private final Scanner scanner = new Scanner(System.in);
    private UserProfile userProfile;

    /**
     * Application entry point.
     *
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        Main app = null;
        try {
            app = new Main();
            app.loadData();
            app.run();
        } catch (Exception e) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, "Application error", e);
            System.out.println("An unexpected error occurred: " + e.getMessage());
        } finally {
            if (app != null) {
                app.cleanup();
            }
        }
    }

    /**
     * Runs the main application loop.
     */
    private void run() {
        if (userProfile == null) {
            userProfile = UserProfileManager.initialiseUserProfile();
        } else {
            System.out.println("Welcome back, " + userProfile.getUsername() + "!");
        }

        mainMenu();
    }

    /**
     * Displays and handles the main menu.
     */
    private void mainMenu() {
        boolean running = true;
        while (running) {
            try {
                System.out.println("\n===== MAIN MENU =====");
                System.out.println("1. Add game");
                System.out.println("2. View Library");
                System.out.println("3. Search games");
                System.out.println("4. Sort games");
                System.out.println("5. Rate/Review game");
                System.out.println("6. Remove game");
                System.out.println("7. Update game progress");
                System.out.println("8. User profile");
                System.out.println("9. Save and Exit");
                System.out.print("\nChoose an option (1-9): ");

                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    System.out.println("Please enter a number.");
                    continue;
                }

                int choice = Integer.parseInt(input);
                if (choice < 1 || choice > 9) {
                    System.out.println("Please enter a number between 1 and 9.");
                    continue;
                }

                handleMenuChoice(choice);
                if (choice == 9) {
                    running = false;
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
                LOGGER.log(Level.WARNING, "Error in main menu", e);
            }
        }
    }

    private void handleMenuChoice(int choice) {
        switch (choice) {
            case 1:
                GameManager.addGame(userProfile);
                break;
            case 2:
                GameManager.viewLibrary(userProfile);
                break;
            case 3:
                GameManager.searchGamesMenu(userProfile);
                break;
            case 4:
                GameManager.sortGamesMenu(userProfile);
                break;
            case 5:
                GameManager.rateOrReviewGame(userProfile);
                break;
            case 6:
                GameManager.removeGame(userProfile);
                break;
            case 7:
                GameManager.updateGameProgress(userProfile);
                break;
            case 8:
                boolean shouldExit = UserProfileManager.userProfileMenu(userProfile);
                if (shouldExit) {
                    System.exit(0);
                }
                break;
            case 9:
                if (saveData()) {
                    System.out.println("\nThank you for using the Video Games Collection app!");
                }
                break;
        }
    }

    /**
     * Saves the current game library and user profile.
     *
     * @return true if the data was saved successfully, false otherwise
     */
    private boolean saveData() {
        boolean success = DataManager.saveData(GameLibrary.getGames(), userProfile);
        if (success) {
            System.out.println("Data saved successfully!");
        } else {
            System.out.println("Failed to save data. Please try again.");
        }
        return success;
    }

    /**
     * Loads data from files.
     */
    private void loadData() {
        try {
            Object[] data = DataManager.loadData();
            if (data == null || data.length < 2) {
                userProfile = UserProfileManager.initializeDefaultProfile();
                return;
            }

            List<AbstractGame> games = (data[0] instanceof List<?>) ? (List<AbstractGame>) data[0] : new ArrayList<>();
            UserProfile profile = (data[1] instanceof UserProfile) ? (UserProfile) data[1] : null;

            if (profile == null) {
                userProfile = UserProfileManager.initializeDefaultProfile();
                return;
            }

            GameLibrary.setGames(games);
            userProfile = profile;
            LOGGER.info("Loaded " + games.size() + " games from storage");
        } catch (Exception e) {
            LOGGER.severe("Error loading data: " + e.getMessage());
            userProfile = UserProfileManager.initializeDefaultProfile();
        }
    }

    public void cleanup() {
        if (scanner != null) {
            scanner.close();
        }
    }
}
