package xyz.hellocraft.fuuleahelper.activity;

import static xyz.hellocraft.fuuleahelper.utils.Constant.AUTH_MAP;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import xyz.hellocraft.fuuleahelper.R;
import xyz.hellocraft.fuuleahelper.adapter.TaskDetailsAdapter;
import xyz.hellocraft.fuuleahelper.data.TaskData;
import xyz.hellocraft.fuuleahelper.data.TaskDetailData;
import xyz.hellocraft.fuuleahelper.databinding.ActivityTaskDetailBinding;
import xyz.hellocraft.fuuleahelper.utils.Logger;
import xyz.hellocraft.fuuleahelper.utils.Network;

public class TaskDetailActivity extends AppCompatActivity {

    private Logger Log;
    private TaskData taskData;
    private ActivityTaskDetailBinding binding;
    private static final String TAG = "TaskDetailActivity";
    private RecyclerView recyclerViewSp;
    private TaskDetailsAdapter taskDetailsAdapter;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTaskDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Log = Logger.getLogger(this);
        Intent intent = getIntent();
        if (intent != null) {
            taskData = (TaskData) intent.getSerializableExtra("data");
        } else {
            Logger.d(TAG, "taskData not found,please insert data!");
            Log.makeToast("打开Activity失败,未找到TaskData!");
            finish();
        }

        binding.textTaskReq.setText(taskData.getTitle());
        binding.textDesc.setText(taskData.getDescription());
        binding.textTime.setText(getString(R.string.task_end_time) + taskData.getEnd_at());
//        binding.textUnfinished.setText("");

        if (taskData.getIs_favorite()) {
            binding.favoriteButton.setImageResource(R.drawable.ic_baseline_star_24);
        } else {
            binding.favoriteButton.setImageResource(R.drawable.ic_baseline_star_outline_24_white);
        }

        binding.favoriteButton.setOnClickListener(view -> {
            taskData.setIs_favorite(!taskData.getIs_favorite());
            if (taskData.getIs_favorite()) {
                binding.favoriteButton.setImageResource(R.drawable.ic_baseline_star_24);
            } else {
                binding.favoriteButton.setImageResource(R.drawable.ic_baseline_star_outline_24_white);
            }
            new Thread(() -> Network.sendPost("https://api.fuulea.com/api/task/" + taskData.getId() + "/favorite/", "", AUTH_MAP)).start();
        });
        recyclerViewSp = binding.recycleViewTasks;
        taskDetailsAdapter = new TaskDetailsAdapter(this);
        ArrayList<TaskDetailData> detailArrayList = new ArrayList<>();
        taskDetailsAdapter.setAllDetails(detailArrayList);
        recyclerViewSp.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewSp.setAdapter(taskDetailsAdapter);
        new Thread(load_data).start();

    }

    @SuppressLint("SetTextI18n")
    private void refView() {
        taskDetailsAdapter.setAllDetails(taskData.getDetail());
        taskDetailsAdapter.notifyItemRangeChanged(0, taskData.getDetail().size());
        binding.textUnfinishedList.setText(taskData.getUnfinished_students_String());
        binding.textUnfinishedCount.setText(getText(R.string.task_unfinished_count).toString() + taskData.getUnfinished_students().size());
    }

    Handler main_handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
        }
    };

    Runnable load_data = new Runnable() {
        @Override
        public void run() {

            if (!taskData.getTaskDetail()) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                new Thread(load_data).start();
            } else {
                main_handler.post(() -> refView());
            }

        }

    };
}