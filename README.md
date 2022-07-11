> 开发时间：2021.11 - 2021.12

# 一、快速开始

方案一：

1. 克隆仓库：使用 Git 克隆仓库或直接下载仓库压缩包到您的计算机
2. 打开工程：使用 `IntelliJ IDEA` 打开克隆的仓库或解压的工程文件，而后使用 Maven 工具更新 `ebrss-server ` 工程模块依赖
3. 创建数据库和表并插入数据：登录 MySQL ，创建 `e_book_resources_service_system` 数据库，将 `ebrss-server/src/main/resources/sql/e_book_resources_service_system.sql` 文件中的数据库表导入 e_book_resources_service_system 数据库中
4. 修改数据库连接信息：修改 `ebrss-server/src/main/resources/druid.properties` 中的数据库连接信息，设置你自己的数据库用户名和密码 
5. 启动服务器：运行 `ebrss-server/src/main/java/com.springbear.ebrss.Server` 类
6. 启动客户端：运行 `ebrss-client/src/main/java/com.springbear.ebrss.LoginFrame` 类
7. 登录系统：默认用户名和密码均为 `admin`

方案二（图书上传、下载功能不可用）：

1. 克隆仓库：使用 Git 克隆仓库或直接下载仓库压缩包到您的计算机

2. 创建数据库和表并插入数据：登录 MySQL ，创建 `e_book_resources_service_system` 数据库，将 `RELEASE/e_book_resources_service_system.sql` 文件中的数据库表导入 e_book_resources_service_system 数据库中

3. 创建数据库用户：在 MySQL 控制台创建 `admin` 用户，密码也为 `admin`，并赋予 admin 用户所有操作权限

   ```sql
   create user 'admin'@'localhost' identified by 'admin';
   grant all on e_book_resources_service_system.* to 'admin'@'localhost' with grant option;
   ```

4. 启动服务器：在命令行控制台进入 RELEASE 目录下，使用 `java -jar ebrss-server.jar` 命令启动服务器

5. 启动客户端：在命令行控制台进入 RELEASE 目录下，使用 `java -jar ebrss-client.jar` 命令启动客服端

6. 登录系统：默认用户名和密码均为 `admin`

# 二、背景调查

随着互联网技术的不断发展，网络上遍布着越来越多的有用或是无用的资源，要在不计其数的资源中筛选出自己亟需的资源，需要耗费较大的精力和非常多的时间。尤其是各种电子图书资源还涉及到知识产权的法律性问题，导致截至目前几乎没有哪个产品能完全满足快速检索电子图书资源和提供相关服务的需求。而现有的各大电子图书网站、系统为了避免侵权问题大部分都选择让网站用户上传对应电子图书的下载链接（电子图书资源一般都还存放在用户的个人云盘中），网站系统负责将所有的链接进行糅合，杂乱无章的整合让普通用户无所适从、望而却步。对于急需查找电子图书的用户，需要到从繁杂的网页中检索出需要的图书的链接，再根据链接跳转到对应的云盘应用进行下载。不得不说，这样的方式对一般用户极其不友好，图书资源也不够规范化、有的是盗版图书、有的甚至不能算是图书、有的下载链接早已失效······ 极大程度浪费了用户的精力和时间。更甚的是，某的网盘下载速度实在堪忧，还得成为会员方可提高下载速度，步骤繁多、代价太大。所以我们的电子图书资源服务系统应运而生。

# 三、需求分析

本电子图书资源服务系统（e-book resource service system）旨在提供电子图书资源一站式服务，只要成为本系统的用户，就能从系统提供的图书资源中直接检索资源并进行下载，操作简单便捷。当然，为解决各竞品都头疼的知识产权问题，本系统采取了以下两条必要措施：

1. 系统专门成立了图书管理部门与图书的作者进行协商以解决侵权问题，图书采集员将长期从正规渠道采购各类电子图书资源以满足用户的需求，图书审核员将主要解决图书的知识产权问题等。
2. 本系统的用户一律采取邀请式注册，即只有拥有本系统的注册码方可注册成为系统用户进而享受相关服务，用户注册和下载图书的过程中也将签署相关协议。

# 四、功能描述

