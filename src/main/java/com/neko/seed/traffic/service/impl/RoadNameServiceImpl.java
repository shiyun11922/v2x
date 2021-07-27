package com.neko.seed.traffic.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.neko.seed.traffic.entity.RoadName;
import com.neko.seed.traffic.mapper.RoadNameMapper;
import com.neko.seed.traffic.service.RoadNameService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Li Jinhui
 * @since 2021-06-10
 */
@Service
public class RoadNameServiceImpl extends ServiceImpl<RoadNameMapper, RoadName> implements RoadNameService {

    @Autowired
    private RoadNameMapper roadNameMapper;

    @Override
    public List<RoadName> roads() {

        QueryWrapper<RoadName> wrapper = new QueryWrapper<>();
        wrapper.lambda();

        List<RoadName> roadNames = roadNameMapper.selectList(wrapper);
        return roadNames;
    }
}
