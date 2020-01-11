# 软件工程课程项目

**股票交易系统**



股票交易系统分为证券账户业务、资金账户业务、交易客户端、中央交易系统、网上信息发布、交易系统管理六个部分，接下来详细说明一下各个模块的配置需求、配置步骤以及使用，以及数据库用到的表



## 1. 各模块配置运行说明

### 1.1 证券账户业务

**后端配置运行**
同资金账户业务

**前端配置运行**
react
```
npm i
npm run start
```

### 1.2 资金账户业务

**后端配置运行**

基于maven构建

数据库配置在./src/main/resources/application.properties

第一次运行需要将 spring.jpa.hibernate.ddl-auto 设置成 create

运行代码命令如下

```
mvn package
cd target
java -jar gs-rest-service-0.1.0.jar
```

一开始没有admin账号，需要自己去改数据库加或者跑以下代码

```sql
INSERT INTO `capital_account_banker` (`id`, `password`, `status`) VALUES
('admin', '21232f297a57a5a743894a0e4a801fc3', '0');
```

默认帐号密码都是admin(密码存储时加密)



**前端配置运行**

需要React环境

运行代码如下：

```
npm start
```



### 1.3 交易客户端
**前端**

https://github.com/Robert-xiaoqiang/YetAnotherStockTradingClientFrontEnd

框架与库：Node.js React AntDesign

配置：npm install

运行: npm run start

**后端**

https://github.com/Robert-xiaoqiang/StockTradingClientBackEnd

架构与库：JavaSpringBoot maven mysql node.js

打包：mvn package

运行：java -jar /target/stock-trading-client-0.0.1-SNAPSHOT.jar --server.port=8080

https://github.com/Jiaofuf/StockClientQueryBackEnd

配置：npm install

运行：node app.js



### 1.4 中央交易系统
后端文件夹为instruction

后端：

* Eclipse 
* Spring5.1
* （项目已通过web.xml,pom.xml，springcontext.xml配置）




### 1.5 网上信息发布

在子系统根目录下，
In the project directory, you can run:

` HOW TO RUN IT? `

`yarn start`

`that's all`



### 1.6 交易系统管理
前端：  
&ensp;&ensp;&ensp;&ensp;React  
&ensp;&ensp;&ensp;&ensp;Ant Design  
后端：  
&ensp;&ensp;&ensp;&ensp;IntelliJ IDEA  
&ensp;&ensp;&ensp;&ensp;Tomcat 9.0




## 2. 数据库表格

### 2.1 证券账户业务

证券账户管理员表
- 只有证券账户业务模块用到
```
stock_account_banker | CREATE TABLE `stock_account_banker` (
  `id` varchar(255) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1
```

证券账户用户表
- 其中的accout_id即为每个证券账户的primary key
- 资金账户用到
- 交易客户端用到
- 其中status代表证券账户是否冻结（规定：证券账户冻结==证券账户下的所有持有股票被冻结）
```
stock_account_user | CREATE TABLE `stock_account_user` (
  `account_id` bigint(20) NOT NULL,
  `account_type` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`account_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1
