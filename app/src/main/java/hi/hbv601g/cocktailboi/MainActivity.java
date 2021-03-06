package hi.hbv601g.cocktailboi;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.SearchView;
import android.widget.Button;
import android.widget.Toast;

/**
 * Main activity class. Creates front page view and contains methods used on the front page.
 *
 * Created by saevar43.
 */

public class MainActivity extends AppCompatActivity {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.filter_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCheckboxFilter();
            }
        });
    }

    public void openCheckboxFilter() {
        Intent intent = new Intent(this, CheckboxFilter.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        // Associate searchable config with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(new ComponentName(this, SearchActivity.class)));
        searchView.setIconifiedByDefault(true);

        return true;
    }

    /**
     * Method that creates an intent to be passed on to RecipeListActivity.
     * Used to get cocktail recipes with a specific alcohol type.
     * @param view
     */
    public void showList(View view) {
        Intent intent = new Intent(this, RecipeListActivity.class);

        Button b = (Button)view;
        String type = b.getTag().toString();
        intent.putExtra("url", "https://addb.absolutdrinks.com/drinks/withtype/" + type + "/?apiKey=8e5143045cc94b4e8801cf09e0c135af&pageSize=1100");

        startActivity(intent);
    }


    /**
     * Method that creates an intent to be passed on to FavoriteListActivity.
     * Used to get cocktail recipes that have been added to favorites.
     * @param view
     */
    public void showFavorites(View view) {
        SharedPreference sharedPreference = new SharedPreference();

        Log.e("favs", sharedPreference.getFavoritesAsJson(this));

        if (sharedPreference.getFavoritesAsJson(this).isEmpty()
                || sharedPreference.getFavoritesAsJson(this).equalsIgnoreCase("[]")
                || sharedPreference.getFavoritesAsJson(this).equalsIgnoreCase("")) {
            Toast.makeText(this,
                    R.string.no_fav_toast_txt, Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(this, FavoriteListActivity.class);

            String jsonFavorites = sharedPreference.getFavoritesAsJson(this);

            intent.putExtra("favorites", jsonFavorites);

            startActivity(intent);
        }
    }
}
