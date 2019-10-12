/*
Navicat MySQL Data Transfer

Source Server         : mysql-localhost
Source Server Version : 50562
Source Host           : localhost:3306
Source Database       : dreamcity2

Target Server Type    : MYSQL
Target Server Version : 50562
File Encoding         : 65001

Date: 2019-10-12 19:35:55
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
INSERT INTO `city_dictionary` VALUES ('1', '平台账户id', 'platform.account.accIds', '888888888,999999999', '1', '平台账户id，多个，都号隔开来自player_account表', null, null);
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
-- Records of city_help
-- ----------------------------
INSERT INTO `city_help` VALUES ('2', '帮助2', '内容22', null, '1', null, null, null);
INSERT INTO `city_help` VALUES ('3', '帮助1', '鞍山市所所所所所所所所所付多所撒', 'help', '1', null, '2019-10-02 10:47:48', '2019-10-02 10:47:48');

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
-- Records of city_invest
-- ----------------------------
INSERT INTO `city_invest` VALUES ('1', '物业1', '100.0000', '2019-10-02 16:31:16', '0.1000', '0.0200', '2', '2019-11-30 16:31:57', null, 'Y', '2019-10-02 16:32:10', '2019-10-02 16:32:23');
INSERT INTO `city_invest` VALUES ('2', '物业2', '50.0000', '2019-10-08 16:47:52', '0.2000', '0.0600', '6', '2019-12-31 16:48:09', null, 'Y', '2019-10-09 16:48:18', '2019-10-09 16:48:22');

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
-- Records of city_message
-- ----------------------------
INSERT INTO `city_message` VALUES ('1', 'FD826FE2E378445594D23CA84C0C485D', 'E13D6322D389411C959CD7AC7A2B230F', 'dgjnyrmtfsd', '0', '2019-10-02 18:03:30', '2019-10-02 18:03:30', null);
INSERT INTO `city_message` VALUES ('3', 'FD826FE2E378445594D23CA84C0C485D', '99B1D351FD5242CD8F73E86A2BBC89A0', 'asadvbsbz', '0', '2019-10-02 18:03:30', null, null);

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
-- Records of city_notice
-- ----------------------------
INSERT INTO `city_notice` VALUES ('1', 'advababavs', '1', '2019-10-02 14:17:02', null);
INSERT INTO `city_notice` VALUES ('3', 'wwwwwwwwwwwwwwww', '1', '2019-10-02 14:17:02', '2019-10-02 14:17:02');

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
INSERT INTO `city_player` VALUES ('100', 'FD826FE2E378445594D23CA84C0C485D', '123', '123', '123', null, '', '0', '1', '2019-09-12 02:37:37', null);
INSERT INTO `city_player` VALUES ('111', '99B1D351FD5242CD8F73E86A2BBC89A0', '17879502040', 'zp00', '123445', '123445', 'fcf7dd', '0', '1', '2019-09-28 11:31:28', '2019-10-04 21:13:42');
INSERT INTO `city_player` VALUES ('112', 'E13D6322D389411C959CD7AC7A2B230F', '17879502041', 'zp01', '11111111', null, 'eb47d7', '0', '1', '2019-09-28 11:32:11', null);
INSERT INTO `city_player` VALUES ('113', 'A701ECCF318A4B018407C821A551DA81', '17896415468', 'sfdfsf', '111111', null, 'c7f8f9', '0', '1', '2019-10-04 16:43:55', null);
INSERT INTO `city_player` VALUES ('114', '55698633E60848EEAEEB84A5CDC4B317', '17897945648', 'dfsd', '111111', null, 'e43865', '0', '1', '2019-10-04 16:48:52', null);
INSERT INTO `city_player` VALUES ('115', '661964B73D22476AAE2444B97B3A7A57', '16848989456', 'awdw', '111111', null, '984c4e', '0', '1', '2019-10-04 17:08:02', null);
INSERT INTO `city_player` VALUES ('116', '1CF9477B70E044FBA962616485FC90D2', '16848988456', 'awdi', '111111', null, '3e53b6', '0', '1', '2019-10-04 17:14:02', null);
INSERT INTO `city_player` VALUES ('117', '908D3C99EC404C8184EC7D39D88054C5', '16948989456', 'awdif', '111111', null, '198c5f', '0', '1', '2019-10-04 17:15:39', null);
INSERT INTO `city_player` VALUES ('118', 'C7C3151A0ADB47DCB85A74842711DDF0', '17897548564', 'erwrew', '111111', null, '2e0d28', '0', '1', '2019-10-04 17:23:58', null);
INSERT INTO `city_player` VALUES ('119', '4FE57D9A8830433987FC930A65C3A7F4', '17895464845', 'asdasd', '111111', null, '56cead', '0', '1', '2019-10-04 17:35:28', null);
INSERT INTO `city_player` VALUES ('120', '84F74A60724D4293B7506C9A72A023B8', '17895464849', 'asdasu', '111111', null, 'c3a9e9', '0', '1', '2019-10-04 17:38:52', null);
INSERT INTO `city_player` VALUES ('121', '28588A2E551248C4A48359BF03796780', '17895464844', 'asdasg', 'dsssss', null, '2a18a7', '0', '1', '2019-10-04 17:45:22', null);
INSERT INTO `city_player` VALUES ('122', '8FAA7F265AA0477EB81FA80BC0A0F959', '17895484849', 'asdsg', 'dsssss', null, '515c30', '0', '1', '2019-10-04 17:45:57', null);
INSERT INTO `city_player` VALUES ('123', 'E5FC87A257DA4F2ABBA69CD323DBE6C1', '17895984849', 'apdsg', 'dsssss', null, '393126', '0', '1', '2019-10-04 17:46:23', null);
INSERT INTO `city_player` VALUES ('124', '622498A4D5804F4F9CC59AC13443DF7B', '17895984842', 'apdst', 'dsssss', null, '252392', '0', '1', '2019-10-04 17:47:35', null);
INSERT INTO `city_player` VALUES ('125', '458AAD9035B142048BAE8EEE291E6CC3', '17895989842', 'apdso', 'dsssss', null, 'a245f9', '0', '1', '2019-10-04 17:49:23', null);
INSERT INTO `city_player` VALUES ('126', '906EA12CBF514EB6B2F8E5A512074FFF', '17895989844', 'apdppo', 'dsssss', null, '0acb06', '0', '1', '2019-10-04 17:50:22', null);
INSERT INTO `city_player` VALUES ('127', 'B287F9590C5E46BEAEF178F8EB6BE8CD', '17895909844', 'apdppj', 'dsssss', null, '54b1b2', '0', '1', '2019-10-04 17:52:26', null);
INSERT INTO `city_player` VALUES ('128', 'BAF99A6B9B1249A78B27AD530154438D', '17895909848', 'apdppj1', 'dsssss', null, 'dec565', '0', '1', '2019-10-04 17:54:39', null);
INSERT INTO `city_player` VALUES ('129', '65BE42D75E95423CA4A8002E1AEFDFB5', '17895909849', 'apdppj1p', 'dsssss', null, 'bd6e61', '0', '1', '2019-10-04 17:55:32', null);
INSERT INTO `city_player` VALUES ('130', '97B5B35F47CF466AA55FC76D980DEC0E', '17895909888', 'apdppj1p1', 'dsssss', null, '48f768', '0', '1', '2019-10-04 17:56:03', null);
INSERT INTO `city_player` VALUES ('131', 'EEB19A3B8B064A4D950A7D076953D72A', '14977894456', 'qwew', '111111', null, 'f50d84', '0', '1', '2019-10-04 18:05:52', null);
INSERT INTO `city_player` VALUES ('132', '17835EA7B4CD4AA5924BF0C69A6184A5', '17879502056', 'qwe', '111111', null, '8fafae', '0', '1', '2019-10-04 19:04:49', null);
INSERT INTO `city_player` VALUES ('133', '4AF375CA36A248E185A57955D840FC38', '18826444089', 'ggjjs', '123456', null, '801dd6', '0', '1', '2019-10-04 21:26:31', null);

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
  `order_amount` decimal(9,4) unsigned DEFAULT NULL COMMENT '投资金额',
  `order_state` varchar(60) DEFAULT NULL COMMENT '状态(SUBSCRIBE预约，SUBSCRIBED预约中,SUBSCRIBE_PASS预约成功,INVEST投资,INVESTED已投资,MANAGEMENT经营中,EXTRACT可提取,FINISHED已完成,CANCEL取消,SUBSCRIBE_VERIFY_FAIL预约审核不通过，INVALID作废)',
  `order_repeat` tinyint(4) unsigned DEFAULT '0' COMMENT '是否复投（0否，1是）',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`order_id`) USING BTREE,
  KEY `index_order_invest_id` (`order_invest_id`),
  KEY `index_order_payer_id` (`order_payer_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='订单（投资记录）';

-- ----------------------------
-- Records of invest_order
-- ----------------------------
INSERT INTO `invest_order` VALUES ('1', '1', '99B1D351FD5242CD8F73E86A2BBC89A0', '订单名1', '12141', '5.0000', 'MANAGEMENT', '0', '2019-10-02 12:43:21', '2019-10-12 18:59:34');
INSERT INTO `invest_order` VALUES ('2', '2', '99B1D351FD5242CD8F73E86A2BBC89A0', '订单名2', '3341341342', '1.0000', 'INVESTED', '0', '2019-10-03 12:44:28', '2019-10-03 12:44:33');
INSERT INTO `invest_order` VALUES ('3', '1', 'E13D6322D389411C959CD7AC7A2B230F', '订单名3', '63663', '5.0000', 'MANAGEMENT', '0', '2019-10-02 12:45:37', '2019-10-02 12:45:42');

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
-- Records of likes_log
-- ----------------------------
INSERT INTO `likes_log` VALUES ('1', '1', 'FD826FE2E378445594D23CA84C0C485D', '99B1D351FD5242CD8F73E86A2BBC89A0', '1', '2019-10-08 19:10:34', '2019-10-08 19:10:38');

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
  `acc_usdt` decimal(9,4) NOT NULL DEFAULT '0.0000' COMMENT '账户usdt额度',
  `acc_usdt_available` decimal(9,4) unsigned NOT NULL DEFAULT '0.0000' COMMENT 'usdt可用金额',
  `acc_usdt_freeze` decimal(9,4) unsigned NOT NULL DEFAULT '0.0000' COMMENT 'usdt冻结金额',
  `acc_mt` decimal(9,4) NOT NULL DEFAULT '0.0000' COMMENT '账户mt额度',
  `acc_mt_available` decimal(9,4) unsigned NOT NULL DEFAULT '0.0000' COMMENT 'mt可用金额',
  `acc_mt_freeze` decimal(9,4) unsigned NOT NULL DEFAULT '0.0000' COMMENT 'mt冻结金额',
  `acc_income` decimal(9,4) unsigned NOT NULL DEFAULT '0.0000' COMMENT '积累总收入',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`acc_id`),
  KEY `index_acc_player_id` (`acc_player_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='玩家账户表';

-- ----------------------------
-- Records of player_account
-- ----------------------------
INSERT INTO `player_account` VALUES ('1', '99B1D351FD5242CD8F73E86A2BBC89A0', 'aaaa', '123456', '1400.0000', '800.0000', '600.0000', '1000.0000', '900.0000', '98.0000', '10.0000', '2019-10-09 18:56:26', '2019-10-12 18:59:49');

-- ----------------------------
-- Table structure for player_account_log
-- ----------------------------
DROP TABLE IF EXISTS `player_account_log`;
CREATE TABLE `player_account_log` (
  `id` int(11) NOT NULL,
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
-- Records of player_earning
-- ----------------------------
INSERT INTO `player_earning` VALUES ('1', '1', '99B1D351FD5242CD8F73E86A2BBC89A0', '20.0000', '10.0000', '0.0100', '0.0200', 'N', '2019-10-08 18:52:50', '2019-10-08 18:52:54');

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
-- Records of player_friends
-- ----------------------------
INSERT INTO `player_friends` VALUES ('1', 'FD826FE2E378445594D23CA84C0C485D', '99B1D351FD5242CD8F73E86A2BBC89A0', '0', '123', '0', '2019-09-11 17:04:40', '2019-09-12 09:44:19');
INSERT INTO `player_friends` VALUES ('2', 'FD826FE2E378445594D23CA84C0C485D', 'E13D6322D389411C959CD7AC7A2B230F', '0', '123', '0', '2019-09-12 17:21:09', null);
INSERT INTO `player_friends` VALUES ('6', '99B1D351FD5242CD8F73E86A2BBC89A0', 'E13D6322D389411C959CD7AC7A2B230F', '1', '123', '1', '2019-09-28 17:36:01', '2019-09-28 19:39:33');
INSERT INTO `player_friends` VALUES ('7', 'E13D6322D389411C959CD7AC7A2B230F', '99B1D351FD5242CD8F73E86A2BBC89A0', '0', '123', '1', '2019-09-28 18:01:24', null);

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
-- Records of player_game_setting
-- ----------------------------
INSERT INTO `player_game_setting` VALUES ('1', 'FD826FE2E378445594D23CA84C0C485D', 'game', 'true', '1', '2019-10-07 15:35:11', '2019-09-16 08:25:39');
INSERT INTO `player_game_setting` VALUES ('2', 'FD826FE2E378445594D23CA84C0C485D', 'bg', 'false', '1', '2019-10-07 15:35:17', '2019-09-16 08:26:33');

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
-- Records of player_grade
-- ----------------------------
INSERT INTO `player_grade` VALUES ('1', 'E13D6322D389411C959CD7AC7A2B230F', '1', '0', null, null);
INSERT INTO `player_grade` VALUES ('2', '99B1D351FD5242CD8F73E86A2BBC89A0', '1', '0', null, null);
INSERT INTO `player_grade` VALUES ('3', 'FD826FE2E378445594D23CA84C0C485D', '1', '0', null, null);

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
-- Records of player_likes
-- ----------------------------
INSERT INTO `player_likes` VALUES ('1', 'FD826FE2E378445594D23CA84C0C485D', '1', '2', '0', '2019-09-08 15:58:07', null);
INSERT INTO `player_likes` VALUES ('2', 'FD826FE2E378445594D23CA84C0C485D', '2', '1', '1', '2019-09-09 15:58:23', null);
INSERT INTO `player_likes` VALUES ('3', 'FD826FE2E378445594D23CA84C0C485D', null, '1', '4', '2019-09-18 11:59:08', null);
INSERT INTO `player_likes` VALUES ('4', 'FD826FE2E378445594D23CA84C0C485D', null, '1', '7', '2019-09-18 11:59:13', null);
INSERT INTO `player_likes` VALUES ('5', 'FD826FE2E378445594D23CA84C0C485D', null, '1', '5', '2019-09-18 12:02:44', null);
INSERT INTO `player_likes` VALUES ('6', 'FD826FE2E378445594D23CA84C0C485D', null, '1', '3', '2019-09-18 12:02:44', null);

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
  `trade_amount` decimal(9,4) NOT NULL DEFAULT '0.0000' COMMENT '交易金额',
  `personal_tax` decimal(9,4) unsigned DEFAULT NULL COMMENT '个人所得税',
  `enterprise_tax` decimal(9,4) unsigned DEFAULT NULL COMMENT '企业所得税',
  `trade_status` varchar(30) DEFAULT NULL COMMENT '交易状态(FREEZE冻结,OUT已出账,IN已入账)',
  `in_out` varchar(10) DEFAULT NULL COMMENT '入账还是出账(入账in,出账out)',
  `trade_type` varchar(30) DEFAULT NULL COMMENT '交易类型（充值:recharge,转账:transfer,提现:withdraw,购买mt:buy_mt,投资invest,投资收益:invest_earnings)',
  `trade_desc` varchar(100) DEFAULT NULL COMMENT '交易描述',
  `create_time` datetime DEFAULT NULL COMMENT '交易时间',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`trade_id`),
  KEY `index_trade_player_id` (`trade_player_id`),
  KEY `index_trade_order_id` (`trade_order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='交易记录表';

-- ----------------------------
-- Records of player_trade
-- ----------------------------
INSERT INTO `player_trade` VALUES ('1', '1000000006', '99B1D351FD5242CD8F73E86A2BBC89A0', '1', '200.0000', '1.0000', '1.0000', 'FREEZE', 'OUT', 'INVEST', '投资adgagaf', '2019-10-09 21:50:30', '2019-10-09 21:50:37');
INSERT INTO `player_trade` VALUES ('2', '1000000006', '99B1D351FD5242CD8F73E86A2BBC89A0', null, '100.0000', null, null, 'IN', 'IN', 'RECHARGE', '充值', '2019-10-08 18:02:15', '2019-10-09 18:02:19');
INSERT INTO `player_trade` VALUES ('3', '1000000006', '99B1D351FD5242CD8F73E86A2BBC89A0', null, '10.0000', '0.1000', '0.0200', 'FREEZE', 'OUT', 'WITHDRAW', '提现冻结', '2019-10-08 18:03:52', '2019-10-09 18:03:56');
INSERT INTO `player_trade` VALUES ('4', '1000000006', '99B1D351FD5242CD8F73E86A2BBC89A0', null, '5.0000', '0.0100', '0.0200', 'OUT', 'OUT', 'WITHDRAW', '提现审核通过扣款', '2019-10-01 18:05:43', '2019-10-02 18:05:46');
INSERT INTO `player_trade` VALUES ('5', '1000000006', '99B1D351FD5242CD8F73E86A2BBC89A0', null, '2.0000', null, null, 'OUT', 'OUT', 'TRANSFER', '内部转账', '2019-10-03 18:07:14', '2019-10-04 18:07:19');
INSERT INTO `player_trade` VALUES ('6', '1000000006', '99B1D351FD5242CD8F73E86A2BBC89A0', null, '10.0000', null, null, 'FREEZE', 'OUT', 'TRANSFER', '外部转账', '2019-10-01 18:08:51', '2019-10-02 18:08:55');
INSERT INTO `player_trade` VALUES ('8', '1000000006', '99B1D351FD5242CD8F73E86A2BBC89A0', '1', '200.0000', '1.0000', '1.0000', null, 'OUT', 'INVEST', '扣除冻结投资金额', '2019-10-12 18:59:53', '2019-10-12 18:59:53');

-- ----------------------------
-- Table structure for qrtz_blob_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_blob_triggers`;
CREATE TABLE `qrtz_blob_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `BLOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `SCHED_NAME` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_blob_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_calendars
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_calendars`;
CREATE TABLE `qrtz_calendars` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `CALENDAR_NAME` varchar(200) NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`CALENDAR_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_calendars
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_cron_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_cron_triggers`;
CREATE TABLE `qrtz_cron_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `CRON_EXPRESSION` varchar(120) NOT NULL,
  `TIME_ZONE_ID` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_cron_triggers
-- ----------------------------
INSERT INTO `qrtz_cron_triggers` VALUES ('schedulerFactoryBean', 'JobDemo2', 'JobDemoTest', '0/15 * * * * ?', 'Asia/Shanghai');
INSERT INTO `qrtz_cron_triggers` VALUES ('schedulerFactoryBean', 'JobDemo3', 'JobDemoTest', '0/3 * * * * ?', 'Asia/Shanghai');
INSERT INTO `qrtz_cron_triggers` VALUES ('schedulerFactoryBean', 'JobDemo5', 'JobDemoTest', '0/20 * * * * ?', 'Asia/Shanghai');

-- ----------------------------
-- Table structure for qrtz_fired_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_fired_triggers`;
CREATE TABLE `qrtz_fired_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `ENTRY_ID` varchar(95) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `FIRED_TIME` bigint(13) NOT NULL,
  `SCHED_TIME` bigint(13) NOT NULL,
  `PRIORITY` int(11) NOT NULL,
  `STATE` varchar(16) NOT NULL,
  `JOB_NAME` varchar(200) DEFAULT NULL,
  `JOB_GROUP` varchar(200) DEFAULT NULL,
  `IS_NONCONCURRENT` varchar(1) DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`ENTRY_ID`),
  KEY `IDX_QRTZ_FT_TRIG_INST_NAME` (`SCHED_NAME`,`INSTANCE_NAME`),
  KEY `IDX_QRTZ_FT_INST_JOB_REQ_RCVRY` (`SCHED_NAME`,`INSTANCE_NAME`,`REQUESTS_RECOVERY`),
  KEY `IDX_QRTZ_FT_J_G` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_FT_JG` (`SCHED_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_FT_T_G` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_FT_TG` (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_fired_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_job_details
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_job_details`;
CREATE TABLE `qrtz_job_details` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) NOT NULL,
  `IS_DURABLE` varchar(1) NOT NULL,
  `IS_NONCONCURRENT` varchar(1) NOT NULL,
  `IS_UPDATE_DATA` varchar(1) NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) NOT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_J_REQ_RECOVERY` (`SCHED_NAME`,`REQUESTS_RECOVERY`),
  KEY `IDX_QRTZ_J_GRP` (`SCHED_NAME`,`JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_job_details
-- ----------------------------
INSERT INTO `qrtz_job_details` VALUES ('schedulerFactoryBean', 'JobDemo2', 'JobDemoTest', null, 'com.dream.city.job.JobDemo2', '0', '0', '0', '0', 0x230D0A23467269204F63742031312031363A35333A35372043535420323031390D0A);
INSERT INTO `qrtz_job_details` VALUES ('schedulerFactoryBean', 'JobDemo3', 'JobDemoTest', null, 'com.dream.city.job.JobDemo3', '0', '0', '0', '0', 0x230D0A23467269204F63742031312031373A33383A34352043535420323031390D0A);
INSERT INTO `qrtz_job_details` VALUES ('schedulerFactoryBean', 'JobDemo5', 'JobDemoTest', null, 'com.dream.city.job.JobDemo5', '0', '0', '0', '0', 0x230D0A23467269204F63742031312031373A33393A35372043535420323031390D0A);

-- ----------------------------
-- Table structure for qrtz_locks
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_locks`;
CREATE TABLE `qrtz_locks` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `LOCK_NAME` varchar(40) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`LOCK_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_locks
-- ----------------------------
INSERT INTO `qrtz_locks` VALUES ('schedulerFactoryBean', 'TRIGGER_ACCESS');

-- ----------------------------
-- Table structure for qrtz_paused_trigger_grps
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;
CREATE TABLE `qrtz_paused_trigger_grps` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_paused_trigger_grps
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_scheduler_state
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_scheduler_state`;
CREATE TABLE `qrtz_scheduler_state` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `LAST_CHECKIN_TIME` bigint(13) NOT NULL,
  `CHECKIN_INTERVAL` bigint(13) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`INSTANCE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_scheduler_state
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_simple_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simple_triggers`;
CREATE TABLE `qrtz_simple_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `REPEAT_COUNT` bigint(7) NOT NULL,
  `REPEAT_INTERVAL` bigint(12) NOT NULL,
  `TIMES_TRIGGERED` bigint(10) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_simple_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_simprop_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simprop_triggers`;
CREATE TABLE `qrtz_simprop_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `STR_PROP_1` varchar(512) DEFAULT NULL,
  `STR_PROP_2` varchar(512) DEFAULT NULL,
  `STR_PROP_3` varchar(512) DEFAULT NULL,
  `INT_PROP_1` int(11) DEFAULT NULL,
  `INT_PROP_2` int(11) DEFAULT NULL,
  `LONG_PROP_1` bigint(20) DEFAULT NULL,
  `LONG_PROP_2` bigint(20) DEFAULT NULL,
  `DEC_PROP_1` decimal(13,4) DEFAULT NULL,
  `DEC_PROP_2` decimal(13,4) DEFAULT NULL,
  `BOOL_PROP_1` varchar(1) DEFAULT NULL,
  `BOOL_PROP_2` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_simprop_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_triggers`;
CREATE TABLE `qrtz_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PREV_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PRIORITY` int(11) DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) NOT NULL,
  `TRIGGER_TYPE` varchar(8) NOT NULL,
  `START_TIME` bigint(13) NOT NULL,
  `END_TIME` bigint(13) DEFAULT NULL,
  `CALENDAR_NAME` varchar(200) DEFAULT NULL,
  `MISFIRE_INSTR` smallint(2) DEFAULT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_T_J` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_T_JG` (`SCHED_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_T_C` (`SCHED_NAME`,`CALENDAR_NAME`),
  KEY `IDX_QRTZ_T_G` (`SCHED_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_T_STATE` (`SCHED_NAME`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_N_STATE` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_N_G_STATE` (`SCHED_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_NEXT_FIRE_TIME` (`SCHED_NAME`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_ST` (`SCHED_NAME`,`TRIGGER_STATE`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE_GRP` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `qrtz_job_details` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_triggers
-- ----------------------------
INSERT INTO `qrtz_triggers` VALUES ('schedulerFactoryBean', 'JobDemo2', 'JobDemoTest', 'JobDemo2', 'JobDemoTest', null, '1570855485000', '1570855470000', '5', 'PAUSED', 'CRON', '1570784037000', '0', null, '0', 0x230D0A23536174204F63742031322031323A33383A32382043535420323031390D0A4445534352495054494F4E3D5C75383945365C75353344315C75353636385C75464631414A6F6244656D6F546573742E4A6F6244656D6F320D0A);
INSERT INTO `qrtz_triggers` VALUES ('schedulerFactoryBean', 'JobDemo3', 'JobDemoTest', 'JobDemo3', 'JobDemoTest', null, '1570789422000', '1570789419000', '5', 'PAUSED', 'CRON', '1570786725000', '0', null, '0', '');
INSERT INTO `qrtz_triggers` VALUES ('schedulerFactoryBean', 'JobDemo5', 'JobDemoTest', 'JobDemo5', 'JobDemoTest', null, '1570843800000', '1570843780000', '5', 'PAUSED', 'CRON', '1570786797000', '0', null, '0', '');

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
-- Records of sales_order
-- ----------------------------
INSERT INTO `sales_order` VALUES ('1', '1', '10', '1', '1', '10', 'FD826FE2E378445594D23CA84C0C485D', '99B1D351FD5242CD8F73E86A2BBC89A0', '0', '2019-10-09 11:33:46', null);

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
  `trade_amount` decimal(11,4) DEFAULT NULL COMMENT '交易金额',
  `verify_time` datetime DEFAULT NULL COMMENT '交易审核时间',
  `descr` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `index_trade_id` (`trade_id`),
  KEY `index_player_id` (`player_id`),
  KEY `index_order_id` (`order_id`),
  KEY `index_verify_id` (`verify_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='交易流水表';

-- ----------------------------
-- Records of trade_detail
-- ----------------------------
INSERT INTO `trade_detail` VALUES ('1', '1', '1', '1', '99B1D351FD5242CD8F73E86A2BBC89A0', 'USDT_INVEST_FREEZE', '200.0000', '2019-10-10 18:22:17', '投资冻结', '2019-10-10 18:22:17');
INSERT INTO `trade_detail` VALUES ('2', '2', null, null, '99B1D351FD5242CD8F73E86A2BBC89A0', 'RECHARGE', '100.0000', '2019-10-04 18:12:07', '充值', '2019-10-04 18:11:40');
INSERT INTO `trade_detail` VALUES ('3', '3', '1', null, '99B1D351FD5242CD8F73E86A2BBC89A0', 'WITHDRAW_FREEZE', '10.0000', '2019-10-10 18:23:55', '提现冻结金额', '2019-10-10 18:23:55');
INSERT INTO `trade_detail` VALUES ('4', '4', '4', null, '99B1D351FD5242CD8F73E86A2BBC89A0', 'WITHDRAW_FREEZE', '5.0000', '2019-10-10 18:24:55', '提现冻结金额', '2019-10-10 18:24:55');
INSERT INTO `trade_detail` VALUES ('5', '4', '2', null, '99B1D351FD5242CD8F73E86A2BBC89A0', 'WITHDRAW_VERIFY', '5.0000', '2019-10-10 18:24:57', '提现审核通过扣款', '2019-10-10 18:24:57');
INSERT INTO `trade_detail` VALUES ('6', '6', '5', null, '99B1D351FD5242CD8F73E86A2BBC89A0', 'TRANSFER_FREEZE', '10.0000', '2019-10-10 18:25:19', '转账冻结', '2019-10-10 18:25:19');
INSERT INTO `trade_detail` VALUES ('7', '8', '15', '1', '99B1D351FD5242CD8F73E86A2BBC89A0', 'USDT_INVEST_VERIFY', '200.0000', '2019-10-12 11:00:01', 'usdt投资审核通过扣款', '2019-10-12 19:00:01');
INSERT INTO `trade_detail` VALUES ('8', '8', '15', '1', '99B1D351FD5242CD8F73E86A2BBC89A0', 'MT_INVEST_PERSONAL_TAX', '1.0000', '2019-10-12 11:00:03', 'mt投资个人税金', '2019-10-12 19:00:03');
INSERT INTO `trade_detail` VALUES ('9', '8', '15', '1', '99B1D351FD5242CD8F73E86A2BBC89A0', 'MT_INVEST_ENTERPRISE_TAX', '1.0000', '2019-10-12 11:00:05', 'mt投资企业税金', '2019-10-12 19:00:05');

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
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COMMENT='交易审核表';

-- ----------------------------
-- Records of trade_verify
-- ----------------------------
INSERT INTO `trade_verify` VALUES ('1', '1', '1', '1', 'WAIT', '待审核', '2019-10-01 19:58:44', '2019-10-01 19:58:47');
INSERT INTO `trade_verify` VALUES ('2', '1', '4', null, 'PASS', '提现审核通过扣款', '2019-10-01 20:00:14', '2019-10-01 20:00:18');
INSERT INTO `trade_verify` VALUES ('3', null, '3', null, 'WAIT', '待审核', '2019-10-05 20:18:15', '2019-10-05 20:18:15');
INSERT INTO `trade_verify` VALUES ('4', null, '4', null, 'WAIT', '提现冻结', null, null);
INSERT INTO `trade_verify` VALUES ('5', null, '6', null, 'WAIT', '转账冻结', null, null);
INSERT INTO `trade_verify` VALUES ('10', null, null, '1', 'WAIT', 'ssss', '2019-10-12 18:29:41', '2019-10-12 18:29:41');
INSERT INTO `trade_verify` VALUES ('11', null, null, '1', 'WAIT', 'wwwww', '2019-10-12 18:52:14', '2019-10-12 18:52:14');
INSERT INTO `trade_verify` VALUES ('15', null, null, '1', 'PASS', 'ok', '2019-10-12 18:59:34', '2019-10-12 18:59:34');

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
INSERT INTO `user` VALUES ('1', 'admin', 'e10adc3949ba59abbe56e057f20f883e', '1', null, '管理员', null, null, null, null, null, null, null, '2017-09-21 10:08:53', null, '2017-09-21 10:08:53', null, '0');
INSERT INTO `user` VALUES ('2', 'superadmin', 'e10adc3949ba59abbe56e057f20f883e', '1', null, '超级管理员', null, null, null, null, null, null, null, '2017-09-21 10:08:53', null, '2017-09-21 10:08:53', null, '0');
INSERT INTO `user` VALUES ('3', 'test2', 'e10adc3949ba59abbe56e057f20f883e', '1', null, '测试2', 'test@mail.cn', '13100131000', null, null, null, null, null, '2017-11-05 23:14:22', null, '2017-11-05 23:14:22', null, '0');
INSERT INTO `user` VALUES ('4', 'test1', 'f4cc399f0effd13c888e310ea2cf5399', '1', null, 'test1', 'tw@mail.com', '13800138000', null, null, null, null, null, '2018-05-26 00:00:45', null, '2018-05-16 23:27:53', '123eee', '0');
INSERT INTO `user` VALUES ('5', 'test1', 'e358efa489f58062f10dd7316b65649e', '1', null, 'test', '123@main.com', '123', null, null, null, null, null, '2018-05-19 20:13:03', null, '2018-05-19 20:13:19', '123', '0');

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
