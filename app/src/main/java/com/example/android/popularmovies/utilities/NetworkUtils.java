package com.example.android.popularmovies.utilities;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by fifiv on 06/02/2018.
 */

public final class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();


    private static final String IMAGES_BASE_URL = "https://image.tmdb.org/t/p/";

    private static final String FILE_SIZE = "w342";

    private static final String FILE_SIZE_BIGGER = "w500";

    private static final String MOVIES_BASE_URL = "https://api.themoviedb.org/3";

    private static final String MOVIE_PATH = "movie";

    private static final String API_KEY_PARAM = "api_key";


    /**
     * Retrieves the proper URL to query for the movie data.
     *
     * @param apiKey    uses the API KEY for fetching data
     * @param sortOrder used to fetch different content according to user's preferences
     * @return URL to query movie data
     */
    public static URL buildUrl(String apiKey, String sortOrder) {

        Uri movieQueryUri = Uri.parse(MOVIES_BASE_URL).buildUpon()
                .appendPath(MOVIE_PATH)
                .appendPath(sortOrder)
                .appendQueryParameter(API_KEY_PARAM, apiKey)
                .build();

        URL movieQueryUrl;
        try {
            movieQueryUrl = new URL(movieQueryUri.toString());

            Log.v(TAG, "Movie Query Url: " + movieQueryUrl);
            return movieQueryUrl;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String buildPosterPathUrl(String posterPath) {

        String posterPathUrlString = IMAGES_BASE_URL + FILE_SIZE + posterPath;
        Log.v(TAG, "Built URI for Poster: " + posterPathUrlString);

        return posterPathUrlString;
    }

    public static String buildPosterBackdropUrl(String backdropPath) {
        String backdropUrlString = IMAGES_BASE_URL + FILE_SIZE_BIGGER + backdropPath;
        Log.v(TAG, "Built URI for Backdrop: " + backdropUrlString);

        return backdropUrlString;
    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url the URL to fetch the HTTP response from.
     * @return The contents of the HTTP response, null if no response
     * @throws IOException Related to network ad stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            String response = null;

            if (hasInput) {
                response = scanner.next();
            }
            scanner.close();
            return response;

        } finally {
            urlConnection.disconnect();
        }
    }
}

