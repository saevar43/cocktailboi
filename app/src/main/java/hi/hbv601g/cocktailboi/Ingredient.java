package hi.hbv601g.cocktailboi;

/**
 * Created by saevar43 on 11/03/2018.
 */

public class Ingredient {

    private String name;
    private String units;

    public Ingredient() {

    }

    public Ingredient(String name, String units) {
        this.name = name;
        this.units = units;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }
}

