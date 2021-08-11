package com.neko.seed.traffic.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neko.seed.traffic.entity.CoreData;
import com.neko.seed.traffic.entity.CoreDataVO;
import com.neko.seed.traffic.entity.ServiceTodo;
import com.neko.seed.traffic.entity.SituationWarning;
import com.neko.seed.traffic.mapper.CoreDataMapper;
import com.neko.seed.traffic.mapper.ServiceTodoMapper;
import com.neko.seed.traffic.mapper.SituationWarningMapper;
import com.neko.seed.traffic.service.CoreDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Li Jinhui
 * @since 2021-06-10
 */
@Service
public class CoreDataServiceImpl extends ServiceImpl<CoreDataMapper, CoreData> implements CoreDataService {

    private static final Logger LOG = LoggerFactory.getLogger(CoreDataServiceImpl.class);
    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:00:00");
    private static final SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:00:00");


    @Autowired
    private CoreDataMapper coreDataMapper;

    @Autowired
    private SituationWarningMapper situationWarningMapper;

    @Autowired
    private ServiceTodoMapper serviceTodoMapper;

    @Override
    public CoreData getDataByName(String name) {
        // 使用SpringValidation校验数据
        QueryWrapper<CoreData> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(CoreData::getRoadSectionName, name)
                .orderByDesc(CoreData::getRecTime)
                .last("limit 1");

        List<CoreData> coreData = coreDataMapper.selectList(wrapper);
        return coreData.isEmpty() ? null : coreData.get(0);
    }

    @Override
    public Map<String, List<CoreDataVO>> save(List<CoreDataVO> coreDataVOS) {

        ArrayList<CoreDataVO> successData = new ArrayList<>();
        ArrayList<CoreDataVO> failedData = new ArrayList<>();
        coreDataVOS.stream().forEach(cd -> {
            CoreData d = new CoreData();

            d.setRoadSectionName(cd.getRoadSectionName());
            d.setNumsYellCar(cd.getNumsYellCar());
            d.setNumsBlueCar(cd.getNumsBlueCar());
            d.setAvgHeadway(cd.getAvgHeadway());
            d.setAvgSpeed(cd.getAvgSpeed());
            d.setCarbonMonoxideEmissions(cd.getCarbonMonoxideEmissions());
            d.setCartMixRate(cd.getCartMixRate());
            d.setCongestionLength(cd.getCongestionLength());
            d.setForecastTraffic(cd.getForecastTraffic());
            d.setInhalableEmissions(cd.getInhalableEmissions());
            d.setMeausreInfo(cd.getMeausreInfo());
            d.setNoxEmissions(cd.getNoxEmissions());
            d.setServiceLevel(cd.getServiceLevel());
            d.setTotal(cd.getTotal());
            d.setSituationPrediction(cd.getSituationPrediction());

            d.setRecTime(cd.getRecTime());
            d.setV2Xcls(cd.getV2xcls());
            d.setV2xAvgHeadway(cd.getV2xAvgHeadway());
            d.setV2xAvgSpeed(cd.getV2xAvgSpeed());
            d.setVc(cd.getVc());
            d.setV2xDrivenDistance(cd.getV2xDrivenDistance());
            d.setV2xNoxEmissions(cd.getV2xNoxEmissions());
            d.setFormatedTime(formatter2.format(cd.getRecTime()));

            int success = 0;
            try {
                success = coreDataMapper.insert(d);
            } catch (Exception e) {
                LOG.error("写入coredata失败", e);
            }
            if (success == 1) {
                successData.add(cd);
            } else {
                failedData.add(cd);
            }

        });

        HashMap<String, List<CoreDataVO>> result = new HashMap<>();
        result.put("success", successData);
        result.put("fail", failedData);

        return result;
    }

    @Override
    public List<CoreData> getDataListByName(String name) {

        Map<String, CoreData> map = new TreeMap<String, CoreData>((k1, k2) -> {
            return k1.compareTo(k2);
        });

        Set<String> recent24Hours = this.genDataSet();

        QueryWrapper<CoreData> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(CoreData::getRoadSectionName, name)
                .orderByDesc(CoreData::getRecTime);
        List<CoreData> coreDatas = coreDataMapper.selectList(wrapper);

        coreDatas.stream()
                .forEach(
                        c -> {
                            String f = formatter.format(c.getRecTime());
                            if (recent24Hours.contains(f) && !map.containsKey(f)) {
                                c.setFormatedTime(f);
                                map.put(f, c);

                            }
                        }
                );

        recent24Hours.stream().forEach(
                r -> {
                    if (!map.containsKey(r)) {
                        CoreData nullData = new CoreData();
                        nullData.setFormatedTime(r);
                        map.put(r, nullData);
                    }
                }

        );
        ServiceTodo leastOne = serviceTodoMapper.getLeastOne(name);
        ArrayList<CoreData> coreDatass = new ArrayList<>();
        map.values().stream().forEach(v -> {

            if (leastOne != null && !StringUtils.isEmpty(leastOne.getNeedTodo())) {
                v.setMeausreInfo(leastOne.getNeedTodo());
            }
            coreDatass.add(v);
        });
        return coreDatass;
    }


