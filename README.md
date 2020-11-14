

# Sonrisa云收藏

与热门的云收藏不同，简化了好友、评论等功能，使用几个 jquery 插件，完善 bug ，能够更准确地进行网址收藏。 这也是自己的第一个Java项目，用时近两周，所以本项目会更适合于 springboot 的初学者们。



<a href="http://101.132.237.172/">项目地址</a> &nbsp;<a href="https://gitee.com/z920848348/collection">码云链接</a>&nbsp;<a href="https://github.com/920848348/collection/">github链接</a>

##  所涉及技术

### 后端：

- Springboot ​ 框架
- Mybatis  框架
- Mysql  数据库
- Shiro  安全框架



### 前端：

- Thymeleaf  模板引擎
- jQuery 框架
- 使用 BootStrap 、layui 框架进行页面渲染
- 部分图标使用于 Fontawesome 图标库
- 弹窗使用于 SweetAlert 、模态框使用于 Bootstrap
- 头像截取使用于 cropper 图片剪裁插件



## 功能概述

- 通过检索网址来获取文章，提供收藏、分类功能
- 可浏览他人的部分收藏
- 可以点赞收藏
- 供注册、登录，个人创建收藏夹，头像等

##### 小模块：

- 使用 Shiro 框架，同个账号不可同时在线

- 直接存储 url 网址，解决了以 ? 或 & 符号导致的存储网址不精确问题

- 开启了邮箱验证功能

- 开启了 druid 后台监控

  

##### 不足之处：

- 首页没有时间去做。

- 未采用响应式布局，手机端无法正常使用。

- 浏览器兼容问题，导致在某些浏览器上，部分页面未居中布局。

  

## 项目截图

![](http://sonrisa.cn/img/sonrisa云收藏/1.png)

![](http://sonrisa.cn/img/sonrisa云收藏/2.png)



![](http://sonrisa.cn/img/sonrisa云收藏/3.png)



![](http://sonrisa.cn/img/sonrisa云收藏/4.png)

![](http://sonrisa.cn/img/sonrisa云收藏/5.png)



## 快速使用

1、克隆并使用 IDEA 或 eclipse 打开 maven 项目。

2、先将 application.yml 配置文件中的模式改为 dev 模式。

3、修改其 application-dev.yml 配置文件的数据库用户名密码等配置。

4、在数据库中创建一个名为 collection 的数据库。

5、执行以下 sql 语句，创建数据库表（该 sql 文件在仓库中存有）。

```sql
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `article`;
CREATE TABLE `article`  (
  `id` bigint(50) NOT NULL,
  `title` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `url` varchar(9000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `description` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `like_total` int(11) NULL DEFAULT NULL,
  `collection_total` int(11) NULL DEFAULT NULL,
  `is_private` bit(1) NULL DEFAULT NULL,
  `create_time` datetime(6) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `folder`;
CREATE TABLE `folder`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(50) NOT NULL,
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `folder`(`user_id`) USING BTREE,
  CONSTRAINT `folder_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 117 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;


DROP TABLE IF EXISTS `profile`;
CREATE TABLE `profile`  (
  `user_id` bigint(50) NOT NULL,
  `article_id` bigint(50) NOT NULL,
  `folder_id` int(20) NULL DEFAULT NULL,
  INDEX `user_profile`(`user_id`) USING BTREE,
  INDEX `folder_profile`(`folder_id`) USING BTREE,
  INDEX `article_profile`(`article_id`) USING BTREE,
  CONSTRAINT `article_profile` FOREIGN KEY (`article_id`) REFERENCES `article` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `folder_profile` FOREIGN KEY (`folder_id`) REFERENCES `folder` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `user_profile` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;


DROP TABLE IF EXISTS `star`;
CREATE TABLE `star`  (
  `user_id` bigint(20) NULL DEFAULT NULL,
  `article_id` bigint(20) NULL DEFAULT NULL,
  INDEX `user_star`(`user_id`) USING BTREE,
  INDEX `article_star`(`article_id`) USING BTREE,
  CONSTRAINT `article_star` FOREIGN KEY (`article_id`) REFERENCES `article` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `user_star` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint(50) NOT NULL,
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `nickname` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `portrait_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;

```


### 您可能需要做以下更改：

1、头像的默认路径为：电脑 F 盘的 pictures 文件夹下（见 WebMvcConfig.java的资源处理器）。<br>
2、默认头像为 F:\pictures\1\user.png （见 application-dev.yml 路径配置）。<br>
3、对于邮件协议，需在配置文件中修改邮件名、smtp密码等配置。<br>
4、druid后台账号密码需在 DruidConfig类 中更改。

