package hi.hbv601g.cocktailboi;

/**
 * Created by saevar43 on 25/02/2018.
 */

public class Recipe {

    private String name;
    private String[] ingredients;
    private String[] garnish;
    private String howTo;
    private String baseAlc;
    private String type;
    private String difficulty;
    private String strength;

    public Recipe() {
    }

    public Recipe(String name, String[] ingredients, String[] garnish, String howTo, String baseAlc,
                  String type, String difficulty, String strength) {
        this.name = name;
        this.ingredients = ingredients;
        this.garnish = garnish;
        this.howTo = howTo;
        this.baseAlc = baseAlc;
        this.type = type;
        this.difficulty = difficulty;
        this.strength = strength;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(String[] ingredients) {
        this.ingredients = ingredients;
    }

    public String[] getGarnish() {
        return garnish;
    }

    public void setGarnish(String[] garnish) {
        this.garnish = garnish;
    }

    public String getHowTo() {
        return howTo;
    }

    public void setHowTo(String howTo) {
        this.howTo = howTo;
    }

    public String getBaseAlc() {
        return baseAlc;
    }

    public void setBaseAlc(String baseAlc) {
        this.baseAlc = baseAlc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getStrength() {
        return strength;
    }

    public void setStrength(String strength) {
        this.strength = strength;
    }
}
