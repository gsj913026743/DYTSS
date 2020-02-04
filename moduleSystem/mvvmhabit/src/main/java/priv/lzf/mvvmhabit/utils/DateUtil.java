package priv.lzf.mvvmhabit.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 作者：Created by 45703
 * 时间：Created on 2019/9/21.
 */
public class DateUtil {

    public static String getCurrentTime(){
        Date date = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return dateFormat.format(date);
    }

    public static String getBeforeMinute(int minute){
        Calendar beforeTime = Calendar.getInstance();
        beforeTime.add(Calendar.MINUTE, -minute);//
        Date beforeD = beforeTime.getTime();
        return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(beforeD);
    }

    public static String getDate(Date time){
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return df.format(time);
    }
}
