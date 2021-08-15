package com.neko.seed.v2x.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.neko.seed.v2x.entity.ddo.TrafficCoreDataPro;
import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author Li Jinhui
 * @since 2021-08-14
 */
public interface TrafficCoreDataProMapper extends BaseMapper<TrafficCoreDataPro> {

    TrafficCoreDataPro getLatestData(String roadname);

    List<TrafficCoreDataPro> getLatestDetails();

    List<TrafficCoreDataPro> getLatestDetailsPro();

    List<TrafficCoreDataPro> getSpecifiedDetailByRoad(String roadname, List<Long> list);

    TrafficCoreDataPro getSpecifiedDetail(String roadname, Long id);

    int generateHourDataBySepcificDate(Long startDate,Long endDate);
}
