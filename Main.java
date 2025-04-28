import java.util.List;
import java.util.Scanner;
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
            initialiseUserProfile();
        } else {
            System.out.println("Welcome back, " + userProfile.getUsername() + "!");
        }

        mainMenu();
    }

    /**
     * Initialises a new user profile.
     */
    private void initialiseUserProfile() {
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

    /**
     * Adds a new game to the user's collection.
     */
    private void addGame() {
        try {
            System.out.println("\n===== ADD NEW GAME =====");

            // Get game title
            System.out.print("Enter game title: ");
            String title = scanner.nextLine();
            if (title.trim().isEmpty()) {
                System.out.println("Title cannot be empty. Operation cancelled.");
                return;
            }

            // Get game genre
            System.out.println("\nSelect game genre:");
            displayGenreOptions();
            GameGenre genre = null;
            while (genre == null) {
                try {
                    System.out.print("Enter genre number: ");
                    int genreChoice = Integer.parseInt(scanner.nextLine());

                    if (genreChoice >= 1 && genreChoice <= GameGenre.values().length) {
                        genre = GameGenre.values()[genreChoice - 1];
                    } else {
                        System.out.println("Invalid choice. Please try again.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a number.");
                }
            }

            // Get game platform
            System.out.println("\nSelect game platform:");
            displayPlatformOptions();
            GamePlatform platform = null;
            while (platform == null) {
                try {
                    System.out.print("Enter platform number: ");
                    int platformChoice = Integer.parseInt(scanner.nextLine());

                    if (platformChoice >= 1 && platformChoice <= GamePlatform.values().length) {
                        platform = GamePlatform.values()[platformChoice - 1];
                    } else {
                        System.out.println("Invalid choice. Please try again.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a number.");
                }
            }

            // Get release year
            int releaseYear = 0;
            while (releaseYear < 1950 || releaseYear > 2100) {
                try {
                    System.out.print("\nEnter release year (1950-2100): ");
                    releaseYear = Integer.parseInt(scanner.nextLine());

                    if (releaseYear < 1950 || releaseYear > 2100) {
                        System.out.println("Year must be between 1950 and 2100. Please try again.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid year.");
                }
            }

            // Get developer
            System.out.print("\nEnter game developer: ");
            String developer = scanner.nextLine();
            if (developer.trim().isEmpty()) {
                System.out.println("Developer cannot be empty. Operation cancelled.");
                return;
            }

            // Determine game type
            System.out.println("\nIs it a Single Player (1) or Multiplayer (2) game?");
            int type = 0;
            while (type != 1 && type != 2) {
                try {
                    System.out.print("Enter choice (1 or 2): ");
                    type = Integer.parseInt(scanner.nextLine());

                    if (type != 1 && type != 2) {
                        System.out.println("Invalid choice. Please enter 1 for Single Player or 2 for Multiplayer.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a number.");
                }
            }

            // Create the game object
            AbstractGame game;
            if (type == 1) {
                int totalLevels = 0;
                while (totalLevels <= 0) {
                    try {
                        System.out.print("\nEnter total levels: ");
                        totalLevels = Integer.parseInt(scanner.nextLine());

                        if (totalLevels <= 0) {
                            System.out.println("Total levels must be greater than zero.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a number.");
                    }
                }

                game = new SinglePlayer(title, genre, platform, releaseYear, developer, totalLevels);
            } else {
                game = new Multiplayer(title, genre, platform, releaseYear, developer);
            }

            // Add the game to the library and user profile
            GameLibrary.add(game);
            userProfile.addGame(game);

            System.out.println("\nGame added successfully!");
            System.out.println(game);

        } catch (IllegalArgumentException e) {
            System.out.println("Error adding game: " + e.getMessage());
        }
    }

    /**
     * Loads data from files.
     */
    private void loadData() {
        Object[] data = DataManager.loadData();
        GameLibrary.setGames((List<AbstractGame>) data[0]);
        userProfile = (UserProfile) data[1];

        LOGGER.info("Loaded " + GameLibrary.getGames().size() + " games from storage");
    }

    /**
     * Allows the user to rate or review a game.
     */
    private void rateOrReviewGame() {
        if (userProfile.getGamesOwned().isEmpty()) {
            System.out.println("You don't own any games yet. Add some games first.");
            return;
        }

        System.out.println("\n===== RATE OR REVIEW GAME =====");
        System.out.print("Enter the title of the game you want to rate/review: ");
        String title = scanner.nextLine();

        List<AbstractGame> matchingGames = userProfile.searchGamesByTitle(title);

        if (matchingGames.isEmpty()) {
            System.out.println("No games found matching '" + title + "' in your collection.");
            return;
        }

        AbstractGame gameToRate;
        if (matchingGames.size() == 1) {
            gameToRate = matchingGames.get(0);
        } else {
            System.out.println("\nMultiple games found. Please select one:");
            for (int i = 0; i < matchingGames.size(); i++) {
                System.out.println((i + 1) + ". " + matchingGames.get(i).getTitle());
            }

            int gameChoice = 0;
            while (gameChoice < 1 || gameChoice > matchingGames.size()) {
                try {
                    System.out.print("Enter game number: ");
                    gameChoice = Integer.parseInt(scanner.nextLine());

                    if (gameChoice < 1 || gameChoice > matchingGames.size()) {
                        System.out.println("Invalid choice. Please try again.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a number.");
                }
            }

            gameToRate = matchingGames.get(gameChoice - 1);
        }

        System.out.println("\nSelected game: " + gameToRate.getTitle());
        System.out.println("1. Rate game (1-5 stars)");
        System.out.println("2. Write review");
        System.out.print("Choose an option (1-2): ");

        try {
            int choice = Integer.parseInt(scanner.nextLine());

            if (choice == 1) {
                int rating = 0;
                while (rating < 1 || rating > 5) {
                    try {
                        System.out.print("Enter your rating (1-5): ");
                        rating = Integer.parseInt(scanner.nextLine());

                        if (rating < 1 || rating > 5) {
                            System.out.println("Rating must be between 1 and 5. Please try again.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a number.");
                    }
                }

                try {
                    userProfile.rateGame(gameToRate, rating);
                    System.out.println("Game rated successfully!");
                } catch (IllegalArgumentException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            } else if (choice == 2) {
                System.out.print("Enter your review: ");
                String review = scanner.nextLine();

                try {
                    userProfile.reviewGame(gameToRate, review);
                    System.out.println("Review added successfully!");
                } catch (IllegalArgumentException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            } else {
                System.out.println("Invalid choice.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        }
    }

    /**
     * Removes a game from the user's collection.
     */
    private void removeGame() {
        if (userProfile.getGamesOwned().isEmpty()) {
            System.out.println("You don't own any games yet.");
            return;
        }

        System.out.println("\n===== REMOVE GAME =====");
        System.out.print("Enter the title of the game you want to remove: ");
        String title = scanner.nextLine();

        List<AbstractGame> matchingGames = userProfile.searchGamesByTitle(title);

        if (matchingGames.isEmpty()) {
            System.out.println("No games found matching '" + title + "' in your collection.");
            return;
        }

        AbstractGame gameToRemove;
        if (matchingGames.size() == 1) {
            gameToRemove = matchingGames.get(0);
        } else {
            System.out.println("\nMultiple games found. Please select one:");
            for (int i = 0; i < matchingGames.size(); i++) {
                System.out.println((i + 1) + ". " + matchingGames.get(i).getTitle());
            }

            int gameChoice = 0;
            while (gameChoice < 1 || gameChoice > matchingGames.size()) {
                try {
                    System.out.print("Enter game number: ");
                    gameChoice = Integer.parseInt(scanner.nextLine());

                    if (gameChoice < 1 || gameChoice > matchingGames.size()) {
                        System.out.println("Invalid choice. Please try again.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a number.");
                }
            }

            gameToRemove = matchingGames.get(gameChoice - 1);
        }

        System.out.println("\nSelected game: " + gameToRemove.getTitle());
        System.out.print("Are you sure you want to remove this game? (y/n): ");
        String confirmation = scanner.nextLine();

        if (confirmation.equalsIgnoreCase("y") || confirmation.equalsIgnoreCase("yes")) {
            if (userProfile.removeGame(gameToRemove)) {
                System.out.println("Game removed successfully!");
            } else {
                System.out.println("Failed to remove the game.");
            }
        } else {
            System.out.println("Operation cancelled.");
        }
    }

    /**
     * Updates the progress of a game.
     */
    private void updateGameProgress() {
        if (userProfile.getGamesOwned().isEmpty()) {
            System.out.println("You don't own any games yet. Add some games first.");
            return;
        }

        System.out.println("\n===== UPDATE GAME PROGRESS =====");
        System.out.print("Enter the title of the game to update: ");
        String title = scanner.nextLine();

        List<AbstractGame> matchingGames = userProfile.searchGamesByTitle(title);

        if (matchingGames.isEmpty()) {
            System.out.println("No games found matching '" + title + "' in your collection.");
            return;
        }

        AbstractGame gameToUpdate;
        if (matchingGames.size() == 1) {
            gameToUpdate = matchingGames.get(0);
        } else {
            System.out.println("\nMultiple games found. Please select one:");
            for (int i = 0; i < matchingGames.size(); i++) {
                System.out.println((i + 1) + ". " + matchingGames.get(i).getTitle());
            }

            int gameChoice = 0;
            while (gameChoice < 1 || gameChoice > matchingGames.size()) {
                try {
                    System.out.print("Enter game number: ");
                    gameChoice = Integer.parseInt(scanner.nextLine());

                    if (gameChoice < 1 || gameChoice > matchingGames.size()) {
                        System.out.println("Invalid choice. Please try again.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a number.");
                }
            }

            gameToUpdate = matchingGames.get(gameChoice - 1);
        }

        System.out.println("\nSelected game: " + gameToUpdate.getTitle());
        System.out.println("Current progress: " + gameToUpdate.getProgress());

        if (gameToUpdate instanceof SinglePlayer) {
            SinglePlayer spGame = (SinglePlayer) gameToUpdate;
            System.out.println("This is a single-player game with " + spGame.getTotalLevels() + " total levels.");
            System.out.print("Enter the number of completed levels: ");
        } else if (gameToUpdate instanceof Multiplayer) {
            System.out.println("This is a multiplayer game with win/loss tracking.");
            System.out.print("Enter your win/loss record in the format 'wins/losses' (e.g., 10/5): ");
        }

        String progressData = scanner.nextLine();

        try {
            gameToUpdate.updateProgress(progressData);
            System.out.println("Progress updated successfully!");
            System.out.println("New progress: " + gameToUpdate.getProgress());
        } catch (IllegalArgumentException e) {
            System.out.println("Error updating progress: " + e.getMessage());
        }
    }

    /**
     * Displays the user profile menu and handles user input.
     */
    private void userProfileMenu() {
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
                    changeUsername();
                    break;
                case 2:
                    changePreferredPlatform();
                    break;
                case 3:
                    addSampleGames();
                    break;
                case 4:
                    deleteAllData();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        }
    }

    /**
     * Changes the user's username.
     */
    private void changeUsername() {
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
     */
    private void changePreferredPlatform() {
        System.out.println("\nSelect your new preferred platform:");
        displayPlatformOptions();

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
     */
    private void deleteAllData() {
        System.out.print("\nWARNING: This will delete all your data. Are you sure? (y/n): ");
        String confirmation = scanner.nextLine();

        if (confirmation.equalsIgnoreCase("y") || confirmation.equalsIgnoreCase("yes")) {
            if (DataManager.deleteAllData()) {
                System.out.println("All data deleted successfully.");
                System.out.println("The application will now exit.");
                System.exit(0);
            } else {
                System.out.println("Failed to delete all data.");
            }
        } else {
            System.out.println("Operation cancelled.");
        }
    }
}
