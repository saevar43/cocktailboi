package hi.hbv601g.cocktailboi;

import android.support.v4.app.Fragment;

/**
 * Created by saevar43 on 27/02/2018.
 */

public class RecipeListActivity extends SingleFragmentActivity{

    @Override
    protected Fragment createFragment() {
        return new RecipeListFragment();
    }

}
