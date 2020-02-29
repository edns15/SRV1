package com.sr.taller1.data;

import org.apache.mahout.cf.taste.impl.common.FastByIDMap;
import org.apache.mahout.cf.taste.impl.model.GenericDataModel;
import org.apache.mahout.cf.taste.impl.model.GenericPreference;
import org.apache.mahout.cf.taste.impl.model.GenericUserPreferenceArray;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.Preference;
import org.apache.mahout.cf.taste.model.PreferenceArray;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

public class DataRecommendationModels {

    private static DataRecommendationModels instance = null;
    private DataModel trackModel = null;
    private DataModel artistModel = null;

    //user,item,Preference
    private HashMap<Long,HashMap<Long,Preference>> track_user_preferences= new HashMap<>();
    private HashMap<Long,HashMap<Long,Preference>> artist_user_preferences= new HashMap<>();

    public static String track_model = "track_model";
    public static String artist_model = "artist_model";

    private String track_file = "track.csv";
    private String artist_file = "artist.csv";
    private String artist_ids_file = "artist_ids.csv";
    private String track_ids_file = "track_ids.csv";
    private String user_ids_file = "user_ids.csv";

    private HashMap<Long,String> artist_ids= new HashMap<>();
    private HashMap<Long,String> track_ids= new HashMap<>();
    private HashMap<Long,String> user_ids= new HashMap<>();

    private DataRecommendationModels(){
    }

    public static DataRecommendationModels instance() throws IOException {
        if(instance == null) {
            instance = new DataRecommendationModels();
            instance.init();
        }
        return instance;
    }

    private void init() throws IOException {
        this.loadUsers();
        this.loadTracks();
        this.loadArtists();
    }

    private void loadUsers() throws IOException {
        File file = this.loadFileFromResource(user_ids_file);

        try (Stream<String> stream = Files.lines(Paths.get(file.getAbsolutePath()))) {
            stream.forEach((line) -> {
                String[] user = line.split(",");
                Long userId = Long.parseLong(user[0]);
                String userName = user[1];
                user_ids.put(userId,userName);
            });
        } catch (IOException e) {
            throw e;
        }
    }

    public String getUser(Long id){
        return user_ids.get(id);
    }

    private void loadTracks() throws IOException {
        File file = this.loadFileFromResource(track_ids_file);

        try (Stream<String> stream = Files.lines(Paths.get(file.getAbsolutePath()))) {
            stream.forEach((line) -> {
                String[] track = line.split(",");
                Long trackId = Long.parseLong(track[0]);
                String trackName = track[1];
                track_ids.put(trackId,trackName);
            });
        } catch (IOException e) {
            throw e;
        }
    }

    public String getTrack(Long id){
        return track_ids.get(id);
    }

    private void loadArtists() throws IOException {
        File file = this.loadFileFromResource(artist_ids_file);

        try (Stream<String> stream = Files.lines(Paths.get(file.getAbsolutePath()))) {
            stream.forEach((line) -> {
                String[] artist = line.split(",");
                Long artistId = Long.parseLong(artist[0]);
                String artistName = artist[1];
                artist_ids.put(artistId,artistName);
            });
        } catch (IOException e) {
            throw e;
        }
    }

    public String getArtis(Long id){
        return artist_ids.get(id);
    }

    private File loadFileFromResource(String fileName){
        URL url = this.getClass().getClassLoader().getResource(fileName);
        File file = null;
        try {
            file = new File(url.toURI());
        } catch (URISyntaxException e) {
            file = new File(url.getPath());
        }
        return file;
    }

    private void initModelFromFile(String model, String fileName) throws IOException {
        File file = this.loadFileFromResource(fileName);

        try (Stream<String> stream = Files.lines(Paths.get(file.getAbsolutePath()))) {
            stream.forEach((line) -> {
                String[] rank = line.split(",");
                Long user = Long.parseLong(rank[0]);
                Long item = Long.parseLong(rank[1]);
                Long rating = Long.parseLong(rank[2]);
                addRating(model,user,item,rating);
            });
        } catch (IOException e) {
           throw e;
        }

        buildModel(model);
    }

    public void buildModel(String model){

        HashMap<Long,HashMap<Long,Preference>> user_preferences;
        if(model.equals(track_model))
            user_preferences = track_user_preferences;
        else
            user_preferences = artist_user_preferences;

        FastByIDMap<PreferenceArray> userData=new FastByIDMap<>();

        ArrayList<Long> users = new ArrayList<>(user_preferences.keySet());
        for(Long user:users) {

            HashMap<Long,Preference> prefs = user_preferences.get(user);
            List<Preference> prefsUser=new ArrayList<>(prefs.values());
            userData.put(user,new GenericUserPreferenceArray(prefsUser));
        }

        if(model.equals(track_model))
            trackModel = new GenericDataModel(userData);
        else
            artistModel = new GenericDataModel(userData);

    }

    public void addRating(String model, Long user,Long item, Long rating) {
        HashMap<Long,HashMap<Long,Preference>> user_preferences;
        if(model.equals(track_model))
            user_preferences = track_user_preferences;
        else
            user_preferences = artist_user_preferences;

        // Preferences for user
        HashMap<Long,Preference> prefsUser;
        if(!user_preferences.containsKey(user)) {
            prefsUser=new HashMap<>();
            user_preferences.put(user,prefsUser);
        }
        else
            prefsUser = user_preferences.get(user);

        prefsUser.put(item,new GenericPreference(user,item,rating));
    }

    public DataModel getModel(String model) throws IOException {
        if( model.equals(track_model)){
            if(trackModel == null) {
                this.initModelFromFile(model,track_file);
                System.out.println("Track: " + this.trackModel);
            }
            return trackModel;
        }
        else if( model.equals(artist_model)){
            if(this.artistModel == null) {
                this.initModelFromFile(model,artist_file);
                System.out.println("Artist: " + this.artistModel);
            }
            return artistModel;
        }
        return null;
    }
}
