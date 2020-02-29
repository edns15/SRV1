package com.sr.taller1.evaluate.user;

import com.sr.taller1.data.DataRecommendationModels;
import com.sr.taller1.recommender.user.UserJaccardRecommenderBuilder;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.eval.RMSRecommenderEvaluator;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;

import java.io.IOException;
import java.util.List;

public class UserJaccardMain {

    public static void main(String[] args) throws IOException, TasteException {

        UserJaccardMain pearson = new UserJaccardMain();
        pearson.recommendAndEvaluate();
    }

    private void recommendAndEvaluate() throws IOException, TasteException {

        System.out.println("User Jaccard");

        UserJaccardRecommenderBuilder recommenderBuilder = new UserJaccardRecommenderBuilder();
        DataModel artistModel = DataRecommendationModels.instace().getModel(DataRecommendationModels.artist_model);
        Recommender artistRecommender = recommenderBuilder.buildRecommender(artistModel);
        List<RecommendedItem> recommendations = artistRecommender.recommend(2, 3);
        for (RecommendedItem recommendation : recommendations) { System.out.println(recommendation);
        }

        DataRecommendationModels.instace().addRating(DataRecommendationModels.artist_model,Long.parseLong("992"),Long.parseLong("1"),Long.parseLong("4"));
        DataRecommendationModels.instace().addRating(DataRecommendationModels.artist_model,Long.parseLong("992"),Long.parseLong("2"),Long.parseLong("4"));
        DataRecommendationModels.instace().addRating(DataRecommendationModels.artist_model,Long.parseLong("992"),Long.parseLong("3"),Long.parseLong("4"));
        DataRecommendationModels.instace().addRating(DataRecommendationModels.artist_model,Long.parseLong("992"),Long.parseLong("4"),Long.parseLong("4"));
        DataRecommendationModels.instace().addRating(DataRecommendationModels.artist_model,Long.parseLong("992"),Long.parseLong("5"),Long.parseLong("4"));
        DataRecommendationModels.instace().addRating(DataRecommendationModels.artist_model,Long.parseLong("992"),Long.parseLong("6"),Long.parseLong("4"));
        DataRecommendationModels.instace().addRating(DataRecommendationModels.artist_model,Long.parseLong("992"),Long.parseLong("7"),Long.parseLong("4"));
        DataRecommendationModels.instace().addRating(DataRecommendationModels.artist_model,Long.parseLong("992"),Long.parseLong("8"),Long.parseLong("4"));
        DataRecommendationModels.instace().addRating(DataRecommendationModels.artist_model,Long.parseLong("992"),Long.parseLong("9"),Long.parseLong("4"));
        DataRecommendationModels.instace().addRating(DataRecommendationModels.artist_model,Long.parseLong("992"),Long.parseLong("10"),Long.parseLong("4"));
        DataRecommendationModels.instace().addRating(DataRecommendationModels.artist_model,Long.parseLong("992"),Long.parseLong("11"),Long.parseLong("4"));
        DataRecommendationModels.instace().addRating(DataRecommendationModels.artist_model,Long.parseLong("992"),Long.parseLong("12"),Long.parseLong("4"));


        DataRecommendationModels.instace().buildModel(DataRecommendationModels.artist_model);

        System.out.println("Retraining jaccard");

        recommenderBuilder = new UserJaccardRecommenderBuilder();
        artistModel = DataRecommendationModels.instace().getModel(DataRecommendationModels.artist_model);
        artistRecommender = recommenderBuilder.buildRecommender(artistModel);
        recommendations = artistRecommender.recommend(992, 3);
        for (RecommendedItem recommendation : recommendations) { System.out.println(recommendation);
        }

        System.out.println("Evaluation");
        RecommenderEvaluator evaluator = new RMSRecommenderEvaluator();
        double result = evaluator.evaluate(recommenderBuilder, null, artistModel, 0.9, 1.0);
        System.out.println(result);

    }
}
