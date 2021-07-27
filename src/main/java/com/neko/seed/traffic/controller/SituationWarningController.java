package com.neko.seed.traffic.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.neko.seed.auth.annotation.AuthRequest;
import com.neko.seed.base.entity.Result;
import com.neko.seed.traffic.entity.CoreDataVO;
import com.neko.seed.traffic.entity.ServiceQuality;
import com.neko.seed.traffic.entity.ServiceTodo;
import com.neko.seed.traffic.entity.SituationWarning;
import com.neko.seed.traffic.service.ServiceQualityService;
import com.neko.seed.traffic.service.SituationWarningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Li Jinhui
 * @since 2021-06-10
 */
@RestController
@RequestMapping("/traffic/situation-warning")
public class SituationWarningController {

    @Autowired
    private SituationWarningService situationWarningServiceImpl;

    @GetMapping("/{name}")
    @AuthRequest
    public Result listAll(@PathVariable String name) {
        QueryWrapper<SituationWarning> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(SituationWarning::getRecRoadSectionName, name)
                .orderByDesc(SituationWarning::getRecTime);
        List<SituationWarning> list = situationWarningServiceImpl.list(wrapper);
        List<SituationWarning> result = list.stream().limit(50).collect(Collectors.toList());
        return new Result().success(list);
    }


    @PostMapping("/save")
    public Result save(@RequestBody List<SituationWarning> vos) {

        if(CollectionUtils.isEmpty(vos)){
            return new Result().fail("态势告警，参数为空", 500);
        }

        ArrayList<SituationWarning> successes = new ArrayList<>();
        ArrayList<SituationWarning> fails = new ArrayList<>();

        vos.stream().forEach(v -> {
            boolean success = situationWarningServiceImpl.save(v);
            if(success) {
                successes.add(v);
            }else {
                fails.add(v);
            }
        });
        HashMap<String, List<SituationWarning>> result = new HashMap<>();
        result.put("success", successes);
        result.put("fail", fails);


        return new Result().success(result);
    }

    @GetMapping("")
    @AuthRequest
    public Result listAll2(String roadname) {

        if (StringUtils.isEmpty(roadname)) {
            QueryWrapper<SituationWarning> wrapper = new QueryWrapper<>();
            wrapper.lambda().orderByDesc(SituationWarning::getRecTime);
            List<SituationWarning> list = situationWarningServiceImpl.list();
            List<SituationWarning> result = list.stream().limit(50).collect(Collectors.toList());
            return new Result().success(result);
        }

        QueryWrapper<SituationWarning> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(SituationWarning::getRecRoadSectionName, roadname)
                .orderByDesc(SituationWarning::getRecTime);
        List<SituationWarning> list = situationWarningServiceImpl.list(wrapper);
        List<SituationWarning> result = list.stream().limit(50).collect(Collectors.toList());

        return new Result().success(result);
    }
}
