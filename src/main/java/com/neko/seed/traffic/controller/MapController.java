package com.neko.seed.traffic.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.neko.seed.auth.annotation.AuthRequest;
import com.neko.seed.base.entity.Result;
import com.neko.seed.traffic.entity.EdgeEntity;
import com.neko.seed.traffic.entity.MapEntity;
import com.neko.seed.v2x.entity.ddo.TrafficCoreDataPro;
import com.neko.seed.v2x.service.ITrafficCoreDataProService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

@RestController
@RequestMapping("/traffic/map")
public class MapController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MapController.class);

    @Autowired
    private ITrafficCoreDataProService trafficCoreDataProServiceImpl;

    private ArrayList<MapEntity> mapEntities = new ArrayList<>();
    private ArrayList<EdgeEntity> edgeEntities = new ArrayList<>();


    @PostConstruct
    public void init() {

        FileSystemResource roadResource = new FileSystemResource("input/g92_road.json");
        File file = roadResource.getFile();
        String jsonData = this.jsonRead(file);
        JSONArray objects = JSONObject.parseArray(jsonData);
        for (int i = 0; i < objects.size(); i++) {
            JSONObject object = objects.getJSONObject(i);
            MapEntity m = JSONObject.toJavaObject(object, MapEntity.class);
            mapEntities.add(m);
        }

        FileSystemResource edgeResource = new FileSystemResource("input/edges.json");
        File edgeFile = edgeResource.getFile();
        String edgeJsonData = this.jsonRead(edgeFile);
        JSONArray edgeObjects = JSONObject.parseArray(edgeJsonData);
        for (int i = 0; i < edgeObjects.size(); i++) {
            JSONObject object = edgeObjects.getJSONObject(i);
            EdgeEntity m = JSONObject.toJavaObject(object, EdgeEntity.class);
            edgeEntities.add(m);
        }
    }


    /*
    todo
    增加flow字段
     */
    @GetMapping("")
    @AuthRequest
    public Result data() {

        List<TrafficCoreDataPro> trafficCoreDataPros = trafficCoreDataProServiceImpl.getAllRoadsLatestDeatail();

        HashMap<String, TrafficCoreDataPro> map = new HashMap<>();

        trafficCoreDataPros.stream().forEach(tcd -> {
            map.put(tcd.getRoadSectionName(), tcd);
        });

        mapEntities.forEach(m -> {

            TrafficCoreDataPro tp = map.get(m.getNAME());
            if (!Objects.isNull(tp) && !Objects.isNull(tp.getTotalCars())) {
                m.setFlow(tp.getTotalCars().intValue());
            }
        });

        return new Result().success(mapEntities);
    }

    @GetMapping("/edges")
    @AuthRequest
    public Result edges() {
        return new Result().success(edgeEntities);
    }

    private String jsonRead(File file) {
        Scanner scanner = null;
        StringBuilder buffer = new StringBuilder();
        try {
            scanner = new Scanner(file, "utf-8");
            while (scanner.hasNextLine()) {
                buffer.append(scanner.nextLine());
            }
        } catch (Exception e) {

        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
        return buffer.toString();
    }
}
