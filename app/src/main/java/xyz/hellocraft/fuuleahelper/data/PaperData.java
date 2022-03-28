package xyz.hellocraft.fuuleahelper.data;

import static xyz.hellocraft.fuuleahelper.utils.Constant.AUTH_MAP;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

import xyz.hellocraft.fuuleahelper.utils.Logger;
import xyz.hellocraft.fuuleahelper.utils.Network;


public class PaperData implements Serializable {
    private int id;
    private String title;
    private String summary;
    private ArrayList<AttachmentData> attachments;
    private ArrayList<QuestionData> questions;

    private boolean has_detail = false;
    private static final String TAG = "PaperData";

    public PaperData(int id) {
        this.id = id;
    }

    public boolean getPaperDetail() {
        if (has_detail) {
            return true;
        } else {
            String feedback = Network.sendGet("https://api.fuulea.com/api/paper/" + id + "/", AUTH_MAP);
            Logger.d(TAG, feedback);
            try {

                JSONObject rawJson = new JSONObject(feedback);
                title = rawJson.getString("title");
                summary = rawJson.getString("summary");
                JSONArray att = rawJson.getJSONArray("attachments");
                ArrayList<AttachmentData> temp = new ArrayList<>();
                for (int i = 0; i < att.length(); i++) {
                    JSONObject att_info = att.getJSONObject(i);
                    temp.add(new AttachmentData(att_info.getString("name"), att_info.getString("source_file")));
                }
                attachments = temp;
                JSONArray att2 = rawJson.getJSONArray("questions");
                ArrayList<QuestionData> temp2 = new ArrayList<>();
                for (int i = 0; i < att2.length(); i++) {
                    temp2.add(new QuestionData(att2.getJSONObject(i)));
                }
                questions = temp2;

            } catch (JSONException e) {
                e.printStackTrace();
                return false;
            }
        }
        has_detail = true;
        return true;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getSummary() {
        return summary;
    }

    public ArrayList<AttachmentData> getAttachments() {
        return attachments;
    }

    public ArrayList<QuestionData> getQuestions() {
        return questions;
    }


}
