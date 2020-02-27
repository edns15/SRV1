package com.sr.data;

import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.model.DataModel;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class DataRecommendationModels {

    private static DataRecommendationModels instance = null;
    private DataModel trackModel = null;
    private DataModel artistModel = null;

    public static String track_model = "track_model";
    public static String artist_model = "artist_model";

    private String track_file = "track.csv";
    private String artist_file = "artist.csv";

    public static DataRecommendationModels instace(){
        if(instance == null) {
            instance = new DataRecommendationModels();
        }
        return instance;
    }

    private DataModel createModel(String fileName){
        URL url = this.getClass().getClassLoader().getResource(fileName);
        File file = null;
        try {
            file = new File(url.toURI());
        } catch (URISyntaxException e) {
            file = new File(url.getPath());
        }

        try {
            DataModel model = new FileDataModel(file);
            return model;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public DataModel getModel(String model){
        if( model.equals(track_model)){
            if(trackModel == null) {
                this.trackModel = this.createModel(track_file);
                System.out.println("Track: " + this.trackModel);
            }
            return trackModel;
        }
        else if( model.equals(artist_model)){
            if(this.artistModel == null) {
                this.artistModel = this.createModel(artist_file);
                System.out.println("Artist: " + this.artistModel);
            }
            return artistModel;
        }
        return null;
    }
}
