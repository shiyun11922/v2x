package com.neko.seed.traffic.controller;


import com.neko.seed.auth.annotation.AuthRequest;
import com.neko.seed.base.entity.Result;
import com.neko.seed.traffic.entity.CoreData;
import com.neko.seed.traffic.entity.CoreDataVO;
import com.neko.seed.traffic.entity.RoadName;
import com.neko.seed.traffic.entity.TopRateVO;
import com.neko.seed.traffic.service.CoreDataService;
import com.neko.seed.traffic.service.RoadNameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Li Jinhui
 * @since 2021-06-10
 */
@RestController
@RequestMapping("/traffic/core-data")
public class CoreDataController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CoreDataController.class);

    @Autowired
    private CoreDataService coreDataServiceImpl;

    @Autowired
    private RoadNameService roadNameServiceImpl;


    @GetMapping("/{name}")
    @AuthRequest
    public Result hello(@PathVariable String name) {
        Objects.requireNonNull(name, "路段明细明细信息，路段名称不能为空");

        CoreData one = coreDataServiceImpl.getDataByName(name);
        return new Result().success(one);
    }

    @GetMapping("")
    @AuthRequest
    public Result hello2(String roadname) {
        Objects.requireNonNull(roadname, "路段明细明细信息，路段名称不能为空");
        CoreData one = coreDataServiceImpl.getDataByName(roadname);
        return new Result().success(one);
    }

    @PostMapping("/save")
    public Result insert(@RequestBody List<CoreDataVO> dataVOS) {
        Map<String, List<CoreDataVO>> result = coreDataServiceImpl.save(dataVOS);
        return new Result().success(result);
    }

    @PostMapping("/predictSave")
    public Result predictSave(@RequestBody List<CoreDataVO> dataVOS) {
        Map<String, List<CoreDataVO>> result = coreDataServiceImpl.save(dataVOS);
        return new Result().success(result);
    }

    /**
     * 登陆接口
     */
    @GetMapping("/{name}/all")
    @AuthRequest
    public Result all(@PathVariable String name) {
        Objects.requireNonNull(name, "路段明细明细信息，路段名称不能为空");
        List<CoreData> dataListByName = coreDataServiceImpl.getDataListByName(name);
        return new Result().success(dataListByName);
    }

    @GetMapping("/all")
    @AuthRequest
    public Result all2(String roadname, String startTimeStamp, String endTimeStamp) {
        Objects.requireNonNull(roadname, "路段明细明细信息，路段名称不能为空");
        LOGGER.info("startTimeStamp={}, endTimeStamp={}", startTimeStamp, endTimeStamp);
        if (StringUtils.isEmpty(startTimeStamp) || StringUtils.isEmpty(endTimeStamp)) {
            return new Result().success(coreDataServiceImpl.getDataListByName(roadname));
        }
        Long start = Long.parseLong(startTimeStamp);
        Long end = Long.parseLong(endTimeStamp);
        List<CoreData> dataListByName = coreDataServiceImpl.getDataListByName(roadname, start, end);
        return new Result().success(dataListByName);
    }


    /**
     * todo  优化toprate 接口
     * @return
     */
    @GetMapping("/topRate")
    public Result topRate() {

        List<CoreData> coreData = coreDataServiceImpl.topRate2();

        ArrayList<TopRateVO> topRateVOS = new ArrayList<>();
        coreData.stream().forEach(c -> {

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

    @GetMapping("/roads")
    @AuthRequest
    public Result roadNames() {
        List<RoadName> roads = roadNameServiceImpl.roads();
        List<String> roadNames = new ArrayList<String>();
        roads.stream()
                .forEach(r -> {
                    roadNames.add(r.getRoadSectionName());
                });

        return new Result().success(roadNames);
    }

    @PostMapping("/cars-rate")
    public Result cars() {

        return new Result().success("todo");
    }



}
