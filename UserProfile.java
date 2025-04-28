import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Represents a user profile in the video game collection application.
 * Stores user information, owned games, reviews, and ratings.
 */
public class UserProfile implements Serializable {
    private static final long serialVersionUID = 1L;

    private String username;
    private GamePlatform preferredPlatform;
    private List<AbstractGame> gamesOwned;
    private Map<AbstractGame, String> gameReviews;   // Store reviews
    private Map<AbstractGame, Integer> gameRatings;  // Store ratings
    private static final int MIN_RATING = 1;
    private static final int MAX_RATING = 5;

    /**
     * Constructor for UserProfile with GamePlatform enum.
     *
     * @param username The username of the user
     * @param preferredPlatform The preferred gaming platform
     * @throws IllegalArgumentException if username is null or empty
     */
    public UserProfile(String username, GamePlatform preferredPlatform) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }

        this.username = username;
        this.preferredPlatform = preferredPlatform;
        this.gamesOwned = new ArrayList<>();
        this.gameReviews = new HashMap<>();
        this.gameRatings = new HashMap<>();
    }

    /**
     * Constructor for UserProfile with String platform (for backward compatibility).
     *
     * @param username The username of the user
     * @param preferredPlatformStr The preferred gaming platform as a string
     * @throws IllegalArgumentException if username is null or empty
     */
    public UserProfile(String username, String preferredPlatformStr) {
        this(username,
             preferredPlatformStr != null ? GamePlatform.fromString(preferredPlatformStr) : GamePlatform.OTHER);
    }

    /**
     * Gets a defensive copy of the list of games owned by the user.
     *
     * @return A list of games owned by the user
     */
    public List<AbstractGame> getGamesOwned() {
        return new ArrayList<>(gamesOwned); // Return a defensive copy
    }

    /**
     * Gets the username of the user.
     *
     * @return The username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the user.
     *
     * @param username The new username
     * @throws IllegalArgumentException if username is null or empty
     */
    public void setUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        this.username = username;
    }

    /**
     * Gets the preferred platform of the user.
     *
     * @return The preferred platform
     */
    public GamePlatform getPreferredPlatform() {
        return preferredPlatform;
    }

    /**
     * Gets the preferred platform as a string (for backward compatibility).
     *
     * @return The preferred platform as a string
     */
    public String getPreferredPlatformString() {
        return preferredPlatform.getDisplayName();
    }

    /**
     * Sets the preferred platform of the user.
     *
     * @param preferredPlatform The new preferred platform
     */
    public void setPreferredPlatform(GamePlatform preferredPlatform) {
        this.preferredPlatform = preferredPlatform;
    }

    /**
     * Sets the preferred platform of the user using a string (for backward compatibility).
     *
     * @param preferredPlatformStr The new preferred platform as a string
     */
    public void setPreferredPlatform(String preferredPlatformStr) {
        this.preferredPlatform = GamePlatform.fromString(preferredPlatformStr);
    }

    /**
     * Adds a game to the user's collection.
     *
     * @param game The game to add
     * @throws IllegalArgumentException if game is null
     */
    public void addGame(AbstractGame game) {
        if (game == null) {
            throw new IllegalArgumentException("Game cannot be null");
        }

        if (!gamesOwned.contains(game)) {
            gamesOwned.add(game);
        }
    }

    /**
     * Removes a game from the user's collection.
     * Also removes any associated reviews and ratings.
     *
     * @param game The game to remove
     * @return true if the game was removed, false otherwise
     */
    public boolean removeGame(AbstractGame game) {
        if (game == null) {
            return false;
        }

        if (gamesOwned.remove(game)) {
            // Also remove any associated reviews and ratings
            gameReviews.remove(game);
            gameRatings.remove(game);
            return true;
        }
        return false;
    }

    /**
     * Adds or updates a review for a game.
     *
     * @param game The game to review
     * @param review The review text
     * @throws IllegalArgumentException if game is not owned or review is null or empty
     */
    public void reviewGame(AbstractGame game, String review) {
        if (game == null) {
            throw new IllegalArgumentException("Game cannot be null");
        }

        if (review == null || review.trim().isEmpty()) {
            throw new IllegalArgumentException("Review cannot be null or empty");
        }

        if (!gamesOwned.contains(game)) {
            throw new IllegalArgumentException("You can only review games you own");
        }

        gameReviews.put(game, review);
    }

    /**
     * Adds or updates a rating for a game.
     *
     * @param game The game to rate
     * @param rating The rating (1-5)
     * @throws IllegalArgumentException if game is not owned or rating is invalid
     */
    public void rateGame(AbstractGame game, int rating) {
        if (game == null) {
            throw new IllegalArgumentException("Game cannot be null");
        }

        if (!gamesOwned.contains(game)) {
            throw new IllegalArgumentException("You can only rate games you own");
        }

        if (rating < MIN_RATING || rating > MAX_RATING) {
            throw new IllegalArgumentException("Rating must be between " + MIN_RATING + " and " + MAX_RATING);
        }

        gameRatings.put(game, rating);
    }

    /**
     * Gets an unmodifiable view of all game ratings.
     *
     * @return A map of games to ratings
     */
    public Map<AbstractGame, Integer> getGameRatings() {
        return Collections.unmodifiableMap(gameRatings);
    }

    /**
     * Gets an unmodifiable view of all game reviews.
     *
     * @return A map of games to reviews
     */
    public Map<AbstractGame, String> getGameReviews() {
        return Collections.unmodifiableMap(gameReviews);
    }

    /**
     * Gets the review for a specific game.
     *
     * @param game The game to get the review for
     * @return The review, or null if no review exists
     */
    public String getGameReview(AbstractGame game) {
        return gameReviews.get(game);
    }

    /**
     * Gets the rating for a specific game.
     *
     * @param game The game to get the rating for
     * @return The rating, or null if no rating exists
     */
    public Integer getGameRating(AbstractGame game) {
        return gameRatings.get(game);
    }

    /**
     * Searches for games by title (case-insensitive partial match).
     *
     * @param title The title to search for
     * @return A list of matching games
     */
    public List<AbstractGame> searchGamesByTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            return new ArrayList<>();
        }

        String searchTerm = title.toLowerCase();
        return gamesOwned.stream()
                .filter(game -> game.getTitle().toLowerCase().contains(searchTerm))
                .collect(Collectors.toList());
    }

    /**
     * Searches for games by genre.
     *
     * @param genre The genre to search for
     * @return A list of matching games
     */
    public List<AbstractGame> searchGamesByGenre(GameGenre genre) {
        if (genre == null) {
            return new ArrayList<>();
        }

        return gamesOwned.stream()
                .filter(game -> game.getGenre() == genre)
                .collect(Collectors.toList());
    }

    /**
     * Searches for games by platform.
     *
     * @param platform The platform to search for
     * @return A list of matching games
     */
    public List<AbstractGame> searchGamesByPlatform(GamePlatform platform) {
        if (platform == null) {
            return new ArrayList<>();
        }

        return gamesOwned.stream()
                .filter(game -> game.getPlatform() == platform)
                .collect(Collectors.toList());
    }

    /**
     * Gets games sorted by title.
     *
     * @param ascending true for ascending order, false for descending
     * @return A sorted list of games
     */
    public List<AbstractGame> getGamesSortedByTitle(boolean ascending) {
        List<AbstractGame> sortedGames = new ArrayList<>(gamesOwned);

        if (ascending) {
            sortedGames.sort(Comparator.comparing(AbstractGame::getTitle));
        } else {
            sortedGames.sort(Comparator.comparing(AbstractGame::getTitle).reversed());
        }

        return sortedGames;
    }

    /**
     * Gets games sorted by release year.
     *
     * @param ascending true for ascending order, false for descending
     * @return A sorted list of games
     */
    public List<AbstractGame> getGamesSortedByReleaseYear(boolean ascending) {
        List<AbstractGame> sortedGames = new ArrayList<>(gamesOwned);

        if (ascending) {
            sortedGames.sort(Comparator.comparingInt(AbstractGame::getReleaseYear));
        } else {
            sortedGames.sort(Comparator.comparingInt(AbstractGame::getReleaseYear).reversed());
        }

        return sortedGames;
    }

    /**
     * Gets games sorted by rating.
     * Games without ratings will be at the end.
     *
     * @param ascending true for ascending order, false for descending
     * @return A sorted list of games
     */
    public List<AbstractGame> getGamesSortedByRating(boolean ascending) {
        List<AbstractGame> sortedGames = new ArrayList<>(gamesOwned);

        Comparator<AbstractGame> ratingComparator = (g1, g2) -> {
            Integer r1 = gameRatings.get(g1);
            Integer r2 = gameRatings.get(g2);

            // Handle null ratings (games without ratings)
            if (r1 == null && r2 == null) return 0;
            if (r1 == null) return 1;  // Null ratings at the end
            if (r2 == null) return -1;

            return r1.compareTo(r2);
        };

        if (ascending) {
            sortedGames.sort(ratingComparator);
        } else {
            sortedGames.sort(ratingComparator.reversed());
        }

        return sortedGames;
    }

    /**
     * Gets the average rating of all rated games.
     *
     * @return The average rating, or 0 if no games are rated
     */
    public double getAverageRating() {
        if (gameRatings.isEmpty()) {
            return 0.0;
        }

        double sum = 0.0;
        for (Integer rating : gameRatings.values()) {
            sum += rating;
        }

        return sum / gameRatings.size();
    }

    @Override
    public String toString() {
        return "UserProfile{" +
                "username='" + username + '\'' +
                ", preferredPlatform='" + preferredPlatform + '\'' +
                ", gamesOwned=" + gamesOwned.size() +
                ", reviews=" + gameReviews.size() +
                ", ratings=" + gameRatings.size() +
                '}';
    }
}
