
use stocksystem;
create table security_stock(
	id integer auto_increment primary key, /*自增长主键、没用*/
    User_id varchar(255) not null,/*证券账户id*/
    stock_id varchar(255) not null,/*股票id*/
    price decimal(10, 5),/*平均持有价格*/
    num integer/*持有数量*/
);
insert into security_stock(User_id, stock_id, price, num) values('zfxzfx', 'sh000002', 10.0, 100);

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

/*
create table record (
     record_id int auto_increment primary key,
     fund_id varchar(255),
     stock_id varchar(255),
     buy tinyint(1),
     amount int(15),
     record_timestamp varchar(255),
     price decimal(10, 5),
     state tinyint(1)
);
drop table record;
*/

insert into instructionset values(1,'111','123456',0,10000,2019060819270808,20.12,1);
insert into instructionset values(2,'A111','zfx',0,10000,2019060819270808,20.12,1);
insert into instructionset values(3,'A112','zfx',0,10000,2019060819270808,20.12,1);

insert into instructionset values(4,'A113','zfx',0,10000,2019060819270808,20.12,0);

insert into instructionset values(5,'sh000002','zfx',0,10000,2019060819270808,20.12,0);

insert into instructionset values(6,'5','zfx',0,10000,2019052719270808,12.33,1);
insert into instructionset values(7,'3','zfx',0,10000,2019061319270808,12.23,1);
insert into instructionset values(8,'2','zfx',0,10000,2019051619270808,12.33,1);
insert into instructionset values(9,'4','zfx',0,10000,2019061609270808,12.33,1);
insert into instructionset values(10,'1','zfx',0,5000,2019061119270808,12.33,1);
insert into instructionset values(11,'1','zfx',0,10000,2019061519270808,16.5,1);
insert into instructionset values(12,'2','zfx',0,10000,2019061612270808,17.4,1);
insert into instructionset values(13,'5','zfx',0,5000,2019052719270808,18.3,1);
insert into instructionset values(14,'3','zfx',0,10000,2019061319270808,19.2,1);
insert into instructionset values(15,'2','zfx',0,4000,2019051619270808,21.23,1);
insert into instructionset values(16,'4','zfx',0,10000,2019061609270808,4.2,1);
insert into instructionset values(17,'1','zfx',0,10000,2019061119270808,40.1,1);
insert into instructionset values(18,'1','zfx',0,10000,2019061519270808,12.23,0);
insert into instructionset values(19,'2','zfx',0,10000,2019061612270808,12.33,0);
insert into instructionset values(20,'5','zfx',0,10000,2019052719270808,12.33,0);
insert into instructionset values(21,'3','zfx',0,10000,2019061319270808,12.23,0);
insert into instructionset values(22,'2','zfx',0,10000,2019051619270808,12.33,0);
insert into instructionset values(23,'4','zfx',0,10000,2019061609270808,12.33,0);
insert into instructionset values(24,'1','zfx',0,5000,2019061119270808,14.33,0);
insert into instructionset values(25,'1','zfx',0,10000,2019061519270808,16.5,0);
insert into instructionset values(26,'2','zfx',0,10000,2019061612270808,37.4,0);
insert into instructionset values(27,'5','zfx',0,5000,2019052719270808,28.3,0);
insert into instructionset values(28,'3','zfx',0,10000,2019061319270808,19.2,0);
insert into instructionset values(29,'2','zfx',0,4000,2019051619270808,20.23,0);
insert into instructionset values(30,'4','zfx',0,10000,2019061609270808,14.2,0);
insert into instructionset values(31,'1','zfx',0,10000,2019061119270808,40.1,0);
insert into instructionset values(32,'5','zfx',1,10000,2019052719270808,10.33,1);
insert into instructionset values(33,'3','zfx',1,10000,2019061319270808,33.23,1);
insert into instructionset values(34,'2','zfx',1,10000,2019051619270808,12.33,1);
insert into instructionset values(35,'4','zfx',1,10000,2019061609270808,23.33,1);
insert into instructionset values(36,'1','zfx',1,5000,2019061119270808,32.33,1);
insert into instructionset values(37,'1','zfx',1,10000,2019061519270808,2.5,1);
insert into instructionset values(38,'2','zfx',1,10000,2019061612270808,12.4,1);
insert into instructionset values(39,'5','zfx',1,5000,2019052719270808,19.3,1);
insert into instructionset values(40,'3','zfx',1,10000,2019061319270808,26.2,1);
insert into instructionset values(41,'2','zfx',1,4000,2019051619270808,21.23,1);
insert into instructionset values(42,'4','zfx',1,10000,2019061609270808,4.2,1);
insert into instructionset values(43,'1','zfx',1,10000,2019061119270808,40.1,1);
insert into instructionset values(44,'1','zfx',1,10000,2019061519270808,12.23,0);
insert into instructionset values(45,'2','zfx',1,10000,2019061612270808,11.33,0);
insert into instructionset values(46,'5','zfx',1,10000,2019052719270808,12.33,0);
insert into instructionset values(47,'3','zfx',1,10000,2019061319270808,15.23,0);
insert into instructionset values(48,'2','zfx',1,10000,2019051619270808,1.33,0);
insert into instructionset values(49,'4','zfx',1,10000,2019061609270808,10.33,0);
insert into instructionset values(50,'1','zfx',1,5000,2019061119270808,12.33,0);
insert into instructionset values(51,'1','zfx',1,10000,2019061519270808,16.5,0);
insert into instructionset values(52,'2','zfx',1,10000,2019061612270808,17.4,0);
insert into instructionset values(53,'5','zfx',1,5000,2019052719270808,18.3,0);
insert into instructionset values(54,'3','zfx',1,10000,2019061319270808,19.2,0);
insert into instructionset values(55,'2','zfx',1,4000,2019051619270808,21.23,0);
insert into instructionset values(56,'4','zfx',1,10000,2019061609270808,4.2,0);
insert into instructionset values(57,'1','zfx',1,10000,2019061119270808,40.1,0);


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


