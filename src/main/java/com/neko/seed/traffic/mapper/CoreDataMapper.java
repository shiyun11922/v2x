package com.neko.seed.traffic.mapper;

import com.neko.seed.traffic.entity.CoreData;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author Li Jinhui
 * @since 2021-06-10
 */
@Mapper
public interface CoreDataMapper extends BaseMapper<CoreData> {

    List<CoreData> selectTopRate();


    List<CoreData> getLatestCoreDataByName();
}
