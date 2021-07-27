package com.neko.seed.traffic.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author Li Jinhui
 * @since 2021-06-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CoreDataVO implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 路段名称
     */
    @Excel(name = "路段名称", orderNum = "1", width = 40)
    private String roadSectionName;

    /**
     * 平均车速
     */
    @Excel(name = "平均车速", orderNum = "2", width = 15)
    private Double avgSpeed = 0.0;

    /**
     * 路段预测流量
     */
    @Excel(name = "路段预测流量", orderNum = "3", width = 15)
    private Double forecastTraffic = 0.0;

    /**
     * 态势预测
     */
    @Excel(name = "态势预测", orderNum = "4", width = 15)
    private Double situationPrediction = 0.0;

    /**
     * 拥堵长度
     */
    @Excel(name = "拥堵长度", orderNum = "5", width = 15)
    private Double congestionLength = 0.0;


    /**
     * 蓝牌车辆数
     */
    @Excel(name = "蓝牌车辆数", orderNum = "6", width = 15)
    private Double numsBlueCar = 0.0;

    /**
     * 黄牌车辆数
     */
    @Excel(name = "黄牌车辆数", orderNum = "7", width = 15)
    private Double numsYellCar = 0.0;

    /**
     * 平均车头时距
     */
    @Excel(name = "平均车头时距", orderNum = "8", width = 15)
    private Double avgHeadway = 0.0;

    /**
     * 大车混入率
     */
    @Excel(name = "大车混入率", orderNum = "9", width = 15)
    private Double cartMixRate = 0.0;


    /**
     * 服务等级
     */
    @Excel(name = "服务等级", orderNum = "10", width = 15)
    private String serviceLevel;

    /**
     * 一氧化碳排放量
     */
    @Excel(name = "一氧化碳排放量", orderNum = "11", width = 15)
    private Double carbonMonoxideEmissions = 0.0;

    /**
     * 可吸入物排放量
     */
    @Excel(name = "可吸入物排放量", orderNum = "12", width = 15)
    private Double inhalableEmissions = 0.0;

    /**
     * 氮氧化物排放量
     */
    @Excel(name = "氮氧化物排放量", orderNum = "13", width = 15)
    private Double noxEmissions = 0.0;

    /**
     * 管控举措
     */
    @Excel(name = "管控举措", orderNum = "14", width = 150)
    private String meausreInfo;

    /**
     * v2xcls
     */
    @Excel(name = "v2xcls", orderNum = "15", width = 15)
    private Double v2xcls = 0.0;

    /**
     * V2X平均车速
     */
    @Excel(name = "V2X平均车速", orderNum = "16", width = 15)
    private Double v2xAvgSpeed = 0.0;

    /**
     * V2X行驶里程
     */
    @Excel(name = "V2X行驶里程", orderNum = "17", width = 15)
    private Double v2xDrivenDistance = 0.0;

    /**
     * V2X平均车头时距
     */
    @Excel(name = "V2X平均车头时距", orderNum = "18", width = 15)
    private Double v2xAvgHeadway = 0.0;

    /**
     * V2X氮氧化物排放量
     */
    @Excel(name = "V2X氮氧化物排放量", orderNum = "19", width = 15)
    private Double v2xNoxEmissions = 0.0;

    /**
     * VC
     */
    @Excel(name = "VC", orderNum = "20", width = 15)
    private Double vc = 0.0;

    /**
     * 合计
     */
    @Excel(name = "合计", orderNum = "21", width = 15)
    private Double total = 0.0;

    /**
     * 记录时间
     */
    @Excel(name = "记录时间", orderNum = "22", width = 40)
    private Date recTime = new Date();

}
