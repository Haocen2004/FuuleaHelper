package xyz.hellocraft.fuuleahelper.data;

public class LogData {
    private final String level;
    private final String TAG;
    private final String message;

    public LogData(String level, String TAG, String message) {
        this.level = level;
        this.TAG = TAG;
        this.message = message;
    }

    public String getLevel() {
        return level;
    }

    public String getTAG() {
        return TAG;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "{" +
                "level='" + level + '\'' +
                ", TAG='" + TAG + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
