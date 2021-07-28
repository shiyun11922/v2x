package com.neko.seed.traffic.entity;

import com.baomidou.mybatisplus.annotation.TableName;
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
 * @since 2021-07-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("traffic_predict")
public class Predict implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 路段名称
     */
    private String recRoadSectionName;

    /**
     * 预测流量
     */
    private Integer nums;

    /**
     * 记录时间
     */
    private LocalDateTime recTime;


}
