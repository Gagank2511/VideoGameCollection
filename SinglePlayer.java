public class SinglePlayer extends AbstractGame {
    private int levelsCompleted;
    private int totalLevels;

    public SinglePlayer(String title, String genre, String platform, int releaseYear, String developer, int totalLevels){
        super(title, genre, platform, releaseYear, developer);
        this.totalLevels = totalLevels;
    }

    @Override
    public void updateProgress(String progressData){
        this.levelsCompleted = Integer.parseInt(progressData);
    }

    @Override
    public String getProgress(){
        return levelsCompleted + "/" + totalLevels + "Levels completed";
    }
}
