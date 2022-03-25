package xyz.hellocraft.fuuleahelper.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import xyz.hellocraft.fuuleahelper.R;
import xyz.hellocraft.fuuleahelper.data.TaskData;

import static xyz.hellocraft.fuuleahelper.utils.Constant.SUBJECT_MAP;

import com.bumptech.glide.Glide;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.TaskViewHolder> {
    private List<TaskData> allTasks;
    private final Activity activity;


    public TasksAdapter(Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.task_card, parent, false);
        return new TaskViewHolder(itemView);
    }

    public void setAllTasks(List<TaskData> allTasks) {
        this.allTasks = allTasks;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final TaskViewHolder holder, final int position) {
        TaskData taskData = allTasks.get(position);
        holder.textViewName.setText(taskData.getTitle());
        holder.textViewDesc.setText(activity.getString(R.string.task_end_time)+taskData.getEnd_at());
//        String logo_url = SUBJECT_MAP.get(taskData.getSubject_id());
        holder.imageViewAvatar.setImageResource(SUBJECT_MAP.get(taskData.getSubject_id()));
//        holder.imageViewAvatar.setImageURI(Uri.parse(logo_url));
//        Glide.with(activity).load(logo_url).circleCrop().into(holder.imageViewAvatar);
        holder.itemView.setOnClickListener(v -> {
            // TODO open task detail page
        });
    }

    @Override
    public int getItemCount() {
        return allTasks.size();
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName, textViewDesc;
        ImageView imageViewAvatar;

        TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewDesc = itemView.findViewById(R.id.textViewDesc);
            imageViewAvatar = itemView.findViewById(R.id.imageViewAvatar);

        }
    }
}
