package hi.hbv601g.cocktailboi;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;

public class RecipeDetailsActivity extends AppCompatActivity {
    private ProgressDialog pDialog;
    private ListView lv;

    ArrayList<String> recipeDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        recipeDetails = new ArrayList<>();

        getRecipeDetails();
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
        String name = getIntent().getExtras().getString("name");
        recipeDetails.addAll(getIntent().getExtras().getStringArrayList("ingredients"));
//        recipeDetails.add(getIntent().getExtras().getString("glass"));
        recipeDetails.add(getIntent().getExtras().getString("howTo"));
        recipeDetails.add(getIntent().getExtras().getString("skill"));
        recipeDetails.addAll(getIntent().getExtras().getStringArrayList("spirits"));

        TextView cocktailNameView = (TextView) findViewById(R.id.cocktailName);
        cocktailNameView.setText(name);

        TextView recipeDetailsView = (TextView) findViewById(R.id.recipeDetails);
        recipeDetailsView.setText(TextUtils.join("\n", recipeDetails));
        /*ImageView recipeImageView = (ImageView) findViewById(R.id.cocktail_image);
        recipeImageView.setImageIcon(image);*/
    }
}
