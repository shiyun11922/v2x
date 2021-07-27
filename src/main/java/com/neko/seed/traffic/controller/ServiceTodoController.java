package com.neko.seed.traffic.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.neko.seed.auth.annotation.AuthRequest;
import com.neko.seed.base.entity.Result;
import com.neko.seed.traffic.entity.ServiceQuality;
import com.neko.seed.traffic.entity.ServiceTodo;
import com.neko.seed.traffic.entity.SituationWarning;
import com.neko.seed.traffic.service.IServiceTodoService;
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
 * @since 2021-06-11
 */
@RestController
@RequestMapping("/traffic/service-todo")
public class ServiceTodoController {

    @Autowired
    private IServiceTodoService serviceTodoServiceImpl;


    @GetMapping("/{name}")
    @AuthRequest
    public Result listAll(@PathVariable String name) {
        QueryWrapper<ServiceTodo> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(ServiceTodo::getRecRoadSectionName, name)
                .orderByDesc(ServiceTodo::getRecTime);
        List<ServiceTodo> list = serviceTodoServiceImpl.list(wrapper);
        List<ServiceTodo> result = list.stream().limit(50).collect(Collectors.toList());
        return new Result().success(result);
    }

    @GetMapping("")
    @AuthRequest
    public Result listAll2(String roadname) {

        if (StringUtils.isEmpty(roadname)) {
            QueryWrapper<ServiceTodo> wrapper = new QueryWrapper<>();
            wrapper.lambda().orderByDesc(ServiceTodo::getRecTime);
            List<ServiceTodo> list = serviceTodoServiceImpl.list();
            return new Result().success(list);
        }
        QueryWrapper<ServiceTodo> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(ServiceTodo::getRecRoadSectionName, roadname)
                .orderByDesc(ServiceTodo::getRecTime);
        List<ServiceTodo> list = serviceTodoServiceImpl.list(wrapper);

        List<ServiceTodo> result = list.stream().limit(50).collect(Collectors.toList());

        return new Result().success(result);
    }

    @PostMapping("/save")
    public Result save(@RequestBody List<ServiceTodo> vos) {

        if(CollectionUtils.isEmpty(vos)){
            return new Result().fail("态势告警，参数为空", 500);
        }

        ArrayList<ServiceTodo> successes = new ArrayList<>();
        ArrayList<ServiceTodo> fails = new ArrayList<>();

        vos.stream().forEach(v -> {
            boolean success = serviceTodoServiceImpl.save(v);
            if(success) {
                successes.add(v);
            }else {
                fails.add(v);
            }
        });


        HashMap<String, List<ServiceTodo>> result = new HashMap<>();
        result.put("success", successes);
        result.put("fail", fails);


        return new Result().success(result);
    }
}
