-- auto Generated on 2021-02-04
-- DROP TABLE IF EXISTS t_comment;
CREATE TABLE t_comment(
	id INT (11) NOT NULL AUTO_INCREMENT COMMENT '编号',
	blogId INT (11) NOT NULL COMMENT '文章编号',
	`date` DATETIME NOT NULL COMMENT '评论时间',
	ip VARCHAR (50) NOT NULL COMMENT '评论人IP地址',
	content VARCHAR (50) NOT NULL COMMENT '评论内容',
	`state` INT (11) NOT NULL COMMENT '当前状态(0未审核 1审核通过 2审核不通过)',
	PRIMARY KEY (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT 't_comment';
