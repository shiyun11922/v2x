package com.neko.seed.traffic.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.neko.seed.traffic.entity.Predict;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Li Jinhui
 * @since 2021-07-27
 */
public interface IPredictService extends IService<Predict> {

    List<Predict> top2();
}
