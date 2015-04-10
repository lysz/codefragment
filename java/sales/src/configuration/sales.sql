/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50616
Source Host           : localhost:3306
Source Database       : sales

Target Server Type    : MYSQL
Target Server Version : 50616
File Encoding         : 65001

Date: 2014-05-16 17:30:53
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `tbl_account`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_account`;
CREATE TABLE `tbl_account` (
  `accountId` int(11) NOT NULL AUTO_INCREMENT,
  `accountNumber` varchar(64) DEFAULT NULL COMMENT '帐号',
  `bank` int(11) DEFAULT NULL COMMENT '银行',
  `userName` varchar(64) DEFAULT NULL COMMENT '户头',
  `companyId` int(11) DEFAULT NULL COMMENT '所属公司',
  `contractId` int(11) DEFAULT NULL COMMENT '所属合同',
  `accountType` int(11) DEFAULT NULL COMMENT '帐号类型(对公/对私)',
  PRIMARY KEY (`accountId`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_account
-- ----------------------------
INSERT INTO `tbl_account` VALUES ('36', 'meizu111111111', '16', 'bbbbbbbbbb', '15', '21', '8');
INSERT INTO `tbl_account` VALUES ('37', 'meizu111111111', '17', 'bbbbbb', '15', '21', '9');
INSERT INTO `tbl_account` VALUES ('38', '11111111', '11', '1111111', '14', '22', '8');
INSERT INTO `tbl_account` VALUES ('39', '11111111', '10', '11111111', '14', '22', '9');
INSERT INTO `tbl_account` VALUES ('40', 'sss', '16', 'sssss', '13', '23', '8');
INSERT INTO `tbl_account` VALUES ('41', 'ss', '16', 'sss', '13', '23', '9');

-- ----------------------------
-- Table structure for `tbl_app`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_app`;
CREATE TABLE `tbl_app` (
  `appId` int(11) NOT NULL AUTO_INCREMENT,
  `appName` varchar(256) DEFAULT NULL COMMENT '用应名称',
  `packageName` varchar(256) DEFAULT NULL COMMENT '包名',
  `iconPath` varchar(512) DEFAULT NULL COMMENT 'icon路径',
  `apkPath` varchar(512) DEFAULT NULL COMMENT 'apk存放路径',
  `platformURL` varchar(512) DEFAULT NULL COMMENT '户客后台查询URL',
  `platformPwd` varchar(512) DEFAULT NULL COMMENT '户客后台查询密码',
  `dividedMode` int(11) DEFAULT NULL COMMENT '成分模式Id',
  `offlineDate` datetime DEFAULT NULL COMMENT '下架时间',
  `onlineDate` datetime DEFAULT NULL COMMENT '上架时间',
  `price` varchar(10) DEFAULT NULL COMMENT '单价',
  `mainCategory` int(11) DEFAULT NULL COMMENT '是否大类Id',
  `companyId` int(11) DEFAULT NULL COMMENT '所属公司',
  `createrId` int(11) DEFAULT NULL COMMENT '用应申请者Id',
  `createDate` date DEFAULT NULL COMMENT '请申日期',
  `contractId` int(11) DEFAULT NULL COMMENT '合同Id',
  `statusId` int(11) DEFAULT NULL COMMENT '合作态状Id',
  `testResult` text COMMENT '试测结果Id',
  `description` varchar(512) DEFAULT NULL COMMENT '注备',
  PRIMARY KEY (`appId`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_app
-- ----------------------------
INSERT INTO `tbl_app` VALUES ('18', 'aora', 'com.aora.anzhuo', null, 'aaaabbbb', null, null, '23', null, '2014-04-24 09:22:19', null, '20', '13', '0', '2014-04-18', '0', '29', '', null);
INSERT INTO `tbl_app` VALUES ('19', 'Zdsfasdf', 'com.meizu.flyme', null, 'asdf', null, null, '23', null, '2014-04-24 09:23:30', null, '21', '15', '0', '2014-04-18', '0', '29', '', null);
INSERT INTO `tbl_app` VALUES ('20', 'asdf', 'com.xiaomi.phone', null, 'asdf', null, null, '22', null, '2014-04-24 09:23:51', null, '21', '14', '0', '2014-04-18', '0', '29', '', null);
INSERT INTO `tbl_app` VALUES ('21', 'asdf', 'com.gg', null, 'asdfasdf', null, null, '23', null, '2014-05-05 09:33:46', null, '21', '17', '0', '2014-04-18', '0', '28', '', null);
INSERT INTO `tbl_app` VALUES ('22', 'asdf', 'com.xunlei', null, 'asdf', null, null, '23', null, '2014-05-05 09:33:59', null, '21', '16', '0', '2014-04-18', '0', '25', '通过:xxxx', null);
INSERT INTO `tbl_app` VALUES ('23', 'asdf', 'com.aora', null, 'asdf', null, null, '24', null, '2014-05-05 09:34:11', null, '21', '13', '0', '2014-04-18', '0', '28', '', null);
INSERT INTO `tbl_app` VALUES ('24', 'adsrf', 'mi.xiao', null, 'aserf', null, null, '22', '2014-04-24 14:57:03', '2014-04-24 14:56:22', null, '20', '14', '0', '2014-04-18', '0', '29', '', null);
INSERT INTO `tbl_app` VALUES ('25', 'sadf', 'com.xiaomi', null, 'asdf', null, null, '23', null, '2014-05-05 09:34:29', null, '20', '14', '0', '2014-04-21', '0', '28', '通过:asdf', null);
INSERT INTO `tbl_app` VALUES ('26', 'aaaaaaaaaa', null, null, 'aaaaaaaaaaaaaaaaaaaaa', null, null, '23', null, null, null, '21', '16', '0', '2014-04-21', '0', '28', '', null);
INSERT INTO `tbl_app` VALUES ('27', 'SDf', 'com.mi.com', null, 'bbbbbbbbbbbbbbbbbbb', null, null, '23', null, '2014-04-25 09:49:13', null, '20', '14', '0', '2014-04-21', '0', '40', '', null);
INSERT INTO `tbl_app` VALUES ('28', 'asdf', null, null, 'asdf', null, null, '23', null, null, null, '21', '15', '0', '2014-04-21', '0', '37', '通过', null);
INSERT INTO `tbl_app` VALUES ('29', 'sssss', 'com.mi.xao', null, 'asdfasdfa', null, null, '23', null, '2014-05-05 09:34:42', null, '21', '14', '0', '2014-04-24', '0', '28', '通过:OK!!!!!!!!!!!!!!', null);
INSERT INTO `tbl_app` VALUES ('30', 'com.aora.water', 'com.aora.water', null, '/data/apks/', null, null, '23', null, '2014-05-05 09:34:51', null, '20', '13', '0', '2014-04-24', '0', '28', '通过', null);
INSERT INTO `tbl_app` VALUES ('31', '4235', 'com.xiaomi.tv', null, '23452345', null, null, '23', null, '2014-05-05 09:35:07', null, '20', '14', '0', '2014-04-28', '0', '28', '通过', null);

-- ----------------------------
-- Table structure for `tbl_audit`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_audit`;
CREATE TABLE `tbl_audit` (
  `auditId` int(11) NOT NULL AUTO_INCREMENT,
  `auditorId` varchar(0) DEFAULT NULL COMMENT '批审者Id',
  `auditTypeId` int(11) DEFAULT NULL COMMENT '审核类型 (CP上架审核, CP下架审核, 结算申请审核, 打款申请审核)',
  `auditTargetId` int(11) DEFAULT NULL COMMENT '批审对象: 应用上架审批, 收款审批,  打款审批',
  `auditResult` int(11) DEFAULT NULL COMMENT '核审结果',
  `auditStatus` int(11) DEFAULT NULL COMMENT '审核态状(己审, 未审)',
  `description` varchar(512) DEFAULT NULL,
  PRIMARY KEY (`auditId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_audit
-- ----------------------------

-- ----------------------------
-- Table structure for `tbl_auditcategory`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_auditcategory`;
CREATE TABLE `tbl_auditcategory` (
  `auditCategoryId` int(11) NOT NULL AUTO_INCREMENT,
  `categoryName` varchar(64) DEFAULT NULL COMMENT '类别名称',
  `description` varchar(64) DEFAULT NULL COMMENT '注备',
  PRIMARY KEY (`auditCategoryId`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_auditcategory
-- ----------------------------
INSERT INTO `tbl_auditcategory` VALUES ('1', 'cponline', 'cp上架申请');
INSERT INTO `tbl_auditcategory` VALUES ('2', 'cpoffline', 'cp下架申请');
INSERT INTO `tbl_auditcategory` VALUES ('3', 'receivable', '收款申请');
INSERT INTO `tbl_auditcategory` VALUES ('4', 'payment', '付款申请');

-- ----------------------------
-- Table structure for `tbl_auditseq`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_auditseq`;
CREATE TABLE `tbl_auditseq` (
  `suditSeqId` int(11) NOT NULL AUTO_INCREMENT,
  `auditCategoryId` int(11) DEFAULT NULL COMMENT '审核类别Id',
  `roleId` int(11) DEFAULT NULL COMMENT '色角Id',
  `order` int(11) DEFAULT NULL COMMENT '顺序',
  `description` varchar(512) DEFAULT NULL,
  PRIMARY KEY (`suditSeqId`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_auditseq
-- ----------------------------
INSERT INTO `tbl_auditseq` VALUES ('1', '1', '1', '1', null);
INSERT INTO `tbl_auditseq` VALUES ('2', '1', '4', '2', null);
INSERT INTO `tbl_auditseq` VALUES ('3', '1', '3', '3', null);
INSERT INTO `tbl_auditseq` VALUES ('4', '2', '1', '1', null);
INSERT INTO `tbl_auditseq` VALUES ('5', '2', '3', '2', null);

-- ----------------------------
-- Table structure for `tbl_bill`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_bill`;
CREATE TABLE `tbl_bill` (
  `billId` int(11) NOT NULL AUTO_INCREMENT,
  `companyId` int(11) DEFAULT NULL COMMENT '公司Id',
  `billDate` date DEFAULT NULL COMMENT '帐单日期',
  `totalMoney` decimal(10,0) DEFAULT NULL COMMENT '流水汇总',
  `requiredMoney` decimal(10,0) DEFAULT NULL COMMENT '应收金额',
  `actualMoney` decimal(10,0) DEFAULT NULL COMMENT '实际结算金额',
  `createDate` date DEFAULT NULL COMMENT '申请日期',
  `status` int(11) DEFAULT NULL COMMENT '审批状态',
  `invoiceId` int(11) DEFAULT NULL COMMENT '发票编号 ',
  `lastChangeDate` datetime DEFAULT NULL COMMENT '最后修改时间',
  `description` varchar(512) DEFAULT NULL,
  PRIMARY KEY (`billId`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_bill
-- ----------------------------
INSERT INTO `tbl_bill` VALUES ('15', '16', '2014-05-07', '10900', '10900', null, '2014-05-07', '41', '0', '2014-05-07 14:35:00', null);
INSERT INTO `tbl_bill` VALUES ('16', '13', '2014-05-07', '29670', '22065', null, '2014-05-07', '41', '0', '2014-05-07 14:35:00', null);
INSERT INTO `tbl_bill` VALUES ('17', '14', '2014-05-07', '116139', '116139', null, '2014-05-07', '41', '0', '2014-05-07 14:35:00', null);
INSERT INTO `tbl_bill` VALUES ('18', '17', '2014-05-07', '0', '0', null, '2014-05-07', '41', '0', '2014-05-07 16:04:00', null);
INSERT INTO `tbl_bill` VALUES ('19', '15', '2014-05-07', '4000', '4000', null, '2014-05-07', '41', '0', '2014-05-07 16:04:00', null);

-- ----------------------------
-- Table structure for `tbl_billdetail`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_billdetail`;
CREATE TABLE `tbl_billdetail` (
  `billDetailId` int(11) NOT NULL AUTO_INCREMENT,
  `billId` int(11) DEFAULT NULL,
  `appId` int(11) DEFAULT NULL,
  `expression` varchar(512) DEFAULT NULL COMMENT '计算工式',
  `totalMoney` decimal(10,0) DEFAULT NULL COMMENT '流水汇总',
  `requiredMoney` decimal(10,0) DEFAULT NULL COMMENT '应收金额',
  `actualMoney` decimal(10,0) DEFAULT NULL COMMENT '结算金额',
  `downloadCount` int(11) DEFAULT NULL COMMENT '本月下载量',
  `spreadDate` text COMMENT '广推日期',
  `benefit` decimal(10,0) DEFAULT NULL COMMENT '单次下载收益',
  `billMonth` varchar(64) DEFAULT NULL COMMENT '帐单月',
  `description` varchar(512) DEFAULT NULL,
  PRIMARY KEY (`billDetailId`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_billdetail
-- ----------------------------
INSERT INTO `tbl_billdetail` VALUES ('7', '15', '26', null, '10900', '10900', null, null, '5.1,5.8', null, '2014.5', null);
INSERT INTO `tbl_billdetail` VALUES ('8', '16', '18', null, '3660', '3660', null, null, '5.1,5.3,5.5,5.31', null, '2014.5', null);
INSERT INTO `tbl_billdetail` VALUES ('9', '16', '30', null, '10800', '10800', null, null, '5.1,5.7', null, '2014.5', null);
INSERT INTO `tbl_billdetail` VALUES ('10', '17', '25', null, '3000', '3000', null, null, '5.3', null, '2014.5', null);
INSERT INTO `tbl_billdetail` VALUES ('11', '17', '29', null, '10500', '10500', null, null, '5.4,5.10', null, '2014.5', null);
INSERT INTO `tbl_billdetail` VALUES ('12', '17', '31', null, '10490', '10490', null, null, '5.2,5.29', null, '2014.5', null);
INSERT INTO `tbl_billdetail` VALUES ('13', '18', '21', null, '0', '0', null, null, '', null, '2014.5', null);
INSERT INTO `tbl_billdetail` VALUES ('14', '19', '19', null, '0', '0', null, null, '', null, '2014.5', null);
INSERT INTO `tbl_billdetail` VALUES ('15', '19', '28', null, '4000', '4000', null, null, '5.1,5.3', null, '2014.5', null);
INSERT INTO `tbl_billdetail` VALUES ('16', '16', '23', '0.5', '15210', '7605', null, null, '5.21,5.30', null, '2014.5', null);
INSERT INTO `tbl_billdetail` VALUES ('17', '17', '20', null, '0', '0', null, null, '', null, '2014.5', null);
INSERT INTO `tbl_billdetail` VALUES ('18', '17', '24', null, '0', '0', null, null, '', null, '2014.5', null);
INSERT INTO `tbl_billdetail` VALUES ('19', '17', '27', null, '92149', '92149', null, null, '5.1,5.5,5.8,5.27', null, '2014.5', null);

-- ----------------------------
-- Table structure for `tbl_company`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_company`;
CREATE TABLE `tbl_company` (
  `companyId` int(11) NOT NULL AUTO_INCREMENT,
  `companyName` varchar(128) DEFAULT NULL,
  `categoryId` int(11) DEFAULT NULL COMMENT '公司类型',
  `billingCycleId` int(11) DEFAULT NULL COMMENT '结算周期',
  `contact` varchar(64) DEFAULT NULL COMMENT '联系人',
  `telephone` varchar(64) DEFAULT NULL COMMENT '联系电话',
  `mobilephone` varchar(64) DEFAULT NULL COMMENT '手机号码',
  `email` varchar(64) DEFAULT NULL,
  `creatorId` int(11) DEFAULT NULL COMMENT '创建者',
  `createDate` datetime DEFAULT NULL COMMENT '建创时间',
  `lastChangeDate` datetime DEFAULT NULL COMMENT '最后修改时间',
  `description` varchar(512) DEFAULT NULL,
  PRIMARY KEY (`companyId`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_company
-- ----------------------------
INSERT INTO `tbl_company` VALUES ('13', '奥软', '6', null, '杨总', '12341234', '', 'aora@aora.com', '0', '2014-04-03 14:27:16', '2014-04-03 14:27:16', '深圳奥软网络有限公司，，，');
INSERT INTO `tbl_company` VALUES ('14', '小米', '6', null, '雷军', 'q2341234', '', '', '0', '2014-04-03 14:27:59', '2014-04-03 14:27:59', '雷军张牙舞爪的样子挺讨厌的');
INSERT INTO `tbl_company` VALUES ('15', '魅族', '6', null, '黄章', '245623', '', 'huangzhang@meizu.com', '0', '2014-04-03 14:28:33', '2014-04-03 14:28:33', 'huangzhang@meizu.com');
INSERT INTO `tbl_company` VALUES ('16', '迅雷', '6', null, '邹胜龙', '123452345', '', 'zsl@xunlei.com', '0', '2014-04-03 14:29:27', '2014-04-03 14:29:27', '迅雷网络技术有限公司，迅雷看看，迅雷下载');
INSERT INTO `tbl_company` VALUES ('17', '广告公司', '7', null, '广告', '2134', '', '', '0', '2014-04-03 14:29:56', '2014-04-03 14:29:56', '');

-- ----------------------------
-- Table structure for `tbl_contract`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_contract`;
CREATE TABLE `tbl_contract` (
  `contractId` int(11) NOT NULL AUTO_INCREMENT,
  `contractCode` varchar(128) DEFAULT NULL COMMENT '合同编号',
  `companyId` int(11) DEFAULT NULL COMMENT '所属公司Id',
  `createTime` date DEFAULT NULL COMMENT '合同签订时间',
  `createrId` int(11) DEFAULT NULL COMMENT '创建者',
  `startTime` date DEFAULT NULL COMMENT '合同生效时间',
  `endTime` date DEFAULT NULL COMMENT '合同结束时间',
  `attachmentPath` text COMMENT '附件地址',
  `contact` varchar(64) DEFAULT NULL,
  `telephone` varchar(64) DEFAULT NULL,
  `searchURL` text,
  `searchpwd` varchar(64) DEFAULT NULL,
  `lastChangeDate` datetime DEFAULT NULL COMMENT '最后修改时间',
  `status` int(11) DEFAULT NULL COMMENT '合同状态',
  `description` varchar(512) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`contractId`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_contract
-- ----------------------------
INSERT INTO `tbl_contract` VALUES ('21', 'meizu111111111', '15', '2014-04-14', '0', '2014-04-10', '2014-04-12', 'F:\\temp\\63.png', 'bbbb', 'meizu111111111', 'bbbbbbbb', 'meizu111111111', '2014-04-14 16:36:49', '0', '');
INSERT INTO `tbl_contract` VALUES ('22', 'xiaomi...........', '14', '2014-04-14', '0', '2014-04-01', '2014-04-05', 'F:\\temp\\62.png', '111111111', '1111111111', '22222222', '00000', '2014-04-15 08:55:21', '0', '');
INSERT INTO `tbl_contract` VALUES ('23', 'sss', '13', '2014-04-25', '0', '2014-04-01', '2014-04-04', 'F:\\temp\\apache-tomcat-6.0.39.zip', 'sss', 'sssss', 'sssss', 'ssssss', '2014-04-25 14:03:39', '0', '');

-- ----------------------------
-- Table structure for `tbl_cpaspreaddate`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_cpaspreaddate`;
CREATE TABLE `tbl_cpaspreaddate` (
  `cpasId` int(11) NOT NULL AUTO_INCREMENT,
  `appId` int(11) DEFAULT NULL,
  `spreadDate` varchar(64) DEFAULT NULL,
  `price` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`cpasId`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_cpaspreaddate
-- ----------------------------
INSERT INTO `tbl_cpaspreaddate` VALUES ('5', '30', '2014-5-8', '5800');
INSERT INTO `tbl_cpaspreaddate` VALUES ('7', '30', '2014-5-3', '53');
INSERT INTO `tbl_cpaspreaddate` VALUES ('11', '30', '2014-5-31', '531');
INSERT INTO `tbl_cpaspreaddate` VALUES ('13', '18', '2014-5-2', '1520');
INSERT INTO `tbl_cpaspreaddate` VALUES ('17', '18', '2014-5-31', '531');
INSERT INTO `tbl_cpaspreaddate` VALUES ('26', '23', '2014-5-30', '10000');
INSERT INTO `tbl_cpaspreaddate` VALUES ('27', '23', '2014-5-21', '5210');

-- ----------------------------
-- Table structure for `tbl_cptspreaddate`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_cptspreaddate`;
CREATE TABLE `tbl_cptspreaddate` (
  `cptSpreadId` int(11) NOT NULL AUTO_INCREMENT,
  `appId` int(11) DEFAULT NULL,
  `spreadDate` varchar(64) DEFAULT NULL,
  `price` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`cptSpreadId`)
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_cptspreaddate
-- ----------------------------
INSERT INTO `tbl_cptspreaddate` VALUES ('4', '19', '2014-7-1', '1000');
INSERT INTO `tbl_cptspreaddate` VALUES ('5', '19', '2014-7-8', '800');
INSERT INTO `tbl_cptspreaddate` VALUES ('6', '21', '2014-6-4', '4000');
INSERT INTO `tbl_cptspreaddate` VALUES ('7', '21', '2014-6-9', '9000');
INSERT INTO `tbl_cptspreaddate` VALUES ('8', '21', '2014-9-2', '200');
INSERT INTO `tbl_cptspreaddate` VALUES ('9', '21', '2014-9-8', '8000');
INSERT INTO `tbl_cptspreaddate` VALUES ('10', '25', '2014-4-1', '500');
INSERT INTO `tbl_cptspreaddate` VALUES ('11', '25', '2014-4-8', '800');
INSERT INTO `tbl_cptspreaddate` VALUES ('12', '25', '2014-6-1', '1000');
INSERT INTO `tbl_cptspreaddate` VALUES ('13', '25', '2014-6-9', '9000');
INSERT INTO `tbl_cptspreaddate` VALUES ('14', '25', '2014-5-3', '3000');
INSERT INTO `tbl_cptspreaddate` VALUES ('15', '25', '2014-8-8', '8000');
INSERT INTO `tbl_cptspreaddate` VALUES ('16', '25', '2014-8-2', '2000');
INSERT INTO `tbl_cptspreaddate` VALUES ('17', '25', '2014-7-3', '3000');
INSERT INTO `tbl_cptspreaddate` VALUES ('18', '25', '2014-7-7', '7000');
INSERT INTO `tbl_cptspreaddate` VALUES ('19', '26', '2014-4-1', '1000');
INSERT INTO `tbl_cptspreaddate` VALUES ('20', '26', '2014-4-4', '4400');
INSERT INTO `tbl_cptspreaddate` VALUES ('21', '26', '2014-5-1', '5100');
INSERT INTO `tbl_cptspreaddate` VALUES ('22', '26', '2014-5-8', '5800');
INSERT INTO `tbl_cptspreaddate` VALUES ('23', '27', '2014-5-1', '555');
INSERT INTO `tbl_cptspreaddate` VALUES ('24', '27', '2014-5-8', '88888');
INSERT INTO `tbl_cptspreaddate` VALUES ('25', '27', '2014-5-5', '5.55');
INSERT INTO `tbl_cptspreaddate` VALUES ('26', '27', '2014-5-27', '2700');
INSERT INTO `tbl_cptspreaddate` VALUES ('27', '18', '2014-5-1', '10');
INSERT INTO `tbl_cptspreaddate` VALUES ('29', '18', '2014-5-3', '50');
INSERT INTO `tbl_cptspreaddate` VALUES ('30', '18', '2014-5-5', '500');
INSERT INTO `tbl_cptspreaddate` VALUES ('31', '18', '2014-5-31', '3100');
INSERT INTO `tbl_cptspreaddate` VALUES ('32', '18', '2014-6-1', '1000');
INSERT INTO `tbl_cptspreaddate` VALUES ('33', '18', '2014-6-27', '2700');
INSERT INTO `tbl_cptspreaddate` VALUES ('35', '18', '2014-10-4', '4000');
INSERT INTO `tbl_cptspreaddate` VALUES ('36', '18', '2014-10-1', '1010');
INSERT INTO `tbl_cptspreaddate` VALUES ('37', '18', '2014-9-1', '9100');
INSERT INTO `tbl_cptspreaddate` VALUES ('38', '28', '2014-5-3', '3000');
INSERT INTO `tbl_cptspreaddate` VALUES ('39', '28', '2014-5-1', '1000');
INSERT INTO `tbl_cptspreaddate` VALUES ('40', '29', '2014-7-2', '7200');
INSERT INTO `tbl_cptspreaddate` VALUES ('41', '29', '2014-7-7', '7700');
INSERT INTO `tbl_cptspreaddate` VALUES ('42', '29', '2014-4-6', '4600');
INSERT INTO `tbl_cptspreaddate` VALUES ('43', '29', '2014-5-4', '5400');
INSERT INTO `tbl_cptspreaddate` VALUES ('44', '29', '2014-5-10', '5100');
INSERT INTO `tbl_cptspreaddate` VALUES ('45', '22', '2014-4-5', '4500');
INSERT INTO `tbl_cptspreaddate` VALUES ('46', '22', '2014-4-2', '4200');
INSERT INTO `tbl_cptspreaddate` VALUES ('47', '22', '2014-8-6', '8600');
INSERT INTO `tbl_cptspreaddate` VALUES ('48', '22', '2014-8-8', '8800');
INSERT INTO `tbl_cptspreaddate` VALUES ('49', '30', '2014-5-1', '5100');
INSERT INTO `tbl_cptspreaddate` VALUES ('50', '30', '2014-5-7', '5700');
INSERT INTO `tbl_cptspreaddate` VALUES ('51', '30', '2014-9-9', '9900');
INSERT INTO `tbl_cptspreaddate` VALUES ('52', '31', '2014-8-1', '8100');
INSERT INTO `tbl_cptspreaddate` VALUES ('53', '31', '2014-8-8', '8800');
INSERT INTO `tbl_cptspreaddate` VALUES ('56', '31', '2014-4-2', '4200');
INSERT INTO `tbl_cptspreaddate` VALUES ('57', '31', '2014-4-8', '4800');
INSERT INTO `tbl_cptspreaddate` VALUES ('58', '31', '2014-10-1', '1010');
INSERT INTO `tbl_cptspreaddate` VALUES ('59', '31', '2014-10-8', '1080');
INSERT INTO `tbl_cptspreaddate` VALUES ('60', '31', '2014-5-29', '5290');
INSERT INTO `tbl_cptspreaddate` VALUES ('61', '31', '2014-5-2', '5200');
INSERT INTO `tbl_cptspreaddate` VALUES ('62', '22', '2014-10-24', '2500');
INSERT INTO `tbl_cptspreaddate` VALUES ('63', '22', '2014-10-29', '2500');

-- ----------------------------
-- Table structure for `tbl_dict`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_dict`;
CREATE TABLE `tbl_dict` (
  `dictId` int(11) NOT NULL AUTO_INCREMENT COMMENT '典字Id',
  `dictType` varchar(256) DEFAULT NULL COMMENT '字典类型',
  `keyName` varchar(512) DEFAULT NULL COMMENT '关键字, 用来在代码里引用该关键字',
  `dictName` varchar(256) DEFAULT NULL COMMENT '典字名称',
  `dictValue` varchar(256) DEFAULT NULL COMMENT '字典值',
  `description` varchar(512) DEFAULT NULL COMMENT '注备',
  PRIMARY KEY (`dictId`)
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_dict
-- ----------------------------
INSERT INTO `tbl_dict` VALUES ('1', 'department', null, '商务部', '1', null);
INSERT INTO `tbl_dict` VALUES ('2', 'department', null, '财务部', '2', null);
INSERT INTO `tbl_dict` VALUES ('3', 'department', null, '运营部', '3', null);
INSERT INTO `tbl_dict` VALUES ('4', 'department', null, '测试部', '4', null);
INSERT INTO `tbl_dict` VALUES ('5', 'department', null, '奥软本部', '5', null);
INSERT INTO `tbl_dict` VALUES ('6', 'companycategory', null, '原厂', '1', null);
INSERT INTO `tbl_dict` VALUES ('7', 'companycategory', null, '广告公司', '2', null);
INSERT INTO `tbl_dict` VALUES ('8', 'accounttype', null, '公有', '1', null);
INSERT INTO `tbl_dict` VALUES ('9', 'accounttype', null, '私有', '2', null);
INSERT INTO `tbl_dict` VALUES ('10', 'bank', null, '中国银行', '1', null);
INSERT INTO `tbl_dict` VALUES ('11', 'bank', null, '工商银行', '2', null);
INSERT INTO `tbl_dict` VALUES ('12', 'bank', null, '农业银行', '3', null);
INSERT INTO `tbl_dict` VALUES ('13', 'bank', null, '交通银行', '4', null);
INSERT INTO `tbl_dict` VALUES ('14', 'bank', null, '建设银行', '5', null);
INSERT INTO `tbl_dict` VALUES ('15', 'bank', null, '兴业银行', '6', null);
INSERT INTO `tbl_dict` VALUES ('16', 'bank', null, '民生银行', '7', null);
INSERT INTO `tbl_dict` VALUES ('17', 'bank', null, '浦发银行', '8', null);
INSERT INTO `tbl_dict` VALUES ('18', 'bank', null, '招商银行', '9', null);
INSERT INTO `tbl_dict` VALUES ('19', 'bank', null, '平安银行', '10', null);
INSERT INTO `tbl_dict` VALUES ('20', 'isappmaincategory', null, '是', '1', 'app是否为大类');
INSERT INTO `tbl_dict` VALUES ('21', 'isappmaincategory', null, '否', '2', '');
INSERT INTO `tbl_dict` VALUES ('22', 'appdividedMode', null, 'CPS', '1', 'app分成模式');
INSERT INTO `tbl_dict` VALUES ('23', 'appdividedMode', null, 'CPT', '2', '');
INSERT INTO `tbl_dict` VALUES ('24', 'appdividedMode', null, 'CPA', '3', '');
INSERT INTO `tbl_dict` VALUES ('25', 'appstatus', 'new', '未提交', '1', 'app状态');
INSERT INTO `tbl_dict` VALUES ('26', 'appstatus', 'test-pass', '测试通过', '2', null);
INSERT INTO `tbl_dict` VALUES ('27', 'appstatus', 'test-no-pass', '测试未通过', '3', null);
INSERT INTO `tbl_dict` VALUES ('28', 'appstatus', 'onlined', '已上架', '4', null);
INSERT INTO `tbl_dict` VALUES ('29', 'appstatus', 'offlined', '已下架', '5', null);
INSERT INTO `tbl_dict` VALUES ('30', 'apptestresult', null, '己通过', '1', 'app测试结果');
INSERT INTO `tbl_dict` VALUES ('31', 'apptestresult', null, '未通过', '2', null);
INSERT INTO `tbl_dict` VALUES ('32', 'appstatus', 'wait-test', '待测试审核', '6', null);
INSERT INTO `tbl_dict` VALUES ('33', 'apptestresult', null, '待测试', '3', null);
INSERT INTO `tbl_dict` VALUES ('36', 'appstatus', 'no-online', '不上架', '7', null);
INSERT INTO `tbl_dict` VALUES ('37', 'appstatus', 'online-wait-operation', '待运营审核', '8', '');
INSERT INTO `tbl_dict` VALUES ('38', 'auditresult', 'agree', '通过', '1', null);
INSERT INTO `tbl_dict` VALUES ('39', 'auditresult', 'disagree', '不通过', '2', null);
INSERT INTO `tbl_dict` VALUES ('40', 'appstatus', 'offline-wait-operation', '待运营审核', null, null);
INSERT INTO `tbl_dict` VALUES ('41', 'settlementstatus', '', '商务未提交', null, null);
INSERT INTO `tbl_dict` VALUES ('42', 'settlementstatus', '', '商务己提交', null, null);
INSERT INTO `tbl_dict` VALUES ('43', 'settlementstatus', '', '待运营审核', null, null);
INSERT INTO `tbl_dict` VALUES ('44', 'settlementstatus', '', '运营审核通过', null, null);
INSERT INTO `tbl_dict` VALUES ('45', 'settlementstatus', '', '运营审核不通过', null, null);
INSERT INTO `tbl_dict` VALUES ('46', 'settlementstatus', null, '待运营总监审核', null, null);
INSERT INTO `tbl_dict` VALUES ('47', 'settlementstatus', null, '运营总监审核通过', null, null);
INSERT INTO `tbl_dict` VALUES ('48', 'settlementstatus', null, '运营总监审核不通过', null, null);
INSERT INTO `tbl_dict` VALUES ('49', 'settlementstatus', null, '待财务审核开票', null, null);
INSERT INTO `tbl_dict` VALUES ('50', 'settlementstatus', null, '财务审核开票通过', null, null);
INSERT INTO `tbl_dict` VALUES ('51', 'settlementstatus', null, '财务审核开票不通过', null, null);
INSERT INTO `tbl_dict` VALUES ('52', 'settlementstatus', null, '待财务回款确认', null, null);
INSERT INTO `tbl_dict` VALUES ('53', 'settlementstatus', null, '财务回款确认通过', null, null);
INSERT INTO `tbl_dict` VALUES ('54', 'settlementstatus', null, '财务回款确认不通过', null, null);
INSERT INTO `tbl_dict` VALUES ('55', 'invoicecategory', null, '地税发票', '1', null);
INSERT INTO `tbl_dict` VALUES ('56', 'invoicecategory', null, '国税普通发票', '2', null);
INSERT INTO `tbl_dict` VALUES ('57', 'invoicecategory', null, '国税增值税专用发票(代开)', '3', null);

-- ----------------------------
-- Table structure for `tbl_invoice`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_invoice`;
CREATE TABLE `tbl_invoice` (
  `invoiceId` int(11) NOT NULL DEFAULT '0',
  `type` int(11) DEFAULT NULL,
  `invoiceCode` varchar(128) DEFAULT NULL COMMENT '发票编号',
  `desc` varchar(512) DEFAULT NULL,
  PRIMARY KEY (`invoiceId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_invoice
-- ----------------------------

-- ----------------------------
-- Table structure for `tbl_permission`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_permission`;
CREATE TABLE `tbl_permission` (
  `permissionId` int(11) NOT NULL AUTO_INCREMENT,
  `permissionName` varchar(256) DEFAULT NULL COMMENT '限权名',
  `url` text COMMENT '请求的url',
  `description` varchar(256) DEFAULT NULL COMMENT '注备',
  PRIMARY KEY (`permissionId`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_permission
-- ----------------------------
INSERT INTO `tbl_permission` VALUES ('1', '查询用户', 'user/getUser', null);
INSERT INTO `tbl_permission` VALUES ('2', '删除用户', 'user/delete', null);
INSERT INTO `tbl_permission` VALUES ('3', '修改用户', 'user/update', null);
INSERT INTO `tbl_permission` VALUES ('4', '新增用户', 'user/create', null);
INSERT INTO `tbl_permission` VALUES ('5', '新增推广合作商', 'partner/create', null);
INSERT INTO `tbl_permission` VALUES ('6', '修改推广合作商', 'partner/update', null);
INSERT INTO `tbl_permission` VALUES ('7', '查询推广合作商', 'partner/getAllPartners', null);
INSERT INTO `tbl_permission` VALUES ('8', '新增合同', 'contract/saveOrUpdate', null);
INSERT INTO `tbl_permission` VALUES ('9', '修改合同', 'contract/saveOrUpdate', null);
INSERT INTO `tbl_permission` VALUES ('10', '查询合同', 'contract/getAllContract', null);
INSERT INTO `tbl_permission` VALUES ('11', '新增CP上架申请', 'appupload/addAppUploadApply', null);
INSERT INTO `tbl_permission` VALUES ('12', '查询所有CP', 'appupload/getApps', null);
INSERT INTO `tbl_permission` VALUES ('13', '修改CP上架申请', 'appupload/updateUploadApply', null);
INSERT INTO `tbl_permission` VALUES ('14', 'CP上架商务审核', 'appupload/auditUploadApplyForBusiness', null);
INSERT INTO `tbl_permission` VALUES ('15', 'CP上架测试审核', 'appupload/auditUploadApplyForTest', null);
INSERT INTO `tbl_permission` VALUES ('16', 'CP上架运营审核', 'appupload/auditUploadApplyForOperation', null);
INSERT INTO `tbl_permission` VALUES ('17', 'CP下架商务审核', 'appupload/auditAppOfflineForBusiness', null);
INSERT INTO `tbl_permission` VALUES ('18', 'CP下架运营审核', 'appupload/auditAppOfflineForOperation', null);

-- ----------------------------
-- Table structure for `tbl_role`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_role`;
CREATE TABLE `tbl_role` (
  `roleId` int(11) NOT NULL AUTO_INCREMENT,
  `roleName` varchar(128) DEFAULT NULL,
  `permissionId` varchar(512) DEFAULT NULL,
  `description` varchar(512) DEFAULT NULL,
  PRIMARY KEY (`roleId`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_role
-- ----------------------------
INSERT INTO `tbl_role` VALUES ('1', '商务', '1,5,6,7,8,9,10,11,12,13,14,17', null);
INSERT INTO `tbl_role` VALUES ('2', '财务', null, null);
INSERT INTO `tbl_role` VALUES ('3', '运营', '7,10,12,16,18', null);
INSERT INTO `tbl_role` VALUES ('4', '测试', '7,10,12,15', null);

-- ----------------------------
-- Table structure for `tbl_user`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_user`;
CREATE TABLE `tbl_user` (
  `userId` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(256) DEFAULT NULL COMMENT '户用姓名',
  `userName` varchar(64) DEFAULT NULL COMMENT '姓名',
  `password` varchar(256) DEFAULT NULL COMMENT '码密',
  `deptId` int(11) DEFAULT NULL COMMENT '门部Id',
  `telephone` varchar(64) DEFAULT NULL COMMENT '电话',
  `mobilephone` varchar(64) DEFAULT NULL COMMENT '手机号码',
  `email` varchar(64) DEFAULT NULL COMMENT '电子邮件',
  `roleId` int(11) DEFAULT NULL COMMENT '色角Id',
  `description` varchar(512) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_user
-- ----------------------------
INSERT INTO `tbl_user` VALUES ('1', '张三', 'zhangsan', 'zhangsan', '3', '1234', '5678', '123@qq.com', '1', 'asdfasdf');
INSERT INTO `tbl_user` VALUES ('2', '李四', 'lisi', 'lisi', '5', '0-1-2-3', '1367895147', 'lisi@qq.com', '4', 'xxxxxxxxx');
INSERT INTO `tbl_user` VALUES ('3', 'asdf', 'asdf', 'asdf', '2', 'asdf', 'asdf', 'asdf', '2', null);
INSERT INTO `tbl_user` VALUES ('4', 'cc', 'cc', 'cc', '2', 'asdf', 'asdf', 'asdf', '3', null);
INSERT INTO `tbl_user` VALUES ('5', '工业区', 'gyq', '123', '2', 'qwr', 'qwer', 'erq', '3', null);
INSERT INTO `tbl_user` VALUES ('7', '空白', 'kb', 'kb', '3', '1234', '1234', '1234', '3', null);
INSERT INTO `tbl_user` VALUES ('8', '格子', 'gz', 'gz', '5', 'asdf', 'asdf', 'asdf', '1', null);
INSERT INTO `tbl_user` VALUES ('9', 'qq', 'qq', 'qq1', '2', 'aas', 'aas', 'aas', '2', null);
INSERT INTO `tbl_user` VALUES ('10', 'baidu', 'baidu', 'baidu', '4', '百度', '百度', '百度', '3', null);
INSERT INTO `tbl_user` VALUES ('11', 'admin', 'admin', '123', '1', 'asdf', 'asdf', 'asdf', '1', '');
