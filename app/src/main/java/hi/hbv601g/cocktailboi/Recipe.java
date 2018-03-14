package hi.hbv601g.cocktailboi;

import java.util.ArrayList;

/**
 * Created by saevar43 on 14/03/2018.
 */

public class Recipe {

    private String name;
    private ArrayList<String> ingredients;
    private String glass;
    private String howTo;
    private String skill;
    private ArrayList<String> spirits;

    public Recipe() {
    }

    public Recipe(String name, ArrayList<String> ingredients, String glass, String howTo,
                  String skill, ArrayList<String> spirits) {
        this.name = name;
        this.ingredients = ingredients;
        this.glass = glass;
        this.howTo = howTo;
        this.skill = skill;
        this.spirits = spirits;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
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
