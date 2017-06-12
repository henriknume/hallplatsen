package ex.nme.hallplatsen;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

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

}
