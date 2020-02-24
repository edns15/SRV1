package com.sr.evaluate;

import com.sr.data.DataRecommendationModels;
import com.sr.recommender.user.UserPearsonRecommenderBuilder;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.eval.AverageAbsoluteDifferenceRecommenderEvaluator;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;

import java.io.IOException;
import java.util.List;

public class PearsonMain {

    public static void main(String[] args) throws IOException, TasteException {

        PearsonMain pearson = new PearsonMain();
        pearson.recommendAndEvaluate();
    }

    private void recommendAndEvaluate() throws IOException, TasteException {
        UserPearsonRecommenderBuilder pearson = new UserPearsonRecommenderBuilder();

        DataModel artistModel = DataRecommendationModels.instace().getModel(DataRecommendationModels.artist_model);
        Recommender artistRecommender = pearson.buildRecommender(artistModel);
        List<RecommendedItem> recommendations = artistRecommender.recommend(2, 3);
        for (RecommendedItem recommendation : recommendations) { System.out.println(recommendation);
        }

        DataModel trackModel = DataRecommendationModels.instace().getModel(DataRecommendationModels.track_model);
        Recommender trackRecommender = pearson.buildRecommender(trackModel);
        recommendations = trackRecommender.recommend(2, 3);
        for (RecommendedItem recommendation : recommendations) { System.out.println(recommendation);
        }

        RecommenderEvaluator evaluator = new AverageAbsoluteDifferenceRecommenderEvaluator();
        double result = evaluator.evaluate(pearson, null, artistModel, 0.9, 1.0);
        System.out.println(result);

        result = evaluator.evaluate(pearson, null, trackModel, 0.9, 1.0);
        System.out.println(result);
    }
}
