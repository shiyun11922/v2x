package com.neko.seed.traffic.mapper;

import com.neko.seed.traffic.entity.ServiceTodo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Li Jinhui
 * @since 2021-06-11
 */
public interface ServiceTodoMapper extends BaseMapper<ServiceTodo> {

    ServiceTodo getLeastOne(String roadName);
}
