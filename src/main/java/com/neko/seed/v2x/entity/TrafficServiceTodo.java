package com.neko.seed.v2x.entity;

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
public class TrafficServiceTodo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 路段名称
     */
    private String roadSectionName;

    /**
     * 管控措施
     */
    private String meausreInfo;

    /**
     * 记录时间
     */
    private LocalDateTime recTime;


}
