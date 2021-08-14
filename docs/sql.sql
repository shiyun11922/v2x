
#核心路段名称表
create table `traffic_road_name` (
	`id` int unsigned NOT NULL AUTO_INCREMENT PRIMARY KEY,
	`road_section_name` varchar(200) DEFAULT NULL  COMMENT '路段名称'
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
insert into traffic_road_name(road_section_name) values('红垦枢纽_机场互通_杭州方向'),('红垦枢纽_机场互通_杭州方向2');

#核心交通数据宽表
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
	`rec_time`  datetime NOT NULL DEFAULT CURRENT_TIMESTAMP()  COMMENT '记录时间',
	PRIMARY KEY (`id`,`road_section_name`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert into traffic_core_data_pro(id, road_section_name) values(1628903839, '红垦枢纽_机场互通_杭州方向');


#态势预警表
create table `traffic_situation_warning` (
	`id` bigint(20)  NOT NULL COMMENT '数据库主键：10位时序时间戳' ,
	`rec_road_section_name` varchar(200) DEFAULT NULL  COMMENT '路段名称',
	`nums_rate` double(10,2) DEFAULT NULL  COMMENT '速率',
	`rec_time`  datetime NOT NULL DEFAULT CURRENT_TIMESTAMP()  COMMENT '记录时间'
	PRIMARY KEY (`id`,`road_section_name`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert into traffic_situation_warning (rec_road_section_name, nums_rate) values ('红垦枢纽_机场互通_杭州方向', 6300.0), ('红垦枢纽_机场互通_杭州方向2', 6300.0);

#服务质量表

create table `traffic_service_quality` (
	`id` int unsigned NOT NULL AUTO_INCREMENT PRIMARY KEY,
	`rec_road_section_name` varchar(200) DEFAULT NULL  COMMENT '路段名称',
	`service_level` varchar(200)  DEFAULT NULL  COMMENT '服务等级',
	`rec_time`  datetime NOT NULL DEFAULT CURRENT_TIMESTAMP()  COMMENT '记录时间'
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

delete from traffic_service_quality;
insert into traffic_service_quality(rec_road_section_name, service_level) values ('红垦枢纽_机场互通_杭州方向', "A"), ('红垦枢纽_机场互通_杭州方向2', "B");


#车辆类型表
create table `traffic_onroad_car_detail` (
	`id` int unsigned NOT NULL AUTO_INCREMENT PRIMARY KEY,
	`car_id` varchar(50) DEFAULT NULL  COMMENT '车牌',
	`rec_time`  datetime DEFAULT NULL  COMMENT '记录时间'
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

# 管控措施
create table `traffic_service_todo` (
	`id` int unsigned NOT NULL AUTO_INCREMENT PRIMARY KEY,
	`rec_road_section_name` varchar(200) DEFAULT NULL  COMMENT '路段名称',
	`need_todo` varchar(200)  DEFAULT NULL  COMMENT '管控措施',
	`rec_time`  datetime NOT NULL DEFAULT CURRENT_TIMESTAMP()  COMMENT '记录时间'
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

# 流量预测表
create table `traffic_predict` (
	`id` int unsigned NOT NULL AUTO_INCREMENT PRIMARY KEY,
	`rec_road_section_name` varchar(200) DEFAULT NULL  COMMENT '路段名称',
	`nums` int  DEFAULT NULL  COMMENT '预测流量',
	`rec_time`  datetime NOT NULL DEFAULT CURRENT_TIMESTAMP()  COMMENT '记录时间'
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


insert into traffic_service_todo(rec_road_section_name,need_todo) values ('红垦枢纽_机场互通_杭州方向', '垦枢纽_机场互通_杭州方向发生拥堵，建议将限速降低到80km/h');

## 跑批所需要建立的表

CREATE TABLE BATCH_JOB_INSTANCE  (
	JOB_INSTANCE_ID BIGINT  NOT NULL PRIMARY KEY ,
	VERSION BIGINT ,
	JOB_NAME VARCHAR(100) NOT NULL,
	JOB_KEY VARCHAR(32) NOT NULL,
	constraint JOB_INST_UN unique (JOB_NAME, JOB_KEY)
) ENGINE=InnoDB;

CREATE TABLE BATCH_JOB_EXECUTION  (
	JOB_EXECUTION_ID BIGINT  NOT NULL PRIMARY KEY ,
	VERSION BIGINT  ,
	JOB_INSTANCE_ID BIGINT NOT NULL,
	CREATE_TIME DATETIME NOT NULL,
	START_TIME DATETIME DEFAULT NULL ,
	END_TIME DATETIME DEFAULT NULL ,
	STATUS VARCHAR(10) ,
	EXIT_CODE VARCHAR(2500) ,
	EXIT_MESSAGE VARCHAR(2500) ,
	LAST_UPDATED DATETIME,
	JOB_CONFIGURATION_LOCATION VARCHAR(2500) NULL,
	constraint JOB_INST_EXEC_FK foreign key (JOB_INSTANCE_ID)
	references BATCH_JOB_INSTANCE(JOB_INSTANCE_ID)
) ENGINE=InnoDB;

CREATE TABLE BATCH_JOB_EXECUTION_PARAMS  (
	JOB_EXECUTION_ID BIGINT NOT NULL ,
	TYPE_CD VARCHAR(6) NOT NULL ,
	KEY_NAME VARCHAR(100) NOT NULL ,
	STRING_VAL VARCHAR(250) ,
	DATE_VAL DATETIME DEFAULT NULL ,
	LONG_VAL BIGINT ,
	DOUBLE_VAL DOUBLE PRECISION ,
	IDENTIFYING CHAR(1) NOT NULL ,
	constraint JOB_EXEC_PARAMS_FK foreign key (JOB_EXECUTION_ID)
	references BATCH_JOB_EXECUTION(JOB_EXECUTION_ID)
) ENGINE=InnoDB;

CREATE TABLE BATCH_STEP_EXECUTION  (
	STEP_EXECUTION_ID BIGINT  NOT NULL PRIMARY KEY ,
	VERSION BIGINT NOT NULL,
	STEP_NAME VARCHAR(100) NOT NULL,
	JOB_EXECUTION_ID BIGINT NOT NULL,
	START_TIME DATETIME NOT NULL ,
	END_TIME DATETIME DEFAULT NULL ,
	STATUS VARCHAR(10) ,
	COMMIT_COUNT BIGINT ,
	READ_COUNT BIGINT ,
	FILTER_COUNT BIGINT ,
	WRITE_COUNT BIGINT ,
	READ_SKIP_COUNT BIGINT ,
	WRITE_SKIP_COUNT BIGINT ,
	PROCESS_SKIP_COUNT BIGINT ,
	ROLLBACK_COUNT BIGINT ,
	EXIT_CODE VARCHAR(2500) ,
	EXIT_MESSAGE VARCHAR(2500) ,
	LAST_UPDATED DATETIME,
	constraint JOB_EXEC_STEP_FK foreign key (JOB_EXECUTION_ID)
	references BATCH_JOB_EXECUTION(JOB_EXECUTION_ID)
) ENGINE=InnoDB;

CREATE TABLE BATCH_STEP_EXECUTION_CONTEXT  (
	STEP_EXECUTION_ID BIGINT NOT NULL PRIMARY KEY,
	SHORT_CONTEXT VARCHAR(2500) NOT NULL,
	SERIALIZED_CONTEXT TEXT ,
	constraint STEP_EXEC_CTX_FK foreign key (STEP_EXECUTION_ID)
	references BATCH_STEP_EXECUTION(STEP_EXECUTION_ID)
) ENGINE=InnoDB;

CREATE TABLE BATCH_JOB_EXECUTION_CONTEXT  (
	JOB_EXECUTION_ID BIGINT NOT NULL PRIMARY KEY,
	SHORT_CONTEXT VARCHAR(2500) NOT NULL,
	SERIALIZED_CONTEXT TEXT ,
	constraint JOB_EXEC_CTX_FK foreign key (JOB_EXECUTION_ID)
	references BATCH_JOB_EXECUTION(JOB_EXECUTION_ID)
) ENGINE=InnoDB;

CREATE TABLE BATCH_STEP_EXECUTION_SEQ (
	ID BIGINT NOT NULL,
	UNIQUE_KEY CHAR(1) NOT NULL,
	constraint UNIQUE_KEY_UN unique (UNIQUE_KEY)
) ENGINE=InnoDB;

INSERT INTO BATCH_STEP_EXECUTION_SEQ (ID, UNIQUE_KEY) select * from (select 0 as ID, '0' as UNIQUE_KEY) as tmp where not exists(select * from BATCH_STEP_EXECUTION_SEQ);

CREATE TABLE BATCH_JOB_EXECUTION_SEQ (
	ID BIGINT NOT NULL,
	UNIQUE_KEY CHAR(1) NOT NULL,
	constraint UNIQUE_KEY_UN unique (UNIQUE_KEY)
) ENGINE=InnoDB;

INSERT INTO BATCH_JOB_EXECUTION_SEQ (ID, UNIQUE_KEY) select * from (select 0 as ID, '0' as UNIQUE_KEY) as tmp where not exists(select * from BATCH_JOB_EXECUTION_SEQ);

CREATE TABLE BATCH_JOB_SEQ (
	ID BIGINT NOT NULL,
	UNIQUE_KEY CHAR(1) NOT NULL,
	constraint UNIQUE_KEY_UN unique (UNIQUE_KEY)
) ENGINE=InnoDB;

INSERT INTO BATCH_JOB_SEQ (ID, UNIQUE_KEY) select * from (select 0 as ID, '0' as UNIQUE_KEY) as tmp where not exists(select * from BATCH_JOB_SEQ);




