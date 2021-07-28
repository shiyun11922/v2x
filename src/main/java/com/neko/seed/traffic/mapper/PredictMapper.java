package com.neko.seed.traffic.mapper;

import com.neko.seed.traffic.entity.Predict;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Li Jinhui
 * @since 2021-07-27
 */
public interface PredictMapper extends BaseMapper<Predict> {

    List<Predict> top22();
}
