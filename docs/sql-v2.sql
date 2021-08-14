
#核心路段名称表
drop table if exists traffic_road_name;
create table `traffic_road_name` (
	`id` int unsigned NOT NULL AUTO_INCREMENT PRIMARY KEY,
	`road_section_name` varchar(200) DEFAULT NULL  COMMENT '路段名称'
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
insert into traffic_road_name(road_section_name) values('红垦枢纽_机场互通_杭州方向'),('红垦枢纽_机场互通_杭州方向2');



#核心交通数据宽表
drop table if exists traffic_core_data_pro;
create table `traffic_core_data_pro` (
	`id` bigint(20)  NOT NULL COMMENT '数据库主键：10位时序时间戳' ,
	`road_section_name` varchar(200) NOT NULL COMMENT '路段名称' ,
	`avg_speed` double(10,2)  DEFAULT NULL  COMMENT '平均车速',
	`situation_prediction` double(10,2) DEFAULT NULL  COMMENT '态势预测',
	`congestion_length` double(10,2) DEFAULT NULL  COMMENT '拥堵长度',
	`nums_blue_car` int unsigned DEFAULT 0 COMMENT '蓝牌车辆数',
	`nums_yell_car` int unsigned DEFAULT 0 COMMENT '黄牌车辆数',
	`avg_headway` double(10,2) DEFAULT NULL  COMMENT '平均车头时距',
	`cart_mix_rate` double(10,2) DEFAULT NULL  COMMENT '大车混入率',
	`service_level` varchar(10)  DEFAULT NULL  COMMENT '服务等级',
	`carbon_monoxide_emissions` double(10,2) DEFAULT NULL  COMMENT '一氧化碳排放量',
	`inhalable_emissions` double(10,2) DEFAULT NULL  COMMENT '可吸入物排放量',
	`nox_emissions` double(10,4) DEFAULT NULL  COMMENT '氮氧化物排放量',
	`meausre_info` varchar(200) DEFAULT NULL  COMMENT '管控举措',
	`total_cars` double(10,2) DEFAULT NULL  COMMENT '车辆总和',
	`predict_total_cars` int unsigned DEFAULT 0  COMMENT '预测车辆总和',
	`rec_time`  datetime NOT NULL DEFAULT CURRENT_TIMESTAMP()  COMMENT '记录时间',
	PRIMARY KEY (`id`,`road_section_name`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

#态势预警表
drop table if exists traffic_situation_warning;
create table `traffic_situation_warning` (
	`id` bigint(20)  NOT NULL COMMENT '数据库主键：10位时序时间戳' ,
	`road_section_name` varchar(200) NOT NULL  COMMENT '路段名称',
	`nums_rate` double(10,2) DEFAULT NULL  COMMENT '速率',
	`rec_time`  datetime NOT NULL DEFAULT CURRENT_TIMESTAMP()  COMMENT '记录时间',
	PRIMARY KEY (`id`,`road_section_name`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

#服务质量表
drop table if exists traffic_service_quality;
create table `traffic_service_quality` (
	`id` bigint(20)  NOT NULL COMMENT '数据库主键：10位时序时间戳' ,
	`road_section_name` varchar(200) NOT NULL  COMMENT '路段名称',
	`service_level` varchar(200)  DEFAULT NULL  COMMENT '服务等级',
	`rec_time`  datetime NOT NULL DEFAULT CURRENT_TIMESTAMP()  COMMENT '记录时间',
	PRIMARY KEY (`id`,`road_section_name`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

# 管控措施
drop table if exists traffic_service_todo;
create table `traffic_service_todo` (
	`id` bigint(20)  NOT NULL COMMENT '数据库主键：10位时序时间戳' ,
	`road_section_name` varchar(200) NOT NULL  COMMENT '路段名称',
	`meausre_info` varchar(200)  DEFAULT NULL  COMMENT '管控措施',
	`rec_time`  datetime NOT NULL DEFAULT CURRENT_TIMESTAMP()  COMMENT '记录时间',
	PRIMARY KEY (`id`,`road_section_name`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

# 流量预测表
drop table if exists traffic_predict;
create table `traffic_predict` (
	`id` bigint(20)  NOT NULL COMMENT '数据库主键：10位时序时间戳' ,
	`road_section_name` varchar(200) NOT NULL  COMMENT '路段名称',
	`predict_nums` int  DEFAULT NULL  COMMENT '预测流量',
	`rec_time`  datetime NOT NULL DEFAULT CURRENT_TIMESTAMP()  COMMENT '记录时间',
	PRIMARY KEY (`id`,`road_section_name`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;





