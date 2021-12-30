### 项目地址

GitHub地址：https://github.com/a6678696/MyBlog

项目演示地址：http://www.zoutl.cn/

### 使用的技术

本项目核心技术采用Spring Boot+Mybatis；开发工具idea；数据库 MySQL5.6；模版引擎采用的是Thymeleaf；安全框架采用SpringSecurity；在线编辑器CKEditor；Lucene全文检索；Quartz定时任务；[基于JavaMail实现用QQ邮箱发送邮件 ](https://blog.zoutl.cn/64.html)；highlight.js代码高亮；Highcharts图表；Redis存储经常查看的数据；

### 功能介绍

#### 前台

主页分页展示所有博客、按类别分类博客、按日期分类博客、查看博客内容详情、Lucene全文检索博客标题和摘要、点赞博客、评论博客、网站数据展示

#### 后台

写博客、博客类别管理、博客管理、安全退出、每天23点定时删除无效的博客图片（删除的博客但未删除其引用的图片）、查看访问记录、一键切换前台UI（一共有4套UI，下面展示的是第二套）、评论管理（删除、审核、回复）、查看点赞记录、友情链接管理、一键备份数据以及每天23点定时备份数据（包括数据库、博客正文图片、Lucene索引）、有人留言自动给站长发邮件、一键设置邮件提醒与否、系统自动封禁ip、封禁ip管理、highlight.js代码高亮、后台修改代码风格、每日访问ip个数统计、每日访问数统计

### 图片展示

项目图片展示查看博客：https://blog.zoutl.cn/12.html
