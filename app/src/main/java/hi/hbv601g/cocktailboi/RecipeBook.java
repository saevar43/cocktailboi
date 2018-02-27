package hi.hbv601g.cocktailboi;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Factory class for recipes. Contains multiple recipes.
 * Created by saevar43 on 25/02/2018.
 */

public class RecipeBook {

    private static RecipeBook sRecipeBook;

    private List<Recipe> mRecipes;

    public static RecipeBook get(Context context) {
        if (sRecipeBook == null) {
            sRecipeBook = new RecipeBook(context);
        }
        return sRecipeBook;
    }

    public RecipeBook(Context context) {
        mRecipes = new ArrayList<Recipe>();
        // 2 dummy gögn
        Recipe gypsyQueen = new Recipe();
        gypsyQueen.setName("Gypsy Queen");
        gypsyQueen.setIngredients(new String[] {"60 ml Vodka", "30 ml Bénédictine", "2 dashes Angostura Bitters"});
        gypsyQueen.setGarnish(new String[] {"1 thin-cut lemon peel"});
        gypsyQueen.setHowTo("Add all the ingredients to a mixing glass and fill with cracked ice. \n" +
                "Stir, and strain into a chilled cocktail glass. \n" +
                "Twist a swatch of lemon peel over the top and discard.");
        gypsyQueen.setBaseAlc("Vodka");
        gypsyQueen.setType("Modern Classics");
        gypsyQueen.setDifficulty("Medium");
        gypsyQueen.setStrength("Medium");

        mRecipes.add(gypsyQueen);

        Recipe moscowMule = new Recipe();
        moscowMule.setName("Organic Moscow Mule");
        moscowMule.setIngredients(new String[] {"60 ml Vodka", "90 ml Ginger Beer", "Juice of half a lime"});
        moscowMule.setGarnish(new String[] {"Lime wheel"});
        moscowMule.setHowTo("Add all of the ingredients to a copper mug or highball glass filled" +
                "with ice, and garnish with a lime wheel.");
        moscowMule.setBaseAlc("Vodka");
        moscowMule.setType("Classics");
        moscowMule.setDifficulty("Simple");
        moscowMule.setStrength("Medium");

        mRecipes.add(moscowMule);
    }

    public List<Recipe> getRecipes() {
        return mRecipes;
    }
}
