package com.neko.seed.v2x.entity.ddo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author Li Jinhui
 * @since 2021-08-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TrafficCoreDataPro implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 路段名称
     */
    private String roadSectionName;

    /**
     * 平均车速
     */
    private Double avgSpeed;

    /**
     * 态势预测
     */
    private Double situationPrediction;

    /**
     * 拥堵长度
     */
    private Double congestionLength;

    /**
     * 蓝牌车辆数
     */
    private Integer numsBlueCar;

    /**
     * 黄牌车辆数
     */
    private Integer numsYellCar;

    /**
     * 平均车头时距
     */
    private Double avgHeadway;

    /**
     * 大车混入率
     */
    private Double cartMixRate;

    /**
     * 服务等级
     */
    private String serviceLevel;

    /**
     * 一氧化碳排放量
     */
    private Double carbonMonoxideEmissions;

    /**
     * 可吸入物排放量
     */
    private Double inhalableEmissions;

    /**
     * 氮氧化物排放量
     */
    @Excel(name = "氮氧化物排放量", orderNum = "13", width = 15)
    private Double noxEmissions;

    /**
     * 管控举措
     */
    private String meausreInfo;

    /**
     * 车辆总和
     */
    private Integer totalCars;

    /**
     * 预测车辆总和
     */
    private Integer predictTotalCars;

    /**
     * 记录时间
     */
    private LocalDateTime recTime;


}
