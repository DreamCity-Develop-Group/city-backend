/*
Navicat MySQL Data Transfer

Source Server         : mysql-localhost
Source Server Version : 50562
Source Host           : localhost:3306
Source Database       : dreamcity2

Target Server Type    : MYSQL
Target Server Version : 50562
File Encoding         : 65001

Date: 2019-10-14 18:37:58
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for city_auth_code
-- ----------------------------
DROP TABLE IF EXISTS `city_auth_code`;
CREATE TABLE `city_auth_code` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `code` varchar(12) CHARACTER SET utf8 NOT NULL COMMENT '认证码',
  `phone` varchar(12) CHARACTER SET utf8 DEFAULT NULL,
  `valid` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '用户id',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `index_code` (`code`),
  KEY `index_phone` (`phone`),
  KEY `index_user_id` (`valid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='认证码';

-- ----------------------------
-- Records of city_auth_code
-- ----------------------------

-- ----------------------------
-- Table structure for city_dictionary
-- ----------------------------
DROP TABLE IF EXISTS `city_dictionary`;
CREATE TABLE `city_dictionary` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `key` varchar(255) DEFAULT NULL,
  `val` varchar(600) DEFAULT NULL,
  `is_valid` tinyint(4) unsigned DEFAULT '1',
  `descr` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='字典（配置项）';

-- ----------------------------
-- Records of city_dictionary
-- ----------------------------
INSERT INTO `city_dictionary` VALUES ('1', '平台账户id', 'platform.account.accIds', '4E2EE556055042AB80E3D164E51DDD1A', '1', '平台账户id，多个，都号隔开来自player_account表', null, null);
INSERT INTO `city_dictionary` VALUES ('2', '注册密码salt', 'register.password.salt', 'DREAM_CITY_890@#$%', '1', '注册密码salt', null, null);

-- ----------------------------
-- Table structure for city_file
-- ----------------------------
DROP TABLE IF EXISTS `city_file`;
CREATE TABLE `city_file` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `file_name` varchar(255) DEFAULT NULL,
  `file_url` varchar(255) DEFAULT NULL COMMENT '文件访问路径',
  `file_type` varchar(20) DEFAULT NULL COMMENT '文件类型（玩家头像:player_img，物业：property）',
  `is_valid` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '是否可用的',
  `from_id` varchar(64) DEFAULT NULL COMMENT '添加人id',
  `descr` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `index_from_id` (`from_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文件';

-- ----------------------------
-- Records of city_file
-- ----------------------------

-- ----------------------------
-- Table structure for city_help
-- ----------------------------
DROP TABLE IF EXISTS `city_help`;
CREATE TABLE `city_help` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(60) NOT NULL,
  `content` text,
  `type` varchar(50) DEFAULT NULL,
  `is_valid` tinyint(4) unsigned DEFAULT '1',
  `descr` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='帮助';


-- ----------------------------
-- Table structure for city_invest
-- ----------------------------
DROP TABLE IF EXISTS `city_invest`;
CREATE TABLE `city_invest` (
  `in_id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '标识',
  `in_name` varchar(255) DEFAULT NULL COMMENT '名称',
  `in_limit` decimal(9,4) unsigned DEFAULT NULL COMMENT '限额',
  `in_start` datetime DEFAULT NULL COMMENT '开始时间',
  `in_personal_tax` decimal(9,4) unsigned DEFAULT NULL COMMENT '个人税金',
  `in_enterprise_tax` decimal(9,4) unsigned DEFAULT NULL COMMENT '企业税金',
  `in_earning` tinyint(3) unsigned DEFAULT NULL COMMENT '收益倍数',
  `in_end` datetime DEFAULT NULL COMMENT '投资结束时间',
  `in_img` varchar(255) DEFAULT NULL COMMENT '项目图片地址(默认主图)',
  `is_valid` char(1) DEFAULT 'Y' COMMENT '是否可投',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`in_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='投资项目表';

-- ----------------------------
-- Table structure for city_message
-- ----------------------------
DROP TABLE IF EXISTS `city_message`;
CREATE TABLE `city_message` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `player_id` varchar(64) CHARACTER SET utf8 DEFAULT NULL,
  `friend_id` varchar(64) CHARACTER SET utf8 DEFAULT NULL,
  `content` varchar(600) DEFAULT NULL COMMENT '消息内容',
  `have_read` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '0未读，1已读',
  `send_time` datetime DEFAULT NULL COMMENT '通知发送时间',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `index_player_id` (`player_id`),
  KEY `index_friend_id` (`friend_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;


-- ----------------------------
-- Table structure for city_notice
-- ----------------------------
DROP TABLE IF EXISTS `city_notice`;
CREATE TABLE `city_notice` (
  `notice_id` int(11) NOT NULL AUTO_INCREMENT,
  `notice_content` varchar(255) DEFAULT NULL COMMENT '内容',
  `notice_state` tinyint(4) DEFAULT NULL COMMENT '状态：1可用0不可用',
  `send_time` datetime DEFAULT NULL COMMENT '公告发送时间',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`notice_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;


-- ----------------------------
-- Table structure for city_player
-- ----------------------------
DROP TABLE IF EXISTS `city_player`;
CREATE TABLE `city_player` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `player_id` varchar(64) CHARACTER SET utf8 NOT NULL COMMENT '玩家id',
  `player_name` varchar(50) CHARACTER SET utf8 NOT NULL COMMENT '用户名',
  `player_nick` varchar(50) NOT NULL COMMENT '昵称',
  `player_pass` varchar(20) CHARACTER SET utf8 NOT NULL COMMENT '密码',
  `player_trade_pass` varchar(20) CHARACTER SET utf8 DEFAULT NULL COMMENT '交易密码',
  `player_invite` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '邀请码',
  `player_level` int(11) unsigned DEFAULT '0' COMMENT '等级',
  `is_valid` tinyint(4) unsigned NOT NULL DEFAULT '1' COMMENT '是否可用',
  `create_time` datetime DEFAULT NULL COMMENT ' 创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_player_id` (`player_id`) USING BTREE,
  UNIQUE KEY `index_player_name` (`player_name`) USING BTREE,
  KEY `index_player_nick` (`player_nick`),
  KEY `index_player_invite` (`player_invite`)
) ENGINE=InnoDB AUTO_INCREMENT=134 DEFAULT CHARSET=utf8mb4 COMMENT='用户表（玩家）';

-- ----------------------------
-- Records of city_player
-- ----------------------------
INSERT INTO `city_player` VALUES ('1', '4E2EE556055042AB80E3D164E51DDD1A', '17879502041', 'zp01', '11111111', null, 'eb47d7', '0', '1', '2019-09-28 11:32:11', null);

-- ----------------------------
-- Table structure for city_player_grade
-- ----------------------------
DROP TABLE IF EXISTS `city_player_grade`;
CREATE TABLE `city_player_grade` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `player_id` varchar(64) CHARACTER SET utf8 NOT NULL COMMENT '玩家id',
  `grade` varchar(10) CHARACTER SET utf8 NOT NULL DEFAULT '' COMMENT '玩家等级',
  `integral` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '积分',
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `index_player_id` (`player_id`) USING BTREE,
  KEY `index_grade` (`grade`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='玩家等级(会员/商会等级)';

-- ----------------------------
-- Records of city_player_grade
-- ----------------------------

-- ----------------------------
-- Table structure for earn_falldown_log
-- ----------------------------
DROP TABLE IF EXISTS `earn_falldown_log`;
CREATE TABLE `earn_falldown_log` (
  `fall_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `fall_invest_id` int(11) DEFAULT NULL COMMENT '掉落的项目',
  `fall_player_id` varchar(50) DEFAULT NULL COMMENT '掉落的玩家',
  `fall_amount` decimal(9,4) DEFAULT NULL COMMENT '掉落的额度',
  `create_time` datetime DEFAULT NULL COMMENT '掉落的时间',
  PRIMARY KEY (`fall_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of earn_falldown_log
-- ----------------------------

-- ----------------------------
-- Table structure for earn_income_log
-- ----------------------------
DROP TABLE IF EXISTS `earn_income_log`;
CREATE TABLE `earn_income_log` (
  `in_log_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `in_invest_id` int(11) DEFAULT NULL COMMENT '投资的ID',
  `in_player_id` varchar(50) DEFAULT NULL COMMENT '玩家的ID',
  `in_amount` decimal(9,4) DEFAULT NULL COMMENT '本次收益',
  `create_time` datetime DEFAULT NULL COMMENT '收益时间',
  PRIMARY KEY (`in_log_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of earn_income_log
-- ----------------------------

-- ----------------------------
-- Table structure for game_notice
-- ----------------------------
DROP TABLE IF EXISTS `game_notice`;
CREATE TABLE `game_notice` (
  `notice_id` int(11) NOT NULL AUTO_INCREMENT,
  `notice_content` varchar(255) DEFAULT NULL COMMENT '内容',
  `notice_state` tinyint(4) DEFAULT NULL COMMENT '状态：1可用0不可用',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`notice_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of game_notice
-- ----------------------------

-- ----------------------------
-- Table structure for invest_order
-- ----------------------------
DROP TABLE IF EXISTS `invest_order`;
CREATE TABLE `invest_order` (
  `order_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `order_invest_id` int(11) unsigned DEFAULT NULL COMMENT '投资项目ID',
  `order_payer_id` varchar(64) DEFAULT NULL COMMENT '玩家ID',
  `order_name` varchar(50) DEFAULT NULL,
  `order_num` varchar(64) DEFAULT NULL,
  `order_amount` decimal(20,4) unsigned DEFAULT '0.0000' COMMENT '投资金额',
  `order_state` varchar(60) DEFAULT NULL COMMENT '状态(SUBSCRIBE预约，SUBSCRIBED预约中,SUBSCRIBE_PASS预约成功,INVEST投资,INVESTED已投资,MANAGEMENT经营中,EXTRACT可提取,FINISHED已完成,CANCEL取消,SUBSCRIBE_VERIFY_FAIL预约审核不通过，INVALID作废)',
  `order_repeat` tinyint(4) unsigned DEFAULT '0' COMMENT '是否复投（0否，1是）',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`order_id`) USING BTREE,
  KEY `index_order_invest_id` (`order_invest_id`),
  KEY `index_order_payer_id` (`order_payer_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='订单（投资记录）';



-- ----------------------------
-- Table structure for invest_rule
-- ----------------------------
DROP TABLE IF EXISTS `invest_rule`;
CREATE TABLE `invest_rule` (
  `rule_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `rule_flag` varchar(30) DEFAULT NULL,
  `rule_opt_pre` varchar(255) DEFAULT NULL,
  `rule_opt` varchar(255) DEFAULT NULL,
  `rule_name` varchar(50) DEFAULT NULL COMMENT '规则名称',
  `rule_desc` varchar(200) DEFAULT NULL COMMENT '规则描述',
  `rule_item` int(11) unsigned DEFAULT NULL COMMENT '规则项目',
  `rule_rate_pre` decimal(11,4) DEFAULT NULL,
  `rule_rate` decimal(11,4) unsigned DEFAULT NULL,
  `rule_level` int(2) unsigned DEFAULT NULL COMMENT '规则优先级别',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`rule_id`) USING BTREE,
  KEY `index_rule_item` (`rule_item`),
  KEY `index_rule_name` (`rule_name`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='项目规则表';

-- ----------------------------
-- Records of invest_rule
-- ----------------------------
INSERT INTO `invest_rule` VALUES ('1', 'DIRECT_DIS', null, 'OPT_RATE', '直推印记分成', null, '1', null, '0.3500', '1', '2019-10-09 09:42:41', null);
INSERT INTO `invest_rule` VALUES ('2', 'INDIRECT_DIS', null, 'OPT_RATE', '间推印记分成', null, '1', null, '0.0500', '2', null, null);
INSERT INTO `invest_rule` VALUES ('3', null, null, null, '小摊预约权重', null, '2', null, '0.2000', '1', null, null);
INSERT INTO `invest_rule` VALUES ('4', null, null, null, '大厦投资', null, '3', null, '0.3000', '1', null, null);
INSERT INTO `invest_rule` VALUES ('5', 'ALL_ORDERS', 'OPT_RATE', 'OPT_RATE', '所有玩家', '订单所有玩家', '4', '1.0000', '0.2000', '1', null, null);
INSERT INTO `invest_rule` VALUES ('6', 'TOP_MEMBERS', 'OPT_TOP', 'OPT_RATE', '会员最多', '取所有会员数量', '4', '20.0000', '0.3000', '2', null, null);
INSERT INTO `invest_rule` VALUES ('7', 'FIRST_TIME', 'OPT_RATE', 'OPT_RATE', '第一次投资', '投资时间与计算当天时间一样', '4', '0.2000', '0.5000', '3', null, null);
INSERT INTO `invest_rule` VALUES ('8', 'LIKES_GATHER', 'OPT_RATE', 'OPT_RATE', '获得点赞', '获得数量最多的', '4', '0.0000', '0.0000', '4', null, null);
INSERT INTO `invest_rule` VALUES ('9', 'INVEST_LONG', 'OPT_TOP', 'OPT_RATE', '投资时长', '第一次投资时间算起', '4', '0.0000', '0.0000', '5', null, null);
INSERT INTO `invest_rule` VALUES ('10', 'ORDER_OTHERS', 'OPT_RATE', 'OPT_RATE', '其他', '其他剩余的订单', '4', '0.0000', '0.0000', '6', null, null);
INSERT INTO `invest_rule` VALUES ('11', 'LEVEL_ONE_START', '', 'OPT_NUM', '一星', '一级商会', '5', null, '3.0000', '1', null, null);
INSERT INTO `invest_rule` VALUES ('12', 'LEVEL_TWO_START', null, 'OPT_NUM', '二星', '一级商会', '5', null, '9.0000', '2', null, null);
INSERT INTO `invest_rule` VALUES ('13', 'LEVEL_THREE_START', null, 'OPT_NUM', '三星', '三级商会', '5', null, '27.0000', '3', null, null);
INSERT INTO `invest_rule` VALUES ('14', 'LEVEL_FOUR_START', null, 'OPT_NUM', '四星', '四级商会', '5', null, '81.0000', '4', null, null);
INSERT INTO `invest_rule` VALUES ('15', 'LEVEL_FIVE_START', null, 'OPT_NUM', '五星', '五级商会', '5', null, '405.0000', '5', null, null);
INSERT INTO `invest_rule` VALUES ('16', 'LEVEL_SIX_START', null, 'OPT_NUM', '六星', '六级商会', '5', null, '2025.0000', '6', null, null);
INSERT INTO `invest_rule` VALUES ('17', 'LEVEL_SEVEN_START', null, 'OPT_NUM', '七星', '七级商会', '5', null, '10125.0000', '7', null, null);
INSERT INTO `invest_rule` VALUES ('18', 'LEVEL_EIGHT_START', null, 'OPT_NUM', '八星', '八商级会', '5', null, '101250.0000', '8', null, null);
INSERT INTO `invest_rule` VALUES ('19', 'LEVEL_NINE_START', null, 'OPT_NUM', '九星', '九级商会', '5', null, '1012500.0000', '9', null, null);
INSERT INTO `invest_rule` VALUES ('20', 'ALL_ORDERS', 'OPT_RATE', 'OPT_RATE', '所有玩家', '订单所有玩家', '6', '1.0000', '0.4000', '1', null, null);
INSERT INTO `invest_rule` VALUES ('21', 'TOP_MEMBERS', 'OPT_TOP', 'OPT_RATE', '会员最多', '取所有会员数量', '6', '20.0000', '0.2000', '2', null, null);
INSERT INTO `invest_rule` VALUES ('22', 'FIRST_TIME', 'OPT_RATE', 'OPT_RATE', '第一次投资', '投资时间与计算当天时间一样', '4', '0.2000', '0.2000', '3', null, null);
INSERT INTO `invest_rule` VALUES ('23', 'LIKES_GATHER', 'OPT_TOP', 'OPT_RATE', '获得点赞', '获得数量最多的', '6', '20.0000', '0.1000', '4', null, null);
INSERT INTO `invest_rule` VALUES ('24', 'INVEST_LONG', 'OPT_TOP', 'OPT_RATE', '投资时长', '第一次投资时间算起', '6', '10.0000', '0.1000', '5', null, null);
INSERT INTO `invest_rule` VALUES ('25', 'ORDER_OTHERS', 'OPT_RATE', 'OPT_RATE', '其他', '其他剩余的订单', '6', '0.0000', '0.0000', '6', null, null);

-- ----------------------------
-- Table structure for likes_log
-- ----------------------------
DROP TABLE IF EXISTS `likes_log`;
CREATE TABLE `likes_log` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `like_id` int(11) unsigned DEFAULT NULL,
  `like_player_id` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '点赞玩家ID',
  `like_liked_id` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '收获点赞玩家ID',
  `like_invest_id` int(11) unsigned DEFAULT NULL COMMENT '点赞投资ID',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='点赞记录表';


-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parent_id` int(11) DEFAULT NULL COMMENT '父级ID',
  `name` varchar(100) NOT NULL COMMENT '名称',
  `sort` int(11) NOT NULL COMMENT '排序',
  `href` varchar(2000) DEFAULT NULL COMMENT '链接',
  `icon` varchar(100) DEFAULT NULL COMMENT '图标',
  `type` tinyint(4) NOT NULL DEFAULT '1' COMMENT '类型：0-菜单 1-子菜单 2-按钮',
  `permission` varchar(200) DEFAULT NULL COMMENT '权限标识',
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标记(0否，1是)',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `menu_del_flag` (`del_flag`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='菜单表';

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES ('1', null, '系统管理', '10000', null, null, '0', 'system', null, '2017-09-20 15:52:54', 'admin', '2017-09-20 15:52:54', null, '0');
INSERT INTO `menu` VALUES ('2', '1', '用户管理', '10100', null, null, '1', 'system:user', null, '2017-09-20 15:55:34', 'admin', '2017-09-20 15:55:34', null, '0');
INSERT INTO `menu` VALUES ('3', '2', '编辑用户', '10101', null, null, '2', 'system:user:edit', null, '2018-05-14 22:45:54', null, '2018-05-14 22:45:54', null, '0');
INSERT INTO `menu` VALUES ('4', '2', '删除用户', '10102', null, null, '2', 'system:user:delete', null, '2018-05-14 22:47:25', null, '2018-05-14 22:47:25', null, '0');
INSERT INTO `menu` VALUES ('5', '2', '分配角色', '10103', null, null, '2', 'system:user:grant', null, '2018-05-14 22:48:04', null, '2018-05-14 22:48:04', null, '0');
INSERT INTO `menu` VALUES ('6', '2', '添加用户', '10104', null, null, '2', 'system:user:add', null, '2018-05-14 22:49:16', null, '2018-05-14 22:49:16', null, '0');
INSERT INTO `menu` VALUES ('7', '1', '角色管理', '10200', null, null, '1', 'system:role', null, '2018-05-19 22:03:19', null, '2018-05-19 22:03:19', null, '0');
INSERT INTO `menu` VALUES ('8', '7', '添加角色', '10201', null, null, '2', 'system:role:add', null, '2018-05-19 23:08:02', null, '2018-05-19 23:08:02', null, '0');
INSERT INTO `menu` VALUES ('9', '7', '编辑角色', '10202', null, null, '2', 'system:role:edit', null, '2018-05-19 23:08:02', null, '2018-05-19 23:08:02', null, '0');
INSERT INTO `menu` VALUES ('10', '7', '删除角色', '10203', null, null, '2', 'system:role:delete', null, '2018-05-19 23:08:02', null, '2018-05-19 23:08:02', null, '0');
INSERT INTO `menu` VALUES ('11', '7', '分配资源', '10204', null, null, '2', 'system:role:grant', null, '2018-05-19 23:08:02', null, '2018-05-19 23:08:02', null, '0');
INSERT INTO `menu` VALUES ('12', '1', '资源管理', '10300', null, null, '1', 'system:menu', null, '2018-05-20 22:16:39', null, '2018-05-20 22:16:39', null, '0');
INSERT INTO `menu` VALUES ('13', '12', '添加资源', '10301', null, null, '2', 'system:menu:add', null, '2018-05-20 22:17:11', null, '2018-05-20 22:17:11', null, '0');
INSERT INTO `menu` VALUES ('14', '12', '编辑资源', '10302', null, null, '2', 'system:menu:edit', null, '2018-05-20 22:17:47', null, '2018-05-20 22:17:47', null, '0');
INSERT INTO `menu` VALUES ('15', '12', '删除资源', '10303', null, null, '2', 'system:menu:delete', null, '2018-05-20 22:18:16', null, '2018-05-20 22:18:16', null, '0');
INSERT INTO `menu` VALUES ('48', null, 'test3', '30101', '', null, '1', 'system:test3', null, '2017-11-06 20:39:01', null, '2017-11-06 20:39:01', '', '0');

-- ----------------------------
-- Table structure for player_account
-- ----------------------------
DROP TABLE IF EXISTS `player_account`;
CREATE TABLE `player_account` (
  `acc_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `acc_player_id` varchar(64) NOT NULL COMMENT '账户玩家id',
  `acc_addr` varchar(255) DEFAULT NULL COMMENT '账户地址',
  `acc_pass` varchar(255) DEFAULT NULL COMMENT '账户密码',
  `acc_usdt` decimal(20,4) NOT NULL DEFAULT '0.0000' COMMENT '账户usdt额度',
  `acc_usdt_available` decimal(20,4) unsigned NOT NULL DEFAULT '0.0000' COMMENT 'usdt可用金额',
  `acc_usdt_freeze` decimal(20,4) unsigned NOT NULL DEFAULT '0.0000' COMMENT 'usdt冻结金额',
  `acc_mt` decimal(20,4) NOT NULL DEFAULT '0.0000' COMMENT '账户mt额度',
  `acc_mt_available` decimal(20,4) unsigned NOT NULL DEFAULT '0.0000' COMMENT 'mt可用金额',
  `acc_mt_freeze` decimal(20,4) unsigned NOT NULL DEFAULT '0.0000' COMMENT 'mt冻结金额',
  `acc_income` decimal(20,4) unsigned NOT NULL DEFAULT '0.0000' COMMENT '积累总收入',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`acc_id`),
  KEY `index_acc_player_id` (`acc_player_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='玩家账户表';

-- ----------------------------
-- Records of player_account
-- ----------------------------
INSERT INTO `player_account` VALUES ('1', '4E2EE556055042AB80E3D164E51DDD1A', 'aaaa', '123456', '180200.0000', '12000200.0000', '60000.0000', '100025.0000', '90025.0000', '980.0000', '10.0000', '2019-10-09 18:56:26', '2019-10-13 20:29:15');

-- ----------------------------
-- Table structure for player_account_log
-- ----------------------------
DROP TABLE IF EXISTS `player_account_log`;
CREATE TABLE `player_account_log` (
  `id` int(11) NOT NULL,
  `acc_id` int(11) NOT NULL COMMENT '交易ID号',
  `player_id` varchar(50) DEFAULT NULL COMMENT '玩家',
  `address` varchar(50) DEFAULT NULL COMMENT '地址',
  `amount_mt` decimal(50,0) DEFAULT NULL,
  `account_usdt` decimal(50,0) DEFAULT NULL,
  `type` varchar(50) DEFAULT NULL COMMENT '1入账2出账',
  `desc` varchar(255) DEFAULT NULL COMMENT '说明',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of player_account_log
-- ----------------------------

-- ----------------------------
-- Table structure for player_earning
-- ----------------------------
DROP TABLE IF EXISTS `player_earning`;
CREATE TABLE `player_earning` (
  `earn_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `earn_invest_id` int(11) DEFAULT NULL COMMENT '投资项目ID',
  `earn_player_id` varchar(64) DEFAULT NULL COMMENT '玩家ID',
  `earn_max` decimal(9,4) unsigned DEFAULT '0.0000' COMMENT '最大提取额度（预计最大收益）',
  `earn_current` decimal(9,4) DEFAULT NULL COMMENT '当前获得额度',
  `earn_personal_tax` decimal(9,4) unsigned DEFAULT '0.0000' COMMENT '个人税金',
  `earn_enterprise_tax` decimal(9,4) DEFAULT NULL COMMENT '企业税金',
  `is_withdrew` char(1) NOT NULL DEFAULT 'N' COMMENT '是否可以提取(N否，Y是）',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`earn_id`) USING BTREE,
  KEY `index_earn_player_id` (`earn_player_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='玩家收益';


-- ----------------------------
-- Table structure for player_ext
-- ----------------------------
DROP TABLE IF EXISTS `player_ext`;
CREATE TABLE `player_ext` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `player_id` varchar(64) CHARACTER SET utf8 NOT NULL COMMENT '用户id',
  `friendlink` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '主页入口',
  `imgurl` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '头像地址',
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_player_id` (`player_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户扩展表（玩家）';

-- ----------------------------
-- Records of player_ext
-- ----------------------------

-- ----------------------------
-- Table structure for player_friends
-- ----------------------------
DROP TABLE IF EXISTS `player_friends`;
CREATE TABLE `player_friends` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `player_id` varchar(64) CHARACTER SET utf8 NOT NULL COMMENT '用户id',
  `friend_id` varchar(64) CHARACTER SET utf8 NOT NULL COMMENT '好友id',
  `agree` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '同意添加(0未同意，1同意，2不通同意)',
  `invite` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '邀请码（来自friend_id）',
  `is_valid` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '是否可用的',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `index_player_id` (`player_id`),
  KEY `index_friend_id` (`friend_id`),
  KEY `index_invite` (`invite`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COMMENT='好友';


-- ----------------------------
-- Table structure for player_game_setting
-- ----------------------------
DROP TABLE IF EXISTS `player_game_setting`;
CREATE TABLE `player_game_setting` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `player_id` varchar(64) NOT NULL COMMENT '玩家id',
  `type` varchar(50) NOT NULL DEFAULT '' COMMENT '设置类型',
  `val` varchar(50) NOT NULL DEFAULT '' COMMENT '设置值',
  `status` varchar(50) DEFAULT NULL COMMENT '状态',
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `index_player_id` (`player_id`),
  KEY `index_type` (`type`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='游戏设置';

-- ----------------------------
-- Table structure for player_grade
-- ----------------------------
DROP TABLE IF EXISTS `player_grade`;
CREATE TABLE `player_grade` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `player_id` varchar(64) CHARACTER SET utf8 NOT NULL COMMENT '玩家id',
  `grade` varchar(10) CHARACTER SET utf8 NOT NULL DEFAULT '' COMMENT '玩家等级',
  `integral` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '积分',
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `index_player_id` (`player_id`),
  KEY `index_grade` (`grade`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COMMENT='玩家等级(会员/商会等级)';

-- ----------------------------
-- Table structure for player_likes
-- ----------------------------
DROP TABLE IF EXISTS `player_likes`;
CREATE TABLE `player_likes` (
  `liked_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `liked_player_id` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '收获玩家',
  `liked_invest_id` int(10) unsigned DEFAULT NULL COMMENT '点赞项目ID',
  `liked_set_total` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '点赞数量',
  `liked_get_total` int(11) unsigned DEFAULT '0' COMMENT '收获点赞数量',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`liked_id`),
  KEY `index_liked_player_id` (`liked_player_id`),
  KEY `index_liked_invest_id` (`liked_invest_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COMMENT='玩家获赞表';

-- ----------------------------
-- Table structure for player_login_log
-- ----------------------------
DROP TABLE IF EXISTS `player_login_log`;
CREATE TABLE `player_login_log` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `player_id` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '玩家id',
  `imei` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '设备识别码',
  `ip` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT 'ip地址',
  `type` varchar(255) DEFAULT NULL,
  `descr` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '登录时间',
  PRIMARY KEY (`id`),
  KEY `index_player_id` (`player_id`),
  KEY `index_imei` (`imei`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='登录日志';

-- ----------------------------
-- Records of player_login_log
-- ----------------------------

-- ----------------------------
-- Table structure for player_trade
-- ----------------------------
DROP TABLE IF EXISTS `player_trade`;
CREATE TABLE `player_trade` (
  `trade_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `trade_acc_id` int(10) unsigned NOT NULL COMMENT '账户id',
  `trade_player_id` varchar(64) DEFAULT NULL COMMENT '交易人id',
  `trade_order_id` int(11) unsigned DEFAULT NULL COMMENT '订单id',
  `trade_amount` decimal(20,4) NOT NULL DEFAULT '0.0000' COMMENT '交易金额',
  `personal_tax` decimal(20,4) unsigned DEFAULT '0.0000' COMMENT '个人所得税',
  `enterprise_tax` decimal(20,4) unsigned DEFAULT '0.0000' COMMENT '企业所得税',
  `trade_status` varchar(30) DEFAULT NULL COMMENT '交易状态(FREEZE冻结,OUT已出账,IN已入账)',
  `in_out` varchar(10) DEFAULT NULL COMMENT '入账还是出账(入账in,出账out)',
  `trade_type` varchar(30) DEFAULT NULL COMMENT '交易类型（充值:recharge,转账:transfer,提现:withdraw,购买mt:buy_mt,投资invest,投资收益:invest_earnings)',
  `trade_desc` varchar(100) DEFAULT NULL COMMENT '交易描述',
  `create_time` datetime DEFAULT NULL COMMENT '交易时间',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`trade_id`),
  KEY `index_trade_player_id` (`trade_player_id`),
  KEY `index_trade_order_id` (`trade_order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='交易记录表';


-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL COMMENT '角色名称',
  `enname` varchar(255) DEFAULT NULL COMMENT '英文名称',
  `useable` tinyint(4) NOT NULL DEFAULT '1' COMMENT '是否可用',
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `role_del_flag` (`del_flag`) USING BTREE,
  KEY `role_en_name` (`enname`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='角色表';

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', '系统管理员', 'admin', '1', null, '2017-08-25 11:26:36', null, '2017-08-25 11:26:36', null, '0');
INSERT INTO `role` VALUES ('2', '运营test', 'operator', '1', null, '2017-08-25 11:26:36', null, '2017-08-25 11:26:36', null, '0');
INSERT INTO `role` VALUES ('3', '人力', 'hr', '1', null, '2017-08-25 11:26:36', null, '2017-08-25 11:26:36', null, '0');
INSERT INTO `role` VALUES ('4', '产品', 'product', '1', null, '2017-08-25 11:26:37', null, '2017-08-25 11:26:37', null, '0');
INSERT INTO `role` VALUES ('5', '财务', 'finance', '1', null, '2017-08-25 11:26:37', null, '2017-08-25 11:26:37', null, '0');
INSERT INTO `role` VALUES ('6', '超级管理员', 'superadmin', '1', null, '2017-09-26 12:46:12', null, '2017-09-26 12:46:12', null, '0');
INSERT INTO `role` VALUES ('7', 'test3', null, '1', null, '2018-05-20 14:15:34', null, '2018-05-20 14:15:34', '吃了2', '0');

-- ----------------------------
-- Table structure for role_menu
-- ----------------------------
DROP TABLE IF EXISTS `role_menu`;
CREATE TABLE `role_menu` (
  `role_id` int(11) NOT NULL COMMENT '角色编号',
  `menu_id` int(11) NOT NULL COMMENT '菜单编号',
  PRIMARY KEY (`role_id`,`menu_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='角色-菜单';

-- ----------------------------
-- Records of role_menu
-- ----------------------------
INSERT INTO `role_menu` VALUES ('1', '1');
INSERT INTO `role_menu` VALUES ('1', '2');
INSERT INTO `role_menu` VALUES ('1', '3');
INSERT INTO `role_menu` VALUES ('1', '4');
INSERT INTO `role_menu` VALUES ('1', '5');
INSERT INTO `role_menu` VALUES ('1', '6');
INSERT INTO `role_menu` VALUES ('1', '7');
INSERT INTO `role_menu` VALUES ('1', '8');
INSERT INTO `role_menu` VALUES ('1', '9');
INSERT INTO `role_menu` VALUES ('1', '10');
INSERT INTO `role_menu` VALUES ('1', '11');
INSERT INTO `role_menu` VALUES ('1', '12');
INSERT INTO `role_menu` VALUES ('1', '13');
INSERT INTO `role_menu` VALUES ('1', '14');
INSERT INTO `role_menu` VALUES ('1', '15');
INSERT INTO `role_menu` VALUES ('1', '48');

-- ----------------------------
-- Table structure for rule_item
-- ----------------------------
DROP TABLE IF EXISTS `rule_item`;
CREATE TABLE `rule_item` (
  `item_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `item_flag` varchar(30) DEFAULT NULL COMMENT '规则标识',
  `item_name` varchar(60) NOT NULL COMMENT '规则项目名称',
  `item_desc` varchar(255) DEFAULT NULL COMMENT '规则项目描述',
  `item_state` tinyint(4) unsigned DEFAULT NULL COMMENT '可用状态(0不可以，1可用)',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`item_id`),
  KEY `index_item_name` (`item_name`),
  KEY `index_item_type` (`item_flag`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='规则表';

-- ----------------------------
-- Records of rule_item
-- ----------------------------
INSERT INTO `rule_item` VALUES ('1', 'STAMP_SIGN', '社交印记', '直推间推印记分成', '1', null, null);
INSERT INTO `rule_item` VALUES ('2', 'PROFIT_CACL', '收益计算', '计算收益分成', '1', null, null);
INSERT INTO `rule_item` VALUES ('4', 'INVEST_ORDER', '投资订单', '计算投资预约成功', '1', null, null);
INSERT INTO `rule_item` VALUES ('5', 'PlAYER_LEVEL', '商会等级', '计算玩家商会等级', '1', null, null);
INSERT INTO `rule_item` VALUES ('6', 'PROFIT_GRANT', '利益分配', '计算收益分配', '1', null, null);

-- ----------------------------
-- Table structure for sales_order
-- ----------------------------
DROP TABLE IF EXISTS `sales_order`;
CREATE TABLE `sales_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_id` varchar(255) DEFAULT NULL COMMENT '订单ID',
  `order_amount` double(255,0) DEFAULT NULL COMMENT '订单额度',
  `order_buy_type` varchar(255) DEFAULT NULL COMMENT '订单购买类型',
  `order_pay_type` varchar(255) DEFAULT NULL COMMENT '订单支付类型',
  `order_pay_amount` double(255,0) DEFAULT NULL COMMENT '支付额度',
  `order_player_buyer` varchar(255) DEFAULT NULL COMMENT '订单玩家',
  `order_player_seller` varchar(255) DEFAULT NULL COMMENT '订单卖家',
  `order_state` varchar(255) DEFAULT NULL COMMENT '订单状态：0创建1完成2拒绝3超时',
  `createtime` datetime DEFAULT NULL,
  `updatetime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for trade_detail
-- ----------------------------
DROP TABLE IF EXISTS `trade_detail`;
CREATE TABLE `trade_detail` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `trade_id` int(11) unsigned DEFAULT NULL,
  `verify_id` int(11) unsigned DEFAULT NULL COMMENT '审核id',
  `order_id` int(11) unsigned DEFAULT NULL COMMENT '订单id',
  `player_id` varchar(64) DEFAULT NULL COMMENT '交易人',
  `trade_detail_type` varchar(50) DEFAULT NULL COMMENT '交易明细类型（充值:recharge,usdt投资收益:usdt_earnings,mt投资收益:mt_earnings,usdt投资税金:usdt_invest_tax,mt投资税金:mt_invest_tax,个人所得税:personal_tax,企业所得税:enterprise_tax,转账所得税:transfer_tax,转账冻结:transfer_freeze,提现冻结:withdraw_freeze,购买mt冻结:buy_mt_freeze,usdt投资冻结:usdt_invest_freeze,mt投资冻结:mt_inves_freeze,转账审核通过扣款:transfer_verify,提现审核通过扣款:withdraw_verify,usdt投资审核通过扣款:usdt_invest_verify',
  `trade_amount` decimal(20,4) DEFAULT '0.0000' COMMENT '交易金额',
  `verify_time` datetime DEFAULT NULL COMMENT '交易审核时间',
  `descr` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `index_trade_id` (`trade_id`),
  KEY `index_player_id` (`player_id`),
  KEY `index_order_id` (`order_id`),
  KEY `index_verify_id` (`verify_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='交易流水表';

-- ----------------------------
-- Records of trade_detail
-- ----------------------------

-- ----------------------------
-- Table structure for trade_verify
-- ----------------------------
DROP TABLE IF EXISTS `trade_verify`;
CREATE TABLE `trade_verify` (
  `verify_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `verify_user_id` int(11) unsigned DEFAULT NULL COMMENT '审核人id(员工表)',
  `verify_trade_id` int(11) unsigned DEFAULT NULL COMMENT '交易id(交易记录表)',
  `verify_order_id` int(11) unsigned DEFAULT NULL COMMENT '订单id',
  `verify_status` varchar(20) DEFAULT NULL COMMENT '审核状态(待审核wait，审核中verifying，pass审核通过，notpass审核不通过)',
  `verify_desc` varchar(255) DEFAULT NULL COMMENT '审核意见',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`verify_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='交易审核表';


-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `login_name` varchar(100) NOT NULL COMMENT '登录名',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `state` tinyint(4) NOT NULL DEFAULT '1' COMMENT '是否可用',
  `no` varchar(100) DEFAULT NULL COMMENT '工号',
  `name` varchar(100) NOT NULL COMMENT '姓名',
  `email` varchar(200) DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(200) DEFAULT NULL COMMENT '电话',
  `mobile` varchar(200) DEFAULT NULL COMMENT '手机',
  `photo` varchar(1000) DEFAULT NULL COMMENT '用户头像',
  `login_ip` varchar(100) DEFAULT NULL COMMENT '最后登陆IP',
  `login_date` datetime DEFAULT NULL COMMENT '最后登陆时间',
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标记(0否，1是)',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_user_login_name` (`login_name`) USING BTREE,
  KEY `idx_user_update_date` (`update_date`) USING BTREE,
  KEY `idx_user_del_flag` (`del_flag`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户表';

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'admin', 'e10adc3949ba59abbe56e057f20f883e', '1', null, '管理员', null, null, null, 'avatars/user.jpg', null, null, null, '2017-09-21 10:08:53', null, '2017-09-21 10:08:53', null, '0');
INSERT INTO `user` VALUES ('2', 'superadmin', 'e10adc3949ba59abbe56e057f20f883e', '1', null, '超级管理员', null, null, null, 'avatars/user.jpg', null, null, null, '2017-09-21 10:08:53', null, '2017-09-21 10:08:53', null, '0');
INSERT INTO `user` VALUES ('3', 'test2', 'e10adc3949ba59abbe56e057f20f883e', '1', null, '测试2', 'test@mail.cn', '13100131000', null, 'avatars/user.jpg', null, null, null, '2017-11-05 23:14:22', null, '2017-11-05 23:14:22', null, '0');
INSERT INTO `user` VALUES ('4', 'test1', 'f4cc399f0effd13c888e310ea2cf5399', '1', null, 'test1', 'tw@mail.com', '13800138000', null, 'avatars/user.jpg', null, null, null, '2018-05-26 00:00:45', null, '2018-05-16 23:27:53', '123eee', '0');
INSERT INTO `user` VALUES ('5', 'test1', 'e358efa489f58062f10dd7316b65649e', '1', null, 'test', '123@main.com', '123', null, 'avatars/user.jpg', null, null, null, '2018-05-19 20:13:03', null, '2018-05-19 20:13:19', '123', '0');

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `user_id` int(11) NOT NULL COMMENT '用户编号',
  `role_id` int(11) NOT NULL COMMENT '角色编号',
  PRIMARY KEY (`user_id`,`role_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户-角色';

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES ('1', '1');
INSERT INTO `user_role` VALUES ('2', '6');
INSERT INTO `user_role` VALUES ('3', '1');
INSERT INTO `user_role` VALUES ('3', '2');
INSERT INTO `user_role` VALUES ('4', '1');
INSERT INTO `user_role` VALUES ('4', '7');
