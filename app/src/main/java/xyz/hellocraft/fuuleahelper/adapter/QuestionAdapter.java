package xyz.hellocraft.fuuleahelper.adapter;

import static androidx.core.text.HtmlCompat.FROM_HTML_MODE_COMPACT;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import xyz.hellocraft.fuuleahelper.R;
import xyz.hellocraft.fuuleahelper.data.ChoiceData;
import xyz.hellocraft.fuuleahelper.data.QuestionData;
import xyz.hellocraft.fuuleahelper.utils.GlideImageGetter;
import xyz.hellocraft.fuuleahelper.utils.Logger;

public class QuestionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<QuestionData> allQuestions = new ArrayList<>();
    private Activity activity;

    private final int DEFAULT_TYPE = 0; // Upload Photo
    private final int SELECT_TYPE = 1;
    private final int EMPTY_TYPE = 2;


    public QuestionAdapter(Activity activity) {
        this.activity = activity;
    }

    public void setAllQuestions(ArrayList<QuestionData> allQuestions) {
        this.allQuestions = allQuestions;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View view;
        switch (viewType) {
            case SELECT_TYPE:
                view = layoutInflater.inflate(R.layout.task_detail_select_main, parent, false);
                return new SelectViewHolder(view);
            case EMPTY_TYPE:
                view = layoutInflater.inflate(R.layout.task_detail_card, parent, false);
                return new EmptyViewHolder(view);
            default:
                view = layoutInflater.inflate(R.layout.task_detail_upload_photo, parent, false);
                return new QuestionViewHolder(view);
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        QuestionData questionData = allQuestions.get(position);
        if (holder instanceof SelectViewHolder) {
            Logger.d("Render", "try render" + questionData.getQuestion().replace("\\", ""));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                ((SelectViewHolder) holder).textViewDesc.setText(Html.fromHtml(questionData.getQuestion().replace("\\", ""), FROM_HTML_MODE_COMPACT, new GlideImageGetter(((SelectViewHolder) holder).textViewDesc), null));
            }
            if (questionData.isIs_multi_choice()) {
                ((SelectViewHolder) holder).textViewHead.setText("第 " + questionData.getNo() + " 题 " + questionData.getScore() + "分 （多选题）");
                for (ChoiceData choice : questionData.getChoices()) {
                    CheckBox checkBox = new CheckBox(activity);
                    checkBox.setText(Html.fromHtml(choice.getName() + "." + choice.getContent(), new GlideImageGetter(checkBox), null));
                    ((SelectViewHolder) holder).multiSelectMap.put(choice.getName(), checkBox);
                    ((SelectViewHolder) holder).layoutSelection.addView(checkBox);
                }
            } else {
                ((SelectViewHolder) holder).textViewHead.setText("第 " + questionData.getNo() + " 题 " + questionData.getScore() + "分 （单选题）");
                RadioGroup radioGroup = new RadioGroup(activity);
                for (ChoiceData choice : questionData.getChoices()) {
                    RadioButton radioButton = new RadioButton(activity);
                    radioButton.setText(Html.fromHtml(choice.getName() + "." + choice.getContent(), new GlideImageGetter(radioButton), null));
                    radioGroup.addView(radioButton);
                    ((SelectViewHolder) holder).singleSelectMap.put(choice.getName(), radioButton);
                }
                ((SelectViewHolder) holder).layoutSelection.addView(radioGroup);
            }
        } else if (holder instanceof QuestionViewHolder) {
            ((QuestionViewHolder) holder).textViewHead.setText("第 " + questionData.getNo() + " 题 " + questionData.getScore() + "分 （非选择题）");
            Logger.d("Render", "try render" + questionData.getQuestion().replace("\\", ""));
            ((QuestionViewHolder) holder).textViewDesc.setText(Html.fromHtml(questionData.getQuestion().replace("\\", ""), new GlideImageGetter(((QuestionViewHolder) holder).textViewDesc), null));
        }
    }

    @Override
    public int getItemViewType(int position) {
        QuestionData questionData = allQuestions.get(position);
        if (questionData.isIs_choice()) return SELECT_TYPE;
        if (questionData.getChildren().size() > 0) return EMPTY_TYPE;
        return DEFAULT_TYPE;
    }

    @Override
    public int getItemCount() {
        return allQuestions.size();
    }

    static class QuestionViewHolder extends RecyclerView.ViewHolder {
        TextView textViewHead, textViewDesc, textViewStatus;

        QuestionViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewHead = itemView.findViewById(R.id.textViewHead);
//            textViewDesc = itemView.findViewById(R.id.textViewType);
            textViewDesc = itemView.findViewById(R.id.textViewDetail);

        }
    }

    static class SelectViewHolder extends RecyclerView.ViewHolder {
        TextView textViewHead, textViewDesc, textViewStatus;
        LinearLayout layoutSelection;
        Map<String, CheckBox> multiSelectMap = new HashMap<>();
        Map<String, RadioButton> singleSelectMap = new HashMap<>();

        SelectViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewHead = itemView.findViewById(R.id.textViewHead);
//            textViewDesc = itemView.findViewById(R.id.textViewType);
            textViewDesc = itemView.findViewById(R.id.textViewDetail);
            layoutSelection = itemView.findViewById(R.id.layoutSelection);

        }
    }

    static class EmptyViewHolder extends RecyclerView.ViewHolder {
        TextView textViewHead, textViewDesc, textViewStatus;

        EmptyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewHead = itemView.findViewById(R.id.textViewHead);
//            textViewDesc = itemView.findViewById(R.id.textViewType);
            textViewDesc = itemView.findViewById(R.id.textViewDetail);

        }
    }
}
