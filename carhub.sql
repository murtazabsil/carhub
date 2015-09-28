/*
SQLyog Trial v8.71 
MySQL - 5.6.24 : Database - carhub
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`carhub` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `carhub`;

/*Table structure for table `customer` */

DROP TABLE IF EXISTS `customer`;

CREATE TABLE `customer` (
  `customer_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `customer_name` varchar(100) DEFAULT NULL,
  `address` text,
  `email_id` varchar(100) DEFAULT NULL,
  `contact` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`customer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `customer` */

/*Table structure for table `item_data` */

DROP TABLE IF EXISTS `item_data`;

CREATE TABLE `item_data` (
  `item_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `item_name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `item_data` */

/*Table structure for table `job` */

DROP TABLE IF EXISTS `job`;

CREATE TABLE `job` (
  `job_id` bigint(30) NOT NULL AUTO_INCREMENT,
  `customer_id` bigint(20) NOT NULL,
  `job_type_id` bigint(20) NOT NULL,
  `vehicle_id` bigint(30) DEFAULT NULL,
  `job_date` datetime DEFAULT NULL,
  PRIMARY KEY (`job_id`),
  KEY `FK_job` (`job_type_id`),
  KEY `FK_job_customer` (`customer_id`),
  KEY `FK_job_vehicle` (`vehicle_id`),
  CONSTRAINT `FK_job` FOREIGN KEY (`job_type_id`) REFERENCES `job_type` (`job_type_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_job_customer` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_job_vehicle` FOREIGN KEY (`vehicle_id`) REFERENCES `vehicle` (`vehicle_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `job` */

/*Table structure for table `job_type` */

DROP TABLE IF EXISTS `job_type`;

CREATE TABLE `job_type` (
  `job_type_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `job_type_name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`job_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `job_type` */

/*Table structure for table `particular` */

DROP TABLE IF EXISTS `particular`;

CREATE TABLE `particular` (
  `particular_id` bigint(30) NOT NULL AUTO_INCREMENT,
  `job_id` bigint(20) DEFAULT NULL,
  `item_id` bigint(20) DEFAULT NULL,
  `part_price` double DEFAULT NULL,
  `labour_price` double DEFAULT NULL,
  `total_price` double DEFAULT NULL,
  PRIMARY KEY (`particular_id`),
  KEY `FK_particular` (`item_id`),
  KEY `FK_particular_job` (`job_id`),
  CONSTRAINT `FK_particular` FOREIGN KEY (`item_id`) REFERENCES `item_data` (`item_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_particular_job` FOREIGN KEY (`job_id`) REFERENCES `job` (`job_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `particular` */

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `user_id` bigint(30) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(100) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `isadmin` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `user` */

/*Table structure for table `vehicle` */

DROP TABLE IF EXISTS `vehicle`;

CREATE TABLE `vehicle` (
  `vehicle_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `customer_id` bigint(30) DEFAULT NULL,
  `vehicle_number` varchar(50) DEFAULT NULL,
  `model` varchar(100) DEFAULT NULL,
  `sold_date` datetime DEFAULT NULL,
  `kilometre` double DEFAULT NULL,
  PRIMARY KEY (`vehicle_id`),
  KEY `FK_vehicle_customer` (`customer_id`),
  CONSTRAINT `FK_vehicle_customer` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `vehicle` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;


/* Adding Seed Data */
INSERT INTO job_type VALUES(1, 'Prepaid Service');
INSERT INTO job_type VALUES(2, 'Repair Service');