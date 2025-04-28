import java.util.List;
import java.util.Scanner;

public class Main {
    Scanner scanner = new Scanner(System.in);
    UserProfile userProfile;

    public static void main(String[] args) throws UnsupportedOperationException {
        Main app = new Main();
        app.initialiseGames();

    }

    private void initialiseGames() {
        System.out.println("Welcome to the Video Games Collection app!");
         
        System.out.println("Enter your username: ");
        String username = scanner.nextLine();
        System.out.println("Enter your preferred platform: ");
        String platform = scanner.nextLine();
        userProfile= new UserProfile(username, platform);
        
        boolean running = true;
        while(running){
            System.out.println("\n Menu: ");
            System.out.println("1. Add game");
            System.out.println("2. View Library");
            System.out.println("3. Search game");
            System.out.println("4. Save and Exit");
            System.out.println("Choose an option: ");
            int choice =  scanner.nextInt();
            
            while (choice < 1 || choice > 4) {
                System.out.println("Choose an option (1-4): ");
                while (!scanner.hasNextInt()) {
                    System.out.println("Invalid input. Please enter a number between 1 and 4.");
                    scanner.next();
                }
                choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addGame();
                    break;
                case 2:
                    viewLibrary();
                    break;

                case 3:
                    searchGame();
                    break;
                case 4:
                    saveData(userProfile);
                    System.exit(0);
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }     
    }}
 
    SinglePlayer sPgame1 = new SinglePlayer("The Legend of Zelda: Breath of the Wild", "Action-Adventure", "Nintendo Switch", 2017, "Nintendo", 120);
    SinglePlayer sPgame2 = new SinglePlayer("God of War", "Action-Adventure", "PlayStation 4", 2018, "Santa Monico Studio", 26);

    Multiplayer mPgame3 = new Multiplayer("Fortnite", "Battle Royale", "Multiple Platforms", 2017, "Epic Games");
    Multiplayer mPgame4 = new Multiplayer("Call of Duty: Warzone", "Battle Royale", "Multiple Platforms", 2020, "Infinity Ward");

    // Adding games to the game library
    // gameLibrary.add(sPgame1);
    // gameLibrary.add(sPgame2);
    // gameLibrary.add(mPgame3);
    // gameLibrary.add(mPgame4);

    private void saveData(UserProfile userProfile) {
       DataManager.saveData(GameLibrary.getGames(), userProfile);
       System.out.println("Data saved successfully!");
    }

    private void searchGame() {
        System.out.println("Enter tite to search: ");
        String title = scanner.nextLine();
        for (AbstractGame game : userProfile.getGamesOwned()) {
            if (game.getTitle().equalsIgnoreCase(title)) {
                System.out.println("Found: " + game.getTitle() + " - " + game.getGenre());
                return;
            }
        }
        System.out.println("Game not found.");
    }

    private void viewLibrary() {
       System.out.println("Your Game Library: ");
       for(AbstractGame game : userProfile.getGamesOwned()){
            System.out.println(game.getTitle() + "-"  + game.getGenre());
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
    }
    
   
}
