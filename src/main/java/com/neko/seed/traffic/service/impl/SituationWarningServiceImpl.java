package com.neko.seed.traffic.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neko.seed.traffic.entity.SituationWarning;
import com.neko.seed.traffic.mapper.SituationWarningMapper;
import com.neko.seed.traffic.service.SituationWarningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Li Jinhui
 * @since 2021-06-10
 */
@Service
public class SituationWarningServiceImpl extends ServiceImpl<SituationWarningMapper, SituationWarning> implements SituationWarningService {

    @Autowired
    private SituationWarningMapper situationWarningMapper;


    public List<SituationWarning> listRecent50() {
        return situationWarningMapper.listRecently(50);
    }
}
