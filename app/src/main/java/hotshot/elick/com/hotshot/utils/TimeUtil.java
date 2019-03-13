package hotshot.elick.com.hotshot.utils;

public class TimeUtil {
    public static String secondToMinute(String second) {
        int sencondInt = Integer.parseInt(second);
        if (sencondInt <= 60) {
            String s="0"+second;
            return String.format("00:%s", s.substring(s.length()-2,s.length()));
        } else {
            int minute = sencondInt / 60;
            String remainder = ("0"+sencondInt % 60);
            return String.format("%s:%s", minute, remainder.substring(remainder.length()-2,remainder.length()));
        }
    }
}
