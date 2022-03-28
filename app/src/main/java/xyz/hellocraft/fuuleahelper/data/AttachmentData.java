package xyz.hellocraft.fuuleahelper.data;

import java.io.Serializable;

public class AttachmentData implements Serializable {
    private String name;
    private String url;

    public AttachmentData(String name, String url) {
        this.name = name;
        this.url = url;
    }
}
