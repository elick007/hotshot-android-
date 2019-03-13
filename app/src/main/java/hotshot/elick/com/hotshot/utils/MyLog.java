package hotshot.elick.com.hotshot.utils;

import android.util.Log;

public class MyLog {
    private static boolean debug;
    private static String TAG="ellision";
    public static void d(String s){
        if (debug){
            Log.d(TAG,s);
        }
    }
    public static void e(String s){
        if (debug){
            Log.e(TAG,s);
        }
    }
    public static void setDebug(boolean b){
        debug=b;
    }
}
