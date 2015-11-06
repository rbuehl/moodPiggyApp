package Utils;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by robin on 05/11/15.
 */
public class Utils {

    public static Date nextMonday(int hour, int minute){

        Calendar date = Calendar.getInstance();

        while (date.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
            date.add(Calendar.DATE, 1);
        }

        date.set(Calendar.HOUR_OF_DAY, hour);
        date.set(Calendar.MINUTE, minute);
        date.set(Calendar.SECOND, 0);

        return date.getTime();
    }

    public static int getMood(int mood, int participants){
        return mood/(2*participants);
    }
}
