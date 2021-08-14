package com.neko.seed.v2x.entity.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TrafficPredictVO {

    private Integer nums ;
    private Integer rec_time ;

    public TrafficPredictVO() {
    }

    public TrafficPredictVO(Integer nums, Integer rec_time) {
        this.nums = nums;
        this.rec_time = rec_time;
    }
}