```

法人类型证券账户表
```
stock_account_legal_user | CREATE TABLE `stock_account_legal_user` (
  `account_id` bigint(20) NOT NULL,
  `authorize_idnum` varchar(255) NOT NULL,
  `authorize_address` varchar(255) NOT NULL,
  `authorize_name` varchar(255) NOT NULL,
  `authorize_phone` varchar(255) NOT NULL,
  `legal_idnum` varchar(255) NOT NULL,
  `legal_address` varchar(255) NOT NULL,
  `legal_name` varchar(255) NOT NULL,
  `legal_num` varchar(255) NOT NULL,
  `legal_phone` varchar(255) NOT NULL,
  `license_num` varchar(255) NOT NULL,
  PRIMARY KEY (`account_id`),
  UNIQUE KEY `UK_97vgtmq599qjj4ecjvhymcaki` (`legal_num`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1
```

个人类型证券账户表
```
stock_account_personal_user | CREATE TABLE `stock_account_personal_user` (
  `account_id` bigint(20) NOT NULL,
  `id_num` varchar(255) NOT NULL,
  `address` varchar(255) NOT NULL,
  `agency` int(11) NOT NULL,
  `agent_idnum` varchar(255) DEFAULT NULL,
  `date` datetime NOT NULL,
  `degree` varchar(255) NOT NULL,
  `gender` varchar(255) NOT NULL,
  `job` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `organization` varchar(255) NOT NULL,
  `phone_num` varchar(255) NOT NULL,
  PRIMARY KEY (`account_id`),
  UNIQUE KEY `UK_2fqplofpy35xg94fujofnmh4k` (`id_num`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1
```




### 2.2 资金账户业务

资金账户用户表

```
capital_account | CREATE TABLE `capital_account` (
  `user_id` varchar(255) NOT NULL,
  `id` varchar(255) DEFAULT NULL,
  `fund` decimal(10,2) DEFAULT NULL,
  `freezing` decimal(10,2) NOT NULL DEFAULT '0.00',
  `login_pwd` varchar(255) DEFAULT NULL,
  `securities_id` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `user_right` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8
```

使用情况：

* 被中央交易系统使用
* 被交易客户端使用



资金账户管理员表

```
capital_account_banker | CREATE TABLE `capital_account_banker` (
  `id` varchar(255) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8
```

使用情况

* 仅本模块使用



### 2.3 交易客户端
资金账户和证券账户关联信息表
```
create table fund_security(
	  fund_id varchar(255) primary key,/*资金账户id*/
    security_id varchar(255) not null/*证券账户id*/
);
```

用户持有股票信息表
```
create table security_stock(
	  id integer auto_increment primary key, /*自增长主键、没用*/
    security_id varchar(255) not null,/*证券账户id*/
    stock_id varchar(255) not null,/*股票id*/
    ava_price decimal(10, 5),/*平均持有价格*/
    num integer/*持有数量*/
);
```

用户指令集表
```
create table instructionset(
     Number int(15)  auto_increment primary key,
     ID char(20),
     User_ID char(20),
     Buy tinyint(1),
     Amount int(15),
     Date char(20),
     Price decimal(10, 5),
     State tinyint(1)
);
```

股票信息表
```
CREATE TABLE `stock_inf` (
  `stock_id` char(10) NOT NULL,
  `stock_name` varchar(20) DEFAULT NULL,
  `stock_price` decimal(7,2) DEFAULT NULL,
  `upper_limit` decimal(5,2) DEFAULT NULL,
  `lower_limit` decimal(5,2) DEFAULT NULL,
  `stock_state` int(1) DEFAULT NULL,
  `stock_authority` int(2) DEFAULT NULL,
  PRIMARY KEY (`stock_id`)
) character set = utf8;
alter table stock_inf add limit_state int(1);
update stock_inf set limit_state=1;
```


### 2.4 中央交易系统
指令存储表格，用于存储所有的交易记录
```
create table instructionset(
     Number int(15)  auto_increment primary key,//主键
     ID char(20),//股票ID
     User_ID char(20),//用户ID
     Buy tinyint(1),//买或者卖
     Amount int(15),//数量
     Date char(20),//时间
     Price decimal(10, 5),//价格
     State tinyint(1)//状态
);
```


### 2.5 网上信息发布
无


### 2.6 交易系统管理
stock_inf记录股票的基本信息，

```
'CREATE TABLE `stock_inf` (
  `stock_id` char(10) NOT NULL,  
  `stock_name` varchar(20) DEFAULT NULL,  
  `stock_price` decimal(7,2) DEFAULT NULL,  
  `upper_limit` decimal(5,2) DEFAULT NULL,  
  `lower_limit` decimal(5,2) DEFAULT NULL,  
  `stock_state` int(1) DEFAULT NULL,  
  `stock_authority` int(2) DEFAULT NULL,  
  `limit_state` int(1) DEFAULT NULL,  
  PRIMARY KEY (`stock_id`)  
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci'
```



