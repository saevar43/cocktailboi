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

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?>adapter,View v, int position){
                Recipe item = (Recipe) adapter.getItemAtPosition(position);

                Intent intent = new Intent(RecipeListActivity.this, RecipeDetailsActivity.class);

                intent.putExtra("name", item.getName());
                intent.putExtra("ingredients", item.getIngredients());
                intent.putExtra("glass", item.getGlass());
                intent.putExtra("howTo", item.getHowTo());
                intent.putExtra("skill", item.getSkill());
                intent.putExtra("spirits", item.getSpirits());

                startActivity(intent);
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
