package com.sr.data;

import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.model.DataModel;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class DataRecommendationModels {

    private static DataRecommendationModels instance = null;
    private DataModel trackModel;
    private DataModel artistModel;

    public static String track_model = "track_model";
    public static String artist_model = "artist_model";

    public static DataRecommendationModels instace(){
        if(instance == null) {
            instance = new DataRecommendationModels();
            instance.loadData();
        }
        return instance;
    }

    private void loadData() {
        this.trackModel = this.createModel("track.csv");
        System.out.println("Track: "+this.trackModel);
        this.artistModel = this.createModel("artist.csv");
        System.out.println("Artist: "+this.artistModel);

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
            return trackModel;
        }
        else if( model.equals(artist_model)){
            return artistModel;
        }
        return null;
    }
}
