package com.ktzouvalidis.www.spacey;

import android.app.Application;
import android.content.Context;

/**
 * Created by Kwstas T on 15-Jan-18.
 */

public class ContextHandler extends Application {
    private static Context context;

    public void onCreate() {
        super.onCreate();
        ContextHandler.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return ContextHandler.context;
    }
}
