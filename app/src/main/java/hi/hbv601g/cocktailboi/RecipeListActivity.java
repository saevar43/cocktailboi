package hi.hbv601g.cocktailboi;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Recipe list activity class. Creates recipe list view
 * and contains methods used on recipe list.
 *
 * Created by saevar43.
 */
public class RecipeListActivity extends AppCompatActivity {

    // Instance variables
    private String TAG = RecipeListActivity.class.getSimpleName();

    private ProgressDialog pDialog;

    private ListView recipeListView;

    private String url;

    ArrayList<Recipe> recipeList;

    SharedPreference sharedPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        sharedPreference = new SharedPreference();

        // Get intent
        Intent intent = getIntent();
        url = intent.getExtras().getString("url");


        // INIT recipe list.
        recipeList = new ArrayList<>();

        // Instantiate the list view for the recipe list.
        recipeListView = (ListView) findViewById(R.id.list);

        // Get data and display it.
        new GetRecipes().execute();

        // Set item click listener that displays recipe details.
        recipeListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?>adapter,View v, int position, long derp){
                Recipe item = (Recipe) adapter.getItemAtPosition(position);
                Intent intent = new Intent(RecipeListActivity.this, RecipeDetailsActivity.class);

                intent.putExtra("imageUrl", "https://assets.absolutdrinks.com/drinks/%s.png");
                intent.putExtra("cocktailId", item.getId());
                intent.putExtra("name", item.getName());
                intent.putExtra("ingredients", item.getIngredients());
                intent.putExtra("glass", item.getGlass());
                intent.putExtra("howTo", item.getHowTo());

                startActivity(intent);
            }
        });

        // Set item long click listener that adds the recipe to favorites.
        recipeListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                ImageView button = (ImageView) view.findViewById(R.id.fav_img);

                String tag = button.getTag().toString();

                if (tag.equalsIgnoreCase("off")) {
                    sharedPreference.addFavorite(RecipeListActivity.this, recipeList.get(i));
                    Toast.makeText(RecipeListActivity.this,
                            R.string.add_fav_toast_txt, Toast.LENGTH_SHORT).show();
                    button.setTag("on");
                    button.setImageResource(android.R.drawable.btn_star_big_on);
                } else {
                    sharedPreference.removeFavorite(RecipeListActivity.this, recipeList.get(i));
                    Toast.makeText(RecipeListActivity.this,
                            R.string.rem_fav_toast_txt, Toast.LENGTH_SHORT).show();
                    button.setTag("off");
                    button.setImageResource(android.R.drawable.btn_star_big_off);
                }
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        // Associate searchable config with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    /* ------------------------------------------------------------------------------------------ */

    /**
     * Async task class to get JSON by making HTTP call
     */
    private class GetRecipes extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Show progress dialog
            pDialog = new ProgressDialog(RecipeListActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler serviceHandler = new HttpHandler();

            // Making a request to URL and getting response
            String jsonStr = serviceHandler.makeServiceCall(url);

            Log.e(TAG, "Response from URL: " + url);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray recipes = jsonObj.getJSONArray("result");

                    // Loop through recipes
                    for (int i = 0; i < recipes.length(); i++) {
                        JSONObject r = recipes.getJSONObject(i);

                        // Recipe name
                        String name = r.getString("name");

                        // Recipe name
                        String id = r.getString("id");

                        // Recipe description
                        String howTo = r.getString("descriptionPlain");

                        // Recipe ingredients
                        JSONArray ingr = r.getJSONArray("ingredients");
                        ArrayList<String> ingredients = new ArrayList<>();

                        // Loop through ingredients and add them to ArrayList.
                        for (int j = 0; j < ingr.length(); j++) {
                            JSONObject ing = ingr.getJSONObject(j);

                            ingredients.add(new String(ing.getString("textPlain")));
                        }

                        // Glass drink is served in
                        JSONObject si = r.getJSONObject("servedIn");
                        String glass = si.getString("text");

                        // Create the recipe to add.
                        Recipe recipe = new Recipe();

                        // Add information to the recipe.
                        recipe.setName(name);
                        recipe.setId(id);
                        recipe.setIngredients(ingredients);
                        recipe.setGlass(glass);
                        recipe.setHowTo(howTo);

                        // Add recipe to recipe list
                        recipeList.add(recipe);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "JSON parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "JSON parsing error:" + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });
                }
            } else {
                Log.e(TAG, "Couldn't get JSON from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get JSON from server. Check LogCat for possible errors",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });
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
            RecipeListAdapter adapter = new RecipeListAdapter(RecipeListActivity.this, recipeList);
            recipeListView.setAdapter(adapter);
        }
    }
}
