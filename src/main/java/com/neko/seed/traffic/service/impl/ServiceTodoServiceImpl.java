package com.neko.seed.traffic.service.impl;

import com.neko.seed.traffic.entity.ServiceTodo;
import com.neko.seed.traffic.mapper.ServiceTodoMapper;
import com.neko.seed.traffic.service.IServiceTodoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Li Jinhui
 * @since 2021-06-11
 */
@Service
public class ServiceTodoServiceImpl extends ServiceImpl<ServiceTodoMapper, ServiceTodo> implements IServiceTodoService {

}
