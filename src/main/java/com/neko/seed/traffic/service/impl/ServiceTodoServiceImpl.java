package com.neko.seed.traffic.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neko.seed.traffic.entity.ServiceTodo;
import com.neko.seed.traffic.mapper.ServiceTodoMapper;
import com.neko.seed.traffic.service.IServiceTodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Li Jinhui
 * @since 2021-06-11
 */
@Service
public class ServiceTodoServiceImpl extends ServiceImpl<ServiceTodoMapper, ServiceTodo> implements IServiceTodoService {

    @Autowired
    private ServiceTodoMapper serviceTodoMapper;

    @Override
    public ServiceTodo getLeastOne(String name) {

        ServiceTodo leastOne = serviceTodoMapper.getLeastOne(name);

        return leastOne;
    }
}
