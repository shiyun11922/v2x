package com.neko.seed.v2x.controller;


import com.neko.seed.base.entity.Result;
import com.neko.seed.traffic.entity.ServiceQuality;
import com.neko.seed.traffic.entity.ServiceTodo;
import com.neko.seed.traffic.entity.SituationWarning;
import com.neko.seed.traffic.entity.TopRateVO;
import com.neko.seed.v2x.entity.ddo.TrafficCoreDataPro;
import com.neko.seed.v2x.entity.vo.TrafficCoreDataProVO;
import com.neko.seed.v2x.entity.vo.TrafficPredictVO;
import com.neko.seed.v2x.service.ITrafficCoreDataProService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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
    public Result saveDetail(@RequestBody @Valid TrafficCoreDataProVO requestVO) {

        if (Objects.isNull(requestVO.getRecTime()) || StringUtils.isEmpty(requestVO.getRoadSectionName())) {
            LOGGER.info("invalid save request, vo={}", requestVO);
            return new Result().fail("参数错误", 400);
        }

        LocalDateTime recTime = requestVO.getRecTime();
        long id = recTime.toEpochSecond(ZoneOffset.of("+8"));

        TrafficCoreDataPro coreData = trafficCoreDataProServiceImpl.getCoreData(requestVO.getRoadSectionName(), id);

        if(!Objects.isNull(coreData)){
            return new Result().fail("此记录已存在",400);
        }

        TrafficCoreDataPro rcdp = new TrafficCoreDataPro();
        BeanUtils.copyProperties(requestVO, rcdp);
        rcdp.setId(id);

        boolean save = trafficCoreDataProServiceImpl.save(rcdp);
        if(save){
            return new Result().success("保存成功");
        }

        return new Result().fail("保存异常", 400);
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
    public Result top() throws Exception {

        List<TrafficCoreDataPro> trafficCoreDataPros = trafficCoreDataProServiceImpl.getAllRoadsLatestDeatail();

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

    @GetMapping("/todo")
    public Result todo() throws Exception {

        List<TrafficCoreDataPro> trafficCoreDataPros = trafficCoreDataProServiceImpl.getAllRoadsLatestDeatail();

        ArrayList<ServiceTodo> serviceTodos = new ArrayList<>();
        trafficCoreDataPros.stream().forEach(tcd -> {

            if (!StringUtils.isEmpty(tcd.getMeausreInfo())) {
                serviceTodos.add(new ServiceTodo(tcd.getRoadSectionName(), tcd.getMeausreInfo(), tcd.getRecTime()));
            }
        });
        return new Result().success(serviceTodos);
    }


    @GetMapping("/quality")
    public Result quality() throws Exception {

        List<TrafficCoreDataPro> trafficCoreDataPros = trafficCoreDataProServiceImpl.getAllRoadsLatestDeatail();

        ArrayList<ServiceQuality> serviceQualities = new ArrayList<>();
        trafficCoreDataPros.stream().forEach(tcd -> {

            if (!StringUtils.isEmpty(tcd.getServiceLevel())) {
                serviceQualities.add(new ServiceQuality(tcd.getRoadSectionName(), tcd.getMeausreInfo(), tcd.getRecTime()));
            }
        });
        return new Result().success(serviceQualities);
    }

    @GetMapping("/warning")
    public Result warning() throws Exception {

        List<TrafficCoreDataPro> trafficCoreDataPros = trafficCoreDataProServiceImpl.getAllRoadsLatestDeatail();

        ArrayList<SituationWarning> situationWarnings = new ArrayList<>();
        trafficCoreDataPros.stream().forEach(tcd -> {

            if (!Objects.isNull(tcd.getSituationPrediction())) {
                situationWarnings.add(new SituationWarning(tcd.getRoadSectionName(), tcd.getSituationPrediction(), tcd.getRecTime()));
            }
        });
        return new Result().success(situationWarnings);
    }

    @GetMapping("/predict")
    public Result predict(String roadname) {

        Date current = new Date();
        long currentTime = current.getTime() / 1000;
        Calendar now = Calendar.getInstance();
        now.setTime(current);
        now.add(Calendar.DAY_OF_YEAR, -1);//
        long startTimeStamp = (now.getTime().getTime()) / 1000;
        long endTimeStamp = currentTime;

        List<TrafficCoreDataPro> roadDetails = trafficCoreDataProServiceImpl.getRoadDetails(roadname, startTimeStamp, endTimeStamp);

        Map<String, List<TrafficPredictVO>> resultVO = new HashMap<>();


        List<TrafficPredictVO> tpReal = new ArrayList<>();
        List<TrafficPredictVO> tpPredict = new ArrayList<>();

        resultVO.put("real", tpReal);
        resultVO.put("predict", tpPredict);

        if (CollectionUtils.isEmpty(roadDetails)) {
            return new Result().success(resultVO);
        }

        roadDetails.stream().forEach(rd -> {
            int currentHour = rd.getRecTime().getHour();
            tpReal.add(new TrafficPredictVO(rd.getTotalCars(), currentHour));
            tpPredict.add(new TrafficPredictVO(rd.getPredictTotalCars(), currentHour));
        });

        return new Result().success(resultVO);
    }


}
