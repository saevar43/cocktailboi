package hi.hbv601g.cocktailboi;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by saevar43 on 02/04/2018.
 */

public class SharedPreference {

    public static final String PREFS_NAME = "COCKTAILBOI_APP";
    public static final String FAVORITES = "Drink_Favorites";

    public SharedPreference() {
        super();
    }

    // Save a list of favorite recipes as JSON onto device.
    public void saveFavorites(Context context, List<Recipe> favorites) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();

        Gson gson = new Gson();

        String jsonFavorites = gson.toJson(favorites);

        editor.putString(FAVORITES, jsonFavorites);

        editor.apply();
    }

    // Add a recipe to favorites.
    public void addFavorite(Context context, Recipe recipe) {
        ArrayList<Recipe> favorites = getFavorites(context);

        if (favorites == null) {
            favorites = new ArrayList<Recipe>();
        }

        favorites.add(recipe);
        saveFavorites(context, favorites);
    }

    // Remove a recipe from favorites.
    public void removeFavorite(Context context, Recipe recipe) {
        ArrayList<Recipe> favorites = getFavorites(context);

        if (favorites != null) {
            favorites.remove(recipe);
            saveFavorites(context, favorites);
        }
    }

    // Get a list of favorite recipes.
    public ArrayList<Recipe> getFavorites(Context context) {
        SharedPreferences settings;
        List<Recipe> favorites;

        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        if (settings.contains(FAVORITES)) {
            String jsonFavorites = settings.getString(FAVORITES, null);
            Gson gson = new Gson();

            Recipe[] favoriteItems = gson.fromJson(jsonFavorites, Recipe[].class);

            favorites = Arrays.asList(favoriteItems);
            favorites = new ArrayList<Recipe>(favorites);
        } else {
            return null;
        }

        return (ArrayList<Recipe>) favorites;
    }

    public String getFavoritesAsJson(Context context) {
        SharedPreferences settings;

        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        if (settings.contains(FAVORITES)) {
            return settings.getString(FAVORITES, null);
        } else {
            return null;
        }
    }
}
