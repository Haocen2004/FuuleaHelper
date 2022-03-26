package xyz.hellocraft.fuuleahelper.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import xyz.hellocraft.fuuleahelper.utils.Logger;

public class TaskDetailData {
    private int id;
    private int type;
    private boolean is_finished;
    private String content;
    private ArrayList<AttachmentData> attachments;
    private String discuss;
    private String status;
    private String audio;
    // another task data 2382460
    private String title;
    private int chapter_id;
    private int exercise_id;
    private boolean can_mark;
    private String correct_at;
    private int paper_id;
    private boolean enable_correct;
    //        type,is_finished,chapter_id,title,discuss,status,exercise_id,can_mark,correct_at,paper_id,attachments,enable_correct
    private Logger Log;

    public TaskDetailData(String raw_title, JSONObject rawJson) {
        title = raw_title;
        Log = Logger.getLogger(null);
        try {
            id = rawJson.getInt("id");
            type = rawJson.getInt("type");
            is_finished = rawJson.getBoolean("is_finished");
            content = rawJson.getString("content");
            JSONArray att = rawJson.getJSONArray("attachments");
            ArrayList<AttachmentData> temp = new ArrayList<>();
            for (int i = 0; i < att.length(); i++) {
                JSONObject att_info = att.getJSONObject(i);
                temp.add(new AttachmentData(att_info.getString("name"), att_info.getString("source_file")));
            }
            attachments = temp;

            discuss = rawJson.get("discuss").toString();
            status = rawJson.getString("status");
            audio = rawJson.get("id").toString();
        } catch (JSONException e) {
            e.printStackTrace();
            Logger.d("Parse Detail", "Normal Parse Failed. Try Second parse.");
            try {
                type = rawJson.getInt("type");
                is_finished = rawJson.getBoolean("is_finished");
//                chapter_id = rawJson.getInt("chapter_id");
                title = rawJson.getString("title");
                discuss = rawJson.getString("discuss");
                status = rawJson.getString("status");
                if (rawJson.getString("exercise_id").equals("null")) {
                    exercise_id = 0;
                } else {
                    exercise_id = rawJson.getInt("exercise_id");
                }
                can_mark = rawJson.getBoolean("can_mark");
                correct_at = rawJson.getString("correct_at");
                paper_id = rawJson.getInt("paper_id");
                enable_correct = rawJson.getBoolean("enable_correct");
            } catch (JSONException e1) {
                e1.printStackTrace();
                Logger.d("Parse Detail", "Second Parse Failed. Try Special parse.");
                try {
                    type = rawJson.getInt("type");
                    is_finished = rawJson.getBoolean("is_finished");
                    chapter_id = rawJson.getInt("chapter_id");
                    title = rawJson.getString("title");
                    discuss = rawJson.getString("discuss");
                    status = rawJson.getString("status");
                    exercise_id = rawJson.getInt("exercise_id");
                    can_mark = rawJson.getBoolean("can_mark");
                    correct_at = rawJson.getString("correct_at");
                    paper_id = rawJson.getInt("paper_id");
                    enable_correct = rawJson.getBoolean("enable_correct");
                    JSONArray att = rawJson.getJSONArray("attachments");
                    ArrayList<AttachmentData> temp = new ArrayList<>();
                    for (int i = 0; i < att.length(); i++) {
                        JSONObject att_info = att.getJSONObject(i);
                        temp.add(new AttachmentData(att_info.getString("name"), att_info.getString("source_file")));
                    }
                    attachments = temp;
                } catch (JSONException ex) {
                    ex.printStackTrace();
                    Log.makeToast("detail 解析错误!");
                }
            }

        }

    }

    public int getId() {
        return id;
    }

    public String getDiscuss() {
        return discuss;
    }

    public String getTitle() {
        return title;
    }

    public int getChapter_id() {
        return chapter_id;
    }

    public int getExercise_id() {
        return exercise_id;
    }

    public boolean isCan_mark() {
        return can_mark;
    }

    public String getCorrect_at() {
        return correct_at;
    }

    public int getPaper_id() {
        return paper_id;
    }

    public boolean isEnable_correct() {
        return enable_correct;
    }

    public Logger getLog() {
        return Log;
    }

    public int getType() {
        return type;
    }

    public boolean isIs_finished() {
        return is_finished;
    }

    public String getContent() {
        return content;
    }

    public ArrayList<AttachmentData> getAttachments() {
        return attachments;
    }

    public String getStatus() {
        return status;
    }

    public String getAudio() {
        return audio;
    }
}
