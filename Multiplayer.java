public class Multiplayer extends AbstractGame {
    private int wins;
    private int losses;

    public Multiplayer(String title, String genre, String platform, int releaseYear, String developer) {
        super(title, genre, platform, releaseYear, developer);   
    }

    @Override
    public void updateProgress(String progressData){
        String[] parts = progressData.split("/");
        wins = Integer.parseInt(parts[0]);
        losses = Integer.parseInt(parts[1]);
    }
    @Override
    public String getProgress(){
        return "W/L: " + wins + "/" + losses;
    }
}
