package xyz.hellocraft.fuuleahelper.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import xyz.hellocraft.fuuleahelper.R;
import xyz.hellocraft.fuuleahelper.data.TaskDetailData;

public class TaskDetailsAdapter extends RecyclerView.Adapter<TaskDetailsAdapter.TaskDetailViewHolder> {

    private List<TaskDetailData> allDetails;
    private final Activity activity;

    public TaskDetailsAdapter(Activity activity) {
        this.activity = activity;
    }

    public void setAllDetails(List<TaskDetailData> allDetails) {
        this.allDetails = allDetails;
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
            // TODO open task detail page
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
            textViewName = itemView.findViewById(R.id.textViewName);
//            textViewDesc = itemView.findViewById(R.id.textViewType);
            textViewStatus = itemView.findViewById(R.id.textViewStatus);

        }
    }

}
