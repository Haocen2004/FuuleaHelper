package xyz.hellocraft.fuuleahelper.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class QuestionData implements Serializable {
    private int id;
    private String question;
    private String cover;
    private String video;
    private boolean is_real;
    private String year;
    private String region;
    private boolean is_choice;
    private boolean is_multi_choice;
    private String no_choice_answer;
    private String analysis;
    private String proper;
    private String source_text;
    private int no;
    private double score;
    private String group_name;
    private boolean has_video;
    private boolean has_analysis;
    private ArrayList<ChoiceData> choices;
    private String first_right_count;
    private String first_right_percent;
    private String watch_count;
    private int model;
    private int level;
    private String audio;
    private boolean has_audio;
    private String audio_author;
    private String judge_proper;
    private int pqid;
    private String audios;
    private ArrayList<QuestionData> children;
    private String intention_type;
    private boolean indeterminate_choices;

    public QuestionData(JSONObject rawJson) {
        try {
            id = rawJson.getInt("id");
            question = rawJson.getString("question");
            question = rawJson.getString("question");
            cover = rawJson.getString("cover");
            video = rawJson.getString("video");
            is_real = rawJson.getBoolean("is_real");
            year = rawJson.getString("year");
            region = rawJson.getString("region");
            is_choice = rawJson.getBoolean("is_choice");
            is_multi_choice = rawJson.getBoolean("is_multi_choice");
            no_choice_answer = rawJson.getString("no_choice_answer");
            analysis = rawJson.getString("analysis");
            proper = rawJson.getString("proper");
            source_text = rawJson.getString("source_text");
            no = rawJson.getInt("no");
            score = rawJson.getDouble("score");
            group_name = rawJson.getString("group_name");
            has_video = rawJson.getBoolean("has_video");
            has_analysis = rawJson.getBoolean("has_analysis");
            JSONArray choices_array = rawJson.getJSONArray("choices");
            ArrayList<ChoiceData> choiceData = new ArrayList<>();
            for (int i = 0; i < choices_array.length(); i++) {
                choiceData.add(new ChoiceData(choices_array.getJSONObject(i)));
            }
            choices = choiceData;
            first_right_count = rawJson.getString("first_right_count");
            first_right_percent = rawJson.getString("first_right_percent");
            watch_count = rawJson.getString("watch_count");
            model = rawJson.getInt("model");
            level = rawJson.getInt("level");
            audio = rawJson.getString("audio");
            has_audio = rawJson.getBoolean("has_audio");
            audio_author = rawJson.getString("audio_author");
            judge_proper = rawJson.getString("judge_proper");
            pqid = rawJson.getInt("pqid");
            audios = rawJson.getString("audios");
            ArrayList<QuestionData> questionDataArrayList = new ArrayList<>();
            JSONArray questionArray = rawJson.getJSONArray("children");
            for (int i = 0; i < questionArray.length(); i++) {
                questionDataArrayList.add(new QuestionData(questionArray.getJSONObject(i)));
            }
            children = questionDataArrayList;
            intention_type = rawJson.getString("intention_type");
            try {
                indeterminate_choices = rawJson.getBoolean("indeterminate_choices");
            } catch (Exception e) {
                indeterminate_choices = false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public String getCover() {
        return cover;
    }

    public String getVideo() {
        return video;
    }

    public boolean isIs_real() {
        return is_real;
    }

    public String getYear() {
        return year;
    }

    public String getRegion() {
        return region;
    }

    public boolean isIs_choice() {
        return is_choice;
    }

    public boolean isIs_multi_choice() {
        return is_multi_choice;
    }

    public String getNo_choice_answer() {
        return no_choice_answer;
    }

    public String getAnalysis() {
        return analysis;
    }

    public String getProper() {
        return proper;
    }

    public String getSource_text() {
        return source_text;
    }

    public int getNo() {
        return no;
    }

    public double getScore() {
        return score;
    }

    public String getGroup_name() {
        return group_name;
    }

    public boolean isHas_video() {
        return has_video;
    }

    public boolean isHas_analysis() {
        return has_analysis;
    }

    public ArrayList<ChoiceData> getChoices() {
        return choices;
    }

    public String getFirst_right_count() {
        return first_right_count;
    }

    public String getFirst_right_percent() {
        return first_right_percent;
    }

    public String getWatch_count() {
        return watch_count;
    }

    public int getModel() {
        return model;
    }

    public int getLevel() {
        return level;
    }

    public String getAudio() {
        return audio;
    }

    public boolean isHas_audio() {
        return has_audio;
    }

    public String getAudio_author() {
        return audio_author;
    }

    public String getJudge_proper() {
        return judge_proper;
    }

    public int getPqid() {
        return pqid;
    }

    public String getAudios() {
        return audios;
    }

    public ArrayList<QuestionData> getChildren() {
        return children;
    }

    public String getIntention_type() {
        return intention_type;
    }

    public boolean getIndeterminate_choices() {
        return indeterminate_choices;
    }
}