    public List<CoreData> getDataListByName(String name, Long startTimeStamp, Long endTimeStamp) {

        if (startTimeStamp == null || endTimeStamp == null) {
            return this.getDataListByName(name);
        }

        Map<String, CoreData> map = new TreeMap<String, CoreData>((k1, k2) -> {
            return k1.compareTo(k2);
        });

        Set<String> rangeHours = this.genTimeRangeSet(startTimeStamp, endTimeStamp);

        QueryWrapper<CoreData> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(CoreData::getRoadSectionName, name)
                .orderByDesc(CoreData::getRecTime);
        List<CoreData> coreDatas = coreDataMapper.selectList(wrapper);

        coreDatas.stream()
                .forEach(
                        c -> {
                            String f = formatter.format(c.getRecTime());
                            if (rangeHours.contains(f) && !map.containsKey(f)) {
                                c.setFormatedTime(f);
                                map.put(f, c);

                            }
                        }
                );

        rangeHours.stream().forEach(
                r -> {
                    if (!map.containsKey(r)) {
                        CoreData nullData = new CoreData();
                        nullData.setFormatedTime(r);
                        map.put(r, nullData);
                    }
                }

        );

        ServiceTodo leastOne = serviceTodoMapper.getLeastOne(name);


        ArrayList<CoreData> coreDatass = new ArrayList<>();
        map.values().stream().forEach(v -> {

            if (leastOne != null && !StringUtils.isEmpty(leastOne.getNeedTodo())) {
                v.setMeausreInfo(leastOne.getNeedTodo());
            }
            coreDatass.add(v);
        });
        return coreDatass;
    }

    public Set<String> genDataSet() {

        Set<String> result = new HashSet<>();
        for (int i = 0; i < 24; i++) {
            Date dt = new Date();
            Calendar rightNow = Calendar.getInstance();
            rightNow.setTime(dt);
            rightNow.add(Calendar.HOUR, -i);
            Date dt1 = rightNow.getTime();
            String oneHoursAgoTime = formatter.format(dt1);
            result.add(oneHoursAgoTime);
        }
        return result;
    }

    public Set<String> genTimeRangeSet(Long startTimeStamp, Long endTimeStamp) {

        Long start = startTimeStamp;
        Long end = endTimeStamp;
        Set<String> result = new HashSet<>();

        while (start <= end) {
            long l = (start / (60 * 60 * 1000)) * 60 * 60 * 1000;
            Date d = new Date(l);
            String f = formatter.format(d);
            result.add(f);
            start = l + 60 * 60 * 1000;
        }
        return result;
    }

    @Override
    public List<CoreData> topRate() {
        QueryWrapper<CoreData> wrapper = new QueryWrapper<>();

        wrapper.select("distinct road_section_name", "nums_blue_car", "nums_yell_car", "nums_blue_car+nums_yell_car total_car")
                .isNotNull("road_section_name")
                .orderByDesc("total_car");
        List<Map<String, Object>> maps = coreDataMapper.selectMaps(wrapper);


        List<CoreData> result = new ArrayList<CoreData>();
        HashSet<String> keys = new HashSet<>();
        maps.stream().forEach(m -> {
                    CoreData coreData = new CoreData();
                    coreData.setRoadSectionName((String) m.get("road_section_name"));
                    coreData.setNumsBlueCar((Double) m.get("nums_blue_car"));
                    coreData.setNumsYellCar((Double) m.get("nums_yell_car"));
                    coreData.setTotalCars((Double) m.get("total_car"));
                    result.add(coreData);
                }
        );

        List<CoreData> rs = result.stream().limit(20).collect(Collectors.toList());
        return rs;

    }

    public List<CoreData> topRate2() {

        List<CoreData> coreData = coreDataMapper.selectTopRate();

        return coreData;

    }

}
