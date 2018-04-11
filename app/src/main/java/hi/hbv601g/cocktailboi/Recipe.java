package hi.hbv601g.cocktailboi;

import java.util.ArrayList;

/**
 * Recipe model class. Contains constructors, getters and setters
 * for Recipe model and its attributes.
 *
 * Created by saevar43 on 14/03/2018.
 */

public class Recipe {

    private String name;
    private String id;
    private ArrayList<String> ingredients;
    private String glass;
    private String howTo;

    public Recipe() {
    }

    public Recipe(String name, String id, ArrayList<String> ingredients,
                  String glass, String howTo) {
        this.name = name;
        this.id = id;
        this.ingredients = ingredients;
        this.glass = glass;
        this.howTo = howTo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }

    public String getGlass() {
        return glass;
    }

    public void setGlass(String glass) {
        this.glass = glass;
    }

    public String getHowTo() {
        return howTo;
    }

    public void setHowTo(String howTo) {
        this.howTo = howTo;
    }

    public String toString() {
        return name;
    }
}
