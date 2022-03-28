package xyz.hellocraft.fuuleahelper.ui.tasks.sub;

import static xyz.hellocraft.fuuleahelper.utils.Constant.AUTH_MAP;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smart.refresh.footer.BallPulseFooter;
import com.scwang.smart.refresh.header.MaterialHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import xyz.hellocraft.fuuleahelper.adapter.TaskListAdapter;
import xyz.hellocraft.fuuleahelper.data.TaskData;
import xyz.hellocraft.fuuleahelper.databinding.FragmentFavoriteTasksBinding;
import xyz.hellocraft.fuuleahelper.utils.Logger;
import xyz.hellocraft.fuuleahelper.utils.Network;


public class FavoriteTasksFragment extends Fragment {
    private RecyclerView recyclerViewSp;
    private TaskListAdapter taskListAdapter;
    private FragmentFavoriteTasksBinding binding;
    private SharedPreferences preferences;
    private int curr_page = 1;
    private ArrayList<TaskData> taskData = new ArrayList<>();
    private RefreshLayout refLayout;
    private int refAction = 0;

    public FavoriteTasksFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFavoriteTasksBinding.inflate(inflater, container, false);
        preferences = getActivity().getSharedPreferences("data",Context.MODE_PRIVATE);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RefreshLayout refreshLayout = binding.refreshLayout;
        refreshLayout.setRefreshHeader(new MaterialHeader(requireContext()));
        refreshLayout.setRefreshFooter(new BallPulseFooter(requireContext()));
        refreshLayout.setOnRefreshListener(refreshlayout -> {
            curr_page = 1;
            refLayout = refreshlayout;
            refAction = 2;
            taskData = new ArrayList<>();
            new Thread(ref_page).start();
        });
        refreshLayout.setOnLoadMoreListener(refreshlayout -> {
            refLayout = refreshlayout;
            refAction = 1;
            new Thread(ref_page).start();
        });
        recyclerViewSp = binding.taskRecycleView;
        taskListAdapter = new TaskListAdapter(getActivity());
        initAdapter(taskListAdapter);
        recyclerViewSp.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewSp.setAdapter(taskListAdapter);
    }

    private void initAdapter(TaskListAdapter adapter) {
//        List<TaskData> sponsorDataOld = new ArrayList<>();
//        sponsorDataOld.add(new TaskData("Loading...", "", "a", "b", "c", "d"));
        adapter.setAllTasks(taskData);
        new Thread(ref_page).start();
    }
    Runnable ref_page = new Runnable() {
        @Override
        public void run() {
            String tasks_feedback = Network.sendGet("https://api.fuulea.com/api/task/?finished=false&page=" + curr_page + "&favorite=true", AUTH_MAP);
            Logger.d("tasks",tasks_feedback);
            try {
                JSONObject tasks_json = new JSONObject(tasks_feedback);
                JSONArray data_array = tasks_json.getJSONArray("data");
                for (int i = 0; i < data_array.length(); i++) {
                    TaskData taskData1 = new TaskData();
                    taskData1.parseJson(data_array.getJSONObject(i));
                    taskData.add(taskData1);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            taskListAdapter.setAllTasks(taskData);
            curr_page++;
            switch (refAction) {
                case 0:
                    break;
                case 1:
                    refLayout.finishLoadMore();
                    refAction = 0;
                    break;
                case 2:
                    refLayout.finishRefresh();
                    refAction = 0;
                    break;
            }
            // 刷新操作
            Looper.prepare();
            new Handler(Looper.getMainLooper()).post(taskListAdapter::notifyDataSetChanged);
            Looper.loop();
        }
    };
}