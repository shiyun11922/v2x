package com.neko.seed.traffic.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TopRateVO {

    private String roadSectionName;

    private Integer numsBlueCar;

    private Integer numsYellCar;

    private Integer numsTotalCar;

}
