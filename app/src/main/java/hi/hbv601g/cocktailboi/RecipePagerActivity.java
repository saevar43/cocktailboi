package hi.hbv601g.cocktailboi;

import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

public class RecipePagerActivity extends AppCompatActivity {

    private static final String EXTRA_RECIPE_NAME = "hi.hbv601g.cocktailboi.recipe_name";

    private ViewPager mViewPager;
    private List<Recipe> mRecipes;

    public static Intent newIntent(Context packageContext, String recipeName) {
        Intent intent = new Intent(packageContext, RecipePagerActivity.class);
        intent.putExtra(EXTRA_RECIPE_NAME, recipeName);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_pager);

        String recipeName = (String) getIntent().getSerializableExtra(EXTRA_RECIPE_NAME);

        mViewPager = (ViewPager) findViewById(R.id.recipe_view_pager);

        mRecipes = RecipeBook.get(this).getRecipes();
        FragmentManager fm = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public Fragment getItem(int position) {
                Recipe recipe = mRecipes.get(position);
                return RecipeFragment.newInstance(recipe.getName());
            }

            @Override
            public int getCount() {
                return mRecipes.size();
            }
        });

        for (int i = 0; i < mRecipes.size(); i++) {
            if (mRecipes.get(i).getName().equals(recipeName)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }

    }
}
