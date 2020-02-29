package com.sr.taller1.recommender;

import com.sr.taller1.data.DataRecommendationModels;
import com.sr.taller1.model.Recommendation;
import com.sr.taller1.recommender.item.ItemCosineRecommenderBuilder;
import com.sr.taller1.recommender.item.ItemJaccardRecommenderBuilder;
import com.sr.taller1.recommender.item.ItemPearsonRecommenderBuilder;
import com.sr.taller1.recommender.user.UserCosineRecommenderBuilder;
import com.sr.taller1.recommender.user.UserJaccardRecommenderBuilder;
import com.sr.taller1.recommender.user.UserPearsonRecommenderBuilder;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RecommenderManager {

    private static RecommenderManager instance = null;

    private Recommender artistRecommender_user_jaccard;
    private Recommender artistRecommender_user_cosine;
    private Recommender artistRecommender_user_pearson;

    private Recommender trackRecommender_user_jaccard;
    private Recommender trackRecommender_user_cosine;
    private Recommender trackRecommender_user_pearson;

    private Recommender artistRecommender_item_jaccard;
    private Recommender artistRecommender_item_cosine;
    private Recommender artistRecommender_item_pearson;

    private Recommender trackRecommender_item_jaccard;
    private Recommender trackRecommender_item_cosine;
    private Recommender trackRecommender_item_pearson;

    private DataRecommendationModels models  = DataRecommendationModels.instance();

    private RecommenderManager() throws IOException {
    }

    public static RecommenderManager instance() throws IOException, TasteException {
        if(instance == null) {
            instance = new RecommenderManager();
            instance.initArtistRecommenders();
            instance.initTrackRecommenders();
        }
        return instance;
    }

    private void initArtistRecommenders() throws IOException, TasteException {

        DataModel artistModel = models.getModel(DataRecommendationModels.artist_model);

        UserCosineRecommenderBuilder userCosineRecommenderBuilder = new UserCosineRecommenderBuilder();
        UserJaccardRecommenderBuilder userJaccardRecommenderBuilder = new UserJaccardRecommenderBuilder();
        UserPearsonRecommenderBuilder userPearsonRecommenderBuilder = new UserPearsonRecommenderBuilder();

        artistRecommender_user_cosine = userCosineRecommenderBuilder.buildRecommender(artistModel);
        artistRecommender_user_jaccard = userJaccardRecommenderBuilder.buildRecommender(artistModel);
        artistRecommender_user_pearson = userPearsonRecommenderBuilder.buildRecommender(artistModel);
        System.out.println("Loaded artist user-user recommenders");

        ItemCosineRecommenderBuilder itemCosineRecommenderBuilder = new ItemCosineRecommenderBuilder();
        ItemJaccardRecommenderBuilder itemJaccardRecommenderBuilder = new ItemJaccardRecommenderBuilder();
        ItemPearsonRecommenderBuilder itemPearsonRecommenderBuilder = new ItemPearsonRecommenderBuilder();

        artistRecommender_item_cosine = itemCosineRecommenderBuilder.buildRecommender(artistModel);
        artistRecommender_item_jaccard = itemJaccardRecommenderBuilder.buildRecommender(artistModel);
        artistRecommender_item_pearson = itemPearsonRecommenderBuilder.buildRecommender(artistModel);
        System.out.println("Loaded artist item-item recommenders");
    }

    private void initTrackRecommenders() throws IOException, TasteException {

        DataModel trackModel = models.getModel(DataRecommendationModels.track_model);

        UserCosineRecommenderBuilder userCosineRecommenderBuilder = new UserCosineRecommenderBuilder();
        UserJaccardRecommenderBuilder userJaccardRecommenderBuilder = new UserJaccardRecommenderBuilder();
        UserPearsonRecommenderBuilder userPearsonRecommenderBuilder = new UserPearsonRecommenderBuilder();

        trackRecommender_user_cosine = userCosineRecommenderBuilder.buildRecommender(trackModel);
        trackRecommender_user_jaccard = userJaccardRecommenderBuilder.buildRecommender(trackModel);
        trackRecommender_user_pearson = userPearsonRecommenderBuilder.buildRecommender(trackModel);
        System.out.println("Loaded track user-user recommenders");

        ItemCosineRecommenderBuilder itemCosineRecommenderBuilder = new ItemCosineRecommenderBuilder();
        ItemJaccardRecommenderBuilder itemJaccardRecommenderBuilder = new ItemJaccardRecommenderBuilder();
        ItemPearsonRecommenderBuilder itemPearsonRecommenderBuilder = new ItemPearsonRecommenderBuilder();

        trackRecommender_item_cosine = itemCosineRecommenderBuilder.buildRecommender(trackModel);
        trackRecommender_item_jaccard = itemJaccardRecommenderBuilder.buildRecommender(trackModel);
        trackRecommender_item_pearson = itemPearsonRecommenderBuilder.buildRecommender(trackModel);
        System.out.println("Loaded track item-item recommenders");
    }

    public List<Recommendation> recommend(String tipoRecomendador, String algoritmo, String user, String tipo_algoritmo, int results) throws TasteException {

        Recommender recommender;

        if(tipoRecomendador.equals(DataRecommendationModels.artist_model)){
            if(algoritmo.equals("jaccard")){
                if(tipo_algoritmo.equals("user-user"))
                    recommender = artistRecommender_user_jaccard;
                else
                    recommender = artistRecommender_item_jaccard;
            }
            else if(algoritmo.equals("cosine")){
                if(tipo_algoritmo.equals("user-user"))
                    recommender = artistRecommender_user_cosine;
                else
                    recommender = artistRecommender_item_cosine;

            }
            else{ //if(algoritmo.equals("pearson")){
                if(tipo_algoritmo.equals("user-user"))
                    recommender = artistRecommender_user_pearson;
                else
                    recommender = artistRecommender_item_pearson;
            }
        }
        else{
            if(algoritmo.equals("jaccard")){
                if(tipo_algoritmo.equals("user-user"))
                    recommender = trackRecommender_user_jaccard;
                else
                    recommender = trackRecommender_item_jaccard;
            }
            else if(algoritmo.equals("cosine")){
                if(tipo_algoritmo.equals("user-user"))
                    recommender = trackRecommender_user_cosine;
                else
                    recommender = trackRecommender_item_cosine;

            }
            else{ //if(algoritmo.equals("pearson")){
                if(tipo_algoritmo.equals("user-user"))
                    recommender = trackRecommender_user_pearson;
                else
                    recommender = trackRecommender_item_pearson;
            }

        }
        Long userId = models.getUser(user);
        List<RecommendedItem> recommendations = recommender.recommend(userId, results);

        ArrayList<Recommendation> recommendationResult = new ArrayList<>();

        for(RecommendedItem recommendation : recommendations){

            Long item = recommendation.getItemID();
            Float rating = recommendation.getValue();

            Recommendation recItem = new Recommendation();
            recItem.setRating(rating.doubleValue());

            if(tipoRecomendador.equals(DataRecommendationModels.artist_model)){
                String artist = models.getArtist(item);
                recItem.setItem(artist);

            }
            else{
                String track = models.getTrack(item);
                recItem.setItem(track);
            }

            recommendationResult.add(recItem);

        }
        return recommendationResult;
    }
}
