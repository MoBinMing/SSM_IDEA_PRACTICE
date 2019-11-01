package info.lzzy.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateTimeUtils {

    public static final SimpleDateFormat DATE_TIME_DEMAND=
            new SimpleDateFormat("yyyyMMdd", Locale.CANADA);
    public static final SimpleDateFormat DATE_TIME_FORMAT=
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CANADA);
    public static final SimpleDateFormat DATE_FORMAT=
            new SimpleDateFormat("yyyy-MM-dd", Locale.CANADA);
    public static final SimpleDateFormat DATE_THIS_FORMAT=
            new SimpleDateFormat("HH:mm:ss", Locale.CANADA);


    public static void main(String[] args) {
        String date=DATE_TIME_FORMAT.format(new Date());
        try {
            Date date1=DATE_TIME_DEMAND.parse(date);
            System.out.println(date);
            System.out.println(date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
