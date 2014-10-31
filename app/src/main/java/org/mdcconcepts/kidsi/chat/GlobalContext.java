package org.mdcconcepts.kidsi.chat;

import android.app.Application;
import android.content.Context;

public class GlobalContext extends Application {
    public static GlobalContext  instance;
    public GlobalContext()
    {
       instance = this;
    }
    public static Context getContext()
    {
       return instance;
    } 
}