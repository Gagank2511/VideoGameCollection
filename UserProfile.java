import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserProfile {
    private String username;
    private String preferredPlatform;
    private List<AbstractGame> gamesOwned;
    private Map<AbstractGame, String> gameReviews;   //Store reviews 
    private Map<AbstractGame, Integer> gameRatings;  // store ratings

    
    public UserProfile(String username, String preferredPlatform){
        this.username = username;
        this.preferredPlatform = preferredPlatform;
        this.gamesOwned = new ArrayList<>();
        this.gameReviews = new HashMap<>();
        this.gameRatings = new HashMap<>();
        
    }
    public List<AbstractGame> getGamesOwned() {
        return new ArrayList<AbstractGame>();
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPreferredPlatform() {
        return preferredPlatform;
    }
    public void setPreferredPlatform(String preferredPlatform) {
        this.preferredPlatform = preferredPlatform;
    }


    public void addGame(AbstractGame game){
        gamesOwned.add(game);
    }

    public void reviewGame(AbstractGame game, String review){
        if(gamesOwned.contains(game)){
            gameReviews.put(game, review);
        }
    }
    public void rateGame(AbstractGame game, int rating){
        if(gamesOwned.contains(game)){
            gameRatings.put(game, rating);
        }
    }

    public Map<AbstractGame, Integer> getGameRatings(){
        return gameRatings;
    }

    public Map<AbstractGame, String> getGameReviews(){
        return gameReviews;
    }

}
