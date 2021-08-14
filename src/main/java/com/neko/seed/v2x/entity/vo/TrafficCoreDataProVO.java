package com.neko.seed.v2x.entity.vo;

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
    private String roadSectionName;

    /**
     * 平均车速
     */
    private Double avgSpeed;

    /**
     * 态势预警
     */
    @NotNull(message = "态势预警不能为空")
    private Double situationPrediction;

    /**
     * 拥堵长度
     */
    private Double congestionLength;

    /**
     * 蓝牌车辆数
     */
    @NotNull(message = "蓝牌车辆数不能为空")
    private Integer numsBlueCar;

    /**
     * 黄牌车辆数
     */
    @NotNull(message = "黄牌车辆数不能为空")
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
    @NotBlank(message = "服务等级不能为空")
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
    private Double noxEmissions;

    /**
     * 管控举措
     */
    @NotBlank(message = "管控举措不能为空")
    private String meausreInfo;

    /**
     * 车辆总和
     */
    @NotNull(message = "车辆总和不能为空")
    private Double totalCars;

    /**
     * 预测车辆总和
     */
    @NotNull(message = "预测车辆总和不能为空")
    private Integer predictTotalCars;

    /**
     * 记录时间
     */
    @NotNull(message = "时间不能为空")
    private LocalDateTime recTime;


}
