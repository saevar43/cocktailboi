package hi.hbv601g.cocktailboi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FavoriteListActivity extends AppCompatActivity {

    private String favoritesString;
    private ListView favoritesListView;

    ArrayList<Recipe> favoritesList;
    RecipeListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        // Get intent
        Intent intent = getIntent();
        favoritesString = intent.getExtras().getString("favorites");

        // INIT favorites list
        favoritesList = new ArrayList<>();

        // INIT ListView
        favoritesListView = findViewById(R.id.list);

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
                Intent intent = new Intent(FavoriteListActivity.this, RecipeDetailsActivity.class);

                intent.putExtra("imageUrl", "https://assets.absolutdrinks.com/drinks/%s.png");
                intent.putExtra("cocktailId", item.getId());
                // intent.putExtra("url", url);
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
}
