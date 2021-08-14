package com.neko.seed;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Date;

public class CommonTest {

    public static void main(String[] args) throws Exception {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String str = "20110823";
        Date dt = sdf.parse(str);
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(dt);
        rightNow.add(Calendar.YEAR, -1);//日期减1年
        rightNow.add(Calendar.MONTH, 3);//日期加3个月
        rightNow.add(Calendar.DAY_OF_YEAR, 10);//日期加10天
        Date dt1 = rightNow.getTime();
        String reStr = sdf.format(dt1);
//        System.out.println(reStr);


        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd HH:mm:ss");

        Date parse = sdf2.parse("20210814 10:00:00");
        System.out.println(parse.getTime());
        System.out.println(parse.getTime() % 3600);


//获取秒数
        Long second = LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
        System.out.println("second" + second);
//获取毫秒数
        Long milliSecond = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();


    }
}
