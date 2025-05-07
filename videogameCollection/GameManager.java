package videogameCollection;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import videogameCollection.game.AbstractGame;
import videogameCollection.game.Multiplayer;
import videogameCollection.game.SinglePlayer;

/**
 * Manager class for game-related functionality.
 * Handles game addition, removal, searching, and sorting.
 */
public class GameManager {
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Adds a new game to the user's collection.
     *
     * @param userProfile The user profile to add the game to
     */
    public static void addGame(UserProfile userProfile) {
        try {
            System.out.println("\n===== ADD NEW GAME =====");

            // Get game title
            System.out.print("Enter game title: ");
            String title = scanner.nextLine().trim();
            if (title.isEmpty()) {
                System.out.println("Title cannot be empty. Operation cancelled.");
                return;
            }

            // Get game genre
            System.out.println("\nSelect game genre:");
            UIHelper.displayGenreOptions();
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
            UIHelper.displayPlatformOptions();
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

            // Validate release year
            int releaseYear;
            int currentYear = java.time.Year.now().getValue();
            do {
                System.out.print("Enter release year (1950-" + currentYear + "): ");
                try {
                    releaseYear = Integer.parseInt(scanner.nextLine().trim());
                    if (releaseYear < 1950 || releaseYear > currentYear) {
                        System.out.println("Please enter a valid year between 1950 and " + currentYear);
                        continue;
                    }
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid year.");
                }
            } while (true);

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
            if (addGameSafely(game, userProfile)) {
                System.out.println("\nGame added successfully!");
                System.out.println(game);
            } else {
                System.out.println("Failed to add game. Please try again.");
            }

        } catch (IllegalArgumentException e) {
            System.out.println("Error adding game: " + e.getMessage());
        }
    }

    /**
     * Adds a game to the library and user profile, and saves the data.
     *
     * @param game The game to add
     * @param userProfile The user profile to add the game to
     * @return true if the game was added successfully, false otherwise
     */
    public static boolean addGameSafely(AbstractGame game, UserProfile userProfile) {
        GameLibrary.add(game);
        userProfile.addGame(game);
        boolean isSaved = DataManager.saveData(GameLibrary.getGames(), userProfile);
        if (isSaved) {
            System.out.println("\nSaved!");
            return true;
        }
        return false;
    }

