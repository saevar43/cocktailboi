package hi.hbv601g.cocktailboi;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Recipe model class. Contains constructors, getters and setters
 * for Recipe model and its attributes.
 *
 * Created by karigeir on 19/03/2018.
 */

public class RecipeDetailsActivity extends AppCompatActivity {

    //instance variables
    private ProgressDialog pDialog;
    private ListView lv;

    ArrayList<String> recipeDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        // INIT recipe list
        recipeDetails = new ArrayList<>();

        // Get images and display them
        getRecipeDetails();
        new GetCocktailImage().execute();
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

    private void getRecipeDetails() {
        // Getting the details to show from the API
        String name = getIntent().getExtras().getString("name");
        recipeDetails.addAll(getIntent().getExtras().getStringArrayList("ingredients"));
        String howTo = getIntent().getExtras().getString("howTo");

        TextView cocktailNameView = (TextView) findViewById(R.id.cocktailName);
        cocktailNameView.setText(name);

        TextView recipeDetailsView = (TextView) findViewById(R.id.recipeDetails);
        recipeDetailsView.setText(TextUtils.join("\n", recipeDetails));

        TextView recipeDetailsHowToView = (TextView) findViewById(R.id.recipeHowToDetails);
        recipeDetailsHowToView.setText(howTo);


    }

    private class GetCocktailImage extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Show progress dialog
            pDialog = new ProgressDialog(RecipeDetailsActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Making a request to URL and getting response
            String url = String.format(getIntent().getExtras().getString("imageUrl"), getIntent().getExtras().getString("cocktailId"));

            try {
                ImageView view = (ImageView)findViewById(R.id.cocktailImages);
                Bitmap bitmap = BitmapFactory.decodeStream((InputStream)new URL(url).getContent());
                setImage(view, bitmap);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
        }

        private void setImage(final ImageView view, final Bitmap bitmap){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    view.setImageBitmap(bitmap);
                }
            });
        }
    }

    
}
