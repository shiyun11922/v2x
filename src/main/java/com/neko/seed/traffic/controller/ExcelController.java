package com.neko.seed.traffic.controller;

import com.neko.seed.ExcelUtils;
import com.neko.seed.auth.annotation.AuthRequest;
import com.neko.seed.traffic.entity.PersonExportVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping(path = "excel")
public class ExcelController {

    private static final Logger log = LoggerFactory.getLogger(ExcelController.class);

    @GetMapping(value = "/export")
    @AuthRequest
    public void exportExcel(HttpServletResponse response) throws IOException {
        long start = System.currentTimeMillis();
        List<PersonExportVo> personList = new ArrayList<PersonExportVo>();
        for (int i = 0; i < 5; i++) {
            PersonExportVo personVo = new PersonExportVo();
            personVo.setName("张三" + i);
            personVo.setUsername("张三" + i);
            personVo.setPhoneNumber("18888888888");
            personVo.setImageUrl("1122");
            personList.add(personVo);
        }
        log.debug("导出excel所花时间：" + (System.currentTimeMillis() - start));
        ExcelUtils.exportExcel(personList, "员工信息表", "员工信息", PersonExportVo.class, "员工信息", response);
    }

}

