package hi.hbv601g.cocktailboi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

public class CheckboxFilter extends AppCompatActivity {

    ArrayList<String> selection = new ArrayList<String>();
    String final_ingredient_selection = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkbox_filter);

        Intent intent = getIntent();
    }

    @Override
    protected void onNewIntent(Intent newIntent) {
        setIntent(newIntent);
    }

    public void selectItem(View view) {

        boolean checked = ((CheckBox) view).isChecked();
        switch (view.getId()) {

            case R.id.vodka_checkBox:
                if(checked) {
                    selection.add("absolut-vodka");
                }
                else {
                    selection.remove("absolut-vodka");
                }
                break;

            case R.id.gin_checkBox:
                if(checked) {
                    selection.add("gin");
                }
                else {
                    selection.remove("gin");
                }
                break;

            case R.id.lime_checkBox:
                if(checked) {
                    selection.add("lime");
                }
                else {
                    selection.remove("lime");
                }
                break;

            case R.id.egg_white_checkbox:
                if(checked) {
                    selection.add("egg-white");
                }
                else {
                    selection.remove("egg-white");
                }
                break;

            case R.id.orangejuice_checkbox:
                if(checked) {
                    selection.add("orange-juice");
                }
                else {
                    selection.remove("orange-juice");
                }
                break;

            case R.id.lemon_juice_checkbox:
                if(checked) {
                    selection.add("lemon-juice");
                }
                else {
                    selection.remove("lemon-juice");
                }
                break;
        }


    }

    public String finalSelection(View view) {

        int count = 1;
        String and = "/and/";

        for(String Selections : selection) {
            if (count == selection.size()) {
                and = "";
            }
            final_ingredient_selection = final_ingredient_selection + Selections + and;
            count++;
        }
        return final_ingredient_selection;

    }

    public void searchIngredients (View intent) {
        finalSelection(intent);

        String query = final_ingredient_selection;
        Intent resultsIntent = new Intent(this, RecipeListActivity.class);
        resultsIntent.putExtra("url", "https://addb.absolutdrinks.com/drinks/with/" + query + "/?apiKey=8e5143045cc94b4e8801cf09e0c135af&pageSize=4000");

        startActivity(resultsIntent);

    }



}
