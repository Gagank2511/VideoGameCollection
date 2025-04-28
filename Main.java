import java.util.List;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        try {
            Main app = new Main();
            app.loadData(); // Try to load existing data first
            app.run();
        } catch (Exception e) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, "Application error", e);
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    /**
     * Runs the main application loop.
     */
    private void run() {
        if (userProfile == null) {
            initializeUserProfile();
        } else {
            System.out.println("Welcome back, " + userProfile.getUsername() + "!");
        }

        mainMenu();
    }

    /**
     * Initializes a new user profile.
     */
    private void initializeUserProfile() {
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
        displayPlatformOptions();

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
    }

    /**
     * Displays all available platform options.
     */
    private void displayPlatformOptions() {
        GamePlatform[] platforms = GamePlatform.values();
        for (int i = 0; i < platforms.length; i++) {
            System.out.println((i + 1) + ". " + platforms[i].getDisplayName());
        }
    }

    /**
     * Displays all available genre options.
     */
    private void displayGenreOptions() {
        GameGenre[] genres = GameGenre.values();
        for (int i = 0; i < genres.length; i++) {
            System.out.println((i + 1) + ". " + genres[i].getDisplayName());
        }
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
                System.out.print("Choose an option (1-9): ");

                int choice;
                try {
                    choice = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a number.");
                    continue;
                }

                if (choice < 1 || choice > 9) {
                    System.out.println("Invalid choice. Please enter a number between 1 and 9.");
                    continue;
                }

                switch (choice) {
                    case 1:
                        addGame();
                        break;
                    case 2:
                        viewLibrary();
                        break;
                    case 3:
                        searchGamesMenu();
                        break;
                    case 4:
                        sortGamesMenu();
                        break;
                    case 5:
                        rateOrReviewGame();
                        break;
                    case 6:
                        removeGame();
                        break;
                    case 7:
                        updateGameProgress();
                        break;
                    case 8:
                        userProfileMenu();
                        break;
                    case 9:
                        if (saveData()) {
                            System.out.println("Thank you for using the Video Games Collection app!");
                            running = false;
                        }
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
                LOGGER.log(Level.WARNING, "Error in main menu", e);
            }
        }
    }

    /**
     * Adds sample games to the library for testing purposes.
     */
    private void addSampleGames() {
        // Create sample single-player games
        SinglePlayer zelda = new SinglePlayer(
            "The Legend of Zelda: Breath of the Wild",
            GameGenre.ACTION_ADVENTURE,
            GamePlatform.NINTENDO_SWITCH,
            2017,
            "Nintendo",
            120
        );

        SinglePlayer godOfWar = new SinglePlayer(
            "God of War",
            GameGenre.ACTION_ADVENTURE,
            GamePlatform.PLAYSTATION_4,
            2018,
            "Santa Monica Studio",
            26
        );

        // Create sample multiplayer games
        Multiplayer fortnite = new Multiplayer(
            "Fortnite",
            GameGenre.BATTLE_ROYALE,
            GamePlatform.MULTIPLE,
            2017,
            "Epic Games"
        );

        Multiplayer warzone = new Multiplayer(
            "Call of Duty: Warzone",
            GameGenre.BATTLE_ROYALE,
            GamePlatform.MULTIPLE,
            2020,
            "Infinity Ward"
        );

        // Add games to library and user profile
        GameLibrary.add(zelda);
        GameLibrary.add(godOfWar);
        GameLibrary.add(fortnite);
        GameLibrary.add(warzone);

        userProfile.addGame(zelda);
        userProfile.addGame(godOfWar);
        userProfile.addGame(fortnite);
        userProfile.addGame(warzone);

        System.out.println("Sample games added to your library!");
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
     * Displays the search games menu and handles user input.
     */
    private void searchGamesMenu() {
        System.out.println("\n===== SEARCH GAMES =====");
        System.out.println("1. Search by title");
        System.out.println("2. Search by genre");
        System.out.println("3. Search by platform");
        System.out.println("4. Return to main menu");
        System.out.print("Choose an option (1-4): ");

        try {
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    searchGamesByTitle();
                    break;
                case 2:
                    searchGamesByGenre();
                    break;
                case 3:
                    searchGamesByPlatform();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        }
    }

    /**
     * Searches for games by title.
     */
    private void searchGamesByTitle() {
        System.out.print("Enter title to search: ");
        String title = scanner.nextLine();

        List<AbstractGame> results = userProfile.searchGamesByTitle(title);

        if (results.isEmpty()) {
            System.out.println("No games found matching '" + title + "'.");
        } else {
            System.out.println("\nFound " + results.size() + " game(s) matching '" + title + "':");
            displayGamesList(results);
        }
    }

    /**
     * Searches for games by genre.
     */
    private void searchGamesByGenre() {
        System.out.println("\nSelect a genre to search for:");
        displayGenreOptions();

        try {
            int genreChoice = Integer.parseInt(scanner.nextLine());

            if (genreChoice >= 1 && genreChoice <= GameGenre.values().length) {
                GameGenre genre = GameGenre.values()[genreChoice - 1];
                List<AbstractGame> results = userProfile.searchGamesByGenre(genre);

                if (results.isEmpty()) {
                    System.out.println("No games found in the " + genre + " genre.");
                } else {
                    System.out.println("\nFound " + results.size() + " game(s) in the " + genre + " genre:");
                    displayGamesList(results);
                }
            } else {
                System.out.println("Invalid choice.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        }
    }

    /**
     * Searches for games by platform.
     */
    private void searchGamesByPlatform() {
        System.out.println("\nSelect a platform to search for:");
        displayPlatformOptions();

        try {
            int platformChoice = Integer.parseInt(scanner.nextLine());

            if (platformChoice >= 1 && platformChoice <= GamePlatform.values().length) {
                GamePlatform platform = GamePlatform.values()[platformChoice - 1];
                List<AbstractGame> results = userProfile.searchGamesByPlatform(platform);

                if (results.isEmpty()) {
                    System.out.println("No games found on the " + platform + " platform.");
                } else {
                    System.out.println("\nFound " + results.size() + " game(s) on the " + platform + " platform:");
                    displayGamesList(results);
                }
            } else {
                System.out.println("Invalid choice.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        }
    }

    /**
     * Displays a list of games with detailed information.
     *
     * @param games The list of games to display
     */
    private void displayGamesList(List<AbstractGame> games) {
        if (games.isEmpty()) {
            System.out.println("No games to display.");
            return;
        }

        System.out.println("\n--------------------------------------------------");
        for (AbstractGame game : games) {
            System.out.println("Title: " + game.getTitle());
            System.out.println("Genre: " + game.getGenreString());
            System.out.println("Platform: " + game.getPlatformString());
            System.out.println("Developer: " + game.getDeveloper());
            System.out.println("Release Year: " + game.getReleaseYear());

            // Display progress
            System.out.println("Progress: " + game.getProgress());

            // Display rating if available
            Integer rating = userProfile.getGameRating(game);
            if (rating != null) {
                System.out.println("Your Rating: " + rating + "/5");
            }

            // Display review if available
            String review = userProfile.getGameReview(game);
            if (review != null) {
                System.out.println("Your Review: " + review);
            }

            System.out.println("--------------------------------------------------");
        }
    }

    /**
     * Displays the user's game library.
     */
    private void viewLibrary() {
        List<AbstractGame> games = userProfile.getGamesOwned();
        if (games.isEmpty()) {
            System.out.println("Your game library is empty. Add some games first!");
            return;
        }

        System.out.println("\n===== YOUR GAME LIBRARY =====");
        System.out.println("Total games: " + games.size());

        if (!userProfile.getGameRatings().isEmpty()) {
            System.out.printf("Average rating: %.1f/5\n", userProfile.getAverageRating());
        }

        displayGamesList(games);
    }

    /**
     * Displays the sort games menu and handles user input.
     */
    private void sortGamesMenu() {
        if (userProfile.getGamesOwned().isEmpty()) {
            System.out.println("Your game library is empty. Add some games first!");
            return;
        }

        System.out.println("\n===== SORT GAMES =====");
        System.out.println("1. Sort by title (A-Z)");
        System.out.println("2. Sort by title (Z-A)");
        System.out.println("3. Sort by release year (oldest first)");
        System.out.println("4. Sort by release year (newest first)");
        System.out.println("5. Sort by rating (lowest first)");
        System.out.println("6. Sort by rating (highest first)");
        System.out.println("7. Return to main menu");
        System.out.print("Choose an option (1-7): ");

        try {
            int choice = Integer.parseInt(scanner.nextLine());
            List<AbstractGame> sortedGames;

            switch (choice) {
                case 1:
                    sortedGames = userProfile.getGamesSortedByTitle(true);
                    System.out.println("\nGames sorted by title (A-Z):");
                    displayGamesList(sortedGames);
                    break;
                case 2:
                    sortedGames = userProfile.getGamesSortedByTitle(false);
                    System.out.println("\nGames sorted by title (Z-A):");
                    displayGamesList(sortedGames);
                    break;
                case 3:
                    sortedGames = userProfile.getGamesSortedByReleaseYear(true);
                    System.out.println("\nGames sorted by release year (oldest first):");
                    displayGamesList(sortedGames);
                    break;
                case 4:
                    sortedGames = userProfile.getGamesSortedByReleaseYear(false);
                    System.out.println("\nGames sorted by release year (newest first):");
                    displayGamesList(sortedGames);
                    break;
                case 5:
                    sortedGames = userProfile.getGamesSortedByRating(true);
                    System.out.println("\nGames sorted by rating (lowest first):");
                    displayGamesList(sortedGames);
                    break;
                case 6:
                    sortedGames = userProfile.getGamesSortedByRating(false);
                    System.out.println("\nGames sorted by rating (highest first):");
                    displayGamesList(sortedGames);
                    break;
                case 7:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        }
    }

    private void addGame() {
        System.out.println("Enter game title: ");
        String title = scanner.nextLine();
        System.out.println("Enter game genre: ");
        String genre =  scanner.nextLine();
        System.out.println("Enter game platform: ");
        String platform =  scanner.nextLine();
        System.out.println("Enter game release year: ");
        int releaseYear =  scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter game developer: ");
        String developer =  scanner.nextLine();

        System.out.println("Is it a Single Player (1) or Multiplayer (2) game? ");
        int type = scanner.nextInt();
        scanner.nextLine();


        AbstractGame game;
        if(type == 1){
            System.out.println("Enter total levels: ");
            int totalLevels = scanner.nextInt();
            game = new SinglePlayer(title, genre, platform, releaseYear, developer, totalLevels);
        } else{
            game = new Multiplayer(title, genre, platform, releaseYear, developer);
        }
        GameLibrary.add(game);
        //UserProfile userProfile = new UserProfile(null, null);
        userProfile.addGame(game);
        System.out.println("Games added successfully!");
    }

    private void loadData(){
        Object[] data = DataManager.loadData();
        GameLibrary.setGames((List<AbstractGame>) data[0]);
        // Also load the user profile
        userProfile = (UserProfile) data[1];
    }

    private void rateOrReviewGame() {
        if (userProfile.getGamesOwned().isEmpty()) {
            System.out.println("You don't own any games yet. Add some games first.");
            return;
        }

        System.out.println("Enter the title of the game you want to rate/review: ");
        String title = scanner.nextLine();

        AbstractGame gameToRate = null;
        for (AbstractGame game : userProfile.getGamesOwned()) {
            if (game.getTitle().equalsIgnoreCase(title)) {
                gameToRate = game;
                break;
            }
        }

        if (gameToRate == null) {
            System.out.println("Game not found in your collection.");
            return;
        }

        System.out.println("Do you want to (1) Rate or (2) Review this game? ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (choice == 1) {
            System.out.println("Enter your rating (1-5): ");
            int rating = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            try {
                userProfile.rateGame(gameToRate, rating);
                System.out.println("Game rated successfully!");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        } else if (choice == 2) {
            System.out.println("Enter your review: ");
            String review = scanner.nextLine();
            userProfile.reviewGame(gameToRate, review);
            System.out.println("Review added successfully!");
        } else {
            System.out.println("Invalid choice.");
        }
    }

    private void removeGame() {
        if (userProfile.getGamesOwned().isEmpty()) {
            System.out.println("You don't own any games yet.");
            return;
        }

        System.out.println("Enter the title of the game you want to remove: ");
        String title = scanner.nextLine();

        AbstractGame gameToRemove = null;
        for (AbstractGame game : userProfile.getGamesOwned()) {
            if (game.getTitle().equalsIgnoreCase(title)) {
                gameToRemove = game;
                break;
            }
        }

        if (gameToRemove == null) {
            System.out.println("Game not found in your collection.");
            return;
        }

        if (userProfile.removeGame(gameToRemove)) {
            System.out.println("Game removed successfully!");
        } else {
            System.out.println("Failed to remove the game.");
        }
    }
}
