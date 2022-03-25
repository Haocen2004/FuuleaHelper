package xyz.hellocraft.fuuleahelper;

import static xyz.hellocraft.fuuleahelper.utils.Constant.SUBJECT_MAP;

import android.app.Application;

import java.util.HashMap;

import xyz.hellocraft.fuuleahelper.data.LogLiveData;
import xyz.hellocraft.fuuleahelper.utils.Logger;

public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LogLiveData.getINSTANCE(this);
        Logger.getLogger(this);
    }
}
