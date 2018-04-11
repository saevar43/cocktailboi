package hi.hbv601g.cocktailboi;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.SearchView;
import android.util.Log;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "hi.hbv601g.cocktailboi.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

    public void showList(View view) {
        Intent intent = new Intent(this, RecipeListActivity.class);

        Button b = (Button)view;
        String type = b.getText().toString();
        intent.putExtra("url", "https://addb.absolutdrinks.com/drinks/withtype/" + type + "/?apiKey=8e5143045cc94b4e8801cf09e0c135af&pageSize=4000");

        startActivity(intent);
    }


    public void showFavorites(View view) {
        SharedPreference sharedPreference = new SharedPreference();
        Intent intent = new Intent(this, FavoriteListActivity.class);

        String jsonFavorites = sharedPreference.getFavoritesAsJson(this);

        intent.putExtra("favorites", jsonFavorites);

        startActivity(intent);
    }
}
