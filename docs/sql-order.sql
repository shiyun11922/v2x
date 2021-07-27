

#核心交通数据宽表

create table `traffic_core_data` (
	`id` int unsigned NOT NULL AUTO_INCREMENT ,
	`apply_user`
	`rec_time`  datetime NOT NULL DEFAULT CURRENT_TIMESTAMP()  COMMENT '记录时间',
	PRIMARY KEY (`id`,`road_section_name`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


