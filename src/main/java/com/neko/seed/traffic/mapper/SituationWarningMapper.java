package com.neko.seed.traffic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.neko.seed.traffic.entity.SituationWarning;
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
public interface SituationWarningMapper extends BaseMapper<SituationWarning> {

    List<SituationWarning> listRecently(Integer nums);
}
