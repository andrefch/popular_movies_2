package br.com.andrefch.popularmoviesii.data.mapper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.andrefch.popularmoviesii.data.model.Review;

/**
 * Author: andrech
 * Date: 16/02/18
 */

public class ReviewMapper {

    private ReviewMapper() {
    }

    //region JSON
    private static Review convertJsonToReview(JSONObject json) {
        if (json == null) {
            return null;
        }

        final Review review = new Review();
        review.setReviewId(json.optString(JSONFields.REVIEW_ID));
        review.setAuthor(json.optString(JSONFields.AUTHOR));
        review.setContent(json.optString(JSONFields.CONTENT));
        review.setUrl(json.optString(JSONFields.URL));

        return review;
    }

    public static List<Review> convertJsonToReviewList(JSONArray json) {
        if (json == null) {
            return null;
        }

        final List<Review> reviews = new ArrayList<>();
        for (int index = 0; index < json.length(); index++) {
            final JSONObject jsonReview = json.optJSONObject(index);
            reviews.add(convertJsonToReview(jsonReview));
        }

        return reviews;
    }

    private static class JSONFields {
        private JSONFields() {
        }

        private static final String REVIEW_ID = "id";
        private static final String AUTHOR = "author";
        private static final String CONTENT = "content";
        private static final String URL = "url";
    }
    //endregion
}
