package ex.nme.hallplatsen;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Created by nm2 on 2017-06-13
 */

public class Utils {

    public static String date(){
        //get current date
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return df.format(Calendar.getInstance().getTime());
    }

    public static String time(){
        //get current time 23:45
        DateFormat df = new SimpleDateFormat("H:m", Locale.getDefault());
        return df.format(Calendar.getInstance().getTime());
    }

    public static String timeDiff(String departuretime){
        //convert time to difference between departuretime and current time.
        String res = "ERR";
        try {
            String currentTime = Utils.time();
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
            Date d1 = sdf.parse(currentTime);
            Date d2 = sdf.parse(departuretime);
            long diff = d2.getTime() - d1.getTime();
            long diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(diff);

            if(diffInMinutes > 60){
                res = ">60";
            } else {
                res = "" + diffInMinutes;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return res;
    }

    public static String cutoff(String str, int len){
        String result = str;
        if(str.length() > len){
            result = str.substring(0, len).trim();
        }
        return result;
    }

}
