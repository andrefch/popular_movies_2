package br.com.andrefch.popularmoviesii.ui.listmovie;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

import br.com.andrefch.popularmoviesii.R;

/**
 * Author: andrech
 * Date: 07/02/18
 */

class ListMovieSortAdapter extends ArrayAdapter<String> {

    private final String[] mPaths;

    ListMovieSortAdapter(@NonNull Context context) {
        super(context,
                R.layout.item_filter_list_movie,
                android.R.id.text1,
                context.getResources().getStringArray(R.array.list_movie_sort_titles));

        setDropDownViewResource(android.R.layout.simple_list_item_1);

        mPaths = context.getResources().getStringArray(R.array.list_movie_sort_paths);
    }

    public String getPath(int position) {
        if ((position < 0) || (position >= mPaths.length)) {
            return null;
        }
        return mPaths[position];
    }
}
