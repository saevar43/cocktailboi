package hi.hbv601g.cocktailboi;

import java.util.ArrayList;

/**
 * Created by saevar43 on 25/02/2018.
 */

public class Recipe {

    private String name;
    private ArrayList<Ingredient> ingredients;
    private ArrayList<String> garnish;
    private String howTo;
    private ArrayList<String> spirits;

    public Recipe() {
    }

    public Recipe(String name, ArrayList<Ingredient> ingredients, ArrayList<String> garnish,
                  String howTo, ArrayList<String> spirits) {
        this.name = name;
        this.ingredients = ingredients;
        this.garnish = garnish;
        this.howTo = howTo;
        this.spirits = spirits;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public ArrayList<String> getGarnish() {
        return garnish;
    }

    public void setGarnish(ArrayList<String> garnish) {
        this.garnish = garnish;
    }

    public String getHowTo() {
        return howTo;
    }

    public void setHowTo(String howTo) {
        this.howTo = howTo;
    }

    public ArrayList<String> getSpirits() {
        return spirits;
    }

    public void setSpirits(ArrayList<String> spirits) {
        this.spirits = spirits;
    }

    public String toString() {
        return name;
    }
}