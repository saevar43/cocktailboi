package hi.hbv601g.cocktailboi;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Recipe list adapter class. A custom adapter class that
 * contains methods that affect recipes in a list view.
 *
 * Created by suzanna on 10/04/2018.
 */

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get intent
        Intent intent = getIntent();
        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent newIntent) {
        setIntent(newIntent);
        handleIntent(newIntent);
    }

    private void handleIntent(Intent intent) {
        // Using the search function provided in the API to our app
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Intent resultsIntent = new Intent(this, RecipeListActivity.class);
            resultsIntent.putExtra("url", "https://addb.absolutdrinks.com/quickSearch/drinks/" + query + "/?apiKey=8e5143045cc94b4e8801cf09e0c135af&pageSize=4000");

            startActivity(resultsIntent);

        }

    }
}
