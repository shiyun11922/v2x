package com.neko.seed.v2x.controller;


import com.neko.seed.base.entity.Result;
import com.neko.seed.traffic.entity.CoreData;
import com.neko.seed.traffic.entity.TopRateVO;
import com.neko.seed.v2x.entity.ddo.TrafficCoreDataPro;
import com.neko.seed.v2x.service.ITrafficCoreDataProService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
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

    @Autowired
    private ITrafficCoreDataProService trafficCoreDataProServiceImpl;

    /**
     * http://ip:port/v1/traffic/traffic-core-data-pro?roadname=aaa
     *
     * @param roadname
     * @return
     */
    @GetMapping("")
    public Result getLatestDetail(String roadname) {

        TrafficCoreDataPro latestData = trafficCoreDataProServiceImpl.getLatestData(roadname);
        return new Result().success(latestData);
    }

    @PostMapping("/save")
    public Result saveDetail() {

        return new Result().success();
    }

    @GetMapping("/all")
    public Result getDetails(String roadname, Long startTimeStamp, Long endTimeStamp) {



        return new Result().success();
    }


    /**
     * todo  优化toprate 接口
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
