package xyz.hellocraft.fuuleahelper.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TaskDetail {
    private int id;
    private int type;
    private boolean is_finished;
    private String content;
    private ArrayList<AttachmentData> attachments;
    private String discuss;
    private String status;
    private String audio;

    public TaskDetail(JSONObject rawJson) {
        try {
            id = rawJson.getInt("id");
            type = rawJson.getInt("type");
            is_finished = rawJson.getBoolean("is_finished");
            content = rawJson.getString("content");
            JSONArray att = rawJson.getJSONArray("attachments");
            ArrayList<AttachmentData> temp = new ArrayList<>();
            for (int i = 0; i < att.length(); i++) {
                JSONObject att_info = att.getJSONObject(i);
                temp.add(new AttachmentData(att_info.getString("name"),att_info.getString("source_file")));
            }
            attachments = temp;

            discuss = rawJson.get("discuss").toString();
            status = rawJson.getString("status");
            audio = rawJson.get("id").toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
