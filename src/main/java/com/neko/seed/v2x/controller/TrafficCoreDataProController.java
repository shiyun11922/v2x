package com.neko.seed.v2x.controller;


import com.neko.seed.base.entity.Result;
import com.neko.seed.traffic.entity.TopRateVO;
import com.neko.seed.v2x.entity.ddo.TrafficCoreDataPro;
import com.neko.seed.v2x.service.ITrafficCoreDataProService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Li Jinhui
 * @since 2021-08-14
 */
@RestController
@RequestMapping("/v2x/core")
public class TrafficCoreDataProController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrafficCoreDataProController.class);

    @Autowired
    private ITrafficCoreDataProService trafficCoreDataProServiceImpl;


    @GetMapping("")
    public Result getLatestDetail(String roadname) {

        TrafficCoreDataPro latestData = trafficCoreDataProServiceImpl.getLatestData(roadname);
        return new Result().success(latestData);
    }

    @PostMapping("/save")
    public Result saveDetail() {

        return new Result().success();
    }


    /**
     * 参数是十位的时间戳
     *
     * @param roadname
     * @param startTimeStamp
     * @param endTimeStamp
     * @return
     */
    @GetMapping("/all")
    public Result getDetails(String roadname, Long startTimeStamp, Long endTimeStamp) {

        if (startTimeStamp != null && startTimeStamp.toString().length() > 10) {
            startTimeStamp = Long.valueOf(startTimeStamp.toString().substring(0, 11));
        }
        if (endTimeStamp != null && endTimeStamp.toString().length() > 10) {
            endTimeStamp = Long.valueOf(endTimeStamp.toString().substring(0, 11));
        }

        LOGGER.debug("获取路段数据,road={}, startTime={}, endTime={}", roadname, startTimeStamp, endTimeStamp);

        if (Objects.isNull(startTimeStamp) && Objects.isNull(endTimeStamp)) {
            Date current = new Date();
            long currentTime = current.getTime() / 1000;
            Calendar now = Calendar.getInstance();
            now.setTime(current);
            now.add(Calendar.DAY_OF_YEAR, -1);//日期加10天
            startTimeStamp = (now.getTime().getTime()) / 1000;
            endTimeStamp = currentTime;
        }

        List<TrafficCoreDataPro> roadDetails = trafficCoreDataProServiceImpl.getRoadDetails(roadname, startTimeStamp, endTimeStamp);

        return new Result().success(roadDetails);
    }


    /**
     * todo  优化toprate 接口
     *
     * @return
     */
    @GetMapping("/top")
    public Result top() {

        List<TrafficCoreDataPro> trafficCoreDataPros = trafficCoreDataProServiceImpl.topRate();

        ArrayList<TopRateVO> topRateVOS = new ArrayList<>();
        trafficCoreDataPros.stream().forEach(c -> {

            TopRateVO v = new TopRateVO();
            v.setRoadSectionName(c.getRoadSectionName());
            v.setNumsBlueCar(c.getNumsBlueCar().intValue());
            v.setNumsYellCar(c.getNumsYellCar().intValue());
            v.setNumsTotalCar(c.getTotalCars().intValue());
            topRateVOS.add(v);
        });

        List<TopRateVO> collects = topRateVOS.stream().sorted((o1, o2) -> {
            return o2.getNumsTotalCar() - o1.getNumsTotalCar();
        }).collect(Collectors.toList());

        return new Result().success(collects);

    }


}