![在这里插入图片描述](https://img-blog.csdnimg.cn/ab820d043af34591a066d345aad09bd6.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAU3ByaW5nLV8tQmVhcg==,size_20,color_FFFFFF,t_70,g_se,x_16#pic_center)

## 1、客户端功能

1. 用户注册：用户通过正规渠道获取注册码，填写相关必要信息进行注册，注册过程中需对用户填写的信息进行合法性校验，使用正则表达式和请求服务器验证的方式进行校验，如用户名不允许重复、身份证号及手机号需真实有效等；用户注册前须阅读用户协议

2. 用户登录：用户注册成功后输入用户名、密码尝试登入系统，客户端发送请求到服务器验证用户名及密码正确性，验证通过则用户进入服务系统，否则提醒信息错误，告知用户需重新输入或进行注册


3. 图书检索：用户可以根据需要的图书信息输入检索条件，从服务器请求对应的图书信息，检索条件可以是书名、作者、关键词、分类或直接检索所有图书，并浏览图书信息；若不存在用户需要的图书信息，友好提示用户可以选择联系客服，阐明自己需要的图书类型，由客服报图书采购部门进行处理，最大程度满足用户的需求

4. 图书下载：用户根据第（3）步请求到的图书信息，可每次选择一项图书立即下载，客户端请求服务器传送对应的图书信息到客户端，由用户自定义路径进行保存，图书下载完成

5. 图书上传：因部分图书资源较为稀缺，故提供用户可上传自己收藏的图书资源的功能。用户成功上传图书文件，经服务器下载后，由图书管理部门解决知识产权问题，而后上架供其它用户下载

6. 下载记录：在用户成功下载图书的同时，自动为用户添加相应的下载记录，用户也可查询自己的历史下载记录

7. 收藏夹：用户根据第（3）步请求到的图书信息，若用户不想立即下载图书，为方便日后再次便捷查阅，用户可选择将需要的图书加入到个人收藏夹；收藏夹中页面提供用户下载图书和取消收藏的功能

8. 账号管理：账号管理页面用户可以选择修改个人部分信息，如登录密码、手机号等；也可选择注销账户，用户申请注销账户后，服务端将该账户状态修改为冻结状态，若 90 日之内用户未申请解冻，则系统彻底删除该账户的所有信息

9. 联系客服：客户端提供用户与平台客服进行实时沟通的功能，用户可与客服沟通解决相关系统使用问题或是申请上架自己想要的图书资源等

## 2、服务端功能

1. 服务器接收处理客户端所有请求，如用户注册、图书查询、图书下载、查询个人收藏夹及个人下载记录等

2. 服务端根据系统角色不同，授予不同的角色不同的权限，角色主要分为系统管理员、图书收集员、图书审核员、图书录入员、图书维护员、注册码管理员等
3. 图书收集：由图书收集员从各种正规渠道获取图书资源，包括整理用户上传的资源，分类整理后交由图书审核员解决知识产权问题
4. 图书审核：由图书审核员解决电子图书的知识产权问题，与作者联系协商，每一位审核员与作者协商成功后都需要签订相关纸质协议，录入电子记录到数据库相关数据表，以便日后查用
5. 图书录入：由图书录入员录入审核员已解决产权问题的图书信息，如 ISBN 号、书名、作者、 出版社、关键词等基本信息，对图书基本信息进行校验
6. 图书维护：由图书维护员对图书的基本信息进行维护，如根据图书的分类对图书进行分类管理以方便用户检索、管理图书上下架状态、对图书信息进行更新、管理出版社信息等，所有的操作记录都需要进行保存，以便日后查用
7. 注册码管理：由注册码管理员对注册码进行管理，如监测到注册码数量不足时添加注册码、用户成功注销后回收注册码等
8. 用户管理：由系统管理员对客户端的普通用户和系统职工的信息进行维护管理，如冻结账户、更新用户信息等
9. 数据备份：由系统管理员提供对数据库数据的备份功能


# 五、数据库概念结构设计

## 1、实体及属性

根据网上图书系统的实际情况，对于系统数据库的设计需要考虑全面，既要涉及多种业务员数据，又要考虑信息代码表的需要性。根据功能需求分析，整个系统主要包含的实体及其属性分析如下：

1. 用户：<u>用户编号</u>，用户名，密码，姓名，性别，身份证号，手机号，邮箱，<u>注册码编号</u>，<u>用户状态编号</u>
2. 注册码：<u>注册码编号</u>，注册码，注册码状态
3. 用户状态：<u>用户状态编号</u>，用户状态状态名称
4. 下载记录：<u>下载记录编号</u>，书名，作者，下载时间，<u>用户编号</u>
5. 收藏记录：<u>收藏记录编号</u>，书名，作者，分类，关键词，收藏时间，<u>用户编号</u>
6. 图书：<u>图书编号</u>，书名，单价，出版社，出版时间，图书分类，关键词，<u>图书状态编号</u>
7. 作者：<u>作者编号</u>，作者姓名
8. 出版社：<u>出版社编号</u>，出版社名称，固定电话，地址
9. 关键词：<u>关键词编号</u>，关键词名称
10. 图书类别：<u>类别编号</u>，类别名称，类别描述
11. 图书状态：<u>图书状态编号</u>，图书状态名称
12. 职工信息：<u>员工编号</u>，姓名，性别，出生年月，手机号，家庭地址，用户密码，<u>职位编号</u>，<u>部门编号</u>，<u>角色编号</u>
13. 职位信息：<u>职位编号</u>，职位描述
14. 部门信息：<u>部门编号</u>，部门信息
15. 角色类型：<u>角色编号</u>，角色描述

## 2、实体间关系

1. 一个用户有且仅有一个注册码，一个注册码能且仅能供一位用户使用，所以用户与注册码之间是一对一的关系
2. 一个用户能且仅能拥有一种用户状态，一种用户状态可以对应多个用户，所以用户与用户状态之间是多对一的关系
3. 一个用户可以有多条下载记录，一条下载记录只能对应一个用户，所以用户与下载记录之间是一对多的关系
4. 一个用户可以有多条收藏记录，一条收藏记录只能对应一个用户，所以用户与收藏记录之间是一对多的关系
5. 一本书可以由多位作者编写，一位作者可以编写多本书，所以图书与作者之间是多对多的关系
6. 一本书有且仅有一个出版社，一个出版社可以出版多本书，所以图书与出版社之间是多对一的关系
7. 一本书只属于一种图书类别，一类图书可以有多本图书，所以图书与图书类别之间是多对一的关系
8. 一本书可以有多个关键词，一个关键词可以描述多本书，所以图书与关键词之间是多对多的关系
9. 一本书有且仅有一种图书状态，一种图书状态可以对应多本图书，所以图书与图书状态之间是多对一的关系
10. 一个职工可以同时担任多种职位，一个职位可以由多个人担任，所以职工与职位之间是多对多的关系
11. 一个职工可在多个部门任职，一个部门可以有多名职工，所以职工与部门之间是多对多的关系
12. 一个职工能且仅能对应本系统的一种角色，一种角色可有多名职工，所以职工与角色之间是多对一的关系
13. 一条收藏记录只能对应一种图书类别，一种图书类别可以对应多种收藏记录，所以收藏记录与图书类别之间是多对一的关系

## 3、实体关系图（ER）

![在这里插入图片描述](https://img-blog.csdnimg.cn/6dbc669fcc5141cd8266f14873f5d4d8.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAU3ByaW5nLV8tQmVhcg==,size_20,color_FFFFFF,t_70,g_se,x_16#pic_center)


# 六、数据库逻辑结构设计

1. 注册码：<u>注册码编号</u>，注册码，注册码状态

| 数据项    | 数据类型 | 主码 | 外码 | 唯一 |
| --------- | -------- | :--: | :--: | :--: |
| codeId    | INT(11)  |  Y   |      |      |
| code      | CHAR(8)  |      |      |  Y   |
| codeState | CHAR(8)  |      |      |      |

2. 用户状态：<u>用户状态编号</u>，用户状态名称

| 数据项      | 数据类型 | 主码 | 外码 |
| ----------- | -------- | :--: | :--: |
| userStateId | INT(11)  |  Y   |      |
| userState   | CHAR(8)  |      |      |

3. 用户：<u>用户编号</u>，用户名，密码，姓名，性别，身份证号，手机号，邮箱，<u>注册码</u>，<u>用户状态编号</u>

| 数据项       | 数据类型    | 主码 | 外码 | 唯一 |
| :----------- | :---------- | :--: | :--: | :--: |
| userId       | INT(11)     |  Y   |      |      |
| username     | VARCHAR(32) |      |      |  Y   |
| password     | VARCHAR(32) |      |      |      |
| name         | VARCHAR(32) |      |      |      |
| sex          | CHAR(1)     |      |      |      |
| idCard       | CHAR(32)    |      |      |      |
| phone        | CHAR(16)    |      |      |      |
| mail         | VARCHAR(32) |      |      |      |
| registerCode | CHAR(8)     |      |  Y   |      |
| userStateId  | INT(11)     |      |  Y   |      |

4. 图书状态：<u>图书状态编号</u>，图书状态

| 数据项      | 数据类型 | 主码 | 外码 |
| ----------- | -------- | :--: | :--: |
| bookStateId | INT(11)  |  Y   |      |
| bookState   | CHAR(8)  |      |      |

5. 图书类别：<u>类别编号</u>，类别名称，类别描述

| 数据项      | 数据类型    | 主码 | 外码 | 唯一 |
| ----------- | ----------- | :--: | :--: | :--: |
| categoryId  | INT(11)     |  Y   |      |      |
| category    | CHAR(1)     |      |      |  Y   |
| description | VARCHAR(32) |      |      |      |

6. 图书：<u>图书编号</u>，ISBN，书名，作者，单价，出版社，出版时间，关键词，保存路径，图书分类，<u>图书状态编号</u>

| 数据项    | 数据类型    | 主码 | 外码 | 唯一 |
| --------- | ----------- | :--: | :--: | :--: |
| bookId    | INT(11)     |  Y   |      |      |
| isbn      | CHAR(16)    |      |      |  Y   |
| title     | VARCHAR(32) |      |      |      |
| author    | VARCHAR(32) |      |      |      |
| price     | DOUBLE      |      |      |      |
| publisher | VARCHAR(32) |      |      |      |
| edition   | VARCHAR(32) |      |      |      |
| keyword   | VARCHAR(32) |      |      |      |
| url       | VARCHAR(64) |      |      |      |
| category  | CHAR(8)     |      |  Y   |      |
| bookState | CHAR(8)     |      |  Y   |      |
7. 下载记录：<u>下载记录编号</u>，书名，作者，下载时间，<u>用户名</u>

| 数据项   | 数据类型    | 主码 | 外码 |
| -------- | ----------- | :--: | :--: |
| recordId | INT(11)     |  Y   |      |
| title    | VARCHAR(32) |      |      |
| author   | VARCHAR(32) |      |      |
| time     | DATE        |      |      |
| username | VARCHAR(32) |      |  Y   |

8. 收藏记录：<u>isbn</u>，书名，作者，关键词，分类，收藏时间，<u>用户名</u>

| 数据项   | 数据类型    | 主码 | 外码 |
| -------- | ----------- | :--: | :--: |
| isbn     | CHAR(16)    |  Y   |  Y   |
| title    | VARCHAR(32) |      |      |
| author   | VARCHAR(32) |      |      |
| category | CHAR(1)     |      |      |
| keyword  | VARCHAR(32) |      |      |
| time     | DATE        |      |      |
| username | VARCHAR(32) |      |  Y   |

9. 作者：<u>作者编号</u>，作者姓名

| 数据项     | 数据类型    | 主码 | 外码 |
| ---------- | ----------- | :--: | :--: |
| authorId   | INT(11)     |  Y   |      |
| authorName | VARCHAR(32) |      |      |

10. 出版社：<u>出版社编号</u>，出版社名称，固定电话，地址

| 数据项        | 数据类型    | 主码 | 外码 |
| ------------- | ----------- | :--: | :--: |
| publisherId   | INT(11)     |  Y   |      |
| publisherName | VARCHAR(32) |      |      |
| tel           | VARCHAR(32) |      |      |
| address       | VARCHAR(64) |      |      |

11. 关键词：<u>关键词编号</u>，关键词名称

| 数据项      | 数据类型    | 主码 | 外码 |
| ----------- | ----------- | :--: | :--: |
| keywordId   | INT(11)     |  Y   |      |
| keywordName | VARCHAR(32) |      |      |

12. 职工信息：<u>职工编号</u>，姓名，性别，出生年月，手机号，家庭地址，用户密码，<u>职位编号</u>，<u>部门编号</u>，<u>角色编号</u>

| 数据项       | 数据类型    | 主码 | 外码 |
| ------------ | ----------- | :--: | :--: |
| employeeId   | INT(11)     |  Y   |      |
| name         | VARCHAR(32) |      |      |
| sex          | CHAR(1)     |      |      |
| birthday     | DATE        |      |      |
| phone        | CHAR(11)    |      |      |
| address      | VARCHAR(64) |      |      |
| password     | VARCHAR(32) |      |      |
| positionId   | INT(11)     |      |  Y   |
| departmentId | INT(11)     |      |  Y   |
| roleId       | INT(11)     |      |  Y   |

13. 职位信息：<u>职位编号</u>，职位描述

| 数据项       | 数据类型    | 主码 | 外码 |
| ------------ | ----------- | :--: | :--: |
| positionId   | INT(11)     |      |      |
| positionName | VARCHAR(32) |      |      |

14. 部门信息：<u>部门编号</u>，部门信息

| 数据项         | 数据类型    | 主码 | 外码 |
| -------------- | ----------- | :--: | :--: |
| departmentId   | INT(11)     |  Y   |      |
| departmentName | VARCHAR(32) |      |      |

15. 角色类型：<u>角色编号</u>，角色描述

| 数据项          | 数据类型    | 主码 | 外码 |
| --------------- | ----------- | :--: | :--: |
| roleId          | INT(11)     |  Y   |      |
| roleDescription | VARCHAR(32) |      |      |

# 七、数据库生成 SQL 语句

1. 建立数据库

```sql
-- 1.创建数据库
CREATE DATABASE ebrss;
USE ebrss;
```

2. 创建用户注册码表

```sql
-- 2.创建用户注册码表
CREATE TABLE `Code`(
`codeId` INT(11) NOT NULL AUTO_INCREMENT COMMENT '注册码编号',
`registerCode` CHAR(8) NOT NULL COMMENT '注册码',
`codeState` CHAR(8) NOT NULL DEFAULT 'unused' COMMENT '注册码状态',
PRIMARY KEY pk_codeId (`codeId`),
UNIQUE INDEX idx_code (`registerCode`)
)ENGINE = INNODB CHARSET = UTF8;
```

3. 创建用户状态表

```sql
-- 3.创建用户状态表
CREATE TABLE `UserState`(
`userStateId` INT(11) NOT NULL COMMENT '用户状态编号',
`userState` CHAR(8) NOT NULL COMMENT '用户状态',
PRIMARY KEY pk_userStateId (`userStateId`)
)ENGINE = INNODB CHARSET = UTF8;
```

4. 创建用户信息表

```sql
-- 4.创建用户信息表
CREATE TABLE `User`(
`userId` INT(11) NOT NULL AUTO_INCREMENT COMMENT '用户编号',
`username` VARCHAR(32) NOT NULL COMMENT '用户名',
`password` VARCHAR(32) NOT NULL COMMENT '密码',
`name` VARCHAR(32) NOT NULL COMMENT '姓名',
`sex` CHAR(1) NOT NULL COMMENT '性别',
`idCard` CHAR(32) NOT NULL COMMENT '身份证号',
`phone` CHAR(16) NOT NULL COMMENT '手机号',
`mail` VARCHAR(32) NOT NULL COMMENT '邮箱',
`registerCode` CHAR(8) NOT NULL COMMENT '用户注册码',
`userStateId` INT(11) NOT NULL DEFAULT 0 COMMENT '用户状态编号',
PRIMARY KEY pk_userid (`userId`),
UNIQUE INDEX idx_username (`username`),
FOREIGN KEY fk_code (`registerCode`) REFERENCES `Code`(`registerCode`),
FOREIGN KEY fk_userStateId (`userStateId`) REFERENCES `UserState`(`userStateId`)
)ENGINE = INNODB CHARSET = UTF8;
```

5. 创建更新注册码状态触发器

```sql
-- 5.创建触发器 update_code_state, 当用户成功注册时，修改 RegisterCode 表中对应注册码状态为已使用
DELIMITER $$
CREATE TRIGGER update_code_state AFTER INSERT ON `User` FOR EACH ROW
BEGIN
   UPDATE `Code` SET `codeState` = 'used' WHERE new.`registerCode` = `registerCode`;
END $$
DELIMITER ;
```

6. 创建图书状态表

```sql
-- 6.创建图书状态表
CREATE TABLE `BookState`(
`bookStateId` INT(11) NOT NULL COMMENT '图书状态编号',
`bookState` CHAR(8) NOT NULL COMMENT '图书状态',
PRIMARY KEY pk_bookStateId (bookStateId)
)ENGINE = INNODB CHARSET = UTF8;
```

7. 创建图书分类表

```sql
-- 7.创建图书分类表
CREATE TABLE `Category`(
`categoryId` INT(11) NOT NULL AUTO_INCREMENT COMMENT '类别编号',
`category` CHAR(1) NOT NULL COMMENT '类别',
`description` VARCHAR(32) NOT NULL COMMENT '类别描述',
PRIMARY KEY pk_categoryId (categoryId),
UNIQUE INDEX idx_category (`category`)
)ENGINE = INNODB CHARSET = UTF8;
```

8. 创建图书信息表

```sql
-- 8.创建图书信息表
CREATE TABLE `Book`(
`bookId` INT(11) NOT NULL AUTO_INCREMENT COMMENT '图书编号',
`isbn` CHAR(16) NOT NULL COMMENT '图书isbn',
`title` VARCHAR(32) NOT NULL COMMENT '书名',
`author` VARCHAR(32) NOT NULL COMMENT '作者',
`price` DOUBLE NOT NULL COMMENT '单价',
`publisher` VARCHAR(32) NOT NULL COMMENT '出版社',
`edition` VARCHAR(32) NOT NULL COMMENT '版次',
`keyword` VARCHAR(32) NOT NULL COMMENT '关键词',
`url` VARCHAR(64) NOT NULL COMMENT '图书保存路径',
`category` CHAR(1) NOT NULL COMMENT '类别',
`bookState` CHAR(8) NOT NULL COMMENT '图书状态编号',
PRIMARY KEY pk_bookId (`bookId`),
UNIQUE INDEX idx_isbn (`isbn`),
FOREIGN KEY fk_category (`category`) REFERENCES `Category`(`category`)
)ENGINE = INNODB CHARSET = UTF8;
```
9. 创建图书信息视图

```sql
-- 9.创建图书信息视图
CREATE VIEW view_book AS SELECT isbn, title, author, keyword, category, bookState FROM `Book`;
```

10. 创建下载记录表

```sql
-- 10.创建下载记录表
CREATE TABLE `Record`(
`recordId` INT(11) NOT NULL AUTO_INCREMENT COMMENT '下载记录编号',
`title` VARCHAR(32) NOT NULL COMMENT '书名',
`author` VARCHAR(32) NOT NULL COMMENT '作者',
`time` DATE NOT NULL COMMENT '下载时间',
`username` VARCHAR(32) NOT NULL COMMENT '用户名',
PRIMARY KEY pk_recordId (`recordId`),
FOREIGN KEY fk_username(`username`) REFERENCES `User`(`username`)
)ENGINE = INNODB CHARSET = UTF8;
```

11. 创建收藏记录表

```sql
-- 11.创建收藏记录表
CREATE TABLE `Favorite`(
`isbn` CHAR(16) NOT NULL COMMENT '图书isbn',
`title` VARCHAR(32) NOT NULL COMMENT '书名',
`author` VARCHAR(32) NOT NULL COMMENT '作者',
`keyword` VARCHAR(32) NOT NULL COMMENT '关键字',
`category` CHAR(1) NOT NULL COMMENT '类别',
`time` DATE NOT NULL COMMENT '收藏时间',
`username` VARCHAR(32) NOT NULL COMMENT '用户名',
FOREIGN KEY fk_isbn (`isbn`) REFERENCES `Book`(`isbn`),
FOREIGN KEY fk_username (`username`) REFERENCES `User`(`username`),
PRIMARY KEY pk_user_book (isbn,username)
)ENGINE = INNODB CHARSET = UTF8;
```

12. 创建作者信息表

```sql
-- 12.创建作者信息表
CREATE TABLE `Author`(
`authorId` INT(11) NOT NULL AUTO_INCREMENT COMMENT '作者编号',
`authorName` VARCHAR(32) NOT NULL COMMENT '作者姓名',
PRIMARY KEY fk_authorId(`authorId`)
)ENGINE = INNODB CHARSET = UTF8;
```

13. 创建出版社信息表

```sql
-- 13.创建出版社信息表
CREATE TABLE `Publisher`(
`publisherId` INT(11) NOT NULL AUTO_INCREMENT COMMENT '出版社编号',
`publisherName` VARCHAR(32) NOT NULL COMMENT '出版社名称',
`tel` VARCHAR(32) NOT NULL COMMENT '出版社固定电话',
`address` VARCHAR(64) NOT NULL COMMENT '出版社地址',
PRIMARY KEY fk_publisherId (`publisherId`)
)ENGINE = INNODB CHARSET = UTF8;
```

14. 创建图书关键词信息表

```sql
-- 14.创建图书关键词信息表
CREATE TABLE `Keyword`(
`keywordId` INT(11) NOT NULL AUTO_INCREMENT COMMENT '关键词编号',
`keywordName` VARCHAR(32) NOT NULL COMMENT '关键词名称',
PRIMARY KEY pk_keywordId (`keywordId`)
)ENGINE = INNODB CHARSET = UTF8;
```

15. 创建系统人员角色表

```sql
-- 15.创建角色类型表
CREATE TABLE `Role`(
`roleId` INT(11) NOT NULL AUTO_INCREMENT COMMENT '角色编号',
`roleDescription` VARCHAR(32) NOT NULL COMMENT '角色描述',
PRIMARY KEY pk_roleId (`roleId`)
)ENGINE = INNODB CHARSET = UTF8;
```

16. 创建部门信息表

```sql
-- 16.创建部门信息表
CREATE TABLE `Department`(
`departmentId` INT(11) NOT NULL AUTO_INCREMENT COMMENT '部门编号',
`departmentName` VARCHAR(32) NOT NULL COMMENT '部门名称',
PRIMARY KEY pk_departmentId (`departmentId`)
)ENGINE = INNODB CHARSET = UTF8;
```

17. 创建职位信息表

```sql
-- 17.创建职位信息表
CREATE TABLE `Position`(
`positionId` INT(11) NOT NULL AUTO_INCREMENT COMMENT '职位编号',
`positionName` VARCHAR(32) NOT NULL COMMENT '职位名称',
PRIMARY KEY pk_positionId (`positionId`)
)ENGINE = INNODB CHARSET = UTF8;
```

18. 创建职工信息表

```sql
-- 18.创建职工信息表
CREATE TABLE `Employee`(
`employeeId` INT(11) NOT NULL AUTO_INCREMENT COMMENT '职工号',
`name` VARCHAR(32) NOT NULL COMMENT '姓名',
`sex` CHAR(1) NOT NULL COMMENT '性别',
`birthday` DATE NOT NULL COMMENT '生日',
`phone` CHAR(16) NOT NULL COMMENT '手机号',
`address` VARCHAR(64) NOT NULL COMMENT '住址',
`password` VARCHAR(32) NOT NULL COMMENT '密码',
`positionId` INT(11) NOT NULL COMMENT '职位编号',
`departmentId` INT(11) NOT NULL COMMENT '部门编号',
`roleId` INT(11) NOT NULL COMMENT '角色编号',
PRIMARY KEY pk_employeeId (`employeeId`),
FOREIGN KEY fk_positionId (`positionId`) REFERENCES `Position`(`positionId`),
FOREIGN KEY fk_departmentId (`departmentId`) REFERENCES `Department`(`departmentId`),
FOREIGN KEY fk_roleId (`roleId`) REFERENCES `Role`(`roleId`)
)ENGINE = INNODB CHARSET = UTF8;
```
19. 插入初始数据

```sql
-- 插入用户状态信息
INSERT INTO `UserState` VALUES (0,'normal');
INSERT INTO `UserState` VALUES (1,'freezing');
-- 插入图书状态信息
INSERT INTO `BookState` VALUES (0,'on');
INSERT INTO `BookState` VALUES (1,'off');
-- 插入图书分类信息
INSERT INTO `category` VALUES (1,'A','马列主义、毛泽东思想、邓小平理论'),(2,'B','哲学、宗教'),(3,'C','社会科学总论'),(4,'D','政治、法律'),(5,'E','军事'),(6,'F','经济'),(7,'G','文化、科学、教育、体育'),(8,'H','语言、文字'),(9,'I','文学'),(10,'J','艺术'),(11,'K','历史、地理'),(12,'L',''),(13,'M',''),(14,'N','自然科学总论'),(15,'O','数理科学和化学'),(16,'P','天文学、地球科学'),(17,'Q','生物科学'),(18,'R','医药、卫生'),(19,'S','农业科学'),(20,'T','工业技术'),(21,'U','交通运输'),(22,'V','航空、航天'),(23,'W',''),(24,'X','环境科学、劳动保护科学(安全科学)'),(25,'Y',''),(26,'Z','综合性图书');
-- 插入图书数据
INSERT INTO `book` VALUES (1,'9787111213826','Java编程思想(第4版)','陈昊鹏 译',108,'机械工业出版社','2020年10月第1版','Java语言程序设计','d:\\book\\Java编程思想(第4版).pdf','T','on'),(2,'0131872486','Thing in Java(Fourth Edition)','Bruce Eckel',0,'President, MindView, Inc. ','2006年1月第1版','Java语言程序设计','d:\\book\\Thing in Java(Fourth Edition).pdf','T','on'),(3,'97871113700048','Java并发编程实战','童云兰 译',69,'机械工业出版社','2012年2月第1版','Java、Java并发编程、并发编程','d:\\book\\Java并发编程实战.pdf','T','on'),(4,'9787111508243','Java并发编程的艺术','方腾飞 魏鹏 程晓明',0,'机械工业出版社','1970年1月第1版','Java、Java并发编程、并发编程','d:\\book\\Java并发编程的艺术.pdf','T','on'),(5,'9787111547426','Java核心技术卷I基础知识(原书第10版)','周立新 陈波 叶乃文 杜永萍 译',119,'机械工业出版社','2016年9月第1版','Java程序设计、计算机科学','d:\\book\\Java核心技术卷I基础知识(原书第10版).pdf','T','on'),(6,'9787111349662','深入理解Java虚拟机JVM高级特性与最佳实践','周志明',69,'机械工业出版社','2011年9月第1版','Java语言程序设计、JVM、Java虚拟机','d:\\book\\深入理解Java虚拟机JVM高级特性与最佳实践.pdf','T','on');
```
# 八、功能演示
## 1、客户端

1. 用户登录：用户登录时先在客户端检查用户名和密码是否为空，若存在为空则提示用户重新输入

![在这里插入图片描述](https://img-blog.csdnimg.cn/38d719d381414cd5bb80872c2c7c21ec.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAU3ByaW5nLV8tQmVhcg==,size_11,color_FFFFFF,t_70,g_se,x_16#pic_center)


2. 用户登录：请求服务器验证用户输入的用户名与密码，若用户名不存在或用户名与密码不匹配，则提示用户信息错误，检查后重新输入或进行注册

    ```java
    String sql = "SELECT username, password FROM User WHERE username = ? AND password = ?;";
    ```

![在这里插入图片描述](https://img-blog.csdnimg.cn/8872fd791fa4426486da7773b1231294.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAU3ByaW5nLV8tQmVhcg==,size_11,color_FFFFFF,t_70,g_se,x_16#pic_center)


3. 用户注册：先在客户端检查每一项注册信息都不允许为空，存在为空的信息则提示用户重新输入

![在这里插入图片描述](https://img-blog.csdnimg.cn/fa49376e3d5c43cca99f814d6b06d8a2.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAU3ByaW5nLV8tQmVhcg==,size_14,color_FFFFFF,t_70,g_se,x_16#pic_center)


4. 用户注册：当用户输入的所有注册信息内容都不为空时，客户端利用正则表达式对每一项内容进行合法性校验，如用户名必须只包含英文字母、数字或下划线且必须由英文字母开头、密码位数必须大于等于 6 位、两次输入的密码必须一致等等，存在格式非法的内容则提示用户检查后重新输入

![在这里插入图片描述](https://img-blog.csdnimg.cn/14de4bb08ad14178a97d92ee18481406.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAU3ByaW5nLV8tQmVhcg==,size_14,color_FFFFFF,t_70,g_se,x_16#pic_center)


5. 用户注册：当用户输入的所有信息均不为空且符合格式要求的情况下，请求服务器验证用户输入的用户名是否存在，若存在则提示当前用户名已存在，提示用户重新输入用户名

    ```java
    String sql = "SELECT username FROM User WHERE username = ?;";
    ```

![在这里插入图片描述](https://img-blog.csdnimg.cn/4d067295e4704376afbfd3e31cae1a48.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAU3ByaW5nLV8tQmVhcg==,size_14,color_FFFFFF,t_70,g_se,x_16#pic_center)


6. 用户注册：在第（5）步的基础上，检查用户输入的注册码是否合法即数据库注册码表中是否存在相同的注册码且处于未使用状态，若合法则用户注册成功，否则提示用户注册码不存在，提示用户需先联系管理员获取注册码

    ```java
    String sql = "SELECT registerCode FROM Code WHERE registerCode = ? AND codeState = 'unused';";
    ```

![在这里插入图片描述](https://img-blog.csdnimg.cn/d8a1258babb040d9b2e616b6f2617ec9.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAU3ByaW5nLV8tQmVhcg==,size_14,color_FFFFFF,t_70,g_se,x_16#pic_center)


7. 用户注册：在第（6）的基础上，用户输入有效的注册码，注册成功，提醒用户返回登录，注册成功的同时用户数据表中的触发器 update_code_state 自动修改对应注册码状态为已使用

    ```java
    String sql = "INSERT INTO User(username,password,name,sex,idCard,phone,mail,registerCode) VALUES(?,?,?,?,?,?,?,?);";
    ```



![在这里插入图片描述](https://img-blog.csdnimg.cn/a707010f508e465dbb665d336536de43.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAU3ByaW5nLV8tQmVhcg==,size_14,color_FFFFFF,t_70,g_se,x_16#pic_center)


8. 用户登录：用户注册成功，输入正确的用户名密码，登录成功，进入服务系统

![在这里插入图片描述](https://img-blog.csdnimg.cn/befced499cfc47a5a8354540131a8e15.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAU3ByaW5nLV8tQmVhcg==,size_20,color_FFFFFF,t_70,g_se,x_16#pic_center)


9. 图书检索：用户根据需要的图书信息输入不同的检索条件进行查询，也可查询所有图书信息，此时客户端发送请求图书信息请求到服务器，服务器根据不同的请求不同的检索条件从图书视图中查询后返回不同的图书信息，客户端处理图书信息格式后显示

    ```java
    String sql = "SELECT * FROM view_book;";
    String sql = "SELECT * FROM view_book WHERE title LIKE ? AND bookState = 'on';";
    String sql = "SELECT * FROM view_book WHERE author LIKE ? AND bookState = 'on';";
    String sql = "SELECT * FROM view_book WHERE category = ? AND bookState = 'on';";
    String sql = "SELECT * FROM view_book WHERE keyword LIKE ? AND bookState = 'on';";
    ```

![在这里插入图片描述](https://img-blog.csdnimg.cn/65a0949c326243d396929bf0ae54ea36.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAU3ByaW5nLV8tQmVhcg==,size_20,color_FFFFFF,t_70,g_se,x_16#pic_center)

10. 图书下载：用户根据从服务器请求到的图书信息，选择自己想要的图书信息，而后立即下载，若用户未选择任何图书，则提示用户先选择图书而后再行下载

![在这里插入图片描述](https://img-blog.csdnimg.cn/1151bbb7f0a24305b63bbe8bb165abad.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAU3ByaW5nLV8tQmVhcg==,size_20,color_FFFFFF,t_70,g_se,x_16#pic_center)


11. 图书下载：用户选择图书并进行下载，提示用户选择图书保存位置，若保存路径无效则图书下载失败。服务器根据用户选择的图书信息，从数据库表中查询出当前图书的保存路径，根据路径从磁盘加载文件而后发送到客户端，客户端接收服务器的文件数据并进行保存

    ```java
    String sql = "SELECT url FROM Book WHERE isbn = ?;";
    ```
- 选择图书保存路径

![在这里插入图片描述](https://img-blog.csdnimg.cn/c7fb284a79444e628119c5cac132efd8.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAU3ByaW5nLV8tQmVhcg==,size_20,color_FFFFFF,t_70,g_se,x_16#pic_center)

- 图书下载并保存成功

![在这里插入图片描述](https://img-blog.csdnimg.cn/c927ece03f524adba38f1522eed67c9e.png#pic_center)

12. 保存下载记录：用户成功下载一本图书的同时，自动为用户添加下载记录

    ```java
    String sql = "INSERT INTO Record(title,author,time,username) VALUES (?,?,?,?);";
    ```

13. 查询用户下载记录，客户端向服务器发出根据登录服务系统用户名查询当前用户的下载记录，若不存在该用户的下载记录则提示用户暂无下载记录，否则显示从服务器查询到的所有该用户的下载记录

    ```java
    String sql = "SELECT * FROM Record WHERE username = ?;";
    ```

![在这里插入图片描述](https://img-blog.csdnimg.cn/b80090b6768d421cab5617c48e3a148d.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAU3ByaW5nLV8tQmVhcg==,size_20,color_FFFFFF,t_70,g_se,x_16#pic_center)

14. 收藏图书：当用户从服务器查询到需要的图书信息时，也可选择加入到个人收藏夹，提示用户先选择想要加入到收藏夹的图书，若收藏夹中已存在对应的图书记录，则提示用户不能重复收藏
    ```java
    String sql = "INSERT INTO Record(title,author,time,username) VALUES (?,?,?,?);";
    ```
- 收藏成功：用户添加图书信息到个人收藏夹成功

![在这里插入图片描述](https://img-blog.csdnimg.cn/733274cffc384baba9ab67acd474bf03.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAU3ByaW5nLV8tQmVhcg==,size_20,color_FFFFFF,t_70,g_se,x_16#pic_center)
- 收藏失败：一个用户不能重复收藏同一条图书记录

![在这里插入图片描述](https://img-blog.csdnimg.cn/08c3b1b908ed400a830e52f876a062fc.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAU3ByaW5nLV8tQmVhcg==,size_20,color_FFFFFF,t_70,g_se,x_16#pic_center)



15. 取消收藏：用户选择个人收藏夹中想要取消收藏的图书信息

    ```java
    String sql = "SELECT * FROM Favorite WHERE username = ?;";
    String sql = "DELETE FROM Favorite WHERE isbn = ? AND username = ?;";
    ```

![在这里插入图片描述](https://img-blog.csdnimg.cn/f25cf7ab4fa44bd8890cc7140881d250.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAU3ByaW5nLV8tQmVhcg==,size_20,color_FFFFFF,t_70,g_se,x_16#pic_center)


16. 图书上传：客户端用户选择图书文件，客户端加载后将图书数据发送的服务器，服务器接收图书数据并保存到服务器云盘

- 选择文件路径

![在这里插入图片描述](https://img-blog.csdnimg.cn/1e72fd0351864319b7058973831a7757.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAU3ByaW5nLV8tQmVhcg==,size_20,color_FFFFFF,t_70,g_se,x_16#pic_center)

- 图书上传成功

![在这里插入图片描述](https://img-blog.csdnimg.cn/6cd9729baffb4bdfa40894b0d5d77b1b.png#pic_center)



17. 账号管理：个人信息修改，若个人信息未发生变更则不允许修改，发送用户修改请求和修改后的信息到服务器，服务器更新相关数据表的用户信息

    ```sql
    String sql = "SELECT * FROM User WHERE username = ?;";
    String sql = "UPDATE User SET password = ?, phone = ?, mail = ? WHERE username = ?;";
    ```

- 信息未变更，不能修改

![在这里插入图片描述](https://img-blog.csdnimg.cn/f179df16f538437785b4cf2135fff35f.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAU3ByaW5nLV8tQmVhcg==,size_20,color_FFFFFF,t_70,g_se,x_16#pic_center)

- 个人信息修改成功

![在这里插入图片描述](https://img-blog.csdnimg.cn/f5d5ee074bf84169a464f7d4dc654a67.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAU3ByaW5nLV8tQmVhcg==,size_20,color_FFFFFF,t_70,g_se,x_16#pic_center)

18. 用户注销：用户选择注销账户，再次确认是否注销，确认注销则发送用户注销请求到服务器，服务器从用户信息表中删除相关用户信息，删除该用户的所有下载记录以及收藏记录，返回用户登录界面

    ```java
    String sql = "DELETE FROM Favorite WHERE username = ?;";
    String sql = "DELETE FROM User WHERE username = ?;";
    String sql = "DELETE FROM Record WHERE username = ?;";
    ```

- 确认注销：提示用户是否注销

![在这里插入图片描述](https://img-blog.csdnimg.cn/91d96adf5ae747ecb58e27de60d9d616.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAU3ByaW5nLV8tQmVhcg==,size_20,color_FFFFFF,t_70,g_se,x_16#pic_center)

- 用户注销成功

![在这里插入图片描述](https://img-blog.csdnimg.cn/6f7b32d641594b03bd751def3b31b685.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAU3ByaW5nLV8tQmVhcg==,size_20,color_FFFFFF,t_70,g_se,x_16#pic_center)



## 2、服务端

1. 响应客户端的各种请求，分类处理请求，控制台打印客户端的请求信息

![在这里插入图片描述](https://img-blog.csdnimg.cn/eb80caa9f34243cfb81bd2783d1f5e92.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAU3ByaW5nLV8tQmVhcg==,size_20,color_FFFFFF,t_70,g_se,x_16#pic_center)


2. 新增图书信息到数据库图书信息表
    ```java
    String sql = "INSERT INTO Book(isbn, title, author, price, publisher, edition, keyword, url, category, bookState) VALUES(?,?,?,?,?,?,?,?,?,?);";
    ```
    ![在这里插入图片描述](https://img-blog.csdnimg.cn/8eeada1a69de4bca844c342923271553.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAU3ByaW5nLV8tQmVhcg==,size_20,color_FFFFFF,t_70,g_se,x_16#pic_center)

3. 以事务批处理方式添加随机生成的注册码到数据库注册码信息表，添加成功则提交事务，否则回滚事务，并且修改自增 id 为事务开始前的值

    ```java
    // 关闭自动提交，开启事务
    connection.setAutoCommit(false);
    // 注册码不存在重复的情况下全部插入成功则提交事务，否则回滚并修改自增 id 的值为事务开始前的值
    if (paramsCounts == updateSuccessCounts) {
        connection.commit();
        return true;
    } else {
        connection.rollback();
        codeService.resetToOldId(oldId);
    }
    
    String sql = "INSERT INTO Code(registerCode) VALUES(?);";
    String sql = "SELECT MAX(codeId) FROM Code FOR UPDATE;";
    String sql = "ALTER TABLE Code AUTO_INCREMENT = ?";
    ```

- 添加注册码成功

![在这里插入图片描述](https://img-blog.csdnimg.cn/e3f77b4e084248bd9911be76ca18a565.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAU3ByaW5nLV8tQmVhcg==,size_20,color_FFFFFF,t_70,g_se,x_16#pic_center)

- 添加注册码失败

![在这里插入图片描述](https://img-blog.csdnimg.cn/402fef99dc1249bd8aa01c93e9e93555.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAU3ByaW5nLV8tQmVhcg==,size_20,color_FFFFFF,t_70,g_se,x_16#pic_center)

4. 备份数据库中的所有数据到指定路径，同时备份数据库数据和数据表结构

    ```java
    // Combining the parameters into a command
    String command = "mysqldump -h" + serverIp + " -u" + username + " -p" + password + " -B " + dbName + " > " + fileSavePath + "\\" + dbName + ".sql";
    try {
        Runtime.getRuntime().exec("cmd /c" + command);
        JOptionPane.showMessageDialog(null, "Database backup successfully", "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
    } catch (IOException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Database backup failed, try again later", "ERROR", JOptionPane.ERROR_MESSAGE);
    }
    ```

- 选择备份文件保存路径

![在这里插入图片描述](https://img-blog.csdnimg.cn/affcdeef531348aea3a53a5c43ce63b3.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAU3ByaW5nLV8tQmVhcg==,size_20,color_FFFFFF,t_70,g_se,x_16#pic_center)

- 数据库备份成功，对应目录生成数据库备份文件

![在这里插入图片描述](https://img-blog.csdnimg.cn/c43f1be7aa5043a99c3dfce1042dafc9.png#pic_center)
