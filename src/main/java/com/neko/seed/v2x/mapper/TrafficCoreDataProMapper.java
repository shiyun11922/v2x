package com.neko.seed.v2x.mapper;

import com.neko.seed.v2x.entity.ddo.TrafficCoreDataPro;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Li Jinhui
 * @since 2021-08-14
 */
public interface TrafficCoreDataProMapper extends BaseMapper<TrafficCoreDataPro> {

    List<TrafficCoreDataPro> getLatestDetails();

    List<TrafficCoreDataPro> getLatestDetailsPro();

    TrafficCoreDataPro getLatestData(String roadname);
}
