package com.neko.seed.traffic.service;

import com.neko.seed.traffic.entity.ServiceTodo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Li Jinhui
 * @since 2021-06-11
 */
public interface IServiceTodoService extends IService<ServiceTodo> {

    public ServiceTodo getLeastOne(String name);
}
