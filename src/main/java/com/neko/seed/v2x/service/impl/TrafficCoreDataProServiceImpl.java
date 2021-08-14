package com.neko.seed.v2x.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.neko.seed.v2x.entity.ddo.TrafficCoreDataPro;
import com.neko.seed.v2x.mapper.TrafficCoreDataProMapper;
import com.neko.seed.v2x.service.ITrafficCoreDataProService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Li Jinhui
 * @since 2021-08-14
 */
@Service
public class TrafficCoreDataProServiceImpl extends ServiceImpl<TrafficCoreDataProMapper, TrafficCoreDataPro> implements ITrafficCoreDataProService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrafficCoreDataProServiceImpl.class);

    @Value("${v2x.service.latest.key.interval}")
    private Integer keyInterval;
    @Autowired
    private TrafficCoreDataProMapper trafficCoreDataProMapper;

    private Cache<String, List<TrafficCoreDataPro>> cache = CacheBuilder.newBuilder()
            .maximumSize(200)
            .expireAfterWrite(600, TimeUnit.SECONDS)
            .build();

    private List<TrafficCoreDataPro> getCacheValue(String key) throws Exception {
        return cache.get(key, new Callable<List<TrafficCoreDataPro>>() {
            public List<TrafficCoreDataPro> call() {
                List<TrafficCoreDataPro> latestDetails = trafficCoreDataProMapper.getLatestDetails();
                return latestDetails;
            }
        });
    }

    @Override
    public List<TrafficCoreDataPro> getAllRoadsLatestDeatail() {

        String key = String.valueOf(System.currentTimeMillis() / 1000 / this.keyInterval);

        List<TrafficCoreDataPro> cacheValue = null;
        try {
            cacheValue = this.getCacheValue(key);
        } catch (Exception e) {
            LOGGER.error("获取缓存失败", e);
        }
        return cacheValue;
    }


    @Override
    public TrafficCoreDataPro getLatestData(String roadname) {

        return trafficCoreDataProMapper.getLatestData(roadname);
    }

    @Override
    public TrafficCoreDataPro getCoreData(String roadname, Long timeId) {

        return trafficCoreDataProMapper.getSpecifiedDetail(roadname, timeId);
    }

    @Override
    public List<TrafficCoreDataPro> getRoadDetails(String roadname, Long start, Long end) {

        List<Long> ids = this.getRangeHourTime(start, end);
        if (CollectionUtils.isEmpty(ids)) {
            LOGGER.error("异常的时间范围, roadname={}, start={}, end={}", roadname, start, end);
            return null;
        }

        return trafficCoreDataProMapper.getSpecifiedDetailByRoad(roadname, ids);
    }

    @Override
    public List<TrafficCoreDataPro> getRoadDetailsOfDay(String roadname, String day) {


        return null;
    }

    public List<Long> getRangeHourTime(long start, Long end) {

        ArrayList<Long> ids = new ArrayList<>();
        long s = start / 3600;
        while ((s * 3600) < end) {
            ids.add(new Long(s * 3600));
            s += 1l;
        }
        return ids;
    }
}
