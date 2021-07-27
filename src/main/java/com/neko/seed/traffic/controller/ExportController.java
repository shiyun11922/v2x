package com.neko.seed.traffic.controller;

import com.neko.seed.ExcelUtils;
import com.neko.seed.auth.annotation.AuthRequest;
import com.neko.seed.base.entity.Result;
import com.neko.seed.traffic.entity.CoreData;
import com.neko.seed.traffic.entity.CoreDataVO;
import com.neko.seed.traffic.entity.PersonExportVo;
import com.neko.seed.traffic.service.CoreDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;


@Controller
@RequestMapping(path = "core")
public class ExportController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExportController.class);
    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private CoreDataService coreDataServiceImpl;

    @GetMapping(value = "/exp")
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
        LOGGER.debug("导出excel所花时间：" + (System.currentTimeMillis() - start));
        ExcelUtils.exportExcel(personList, "员工信息表", "员工信息", PersonExportVo.class, "员工信息", response);
    }

    @GetMapping("/export")
    @AuthRequest
    public void exportExcel(HttpServletResponse response,
                            String roadname, String startTimeStamp, String endTimeStamp) throws IOException {
        Objects.requireNonNull(roadname, "路段明细明细信息，路段名称不能为空");
        Objects.requireNonNull(startTimeStamp, "startTimeStamp不可以为空");
        Objects.requireNonNull(endTimeStamp, "endTimeStamp不可以为空");

        long startTS = System.currentTimeMillis();
        LOGGER.info("export core data, startTimeStamp={}, endTimeStamp={}", startTimeStamp, endTimeStamp);
        Long start = Long.parseLong(startTimeStamp);
        Long end = Long.parseLong(endTimeStamp);

        String startDate = formatter.format(new Date(start));
        String endDate = formatter.format(new Date(end));

        String title = roadname + "路段车况明细表(" + startDate + " 到 " + endDate + ")";
        List<CoreData> dataListByName = coreDataServiceImpl.getDataListByName(roadname, start, end);

        List<CoreDataVO> coreDataVOS = new ArrayList<>();
        dataListByName.stream().forEach(
                d -> {
                    CoreDataVO coreDataVO = new CoreDataVO();
                    BeanUtils.copyProperties(d, coreDataVO);
                    coreDataVO.setV2xcls(d.getV2Xcls());
                    coreDataVOS.add(coreDataVO);
                }
        );

        LOGGER.info("导出" + title + ", excel所花时间：" + (System.currentTimeMillis() - startTS));

        ExcelUtils.exportExcel(coreDataVOS, title, "路段车况明细表", CoreDataVO.class, title, response);
    }
}

