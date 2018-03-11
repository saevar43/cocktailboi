package hi.hbv601g.cocktailboi;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends SingleFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        FragmentManager fm = getSupportFragmentManager();
//
//        Fragment fragment = fm.findFragmentById(R.id.main_container);
//        if (fragment == null) {
//            fragment = new RecipeListFragment();
//            fm.beginTransaction()
//                    .add(R.id.main_container, fragment) // FrameLayout
//                    .commit();
//        }
    }
    @Override
    protected Fragment createFragment() {
        return new RecipeListFragment();
    }
}
