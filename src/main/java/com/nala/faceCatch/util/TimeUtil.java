package com.nala.faceCatch.util;

import java.util.Date;

/**
 * @author: heshangqiu
 * Created Date:  2018/3/14
 */
public class TimeUtil {

    /**
     * 一种说法：时间戳从1970年1月1日00:00:00至今的秒数,即当前的时间
     * 另一种说法：时间戳,单位秒，即unix-timestamp
     * @return
     */
    public static String timeStamp(){
        return String.valueOf(System.currentTimeMillis() / 1000);
    }

    /**
     * 时间戳
     * @param date
     * @return
     */
    public static String timeStamp(Date date){
        return String.valueOf(date.getTime() / 1000);
    }
}
