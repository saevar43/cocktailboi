package hi.hbv601g.cocktailboi;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeFragment extends Fragment {

    private static final String ARG_RECIPE_NAME = "recipe_name";

    private Recipe mRecipe;

    private TextView mRecipeTitle;
    private TextView mRecipeIngreds;
    private TextView mRecipeGarnish;
    private TextView mRecipeHowTo;
    private TextView mRecipeBase;
    private TextView mRecipeType;
    private TextView mRecipeDiff;
    private TextView mRecipeStrength;

    public static RecipeFragment newInstance(String recipeName) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_RECIPE_NAME, recipeName);

        RecipeFragment fragment = new RecipeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String recipeName = (String) getArguments().getSerializable(ARG_RECIPE_NAME);
        mRecipe = RecipeBook.get(getActivity()).getRecipe(recipeName);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_recipe, container, false);

        mRecipeTitle = v.findViewById(R.id.recipe_title);
        mRecipeIngreds = v.findViewById(R.id.recipe_ingreds);
        mRecipeGarnish = v.findViewById(R.id.recipe_garnish);
        mRecipeHowTo = v.findViewById(R.id.recipe_howto);
        mRecipeBase = v.findViewById(R.id.recipe_base);
        mRecipeType = v.findViewById(R.id.recipe_type);
        mRecipeDiff = v.findViewById(R.id.recipe_diff);
        mRecipeStrength = v.findViewById(R.id.recipe_strength);

        mRecipeTitle.setText(mRecipe.getName());
        mRecipeIngreds.setText(arrayToString(mRecipe.getIngredients()));
        mRecipeGarnish.setText(arrayToString(mRecipe.getGarnish()));
        mRecipeHowTo.setText(mRecipe.getHowTo());
        mRecipeBase.setText(mRecipe.getBaseAlc());
        mRecipeType.setText(mRecipe.getType());
        mRecipeDiff.setText(mRecipe.getDifficulty());
        mRecipeStrength.setText(mRecipe.getStrength());

        return v;
    }

    private String arrayToString(String[] array) {
        String s = "";

        for (int i = 0; i < array.length; i++) {
            s += array[i] + "\n";
        }
        return s;
    }
}
