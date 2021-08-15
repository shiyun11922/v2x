package com.neko.seed.v2x.schedule;

import com.neko.seed.v2x.service.ITrafficCoreDataProService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * 定时生成流量数据
 */
@Configuration
//1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling
public class GenerateTrafficeDataSchedule {

    @Autowired
    private ITrafficCoreDataProService trafficCoreDataProServiceImpl;

    //是否生成的时候给一个整点的字段用来展示数据?
    @Scheduled( cron="0 0 0/1 * * ? *")
    private void generateHourData() {
        LocalDateTime now = LocalDateTime.now();
        now = now.withSecond(0).withMinute(0);//格式化时间
        Long startDate=now.toEpochSecond(ZoneOffset.of("+8"));
        now=now.plusHours(1);
        Long endDate=now.toEpochSecond(ZoneOffset.of("+8"));
        trafficCoreDataProServiceImpl.generateHourData(startDate,endDate);
    }
}