    /**
     * Adds sample games to the library for testing purposes.
     *
     * @param userProfile The user profile to add the games to
     */
    public static void addSampleGames(UserProfile userProfile) {
        // Create sample single-player games
        SinglePlayer zelda = new SinglePlayer(
                "The Legend of Zelda: Breath of the Wild",
                GameGenre.ACTION_ADVENTURE,
                GamePlatform.NINTENDO_SWITCH,
                2017,
                "Nintendo",
                120);

        SinglePlayer godOfWar = new SinglePlayer(
                "God of War",
                GameGenre.ACTION_ADVENTURE,
                GamePlatform.PLAYSTATION_4,
                2018,
                "Santa Monica Studio",
                26);

        // Create sample multiplayer games
        Multiplayer fortnite = new Multiplayer(
                "Fortnite",
                GameGenre.BATTLE_ROYALE,
                GamePlatform.MULTIPLE,
                2017,
                "Epic Games");

        Multiplayer warzone = new Multiplayer(
                "Call of Duty: Warzone",
                GameGenre.BATTLE_ROYALE,
                GamePlatform.MULTIPLE,
                2020,
                "Infinity Ward");

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
     * Displays the user's game library.
     *
     * @param userProfile The user profile to display the library for
     */
    public static void viewLibrary(UserProfile userProfile) {
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

        UIHelper.displayGamesList(games);
    }

    /**
     * Displays the search games menu and handles user input.
     *
     * @param userProfile The user profile to search games for
     */
    public static void searchGamesMenu(UserProfile userProfile) {
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
                    searchGamesByTitle(userProfile);
                    break;
                case 2:
                    searchGamesByGenre(userProfile);
                    break;
                case 3:
                    searchGamesByPlatform(userProfile);
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
     *
     * @param userProfile The user profile to search games for
     */
    private static void searchGamesByTitle(UserProfile userProfile) {
        System.out.print("\nEnter title to search (or press Enter to cancel): ");
        String title = scanner.nextLine().trim().toLowerCase();

        if (title.isEmpty()) {
            System.out.println("Search cancelled.");
            return;
        }

        List<AbstractGame> results = userProfile.getGamesOwned().stream()
            .filter(game -> game.getTitle().toLowerCase().contains(title))
            .collect(Collectors.toList());

        if (results.isEmpty()) {
            System.out.println("\nNo games found matching '" + title + "'.");
        } else {
            System.out.println("\nFound " + results.size() + " game(s):");
            UIHelper.displayGamesList(results);
        }
    }

    /**
     * Searches for games by genre.
     *
     * @param userProfile The user profile to search games for
     */
    private static void searchGamesByGenre(UserProfile userProfile) {
        System.out.println("\nSelect a genre to search for:");
        UIHelper.displayGenreOptions();

        try {
            int genreChoice = Integer.parseInt(scanner.nextLine());

            if (genreChoice >= 1 && genreChoice <= GameGenre.values().length) {
                GameGenre genre = GameGenre.values()[genreChoice - 1];
                List<AbstractGame> results = userProfile.searchGamesByGenre(genre);

                if (results.isEmpty()) {
                    System.out.println("No games found in the " + genre + " genre.");
                } else {
                    System.out.println("\nFound " + results.size() + " game(s) in the " + genre + " genre:");
                    UIHelper.displayGamesList(results);
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
     *
     * @param userProfile The user profile to search games for
     */
    private static void searchGamesByPlatform(UserProfile userProfile) {
        System.out.println("\nSelect a platform to search for:");
        UIHelper.displayPlatformOptions();

        try {
            int platformChoice = Integer.parseInt(scanner.nextLine());

            if (platformChoice >= 1 && platformChoice <= GamePlatform.values().length) {
                GamePlatform platform = GamePlatform.values()[platformChoice - 1];
                List<AbstractGame> results = userProfile.searchGamesByPlatform(platform);

                if (results.isEmpty()) {
                    System.out.println("No games found on the " + platform + " platform.");
                } else {
                    System.out.println("\nFound " + results.size() + " game(s) on the " + platform + " platform:");
                    UIHelper.displayGamesList(results);
                }
            } else {
                System.out.println("Invalid choice.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        }
    }

    /**
     * Displays the sort games menu and handles user input.
     *
     * @param userProfile The user profile to sort games for
     */
    public static void sortGamesMenu(UserProfile userProfile) {
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
                    UIHelper.displayGamesList(sortedGames);
                    break;
                case 2:
                    sortedGames = userProfile.getGamesSortedByTitle(false);
                    System.out.println("\nGames sorted by title (Z-A):");
                    UIHelper.displayGamesList(sortedGames);
                    break;
                case 3:
                    sortedGames = userProfile.getGamesSortedByReleaseYear(true);
                    System.out.println("\nGames sorted by release year (oldest first):");
                    UIHelper.displayGamesList(sortedGames);
                    break;
                case 4:
                    sortedGames = userProfile.getGamesSortedByReleaseYear(false);
                    System.out.println("\nGames sorted by release year (newest first):");
                    UIHelper.displayGamesList(sortedGames);
                    break;
                case 5:
                    sortedGames = userProfile.getGamesSortedByRating(true);
                    System.out.println("\nGames sorted by rating (lowest first):");
                    UIHelper.displayGamesList(sortedGames);
                    break;
                case 6:
                    sortedGames = userProfile.getGamesSortedByRating(false);
                    System.out.println("\nGames sorted by rating (highest first):");
                    UIHelper.displayGamesList(sortedGames);
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
     * Removes a game from the user's collection.
     *
     * @param userProfile The user profile to remove the game from
     */
    public static void removeGame(UserProfile userProfile) {
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
     *
     * @param userProfile The user profile to update the game progress for
     */
    public static void updateGameProgress(UserProfile userProfile) {
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
     * Allows the user to rate or review a game.
     *
     * @param userProfile The user profile to rate or review a game for
     */
    public static void rateOrReviewGame(UserProfile userProfile) {
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
}
