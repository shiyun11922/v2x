package com.neko.seed.traffic.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.neko.seed.auth.annotation.AuthRequest;
import com.neko.seed.base.entity.Result;
import com.neko.seed.traffic.entity.CoreData;
import com.neko.seed.traffic.entity.EdgeEntity;
import com.neko.seed.traffic.entity.MapEntity;
import com.neko.seed.traffic.service.CoreDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

@RestController
@RequestMapping("/traffic/map")
public class MapController {


    @Autowired
    private CoreDataService coreDataServiceImpl;

    @GetMapping("")
    @AuthRequest
    public Result data() {

        ArrayList<MapEntity> mapEntities = new ArrayList<>();

        try {

            FileSystemResource resource1 = new FileSystemResource("input/g92_road.json");
            File file = resource1.getFile();
            String jsonData = this.jsonRead(file);
            JSONArray objects = JSONObject.parseArray(jsonData);
            for (int i = 0; i < objects.size(); i++) {
                JSONObject object = objects.getJSONObject(i);
                MapEntity m = JSONObject.toJavaObject(object, MapEntity.class);
                CoreData cd = coreDataServiceImpl.getDataByName(m.getNAME());
                if(cd != null){
                    Double total = cd.getNumsBlueCar() + cd.getNumsYellCar();
                    m.setFlow(total.intValue());
                }
                mapEntities.add(m);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        return new Result().success(mapEntities);
    }

    @GetMapping("/edges")
    @AuthRequest
    public Result edges() {

        ArrayList<EdgeEntity> mapEntities = new ArrayList<>();

        try {
            FileSystemResource resource1 = new FileSystemResource("input/edges.json");
            File file = resource1.getFile();
            String jsonData = this.jsonRead(file);
            JSONArray objects = JSONObject.parseArray(jsonData);
            for (int i = 0; i < objects.size(); i++) {
                JSONObject object = objects.getJSONObject(i);
                EdgeEntity m = JSONObject.toJavaObject(object, EdgeEntity.class);
                mapEntities.add(m);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return new Result().success(mapEntities);
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
