package hi.hbv601g.cocktailboi;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ListView;
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

    private void getRecipeDetails() {
        String name = getIntent().getExtras().getString("name");
        recipeDetails.addAll(getIntent().getExtras().getStringArrayList("ingredients"));
        recipeDetails.add(getIntent().getExtras().getString("glass"));
        recipeDetails.add(getIntent().getExtras().getString("howTo"));
        recipeDetails.add(getIntent().getExtras().getString("skill"));
        recipeDetails.addAll(getIntent().getExtras().getStringArrayList("spirits"));

        TextView cocktailNameView = (TextView) findViewById(R.id.cocktailName);
        cocktailNameView.setText(name);

        TextView recipeDetailsView = (TextView) findViewById(R.id.recipeDetails);
        recipeDetailsView.setText(TextUtils.join("\n", recipeDetails));
    }
}
