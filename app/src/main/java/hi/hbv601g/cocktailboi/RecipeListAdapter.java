package hi.hbv601g.cocktailboi;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Recipe list adapter class. A custom adapter class that
 * contains methods that affect recipes in a list view.
 *
 * Created by saevar43 on 03/04/2018.
 */

public class RecipeListAdapter extends ArrayAdapter<Recipe> {

    private Context context;
    List<Recipe> recipes;
    SharedPreference sharedPreference;

    public RecipeListAdapter(Context context, List<Recipe> recipes) {
        super(context, R.layout.recipe_list_item, recipes);
        this.context = context;
        this.recipes = recipes;
        sharedPreference = new SharedPreference();
    }

    private class ViewHolder {
        TextView recipeNameTxt;
        ImageView favImg;
    }


    @Override
    public int getCount() {
        return recipes.size();
    }

    @Override
    public Recipe getItem(int position) {
        return recipes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.recipe_list_item, null);
            holder = new ViewHolder();
            holder.recipeNameTxt = (TextView) convertView.findViewById(R.id.name);
            holder.favImg = (ImageView) convertView.findViewById(R.id.fav_img);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Recipe recipe = (Recipe) getItem(position);
        holder.recipeNameTxt.setText(recipe.getName());

        // If recipe exists in Favorites then set a tag and a yellow star.
        if (checkFavoriteItem(recipe)) {
            holder.favImg.setImageResource(android.R.drawable.btn_star_big_on);
            holder.favImg.setTag("on");
        } else {
            holder.favImg.setImageResource(android.R.drawable.btn_star_big_off);
            holder.favImg.setTag("off");
        }

        return convertView;
    }

    /**
     * Checks whether a recipe exists in Favorites.
     * @param checkRecipe - The recipe to check if exists.
     * @return boolean value that is true if the recipe is in favorites.
     */
    private boolean checkFavoriteItem(Recipe checkRecipe) {
        boolean check = false;

        ArrayList<Recipe> favorites = sharedPreference.getFavorites(context);

        if (favorites != null) {
            for (Recipe recipe: favorites) {
                if (recipe.getName().equalsIgnoreCase(checkRecipe.getName())) {
                    check = true;
                    break;
                }
            }
        }
        return check;
    }

    @Override
    public void add(Recipe recipe) {
        super.add(recipe);
        recipes.add(recipe);
        notifyDataSetChanged();
    }

    @Override
    public void remove(Recipe recipe) {
        super.remove(recipe);
        recipes.remove(recipe);
        notifyDataSetChanged();
    }
}
