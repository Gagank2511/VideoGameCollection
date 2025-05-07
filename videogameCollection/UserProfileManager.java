package videogameCollection;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * Manager class for user profile-related functionality.
 * Handles user profile creation, modification, and management.
 */
public class UserProfileManager {
    private static final Logger LOGGER = Logger.getLogger(UserProfileManager.class.getName());
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Initialises a new user profile.
     * 
     * @return The created user profile
     */
    public static UserProfile initialiseUserProfile() {
        System.out.println("Welcome to the Video Games Collection app!");
        System.out.println("Let's set up your profile.");

        String username = "";
        while (username.trim().isEmpty()) {
            try {
                System.out.print("Enter your username: ");
                username = scanner.nextLine();
                if (username.trim().isEmpty()) {
                    System.out.println("Username cannot be empty. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please try again.");
                scanner.nextLine(); // Clear the scanner
            }
        }

        System.out.println("\nSelect your preferred gaming platform:");
        UIHelper.displayPlatformOptions();

        GamePlatform platform = null;
        while (platform == null) {
            try {
                System.out.print("Enter the number of your preferred platform: ");
                int platformChoice = Integer.parseInt(scanner.nextLine());

                if (platformChoice >= 1 && platformChoice <= GamePlatform.values().length) {
                    platform = GamePlatform.values()[platformChoice - 1];
                } else {
                    System.out.println("Invalid choice. Please enter a number between 1 and " +
                            GamePlatform.values().length);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }

        UserProfile userProfile;
        try {
            userProfile = new UserProfile(username, platform);
            System.out.println("\nProfile created successfully!");
            System.out.println("Username: " + userProfile.getUsername());
            System.out.println("Preferred Platform: " + userProfile.getPreferredPlatform());
        } catch (IllegalArgumentException e) {
            System.out.println("Error creating profile: " + e.getMessage());
            System.out.println("Using default profile instead.");
            userProfile = new UserProfile("Guest", GamePlatform.OTHER);
        }
        
        return userProfile;
    }

    /**
     * Displays the user profile menu and handles user input.
     * 
     * @param userProfile The user profile to manage
     * @return true if the application should exit, false otherwise
     */
    public static boolean userProfileMenu(UserProfile userProfile) {
        System.out.println("\n===== USER PROFILE =====");
        System.out.println("Username: " + userProfile.getUsername());
        System.out.println("Preferred Platform: " + userProfile.getPreferredPlatform());
        System.out.println("Games Owned: " + userProfile.getGamesOwned().size());

        if (!userProfile.getGameRatings().isEmpty()) {
            System.out.printf("Average Rating: %.1f/5\n", userProfile.getAverageRating());
        }

        System.out.println("\n1. Change username");
        System.out.println("2. Change preferred platform");
        System.out.println("3. Add sample games");
        System.out.println("4. Delete all data");
        System.out.println("5. Return to main menu");
        System.out.print("Choose an option (1-5): ");

        try {
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    changeUsername(userProfile);
                    break;
                case 2:
                    changePreferredPlatform(userProfile);
                    break;
                case 3:
                    GameManager.addSampleGames(userProfile);
                    break;
                case 4:
                    return deleteAllData();
                case 5:
                    return false;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        }
        
        return false;
    }

    /**
     * Changes the user's username.
     * 
     * @param userProfile The user profile to modify
     */
    private static void changeUsername(UserProfile userProfile) {
        System.out.print("\nEnter new username: ");
        String newUsername = scanner.nextLine();

        try {
            userProfile.setUsername(newUsername);
            System.out.println("Username changed successfully to: " + userProfile.getUsername());
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Changes the user's preferred platform.
     * 
     * @param userProfile The user profile to modify
     */
    private static void changePreferredPlatform(UserProfile userProfile) {
        System.out.println("\nSelect your new preferred platform:");
        UIHelper.displayPlatformOptions();

        try {
            int platformChoice = Integer.parseInt(scanner.nextLine());

            if (platformChoice >= 1 && platformChoice <= GamePlatform.values().length) {
                GamePlatform platform = GamePlatform.values()[platformChoice - 1];
                userProfile.setPreferredPlatform(platform);
                System.out.println("Preferred platform changed successfully to: " + platform);
            } else {
                System.out.println("Invalid choice.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        }
    }

    /**
     * Deletes all user data after confirmation.
     * 
     * @return true if the application should exit, false otherwise
     */
    private static boolean deleteAllData() {
        System.out.print("\nWARNING: This will delete all your data. Are you sure? (y/n): ");
        String confirmation = scanner.nextLine();

        if (confirmation.equalsIgnoreCase("y") || confirmation.equalsIgnoreCase("yes")) {
            if (DataManager.deleteAllData()) {
                System.out.println("All data deleted successfully.");
                System.out.println("The application will now exit.");
                return true;
            } else {
                System.out.println("Failed to delete all data.");
            }
        } else {
            System.out.println("Operation cancelled.");
        }
        
        return false;
    }

    /**
     * Initializes a default profile.
     * 
     * @return The default user profile
     */
    public static UserProfile initializeDefaultProfile() {
        LOGGER.warning("Creating default profile");
        UserProfile userProfile = new UserProfile("Guest", GamePlatform.OTHER);
        GameLibrary.setGames(new ArrayList<>());
        return userProfile;
    }
}
