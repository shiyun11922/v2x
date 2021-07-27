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
 * @since 2021-06-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("traffic_service_quality")
public class ServiceQuality implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 路段名称
     */
    private String recRoadSectionName;

    /**
     * 服务等级
     */
    private String serviceLevel;

    /**
     * 记录时间
     */
    private LocalDateTime recTime;


}
