package hi.hbv601g.cocktailboi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Favorite list activity class. Creates favorite list view
 * and contains methods used on favorite list.
 *
 * Created by saevar43.
 */

public class FavoriteListActivity extends AppCompatActivity {

    private String favoritesString;
    private ListView favoritesListView;
    private Intent intent;

    ArrayList<Recipe> favoritesList;
    RecipeListAdapter adapter;

    SharedPreference sharedPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        // Get intent
        intent = getIntent();
        favoritesString = intent.getExtras().getString("favorites");

        // INIT favorites list
        favoritesList = new ArrayList<>();

        // INIT ListView
        favoritesListView = findViewById(R.id.list);

        //INIT sharedPreference
        sharedPreference = new SharedPreference();

        Gson gson = new Gson();
        List<Recipe> favorites;

        Recipe[] favoriteItems = gson.fromJson(favoritesString, Recipe[].class);

        favorites = Arrays.asList(favoriteItems);
        favoritesList = new ArrayList<>(favorites);

        adapter = new RecipeListAdapter(this, favoritesList);

        favoritesListView.setAdapter(adapter);

        // Set item click listener that displays recipe details.
        favoritesListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?>adapter, View v, int position, long derp){
                Recipe item = (Recipe) adapter.getItemAtPosition(position);
                Intent clickIntent = new Intent(FavoriteListActivity.this, RecipeDetailsActivity.class);

                clickIntent.putExtra("imageUrl", "https://assets.absolutdrinks.com/drinks/%s.png");
                clickIntent.putExtra("cocktailId", item.getId());
                clickIntent.putExtra("name", item.getName());
                clickIntent.putExtra("ingredients", item.getIngredients());
                clickIntent.putExtra("glass", item.getGlass());
                clickIntent.putExtra("howTo", item.getHowTo());

                startActivity(clickIntent);
            }
        });

        // Set item long click listener that removes a recipe from Favorites.
        favoritesListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                ImageView button = (ImageView) view.findViewById(R.id.fav_img);

                sharedPreference.removeFavorite(FavoriteListActivity.this, favoritesList.get(i));
                Toast.makeText(FavoriteListActivity.this,
                        R.string.rem_fav_toast_txt, Toast.LENGTH_SHORT).show();
                button.setTag("off");

                adapter.remove(favoritesList.get(i));
                
                return true;
            }
        });
    }
}
