package xyz.hellocraft.fuuleahelper.ui.tasks;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.google.android.material.tabs.TabLayoutMediator;

import xyz.hellocraft.fuuleahelper.R;
import xyz.hellocraft.fuuleahelper.databinding.FragmentTasksBinding;
import xyz.hellocraft.fuuleahelper.ui.tasks.sub.FavoriteTasksFragment;
import xyz.hellocraft.fuuleahelper.ui.tasks.sub.FinishedTasksFragment;
import xyz.hellocraft.fuuleahelper.ui.tasks.sub.MainTasksFragment;

public class TasksFragment extends Fragment {

    private FragmentTasksBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTasksBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.viewPager.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                switch (position) {
                    case 0:
                        return new MainTasksFragment();
                    case 1:
                        return new FinishedTasksFragment();
                    case 2:
                        return new FavoriteTasksFragment();
                }
                return new MainTasksFragment();
            }


            @Override
            public int getItemCount() {
                return 3;
            }
        });
        new TabLayoutMediator(binding.tabLayout, binding.viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText(R.string.tasks_main);
                    break;
                case 1:
                    tab.setText(R.string.tasks_finished);
                    break;
                case 2:
                    tab.setText(R.string.tasks_favorite);
                    break;
            }
        }).attach();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}