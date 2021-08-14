package com.neko.seed.v2x.entity.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

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
public class TrafficCoreDataProVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 路段名称
     */
    @NotBlank(message = "路段名称不能为空")
    @Excel(name = "路段名称", orderNum = "1", width = 40)
    private String roadSectionName;

    /**
     * 平均车速
     */
    @Excel(name = "平均车速", orderNum = "2", width = 15)
    private Double avgSpeed;

    /**
     * 态势预警
     */
    @NotNull(message = "态势预警不能为空")
    @Excel(name = "态势预测", orderNum = "4", width = 15)
    private Double situationPrediction;

    /**
     * 拥堵长度
     */
    @Excel(name = "拥堵长度", orderNum = "5", width = 15)
    private Double congestionLength;

    /**
     * 蓝牌车辆数
     */
    @NotNull(message = "蓝牌车辆数不能为空")
    @Excel(name = "蓝牌车辆数", orderNum = "6", width = 15)
    private Integer numsBlueCar;

    /**
     * 黄牌车辆数
     */
    @NotNull(message = "黄牌车辆数不能为空")
    @Excel(name = "黄牌车辆数", orderNum = "7", width = 15)
    private Integer numsYellCar;

    /**
     * 平均车头时距
     */
    @Excel(name = "平均车头时距", orderNum = "8", width = 15)
    private Double avgHeadway;

    /**
     * 大车混入率
     */
    @Excel(name = "大车混入率", orderNum = "9", width = 15)
    private Double cartMixRate;

    /**
     * 服务等级
     */
    @NotBlank(message = "服务等级不能为空")
    @Excel(name = "服务等级", orderNum = "10", width = 15)
    private String serviceLevel;

    /**
     * 一氧化碳排放量
     */
    @Excel(name = "一氧化碳排放量", orderNum = "11", width = 15)
    private Double carbonMonoxideEmissions;

    /**
     * 可吸入物排放量
     */
    @Excel(name = "可吸入物排放量", orderNum = "12", width = 15)
    private Double inhalableEmissions;

    /**
     * 氮氧化物排放量
     */
    @Excel(name = "氮氧化物排放量", orderNum = "13", width = 15)
    private Double noxEmissions;

    /**
     * 管控举措
     */
    @NotBlank(message = "管控举措不能为空")
    @Excel(name = "管控举措", orderNum = "14", width = 150)
    private String meausreInfo;

    /**
     * 车辆总和
     */
    @NotNull(message = "车辆总和不能为空")
    @Excel(name = "合计车辆数", orderNum = "15", width = 15)
    private Integer totalCars;

    /**
     * 预测车辆总和
     */
    @NotNull(message = "预测车辆总和不能为空")
    @Excel(name = "预测车辆总和", orderNum = "16", width = 15)
    private Integer predictTotalCars;

    /**
     * 记录时间
     */
    @NotNull(message = "时间不能为空")
    @Excel(name = "记录时间", orderNum = "3", width = 40)
    private LocalDateTime recTime;


}
