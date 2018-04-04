package hi.hbv601g.cocktailboi;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RecipeListFragment extends Fragment implements
        AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    public static final String ARG_ITEM_ID = "recipe_list";

    private String TAG = RecipeListActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private String url;

    Activity activity;
    ListView recipeListView;
    ArrayList<Recipe> recipes;
    RecipeListAdapter recipeListAdapter;

    SharedPreference sharedPreference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_list, container, false);
        findViewsById(view);

        // Get intent
        Intent intent = getActivity().getIntent();
        url = intent.getExtras().getString("url");

        recipeListAdapter = new RecipeListAdapter(activity, recipes);
        recipeListView.setAdapter(recipeListAdapter);
        recipeListView.setOnItemClickListener(this);
        recipeListView.setOnItemLongClickListener(this);

        return view;
    }

    private void findViewsById(View view) {
        recipeListView = view.findViewById(R.id.list);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Recipe item = (Recipe) parent.getItemAtPosition(position);
        Intent intent = new Intent(activity, RecipeDetailsActivity.class);

        intent.putExtra("url", url);
        intent.putExtra("name", item.getName());
        intent.putExtra("ingredients", item.getIngredients());
        intent.putExtra("glass", item.getGlass());
        intent.putExtra("howTo", item.getHowTo());
        intent.putExtra("skill", item.getSkill());
        intent.putExtra("spirits", item.getSpirits());

        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> arg0, View view, int position, long arg3) {
        ImageView button = (ImageView) view.findViewById(R.id.fav_img);

        String tag = button.getTag().toString();

        if (tag.equalsIgnoreCase("off")) {
            sharedPreference.addFavorite(activity, recipes.get(position));
            Toast.makeText(activity,
                    "Added recipe to Favorites", Toast.LENGTH_SHORT).show();
            button.setTag("on");
            button.setImageResource(android.R.drawable.btn_star_big_on);
        } else {
            sharedPreference.removeFavorite(activity, recipes.get(position));
            Toast.makeText(activity,
                    "Removed recipe from Favorites", Toast.LENGTH_SHORT).show();
            button.setTag("off");
            button.setImageResource(android.R.drawable.btn_star_big_off);
        }
        return true;
    }

    @Override
    public void onResume() {
        getActivity().setTitle(R.string.app_name);
        getActivity().getActionBar().setTitle(R.string.app_name);
        super.onResume();
    }

    /**
     * Async task class to get JSON by making HTTP call
     */
    private class GetRecipes extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Show progress dialog
            pDialog = new ProgressDialog(activity);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to URL and getting response
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from URL: " + url);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray jsonRecipes = jsonObj.getJSONArray("result");

                    // Loop through recipes
                    for (int i = 0; i < jsonRecipes.length(); i++) {
                        JSONObject r = jsonRecipes.getJSONObject(i);

                        // Recipe name
                        String name = r.getString("name");

                        // Recipe description
                        String howTo = r.getString("descriptionPlain");

                        // Recipe rating
                        String rating = r.getString("rating");

                        // Recipe garnish, ingredients and spirits
                        JSONArray ingr = r.getJSONArray("ingredients");
                        ArrayList<String> ingredients = new ArrayList<>();
                        ArrayList<String> spirits = new ArrayList<>();

                        for (int j = 0; j < ingr.length(); j++) {
                            JSONObject ing = ingr.getJSONObject(j);

                            if (ing.getString("type").equals("vodka")) {
                                spirits.add("Vodka");
                            }
                            if (ing.getString("type").equals("rum")) {
                                spirits.add("Rum");
                            }
                            if (ing.getString("type").equals("whisky")) {
                                spirits.add("Whisky");
                            }
                            if (ing.getString("type").equals("brandy")) {
                                spirits.add("Brandy");
                            }
                            if (ing.getString("type").equals("tequila")) {
                                spirits.add("Tequila");
                            }
                            if (ing.getString("type").equals("gin")) {
                                spirits.add("Gin");
                            }

                            ingredients.add(new String(ing.getString("textPlain")));
                        }

                        // Recipe difficulty
                        JSONObject sk = r.getJSONObject("skill");
                        String skill = sk.getString("name");

                        // Glass drink is served in
                        JSONObject si = r.getJSONObject("servedIn");
                        String glass = si.getString("text");


                        // Create the recipe to add.
                        Recipe recipe = new Recipe();

                        // Add information to the recipe.
                        recipe.setName(name);
                        recipe.setIngredients(ingredients);
                        recipe.setGlass(glass);
                        recipe.setHowTo(howTo);
                        recipe.setSkill(skill);
                        recipe.setSpirits(spirits);

                        // Add recipe to recipe list
                        recipes.add(recipe);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "JSON parsing error: " + e.getMessage());
                }
            } else {
                Log.e(TAG, "Couldn't get JSON from server.");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();

            /**
             * Updating parsed JSON data into ListView
             */
            RecipeListAdapter adapter = new RecipeListAdapter(activity, recipes);
            recipeListView.setAdapter(adapter);
        }
    }
}