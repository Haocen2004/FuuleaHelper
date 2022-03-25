package xyz.hellocraft.fuuleahelper.data;

import static java.lang.Integer.parseInt;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TaskData {
    private int id;
    private String title;
    private Integer subject_id;
    private String finish_at;
    private String description;
    private String subject_name;
    private String published_at;
    private String end_at;
    private String show_answer_at;
    private boolean allow_submit_if_delay;
    private String student_commit_pattern;
    private Integer type;
    private Integer mark_type;
    private Integer task_class_id;
    private boolean is_favorite;
    private String teacher_name;
    private String submit_at;
    private ArrayList<TaskDetail> detail;
    private Boolean force_submit;
    private String allow_collect_audio;
    private String enable_judge_proper;
    private String enable_correct;
    private String hash_id;
    private ArrayList<String> unfinished_students;

    private boolean has_detail = false;

    public void parseJson(JSONObject rawJson) {
        try {
            id = rawJson.getInt("id");
            title = rawJson.getString("title");
            description = rawJson.getString("description");
            published_at = rawJson.getString("published_at");
            finish_at = rawJson.getString("finish_at");
            end_at = rawJson.getString("end_at");
            subject_name = rawJson.getString("subject_name");
            subject_id = rawJson.getInt("subject_id");
            show_answer_at = rawJson.getString("show_answer_at");
            is_favorite = rawJson.getBoolean("is_favorite");
            finish_at = rawJson.getString("finish_at");
            type = rawJson.getInt("type");
            mark_type = rawJson.getInt("mark_type");
            task_class_id = rawJson.getInt("task_class_id");
            allow_submit_if_delay = rawJson.getBoolean("allow_submit_if_delay");
            student_commit_pattern = rawJson.getString("student_commit_pattern");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getTaskDetail() {
        if (has_detail) {
            return;
        }
        try {
        JSONObject rawJson = new JSONObject();
        teacher_name = rawJson.getString("teacher_name");
        submit_at = rawJson.getString("submit_at");
        JSONArray details = rawJson.getJSONArray("detail");
        ArrayList<TaskDetail> detailArrayList = new ArrayList<>();
            for (int i = 0; i < details.length(); i++) {
                detailArrayList.add(new TaskDetail(details.getJSONObject(i)));
            }
        detail = detailArrayList;
        force_submit = rawJson.getBoolean("force_submit");
        allow_collect_audio = rawJson.getString("allow_collect_audio");
        enable_judge_proper = rawJson.getString("enable_judge_proper");
        enable_correct = rawJson.getString("enable_correct");
        hash_id = rawJson.getString("hash_id");
        JSONArray unfinished_stu = rawJson.getJSONArray("unfinished_students");
        ArrayList<String> temp = new ArrayList();
        for (int i = 0; i < unfinished_stu.length(); i++) {
            temp.add(unfinished_stu.getString(i));
        }
        unfinished_students = temp;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        has_detail = true;
    }

    public boolean has_detail() {
        return has_detail;
    }



    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Integer getSubject_id() {
        return subject_id;
    }

    public String getFinish_at() {
        return finish_at;
    }

    public String getSubmit_at() {
        return submit_at;
    }

    public ArrayList<TaskDetail> getDetail() {
        return detail;
    }

    public String getDescription() {
        return description;
    }

    public String getSubject_name() {
        return subject_name;
    }

    public String getTeacher_name() {
        return teacher_name;
    }

    public String getPublished_at() {
        return published_at;
    }

    public String getEnd_at() {
        return end_at;
    }

    public Boolean getForce_submit() {
        return force_submit;
    }

    public String getAllow_collect_audio() {
        return allow_collect_audio;
    }

    public String getShow_answer_at() {
        return show_answer_at;
    }

    public String getEnable_judge_proper() {
        return enable_judge_proper;
    }

    public String getEnable_correct() {
        return enable_correct;
    }

    public boolean getIs_favorite() {
        return is_favorite;
    }

    public boolean isIs_favorite() {
        return is_favorite;
    }

    public boolean isAllow_submit_if_delay() {
        return allow_submit_if_delay;
    }

    public boolean isHas_detail() {
        return has_detail;
    }

    public String getStudent_commit_pattern() {
        return student_commit_pattern;
    }

    public Integer getType() {
        return type;
    }

    public Integer getMark_type() {
        return mark_type;
    }

    public Integer getTask_class_id() {
        return task_class_id;
    }

    public String getHash_id() {
        return hash_id;
    }

    public ArrayList<String> getUnfinished_students() {
        return unfinished_students;
    }


}
