package hi.hbv601g.cocktailboi;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Shared preferences class. Contains all methods used to add/remove recipes from favorites.
 *
 * Created by saevar43 on 02/04/2018.
 */

public class SharedPreference {

    public static final String PREFS_NAME = "COCKTAILBOI_APP";
    public static final String FAVORITES = "Drink_Favorites";

    public SharedPreference() {
        super();
    }

    /**
     * A method that saves a list of favorite recipes as JSON onto a device.
     * @param context - The current context of the app.
     * @param favorites - The list to be saved to favorites.
     */
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

    /**
     * A method that adds a recipe to favorites.
     * @param context - The current context of the app.
     * @param recipe - The recipe to be added to favorites.
     */
    public void addFavorite(Context context, Recipe recipe) {
        ArrayList<Recipe> favorites = getFavorites(context);

        if (favorites == null) {
            favorites = new ArrayList<Recipe>();
        }

        favorites.add(recipe);
        saveFavorites(context, favorites);
    }

    /**
     * A method to remove a recipe from favorites.
     * @param context - The current context of the app.
     * @param recipeToRemove - The recipe to be removed.
     */
    public void removeFavorite(Context context, Recipe recipeToRemove) {
        ArrayList<Recipe> favorites = getFavorites(context);

        if (favorites != null) {
            for (int i = 0; i < favorites.size(); i++) {
                if (favorites.get(i).getName().equalsIgnoreCase(recipeToRemove.getName())) {
                    favorites.remove(favorites.get(i));
                }
            }
            saveFavorites(context, favorites);
        }
    }

    /**
     * A method that returns the recipes currently saved to favorites.
     * @param context - The current context of the app.
     * @return a list of recipes currently saved to favorites.
     */
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

    /**
     * A method that returns recipes currently saved to favorites as a JSON string.
     * @param context - The current context of the app.
     * @return a JSON string of recipes currently saved to favorites.
     */
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
