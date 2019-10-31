/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50713
Source Host           : localhost:3306
Source Database       : practicedb

Target Server Type    : MYSQL
Target Server Version : 50713
File Encoding         : 65001

Date: 2019-10-31 09:56:59
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `admin_id` varchar(11) NOT NULL,
  `name` varchar(10) NOT NULL,
  `email` varchar(20) NOT NULL,
  `password` varchar(100) NOT NULL,
  `iphone` varchar(11) NOT NULL,
  `gender` varchar(1) NOT NULL,
  `imgHead` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`admin_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('20170313007', '莫槟铭', '1915809094@qq.com', '>h7MBAKUT*[#MO/`', '17777581901', '1', 'abc.png');

-- ----------------------------
-- Table structure for course
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `teacher_id` varchar(11) NOT NULL,
  `name` varchar(40) NOT NULL,
  `intro` varchar(200) DEFAULT NULL,
  `add_time` datetime(6) NOT NULL,
  `age` int(4) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `course_teacher_id` (`teacher_id`),
  CONSTRAINT `course_teacher_id` FOREIGN KEY (`teacher_id`) REFERENCES `teachers` (`teacher_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of course
-- ----------------------------
INSERT INTO `course` VALUES ('30', '8888', 'Android应用基础', 'Android应用开发基础课程', '2019-10-31 23:40:27.000000', '2017');
INSERT INTO `course` VALUES ('31', '8888', '算法分析与设计', '影响排序性能，最佳策略属于复杂的数学问题', '2019-03-07 23:40:40.000000', '2018');
INSERT INTO `course` VALUES ('32', '8888', '移动应用项目开发', 'Android进阶课程', '2019-03-14 23:40:51.000000', '2019');
INSERT INTO `course` VALUES ('33', '8888', 'iOS应用开发（Swift）', '使用Swift语言进行iOS开发', '2019-10-17 01:19:21.000000', '2019');

-- ----------------------------
-- Table structure for enrollments
-- ----------------------------
DROP TABLE IF EXISTS `enrollments`;
CREATE TABLE `enrollments` (
  `id` int(5) NOT NULL AUTO_INCREMENT,
  `course_id` int(10) NOT NULL,
  `student_id` varchar(11) NOT NULL,
  `take_effect` int(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `course_id` (`course_id`) USING BTREE,
  KEY `enrollments_student_id` (`student_id`),
  CONSTRAINT `enrollments_course_id` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `enrollments_student_id` FOREIGN KEY (`student_id`) REFERENCES `students` (`student_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=93 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of enrollments
-- ----------------------------
INSERT INTO `enrollments` VALUES ('23', '31', '20170313007', '1');
INSERT INTO `enrollments` VALUES ('24', '32', '20170313007', '1');
INSERT INTO `enrollments` VALUES ('78', '30', '114', '0');
INSERT INTO `enrollments` VALUES ('84', '30', 'Name22', '1');
INSERT INTO `enrollments` VALUES ('90', '30', '20170313008', '1');
INSERT INTO `enrollments` VALUES ('91', '30', '20170313007', '1');
INSERT INTO `enrollments` VALUES ('92', '33', '20170313007', '0');

-- ----------------------------
-- Table structure for messages
-- ----------------------------
DROP TABLE IF EXISTS `messages`;
CREATE TABLE `messages` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `teacher_id` varchar(255) DEFAULT NULL,
  `student_id` varchar(255) DEFAULT NULL,
  `datetime` datetime DEFAULT NULL,
  `message` text,
  `fromto` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of messages
-- ----------------------------

-- ----------------------------
-- Table structure for options
-- ----------------------------
DROP TABLE IF EXISTS `options`;
CREATE TABLE `options` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` varchar(255) NOT NULL,
  `label` varchar(255) NOT NULL,
  `question_id` int(11) NOT NULL,
  `is_answer` int(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `question_id_options` (`question_id`),
  CONSTRAINT `question_id_options` FOREIGN KEY (`question_id`) REFERENCES `questions` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of options
-- ----------------------------

-- ----------------------------
-- Table structure for practices
-- ----------------------------
DROP TABLE IF EXISTS `practices`;
CREATE TABLE `practices` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(10) NOT NULL,
  `question_count` int(5) NOT NULL DEFAULT '0',
  `up_time` datetime NOT NULL,
  `is_ready` int(8) NOT NULL DEFAULT '0',
  `outlines` text NOT NULL,
  `course_id` int(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `teacher_course_id` (`course_id`),
  CONSTRAINT `teacher_course_id` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of practices
-- ----------------------------
INSERT INTO `practices` VALUES ('1', '第1章 语言、XML', '0', '2019-06-29 10:57:12', '1', '563', '30');
INSERT INTO `practices` VALUES ('2', '第2章 XML、布局', '0', '2019-09-05 21:10:10', '1', '123', '30');
INSERT INTO `practices` VALUES ('3', '第3章 列表及数据存', '0', '2019-09-05 21:11:11', '1', '123', '30');
INSERT INTO `practices` VALUES ('4', '第4章 考核及扩展', '0', '2019-09-06 15:34:37', '1', '123', '30');

-- ----------------------------
-- Table structure for practice_results
-- ----------------------------
DROP TABLE IF EXISTS `practice_results`;
CREATE TABLE `practice_results` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `practice_id` int(11) NOT NULL,
  `student_id` varchar(11) NOT NULL,
  `question_id` int(11) NOT NULL,
  `wrong_tpye` int(11) NOT NULL,
  `student_answer` varchar(200) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `practice_results_p_id` (`practice_id`),
  KEY `practice_results_student_id` (`student_id`),
  KEY `practice_results_q_id` (`question_id`),
  CONSTRAINT `practice_results_p_id` FOREIGN KEY (`practice_id`) REFERENCES `practices` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `practice_results_q_id` FOREIGN KEY (`question_id`) REFERENCES `questions` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `practice_results_student_id` FOREIGN KEY (`student_id`) REFERENCES `students` (`student_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of practice_results
-- ----------------------------

-- ----------------------------
-- Table structure for questions
-- ----------------------------
DROP TABLE IF EXISTS `questions`;
CREATE TABLE `questions` (
  `id` int(11) NOT NULL,
  `content` varchar(255) NOT NULL,
  `question_type` int(2) NOT NULL,
  `number` int(11) NOT NULL,
  `analysis` text NOT NULL,
  `practice_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `practice_id_questions` (`practice_id`),
  CONSTRAINT `practice_id_questions` FOREIGN KEY (`practice_id`) REFERENCES `practices` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of questions
-- ----------------------------

-- ----------------------------
-- Table structure for students
-- ----------------------------
DROP TABLE IF EXISTS `students`;
CREATE TABLE `students` (
  `student_id` varchar(11) NOT NULL,
  `name` varchar(10) NOT NULL,
  `email` varchar(20) NOT NULL,
  `password` varchar(100) NOT NULL,
  `iphone` varchar(11) NOT NULL,
  `gender` varchar(1) NOT NULL,
  `imgHead` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`student_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of students
-- ----------------------------
INSERT INTO `students` VALUES ('114', '411', '9332134524@qq.com', '*u?f>f?s?a?/?.1/', '18775533814', '男', 'abc.png');
INSERT INTO `students` VALUES ('20170313007', '莫槟铭', '1915809094@qq.com', '>h7MBAKUT*[#MO/`', '17777581901', '男', 'abc.png');
INSERT INTO `students` VALUES ('20170313008', 'Name2', '191@hotmail.com', '?g--2V<JY%LPR)[#', '17777581908', '男', 'abc.png');
INSERT INTO `students` VALUES ('Name22', 'Name', '567@hotmail.com', '/pO{BA%3[#9>>G3C', '17777581909', '男', 'abc.png');

-- ----------------------------
-- Table structure for teachers
-- ----------------------------
DROP TABLE IF EXISTS `teachers`;
CREATE TABLE `teachers` (
  `teacher_id` varchar(11) NOT NULL,
  `name` varchar(10) NOT NULL,
  `email` varchar(20) NOT NULL,
  `password` varchar(32) NOT NULL,
  `iphone` varchar(11) NOT NULL,
  `gender` varchar(1) NOT NULL,
  `valid` int(11) NOT NULL DEFAULT '0',
  `imgHead` varchar(60) NOT NULL,
  PRIMARY KEY (`teacher_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of teachers
-- ----------------------------
INSERT INTO `teachers` VALUES ('345454', '嘻嘻嘻', '896262@qq.com', '?g@D8?:L)7U(Y%)7', '17777581901', '男', '0', '17777581901.png');
INSERT INTO `teachers` VALUES ('646757', '所以意思就是', '8162728@qq.com', '?g=KAF;:[#%32VY%', '17777581902', '男', '1', '17777581902.png');
INSERT INTO `teachers` VALUES ('8888', '葛祥友', 't@qq.com', '9.HY[#[#MOAF%3S_', '13877209240', '男', '1', '13877209240.png');

-- ----------------------------
-- View structure for abc
-- ----------------------------
DROP VIEW IF EXISTS `abc`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `abc` AS select `course`.`name` AS `name`,`enrollments`.`student_id` AS `student_id` from (`course` join `enrollments`) where (`enrollments`.`course_id` = `course`.`id`) ;
