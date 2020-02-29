package com.sr.taller1.evaluate.item;

import com.sr.taller1.data.DataRecommendationModels;
import com.sr.taller1.recommender.user.UserPearsonRecommenderBuilder;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.eval.RMSRecommenderEvaluator;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;

import java.io.IOException;
import java.util.List;

public class ItemPearsonMain {

    public static void main(String[] args) throws IOException, TasteException {

        ItemPearsonMain pearson = new ItemPearsonMain();
        pearson.recommendAndEvaluate();
    }

    private void recommendAndEvaluate() throws IOException, TasteException {
        UserPearsonRecommenderBuilder recommenderBuilder = new UserPearsonRecommenderBuilder();

        System.out.println("Item Pearson");
        DataModel artistModel = DataRecommendationModels.instace().getModel(DataRecommendationModels.artist_model);
        Recommender artistRecommender = recommenderBuilder.buildRecommender(artistModel);
        List<RecommendedItem> recommendations = artistRecommender.recommend(2, 3);
        for (RecommendedItem recommendation : recommendations) { System.out.println(recommendation);
        }
        System.out.println("Evaluation");

        RecommenderEvaluator evaluator = new RMSRecommenderEvaluator();
        double result = evaluator.evaluate(recommenderBuilder, null, artistModel, 0.9, 1.0);
        System.out.println(result);
    }
}
