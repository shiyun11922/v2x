package com.neko.seed.v2x.service;

import com.neko.seed.traffic.entity.CoreData;
import com.neko.seed.v2x.entity.ddo.TrafficCoreDataPro;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Li Jinhui
 * @since 2021-08-14
 */
public interface ITrafficCoreDataProService extends IService<TrafficCoreDataPro> {

    List<TrafficCoreDataPro> getAllRoadsLatestDeatail();

    TrafficCoreDataPro getLatestData(String roadname);

    TrafficCoreDataPro getCoreData(String roadname, Long timeId);

    List<TrafficCoreDataPro> getRoadDetails(String roadname, Long start, Long end);

    List<TrafficCoreDataPro> getRoadDetailsOfDay(String roadname, String day);

}
