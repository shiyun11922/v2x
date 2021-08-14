package com.neko.seed.v2x.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neko.seed.v2x.entity.ddo.TrafficCoreDataPro;
import com.neko.seed.v2x.mapper.TrafficCoreDataProMapper;
import com.neko.seed.v2x.service.ITrafficCoreDataProService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
