package com.neko.seed.traffic.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neko.seed.traffic.entity.Predict;
import com.neko.seed.traffic.mapper.PredictMapper;
import com.neko.seed.traffic.service.IPredictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Li Jinhui
 * @since 2021-07-27
 */
@Service
public class PredictServiceImpl extends ServiceImpl<PredictMapper, Predict> implements IPredictService {

    @Autowired
    private PredictMapper predictMapper;

    public List<Predict> top2() {

        List<Predict> predicts = predictMapper.top22();

        List<Predict> result = predicts.stream().sorted((p1, p2) -> {
            return p2.getNums() - p1.getNums();
        }).limit(2).collect(Collectors.toList());

        return result;
    }
}
