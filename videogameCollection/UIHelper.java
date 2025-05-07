package videogameCollection;

import java.util.List;
import java.util.Scanner;

import videogameCollection.game.AbstractGame;

/**
 * Helper class for UI-related functionality.
 * Contains methods for displaying information to the user.
 */
public class UIHelper {
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Displays all available platform options.
     */
    public static void displayPlatformOptions() {
        GamePlatform[] platforms = GamePlatform.values();
        for (int i = 0; i < platforms.length; i++) {
            System.out.println((i + 1) + ". " + platforms[i].getDisplayName());
        }
    }

    /**
     * Displays all available genre options.
     */
    public static void displayGenreOptions() {
        GameGenre[] genres = GameGenre.values();
        for (int i = 0; i < genres.length; i++) {
            System.out.println((i + 1) + ". " + genres[i].getDisplayName());
        }
    }

    /**
     * Displays a list of games with detailed information.
     *
     * @param games The list of games to display
     */
    public static void displayGamesList(List<AbstractGame> games) {
        if (games.isEmpty()) {
            System.out.println("\nNo games to display.");
            return;
        }

        final int ITEMS_PER_PAGE = 5;
        int currentPage = 1;
        int totalPages = (games.size() + ITEMS_PER_PAGE - 1) / ITEMS_PER_PAGE;

        while (true) {
            System.out.println("\n----------------------------------------");
            int start = (currentPage - 1) * ITEMS_PER_PAGE;
            int end = Math.min(start + ITEMS_PER_PAGE, games.size());

            for (int i = start; i < end; i++) {
                System.out.println("\n" + (i + 1) + ". " + games.get(i).toString());
            }

            if (totalPages > 1) {
                System.out.println("\n----------------------------------------");
                System.out.printf("Page %d of %d\n", currentPage, totalPages);
                System.out.print("(N)ext, (P)revious, or (E)xit to menu: ");
                
                String choice = scanner.nextLine().trim().toUpperCase();
                if (choice.equals("N") && currentPage < totalPages) {
                    currentPage++;
                } else if (choice.equals("P") && currentPage > 1) {
                    currentPage--;
                } else if (choice.equals("E")) {
                    break;
                }
            } else {
                System.out.println("\nPress Enter to continue...");
                scanner.nextLine();
                break;
            }
        }
    }
}
