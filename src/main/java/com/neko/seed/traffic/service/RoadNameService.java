package com.neko.seed.traffic.service;

import com.neko.seed.traffic.entity.RoadName;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Li Jinhui
 * @since 2021-06-10
 */
public interface RoadNameService extends IService<RoadName> {


    List<RoadName> roads();
}
