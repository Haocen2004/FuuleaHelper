package xyz.hellocraft.fuuleahelper.activity;

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

import xyz.hellocraft.fuuleahelper.adapter.QuestionAdapter;
import xyz.hellocraft.fuuleahelper.data.PaperData;
import xyz.hellocraft.fuuleahelper.data.TaskDetailData;
import xyz.hellocraft.fuuleahelper.databinding.ActivityTaskDetailBinding;
import xyz.hellocraft.fuuleahelper.utils.Logger;

public class TaskDetailActivity extends AppCompatActivity {

    private ActivityTaskDetailBinding binding;
    private Logger Log;
    private static final String TAG = "TaskDetailActivity";
    private TaskDetailData taskDetailData;
    private RecyclerView recycleViewPaper;
    private QuestionAdapter questionAdapter;
    private PaperData paperData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTaskDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Log = Logger.getLogger(this);
        Intent intent = getIntent();
        if (intent != null) {
            taskDetailData = (TaskDetailData) intent.getSerializableExtra("data");
        } else {
            Logger.d(TAG, "taskDetailData not found,please insert data!");
            Log.makeToast("打开Activity失败,未找到TaskDetailData!");
            finish();
        }

        binding.textTaskName.setText(taskDetailData.getTitle());
        paperData = new PaperData(taskDetailData.getPaper_id());

        recycleViewPaper = binding.recycleViewPaper;
        questionAdapter = new QuestionAdapter(this);
        recycleViewPaper.setLayoutManager(new LinearLayoutManager(this));
        recycleViewPaper.setAdapter(questionAdapter);
        new Thread(load_data).start();
    }


    @SuppressLint("SetTextI18n")
    private void refView() {
        questionAdapter.setAllQuestions(paperData.getQuestions());
        questionAdapter.notifyItemRangeChanged(0, paperData.getQuestions().size() - 1);
        binding.textTaskInfo.setText(paperData.getSummary());
//        binding.textUnfinishedCount.setText(getText(R.string.task_unfinished_count).toString() + taskData.getUnfinished_students().size());
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

            if (!paperData.getPaperDetail()) {
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