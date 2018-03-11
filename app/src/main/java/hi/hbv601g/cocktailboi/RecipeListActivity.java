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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RecipeListActivity extends AppCompatActivity {

    private String TAG = RecipeListActivity.class.getSimpleName();

    private ProgressDialog pDialog;
    private ListView lv;

    private String url;

    ArrayList<Recipe> recipeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        // Get intent
        Intent intent = getIntent();
        url = intent.getExtras().getString("url");

        recipeList = new ArrayList<>();

        lv = (ListView) findViewById(R.id.list);

        new GetRecipes().execute();
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

    /**
     * Setter for URL to parse.
     * @param url
     */
    public void setUrl(String url) {
        url = url;
    }

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
            HttpHandler sh = new HttpHandler();

            // Making a request to URL and getting response
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from URL: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray recipes = jsonObj.getJSONArray("recipes");

                    // Loop through contacts
                    for (int i = 0; i < recipes.length(); i++) {
                        JSONObject r = recipes.getJSONObject(i);

                        // recipe name
                        String name = r.getString("name");

                        // ingredients
                        JSONArray ingr = r.getJSONArray("ingredients");
                        ArrayList<Ingredient> ingredients = new ArrayList<>();

                        for (int j = 0; j <  ingr.length(); j++) {
                            JSONObject ing = ingr.getJSONObject(j);
                            Ingredient ingToAdd = new Ingredient();

                            ingToAdd.setName(ing.getString("name"));
                            if (ing.has("units")) {
                                ingToAdd.setUnits(ing.getString("units"));
                            }
                            ingredients.add(ingToAdd);
                        }

                        // garnish
                        ArrayList<String> garnish = new ArrayList<String>();
                        if (r.has("garnish")) {
                            JSONArray garn = r.getJSONArray("garnish");

                            for (int j = 0; j < garn.length(); j++) {
                                JSONObject gar = garn.getJSONObject(j);

                                garnish.add(gar.getString("name"));
                            }
                        }

                        // howTo
                        JSONArray how = r.getJSONArray("howto");
                        String howTo = "";

                        for (int j = 0; j < how.length(); j++) {
                            JSONObject h = how.getJSONObject(j);

                            howTo += h.getString("text") + "\n";
                        }

                        // spirits
                        JSONArray spir = r.getJSONArray("spirits");
                        ArrayList<String> spirits = new ArrayList<String>();

                        for (int j = 0; j < spir.length(); j++) {
                            JSONObject spi = spir.getJSONObject(j);

                            spirits.add(spi.getString("name"));
                        }

                        // Create the recipe to add.
                        Recipe recipe = new Recipe();

                        // Add information to the recipe.
                        recipe.setName(name);
                        recipe.setIngredients(ingredients);
                        if (!garnish.isEmpty()) {
                            recipe.setGarnish(garnish);
                        }
                        recipe.setHowTo(howTo);
                        recipe.setSpirits(spirits);

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

            ArrayAdapter<Recipe> adapter = new ArrayAdapter<>(RecipeListActivity.this,
                    R.layout.recipe_list_item, R.id.name, recipeList);

            lv.setAdapter(adapter);

        }
    }
}
