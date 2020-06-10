/*
Navicat MySQL Data Transfer

Source Server         : wow
Source Server Version : 50726
Source Host           : localhost:3306
Source Database       : medical

Target Server Type    : MYSQL
Target Server Version : 50726
File Encoding         : 65001

Date: 2020-06-10 15:10:02
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_authorize
-- ----------------------------
DROP TABLE IF EXISTS `t_authorize`;
CREATE TABLE `t_authorize` (
  `apply_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '授权人',
  `to_user_id` int(11) NOT NULL,
  `apply_state` tinyint(1) DEFAULT NULL COMMENT '0-未授权，1-已授权，2-已拒绝',
  `apply_date` datetime DEFAULT NULL COMMENT '授权时间',
  `description` text COMMENT '授权信息，指代病历数据',
  `sign_text` varchar(255) DEFAULT NULL COMMENT '签名信息',
  PRIMARY KEY (`apply_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_authorize
-- ----------------------------
INSERT INTO `t_authorize` VALUES ('5', '1', '3', '1', '2020-06-07 02:47:33', '{\"createTime\":\"2020/06/07\",\"description\":\"RUrITYbNDoN5JSAYt/6u7wcWRPAmIL3lIiLBOfKDo6+NVrMY3VfkJbhKgz5sVlLstLurT42m8HwdP+IetynMBCSN419pZuIKi4WYnSkot5NNhXxg5hujUMPm28fvvkI6szAJ+QWTQGCPOfWzQQBEJ5/X5n4d4m9yC373OiCEebM=\",\"doctorName\":\"doctorName1\",\"medicalPicture\":\"1591468204842test.JPG\",\"patientName\":\"patientName1\"}', null);

-- ----------------------------
-- Table structure for t_doctor
-- ----------------------------
DROP TABLE IF EXISTS `t_doctor`;
CREATE TABLE `t_doctor` (
  `doctor_id` int(11) NOT NULL,
  `doctor_name` varchar(16) NOT NULL,
  `doctor_phone` varchar(11) NOT NULL COMMENT '手机号唯一',
  `doctor_password` varchar(32) NOT NULL,
  `doctor_picture` varchar(32) DEFAULT NULL,
  `doctor_hospital` int(10) unsigned NOT NULL COMMENT '所属医疗机构id',
  `doctor_state` tinyint(1) DEFAULT NULL COMMENT '0-正常，1-非法',
  PRIMARY KEY (`doctor_id`),
  UNIQUE KEY `doctor_phone` (`doctor_phone`) USING BTREE,
  KEY `doctor_hospital` (`doctor_hospital`),
  CONSTRAINT `t_doctor_ibfk_1` FOREIGN KEY (`doctor_hospital`) REFERENCES `t_hospital` (`hospital_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_doctor
-- ----------------------------
INSERT INTO `t_doctor` VALUES ('3', 'name1', 'phone1', '7C6A180B36896A0A8C02787EEAFB0E4C', null, '1', '0');

-- ----------------------------
-- Table structure for t_hospital
-- ----------------------------
DROP TABLE IF EXISTS `t_hospital`;
CREATE TABLE `t_hospital` (
  `hospital_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `hospital_name` varchar(32) NOT NULL COMMENT '医院全称',
  `hospital_state` tinyint(1) NOT NULL COMMENT '医院状态，0-正常，1-待审核，2-非法',
  `business_license` varchar(32) NOT NULL COMMENT '营业执照图片地址',
  `in_date` date NOT NULL COMMENT '加入时间',
  PRIMARY KEY (`hospital_id`),
  UNIQUE KEY `hospital_name` (`hospital_name`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_hospital
-- ----------------------------
INSERT INTO `t_hospital` VALUES ('1', 'hospital1', '0', 'business_license1', '2020-06-05');

-- ----------------------------
-- Table structure for t_medical
-- ----------------------------
DROP TABLE IF EXISTS `t_medical`;
CREATE TABLE `t_medical` (
  `patient_name` varchar(255) NOT NULL,
  `doctor_name` varchar(255) NOT NULL,
  `create_time` varchar(255) NOT NULL,
  `medical_picture` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_medical
-- ----------------------------

-- ----------------------------
-- Table structure for t_patient
-- ----------------------------
DROP TABLE IF EXISTS `t_patient`;
CREATE TABLE `t_patient` (
  `patient_id` int(10) unsigned NOT NULL,
  `patient_name` varchar(16) NOT NULL,
  `patient_phone` varchar(11) NOT NULL COMMENT '手机号唯一',
  `patient_password` varchar(64) NOT NULL COMMENT '经过 MD5 哈希运算',
  `patient_picture` varchar(32) DEFAULT NULL,
  `patient_state` tinyint(1) DEFAULT NULL COMMENT '0-正常，1-非法',
  PRIMARY KEY (`patient_id`),
  UNIQUE KEY `patient_phone` (`patient_phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_patient
-- ----------------------------
INSERT INTO `t_patient` VALUES ('1', 'name1', 'phone1', '7C6A180B36896A0A8C02787EEAFB0E4C', null, '0');
INSERT INTO `t_patient` VALUES ('4', 'name2', 'phone2', '6CB75F652A9B52798EB6CF2201057C73', null, '0');
INSERT INTO `t_patient` VALUES ('5', 'name3', 'phone3', '819B0643D6B89DC9B579FDFC9094F28E', null, '0');

-- ----------------------------
-- Table structure for t_publickey
-- ----------------------------
DROP TABLE IF EXISTS `t_publickey`;
CREATE TABLE `t_publickey` (
  `user_id` int(11) NOT NULL,
  `user_publickey` varchar(255) NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_publickey
-- ----------------------------
INSERT INTO `t_publickey` VALUES ('1', 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCKA933cmIjTjfGIYqWbyDxhdtCaFdN4JW49akXZdC6wnQYnyEWGNoyG3L+GnqJVrAtrA4kwbdb6omWQ5Dbo3O9bWpodm+aLzeOrqdxTPefQJPZMhUoOgAqGAGAKco5eViR3BLVuITygp/z19gIx1V9b55F4TjlRJL5E4GAE0qhhwIDAQAB');
INSERT INTO `t_publickey` VALUES ('3', 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCQ+NQa1mG+3Jqq2TZxbdSbwIeZPNECznNOjRiFpnAGQkXrthzSUR6Yym8W1Bmd0kP5+P3BjeNaDeYDjZMv6OVD5ptr/YESAn2hzcWbF2XteBy0ZpZkEQT0UmkBQNth/S6d3BrgSq5Ulpjm3Hg7NOAnspnh4WuqstQhO4EIMe6FvwIDAQAB');
INSERT INTO `t_publickey` VALUES ('4', 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCIPguAKrqOcHfgonSGsCbzA1t7rMlJ9BBqh4mI1y5URSPRoIbXodX7Nwr9ZvfKG/nOKzucaxdqI/DSidQbLbqas+Zi5KTVLhUNGJ0GVkmOykT4JS/V5LDFYr+eQ8js5fR9z4NfctCjWyj1er+SyNIgTyfrAAh7MTEX44surRY5pQIDAQAB');
INSERT INTO `t_publickey` VALUES ('5', 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC6nNdnusgj+6d3MlaYVYynL/rgwsC3pqOwoZjkXfWVz60O1Mf90s8K228LUzRx/wgsul/nWoIDV4z1LTRw/Dcob/bd3p4GdqvHdX+Kv3lT3fV3+lFu0QyvK74H/xZesAxrqBgXPaXpiIJfyXhs+SSo7uCapaOxrj9H9qjPw2Nw3wIDAQAB');

-- ----------------------------
-- Table structure for t_usercount
-- ----------------------------
DROP TABLE IF EXISTS `t_usercount`;
CREATE TABLE `t_usercount` (
  `user_count` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_usercount
-- ----------------------------
INSERT INTO `t_usercount` VALUES ('6');
