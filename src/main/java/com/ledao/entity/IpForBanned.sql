-- auto Generated on 2021-05-17
-- DROP TABLE IF EXISTS t_ip_for_banned;
CREATE TABLE t_ip_for_banned(
	id INT (11) NOT NULL AUTO_INCREMENT COMMENT '编号',
	ip VARCHAR (50) NOT NULL COMMENT 'ip地址',
	`type` VARCHAR (50) NOT NULL COMMENT '分为主动封禁和自动封禁',
	`date` DATETIME NOT NULL COMMENT '封禁时间',
	PRIMARY KEY (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT 't_ip_for_banned';
