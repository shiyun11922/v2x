package com.neko.seed.v2x.entity.ddo;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
public class TrafficPredict implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 路段名称
     */
    private String roadSectionName;

    /**
     * 车辆总和
     */
    private Double totalCars;

    /**
     * 预测车辆总和
     */
    private Integer predictTotalCars;

    /**
     * 记录时间
     */
    private LocalDateTime recTime;


}
