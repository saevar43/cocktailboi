package hi.hbv601g.cocktailboi;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class RecipeListFragment extends Fragment {

    private RecyclerView mRecipeRecyclerView;
    private RecipeAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_list, container, false);

        mRecipeRecyclerView = view.findViewById(R.id.recipe_recycler_view);
        mRecipeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();
        return view;
    }

    private void updateUI() {
        RecipeBook recipeBook = RecipeBook.get(getActivity());
        List<Recipe> recipes = recipeBook.getRecipes();

        mAdapter = new RecipeAdapter(recipes);
        mRecipeRecyclerView.setAdapter(mAdapter);
    }

    private TextView mTitleTextView;

    private class RecipeHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{
        private Recipe mRecipe;

        // itemView er public breyta í RecyclerView.ViewHolder

        public RecipeHolder (LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_recipe, parent, false));
            itemView.setOnClickListener(this);

            mTitleTextView = (TextView) itemView.findViewById(R.id.recipe_name);
        }
        public void bind (Recipe Recipe) {
            mRecipe = Recipe;
            mTitleTextView.setText(mRecipe.getName());
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(getActivity(),
                    mRecipe.getName() +" clicked!",Toast.LENGTH_SHORT)
                    .show();
        }

    }
    private class RecipeAdapter extends RecyclerView.Adapter<RecipeHolder> {
        private List<Recipe> mRecipes;
        public RecipeAdapter(List<Recipe> Recipes) {
            mRecipes = Recipes;
        }
        @Override
        public RecipeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new RecipeHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder (RecipeHolder holder, int position) {
            Recipe Recipe = mRecipes.get(position);
            holder.bind(Recipe);
        }
        @Override
        public int getItemCount() {
            return mRecipes.size();
        }


    }
}
