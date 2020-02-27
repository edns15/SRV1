package com.sr.evaluate.item;

import com.sr.data.DataRecommendationModels;
import com.sr.recommender.item.ItemCosineRecommenderBuilder;
import com.sr.recommender.item.ItemJaccardRecommenderBuilder;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.eval.RMSRecommenderEvaluator;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;

import java.io.IOException;
import java.util.List;

public class ItemCosineMain {

    public static void main(String[] args) throws IOException, TasteException {

        ItemCosineMain pearson = new ItemCosineMain();
        pearson.recommendAndEvaluate();
    }

    private void recommendAndEvaluate() throws IOException, TasteException {
        ItemCosineRecommenderBuilder recommenderBuilder = new ItemCosineRecommenderBuilder();

        System.out.println("Item Cosine");
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
