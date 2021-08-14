package com.neko.seed.v2x.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neko.seed.v2x.entity.ddo.TrafficCoreDataPro;
import com.neko.seed.v2x.mapper.TrafficCoreDataProMapper;
import com.neko.seed.v2x.service.ITrafficCoreDataProService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    @Autowired
    private TrafficCoreDataProMapper trafficCoreDataProMapper;

    @Override
    public List<TrafficCoreDataPro> topRate() {

        return trafficCoreDataProMapper.getLatestDetails();
    }

    @Override
    public TrafficCoreDataPro getLatestData(String roadname) {

        return trafficCoreDataProMapper.getLatestData(roadname);
    }

    @Override
    public List<TrafficCoreDataPro> getRoadDetails(String roadname, Long start, Long end) {

        List<Long> ids = this.getRangeHourTime(start, end);
        if(CollectionUtils.isEmpty(ids)){
            LOGGER.error("异常的时间范围, roadname={}, start={}, end={}", roadname, start, end);
            return null;
        }

        return trafficCoreDataProMapper.getSpecifiedDetailByRoad(roadname, ids);
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
