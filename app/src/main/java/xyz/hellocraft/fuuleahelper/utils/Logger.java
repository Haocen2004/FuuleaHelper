package xyz.hellocraft.fuuleahelper.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import xyz.hellocraft.fuuleahelper.data.LogLiveData;

@SuppressLint("StaticFieldLeak")
public class Logger {
    private static Logger INSTANCE;
    private static Context context;
    private static View view;
    private static boolean useSnackBar;
    private static List<String> logBlackList;
    private static LogLiveData logLiveData;
//    private static String blackListString;

    public Logger(Context context) {
        Logger.context = context;
        useSnackBar = false;
        logBlackList = new ArrayList();
        logLiveData = LogLiveData.getINSTANCE(context);
//        blackListString = getString(context, "logBlackLists");
//        if (!blackListString.equals("")) {
//            for (String blackItem : blackListString.split(";")) {
//                if (blackItem.length() < 4) w("BlackList", "blackMsg is too short: " + blackItem);
//                if (!blackItem.equals("")) {
//                    logBlackList.add(blackItem);
//                }
//            }
//            d("BlackList", "Total " + logBlackList.size());
////            logBlackList.addAll(Arrays.asList(blackListString.split(";")));
//        }
//        ToastUtils.init();
    }

    public static Logger getLogger(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new Logger(context);
        }
        return INSTANCE;
    }

    public static void addBlacklist(String blackMsg) {
//        d("addBlackList",blackMsg);
//        d("addBlackList",blackListString);
//        d("addBlackList",logBlackList.toString());
        if (logBlackList.contains(blackMsg)) return;
        if (blackMsg.length() < 2) {
            d("BlackList", "blackMsg is too short.");
            return;
        }
        logBlackList.add(blackMsg);
//        if (blackListString.equals("")) {
//            blackListString = blackMsg;
//        } else {
//            blackListString = blackListString + ";" + blackMsg;
//        }
//        saveString(context, "logBlackLists", blackListString);
    }

    public static void setView(View view) {
        Logger.view = view;
    }

    public static void e(String TAG, String msg) {
        Log.e(TAG, msg);
        logLiveData.addNewLog("ERROR", TAG, msg);
    }

    public static void d(String TAG, String msg) {
        Log.d(TAG, msg);
        logLiveData.addNewLog("DEBUG", TAG, msg);
    }

    public static void i(String TAG, String msg) {
        Log.i(TAG, msg);
        logLiveData.addNewLog("INFO", TAG, msg);
    }

    public static void w(String TAG, String msg) {
        Log.w(TAG, msg);
        logLiveData.addNewLog("WARNING", TAG, msg);
    }

    public static void makeToast(Context context, String msg, Integer length) {
        if (useSnackBar) {
            if (length == Toast.LENGTH_SHORT) {
                length = Snackbar.LENGTH_SHORT;
            } else if (length == Toast.LENGTH_LONG) {
                length = Snackbar.LENGTH_LONG;
            }
            d("Logger", "Transfer Toast Length to SnackBar Length: " + length);
            Snackbar.make(view, msg, length).show();
        } else {
            try {
                if (length == Toast.LENGTH_SHORT) {
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                } else if (length == Toast.LENGTH_LONG) {
                    Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
                }
            } catch (NullPointerException e) {
                Looper.prepare();
                if (length == Toast.LENGTH_SHORT) {
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                } else if (length == Toast.LENGTH_LONG) {
                    Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
                }
                Looper.loop();
            }

            d("TOAST", "show Toast " + msg);
        }
    }

    public void makeToast(String msg) {
        makeToast(context, msg, Toast.LENGTH_SHORT);
    }

    public void makeToast(Integer id) {
        makeToast(context.getString(id));
    }

    public void makeToast(Context context, String msg) {
        makeToast(context, msg, Toast.LENGTH_SHORT);
    }
}
