import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private static final String GAME_FILE = "gamedata.ser";
    private static final String PROFILE_FILE = "profiledata.ser";

    public static void saveData(List<AbstractGame> games, UserProfile profile){
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(GAME_FILE))){
            oos.writeObject(games);    
        } catch (IOException e) {
            e.printStackTrace();
        }
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(PROFILE_FILE))){
            oos.writeObject(profile);
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    public static <userProfile> Object[] loadData(){
        List<AbstractGame> games = new ArrayList<>();
        UserProfile profile = new UserProfile(null, null);

        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(GAME_FILE))){
            games = (List<AbstractGame>) ois.readObject();
        }catch (Exception e){

        }

        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(PROFILE_FILE))){
            profile = (UserProfile) ois.readObject();
        } catch (Exception e){

        }
        return new Object[]{
            games, profile
        };

    }
    
}

