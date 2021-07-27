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
 * @since 2021-06-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("traffic_onroad_car_detail")
public class OnroadCarDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 车牌
     */
    private String carId;

    /**
     * 记录时间
     */
    private LocalDateTime recTime;


}
