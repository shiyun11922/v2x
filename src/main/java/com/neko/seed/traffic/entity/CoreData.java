package com.neko.seed.traffic.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.util.StringUtils;

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
@TableName("traffic_core_data")
public class CoreData implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField(exist = false)
    private Integer id;
    /**
     * 路段名称
     */
    private String roadSectionName;

    /**
     * 平均车速
     */
    private Double avgSpeed = 0.0;

    /**
     * 路段预测流量
     */
    @TableField(exist = false)
    private Double forecastTraffic = 0.0;

    /**
     * 态势预测
     */
    private Double situationPrediction = 0.0;

    /**
     * 拥堵长度
     */
    private Double congestionLength = 0.0;


    /**
     * 蓝牌车辆数
     */
    private Double numsBlueCar = 0.0;

    /**
     * 黄牌车辆数
     */
    private Double numsYellCar = 0.0;

    /**
     * 平均车头时距
     */
    private Double avgHeadway = 0.0;

    /**
     * 大车混入率
     */
    private Double cartMixRate = 0.0;


    /**
     * 服务等级
     */
    private String serviceLevel;

    /**
     * 一氧化碳排放量
     */
    private Double carbonMonoxideEmissions = 0.0;

    /**
     * 可吸入物排放量
     */
    private Double inhalableEmissions = 0.0;

    /**
     * 氮氧化物排放量
     */
    private Double noxEmissions = 0.0;

    /**
     * 管控举措
     */
    private String meausreInfo;

    /**
     * v2xcls
     */
    @TableField("V2Xcls")
    private Double V2Xcls = 0.0;

    /**
     * V2X平均车速
     */
    @TableField("V2X_avg_speed")
    private Double v2xAvgSpeed = 0.0;

    /**
     * V2X行驶里程
     */
    @TableField("V2X_driven_distance")
    private Double v2xDrivenDistance = 0.0;

    /**
     * V2X平均车头时距
     */
    @TableField("V2X_avg_headway")
    private Double v2xAvgHeadway = 0.0;

    /**
     * V2X氮氧化物排放量
     */
    @TableField("V2X_nox_emissions")
    private Double v2xNoxEmissions = 0.0;

    /**
     * VC
     */
    @TableField("VC")
    private Double vc = 0.0;

    /**
     * 合计
     */
    private Double total = 0.0;

    /**
     * 记录时间
     */
    private Date recTime;


    private String formatedTime;

    @TableField(exist = false)
    private Double totalCars;

    @TableField(exist = false)
    private String carNum;

    @TableField(exist = false)
    private String carType;


}
