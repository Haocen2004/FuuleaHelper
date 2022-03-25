package xyz.hellocraft.fuuleahelper.ui.tasks.sub;

import static xyz.hellocraft.fuuleahelper.utils.Constant.TOKEN;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import xyz.hellocraft.fuuleahelper.adapter.TasksAdapter;
import xyz.hellocraft.fuuleahelper.data.TaskData;
import xyz.hellocraft.fuuleahelper.databinding.FragmentMainTasksBinding;
import xyz.hellocraft.fuuleahelper.utils.Logger;
import xyz.hellocraft.fuuleahelper.utils.Network;


public class MainTasksFragment extends Fragment {
    private RecyclerView recyclerViewSp;
    private TasksAdapter tasksAdapter;
    private FragmentMainTasksBinding binding;
    private SharedPreferences preferences;

    public MainTasksFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMainTasksBinding.inflate(inflater, container, false);
        preferences = getActivity().getSharedPreferences("data",Context.MODE_PRIVATE);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerViewSp = binding.taskRecycleView;
        tasksAdapter = new TasksAdapter(getActivity());
        initAdapter(tasksAdapter);
        recyclerViewSp.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewSp.setAdapter(tasksAdapter);
    }

    private void initAdapter(TasksAdapter adapter) {
//        List<TaskData> sponsorDataOld = new ArrayList<>();
        ArrayList<TaskData> taskData = new ArrayList<>();
//        sponsorDataOld.add(new TaskData("Loading...", "", "a", "b", "c", "d"));
        adapter.setAllTasks(taskData);
            new Thread(() -> {
                HashMap<String, String> map = new HashMap<>();
                map.put("Cookie", "sessionid="+preferences.getString("sid",""));
                map.put("Authorization", TOKEN);
                String tasks_feedback = Network.sendGet("https://api.fuulea.com/api/task/?finished=false&page=1&favorite=false",map);
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
                adapter.setAllTasks(taskData);
                // 刷新操作
                Looper.prepare();
                new Handler(Looper.getMainLooper()).post(adapter::notifyDataSetChanged);
                Looper.loop();
            }).start();
    }
}