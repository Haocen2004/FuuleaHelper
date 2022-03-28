package xyz.hellocraft.fuuleahelper.data;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class ChoiceData implements Serializable {
    private String name;
    private String content;

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }

    public ChoiceData(JSONObject rawJson) {
        try {
            name = rawJson.getString("name");
            content = rawJson.getString("content");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
