package xyz.hellocraft.fuuleahelper.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import xyz.hellocraft.fuuleahelper.R;
import xyz.hellocraft.fuuleahelper.activity.TaskDetailActivity;
import xyz.hellocraft.fuuleahelper.data.TaskDetailData;
import xyz.hellocraft.fuuleahelper.utils.Logger;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskDetailViewHolder> {

    private List<TaskDetailData> allDetails;
    private final Activity activity;

    public TaskAdapter(Activity activity) {
        this.activity = activity;
    }

    public void setAllDetails(List<TaskDetailData> allDetails) {
        this.allDetails = allDetails;
    }

    @Override
    public int getItemViewType(int position) {

        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public TaskDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.task_detail_card, parent, false);
        return new TaskDetailViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskDetailViewHolder holder, int position) {
        TaskDetailData taskDetailData = allDetails.get(position);
        holder.textViewName.setText(taskDetailData.getTitle());
//        holder.textViewDesc.setText(taskDetailData.getStatus());
        String taskStatus = "未完成";
        switch (taskDetailData.getStatus()) {
            case "finished":
            case "marked":
                taskStatus = "已完成";
                break;

        }
        holder.textViewStatus.setText(taskStatus);
//        String logo_url = SUBJECT_MAP.get(taskData.getSubject_id());
        holder.itemView.setOnClickListener(v -> {
            if (taskDetailData.getPaper_id() != 0) {
                Logger.d("TaskAdapter", "found paper_id: " + taskDetailData.getPaper_id());
                Intent intent = new Intent(activity, TaskDetailActivity.class);
                intent.putExtra("data", taskDetailData);
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return allDetails.size();
    }

    static class TaskDetailViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName, textViewDesc, textViewStatus;

        TaskDetailViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewHead);
//            textViewDesc = itemView.findViewById(R.id.textViewType);
            textViewStatus = itemView.findViewById(R.id.textViewDetail);

        }
    }

}
