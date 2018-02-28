package br.com.andrefch.popularmoviesii.data.mapper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.andrefch.popularmoviesii.data.model.Video;

/**
 * Author: andrech
 * Date: 16/02/18
 */

public class VideoMapper {

    private VideoMapper() {
    }

    //region JSON
    private static Video convertJsonToVideo(JSONObject json) {
        if (json == null) {
            return null;
        }

        final Video video = new Video();
        video.setVideoId(json.optString(JSONFields.VIDEO_ID));
        video.setKey(json.optString(JSONFields.KEY));
        video.setName(json.optString(JSONFields.NAME));
        video.setSite(json.optString(JSONFields.SITE));
        video.setSize(json.optInt(JSONFields.SIZE, 0));
        video.setType(json.optString(JSONFields.TYPE));

        return video;
    }

    public static List<Video> convertJsonToVideoList(JSONArray json) {
        if (json == null) {
            return null;
        }

        final List<Video> videos = new ArrayList<>();
        for (int index = 0; index < json.length(); index++) {
            final JSONObject jsonVideo = json.optJSONObject(index);
            videos.add(convertJsonToVideo(jsonVideo));
        }

        return videos;
    }

    private static class JSONFields {
        private JSONFields() {
        }

        private static final String VIDEO_ID = "id";
        private static final String KEY = "key";
        private static final String NAME = "name";
        private static final String SITE = "site";
        private static final String SIZE = "size";
        private static final String TYPE = "type";
    }
    //endregion
}
