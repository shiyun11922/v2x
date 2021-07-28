package com.neko.seed.traffic.controller;


import com.neko.seed.base.entity.Result;
import com.neko.seed.traffic.entity.Predict;
import com.neko.seed.traffic.service.IPredictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Li Jinhui
 * @since 2021-07-27
 */
@RestController
@RequestMapping("/traffic/predict")
public class PredictController {

    @Autowired
    private IPredictService predictServiceImpl;

    @PostMapping("/save")
    public Result predictSave(@RequestBody List<Predict> dataVOS) {

        ArrayList<Predict> success = new ArrayList<>();
        ArrayList<Predict> failed = new ArrayList<>();


        if (dataVOS != null) {

            dataVOS.stream().forEach(d -> {
                boolean save = predictServiceImpl.save(d);
                if (save) {
                    success.add(d);
                } else {
                    failed.add(d);
                }
            });
        }

        HashMap<String, List<Predict>> result = new HashMap<>();
        result.put("success", success);
        result.put("failed", failed);
        return new Result().success(result);
    }

    @GetMapping("/top2")
    public Result top() {

        List<Predict> predicts = predictServiceImpl.top2();

        return new Result().success(predicts);
    }
}