INSERT INTO `stock_inf` VALUES ('1', 'n1', 10.20, 10.22, 10.20, 1, 2, 1);
INSERT INTO `stock_inf` VALUES ('2', 'n3', 13.20, 40.00, 40.00, 1, 2, 1);
INSERT INTO `stock_inf` VALUES ('3', '国农科技', 1.50, 20.00, 10.00, 1, 2, 1);
INSERT INTO `stock_inf` VALUES ('4', 'n4', 21.20, 20.30, 10.20, 1, 2, 1);
INSERT INTO `stock_inf` VALUES ('5', 'n5', 12.20, 30.30, 14.20, 1, 2, 1);
INSERT INTO `stock_inf` VALUES ('sh000002', 'Ａ股指数', 3047.74, 74.05, 32.88, 0, 9, 1);
INSERT INTO `stock_inf` VALUES ('sh000003', 'Ｂ股指数', 290.53, 48.02, 1.95, 0, 10, 1);
INSERT INTO `stock_inf` VALUES ('sh000004', '工业指数', 2277.72, 32.85, 94.74, 0, 6, 1);
INSERT INTO `stock_inf` VALUES ('sh000005', '商业指数', 2701.59, 26.86, 78.34, 0, 5, 1);
INSERT INTO `stock_inf` VALUES ('sh000006', '地产指数', 6632.15, 62.94, 44.64, 1, 9, 1);
INSERT INTO `stock_inf` VALUES ('sh000007', '公用指数', 5090.37, 56.23, 62.99, 1, 2, 1);
INSERT INTO `stock_inf` VALUES ('sh000008', '综合指数', 2806.37, 92.72, 19.98, 0, 8, 1);
INSERT INTO `stock_inf` VALUES ('sh000009', '上证380', 4594.97, 74.76, 82.37, 0, 7, 1);
INSERT INTO `stock_inf` VALUES ('sh000010', '上证180', 8168.02, 94.95, 75.16, 1, 8, 1);
INSERT INTO `stock_inf` VALUES ('sh000011', '基金指数', 5986.66, 44.58, 43.80, 1, 5, 1);
INSERT INTO `stock_inf` VALUES ('sh000012', '国债指数', 172.51, 70.86, 90.13, 0, 8, 1);
INSERT INTO `stock_inf` VALUES ('sh000013', '企债指数', 231.34, 63.39, 25.35, 0, 3, 1);
INSERT INTO `stock_inf` VALUES ('sh000015', '红利指数', 2709.28, 84.80, 54.69, 1, 7, 1);
INSERT INTO `stock_inf` VALUES ('sh000016', '上证50', 2748.80, 42.46, 75.44, 0, 3, 1);
INSERT INTO `stock_inf` VALUES ('sh000017', '新综指', 2457.37, 65.84, 6.08, 0, 3, 1);
INSERT INTO `stock_inf` VALUES ('sh000018', '180金融', 5336.33, 25.21, 88.31, 1, 9, 1);
INSERT INTO `stock_inf` VALUES ('sh000019', '治理指数', 1012.97, 90.94, 41.29, 0, 9, 1);
INSERT INTO `stock_inf` VALUES ('sh000020', '中型综指', 1028.53, 29.88, 43.02, 1, 7, 1);
INSERT INTO `stock_inf` VALUES ('sh000021', '180治理', 938.19, 26.30, 27.91, 0, 4, 1);
INSERT INTO `stock_inf` VALUES ('sh000022', '沪公司债', 196.92, 28.26, 41.46, 1, 2, 1);
INSERT INTO `stock_inf` VALUES ('sh000025', '180基建', 1718.33, 61.74, 98.97, 0, 4, 1);
INSERT INTO `stock_inf` VALUES ('sh000026', '180资源', 2164.18, 57.50, 48.06, 0, 7, 1);
INSERT INTO `stock_inf` VALUES ('sh000027', '180运输', 1118.12, 76.12, 20.14, 1, 5, 1);
INSERT INTO `stock_inf` VALUES ('sh000028', '180成长', 3353.18, 61.92, 37.79, 0, 5, 1);
