/*
SQLyog Ultimate v13.1.1 (64 bit)
MySQL - 10.4.19-MariaDB-log : Database - atm_driver
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`atm_driver` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `atm_driver`;

/*Table structure for table `atmconfigs` */

DROP TABLE IF EXISTS `atmconfigs`;

CREATE TABLE `atmconfigs` (
  `atmcnf_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `atmcnf_config_id` varchar(255) NOT NULL,
  `atmcnf_protocol` varchar(255) NOT NULL,
  `atmcnf_desc` varchar(255) DEFAULT NULL,
  `atmcnf_language639` varchar(255) NOT NULL,
  `atmcnf_languageatm` char(1) DEFAULT NULL,
  `atmcnf_languageindex` int(11) DEFAULT NULL,
  `atmcnf_screengroupbase` int(11) NOT NULL,
  PRIMARY KEY (`atmcnf_id`),
  UNIQUE KEY `atmcnf_uk` (`atmcnf_config_id`,`atmcnf_screengroupbase`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4;

/*Data for the table `atmconfigs` */

insert  into `atmconfigs`(`atmcnf_id`,`atmcnf_config_id`,`atmcnf_protocol`,`atmcnf_desc`,`atmcnf_language639`,`atmcnf_languageatm`,`atmcnf_languageindex`,`atmcnf_screengroupbase`) values 
(1,'0850','NDC','NDC Configuration with EMV English','eng','A',0,0),
(2,'0870','NDC','NDC Configuration without EMV English','eng','A',0,0),
(3,'0870','NDC','NDC Configuration without EMV Indonesian','ind','B',0,400);

/*Table structure for table `atmlog` */

DROP TABLE IF EXISTS `atmlog`;

CREATE TABLE `atmlog` (
  `atmlog_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `atmlog_luno` varchar(9) NOT NULL,
  `atmlog_message_class` varchar(2) NOT NULL,
  `atmlog_ip` varchar(20) NOT NULL,
  `atmlog_card` varchar(20) DEFAULT NULL,
  `atmlog_op_code` varchar(20) DEFAULT NULL,
  `atmlog_op_desc` varchar(255) DEFAULT NULL,
  `atmlog_currency_code` varchar(10) DEFAULT NULL,
  `atmlog_amount` decimal(7,2) DEFAULT NULL,
  `atmlog_timezone` varchar(20) DEFAULT NULL,
  `atmlog_atm_request_dt` datetime(6) NOT NULL,
  `atmlog_atm_request` longtext NOT NULL,
  `atmlog_iso_request_dt` datetime(6) DEFAULT NULL,
  `atmlog_iso_request` longtext DEFAULT NULL,
  `atmlog_iso_reply_dt` datetime(6) DEFAULT NULL,
  `atmlog_iso_reply` longtext DEFAULT NULL,
  `atmlog_atm_reply_dt` datetime(6) DEFAULT NULL,
  `atmlog_atm_reply` longtext DEFAULT NULL,
  `atmlog_atm_confirmation_dt` datetime(6) DEFAULT NULL,
  `atmlog_atm_confirmation` longtext DEFAULT NULL,
  `atmlog_iso_confirmation_request_dt` datetime(6) DEFAULT NULL,
  `atmlog_iso_confirmation_request` longtext DEFAULT NULL,
  `atmlog_iso_confirmation_reply_dt` datetime(6) DEFAULT NULL,
  `atmlog_iso_confirmation_reply` longtext DEFAULT NULL,
  `atmlog_created_ts` timestamp NOT NULL DEFAULT current_timestamp(),
  `atmlog_updated_ts` timestamp NULL DEFAULT NULL ON UPDATE current_timestamp(),
  `aud_usr_id` int(11) DEFAULT NULL,
  `aud_created_log_entry_id` int(11) DEFAULT NULL,
  `aud_last_update_log_entry_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`atmlog_id`),
  UNIQUE KEY `atmlog_uk` (`atmlog_ip`,`atmlog_message_class`,`atmlog_atm_request_dt`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `atmlog` */

/*Table structure for table `atmprotocols` */

DROP TABLE IF EXISTS `atmprotocols`;

CREATE TABLE `atmprotocols` (
  `atmprt_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `atmprt_name` varchar(255) DEFAULT NULL,
  `atmprt_desc` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`atmprt_id`),
  UNIQUE KEY `atmprt_uk` (`atmprt_name`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

/*Data for the table `atmprotocols` */

insert  into `atmprotocols`(`atmprt_id`,`atmprt_name`,`atmprt_desc`) values 
(1,'NDC','NCR Direct Connection');

/*Table structure for table `atms` */

DROP TABLE IF EXISTS `atms`;

CREATE TABLE `atms` (
  `atm_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `atm_last_trn_log_id` bigint(20) DEFAULT NULL,
  `atm_ip` varchar(40) NOT NULL,
  `atm_luno` varchar(9) NOT NULL,
  `atm_name` varchar(255) DEFAULT NULL,
  `atm_protocol` varchar(10) NOT NULL,
  `atm_aceptor_id` varchar(15) DEFAULT NULL,
  `atm_status` varchar(20) DEFAULT NULL,
  `atm_active` tinyint(4) DEFAULT NULL,
  `atm_group` varchar(255) DEFAULT NULL,
  `atm_region` varchar(255) DEFAULT NULL,
  `atm_brand` varchar(20) DEFAULT NULL,
  `atm_model` varchar(255) DEFAULT NULL,
  `atm_address1` varchar(255) DEFAULT NULL,
  `atm_address2` varchar(255) DEFAULT NULL,
  `atm_city` varchar(255) DEFAULT NULL,
  `atm_state` varchar(255) DEFAULT NULL,
  `atm_province` varchar(255) DEFAULT NULL,
  `atm_country` varchar(3) DEFAULT NULL,
  `atm_zip` varchar(15) DEFAULT NULL,
  `atm_contact` varchar(255) DEFAULT NULL,
  `atm_phone` varchar(20) DEFAULT NULL,
  `atm_master_key` varchar(64) DEFAULT NULL,
  `atm_communications_key` varchar(64) DEFAULT NULL,
  `atm_mac_key` varchar(64) DEFAULT NULL,
  `atm_config_id` varchar(4) DEFAULT NULL,
  `atm_institution_code` varchar(11) DEFAULT NULL,
  `atm_merch_type` varchar(4) DEFAULT NULL,
  `atm_network_data` varchar(255) DEFAULT NULL,
  `atm_point_serv_data` varchar(255) DEFAULT NULL,
  `atm_pos_entry_mode` varchar(3) DEFAULT NULL,
  `atm_terminal_id` varchar(8) DEFAULT NULL,
  `atm_trn_ser_num` smallint(6) DEFAULT NULL,
  `atm_timezone` varchar(20) NOT NULL,
  PRIMARY KEY (`atm_id`),
  UNIQUE KEY `atm_uk` (`atm_ip`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

/*Data for the table `atms` */

insert  into `atms`(`atm_id`,`atm_last_trn_log_id`,`atm_ip`,`atm_luno`,`atm_name`,`atm_protocol`,`atm_aceptor_id`,`atm_status`,`atm_active`,`atm_group`,`atm_region`,`atm_brand`,`atm_model`,`atm_address1`,`atm_address2`,`atm_city`,`atm_state`,`atm_province`,`atm_country`,`atm_zip`,`atm_contact`,`atm_phone`,`atm_master_key`,`atm_communications_key`,`atm_mac_key`,`atm_config_id`,`atm_institution_code`,`atm_merch_type`,`atm_network_data`,`atm_point_serv_data`,`atm_pos_entry_mode`,`atm_terminal_id`,`atm_trn_ser_num`,`atm_timezone`) values 
(1,60,'127.0.0.1','001','R200','NDC','1234567',NULL,1,NULL,NULL,'NCR','SELFSERV 22','SUCURSAL CENTRAL',NULL,'CARACAS','DC',NULL,'VE','1060','Pedro Perez','+58 (414) 320-6238','0A0F0A0F0A0F0A0F0A0F0A0F0A0F0A0F','0A0F0A0F0A0F0A0F0A0F0A0F0A0F0A0F','0A0F0A0F0A0F0A0F0A0F0A0F0A0F0A0F','0870','1111','6011','CI2000000000','91000000025008620000000000','051','29110001',0,'America/Caracas'),
(2,NULL,'192.168.1.60','001','R200','NDC','1234567',NULL,1,NULL,NULL,'NCR','SELFSERV 22','SUCURSAL CENTRAL',NULL,'CARACAS','DC',NULL,'VE','1060','Pedro Perez','+58 (414) 320-6238','0A0F0A0F0A0F0A0F0A0F0A0F0A0F0A0F','0A0F0A0F0A0F0A0F0A0F0A0F0A0F0A0F','0A0F0A0F0A0F0A0F0A0F0A0F0A0F0A0F','0870','1111','6011','CI2000000000','91000000025008620000000000','051','29110001',0,'America/Caracas');

/*Table structure for table `cassettes` */

DROP TABLE IF EXISTS `cassettes`;

CREATE TABLE `cassettes` (
  `cass_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cass_luno` varchar(9) NOT NULL,
  `cass_instaled` bit(1) NOT NULL,
  `cass_position` int(11) NOT NULL,
  `cass_curr_type` varchar(2) NOT NULL,
  `cass_curr_number` varchar(3) NOT NULL,
  `cass_denomination` int(11) NOT NULL,
  `cass_begin` int(11) NOT NULL,
  `cass_dispensed` int(11) NOT NULL,
  `cass_rejected` int(11) NOT NULL,
  PRIMARY KEY (`cass_id`),
  UNIQUE KEY `cass_uk` (`cass_luno`,`cass_instaled`,`cass_position`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;

/*Data for the table `cassettes` */

insert  into `cassettes`(`cass_id`,`cass_luno`,`cass_instaled`,`cass_position`,`cass_curr_type`,`cass_curr_number`,`cass_denomination`,`cass_begin`,`cass_dispensed`,`cass_rejected`) values 
(1,'001','',1,'01','840',10,11,0,0),
(2,'001','',2,'01','840',20,22,0,0),
(3,'001','',3,'01','840',50,33,0,0),
(4,'001','',4,'01','840',100,44,0,0);

/*Table structure for table `consumer` */

DROP TABLE IF EXISTS `consumer`;

CREATE TABLE `consumer` (
  `id` varchar(255) NOT NULL,
  `hash` varchar(8000) DEFAULT NULL,
  `active` char(1) DEFAULT NULL,
  `deleted` char(1) DEFAULT NULL,
  `startDate` date DEFAULT NULL,
  `endDate` date DEFAULT NULL,
  `eeuser` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKConsumerUser` (`eeuser`),
  CONSTRAINT `FKConsumerUser` FOREIGN KEY (`eeuser`) REFERENCES `eeuser` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `consumer` */

/*Table structure for table `consumer_props` */

DROP TABLE IF EXISTS `consumer_props`;

CREATE TABLE `consumer_props` (
  `id` varchar(255) NOT NULL,
  `propName` varchar(64) NOT NULL,
  `propValue` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`,`propName`),
  CONSTRAINT `FKConsumerProps` FOREIGN KEY (`id`) REFERENCES `consumer` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `consumer_props` */

/*Table structure for table `consumer_roles` */

DROP TABLE IF EXISTS `consumer_roles`;

CREATE TABLE `consumer_roles` (
  `consumer` varchar(255) NOT NULL,
  `role` bigint(20) NOT NULL,
  PRIMARY KEY (`consumer`,`role`),
  KEY `FKConsumerRolesRole` (`role`),
  CONSTRAINT `FKConsumerRolesConsumer` FOREIGN KEY (`consumer`) REFERENCES `consumer` (`id`),
  CONSTRAINT `FKConsumerRolesRole` FOREIGN KEY (`role`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `consumer_roles` */

/*Table structure for table `currencies` */

DROP TABLE IF EXISTS `currencies`;

CREATE TABLE `currencies` (
  `curr_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cuur_country` varchar(255) NOT NULL,
  `curr_name` varchar(255) NOT NULL,
  `curr_code` varchar(3) NOT NULL,
  `curr_number` varchar(3) NOT NULL,
  `curr_exponent` varchar(2) NOT NULL,
  PRIMARY KEY (`curr_id`),
  UNIQUE KEY `curr_uk` (`curr_number`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

/*Data for the table `currencies` */

insert  into `currencies`(`curr_id`,`cuur_country`,`curr_name`,`curr_code`,`curr_number`,`curr_exponent`) values 
(1,'United States','United States dollar','USD','840','2'),
(2,'Indonesia','Indonesian rupiah','IDR','360','2');

/*Table structure for table `eeuser` */

DROP TABLE IF EXISTS `eeuser`;

CREATE TABLE `eeuser` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `nick` varchar(64) NOT NULL,
  `passwordHash` varchar(8000) DEFAULT NULL,
  `name` varchar(128) DEFAULT NULL,
  `email` varchar(128) DEFAULT NULL,
  `active` char(1) DEFAULT NULL,
  `deleted` char(1) DEFAULT NULL,
  `verified` char(1) DEFAULT NULL,
  `startDate` date DEFAULT NULL,
  `endDate` date DEFAULT NULL,
  `forcePasswordChange` char(1) DEFAULT NULL,
  `lastLogin` datetime DEFAULT NULL,
  `passwordChanged` datetime DEFAULT NULL,
  `loginAttempts` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_gi0mq7qm8wxwmxhkcwksbnue` (`nick`),
  KEY `eeuser_nick` (`nick`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

/*Data for the table `eeuser` */

insert  into `eeuser`(`id`,`nick`,`passwordHash`,`name`,`email`,`active`,`deleted`,`verified`,`startDate`,`endDate`,`forcePasswordChange`,`lastLogin`,`passwordChanged`,`loginAttempts`) values 
(1,'admin','AQAAAAAAAAAAAAAAAAAAAAD9c0H+b9/sE7pfniFyVN8vk3yKd1htAOh7PdcqniN5RRTiYXCuxWi19Q4nN9nh5vdcWf+and/APm4KYE4dqLPSOH4znOBGyK2uOhT5pde6doEWjE02UhPV1taqsS9165dCyURoi15u7aaXLzxObhReytN+GUKyr3RZeXJNaT48QBgwFPFOAHOlM3cnsRgZMUmF84MbXya4g5B35MsN8fl8IqDbLX950tQnbnr/4KktNkDYt0wNphRCARsIMiXlbd9XL7/TRqsiHpCB0JIEQNTr6N6TVGth7GdYBi7IYBroHw/CmgN+OEp8ER43PqxaSzDLamwJErw0LDwn/B87C7bk',NULL,NULL,'Y','N','N',NULL,NULL,'N','2022-05-14 18:01:38','2022-03-28 17:50:32',0);

/*Table structure for table `eeuser_passwordhistory` */

DROP TABLE IF EXISTS `eeuser_passwordhistory`;

CREATE TABLE `eeuser_passwordhistory` (
  `eeuser` bigint(20) NOT NULL,
  `id` int(11) NOT NULL,
  `value` varchar(8000) NOT NULL,
  PRIMARY KEY (`eeuser`,`id`),
  CONSTRAINT `FKdpp5d41gbwwq9xg16m2oe2i03` FOREIGN KEY (`eeuser`) REFERENCES `eeuser` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `eeuser_passwordhistory` */

/*Table structure for table `eeuser_props` */

DROP TABLE IF EXISTS `eeuser_props`;

CREATE TABLE `eeuser_props` (
  `id` bigint(20) NOT NULL,
  `propName` varchar(64) NOT NULL,
  `propValue` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`,`propName`),
  CONSTRAINT `FKUserProps` FOREIGN KEY (`id`) REFERENCES `eeuser` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `eeuser_props` */

/*Table structure for table `eeuser_roles` */

DROP TABLE IF EXISTS `eeuser_roles`;

CREATE TABLE `eeuser_roles` (
  `eeuser` bigint(20) NOT NULL,
  `role` bigint(20) NOT NULL,
  PRIMARY KEY (`eeuser`,`role`),
  KEY `FKUserRolesRole` (`role`),
  CONSTRAINT `FKUserRolesRole` FOREIGN KEY (`role`) REFERENCES `role` (`id`),
  CONSTRAINT `FKUserRolesUser` FOREIGN KEY (`eeuser`) REFERENCES `eeuser` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `eeuser_roles` */

insert  into `eeuser_roles`(`eeuser`,`role`) values 
(1,1);

/*Table structure for table `fits` */

DROP TABLE IF EXISTS `fits`;

CREATE TABLE `fits` (
  `fit_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fit_config_id` varchar(4) NOT NULL,
  `fit_number` varchar(3) NOT NULL,
  `fit_algoidx` varchar(2) NOT NULL,
  `fit_bin_prefix` varchar(10) NOT NULL,
  `fit_decimaltab` varchar(16) NOT NULL,
  `fit_desc` varchar(250) NOT NULL,
  `fit_encpinkey` varchar(16) NOT NULL,
  `fit_idxrefpoints` varchar(6) NOT NULL,
  `fit_indirectstateidx` varchar(2) NOT NULL,
  `fit_langcodeidx` varchar(2) NOT NULL,
  `fit_localpinchecklen` varchar(2) NOT NULL,
  `fit_maxpinlen` varchar(2) NOT NULL,
  `fit_panlen` varchar(2) NOT NULL,
  `fit_panlocidx` varchar(2) NOT NULL,
  `fit_panpad` varchar(2) NOT NULL,
  `fit_pinblkformat` varchar(2) DEFAULT NULL,
  `fit_pinoffsetidx` varchar(2) NOT NULL,
  `fit_pinpad` varchar(2) NOT NULL,
  `fit_pinretrycount` varchar(2) NOT NULL,
  PRIMARY KEY (`fit_id`),
  UNIQUE KEY `fit_uk` (`fit_config_id`,`fit_number`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4;

/*Data for the table `fits` */

insert  into `fits`(`fit_id`,`fit_config_id`,`fit_number`,`fit_algoidx`,`fit_bin_prefix`,`fit_decimaltab`,`fit_desc`,`fit_encpinkey`,`fit_idxrefpoints`,`fit_indirectstateidx`,`fit_langcodeidx`,`fit_localpinchecklen`,`fit_maxpinlen`,`fit_panlen`,`fit_panlocidx`,`fit_panpad`,`fit_pinblkformat`,`fit_pinoffsetidx`,`fit_pinpad`,`fit_pinretrycount`) values 
(1,'0850','000','00','542449ffff','0000000000000000','FIT-542449ffff','0000000000000000','000000','01','00','00','86','00','00','00',NULL,'ff','0f','00'),
(2,'0850','001','00','535316ffff','0000000000000000','FIT-535316ffff','0000000000000000','000000','01','00','00','86','00','00','00',NULL,'ff','0f','00'),
(3,'0850','002','00','ffffffffff','0000000000000000','FIT-ffffffffff','0000000000000000','000000','00','00','00','86','00','00','00',NULL,'ff','0f','00'),
(4,'0870','000','00','542449ffff','0000000000000000','FIT-542449ffff','0000000000000000','000000','01','00','00','86','00','00','00',NULL,'ff','0f','00'),
(5,'0870','001','00','535316ffff','0000000000000000','FIT-535316ffff','0000000000000000','000000','01','00','00','86','00','00','00',NULL,'ff','0f','00'),
(6,'0870','002','00','ffffffffff','0000000000000000','FIT-ffffffffff','0000000000000000','000000','00','00','00','86','00','00','00',NULL,'ff','0f','00');

/*Table structure for table `institutions` */

DROP TABLE IF EXISTS `institutions`;

CREATE TABLE `institutions` (
  `ins_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ins_code` varchar(250) NOT NULL,
  `ins_name` varchar(40) NOT NULL,
  `ins_active` char(1) DEFAULT '1',
  `ins_address1` varchar(40) NOT NULL,
  `ins_address2` varchar(40) DEFAULT NULL,
  `ins_city` varchar(40) NOT NULL,
  `ins_state` varchar(2) NOT NULL,
  `ins_province` varchar(20) DEFAULT NULL,
  `ins_country` varchar(40) NOT NULL,
  `ins_zip` varchar(10) NOT NULL,
  `ins_contact` varchar(40) DEFAULT NULL,
  `ins_phone` varchar(40) DEFAULT NULL,
  `ins_institution_code_mc` varchar(20) NOT NULL,
  `ins_institution_code_visa` varchar(20) NOT NULL,
  `ins_date_created` timestamp NOT NULL DEFAULT current_timestamp(),
  `ins_date_updated` timestamp NULL DEFAULT NULL ON UPDATE current_timestamp(),
  `aud_usr_id` int(11) DEFAULT NULL,
  `aud_created_log_entry_id` int(11) DEFAULT NULL,
  `aud_last_update_log_entry_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`ins_id`),
  UNIQUE KEY `ins_uk` (`ins_code`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

/*Data for the table `institutions` */

insert  into `institutions`(`ins_id`,`ins_code`,`ins_name`,`ins_active`,`ins_address1`,`ins_address2`,`ins_city`,`ins_state`,`ins_province`,`ins_country`,`ins_zip`,`ins_contact`,`ins_phone`,`ins_institution_code_mc`,`ins_institution_code_visa`,`ins_date_created`,`ins_date_updated`,`aud_usr_id`,`aud_created_log_entry_id`,`aud_last_update_log_entry_id`) values 
(1,'0145','Banco de Prueba','1','SUCURSAL CENTRAL',NULL,'Caracas','DC',NULL,'VE','1060','Pedro Perez','+58 (414) 320-6238','9000000138','','2022-03-09 16:40:11',NULL,NULL,NULL,NULL);

/*Table structure for table `isoerr2atms` */

DROP TABLE IF EXISTS `isoerr2atms`;

CREATE TABLE `isoerr2atms` (
  `isoerr2atm_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `isoerr2atm_config_id` varchar(255) NOT NULL,
  `isoerr2atm_description` varchar(255) NOT NULL,
  `isoerr2atm_isoresp` int(11) NOT NULL,
  `isoerr2atm_language639` varchar(255) NOT NULL,
  `isoerr2atm_language3166` varchar(255) NOT NULL,
  `isoerr2atm_screen_card_retained` varchar(255) NOT NULL,
  `isoerr2atm_screen_no_receipt` varchar(255) NOT NULL,
  `isoerr2atm_screen_receipt` varchar(255) NOT NULL,
  `isoerr2atm_screen_stmt_delivered` varchar(255) NOT NULL,
  `isoerr2atm_state` varchar(255) NOT NULL,
  PRIMARY KEY (`isoerr2atm_id`),
  UNIQUE KEY `isoerr2atm_uk` (`isoerr2atm_config_id`,`isoerr2atm_isoresp`,`isoerr2atm_language639`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4;

/*Data for the table `isoerr2atms` */

insert  into `isoerr2atms`(`isoerr2atm_id`,`isoerr2atm_config_id`,`isoerr2atm_description`,`isoerr2atm_isoresp`,`isoerr2atm_language639`,`isoerr2atm_language3166`,`isoerr2atm_screen_card_retained`,`isoerr2atm_screen_no_receipt`,`isoerr2atm_screen_receipt`,`isoerr2atm_screen_stmt_delivered`,`isoerr2atm_state`) values 
(1,'0870','Sin Respuesta',99,'eng','840','363','362','361','000','361'),
(2,'0870','Pin Incorrecto',17,'eng','840','351','350','350','000','350'),
(3,'0870','Sin Respuesta',99,'ind','360','363','362','361','000','361'),
(4,'0870','Pin Incorrecto',17,'ind','360','351','350','350','000','350'),
(5,'0850','Sin Respuesta',99,'eng','840','054','017','017','000','050'),
(6,'0850','Pin Incorrecto',17,'eng','840','054','032','032','000','236');

/*Table structure for table `realm` */

DROP TABLE IF EXISTS `realm`;

CREATE TABLE `realm` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(8000) DEFAULT NULL,
  `name` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_realm_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `realm` */

/*Table structure for table `receipts` */

DROP TABLE IF EXISTS `receipts`;

CREATE TABLE `receipts` (
  `rcp_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `rcp_config_id` varchar(255) DEFAULT NULL,
  `rcp_code` varchar(255) DEFAULT NULL,
  `rcp_description` varchar(255) DEFAULT NULL,
  `rcp_template` longtext DEFAULT NULL,
  `rcp_date_created` timestamp NOT NULL DEFAULT current_timestamp(),
  `rcp_date_updated` timestamp NULL DEFAULT NULL ON UPDATE current_timestamp(),
  `aud_usr_id` int(11) DEFAULT NULL,
  `aud_created_log_entry_id` int(11) DEFAULT NULL,
  `aud_last_update_log_entry_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`rcp_id`),
  UNIQUE KEY `rcp_uk` (`rcp_config_id`,`rcp_code`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

/*Data for the table `receipts` */

insert  into `receipts`(`rcp_id`,`rcp_config_id`,`rcp_code`,`rcp_description`,`rcp_template`,`rcp_date_created`,`rcp_date_updated`,`aud_usr_id`,`aud_created_log_entry_id`,`aud_last_update_log_entry_id`) values 
(1,'0870','00000001','Recibo de Transacciones','BANCO DE PRUEBA\r\nCAJERO: ${vars.luno}\r\nTRACE: ${vars.trace}\r\n\r\nCOMPROBANTE DE OPERACION\r\n\r\nNUMERO DE TARJETA   ---FECHA-- --HORA--\r\n${vars.cardNumber}      ${vars.currentDate}\r\n\r\nSEQ. ----TRANSACCION----- AUTORIZACION\r\n${vars.trnSerialNumber} ${vars.opDescription}\r\n\r\nMONTO OPERACION :\r\nSALDO DISPONIBLE: ${vars.balance}\r\nCOM BANCO EMISOR:\r\n\r\nOPERACION EXITOSA\r\n\r\nCONSERVE PARA SU CONTROL\r\n','2022-03-09 16:36:09','2022-03-23 16:17:25',NULL,NULL,NULL);

/*Table structure for table `revision` */

DROP TABLE IF EXISTS `revision`;

CREATE TABLE `revision` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `date` datetime DEFAULT NULL,
  `info` varchar(8000) DEFAULT NULL,
  `ref` varchar(64) DEFAULT NULL,
  `author` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ref` (`ref`),
  KEY `FKgn31vrcdhydl4x38cmdfxiyrn` (`author`),
  CONSTRAINT `FKgn31vrcdhydl4x38cmdfxiyrn` FOREIGN KEY (`author`) REFERENCES `eeuser` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

/*Data for the table `revision` */

insert  into `revision`(`id`,`date`,`info`,`ref`,`author`) values 
(1,'2022-03-28 17:50:32','Password changed','user.1',1);

/*Table structure for table `role` */

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL,
  `realm` bigint(20) DEFAULT NULL,
  `parent` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_RoleRealmName` (`name`,`realm`),
  KEY `FK_RoleRealm` (`realm`),
  KEY `FK_RoleParent` (`parent`),
  CONSTRAINT `FK_RoleParent` FOREIGN KEY (`parent`) REFERENCES `role` (`id`),
  CONSTRAINT `FK_RoleRealm` FOREIGN KEY (`realm`) REFERENCES `realm` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

/*Data for the table `role` */

insert  into `role`(`id`,`name`,`realm`,`parent`) values 
(1,'admin',NULL,NULL);

/*Table structure for table `role_perms` */

DROP TABLE IF EXISTS `role_perms`;

CREATE TABLE `role_perms` (
  `role` bigint(20) NOT NULL,
  `name` varchar(64) NOT NULL,
  PRIMARY KEY (`role`,`name`),
  CONSTRAINT `FK_RolePermissions` FOREIGN KEY (`role`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `role_perms` */

insert  into `role_perms`(`role`,`name`) values 
(1,'accounting'),
(1,'login'),
(1,'sysadmin'),
(1,'sysconfig.read'),
(1,'sysconfig.write'),
(1,'users.read'),
(1,'users.write');

/*Table structure for table `screens` */

DROP TABLE IF EXISTS `screens`;

CREATE TABLE `screens` (
  `scr_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `scr_config_id` varchar(255) DEFAULT NULL,
  `scr_number` varchar(255) DEFAULT NULL,
  `scr_data` longtext DEFAULT NULL,
  `scr_desc` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`scr_id`),
  UNIQUE KEY `scr_uk` (`scr_config_id`,`scr_number`)
) ENGINE=InnoDB AUTO_INCREMENT=244 DEFAULT CHARSET=utf8mb4;

/*Data for the table `screens` */

insert  into `screens`(`scr_id`,`scr_config_id`,`scr_number`,`scr_data`,`scr_desc`) values 
(1,'0850','000','␌␏@@000EN␏ADYOU HAVE RUN OUT OF TIME␏CCWOULD YOU LIKE MORE TIME?␏F;YES >␏I<NO >','Sin Descripcion'),
(2,'0850','001','␌␏@@001EN␏GIOUT OF SERVICE','Sin Descripcion'),
(3,'0850','002','␌␏@@002EN␏EIDUE TO A FAULT␏GGUNABLE TO CONTINUE␏IHPLEASE TAKE CARD','Sin Descripcion'),
(4,'0850','010','␎011[100z␎012[100z','Sin Descripcion'),
(5,'0850','011','␌␏@@011EN␏EFWELCOME TO NCR BANK!␏GGPLEASE INSERT CARD','Sin Descripcion'),
(6,'0850','012','␌␏@@012EN␏DKNCR BANK␏FDHOME OF ICC ENABLED ATMS␏HGPLEASE INSERT CARD','Sin Descripcion'),
(7,'0850','013','␌␏@@013EN␏EDBAD READ – INVALID CARD!␏GHPLEASE TAKE CARD','Sin Descripcion'),
(8,'0850','014','␌␏@@014EN␏ECCARD NOT ACCEPTED BY BANK!␏GHPLEASE TAKE CARD','Sin Descripcion'),
(9,'0850','015','␌␏@@015EN␏EJPLEASE WAIT','Sin Descripcion'),
(10,'0850','016','␌␏@@016EN␏EIDUE TO A FAULT␏GGUNABLE TO CONTINUE␏IHPLEASE TAKE CARD','Sin Descripcion'),
(11,'0850','017','␌␏@@017EN␏EEUNABLE TO PROCESS CARD␏GHPLEASE TAKE CARD␏IBSEEK ASSISTANCE FROM ISSUER!','Sin Descripcion'),
(12,'0850','018','␌␏@@018EN␏BDPLEASE CHOOSE A LANGUAGE␏I@< FRENCH␏I7ENGLISH >␏L@< GERMAN␏L9WELSH >','Sin Descripcion'),
(13,'0850','019','␌␏@@019EN␏EETRANSACTION CANCELLED␏GHPLEASE TAKE CARD','Sin Descripcion'),
(14,'0850','020','␌␏@@020EN␏EKTIMED OUT!␏GHPLEASE TAKE CARD','Sin Descripcion'),
(15,'0850','021','␌␏@@021EN␏BAPLEASE SELECT PRODUCT/ACCOUNT:','Sin Descripcion'),
(16,'0850','022','␏FN&&S&&RJ**************** >&&E','Sin Descripcion'),
(17,'0850','023','␏IN&&S&&RJ**************** >&&E','Sin Descripcion'),
(18,'0850','024','␏LN&&S&&RJ**************** >&&E','Sin Descripcion'),
(19,'0850','025','␏ON&&S&&RJ**************** >&&E','Sin Descripcion'),
(20,'0850','026','␏O:MORE >','Sin Descripcion'),
(21,'0850','027','␏O@< BACK','Sin Descripcion'),
(22,'0850','028','&&S&&CD****************&&E','Sin Descripcion'),
(23,'0850','030','␌␏@@030EN␏ADSORRY CANNOT ACCESS YOUR␏BECHOSEN PRODUCT/ACCOUNT␏DBPLEASE SELECT AN ALTERNATIVE','Sin Descripcion'),
(24,'0850','031','␌␏@@031EN␏ECPLEASE ENTER YOUR PIN FOR:␏GH␎029␏JN','Sin Descripcion'),
(25,'0850','032','␌␏@@032EN␏CIPIN INCORRECT!␏ECPLEASE ENTER YOUR PIN FOR:␏GH␎029␏JN','Sin Descripcion'),
(26,'0850','033','␌␏@@033EN␏DATOO MANY INCORRECT PIN ENTRIES␏GHPLEASE TAKE CARD␏JBSEEK ASSISTANCE FROM ISSUER','Sin Descripcion'),
(27,'0850','034','␌␏@@034EN␏EHPLEASE ENTER PIN␏JN','Sin Descripcion'),
(28,'0850','035','␌␏@@035EN␏CIPIN INCORRECT!␏EHPLEASE ENTER PIN␏JN','Sin Descripcion'),
(29,'0850','036','␌␏@@036EN␏ABPLEASE CHOOSE THE CURRENCY FOR␏CFTHIS CASH WITHDRAWAL␏L:EURO >␏O6STERLING >','Sin Descripcion'),
(30,'0850','037','␌␏@@037EN␏CDPLEASE CHOOSE A SERVICE:␏I:CASH >␏L7BALANCE >','Sin Descripcion'),
(31,'0850','038','␌␏@@038EN␏CDPLEASE CHOOSE A SERVICE␏I:CASH >␏L7BALANCE >␏O1MINI-TATEMENT >␏N9','Sin Descripcion'),
(32,'0850','039','␌␏@@039EN␏DFPLEASE ENTER AMOUNT:','Sin Descripcion'),
(33,'0850','040','␏IJE *****0.00','Sin Descripcion'),
(34,'0850','041','␌␏@@041EN␏BIINVALID AMOUNT␏DFPLEASE ENTER AMOUNT:','Sin Descripcion'),
(35,'0850','042','␌␏@@042EN␏DFPLEASE ENTER AMOUNT:','Sin Descripcion'),
(36,'0850','043','␏IJ` *****0.00','Sin Descripcion'),
(37,'0850','044','␌␏@@044EN␏BIINVALID AMOUNT␏DFPLEASE ENTER AMOUNT:','Sin Descripcion'),
(38,'0850','046','␌␏@@046EN␏BGDO YOU WISH TO USE␏DDTHE SAME PRODUCT/ACCOUNT␏L;YES >␏O<NO >','Sin Descripcion'),
(39,'0850','047','␌␏@@047EN␏EHPLEASE TAKE CARD␏IBTHANK YOU FOR USING NCR BANK','Sin Descripcion'),
(40,'0850','049','␎050␎051','Sin Descripcion'),
(41,'0850','050','␌␏@@050EN␏EHPLEASE TAKE CARD␏IEAND WAIT FOR YOUR CASH','Sin Descripcion'),
(42,'0850','051','␌␏@@051EN␏EHPLEASE TAKE CASH␏IFAND WAIT FOR RECEIPT','Sin Descripcion'),
(43,'0850','052','␌␏@@052EN␏EDPLEASE TAKE YOUR RECEIPT␏IBTHANK YOU FOR USING NCR BANK','Sin Descripcion'),
(44,'0850','053','␌␏@@053EN␏CMSORRY!␏EAUNABLE TO PROVIDE YOUR RECEIPT␏HBTHANK YOU FOR USING NCR BANK','Sin Descripcion'),
(45,'0850','054','␌␏@@054EN␏CMSORRY!␏EBYOUR CARD HAS BEEN RETAINED,␏GEPLEASE SEEK ASSISTANCE','Sin Descripcion'),
(46,'0850','100','␎000␏@E100CY','Sin Descripcion'),
(47,'0850','101','␎001␏@E101CY','Sin Descripcion'),
(48,'0850','102','␎002␏@E102CY','Sin Descripcion'),
(49,'0850','114','␎014␏@E114CY','Sin Descripcion'),
(50,'0850','115','␎015␏@E115CY','Sin Descripcion'),
(51,'0850','116','␎016␏@E116CY','Sin Descripcion'),
(52,'0850','117','␎017␏@E117CY','Sin Descripcion'),
(53,'0850','119','␎019␏@E119CY','Sin Descripcion'),
(54,'0850','120','␎020␏@E120CY','Sin Descripcion'),
(55,'0850','121','␎021␏@E121CY','Sin Descripcion'),
(56,'0850','126','␎026␏O7CY','Sin Descripcion'),
(57,'0850','127','␎027␏OGCY','Sin Descripcion'),
(58,'0850','130','␎030␏@E130CY','Sin Descripcion'),
(59,'0850','131','␎031␏@E131CY␏JN','Sin Descripcion'),
(60,'0870','000','␌␏@KCOPIN ATM␏BDYOU ARE RUN  OUT OF TIME␏CEDO YOU NEED MORE TIME?␏F8YES -- >␏I9NO -- >','Sin Descripcion'),
(61,'0870','001','␌␏@KCOPIN ATM␏CCSORRY, THIS COPIN  ATM IS␏DLOFFLINE␏FDPLEASE USE OTHER NEAREST␏GJCOPIN KASSA␏H@--------------------------------␏IBMAAF, SEMENTARA  COPIN  ATM␏JAINI TIDAK  DAPAT MELAYANI ANDA␏LFSILAHKAN MENGGUNAKAN␏MBATM  COPIN TERDEKAT LAINNYA','Sin Descripcion'),
(62,'0870','002','␌␏@KCOPIN ATM␏CCSORRY, THIS COPIN  ATM IS␏DIOUT OF SERVICE␏FDPLEASE USE OTHER NEAREST␏GJCOPIN KASSA␏H@--------------------------------␏IEMAAF, COPIN KASSA INI␏JESEDANG DALAM PERBAIKAN␏LFSILAHKAN MENGGUNAKAN␏MBATM  COPIN TERDEKAT LAINNYA','Sin Descripcion'),
(63,'0870','003','␌␏@KCOPIN ATM␏CDSORRY, WE ARE UNDERGOING␏DEADMINISTRATION PROCESS␏FJPLEASE  WAIT␏GEIN JUST  A FEW MINUTES␏H@--------------------------------␏IBMAAF, KAMI  SEDANG MELAKUKAN␏JFPROSES  ADMINISTRASI␏LHSILAHKAN  TUNGGU␏MFDALAM BEBERAPA MENIT','Sin Descripcion'),
(64,'0870','010','␌␏@EWELCOME, TO COPIN ATM␏ABINSERT YOUR CARD FOR SERVICE␏NASELAMAT DATANG DI COPIN KASSA␏OCSILAKAN MASUKAN KARTU ANDA','Sin Descripcion'),
(65,'0870','011','[77m␌␎L07␏@9␎L07␏@C␎L03␏DC␎L06␏E0␎L15','Sin Descripcion'),
(66,'0870','012','[77m␌␎L07␏@9␎L07␏@C␎L03␏ED␎L15␏D3␎L05','Sin Descripcion'),
(67,'0870','013','[77m␌␎L07␏@9␎L07␏@C␎L03␏E0␎L10␏EB␎L08[010z␏EB␎L09[010z␏EB␎L08[010z␏EB␎L09','Sin Descripcion'),
(68,'0870','014','[77m␌␏AH␎L11␏NE␎L12','Sin Descripcion'),
(69,'0870','015','[77m␌␏AD␎L13␏JF␎L04','Sin Descripcion'),
(70,'0870','016','[77m␌␏CE␎L14␏JF␎L04','Sin Descripcion'),
(71,'0870','030','␌␏@KCOPIN ATM␏BENO RECEIPT IS PROVIDED␏CBWOULD YOU  LIKE TO CONTINUE?␏DA-------------------------------␏EAOTA TRANSAKSI  TIDAK DISEDIAKAN␏FAAPAKAH ANDA INGIN MELANJUTKAN?␏I5YES/YA -- >␏L3NO/TIDAK -- >','Sin Descripcion'),
(72,'0870','040','␌␏@KCOPIN ATM␏BBPLEASE SELECT  YOUR LANGUAGE␏C@--------------------------------␏DCSILAHKAN PILIH BAHASA ANDA␏FLENGLISH/INGGRIS -- >␏IGINDONESIAN/INDONESIA -- >␏LBTO OBTAIN  CARD PRESS CANCEL␏M@--------------------------------␏N@UNTUK MENDAPATKAN  KAR','Sin Descripcion'),
(73,'0870','050','␌PEG00.jpg\\␏@1COPIN ATM␏BNENTER YOUR PIN␏DNAFTER PIN ENTERED␏F6PRESS -- >␏MNTO OBTAIN CARD␏ONPRESS CANCEL␏I0','Sin Descripcion'),
(74,'0870','051','␌␏@KCOPIN ATM␏BCYOU ENTERED AN INVALID PIN␏CIRE-ENTER  YOUR␏DAPERSONAL IDENTIFICATION NUMBER␏FAAFTER PIN RE-ENTERED PRESS -- >␏OBTO OBTAIN  CARD PRESS CANCEL␏I0','Sin Descripcion'),
(75,'0870','100','␌␏@KCOPIN ATM␏BDSELECT  TRANSACTION TYPE␏CABY PRESSING  CORRESPONDING KEY␏EEBALANCE␏F@< -- INQUIRY     WITHDRAWAL -- >␏I3TRANSFER -- >␏L1PIN CHANGE -- >␏M1ON US ONLY␏OAO VOID TRANSACTION PRESS CANCEL␏FA','Sin Descripcion'),
(76,'0870','110','␌␏@KCOPIN ATM␏BKWITHDRAWAL␏DDSELECT YOUR ACCOUNT TYPE␏F4SAVINGS -- >␏I5CURRENT -- >␏LO CREDIT CARD -- >␏O@TO VOID TRANSACTION PRESS CANCEL','Sin Descripcion'),
(77,'0870','111','␌␏@KCOPIN ATM␏BKWITHDRAWAL␏DHENTER THE AMOUNT␏ECIN MULTIPLES OF RP. 20,000␏I4CORRECT -- >␏L2INCORRECT -- >␏O@TO VOID TRANSACTION PRESS CANCEL','Sin Descripcion'),
(78,'0870','112','␌␏@KCOPIN ATM␏BKWITHDRAWAL␏CAYOU ENTERED  AN INVALID AMOUNT␏DFRE-ENTER  THE AMOUNT␏ECIN MULTIPLES OF RP. 20,000␏I4CORRECT -- >␏L2INCORRECT -- >␏O@TO VOID TRANSACTION PRESS CANCEL','Sin Descripcion'),
(79,'0870','113','␌␏@KCOPIN ATM␏BKWITHDRAWAL␏CBYOU HAVE  INSUFFICIENT FUNDS␏DFRE-ENTER  THE AMOUNT␏ECIN MULTIPLES OF RP. 20,000␏I4CORRECT -- >␏L2INCORRECT -- >␏O@TO VOID TRANSACTION PRESS CANCEL','Sin Descripcion'),
(80,'0870','114','␌␏@KCOPIN ATM␏BKWITHDRAWAL␏CCAMOUNT EXCEEDS DAILY LIMIT␏DFRE-ENTER  THE AMOUNT␏ECIN MULTIPLES OF RP. 20,000␏I4CORRECT -- >␏L2INCORRECT -- >␏O@TO VOID TRANSACTION PRESS CANCEL','Sin Descripcion'),
(81,'0870','115','␏G3RP. *,***,**0','Sin Descripcion'),
(82,'0870','118','␌␏@KCOPIN ATM␏BKWITHDRAWAL␏DBSELECT YOUR  SAVINGS ACCOUNT␏O@TO VOID TRANSACTION PRESS CANCEL','Sin Descripcion'),
(83,'0870','119','␌␏@KCOPIN ATM␏BKWITHDRAWAL␏DBSELECT YOUR CHECKING ACCOUNT␏O@TO VOID TRANSACTION PRESS CANCEL','Sin Descripcion'),
(84,'0870','130','␌␏@KCOPIN ATM␏BKFAST  CASH␏DDSELECT YOUR ACCOUNT TYPE␏F4SAVINGS -- >␏I3CHECKING -- >␏L0CREDIT CARD -- >␏O@TO VOID TRANSACTION PRESS CANCEL','Sin Descripcion'),
(85,'0870','131','␌␏@KCOPIN ATM␏BKFAST  CASH␏DCSELECT THE AMOUNT (RUPIAH)␏F@< -- 100,000         60,000 -- >␏I@< -- 400,000        200,000 -- >␏L@< -- OTHER AMOUNT   600,000 -- >␏O@TO VOID TRANSACTION PRESS CANCEL','Sin Descripcion'),
(86,'0870','132','␌␏@KCOPIN ATM␏BKFAST  CASH␏CBYOU HAVE  INSUFFICIENT FUNDS␏DARE-SELECT THE  AMOUNT (RUPIAH)␏F@< -- 100,000         60,000 -- >␏I@< -- 400,000        200,000 -- >␏L@< -- OTHER AMOUNT   600,000 -- >␏O@TO VOID TRANSACTION PRESS CANCEL','Sin Descripcion'),
(87,'0870','133','␌␏@KCOPIN ATM␏BKFAST  CASH␏CCAMOUNT EXCEEDS DAILY LIMIT␏DARE-SELECT THE  AMOUNT (RUPIAH)␏F@< -- 100,000         60,000 -- >␏I@< -- 400,000        200,000 -- >␏L@< -- OTHER AMOUNT   600,000 -- >␏O@TO VOID TRANSACTION PRESS CANCEL','Sin Descripcion'),
(88,'0870','138','␌␏@KCOPIN ATM␏BKFAST  CASH␏DBSELECT YOUR  SAVINGS ACCOUNT␏O@TO VOID TRANSACTION PRESS CANCEL','Sin Descripcion'),
(89,'0870','139','␌␏@KCOPIN ATM␏BKFAST  CASH␏DBSELECT YOUR CHECKING ACCOUNT␏O@TO VOID TRANSACTION PRESS CANCEL','Sin Descripcion'),
(90,'0870','150','␌␏@KCOPIN ATM␏BLTRANSFER␏DDSELECT YOUR ACCOUNT TYPE␏FOFROM SAVINGS -- >␏INFROM CHECKING -- >␏O@TO VOID TRANSACTION PRESS CANCEL','Sin Descripcion'),
(91,'0870','151','␌␏@KCOPIN ATM␏BLTRANSFER␏DDSELECT YOUR ACCOUNT TYPE␏F1TO SAVINGS -- >␏I0TO CHECKING -- >␏O@TO VOID TRANSACTION PRESS CANCEL','Sin Descripcion'),
(92,'0870','152','␌␏@KCOPIN ATM␏BLTRANSFER␏DHENTER THE AMOUNT␏I4CORRECT -- >␏L2INCORRECT -- >␏O@TO VOID TRANSACTION PRESS CANCEL','Sin Descripcion'),
(93,'0870','153','␌␏@KCOPIN ATM␏BLTRANSFER␏CBYOU HAVE  INSUFFICIENT FUNDS␏DFRE-ENTER  THE AMOUNT␏I4CORRECT -- >␏L2INCORRECT -- >␏O@TO VOID TRANSACTION PRESS CANCEL','Sin Descripcion'),
(94,'0870','154','␌␏@KCOPIN ATM␏BLTRANSFER␏CCAMOUNT EXCEEDS DAILY LIMIT␏DFRE-ENTER  THE AMOUNT␏I4CORRECT -- >␏L2INCORRECT -- >␏O@TO VOID TRANSACTION PRESS CANCEL','Sin Descripcion'),
(95,'0870','155','␏G3RP. *,***,**0','Sin Descripcion'),
(96,'0870','156','␌␏@KCOPIN ATM␏BLTRANSFER␏CL( FROM )␏DBSELECT YOUR  SAVINGS ACCOUNT␏O@TO VOID TRANSACTION PRESS CANCEL','Sin Descripcion'),
(97,'0870','157','␌␏@KCOPIN ATM␏BLTRANSFER␏CL( FROM )␏DBSELECT YOUR CHECKING ACCOUNT␏O@TO VOID TRANSACTION PRESS CANCEL','Sin Descripcion'),
(98,'0870','158','␌␏@KCOPIN ATM␏BLTRANSFER␏CM( TO )␏DBSELECT YOUR  SAVINGS ACCOUNT␏O@TO VOID TRANSACTION PRESS CANCEL','Sin Descripcion'),
(99,'0870','159','␌␏@KCOPIN ATM␏BLTRANSFER␏CM( TO )␏DBSELECT YOUR CHECKING ACCOUNT␏O@TO VOID TRANSACTION PRESS CANCEL','Sin Descripcion'),
(100,'0870','170','␌␏@KCOPIN ATM␏BHBALANCE  INQUIRY␏DDSELECT YOUR ACCOUNT TYPE␏F4SAVINGS -- >␏I3CHECKING -- >␏L0CREDIT CARD -- >␏O@TO VOID TRANSACTION PRESS CANCEL','Sin Descripcion'),
(101,'0870','171','␌␏@KCOPIN ATM␏BHBALANCE  INQUIRY␏D@LEDGER    RP.␏F@AVAILABLE RP.␏I6PRINT -- >␏L7DONE -- >␏O@TO VOID TRANSACTION PRESS CANCEL␎172','Sin Descripcion'),
(102,'0870','172','','Sin Descripcion'),
(103,'0870','178','␌␏@KCOPIN ATM␏BHBALANCE  INQUIRY␏DBSELECT YOUR  SAVINGS ACCOUNT␏O@TO VOID TRANSACTION PRESS CANCEL','Sin Descripcion'),
(104,'0870','179','␌␏@KCOPIN ATM␏BHBALANCE  INQUIRY␏DBSELECT YOUR CHECKING ACCOUNT␏O@TO VOID TRANSACTION PRESS CANCEL','Sin Descripcion'),
(105,'0870','200','␌␏@KCOPIN ATM␏BKPIN CHANGE␏DGENTER YOUR NEW PIN␏FHAFTER ENTERED PRESS -- >␏LF*** FIRST  ENTRY ***␏O@TO VOID TRANSACTION PRESS CANCEL␏I0','Sin Descripcion'),
(106,'0870','201','␌␏@KCOPIN ATM␏BKPIN CHANGE␏DGENTER YOUR NEW PIN␏FHAFTER ENTERED PRESS -- >␏LF*** SECOND ENTRY ***␏O@TO VOID TRANSACTION PRESS CANCEL␏I0','Sin Descripcion'),
(107,'0870','202','␌␏@KCOPIN ATM␏BKPIN CHANGE␏CBNEW PIN ENTRIES DO NOT MATCH␏DERE-ENTER YOUR  NEW PIN␏FEAFTER RE-ENTERED PRESS -- >␏LF*** FIRST  ENTRY ***␏O@TO VOID TRANSACTION PRESS CANCEL␏I0','Sin Descripcion'),
(108,'0870','203','␌␏@KCOPIN ATM␏BKPIN CHANGE␏CBNEW PIN ENTRIES DO NOT MATCH␏DERE-ENTER YOUR  NEW PIN␏FEAFTER RE-ENTERED PRESS -- >␏LF*** SECOND ENTRY ***␏O@TO VOID TRANSACTION PRESS CANCEL␏I0','Sin Descripcion'),
(109,'0870','280','␌␏@KCOPIN ATM␏BASELECT ADMINISTRATIVE FUNCTION␏CABY PRESSING  CORRESPONDING KEY␏FITERMINAL BALANCING -- >␏ILCASH ADJUSTMENT -- >␏LEPRINT TERMINAL BALANCE -- >␏O@TO VOID TRANSACTION PRESS CANCEL','Sin Descripcion'),
(110,'0870','281','␌␏@KCOPIN ATM␏BGTERMINAL BALANCING␏DESELECT  BALANCING TYPE␏FNSTANDARD CASH -- >␏IOCURRENT CASH -- >␏O@TO VOID TRANSACTION PRESS CANCEL','Sin Descripcion'),
(111,'0870','282','␌␏@KCOPIN ATM␏BHCASH  ADJUSTMENT␏DBSELECT CASH  ADJUSTMENT TYPE␏FNINCREASE CASH -- >␏INDECREASE CASH -- >␏O@TO VOID TRANSACTION PRESS CANCEL','Sin Descripcion'),
(112,'0870','283','␌␏@KCOPIN ATM␏BHCASH  ADJUSTMENT␏DFSELECT  DENOMINATION␏F@< -- 20,000          50,000 -- >␏I@< -- 00,000          10,000 -- >␏O@TO VOID TRANSACTION PRESS CANCEL','Sin Descripcion'),
(113,'0870','284','␌␏@KCOPIN ATM␏BHCASH  ADJUSTMENT␏CFINVALID DENOMINATION␏DERE-SELECT DENOMINATION␏F@< -- 20,000          50,000 -- >␏I@< -- 00,000          10,000 -- >␏O@TO VOID TRANSACTION PRESS CANCEL','Sin Descripcion'),
(114,'0870','285','␌␏@KCOPIN ATM␏BHCASH  ADJUSTMENT␏DHENTER THE AMOUNT␏EC                          ␏I4CORRECT -- >␏L2INCORRECT -- >␏O@TO VOID TRANSACTION PRESS CANCEL','Sin Descripcion'),
(115,'0870','286','␌␏@KCOPIN ATM␏BHCASH  ADJUSTMENT␏CAYOU ENTERED  AN INVALID AMOUNT␏DFRE-ENTER  THE AMOUNT␏EC                          ␏I4CORRECT -- >␏L2INCORRECT -- >␏O@TO VOID TRANSACTION PRESS CANCEL','Sin Descripcion'),
(116,'0870','287','␏G2RP. **,***,**0','Sin Descripcion'),
(117,'0870','300','␌␏@KCOPIN ATM␏CBYOUR TRANSACTION IS COMPLETE␏FIWOULD YOU LIKE␏GFANOTHER TRANSACTION?␏I8YES -- >␏L9NO -- >','Sin Descripcion'),
(118,'0870','301','␌␏@KCOPIN ATM␏CDADMINISTRATIVE  FUNCTION␏DJIS  COMPLETE␏FIWOULD YOU LIKE␏GFANOTHER TRANSACTION?␏I8YES -- >␏L9NO -- >','Sin Descripcion'),
(119,'0870','302','␌␏@KCOPIN ATM␏CGTERMINAL BALANCING␏DGHAS EVER BEEN DONE␏FIWOULD YOU LIKE␏GFANOTHER TRANSACTION?␏I8YES -- >␏L9NO -- >','Sin Descripcion'),
(120,'0870','303','␌␏@KCOPIN ATM␏CDYOUR CARD  DOES NOT HAVE␏DERELATED ACCOUNT NUMBER␏FIWOULD YOU LIKE␏GFANOTHER TRANSACTION?␏I8YES -- >␏L9NO -- >','Sin Descripcion'),
(121,'0870','304','␌␏@KCOPIN ATM␏CCYOU ARE RESTRICTED ON THIS␏DETRANSACTION OR ACCOUNT␏FIWOULD YOU LIKE␏GFANOTHER TRANSACTION?␏I8YES -- >␏L9NO -- >','Sin Descripcion'),
(122,'0870','305','␌␏@KCOPIN ATM␏CDUNABLE TO DISPENSE MONEY␏DDPOSSIBLE EQUIPMENT ERROR␏FIWOULD YOU LIKE␏GFANOTHER TRANSACTION?␏I8YES -- >␏L9NO -- >','Sin Descripcion'),
(123,'0870','306','␌␏@KCOPIN ATM␏CCDUE TO  THE ATM CAPABILITY␏DDONLY 40  NOTES DISPENSED␏FIWOULD YOU LIKE␏GFANOTHER TRANSACTION?␏I8YES -- >␏L9NO -- >','Sin Descripcion'),
(124,'0870','307','␌␏@KCOPIN ATM␏CDTRANSFER CAN NOT BE DONE␏DFTO THE  SAME ACCOUNT␏FIWOULD YOU LIKE␏GFANOTHER TRANSACTION?␏I8YES -- >␏L9NO -- >','Sin Descripcion'),
(125,'0870','308','␌␏@KCOPIN ATM␏CCWE ARE  UNDERGOING PROCESS␏DEREQUEST  NOT AVAILABLE␏FIWOULD YOU LIKE␏GFANOTHER TRANSACTION?␏I8YES -- >␏L9NO -- >','Sin Descripcion'),
(126,'0870','320','␌␏@KCOPIN ATM␏CAYOUR TRANSACTION IS INCOMPLETE␏DEAND HAS  BEEN CANCELED␏FIWOULD YOU LIKE␏GFANOTHER TRANSACTION?␏I8YES -- >␏L9NO -- >','Sin Descripcion'),
(127,'0870','321','␌␏@KCOPIN ATM␏CDYOU ARE RUN  OUT OF TIME␏FIWOULD YOU LIKE␏GFANOTHER TRANSACTION?␏I8YES -- >␏L9NO -- >','Sin Descripcion'),
(128,'0870','322','␌␏@KCOPIN ATM␏CGYOU  HAVE CANCELED␏DFTHE LAST TRANSACTION␏FIWOULD YOU LIKE␏GFANOTHER TRANSACTION?␏I8YES -- >␏L9NO -- >','Sin Descripcion'),
(129,'0870','330','␌␏@KCOPIN ATM␏BEPLEASE TAKE  YOUR CASH␏DI␎L00','Sin Descripcion'),
(130,'0870','331','␌␏@KCOPIN ATM␏CJPLEASE  TAKE␏DEYOUR CARD  AND RECEIPT␏HETHANK YOU FOR VISITING␏IJCOPIN KASSA','Sin Descripcion'),
(131,'0870','332','␌␏@KCOPIN ATM␏CEPLEASE TAKE  YOUR CARD␏HETHANK YOU FOR VISITING␏IJCOPIN KASSA','Sin Descripcion'),
(132,'0870','333','␌␏@KCOPIN ATM␏CCSORRY, FOR SECURITY REASON␏DBYOUR CARD  HAS BEEN RETAINED␏HETHANK YOU FOR VISITING␏IJCOPIN KASSA␏LBPLEASE CONTACT  COPIN ADMIN    CORP␏MACUSTOMER SERVICE: BANKING HOUR␏NDOR ATM HOTLINE: 24 HOURS␏ODPHONE: 83703530- 7319326','Sin Descripcion'),
(133,'0870','334','␌␏@KCOPIN ATM␏CDADMINISTRATIVE  FUNCTION␏DJIS  COMPLETE␏HEPLEASE TAKE  YOUR CARD','Sin Descripcion'),
(134,'0870','335','␌␏@KCOPIN ATM␏CDADMINISTRATIVE  FUNCTION␏DJIS  COMPLETE␏HCSORRY, FOR SECURITY REASON␏IBYOUR CARD  HAS BEEN RETAINED␏LBPLEASE CONTACT  COPIN ADMIN    CORP␏MACUSTOMER SERVICE: BANKING HOUR␏NDOR ATM HOTLINE: 24 HOURS␏ODPHONE: 83703530- 7319326','Sin Descripcion'),
(135,'0870','350','␌␏@KCOPIN ATM␏CHYOU ENTERED YOUR␏DAPERSONAL IDENTIFICATION NUMBER␏EJINCORRECTLY␏HEPLEASE TAKE  YOUR CARD','Sin Descripcion'),
(136,'0870','351','␌␏@KCOPIN ATM␏CHYOU ENTERED YOUR␏DAPERSONAL IDENTIFICATION NUMBER␏EJINCORRECTLY␏HCSORRY, FOR SECURITY REASON␏IBYOUR CARD  HAS BEEN RETAINED␏LBPLEASE CONTACT  COPIN ADMIN    CORP␏MACUSTOMER SERVICE: BANKING HOUR␏NDOR ATM HOTLINE: 24 HOURS␏ODPHONE: 230','Sin Descripcion'),
(137,'0870','352','␌␏@KCOPIN ATM␏CEYOUR CARD  HAS EXPIRED␏HJPLEASE  TAKE␏IEYOUR CARD  AND RECEIPT','Sin Descripcion'),
(138,'0870','353','␌␏@KCOPIN ATM␏CEYOUR CARD  HAS EXPIRED␏HEPLEASE TAKE  YOUR CARD','Sin Descripcion'),
(139,'0870','354','␌␏@KCOPIN ATM␏CEYOUR CARD  HAS EXPIRED␏HCSORRY, FOR SECURITY REASON␏IBYOUR CARD  HAS BEEN RETAINED␏LBPLEASE CONTACT  COPIN    ATM␏MACUSTOMER SERVICE: BANKING HOUR␏NDOR ATM HOTLINE: 24 HOURS␏ODPHONE: 2300763 - 2300785','Sin Descripcion'),
(140,'0870','355','␌␏@KCOPIN ATM␏CAYOUR CARD CAN NOT BE PROCESSED␏HJPLEASE  TAKE␏IEYOUR CARD  AND RECEIPT','Sin Descripcion'),
(141,'0870','356','␌␏@KCOPIN ATM␏CAYOUR CARD CAN NOT BE PROCESSED␏HEPLEASE TAKE  YOUR CARD','Sin Descripcion'),
(142,'0870','357','␌␏@KCOPIN ATM␏CAYOUR CARD CAN NOT BE PROCESSED␏HCSORRY, FOR SECURITY REASON␏IBYOUR CARD  HAS BEEN RETAINED␏LBPLEASE CONTACT  COPIN    ATM␏MACUSTOMER SERVICE: BANKING HOUR␏NDOR ATM HOTLINE: 24 HOURS␏ODPHONE: 2300763 - 2300785','Sin Descripcion'),
(143,'0870','358','␌␏@KCOPIN ATM␏CCYOU ARE RESTRICTED ON THIS␏DETRANSACTION OR ACCOUNT␏HJPLEASE  TAKE␏IEYOUR CARD  AND RECEIPT','Sin Descripcion'),
(144,'0870','359','␌␏@KCOPIN ATM␏CCYOU ARE RESTRICTED ON THIS␏DETRANSACTION OR ACCOUNT␏HEPLEASE TAKE  YOUR CARD','Sin Descripcion'),
(145,'0870','360','␌␏@KCOPIN ATM␏CCYOU ARE RESTRICTED ON THIS␏DETRANSACTION OR ACCOUNT␏HCSORRY, FOR SECURITY REASON␏IBYOUR CARD  HAS BEEN RETAINED␏LBPLEASE CONTACT  COPIN ADMIN    CORP␏MACUSTOMER SERVICE: BANKING HOUR␏NDOR ATM HOTLINE: 24 HOURS␏ODPHONE: 2300763 - 23007','Sin Descripcion'),
(146,'0870','361','␌␏@KCOPIN ATM␏CAUNABLE TO COMPLETE TRANSACTION␏DDPOSSIBLE EQUIPMENT ERROR␏HJPLEASE  TAKE␏IEYOUR CARD  AND RECEIPT','Sin Descripcion'),
(147,'0870','362','␌␏@KCOPIN ATM␏CAUNABLE TO COMPLETE TRANSACTION␏DDPOSSIBLE EQUIPMENT ERROR␏HEPLEASE TAKE  YOUR CARD','Sin Descripcion'),
(148,'0870','363','␌␏@KCOPIN ATM␏CAUNABLE TO COMPLETE TRANSACTION␏DDPOSSIBLE EQUIPMENT ERROR␏HCSORRY, FOR SECURITY REASON␏IBYOUR CARD  HAS BEEN RETAINED␏LBPLEASE CONTACT  COPIN    ATM␏MACUSTOMER SERVICE: BANKING HOUR␏NDOR ATM HOTLINE: 24 HOURS␏ODPHONE: 2300763 - 230078','Sin Descripcion'),
(149,'0870','380','␌␏@KCOPIN ATM␏CDYOU ARE RUN  OUT OF TIME␏HJPLEASE  TAKE␏IEYOUR CARD  AND RECEIPT','Sin Descripcion'),
(150,'0870','381','␌␏@KCOPIN ATM␏CDYOU ARE RUN  OUT OF TIME␏HEPLEASE TAKE  YOUR CARD','Sin Descripcion'),
(151,'0870','382','␌␏@KCOPIN ATM␏CDYOU ARE RUN  OUT OF TIME␏HCSORRY, FOR SECURITY REASON␏IBYOUR CARD  HAS BEEN RETAINED␏LBPLEASE CONTACT  COPIN    ATM␏MACUSTOMER SERVICE: BANKING HOUR␏NDOR ATM HOTLINE: 24 HOURS␏ODPHONE: 2300763 - 2300785','Sin Descripcion'),
(152,'0870','383','␌␏@KCOPIN ATM␏CGYOU  HAVE CANCELED␏DFTHE LAST TRANSACTION␏HJPLEASE  TAKE␏IEYOUR CARD  AND RECEIPT','Sin Descripcion'),
(153,'0870','384','␌␏@KCOPIN ATM␏CGYOU  HAVE CANCELED␏DFTHE LAST TRANSACTION␏HEPLEASE TAKE  YOUR CARD','Sin Descripcion'),
(154,'0870','385','␌␏@KCOPIN ATM␏CGYOU  HAVE CANCELED␏DFTHE LAST TRANSACTION␏HCSORRY, FOR SECURITY REASON␏IBYOUR CARD  HAS BEEN RETAINED␏LBPLEASE CONTACT  COPIN    ATM␏MACUSTOMER SERVICE: BANKING HOUR␏NDOR ATM HOTLINE: 24 HOURS␏ODPHONE: 2300763 - 2300785','Sin Descripcion'),
(155,'0870','390','␌␏@KCOPIN ATM␏CHYOUR TRANSACTION␏EGIS BEING PROCESSED␏HJPLEASE  WAIT','Sin Descripcion'),
(156,'0870','392','␌␏@KCOPIN ATM␏CC  FIRST ENTRY PIN IS NOT     ␏DEEQUAL TO SECOND ENTRY ␏FIWOULD YOU LIKE␏GFANOTHER TRANSACTION?␏I8YES -- >␏L9NO -- >','Sin Descripcion'),
(157,'0870','400','␌␏@KCOPIN ATM␏BFANDA KEHABISAN WAKTU␏CAAPAKAH ANDA  MEMBUTUHKAN WAKTU␏DJLEBIH  LAMA?␏F9YA -- >␏I6TIDAK -- >','Sin Descripcion'),
(158,'0870','450','␌␏@KCOPIN ATM␏BLMASUKKAN␏C@NOMOR IDENTIFIKASI  PRIBADI ANDA␏FFSETELAH SELESAI TEKAN -- >␏O@MENGEMBALIKAN KARTU TEKAN CANCEL␏I0','Sin Descripcion'),
(159,'0870','451','␌␏@KCOPIN ATM␏BAANDA MEMASUKKAN PIN YANG SALAH␏CHMASUKKAN KEMBALI␏D@NOMOR IDENTIFIKASI  PRIBADI ANDA␏FFSETELAH SELESAI TEKAN -- >␏O@MENGEMBALIKAN KARTU TEKAN CANCEL␏I0','Sin Descripcion'),
(160,'0870','500','␌␏@KCOPIN ATM␏BBPILIH JENIS TRANSAKSI DENGAN␏CCMENEKAN TOMBOL YANG SESUAI␏EEINFORMASI␏F@< -- SALDO        PENARIKAN -- >␏I0   TRANSFER -- >␏L@                    UBAH PIN -->␏M@                KHUSUS ON US␏O@MEMBATALKAN TRANS.  TEKAN CANCEL','Sin Descripcion'),
(161,'0870','510','␌␏@KCOPIN ATM␏BKPENARIKAN␏DCPILIH JENIS  REKENING ANDA␏F3TABUNGAN -- >␏I7GIRO -- >␏LOKARTU KREDIT -- >␏O@MEMBATALKAN TRANS.  TEKAN CANCEL','Sin Descripcion'),
(162,'0870','511','␌␏@KCOPIN ATM␏BKPENARIKAN␏DDMASUKKAN NILAI TRANSAKSI␏ECDALAM KELIPATAN RP. 20.000␏I6BENAR -- >␏L6SALAH -- >␏O@MEMBATALKAN TRANS.  TEKAN CANCEL','Sin Descripcion'),
(163,'0870','512','␌␏@KCOPIN ATM␏BKPENARIKAN␏C@ANDA MEMASUKKAN NILAI YANG SALAH␏D@MASUKKAN KEMBALI NILAI TRANSAKSI␏ECDALAM KELIPATAN RP. 20.000␏I6BENAR -- >␏L6SALAH -- >␏O@MEMBATALKAN TRANS.  TEKAN CANCEL','Sin Descripcion'),
(164,'0870','513','␌␏@KCOPIN ATM␏BKPENARIKAN␏CESALDO ANDA TIDAK CUKUP␏D@MASUKKAN KEMBALI NILAI TRANSAKSI␏ECDALAM KELIPATAN RP. 20.000␏I6BENAR -- >␏L6SALAH -- >␏O@MEMBATALKAN TRANS.  TEKAN CANCEL','Sin Descripcion'),
(165,'0870','514','␌␏@KCOPIN ATM␏BKPENARIKAN␏CBNILAI MELAMPAUI LIMIT HARIAN␏D@MASUKKAN KEMBALI NILAI TRANSAKSI␏ECDALAM KELIPATAN RP. 20.000␏I6BENAR -- >␏L6SALAH -- >␏O@MEMBATALKAN TRANS.  TEKAN CANCEL','Sin Descripcion'),
(166,'0870','515','␏G3RP. *.***.**0','Sin Descripcion'),
(167,'0870','518','␌␏@KCOPIN ATM␏BKPENARIKAN␏DBPILIH REKENING TABUNGAN ANDA␏O@MEMBATALKAN TRANS.  TEKAN CANCEL','Sin Descripcion'),
(168,'0870','519','␌␏@KCOPIN ATM␏BKPENARIKAN␏DDPILIH REKENING GIRO ANDA␏O@MEMBATALKAN TRANS.  TEKAN CANCEL','Sin Descripcion'),
(169,'0870','530','␌␏@KCOPIN ATM␏BJPAKET  TUNAI␏DCPILIH JENIS  REKENING ANDA␏F3TABUNGAN -- >␏I7GIRO -- >␏LOKARTU KREDIT -- >␏O@MEMBATALKAN TRANS.  TEKAN CANCEL','Sin Descripcion'),
(170,'0870','531','␌␏@KCOPIN ATM␏BJPAKET  TUNAI␏DFPILIH NILAI (RUPIAH)␏F@< -- 100.000         60.000 -- >␏I@< -- 400.000        200.000 -- >␏L@< -- LAINNYA        600.000 -- >␏O@MEMBATALKAN TRANS.  TEKAN CANCEL','Sin Descripcion'),
(171,'0870','532','␌␏@KCOPIN ATM␏BJPAKET  TUNAI␏CESALDO ANDA TIDAK CUKUP␏DBPILIH KEMBALI NILAI (RUPIAH)␏F@< -- 100.000         60.000 -- >␏I@< -- 400.000        200.000 -- >␏L@< -- LAINNYA        600.000 -- >␏O@TO VOID TRANSACTION PRESS CANCEL','Sin Descripcion'),
(172,'0870','533','␌␏@KCOPIN ATM␏BJPAKET  TUNAI␏CBNILAI MELAMPAUI LIMIT HARIAN␏DBPILIH KEMBALI NILAI (RUPIAH)␏F@< -- 100.000         60.000 -- >␏I@< -- 400.000        200.000 -- >␏L@< -- LAINNYA        600.000 -- >␏O@TO VOID TRANSACTION PRESS CANCEL','Sin Descripcion'),
(173,'0870','538','␌␏@KCOPIN ATM␏BJPAKET  TUNAI␏DBPILIH REKENING TABUNGAN ANDA␏O@MEMBATALKAN TRANS.  TEKAN CANCEL','Sin Descripcion'),
(174,'0870','539','␌␏@KCOPIN ATM␏BJPAKET  TUNAI␏DDPILIH REKENING GIRO ANDA␏O@MEMBATALKAN TRANS.  TEKAN CANCEL','Sin Descripcion'),
(175,'0870','550','␌␏@KCOPIN ATM␏BLTRANSFER␏DCPILIH JENIS  REKENING ANDA␏FNDARI TABUNGAN -- >␏I2DARI GIRO -- >␏O@MEMBATALKAN TRANS.  TEKAN CANCEL','Sin Descripcion'),
(176,'0870','551','␌␏@KCOPIN ATM␏BLTRANSFER␏DCPILIH JENIS  REKENING ANDA␏F0KE TABUNGAN -- >␏I4KE GIRO -- >␏O@MEMBATALKAN TRANS.  TEKAN CANCEL','Sin Descripcion'),
(177,'0870','552','␌␏@KCOPIN ATM␏BLTRANSFER␏DDMASUKKAN NILAI TRANSAKSI␏I6BENAR -- >␏L6SALAH -- >␏O@MEMBATALKAN TRANS.  TEKAN CANCEL','Sin Descripcion'),
(178,'0870','553','␌␏@KCOPIN ATM␏BLTRANSFER␏CESALDO ANDA TIDAK CUKUP␏D@MASUKKAN KEMBALI NILAI TRANSAKSI␏I6BENAR -- >␏L6SALAH -- >␏O@MEMBATALKAN TRANS.  TEKAN CANCEL','Sin Descripcion'),
(179,'0870','554','␌␏@KCOPIN ATM␏BLTRANSFER␏CBNILAI MELAMPAUI LIMIT HARIAN␏D@MASUKKAN KEMBALI NILAI TRANSAKSI␏I6BENAR -- >␏L6SALAH -- >␏O@MEMBATALKAN TRANS.  TEKAN CANCEL','Sin Descripcion'),
(180,'0870','555','␏G3RP. *.***.**0','Sin Descripcion'),
(181,'0870','556','␌␏@KCOPIN ATM␏BLTRANSFER␏CL( DARI )␏DBPILIH REKENING TABUNGAN ANDA␏O@MEMBATALKAN TRANS.  TEKAN CANCEL','Sin Descripcion'),
(182,'0870','557','␌␏@KCOPIN ATM␏BLTRANSFER␏CL( DARI )␏DDPILIH REKENING GIRO ANDA␏O@MEMBATALKAN TRANS.  TEKAN CANCEL','Sin Descripcion'),
(183,'0870','558','␌␏@KCOPIN ATM␏BLTRANSFER␏CM( KE )␏DBPILIH REKENING TABUNGAN ANDA␏O@MEMBATALKAN TRANS.  TEKAN CANCEL','Sin Descripcion'),
(184,'0870','559','␌␏@KCOPIN ATM␏BLTRANSFER␏CM( KE )␏DDPILIH REKENING GIRO ANDA␏O@MEMBATALKAN TRANS.  TEKAN CANCEL','Sin Descripcion'),
(185,'0870','570','␌␏@KCOPIN ATM␏BHINFORMASI  SALDO␏DCPILIH JENIS  REKENING ANDA␏F3TABUNGAN -- >␏I7GIRO -- >␏LOKARTU KREDIT -- >␏O@MEMBATALKAN TRANS.  TEKAN CANCEL','Sin Descripcion'),
(186,'0870','571','␌␏@KCOPIN ATM␏BHINFORMASI  SALDO␏D@BUKU      RP.␏F@EFEKTIF   RP.␏I6CETAK -- >␏L4SELESAI -- >␏O@MEMBATALKAN TRANS.  TEKAN CANCEL␎172','Sin Descripcion'),
(187,'0870','572','','Sin Descripcion'),
(188,'0870','578','␌␏@KCOPIN ATM␏BHINFORMASI  SALDO␏DBPILIH REKENING TABUNGAN ANDA␏O@MEMBATALKAN TRANS.  TEKAN CANCEL','Sin Descripcion'),
(189,'0870','579','␌␏@KCOPIN ATM␏BHINFORMASI  SALDO␏DDPILIH REKENING GIRO ANDA␏O@MEMBATALKAN TRANS.  TEKAN CANCEL','Sin Descripcion'),
(190,'0870','600','␌␏@KCOPIN ATM␏BKRUBAH  PIN␏DEMASUKKAN PIN BARU ANDA␏FFSETELAH SELESAI TEKAN -- >␏LD*** MASUKAN  PERTAMA ***␏O@MEMBATALKAN TRANS.  TEKAN CANCEL␏I0','Sin Descripcion'),
(191,'0870','601','␌␏@KCOPIN ATM␏BKRUBAH  PIN␏DEMASUKKAN PIN BARU ANDA␏FFSETELAH SELESAI TEKAN -- >␏LE*** MASUKAN  KEDUA ***␏O@MEMBATALKAN TRANS.  TEKAN CANCEL␏I0','Sin Descripcion'),
(192,'0870','602','␌␏@KCOPIN ATM␏BKRUBAH  PIN␏CFPIN BARU TIDAK COCOK␏DAMASUKKAN KEMBALI PIN BARU ANDA␏FFSETELAH SELESAI TEKAN -- >␏LD*** MASUKAN  PERTAMA ***␏O@MEMBATALKAN TRANS.  TEKAN CANCEL␏I0','Sin Descripcion'),
(193,'0870','603','␌␏@KCOPIN ATM␏BKRUBAH  PIN␏CFPIN BARU TIDAK COCOK␏DAMASUKKAN KEMBALI PIN BARU ANDA␏FFSETELAH SELESAI TEKAN -- >␏LE*** MASUKAN  KEDUA ***␏O@MEMBATALKAN TRANS.  TEKAN CANCEL␏I0','Sin Descripcion'),
(194,'0870','680','␌␏@KCOPIN ATM␏B@PILIH FUNGSI ADMINISTRASI DENGAN␏CCMENEKAN TOMBOL YANG SESUAI␏FIBALANCING TERMINAL -- >␏IJPENYESUAIAN TUNAI -- >␏LGCETAK SALDO TERMINAL -- >␏O@MEMBATALKAN TRANS.  TEKAN CANCEL','Sin Descripcion'),
(195,'0870','681','␌␏@KCOPIN ATM␏BGBALANCING TERMINAL␏DEPILIH  JENIS BALANCING␏FMSTANDARD TUNAI -- >␏INCURRENT TUNAI -- >␏O@MEMBATALKAN TRANS.  TEKAN CANCEL','Sin Descripcion'),
(196,'0870','682','␌␏@KCOPIN ATM␏BGPENYESUAIAN  TUNAI␏DAPILIH JENIS  PENYESUAIAN TUNAI␏FFMENAIKKAN JUMLAH UANG -- >␏IEMENURUNKAN JUMLAH UANG -- >␏O@MEMBATALKAN TRANS.  TEKAN CANCEL','Sin Descripcion'),
(197,'0870','683','␌␏@KCOPIN ATM␏BGPENYESUAIAN  TUNAI␏DHPILIH DENOMINASI␏F@< -- 20.000          50.000 -- >␏I@< -- 00.000          10.000 -- >␏O@MEMBATALKAN TRANS.  TEKAN CANCEL','Sin Descripcion'),
(198,'0870','684','␌␏@KCOPIN ATM␏BGPENYESUAIAN  TUNAI␏CHDENOMINASI SALAH␏DDPILIH KEMBALI DENOMINASI␏F@< -- 20.000          50.000 -- >␏I@< -- 00.000          10.000 -- >␏O@MEMBATALKAN TRANS.  TEKAN CANCEL','Sin Descripcion'),
(199,'0870','685','␌␏@KCOPIN ATM␏BGPENYESUAIAN  TUNAI␏DDMASUKKAN NILAI TRANSAKSI␏                            ␏I6BENAR -- >␏L6SALAH -- >␏O@MEMBATALKAN TRANS.  TEKAN CANCEL','Sin Descripcion'),
(200,'0870','686','␌␏@KCOPIN ATM␏BGPENYESUAIAN  TUNAI␏C@ANDA MEMASUKKAN NILAI YANG SALAH␏D@MASUKKAN KEMBALI NILAI TRANSAKSI␏EC                          ␏I6BENAR -- >␏L6SALAH -- >␏O@MEMBATALKAN TRANS.  TEKAN CANCEL','Sin Descripcion'),
(201,'0870','687','␏G2RP. **.***.**0','Sin Descripcion'),
(202,'0870','700','␌␏@KCOPIN ATM␏CBTRANSAKSI ANDA TELAH SELESAI␏FDAPAKAH  ANDA MENGHENDAKI␏GFTRANSAKSI YANG LAIN?␏I9YA -- >␏L6TIDAK -- >','Sin Descripcion'),
(203,'0870','701','␌␏@KCOPIN ATM␏CFFUNGSI  ADMINISTRASI␏DITELAH  SELESAI␏FDAPAKAH  ANDA MENGHENDAKI␏GFTRANSAKSI YANG LAIN?␏I9YA -- >␏L6TIDAK -- >','Sin Descripcion'),
(204,'0870','702','␌␏@KCOPIN ATM␏CGBALANCING TERMINAL␏DETELAH PERNAH DILAKUKAN␏FDAPAKAH  ANDA MENGHENDAKI␏GFTRANSAKSI YANG LAIN?␏I9YA -- >␏L6TIDAK -- >','Sin Descripcion'),
(205,'0870','703','␌␏@KCOPIN ATM␏CCKARTU ANDA TIDAK MEMPUNYAI␏DINOMOR REKENING␏FDAPAKAH  ANDA MENGHENDAKI␏GFTRANSAKSI YANG LAIN?␏I9YA -- >␏L6TIDAK -- >','Sin Descripcion'),
(206,'0870','704','␌␏@KCOPIN ATM␏CCANDA TIDAK  BERWENANG ATAS␏DBTRANSAKSI ATAU  REKENING INI␏FDAPAKAH  ANDA MENGHENDAKI␏GFTRANSAKSI YANG LAIN?␏I9YA -- >␏L6TIDAK -- >','Sin Descripcion'),
(207,'0870','705','␌␏@KCOPIN ATM␏C@TIDAK BERHASIL MENGELUARKAN UANG␏DBKEMUNGKINAN  KERUSAKAN MESIN␏FDAPAKAH  ANDA MENGHENDAKI␏GFTRANSAKSI YANG LAIN?␏I9YA -- >␏L6TIDAK -- >','Sin Descripcion'),
(208,'0870','706','␌␏@KCOPIN ATM␏CDKARENA  KETERBATASAN ATM␏D@HANYA 40 LEMBAR YANG DIKELUARKAN␏FDAPAKAH  ANDA MENGHENDAKI␏GFTRANSAKSI YANG LAIN?␏I9YA -- >␏L6TIDAK -- >','Sin Descripcion'),
(209,'0870','707','␌␏@KCOPIN ATM␏CATRANSFER TIDAK DAPAT DILAKUKAN␏DDPADA REKENING  YANG SAMA␏FDAPAKAH  ANDA MENGHENDAKI␏GFTRANSAKSI YANG LAIN?␏I9YA -- >␏L6TIDAK -- >','Sin Descripcion'),
(210,'0870','708','␌␏@KCOPIN ATM␏CBKAMI SEDANG MELAKUKAN PROSES␏D@PERMINTAAN TIDAK  DAPAT DILAYANI␏FDAPAKAH  ANDA MENGHENDAKI␏GFTRANSAKSI YANG LAIN?␏I9YA -- >␏L6TIDAK -- >','Sin Descripcion'),
(211,'0870','720','␌␏@KCOPIN ATM␏CATRANSAKSI ANDA  TIDAK BERHASIL␏DFDAN TELAH DIBATALKAN␏FDAPAKAH  ANDA MENGHENDAKI␏GFTRANSAKSI YANG LAIN?␏I9YA -- >␏L6TIDAK -- >','Sin Descripcion'),
(212,'0870','721','␌␏@KCOPIN ATM␏CFANDA KEHABISAN WAKTU␏FDAPAKAH  ANDA MENGHENDAKI␏GFTRANSAKSI YANG LAIN?␏I9YA -- >␏L6TIDAK -- >','Sin Descripcion'),
(213,'0870','722','␌␏@KCOPIN ATM␏CEANDA TELAH MEMBATALKAN␏DGTRANSAKSI TERAKHIR␏FDAPAKAH  ANDA MENGHENDAKI␏GFTRANSAKSI YANG LAIN?␏I9YA -- >␏L6TIDAK -- >','Sin Descripcion'),
(214,'0870','730','␌␏@KCOPIN ATM␏BDSILAHKAN AMBIL UANG ANDA␏DI␎L00','Sin Descripcion'),
(215,'0870','731','␌␏@KCOPIN ATM␏CISILAHKAN AMBIL␏DAKARTU DAN  NOTA TRANSAKSI ANDA␏HGTERIMA  KASIH ATAS␏IGKUNJUNGAN  ANDA KE␏JJCOPIN KASSA','Sin Descripcion'),
(216,'0870','732','␌␏@KCOPIN ATM␏CCSILAHKAN AMBIL  KARTU ANDA␏HGTERIMA  KASIH ATAS␏IGKUNJUNGAN  ANDA KE␏JJCOPIN KASSA','Sin Descripcion'),
(217,'0870','733','␌␏@KCOPIN ATM␏CFMAAF,  DEMI KEAMANAN␏DDKARTU ANDA TELAH DITAHAN␏HGTERIMA  KASIH ATAS␏IGKUNJUNGAN  ANDA KE␏JJCOPIN KASSA␏LASILAHKAN HUBUNGI  COPIN ADMIN    CORP␏MBCUSTOMER SERVICE:  JAM KERJA␏NDATAU HOTLINE ATM: 24 JAM␏OCTELEPON: 83703530- 7319326','Sin Descripcion'),
(218,'0870','734','␌␏@KCOPIN ATM␏CFFUNGSI  ADMINISTRASI␏DITELAH  SELESAI␏HCSILAHKAN AMBIL  KARTU ANDA','Sin Descripcion'),
(219,'0870','735','␌␏@KCOPIN ATM␏CFFUNGSI  ADMINISTRASI␏DITELAH  SELESAI␏HFMAAF,  DEMI KEAMANAN␏IDKARTU ANDA TELAH DITAHAN␏LASILAHKAN HUBUNGI  COPIN    ATM␏MBCUSTOMER SERVICE:  JAM KERJA␏NDATAU HOTLINE ATM: 24 JAM␏OCTELEPON: 83703530- 7319326','Sin Descripcion'),
(220,'0870','750','␌␏@KCOPIN ATM␏CHANDA  MEMASUKKAN␏DCNOMOR IDENTIFIKASI PRIBADI␏EMSALAH␏HCSILAHKAN AMBIL  KARTU ANDA','Sin Descripcion'),
(221,'0870','751','␌␏@KCOPIN ATM␏CHANDA  MEMASUKKAN␏DCNOMOR IDENTIFIKASI PRIBADI␏EMSALAH␏HFMAAF,  DEMI KEAMANAN␏IDKARTU ANDA TELAH DITAHAN␏LASILAHKAN HUBUNGI  COPIN ADMIN    CORP␏MBCUSTOMER SERVICE:  JAM KERJA␏NDATAU HOTLINE ATM: 24 JAM␏OCTELEPON: 2300763 - 2300785','Sin Descripcion'),
(222,'0870','752','␌␏@KCOPIN ATM␏CBKARTU ANDA  TELAH KADALUARSA␏HISILAHKAN AMBIL␏IAKARTU DAN  NOTA TRANSAKSI ANDA','Sin Descripcion'),
(223,'0870','753','␌␏@KCOPIN ATM␏CBKARTU ANDA  TELAH KADALUARSA␏HCSILAHKAN AMBIL  KARTU ANDA','Sin Descripcion'),
(224,'0870','754','␌␏@KCOPIN ATM␏CBKARTU ANDA  TELAH KADALUARSA␏HFMAAF,  DEMI KEAMANAN␏IDKARTU ANDA TELAH DITAHAN␏LASILAHKAN HUBUNGI  COPIN ADMIN    CORP␏MBCUSTOMER SERVICE:  JAM KERJA␏NDATAU HOTLINE ATM: 24 JAM␏OCTELEPON: 2300763 - 2300785','Sin Descripcion'),
(225,'0870','755','␌␏@KCOPIN ATM␏C@KARTU ANDA  TIDAK DAPAT DIPROSES␏HISILAHKAN AMBIL␏IAKARTU DAN  NOTA TRANSAKSI ANDA','Sin Descripcion'),
(226,'0870','756','␌␏@KCOPIN ATM␏C@KARTU ANDA  TIDAK DAPAT DIPROSES␏HCSILAHKAN AMBIL  KARTU ANDA','Sin Descripcion'),
(227,'0870','757','␌␏@KCOPIN ATM␏C@KARTU ANDA  TIDAK DAPAT DIPROSES␏HFMAAF,  DEMI KEAMANAN␏IDKARTU ANDA TELAH DITAHAN␏LASILAHKAN HUBUNGI  COPIN    ATM␏MBCUSTOMER SERVICE:  JAM KERJA␏NDATAU HOTLINE ATM: 24 JAM␏OCTELEPON: 2300763 - 2300785','Sin Descripcion'),
(228,'0870','758','␌␏@KCOPIN ATM␏CCANDA TIDAK  BERWENANG ATAS␏DBTRANSAKSI ATAU  REKENING INI␏HISILAHKAN AMBIL␏IAKARTU DAN  NOTA TRANSAKSI ANDA','Sin Descripcion'),
(229,'0870','759','␌␏@KCOPIN ATM␏CCANDA TIDAK  BERWENANG ATAS␏DBTRANSAKSI ATAU  REKENING INI␏HCSILAHKAN AMBIL  KARTU ANDA','Sin Descripcion'),
(230,'0870','760','␌␏@KCOPIN ATM␏CCANDA TIDAK  BERWENANG ATAS␏DBTRANSAKSI ATAU  REKENING INI␏HFMAAF,  DEMI KEAMANAN␏IDKARTU ANDA TELAH DITAHAN␏LASILAHKAN HUBUNGI  COPIN    ATM␏MBCUSTOMER SERVICE:  JAM KERJA␏NDATAU HOTLINE ATM: 24 JAM␏OCTELEPON: 2300763 - 2300785','Sin Descripcion'),
(231,'0870','761','␌␏@KCOPIN ATM␏CITIDAK BERHASIL␏DDMENYELESAIKAN  TRANSAKSI␏EEKEMUNGKINAN  KERUSAKAN␏FHPERALATAN  MESIN␏HISILAHKAN AMBIL␏IAKARTU DAN  NOTA TRANSAKSI ANDA','Sin Descripcion'),
(232,'0870','762','␌␏@KCOPIN ATM␏CITIDAK BERHASIL␏DDMENYELESAIKAN  TRANSAKSI␏EEKEMUNGKINAN  KERUSAKAN␏FHPERALATAN  MESIN␏HCSILAHKAN AMBIL  KARTU ANDA','Sin Descripcion'),
(233,'0870','763','␌␏@KCOPIN ATM␏CITIDAK BERHASIL␏DDMENYELESAIKAN  TRANSAKSI␏EEKEMUNGKINAN  KERUSAKAN␏FHPERALATAN  MESIN␏HFMAAF,  DEMI KEAMANAN␏IDKARTU ANDA TELAH DITAHAN␏LASILAHKAN HUBUNGI  COPIN ADMIN    CORP␏MBCUSTOMER SERVICE:  JAM KERJA␏NDATAU HOTLINE ATM: 24 JAM␏','Sin Descripcion'),
(234,'0870','780','␌␏@KCOPIN ATM␏CFANDA KEHABISAN WAKTU␏HISILAHKAN AMBIL␏IAKARTU DAN  NOTA TRANSAKSI ANDA','Sin Descripcion'),
(235,'0870','781','␌␏@KCOPIN ATM␏CFANDA KEHABISAN WAKTU␏HCSILAHKAN AMBIL  KARTU ANDA','Sin Descripcion'),
(236,'0870','782','␌␏@KCOPIN ATM␏CFANDA KEHABISAN WAKTU␏HFMAAF,  DEMI KEAMANAN␏IDKARTU ANDA TELAH DITAHAN␏LASILAHKAN HUBUNGI  COPIN ADMIN    CORP␏MBCUSTOMER SERVICE:  JAM KERJA␏NDATAU HOTLINE ATM: 24 JAM␏OCTELEPON: 2300763 - 2300785','Sin Descripcion'),
(237,'0870','783','␌␏@KCOPIN ATM␏CEANDA TELAH MEMBATALKAN␏DGTRANSAKSI TERAKHIR␏HISILAHKAN AMBIL␏IAKARTU DAN  NOTA TRANSAKSI ANDA','Sin Descripcion'),
(238,'0870','784','␌␏@KCOPIN ATM␏CEANDA TELAH MEMBATALKAN␏DGTRANSAKSI TERAKHIR␏HCSILAHKAN AMBIL  KARTU ANDA','Sin Descripcion'),
(239,'0870','785','␌␏@KCOPIN ATM␏CEANDA TELAH MEMBATALKAN␏DGTRANSAKSI TERAKHIR␏HFMAAF,  DEMI KEAMANAN␏IDKARTU ANDA TELAH DITAHAN␏LASILAHKAN HUBUNGI  COPIN    ATM␏MBCUSTOMER SERVICE:  JAM KERJA␏NDATAU HOTLINE ATM: 24 JAM␏OCTELEPON: 2300763 - 2300785','Sin Descripcion'),
(240,'0870','790','␌␏@KCOPIN ATM␏CITRANSAKSI ANDA␏EHSEDANG  DIPROSES␏HHSILAHKAN  TUNGGU','Sin Descripcion'),
(241,'0870','792','␌␏@KCOPIN ATM␏C@PIN MASUKKAN PERTAMA TIDAK SAMA␏DBDENGAN PIN MASUKKAN KEDUA ␏FDAPAKAH  ANDA MENGHENDAKI␏GFTRANSAKSI YANG LAIN?␏I9YA -- >␏L6TIDAK -- >','Sin Descripcion');

/*Table structure for table `states` */

DROP TABLE IF EXISTS `states`;

CREATE TABLE `states` (
  `std_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `std_config_id` varchar(255) DEFAULT NULL,
  `std_number` varchar(255) DEFAULT NULL,
  `std_desc` varchar(255) DEFAULT NULL,
  `std_s1` varchar(255) DEFAULT NULL,
  `std_s2` varchar(255) DEFAULT NULL,
  `std_s3` varchar(255) DEFAULT NULL,
  `std_s4` varchar(255) DEFAULT NULL,
  `std_s5` varchar(255) DEFAULT NULL,
  `std_s6` varchar(255) DEFAULT NULL,
  `std_s7` varchar(255) DEFAULT NULL,
  `std_s8` varchar(255) DEFAULT NULL,
  `std_type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`std_id`),
  UNIQUE KEY `std_uk` (`std_config_id`,`std_number`)
) ENGINE=InnoDB AUTO_INCREMENT=148 DEFAULT CHARSET=utf8mb4;

/*Data for the table `states` */

insert  into `states`(`std_id`,`std_config_id`,`std_number`,`std_desc`,`std_s1`,`std_s2`,`std_s3`,`std_s4`,`std_s5`,`std_s6`,`std_s7`,`std_s8`,`std_type`) values 
(1,'0850','000','Card Read State','010','005','013','002','002','008','001','035','A'),
(2,'0850','005','FIT Switch State','010','015','015','015','020','015','255','255','K'),
(3,'0850','010','Begin ICC Initialisation State','025','055','001','001','000','000','000','000','+'),
(4,'0850','015','Begin ICC Initialisation State','040','055','001','000','000','000','000','000','+'),
(5,'0850','020','Close State','000','000','014','054','000','000','000','000','J'),
(6,'0850','025','Pre-Set Operation Code Buffer State','240','000','002','001','000','000','000','000','D'),
(7,'0850','035','Begin ICC Initialisation State','040','055','000','000','000','000','000','000','+'),
(8,'0850','040','Complete ICC Initialisation State','015','100','050','050','045','045','000','000',','),
(9,'0850','045','Close State','016','000','016','054','000','000','000','000','J'),
(10,'0850','050','Close State','017','000','017','054','000','000','000','000','J'),
(11,'0850','055','Pre-Set Operation Code Buffer State','085','000','003','000','000','000','000','000','D'),
(12,'0850','065','Eight FDK Selection Function State','018','110','080','240','070','111','102','075','Y'),
(13,'0850','070','Extension State','000','001','002','000','000','003','004','000','Z'),
(14,'0850','075','Extension State','000','000','100','000','000','200','300','000','Z'),
(15,'0850','080','Close State','000','000','019','054','000','000','000','000','J'),
(16,'0850','085','FIT Switch State','240','240','240','065','255','065','255','255','K'),
(17,'0850','100','Automatic Language Selection State','120','105','000','000','000','000','000','000','-'),
(18,'0850','105','Eight FDK Selection Function State','018','110','080','120','070','111','102','075','Y'),
(19,'0850','110','Close State','000','000','020','054','000','000','000','000','J'),
(20,'0850','120','Begin ICC Application Selection and Initialisation State','021','125','130','135','000','000','000','000','.'),
(21,'0850','125','Extension State','022','023','024','025','000','000','000','000','Z'),
(22,'0850','130','Extension State','026','004','027','005','000','000','000','000','Z'),
(23,'0850','135','Extension State','110','080','155','145','050','000','000','000','Z'),
(24,'0850','145','Pre-Set Operation Code Buffer State','165','002','000','000','000','001','000','000','D'),
(25,'0850','155','Pre-Set Operation Code Buffer State','165','002','000','000','001','000','000','000','D'),
(26,'0850','165','Complete ICC Application Selection and Initialisation State','015','028','029','170','000','000','000','000','/'),
(27,'0850','170','Extension State','200','255','180','255','045','045','000','000','Z'),
(28,'0850','180','Begin ICC Application Selection and Initialisation State','030','125','130','135','000','000','000','000','.'),
(29,'0850','200','Smart FIT Check State','000','205','000','002','002','002','001','255','k'),
(30,'0850','205','FIT Switch State','220','220','220','220','020','220','255','255','K'),
(31,'0850','220','PIN Entry State','031','110','080','255','235','032','245','003','B'),
(32,'0850','230','Extension State','000','000','012','011','000','000','000','000','Z'),
(33,'0850','235','Close State','033','000','033','054','000','000','000','000','J'),
(34,'0850','240','PIN Entry State','034','110','080','255','235','035','245','003','B'),
(35,'0850','245','FIT Switch State','280','280','260','250','255','250','255','255','K'),
(36,'0850','250','Eight FDK Selection Function State','036','110','080','295','230','023','012','000','Y'),
(37,'0850','260','Eight FDK Selection Function State','037','110','080','290','265','002','006','000','Y'),
(38,'0850','265','Extension State','000','001','002','000','000','000','000','000','Z'),
(39,'0850','280','Eight FDK Selection Function State','038','110','080','290','285','002','014','000','Y'),
(40,'0850','285','Extension State','000','001','002','003','000','000','000','000','Z'),
(41,'0850','290','FDK Switch State','255','250','300','300','255','255','255','255','W'),
(42,'0850','295','FDK Switch State','255','255','335','340','255','255','255','255','W'),
(43,'0850','300','Complete ICC Application Selection and Initialisation State','015','028','029','305','000','000','000','000','/'),
(44,'0850','305','Extension State','310','310','310','310','310','310','310','000','Z'),
(45,'0850','310','Set ICC Transaction Data State','330','000','001','000','000','000','000','000','?'),
(46,'0850','315','Complete ICC Application Selection and Initialisation State','015','028','029','320','000','000','000','000','/'),
(47,'0850','320','Extension State','325','325','325','325','325','325','325','000','Z'),
(48,'0850','325','Set ICC Transaction Data State','330','001','002','030','000','000','000','000','?'),
(49,'0850','330','Transaction Request State','015','350','001','000','001','001','129','345','I'),
(50,'0850','335','Amount Entry State','039','110','080','355','255','255','255','040','F'),
(51,'0850','340','Amount Entry State','042','110','080','375','255','255','255','043','F'),
(52,'0850','345','Extension State','000','000','000','000','000','000','001','000','Z'),
(53,'0850','350','Close State','000','000','016','054','000','000','000','000','J'),
(54,'0850','355','Amount Check State','315','360','000','000','002','001','003','000','G'),
(55,'0850','360','Amount Entry State','041','110','080','355','255','255','255','040','F'),
(56,'0850','375','Amount Check State','385','380','000','000','002','002','003','000','G'),
(57,'0850','380','Amount Entry State','044','110','080','375','255','255','255','043','F'),
(58,'0850','385','Complete ICC Application Selection and Initialisation State','015','028','029','390','000','000','000','000','/'),
(59,'0850','390','Extension State','395','395','395','395','395','395','395','000','Z'),
(60,'0850','395','Set ICC Transaction Data State','330','002','002','030','000','000','000','000','?'),
(61,'0850','400','Eight FDK Selection Function State','045','110','080','440','405','000','012','000','Y'),
(62,'0850','405','Extension State','000','000','000','000','000','000','000','000','Z'),
(63,'0850','420','Eight FDK Selection Function State','045','110','080','430','425','000','012','000','Y'),
(64,'0850','425','Extension State','000','000','000','000','000','000','000','000','Z'),
(65,'0850','430','FDK Switch State','255','255','460','435','255','255','255','255','W'),
(66,'0850','435','Close State','000','000','047','054','000','000','000','000','J'),
(67,'0850','440','FDK Switch State','000','000','445','435','255','255','255','255','W'),
(68,'0850','445','Eight FDK Selection Function State','046','110','080','455','450','000','012','000','Y'),
(69,'0850','450','Extension State','000','000','000','000','000','000','000','000','Z'),
(70,'0850','455','FDK Switch State','255','255','460','120','255','255','255','255','W'),
(71,'0850','460','ICC Re-initialise State','245','245','000','000','000','000','000','000',';'),
(72,'0850','465','Close State','000','000','048','054','000','000','000','000','J'),
(73,'0850','470','Close State','052','000','053','054','000','000','000','000','J'),
(74,'0870','000','Card Read State','010','040','356','002','002','002','001','355','A'),
(75,'0870','030','Four FDK Selection Function State jfrd','030','380','383','255','040','331','255','000','E'),
(76,'0870','040','Eight FDK Selection Function State','040','380','383','050','042','000','003','041','Y'),
(77,'0870','041','Extension State','000','400','000','000','000','000','000','000','Z'),
(78,'0870','042','Extension State','@A@','@B@','@C@','@@@','@@@','@@@','@@@','@@@','Z'),
(79,'0870','050','PIN Entry State','050','380','383','255','255','255','052','003','B'),
(80,'0870','051','PIN Entry State','051','321','322','255','255','255','390','003','B'),
(81,'0870','052','FIT Switch State','053','054','255','255','255','255','255','255','K'),
(82,'0870','053','Pre-Set Operation Code Buffer State','100','001','064','000','000','000','000','000','D'),
(83,'0870','054','Pre-Set Operation Code Buffer State','280','128','000','064','000','000','000','000','D'),
(84,'0870','100','Eight FDK Selection Function State','100','321','322','102','101','001','135','255','Y'),
(85,'0870','101','Extension State','A@@','B@@','C@@','@@@','@@@','@@@','@@@','I@@','Z'),
(86,'0870','102','FDK Switch State','110','150','200','255','255','255','255','170','W'),
(87,'0870','110','Eight FDK Selection Function State','110','321','322','111','172','007','007','255','Y'),
(88,'0870','111','Amount Entry State','111','321','322','255','115','111','255','115','F'),
(89,'0870','112','Amount Entry State','112','321','322','255','115','112','255','115','F'),
(90,'0870','113','Amount Entry State','113','321','322','255','115','113','255','115','F'),
(91,'0870','114','Amount Entry State','114','321','322','255','115','114','255','115','F'),
(92,'0870','115','Amount Check State','390','112','000','000','000','000','000','000','G'),
(93,'0870','130','Eight FDK Selection Function State','130','321','322','131','172','007','007','255','Y'),
(94,'0870','131','FDK Information Entry State','131','321','322','135','134','033','231','000','X'),
(95,'0870','132','FDK Information Entry State','132','321','322','135','134','033','231','000','X'),
(96,'0870','133','FDK Information Entry State','133','321','322','135','134','033','231','000','X'),
(97,'0870','134','Extension State','060','200','600','000','000','000','400','100','Z'),
(98,'0870','135','FDK Switch State','390','390','390','000','000','111','390','390','W'),
(99,'0870','150','Eight FDK Selection Function State','150','321','322','151','172','007','003','255','Y'),
(100,'0870','151','Eight FDK Selection Function State','151','321','322','152','172','123','003','255','Y'),
(101,'0870','152','Amount Entry State','152','321','322','255','390','152','255','155','F'),
(102,'0870','153','Amount Entry State','153','321','322','255','390','153','255','155','F'),
(103,'0870','154','Amount Entry State','154','321','322','255','390','154','255','155','F'),
(104,'0870','170','Eight FDK Selection Function State','170','321','322','390','172','002','007','255','Y'),
(105,'0870','171','Four FDK Selection Function State','171','321','322','255','390','300','255','002','E'),
(106,'0870','172','Extension State','@A@','@B@','@C@','@@@','@@@','@@@','@@@','@@@','Z'),
(107,'0870','200','PIN Entry State','200','321','322','255','255','255','204','003','B'),
(108,'0870','201','PIN Entry State','201','321','322','255','255','255','205','003','B'),
(109,'0870','202','PIN Entry State','202','321','322','255','255','255','206','003','B'),
(110,'0870','203','PIN Entry State','203','321','322','255','255','255','207','003','B'),
(111,'0870','204','Pre-Set Operation Code Buffer State','390','193','006','000','000','000','000','000','D'),
(112,'0870','205','Pre-Set Operation Code Buffer State','390','193','002','004','000','000','000','000','D'),
(113,'0870','206','Pre-Set Operation Code Buffer State','390','193','004','002','000','000','000','000','D'),
(114,'0870','207','Pre-Set Operation Code Buffer State','390','193','000','006','000','000','000','000','D'),
(115,'0870','280','Four FDK Selection Function State','280','321','322','281','282','390','255','000','E'),
(116,'0870','281','Four FDK Selection Function State','281','321','322','390','390','255','255','001','E'),
(117,'0870','282','Four FDK Selection Function State','282','321','322','283','283','255','255','001','E'),
(118,'0870','283','Eight FDK Selection Function State','283','321','322','285','288','007','195','255','Y'),
(119,'0870','284','Eight FDK Selection Function State','284','321','322','390','288','012','195','255','Y'),
(120,'0870','285','Amount Entry State','285','321','322','255','287','285','255','287','F'),
(121,'0870','286','Amount Entry State','286','321','322','255','287','286','255','287','F'),
(122,'0870','287','Amount Check State','390','286','000','000','000','000','000','000','G'),
(123,'0870','288','Extension State','@@A','@@B','@@@','@@@','@@@','@@@','@@H','@@I','Z'),
(124,'0870','300','Four FDK Selection Function State','300','321','300','255','053','331','255','000','E'),
(125,'0870','301','Four FDK Selection Function State','301','321','301','255','054','334','255','000','E'),
(126,'0870','302','Four FDK Selection Function State','302','321','302','255','054','334','255','000','E'),
(127,'0870','303','Four FDK Selection Function State','303','321','303','255','053','331','255','000','E'),
(128,'0870','304','Four FDK Selection Function State','304','321','304','255','053','331','255','000','E'),
(129,'0870','305','Four FDK Selection Function State','305','321','305','255','053','331','255','000','E'),
(130,'0870','306','Four FDK Selection Function State','306','321','306','255','053','331','255','000','E'),
(131,'0870','307','Four FDK Selection Function State','307','321','307','255','053','331','255','000','E'),
(132,'0870','308','Four FDK Selection Function State','308','321','308','255','053','331','255','000','E'),
(133,'0870','320','Four FDK Selection Function State','320','321','320','255','052','323','255','000','E'),
(134,'0870','321','Four FDK Selection Function State','321','321','321','255','052','323','255','000','E'),
(135,'0870','322','Four FDK Selection Function State','322','321','322','255','052','323','255','000','E'),
(136,'0870','323','FIT Switch State','331','334','255','255','255','255','255','255','K'),
(137,'0870','331','Close State','331','000','332','333','255','000','000','000','J'),
(138,'0870','334','Close State','334','000','334','335','255','000','000','000','J'),
(139,'0870','350','Close State','350','000','350','351','255','000','000','000','J'),
(140,'0870','352','Close State','352','000','353','354','255','000','000','000','J'),
(141,'0870','355','Close State','356','000','356','357','255','000','000','000','J'),
(142,'0870','357','Close State','357','000','359','360','255','000','000','000','J'),
(143,'0870','361','Close State','361','000','362','363','255','000','000','000','J'),
(144,'0870','380','Close State','380','000','381','382','255','000','000','000','J'),
(145,'0870','383','Close State','383','000','384','385','255','000','000','000','J'),
(146,'0870','390','Transaction Request State','390','320','001','000','001','001','001','000','I'),
(147,'0870','392','Four FDK Selection Function State','392','321','304','255','053','331','255','000','E');

/*Table structure for table `sysconfig` */

DROP TABLE IF EXISTS `sysconfig`;

CREATE TABLE `sysconfig` (
  `id` varchar(64) NOT NULL,
  `readPerm` varchar(64) DEFAULT NULL,
  `value` varchar(8000) DEFAULT NULL,
  `writePerm` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `sysconfig` */

insert  into `sysconfig`(`id`,`readPerm`,`value`,`writePerm`) values 
('key.0922b8ea-3b74-4825-a6d6-6058f851895e','security.read','-----BEGIN PGP MESSAGE-----\r\nVersion: BCPG v1.69\r\n\r\nhQEMA6Nw6GrTY6BpAQgAuB6e9FatbwloIjnM/kgY7MNz4fFrI4JAQUSd7AMrXFQC\r\nlZYC9NGXaHKhhQa2NzLglx83kpjl55YER6Qo6LvZGUcYnA/eXNMsv66U/MVl+TAR\r\nkZRJM+Yl8mOIhpZMTlRP3WZXtibSJbLdmMNBambtXHnc5QRNY0FO3UTie45ro7vF\r\nURhNOqbUyKqkDgC39wn28aFeM0dDBM0OLe1mICiZ9n36jWn/SlVUZ0Xy0KY2q3ri\r\nVF4i52D2gx/62a41E26bmYDltJDYQ9eJDWNJfXvdzN1SiK2XvLpIjkv5l98cq9jW\r\nxSXWZ0PuoFM/WLXp66iMN+jduJkVaaX13meyA0DQD9J0AXh8LNMdXQe3CFpJwP3O\r\nbkt9TO/7kZOromb1a/wTnnp9y/WuEzh8Mz/0kIhv2j71vTpPrrhJz42maK0nF586\r\nNyWx4+JE/EJwb620hal+MzB96tL6WDP7/PQ4OsBqyaA3sbbnBHgTN4WZ71nRci16\r\nM4vyJW8=\r\n=dNUz\r\n-----END PGP MESSAGE-----\r\n','security.write'),
('key.0ea9ba87-0c58-419c-aa78-c916802f8ff0','security.read','-----BEGIN PGP MESSAGE-----\r\nVersion: BCPG v1.69\r\n\r\nhQEMA6Nw6GrTY6BpAQgAq0dwES/yozz+bkRm9ivND5XUIlC6uE0/cEEwgjdG8wMV\r\nA0tCX1I8/O1iJskDB49vkhmRd2HIC0TUwglU1xbdd6thF33SeiDyLjxYRks2vka0\r\nU3jzIMqxLP7Pu4JGOvoLV7GqlN0Wg87VqveyuQ4KTbhK5J3vafvxLndIpdayY2j/\r\na0NB4Eydzgpft6dYnQBzRGgt2bfg2yN8BAYUKTPET1UL5cfKUxD8ovzWDJl5GAjp\r\nPVnF/dCFk3bIHlXZUQ56R1T7sMo7Dvs4dF5LZDtU1ou+Z/KhnhnYRhHks4w9U0ki\r\n+UmGWm6e6Rqg/G476xqgdU+b04nK4ORhx1W5IAtc+9J0AR1/U+ohLYNvuVp4YfdP\r\nB2L9X6XUawCu2G0FhKo4qGcWW2q+KsocMEwCX3L15Qe25DyWpRnCs72WqxR8rRQM\r\nruahUSToE4GnV5QNdv+37O7Z5RzoWF3QmNoSm5rL8c9evJDP+DJFacZETYu1Ay+5\r\nuNImu+Q=\r\n=LzS1\r\n-----END PGP MESSAGE-----\r\n','security.write'),
('key.139b7f6b-78f6-46d1-8b9b-f45317d4b431','security.read','-----BEGIN PGP MESSAGE-----\r\nVersion: BCPG v1.69\r\n\r\nhQEMA6Nw6GrTY6BpAQf9GjTEpy4bZ6Pen0jbKV35+9LoUJHETrXUlaZ5eB5IPsEo\r\nUv+9FJE7eKfFSL14GbkkUbvR2E6bh5f/y0TSI6GMdRevlKAvuthqukdwbjRMd6pn\r\nyKHzTcoB/XTicZAw3apZ4j7ZcyT3k7WKavrLDDhYApBjAX2oANFlBVmUZNt45gux\r\nXSGWRq6zsm66yhD6ttK5d+MBOzhsz44Ibi+sP6aXR+f7rZAqtP/aHUMT6QNF2Qsf\r\nV1O/xJ+dvekMNFgTj+g1drmXba6H5DSBcLo3rnryfjiLjuTL0hQL3UIIrtQw2tc2\r\nWsiyJeWIpcH4ayErCYPe5yyA/FAyM4AH7hgyLY0+V9J0AYruUd8tcJYVoMZxlc9P\r\nwBimorSI9Mkq6+HBZl2tF0h9hI+LNTwJUsZoEIhcmeJAog/KtoDGeh9DXSFLYfBn\r\no+Wny1aCP8SLjJlNoIMtyHTWvrQOgMNlEQvjYgCxrci1f6Cr4RoFCOWds9X3DM14\r\nrYO2/uQ=\r\n=nJ32\r\n-----END PGP MESSAGE-----\r\n','security.write'),
('key.237b33e2-d84d-4ab7-bc3c-4376a87b769c','security.read','-----BEGIN PGP MESSAGE-----\r\nVersion: BCPG v1.69\r\n\r\nhQEMA6Nw6GrTY6BpAQf/X7rmE8YNqkn7RqHe5yme6se5FZ14Hl3Ibs7S9Q+Y47p1\r\nSvifC/+U40N9Aaj2uz/GMkvq3Q48NpybytXmU5GlVV+fRKYJNT+NgxgaeR4tFxU+\r\ni0CEkn6TqrCOgABlHZtyKdvUGcKX/uBqZrp/gXaLKfCgJXEhLDZfO4NXFNmZnb4s\r\ncTr4Tm3CPGiomULe5awFwaXZhiOURvmqaHm2+88JOR8Bi8lK1Xiz/ET+S+glTmBg\r\nDDfUlj65/p7eSnI+T13m5ChaWiDKGplRfHbvg0GkI/HaXV8ivSjmv15RsNf0nkJu\r\nKcoqUlc2iYf4fjef9HZrO/cvq+BRAfnzOOqW5WE34tJ0Adk4dr3/Z06CFZ/NZQzh\r\np3YgxESEI8zUn227eecnb28A+n9xQ5/QoQXpwPWybummkUeu6ZBB1x2n2W/6Xk0D\r\nLCwnaNWVIgQE2fiuG661Rw1Y2Hsl+TUA+iZeQk3IBIpJr91VIvoDyL4qF1zpSVCD\r\nQ3VCc+w=\r\n=/U75\r\n-----END PGP MESSAGE-----\r\n','security.write'),
('key.26500d09-3eee-4132-b3c7-b157b4b10d01','security.read','-----BEGIN PGP MESSAGE-----\r\nVersion: BCPG v1.69\r\n\r\nhQEMA6Nw6GrTY6BpAQgAm6bdnp7k+RZP2CROm3CBdMWACJ04BATFHvHLdZ9O3plI\r\nl6/k/0vt+AdI08uN04AT5Fm8DcrgCT/UjNRuZTK02ab3SYdNenFuFx1S8Wfp2ShQ\r\nvR9Dp4VeatJeYDHt1GkCWhnZE2xzppm/vHcV+eBxWfQ/zvSKk7lR7yH4ThVhvIi/\r\nRcD7H+XsuOILeNex0tczuwYQMAgUfCFSn1Euj9jyNGG+6upZqHjaHFIYvwV7rcQj\r\nRIyAm76qPWl34S3olbcx3r79jQTcCsAO05Yjmo7+d+8wCUpoBq811a1NoGQO4e/w\r\nyRfkp4H+ei8EyPcCpNtMhoqpq2H3+7FR/j8ac5VIkNJxAbMLJVBJbkmAd4HumjXU\r\niDKy3X0+p0p6YKCbaSzh5+/DA2oRrIVvOdf/xoQSkQLMHpIRDbY5b42UtNMMgBLz\r\nhkRkK3j2XjwJhVNpEd4uES5ggqfs59AnpOXwCIql3/BEExXm7+sDsOTxu7NkavCH\r\nL6k=\r\n=mDDS\r\n-----END PGP MESSAGE-----\r\n','security.write'),
('key.27d1ba9d-9a8f-4599-a1bb-7c0283045cca','security.read','-----BEGIN PGP MESSAGE-----\r\nVersion: BCPG v1.69\r\n\r\nhQEMA6Nw6GrTY6BpAQf9ELXZOD4Ios5LRXnZP07VKoUOG8Ek84HGfz3YhqoFEdNy\r\nPJR1gBDEuL0kEVvYnWYlhMCeaXdxtsVV8e1s+m9cpXy1JYa6kFM1rUgGGaorRVzp\r\neLIV6oPjSeMQZ4YRyEyYnFXO1YxDLTEbGE4DYUzaf44kCVf+AnBtEsAPk4P3ZwxJ\r\nknopABZ/nZZYERJLKjeiQLMG3q5LgdZB06kb/F0bQ2k/HnTUPlLvvpVWvWXdnEQh\r\nhMPKA3eB06Oj3KWE26uMuFDqUC4VgmUoB4cTADjSqwKWRr4xnZyeskst6gp5amLa\r\nxwz/vn2nskIpJybWJD5+vOkX/A0+DAXUgWp70ycS3NJ0AZQMV1N00bUOCp86PPtm\r\n8pNTz4VI59rLEIuYjhN3ZefsY+GXCNK0LyFW6zV80bF7uNweZM0BDBYR6bFlYt29\r\nRZPiQc6c3XVLQiPz7SRa7InUcOj9HLR1o9mhVizRE9S9mti0AmpIHk8LfvkYKdMS\r\n7pAsevk=\r\n=UOWX\r\n-----END PGP MESSAGE-----\r\n','security.write'),
('key.2a93dab5-f758-4883-a5fb-809fa76f2f3f','security.read','-----BEGIN PGP MESSAGE-----\r\nVersion: BCPG v1.69\r\n\r\nhQEMA6Nw6GrTY6BpAQf9EcgHiTQ/EugWEyLMjAtY/hIBpaNIB8U/HlFnGOZfIK9x\r\np1vntVLCmG9Z1kDY0DQw9qfQw5lOjsBENxbVMy6IcBrDz7MTKcqnycWhXAg0Npue\r\nwj928I6WEgDvHDsfjSd31Z9guQmLoGmkYtEcEM2ZsxFawXfVp9dpXVMoMTFpHzJ1\r\nrYHqziBiJpPucU4SoyVbv/JaTnUaMZFcAoHcXP+luEgSIWl+Vc/CWSVopMdMyFSE\r\nR0ZhcswO8Lbg4cn4/cI35hr/fP0IFFeqUktyGaGCf3s/hcEP6mbwMWeCS1Jce3U5\r\n1q64F6/vy1oJiUFdWFsYMRJO1rENPOa8hZdB+GXnPNJ0AaOdGxJezPvDiXof+ChQ\r\nmqbIaBiHlXIbD0SxtpYcKzKdRb4VfmIMgu3T0FODeXiXg1LrOpIbrgEns8kFiQmz\r\n68ECp7zqDKhSD5MPUr248Cqiu8g+/IawPftWwdSj0xFk6DnrLZjMhThTnC1rW14b\r\ngHdZWS4=\r\n=xXHu\r\n-----END PGP MESSAGE-----\r\n','security.write'),
('key.2c2c6d00-f76e-4696-a462-5371e60539dd','security.read','-----BEGIN PGP MESSAGE-----\r\nVersion: BCPG v1.69\r\n\r\nhQEMA6Nw6GrTY6BpAQgAhZEdZZrQ+DApPVc63ajVfCNOqhDLcighsPGAS1Qv8SYk\r\nbDg/XSPCW/AJ4tl8RjquaaYasGkIkuIdeasuQoNfUu2B0ASDvGouviuRSujqQ1tI\r\nFDPeNfG5wfnwTFoQKKsGNOqnEe+4g6juknJtiknSNJQq3rvMYcMR5cPj2A4E7y6V\r\nCUmutWoeYDaRbd1i5IWSp3Fcmy4AG4ExCH7KfmhyCIkPnvTrZBT66mgLeUMgXFbx\r\niwq9+T9KymkrhgR7Ng3IqWa/0CQJtiJcZiy/1Iv0OGUZpVX5Od7SPTEOpnJU1VjP\r\nRNLnssFRX+c/4BY8NFQHt4bQ+YbCpz+V1AgpEnTMcdJ0AUKXgEv1RAZpPSyXnhHM\r\n4KOw1lKb5Hgy9bfYMi4ixIH+VX00s/WKvgXhbhXli/ghdz2U2dzQpWWxR8ARhM8Z\r\neN0G52hfw/FHL+79e9rO6cdQ5Sq4kqyMSnGNurFmFruj/L41nKFEdbSQxVfBlgRl\r\nTKasvdQ=\r\n=Ww6r\r\n-----END PGP MESSAGE-----\r\n','security.write'),
('key.2e5e4b54-943f-424b-a804-20c7a6a4a53d','security.read','-----BEGIN PGP MESSAGE-----\r\nVersion: BCPG v1.69\r\n\r\nhQEMA6Nw6GrTY6BpAQgAkiTrjmZGi1+NkBS5RF35ACgOymN/QEB3cmVSiv/yiFll\r\nbZ2C/AMNaPHNrgdq+S+pdmDIO1JqDD4OMpiBeeJtv9YRRs+IpxaK2PuYk4qzyUyP\r\nM6koWCmbK72SAKg38zd3XUUBpy72auqvmBOqJB497YjSlj60mmTNRKnbA3Nlc7PZ\r\nfLXQDIkHPMe5x2GHsQwLJICdNl5ADbKKGwsiEsmQCkKOn+WmR9zKcWwVPmJlCLEJ\r\nvMPomEw++ZYwsy0hQAAZIl4XD+GPkGjqUyq5zwzfiK64Ys3ckAsVLveYByXosHck\r\n04mC2NUvIDNXUTQon84ltlWnJTg+k468YVwNmulModJ0AQXVHmQvOyVb1SwLbmSX\r\n6q3IlNQfrlFu/mUglZ/1DTximgG5MXv57evzXQKmuOCCT42qs+ywIUS254eZinug\r\nQj+K1R2FN8UycWMUQ06Ttaxd2wAU3+G/yGhTaEeqsn1zmZUusgll+b5yNFACsizv\r\nKub5hZ0=\r\n=XATs\r\n-----END PGP MESSAGE-----\r\n','security.write'),
('key.2f391701-3e8f-4425-bbd2-00b9045e8f79','security.read','-----BEGIN PGP MESSAGE-----\r\nVersion: BCPG v1.69\r\n\r\nhQEMA6Nw6GrTY6BpAQf9EGGAxNNRW+u5plfaSUDT/KJ16As3l4kpoqPS8BdagsIj\r\nQwoXxxZmGqzz9s7FMikgF0qrjsdjZCQMOHXGOs1VPs+9Up38+25+7JBgL2Vf/Rwl\r\nGy2ED3fzMgMdYesIJGU+c5F02sFjjZlRTUpyvvhGhsXk3Nc/BRiWpyIH/bYTjpLE\r\niwdEiWK/rezSg49mCCQm562S+OAwqzNmu7x3oLJI769yhzZWO67zaCgG7YmzKEqM\r\nrUUvkpdDNHbOhrX7lPXY6IFYk9+p0OBV1x5qptFUEfQI/NRKvsMcT7vuuhSJORbG\r\nPOPqb5rAZ4yyAyGfbYuG98+sqVBXep7tTcSx3MNwudJxAQx19UHkDkM2SWpKqYtj\r\nVnOmUlA4FN5uLLZAuV5JAtEhPl0KbtjCZsPTyXMUvIpktfU8JjloUlz0bavbQ2nL\r\nkG8KmKY9CJp3o31R5V2TYFND3++od7cJn+LpLAQQdue/dPi9hxR7Eczch/S8S8fv\r\nvYc=\r\n=uwh9\r\n-----END PGP MESSAGE-----\r\n','security.write'),
('key.378e45f3-bf97-43be-84c8-a72ff5492e15','security.read','-----BEGIN PGP MESSAGE-----\r\nVersion: BCPG v1.69\r\n\r\nhQEMA6Nw6GrTY6BpAQgAvTAQMu7JAJY4M8VOfc4sjL8usjlXR4+LKghRKsAEi2c6\r\ndDpZMw7tZmdcoZUqQL94AzkHc3gNj8VYLbf9wHFJfEp9UccbClabfRig+WjDYOsk\r\nRYOkOmeNiK7JCQoq45no8HrqjNEdhKGmTo09w8V/KLvTLFYU9AgCcY1JKJg15VoU\r\nwilPJtY3QHp/ImgrTla5GNhGC1WlImJg7a9khQ+KaVAY85W4DEvM9lxVRSqJWkCO\r\nndI4TqB4djhRLtZFMvUAWlCbO0IRh39lpBrHHuL6RKo+rbeTNVlw6AfBETempjTv\r\nNHC03JN+7vb12NUoavVNKsKemELpXVuCpCT1f4WVytJ0AaOlB9ZJNP1GOWqe9RZg\r\nUUnu7qzLunYqftkNiGa9Ur3aoaxnhAcV++Ondb1IRAy2wiAZ/ckPTqUHe5RNkxit\r\noW+oF4Z2EVKDj/aY5XCFLTHq0eQhwadlNDmzoMkKKNszUwh8zvAwvLW2s/3NgIcd\r\nx78txcQ=\r\n=Bo2u\r\n-----END PGP MESSAGE-----\r\n','security.write'),
('key.37bca14a-fbd0-4b73-8edc-325a92304dfd','security.read','-----BEGIN PGP MESSAGE-----\r\nVersion: BCPG v1.69\r\n\r\nhQEMA6Nw6GrTY6BpAQf/YOvNLKtt8+LSZfIFNfAclrSoTUTS3slZq70C82qKJEak\r\nNg5rTmlQuHBNo/8AmYS5cbLn11E3g0N72nu+7GZzv0IG0YFuodKbq7zxms3iJvcc\r\nonSrhdNTFJY6m7pvce2CaGEXhg2LZmHvzHwMX85jpHD5GwN7M6qKnDlCPx4NPpbD\r\npkS5/02utJec6YaSv6Qsgqyx7iOf18qkV4G9EyjGvUUp/SBJmxObeXbTlt6qEy3G\r\n5nTByqngXvuoK8cyZIQF/ltV8ectdT4YQHYhR/DeeZKXcmUE/gNTGi+H4kL1Wdin\r\nDcM/+BCfxAmoHA+ZNL0GuPb7t0mguZ1kJRZvoTCMjdJ0Ab6FiKj1CmFppAUNnWX1\r\nkP8NX6GPv8f0XdsHU9Haev5+PpFfM/yscvSrIeP6uCGNK3AUW9drVzXUy5zYLrsG\r\nW39bw6PR8o10/UiZ+JUSRBmWBvp+R8okU3ecqc4fbMH2ez7JUsDaszroHdpc5VcV\r\ncsdWAVQ=\r\n=nxIT\r\n-----END PGP MESSAGE-----\r\n','security.write'),
('key.3c11660f-069a-4ca8-96d2-f11bb876d2af','security.read','-----BEGIN PGP MESSAGE-----\r\nVersion: BCPG v1.69\r\n\r\nhQEMA6Nw6GrTY6BpAQgAwoD/f7ttAFlu/xDv+oIVM9dflslksraP2m2Dknr/8lIN\r\nin7qfqEw1TIS+/zmE8OUMRuW2Erbv5WJ521HGj7+mSBhvVPwCbK90qs84fhtmIyT\r\nAGB9sgusgtEFx91yIJ3BW/nFJJSe0grUE6/k7OUQnidAq7cvazDADA3g+vfw3z9B\r\ntXJs8cKezS5yfEtuBuNWp3OWiNkzeEE+48HzOEjSlIhQzfQ2m9bcAmmcFCPfVoQB\r\nEW1EHvjyNW+wRUfJlCcud+2BUk0OULWeJbmGfYYwEDbhLNxWeqWZZ+GeII9j2cxD\r\n4J7jyhLkBVB/vPaNaAGpBYvsBGJt4ljwJS0lVzBMg9JxAbaOY784XvQ04tEyYdcQ\r\nXcAw1ArlT8CDgtVb+K5jgEa7BH1FPn6CkCjxhm02/uDAep8I0nu+cjxfab8l/DNm\r\nP7hHHO4BsdWv/lHWl4f3mGYf6usEfaFrA21Ka1AbjlwEl1Gf/zrHv1S3OBmud1ij\r\nBMU=\r\n=dWsF\r\n-----END PGP MESSAGE-----\r\n','security.write'),
('key.3d13786b-b278-45cc-aaa6-6069cea7a069','security.read','-----BEGIN PGP MESSAGE-----\r\nVersion: BCPG v1.69\r\n\r\nhQEMA6Nw6GrTY6BpAQf9HDbbFUtqnJ/Ar6CSar2kXVcUpY24EGruIo4d1W5UKiNb\r\nRvA1/eAWoG9e5Vpg+yb1b7uUabv+0MfKhhbhAMsHDaRJaDnOUVOYIwShDpkSdzRl\r\nTWT7QnuGosdCVMT9no5P6EiK6J/r6l1kh1NDDqE0qo9hlWqc5kpLLf9abKbPEMSN\r\n3LmP7S7UfRXZdgkwgRgqlqmtRJ2NlrJYvand6Ex+uBSJcVmfr2kprFZxxe0A7lb1\r\nAWDUb8o900YY/S2ylq+fw9FAHNVTN62luBxa9OT91WUX0GkfjphNhvkhp6e+XdkX\r\nlPiN5ZEqXKPnCHLV2ht4/QgI57yuMWVPqBVeapNgNNJxARBR75TjrF8LNdazX/2c\r\nEarjsnPFR8HvFlX1Qq+2AHffsfurqliImU3CLl4wuYawQwseo9GVbe2cVuR3pQnl\r\nkuYIHW1nijfRuHLrgtQ550Ybl/ADYGQjFcb8Kjvlxy3kTIhki8rZWaHbNQMDAafz\r\nlug=\r\n=ZALs\r\n-----END PGP MESSAGE-----\r\n','security.write'),
('key.4040a82e-933a-480b-9e35-4f83b651d30d','security.read','-----BEGIN PGP MESSAGE-----\r\nVersion: BCPG v1.69\r\n\r\nhQEMA6Nw6GrTY6BpAQgArfplwol0dzZCPDu44kwj16j5p+W6NtBGADiuveUP4sQy\r\nNJugFeg0MB22yGxGVYftFHUNYYp9Ymy8ElBS/iMcH6BxR/iN4HFZwFfCKhSwZWDg\r\n1u9FY9XZPR38q/uwxrJAF8uEAtCWeO1J9L7eJZeeES0E0mtokLdKqFC3FSYvlq+u\r\nLlkrSkf86Yuxmk9phQCbXBCZfF/aaMYrXP4ZZOd9m6AxiEWOYrcAovepjAjqVrx6\r\n3b5GpH8iu6A9orKBfNHyPyLitcEPDPgVNrrOpj1LDUpmsX2K/z1bTiTvzIeqEv81\r\npeMdvKfyljjkmCLWetB1P0tfRojai0ipAQGgVzTiPtJyAe/Nm/FSiZFHb84fvnLH\r\nCFXGLS8qO/WEB542AOhL71RigCM3zbiEGuabPX/CgFpKP3YftBoyiwtGIR8vU3fH\r\n/Vk6CPL+dIJ8/jlkTgFAmheEiXrMNwFwOEEkusGR0QPwAl8Pz/k68nYjzhAwVnhJ\r\n/H/v\r\n=EGkG\r\n-----END PGP MESSAGE-----\r\n','security.write'),
('key.404c0d3c-4f48-46d7-b91f-86d4a2b9388c','security.read','-----BEGIN PGP MESSAGE-----\r\nVersion: BCPG v1.69\r\n\r\nhQEMA6Nw6GrTY6BpAQf/a7L7/Nq/bUtph4qfvkETniOSy4RHqQyEAd50qKFzTgjm\r\nFGx328U+q5bvnGNVc7PLcSf6wHXkhaHeqRxZaujW0dr/bX24PlfkIYFejw9M/zSy\r\nZBIYu7/cT+0w57j2o+aacxdSI3EEwcemKC9r+oFgcCo6RP9LxugiOfqGf/XAzktC\r\nSfEk2BP97tLLVn4UxisSbx1BF5DAnG9APypHo0MDPjVQ9HX5kTFW0JCOAJdfIeux\r\nj4M8ofveRotxQlrPCiy2OuY6PS4pRjpfbsYC2vK35mwTFkspEJLE+meAZQyYG2w2\r\n47W7ZywF6qkO3gmeWaCk6sU5WqRA1SFzls7J7osiY9JyAUd3JRhib4acrOrep67a\r\n1kUAl3APoSD9sS/SNVKoNtYT95VzX8Q35a6xULPobdIZA7a6Hkday8GaWJvqGP24\r\nkP1SlJtF+dr8vrHi0i+wPmZluG0E9DTxT7KRb//W22cS48qPFJvQSuMzVuBqZzwp\r\nnZqn\r\n=9elM\r\n-----END PGP MESSAGE-----\r\n','security.write'),
('key.4a18d61a-abe6-486c-8b29-f516a92e8694','security.read','-----BEGIN PGP MESSAGE-----\r\nVersion: BCPG v1.69\r\n\r\nhQEMA6Nw6GrTY6BpAQf/ZZNv077oX0XkRC0XDsGKwoZBgHWlq2Pr5tATVGMtbSTw\r\nmHUegDi6vX3CszJtpT+pA65gYLBmhw3JGHZ/BSH3RsFSvii8047d86kG/hzjTq/3\r\nRMMgynEQxpSs80QTXO6mkfvimB7WvRtLIfBR5y1PNmx56EKGUkqW94wwzhXIV+wd\r\nTUzvBbTGjUP2lG4fwdVFVaVXKWN1IUtSqKPM5vxtBA3tOZVob7xtsevIOWN9DWdU\r\n9DQ3T6uFyAou4OEsoOWDa1hWaUaJ32pf2B7e0sxtPsrmH2xRxiyut2nfH54hHHH0\r\nj7sn9RTfWEOmJOgnE9Tcr+BcJN6G00e+AybDD3L5FNJ0AQmd3e9kvQK+CalPJ8zm\r\nZmirwapnT36zPWJMmIJ6nvN3FJataQsXW/kBn+9+N/mdin7TI71I6x+6J4ybGkQ9\r\nQJyREFhZSzVyg9aUXteM3zL2WISmNmCKso5a1gtB6WPYXxSxIUB6LK9GuX+soJjE\r\nwqcbezE=\r\n=t0i6\r\n-----END PGP MESSAGE-----\r\n','security.write'),
('key.4f89c73b-5827-4d68-ab5d-cf8e18b06ba2','security.read','-----BEGIN PGP MESSAGE-----\r\nVersion: BCPG v1.69\r\n\r\nhQEMA6Nw6GrTY6BpAQgAhpzljjrH1/OBkoYVi+dmGnRFL/seieAtZeOSRQJR/5Dz\r\n4o2YbPtr4QTmmvuLUJ+9faGAy731bkJJI+KKiC7rG0S4QjwOdtXyXTe/LBeh1Ras\r\nzDWcLUeVotKb2h+c1HoSFhVDDF8UlkBOP61EQajQT7Qivx4WswYjUQh7enOUfcvO\r\ne4CtyGPLvO8u0F6CgE2sN5PpTs/PuXujlJ3vEw31ODvQ2kH7KeGFcuG/ry5SBLca\r\nUDqMsi31aXbpDy4i1rgv8+O6jTAE24UmP5U+RPYs7I1oKHblJF3XNxxw09a8GcsZ\r\nuLPQ4iVJOEatBTdgOgHjAuK5cujx8D/krdcnjRYo4NJ0AXDtmQ1Jzec0kGbRMK/S\r\nrEhKdjzw4WaxyKv8Deo026yhUEAxTVm9GdkRnFn6lBK4Squ3cgp7Ecnnx6mV/5r6\r\nI9yANnZfRr29LVXzyAAbqakbfHMjdXkqepw5wpDWlFw5VV4bzj64t/pT8ZS48G0+\r\nnf+1wxs=\r\n=eySW\r\n-----END PGP MESSAGE-----\r\n','security.write'),
('key.508461c4-453f-42b2-9e2f-05a5f4d328ae','security.read','-----BEGIN PGP MESSAGE-----\r\nVersion: BCPG v1.69\r\n\r\nhQEMA6Nw6GrTY6BpAQf+J4lMa8RDmq07PCSlR5jUcBXsr6k4VabN5K8gqk/4cCm1\r\nBWov49WqLcmIWj7o2d9LQDVboWOOGQNIJeZ1TspEdP+y3Lw495C1YrPZb1YeKwqD\r\nJrzWYzc0St29hg02wCJMOqopF6s1liozD4+jzvy/owWGG40jQ3nPgzrm0uyMjndO\r\nuwKEiysR30umCGtru73rTD5yUyIZUtG0JEK+RQ/ZE/2gE1vRaf3GLegPrd2JkhGK\r\nudX4x2ps37w8rUFHFniyEk49ehfn/59PzRjUS4Qddj3jOfNcDk0g7ZwgLZ/Lyta6\r\nEtOjy8ptwjk/aPWfJO8k9W4wlIyWMIMxmq09jQlfBdJ0AYqvrj08ZhRC/oaRMakG\r\n/2tTiBYl1SIAPGVnFAKkBbVUomIiSGbwAoy+mY25zEZnv2uNuJrcztpRA/c5Wnw8\r\nDHTrsbjFJU1mJEfwaa3qtqw3nsc6uauU22ToETLGAu/7RrNQUB8b/lp77GYcxJfd\r\nK7YERsc=\r\n=j04L\r\n-----END PGP MESSAGE-----\r\n','security.write'),
('key.545fc92b-e5c7-470a-a42e-50b94707b2b1','security.read','-----BEGIN PGP MESSAGE-----\r\nVersion: BCPG v1.69\r\n\r\nhQEMA6Nw6GrTY6BpAQf/Z+ObVAerFJPNy8ZQMD6ngy7hZNFiU+6Y8jpr+UM30YZv\r\neMWtDgTEpZ//CvOvNwR+F1HoXcpVWNYa4nltnIFanRcwBTiYmMQkmbMlWPDJRsB1\r\n70ieAWRiUfiCKAbNSuP6j9JAI3N9FzOMIXLAQN9+EeoB43UdYFpwU4Q9FuO3VlSt\r\nJv2zOlp6VTQpCuZIj+TVDrI7V2pRy09Hrq3KeyGrYgNitvS3mejIpRb9aT2YqxeX\r\n0HSrOpSnKtwda4iztF/1DJq5GAE2ipkYP8JvFk2ai6ATNRXp7qfj1fl6L/sSqdTH\r\nRfT0pBwNGxo4J5P9VUuD9Rj0U7SHJAdw9esVNHVfANJyAcDaWe46vCnDW/LV1Ote\r\ndSqEfsKPp/ODqAgrEzKoDYnIkvAkcn9F9IM4dBM7xgvaQmqJhHyR99oVCMRJx950\r\nx295KmIOqHcR0xx6mC57jMUqftAUWv1CBc8CBl4CrtpB++IeUa+kw0rQpbPB2dpH\r\nWCYA\r\n=9Ody\r\n-----END PGP MESSAGE-----\r\n','security.write'),
('key.586d9056-d200-4079-81fa-c75875d2ec3d','security.read','-----BEGIN PGP MESSAGE-----\r\nVersion: BCPG v1.69\r\n\r\nhQEMA6Nw6GrTY6BpAQf+LQ7oeC5jHcIgXGGX+8D7b7a6lDzbiqleFrFhZdRzTEli\r\n+W8pSAh3RV3H3pl6a0Nm4pqqKaYBn3PnsA47BU5iUjleeBWxvAqdOmvmQYTMOF4c\r\nmQKv8Uf+bWlLCfOo9x/LipXii5kDa7DvI+AkjIE2TdMOGq5tijagQjHqwUSQKCDz\r\n4GjEPEbrC0O1JiOlECRT57sdyRrBXvyCeJomMwXp/SYuo3LZKxocSgvEyuxMJJ4O\r\nKZMPkzl8tFWlMndg+uXwEVEDGanA4oqXQ4FdWrH4tfrpsCrdRvooyRGz4wLphIo/\r\n2VnBY5glyfnosbr0f1hwgiYMYlAX+gAlj9GGHkI4j9J0Aci4ITKs3YAFdLSp64wD\r\nmsQJtGbLTYkanNLdqA3lQAWKH3P4bcQ4tK9udD/80RXdf4fGAc3ayn3bpv49Q3fh\r\nIaidOlzf3T/F90hj3O/OOGdnT+Z//3hbXlMq8AFJGuc3W8s38EPa/1GEYerApVKM\r\neCtM5dc=\r\n=qoYT\r\n-----END PGP MESSAGE-----\r\n','security.write'),
('key.5da2ea1b-4cce-43bc-8488-f72aa6f57809','security.read','-----BEGIN PGP MESSAGE-----\r\nVersion: BCPG v1.69\r\n\r\nhQEMA6Nw6GrTY6BpAQf/V0K2JeVtnKNNeKmwdRXAJlV7wRGChXzdotdZ4zTSYzS1\r\nPp6D5FUnPiIdz+gpw3gA2PhdX1ckighk+jsPuv5mY82tOmKdV0Tr0Jrhjv6CoIlZ\r\naTdL7b23P4DU8DaS+sDY2g6oMvbC7GRNvMtMRrOs7Bdf+/ABC59wuX9IEgimPV44\r\nWN8mzfkOu4uZ35F9afq0ttK0fHNdQ+itLsa6PH7lp5EqTacArNhg8WKy3tQ/CF9F\r\n+B4yne0CQdtpfOi4hx+wk6iQ3N+fd8av3QLiOK3F3iDSzf/0eIfoOFUwj49BTU+K\r\nIpGf5zY9UyFBtZZt3rHkKgbkTeb2vYQfNpcTyqFwI9J0AYuiurlid/3sdd8gCaWa\r\nfpzL/rSINz+iit5zIv1LnZHJkI27wL1yzP9ttDVecRGmgJUVqn0MFwJpZMgNPore\r\nvYNOr039vD4FGUVF6mui76HBi/JsJaXRe1JHvX23c+B6m+yOZIwaZ686Vx7wEwzP\r\nV2jxJbc=\r\n=VokK\r\n-----END PGP MESSAGE-----\r\n','security.write'),
('key.65895d2d-1d9f-4078-91d0-66cddb818e43','security.read','-----BEGIN PGP MESSAGE-----\r\nVersion: BCPG v1.69\r\n\r\nhQEMA6Nw6GrTY6BpAQf/eUyof4wjwrN12NfxLxK/rCKNk2CRCRNNl9RUwSl1+9Eg\r\nlIUVw0R+oLyKjvss5paDLSGGCDvTCyxkzFzeBD7Adt1hQgNcyvNpAjxwoiRncKEc\r\nkqIDpz0gwyH8MxjuePYLH2/eslDLjvlvsOr6nnVq/eGFEbbKttVPQMooZBLWtCf5\r\ndyzBzMnxYnr/olS+YZlWlL8ZmoaKFCGa9Bs1+diMqd5fGzlYtKqqMwxqiNDYeUA1\r\naZ7odfwKETA9WfZMx+WyzH7CitW04iaQsNVlkeG98H5i8duxRTePU+WSLmiep2+x\r\n2Vds+qTS7/Ld/8rH6YeDhG08VsdXHWBnkmWmO0DgH9J0Aee9SXj75Gd9JtrXfomt\r\nzOZNTIW72AOFw0n7NSDOAPBEDKrG6KNRqDN19Y1M+xlN2xVNCHno/IcDzODtYC6j\r\n8hOVWMZKfg26JR0GD2nCmKl6i3mycgtkuJneOnIIS/zt9o37h4U6Caoo9tXNlrC9\r\nhTWZlgE=\r\n=jh+W\r\n-----END PGP MESSAGE-----\r\n','security.write'),
('key.6788a1ad-fbbd-45c1-8609-5243b65392c4','security.read','-----BEGIN PGP MESSAGE-----\r\nVersion: BCPG v1.69\r\n\r\nhQEMA6Nw6GrTY6BpAQf7BSsYFVcCWLvaLtDM+zOd3eK3r+7M5FJM3OcACkuR+60I\r\njna7JKJfIM8vQN4DztyN4ZsTHw183P/AC5p5Cg+Q7OpehQFiVeDEZND+MDdByqxK\r\nS/J7q5DFLdmw7hEaODV2xpDnmedU7kjKUBpHP9gTmHCq5ktEPL1N9OvCHiBoonId\r\nr+/cDSJWuSzFYkIwt7jfydrkNf5W8xEYk0GtOqyeG4OzQB+ZCRPs6dmMTb8w3UP/\r\ngORJVlU1BEkNbZ0TeGzhux5bQJDXFYpCpU/cXoexTncx6zc0+Jziwb4zx7tI9wKQ\r\nHiqckWuN+oAOTq0su33AU/WvEozDrtYHK+QUw4iIc9J0AWHEVws9kqnNopIswgKN\r\ngF+yZOoBQmmYNQ6ThY67+54XTXLCWL6dbpNOyip4qIAbY7xy1xRDkIZiqUkFm6Hq\r\nWaPJmZgCr9WoVwP7ZDYWD4d8oUhevLkMBdVLiHc1JYxqytS/OUuS1rwCF3j9d/Ej\r\nITtsSfo=\r\n=SCUc\r\n-----END PGP MESSAGE-----\r\n','security.write'),
('key.6cb8e799-686d-4f52-8a2d-bd44e8b22504','security.read','-----BEGIN PGP MESSAGE-----\r\nVersion: BCPG v1.69\r\n\r\nhQEMA6Nw6GrTY6BpAQf/UIf36vXeQn7z5m1kxBiZjEG3ePKp3iCGUCSit6PtTAYV\r\nW23U36B+Mas1PhtrKFB2lYrX0N0oKpIgFikf9Siha1DF6SGitLcBV2eR+llsS+1D\r\nFr8D2263kbUK4hiNFd3QWfUg0p3QpwpjgCfYP2PD4c17Vm2Tx9cEaAwNk2MiI9rz\r\nDTMssfJR8FYKNJ1KeVExgU+u1NZnVL/aW7pdr5lkgsIeHuq5PjJYNhUnjk2gA/0C\r\ntb3rSbB7sScOzvQX1No0zYVQxdaHR0yZadWP4eP+oj+9zDopkMPbbwr0nGHGaXp0\r\nou/XuvTO+c1WLt6LjvhtxE5BkvGl7IbQ3J3t+/IPY9J0AV6vNNxzNcz4Dqu5YI6w\r\nFn/lvYp5xR/T16WoeVJaeWYNJ/9FzhMimln2xonJB+tlI0cPmeHGdjHsMvCmas5F\r\n+1fUoJPPJSBfcvArLj6rqHTtmTFUy/KKHA2skWERTw0OB8aOnB0ajrhFbXJdSSPp\r\nVxhVFac=\r\n=b629\r\n-----END PGP MESSAGE-----\r\n','security.write'),
('key.6d9eb75e-5bb5-417d-8b70-42ea73d2a483','security.read','-----BEGIN PGP MESSAGE-----\r\nVersion: BCPG v1.69\r\n\r\nhQEMA6Nw6GrTY6BpAQgAsLDAava9sYtDj/G35FDvsJoEg56FMEm4dkufbt9b9Ams\r\nfi/SaAUFcLREOBDnczir0x7zYsJvpGM/abl4Vau0ux26yOpKvkYZYvHU9vhbvykK\r\nm2lzwbJVUHqKwJKlOq9xoewT7XaaXCyzSsA/A1b4eXQPqJGzjr2zKszwyynROJoz\r\nkg4yfg0wFxnqBQdM7eQ0L0N4SliczfoOGL0niUsS/x7BHSddctY1ZHXKAcVtfVVM\r\n55T9v8YjOZq/mnk/eojzdO6hhG09C5VLU5MRnNv29KqI5hSNOtxbpyIzyRPwr0oc\r\nMJD8stwSAv7Em+chmoh/URyNCmBzPawj3nGQTZTH4dJ0Afql6zVv9TELI+pNcEC9\r\ncar7YG4YfmU/QuWlRTmOBsCsffER6jpi5euUMlvfDrFc8b2nfOUTZCPER98UmVrK\r\nlR2bPRBbOx4GHwJoaCycdHLsKIRl4yYUdpvpEOCXp3Rg+TNBDnObd3qojHEg8oWf\r\nOCdwPuM=\r\n=sTRM\r\n-----END PGP MESSAGE-----\r\n','security.write'),
('key.6f8bc351-96ec-4bd0-8d13-a54a5404e0ad','security.read','-----BEGIN PGP MESSAGE-----\r\nVersion: BCPG v1.69\r\n\r\nhQEMA6Nw6GrTY6BpAQf/d0lUTJlfnfF3o0m5mhmzoiFXCgMjfPw5cj8LqKZObJSs\r\noPUoaZoAzgnOkDL63PN98pyALe9IKz8lgot/2wYjL0lhO3W/JHSJ+wJvECG98Xlq\r\nG3B9IUNDma2/skqQpbfB9rORVUyT3KW54PaQ+8oitEmErKbhZYUbJtpwz2AZ9Ti7\r\nbkzz0psNRT1/8L7JXKYRos78sdzqKjvt6fW/UJMsbPxLXXzGY6CrFfKJgXjL5BNQ\r\nkD5TQZV2m1rNCTHusBgdTxrd0H/6OhBhoXhYqu3pWNwx/EhH7NDOwhB/RfPaQvxg\r\ngEfBELOAsmy5qadnUNujYBE009c+relOK9fJ2+s8FdJwAbJW1rFhMA25HZ/repaj\r\nHgWA8CSrtAQlsTSBdn+wyijI5VGHfmU1yKGbZ5fCZ4F5lhcd1EfBNmtj+BRmJLHV\r\nUCejFDSax7c/10WdMpWRX3oaZLwl9SuFHy1E0hkYR3iaRDlwuxTJNiK9hVmeu0qz\r\nHw==\r\n=vsBn\r\n-----END PGP MESSAGE-----\r\n','security.write'),
('key.73dc4aac-68f1-4194-a81d-8f166a935d80','security.read','-----BEGIN PGP MESSAGE-----\r\nVersion: BCPG v1.69\r\n\r\nhQEMA6Nw6GrTY6BpAQgAgXSfL1UV2m8ytW5ajU1KG+WGeRA6oNrUbgSMUhZBIpBP\r\n89SwhevFVAhDyB4TtwqULIm+eWIrU+PJYncTCOLYS389egmvnACzJhcaAsxd+cF/\r\nvsCs/E8mZ55JOl7c/Er0tRNuGz/Vy+VENImBi5VRs8K+15rJtdDZmUjrFcAWG+k0\r\nPbKJMuKK4JEZtZyzg2wPmpk82qAn8R+p8vEp0yPY2fOzDckj19zBmCBlNfo/yXwU\r\nNj+q6RJFFudFNIfZNEY/VinZDaNcW2n5UPE06lsLc/L4h2K3LuxtnPr+6+KZatb7\r\nK2CBx0RhUQ2O4nR1cCupUWoXccbahi1CSB+EuPrPNdJxAVxwtXEQLzHdxiWTLvR2\r\nKJMg9vYqX2pAcpkLViMP8A/xJCQoQ9apnRjbHalP1xpBDlFGBf+UfpzwqZGO+8rP\r\n+IP2mGGLeyLJOXVQgNOADgQn3VDP63lMQ1/sau51PYjWWjjjzDkQ+3LxHkxrcTPz\r\nczM=\r\n=xosl\r\n-----END PGP MESSAGE-----\r\n','security.write'),
('key.78b23d89-5de9-4d55-8bbd-1810ac077b62','security.read','-----BEGIN PGP MESSAGE-----\r\nVersion: BCPG v1.69\r\n\r\nhQEMA6Nw6GrTY6BpAQf/axrlrWx/uG4c1FYvG4oURQgkq8Mi8xKuU9uBtu9yBAkj\r\nMDqizDwVKNh19pDPkpyV/spXb5+26Rmc7qtCuJiId9LqC5BctPOI5uqX4/4duRcz\r\nixzkyzIHbwV3qIe/xN1AmCcjWzwkG/l+hmONmXAzFabpxh5ZhE8JRSpo5W0Mcmfl\r\nGL7vn11sadWGzSTE4dBYYUHnpk4haRZ43THF2yPg6BjYG89RsGTm7eKcgOc/m/c+\r\nUBDFQB0PZqgRAMWkxeyuwWC6aYFJdSkyCPwA6RN3iOQjiU+Y5jmzlPSNAYMyzKXE\r\na0kFikPINMYunCcuFbeR5+uns6XjphbFiKTkLalxLdJ0AR7JGnSYOColud8CxbrM\r\n1Q9aWfbZ46eZnrsACAmkTgf0GLxZ4BZ2Ir1uhZ8UpJT4nUiF6LakOMRa+zH604nF\r\ngMV2gJAkfSPmd8NlFIgM91aZft7VDIjkJ1eeqNHNGf7neQbXHkqD05ZitwKAhvGf\r\nT3TUyE8=\r\n=KktC\r\n-----END PGP MESSAGE-----\r\n','security.write'),
('key.796f8ed8-4210-4a2d-98eb-ff51470efa9b','security.read','-----BEGIN PGP MESSAGE-----\r\nVersion: BCPG v1.69\r\n\r\nhQEMA6Nw6GrTY6BpAQf+NsAKBo6fsLfk1aGoOqtwz0LH0mVaR/CyazstclwAlLHo\r\n5SQ4Qc09peH71GviWa1GReNhhRLjlNbgEwidH7j5I2fbPaH1zxY0LztuXmCaT+PS\r\nUCH8XHy6aSabuLpkiYG0DRU2QO/DKxqKK8sqHrBRPEzIKEuz8rjooG99CWqtPk5L\r\ndcDk6C3K1JuMqLqUT1IESvxDcWhtAuJ1Gfdei/7C5a5X4MzhFOyLUBn/rGYj8grw\r\nIZ1JAB9cuH4+3j8cGi0e9CNchdAJWDNBaXAKFiOOy+/Tt6a2WnwrdiiyVGUWqA9W\r\nGDNhkvk+F5rAk4HPsrIEob4HyY8tN2R+N0QXeUo2EdJ0AQBPdG1ZUE4HwSWIsjRj\r\nc4WZIjGORrTLid3ucJI098KihPgM9k/K7fRgW46BLAONDMl3Gt0+Ec7K5CnLHEmk\r\nwFu/gRjfPUqwUgnCsKRLp4WYNN8dAMFN8VoM5wcioS3Wwu9aEZlH6orzmAOiWypI\r\n66WcKfM=\r\n=YBYZ\r\n-----END PGP MESSAGE-----\r\n','security.write'),
('key.7b2da448-938b-45b4-b078-3d6609cbbc3b','security.read','-----BEGIN PGP MESSAGE-----\r\nVersion: BCPG v1.69\r\n\r\nhQEMA6Nw6GrTY6BpAQf7B2/olbSbzUZq7lY5ppMYurV/TwkVhxGBhu9UED5BWlEP\r\ngOCe9J4dk06bIDdI2ShAdIbsdkV+LF70c27vlCq9PNKsnT5rX7xbobF5MfCTp4JW\r\nX7MM3li/bjBrqVXa4bKvHQEly9hDaDJrtdhtS2qaYRvzxSitPluNEIZ6zpXz3Vqo\r\nNRW1bgSW7X/ZxVuThdfIph0/E+ND3FHxVYfEs+o/TJorPMLZKb/I2GPYezh6f7k6\r\n7DeCy7wCvfPAOc64oU3+UL5WWHATFmTOfLPzhScn12e/WuhF+y6t4OX6wkm2HYTW\r\ntylTlvQnvqZGcGmrYeygQ5rOqnj2MERijkXznyP5rtJyAXm5mF1F8+rvotHWPMtl\r\nueQJK0zqzRCPBXPtvNwYp7sYWEwISNI3hb0PVak8CHwWO1VC10NSk20C9cgijx1E\r\ndclrSyzcK/iszJHhxkAhL5n0V1llyrKlbsaE33gBtDFw2tiMxLL2ILfRbJW7AK5V\r\nXS3a\r\n=E7Xt\r\n-----END PGP MESSAGE-----\r\n','security.write'),
('key.7fab23a4-1b9a-460a-8b69-b2f2244f779a','security.read','-----BEGIN PGP MESSAGE-----\r\nVersion: BCPG v1.69\r\n\r\nhQEMA6Nw6GrTY6BpAQf/aes4DORw/wE4QgccaRxWxHzUcNADiAmdhofzAe0qB0aw\r\nKf8U+qR7TW1jp/QI0GhYx36EUmQRmLPHUxrlz5j7Yh+g0StvvbO7ZLNOTC8am3NK\r\nQcTle4wFh56GkPX+sHmpm2CFIG2IuVFzDGtHFo/6gALV+A8pcmu1/60O8XR4M0JN\r\nZQkJ3unnmQqgFsGl13NGz9RL8dpVFxFnmD/R66SUWE20FsPZxKdjPasXWx7JvSow\r\n3sje6kjCmt+DXaGhEc6UZQmzTalEwwCqWMuqN/N7Pr79WB/Hr0DfGyuMl26CSShB\r\nwXmIwYOhER9oeyIa+eZSUUy3SMEcTRT3Av9hFmUzotJ0AQemRC39xd05Up53jVC4\r\n2fHFwCX8G7Pgjb2n5SlDFX4emBPQfaIsLwIcxe3cIGTH8nFKXQe9YQZQtA9J4i/n\r\nZ66N4U4HMBOf4YJs8RaiJH2M9wZsMg490SjqGs+HXSw7Sz4V+M3vSknHiTlVU7Z6\r\nhqpOVYk=\r\n=MGL7\r\n-----END PGP MESSAGE-----\r\n','security.write'),
('key.80546006-0c83-4485-80b5-5fbb72d8c8cc','security.read','-----BEGIN PGP MESSAGE-----\r\nVersion: BCPG v1.69\r\n\r\nhQEMA6Nw6GrTY6BpAQf/dcr9x8QjYP1KYpPoF2MD1o3C0LtrxdTu9GyA7S2uRvpr\r\nazAinuuhNhv2nh2w7RZFN+ZD7hOgwjiMkrHfLWxHwtpaz71pUKEIexJHWqyVhva8\r\n3p32Gno8Zz5spimF2VrM/SzknLlkBeQ3vmmDUszlywViISXwsVFhsWqd8ZFWXWWK\r\nr+bIS+es5X0BRlur3iIy4ZjhaSmETcbIMJsk7fxpvJIk0FmRwU20IcMlHWBuoHWn\r\nLLYj999jnwdFdt159CvTNf6N1bcNay/SI//pmmvrU7Y17C/a3XlMJWewhIGHO5YQ\r\nahhnpTgs6eNwLU9MxZyggQv+lmXGYYZ7hN6GTqz17NJ0ARpw1xN5ajnv4DdFZlRs\r\n4grO2YOTpc5gjwlScxNZs4glkniYjdXtw4qFcYMMBe3f+fizMfPX0yXcmw5k6qeQ\r\nQGLOgxIyyqe2KdYP6S27iGco4J1BPQE1OUp0/gPTU2Izo3G4tXHed1tWWFiw2p7H\r\n3aiS+EE=\r\n=XLfF\r\n-----END PGP MESSAGE-----\r\n','security.write'),
('key.81f68a5f-3345-44c5-9867-844eadb63957','security.read','-----BEGIN PGP MESSAGE-----\r\nVersion: BCPG v1.69\r\n\r\nhQEMA6Nw6GrTY6BpAQf+M5KRM3U6hETxfzC8QCSWMaTEqeQiLG/emZEdbuGuQDyz\r\nUzTD56JmRDYYFix/TlZJrnMj3xmpLx/rArVzx5DSq6+DdG1a5pYARZNWCBqlHepR\r\n37Jca0TXoHJERdvyNKjhonoj2eihoHe0FHfKSXYMUxr1k2BGJGbVuSCtov12TRPJ\r\n0MXPHNpocHvX65WWsElrIr3ziKMaXKRk+8cygLTn67LnYxp9xR9jK3txZEDgx2dw\r\ndo8X61Zh9IZMixKiB1M9jgWGZwqkdXS4iP9zHG+CCY0vedWBqyb3dkm9qDI57dLy\r\nj/cVNaPHsPr0udpPs7l+uk//gxHtT6hU5WAlGzAc9NJyAUT99YBBbHExRi6B++fg\r\nJkQFCccLoL+2fJo+VX0x+fdjF70Pu9qyA1hvB/D+Tgv2wG4Q3LIj+69Q/mpHqHpJ\r\nYWaqMa82CG+ULYU9R7YTDi7DVjY2qWTsc28cZ1V2QRNVsRYGH5G/Zt8cfOPShLnL\r\n3tFD\r\n=wQro\r\n-----END PGP MESSAGE-----\r\n','security.write'),
('key.82eaf016-e5f6-4984-9663-3cefbba974ca','security.read','-----BEGIN PGP MESSAGE-----\r\nVersion: BCPG v1.69\r\n\r\nhQEMA6Nw6GrTY6BpAQf/R981tnvcCXRgpoqsNYnr4vT6+XwCeYPfXpXAcaXoVbVC\r\nyY7D5lKdZtP/FR6AtITYCCeiQWVCTjJnYbFMGIhvlpdiPbSPIvn6IZXkCnNT/cM6\r\nD5zjfxoagpPKcajDci2eiMPDmxAhbEG70dRCGB2kxwdULvwUJVoZAY5qx6aWajlv\r\nqQzln5YP+guNGCIxuJNKmuc4lQMIkLmtzL/OXZD6J2D1lxUc7UNa4j3s7vpthfEs\r\ncIYqslIry7AxoVTiSop+Udc/Ir5iH9DDBQBUDBVaFA4Q6p2UTTsnthr0GPeFC22O\r\n600S706nQYLcbMwW2DMRDpIXw2z+sH0k6Fh9cP4cMdJ0AUWgrMrzD6Q935ieXDWG\r\n7Wyvq9Pi5lP3NIX9I/BpsYK/kKEUaeg4F9a28MgM1FP7CeFVOU50MYou5ZuwzQ7B\r\nUu6rt+fFt6EN4s6pF/uziAUI4lX5FZCwFY6uUoiGBspTzNjXAaR2WoK3TUXCBaQC\r\n0XosK4s=\r\n=vGlV\r\n-----END PGP MESSAGE-----\r\n','security.write'),
('key.84934d51-56f2-4f29-8d33-d3912fc73fc4','security.read','-----BEGIN PGP MESSAGE-----\r\nVersion: BCPG v1.69\r\n\r\nhQEMA6Nw6GrTY6BpAQgAtS6uy6StawXXXsNeJsihxwgBdLYR5iwW5aBx5MsXeXdA\r\neDEqd3+SmN28hkyAdiKxhG271HkXZUlvWhZ06b6djNHuihae2mH0YD56BoPvkCV6\r\n9mDqKed2x6VEW4iNdIORL1DpY5G9OiClZneWsn86k7Plj/GXZZaMmbO6DjsH4Ug1\r\nxk+IZj5383fJ6fiW+tWhs8OIzE7OnAvR1q2E5950gy9BoCpvbvpfDzoqjpfaEZ7H\r\nlxXPy6HK+ceqoTkY5BDrEUAdRqExiZf1hDH0QxA070bdIuUpWy5briibUyPk+nko\r\nE5Jfg+kNXvfeBQF6MT4Y6Ff+PQjBU2gUz6Sys8W70dJyATccx06vuYPZOvrWQgg4\r\njy5l7aYwpWrrID1Vn0acz70wC6JlanL6tMsyNZBXFreOB0jiFn2U9QBIYOEfMV9m\r\n+3Z8IZOnfM789K/rc3cObLwV4iOICZ7hb19k+nsfgE+r1FOMMN5l2LgZGqEomx6v\r\nPpOB\r\n=kjOS\r\n-----END PGP MESSAGE-----\r\n','security.write'),
('key.881090b2-f1eb-4198-877f-b1a4a7bbe73e','security.read','-----BEGIN PGP MESSAGE-----\r\nVersion: BCPG v1.69\r\n\r\nhQEMA6Nw6GrTY6BpAQf8DvvArDuY+6TEwhLcY8gDqRyNnsQqKOu1Rge+faAM/Mcu\r\nx60Syq5JDeMBFJ26kq5/m42XmnqkZwCZa1cAKotsiL1nGiIe7CLWo4pgsLXHcTWA\r\nLGE2sb6sXX5yxwF0QZPCfbBO6O33zT1zzPMJmwI99sTLJ0Rw3b3X7Lp9WFbCsXp8\r\nDU9aZfTKtsY/rL7ZkuRZ/Jl+9TLTvhGa56BELF7SaYgZEvtmq5vKtEansFSoSgll\r\nLtu6ueFdOlehZ/9gN95DDXeezhiqnt0Cy4t+Op/lOh8xM5f7ylp2iIzhng0EjVfQ\r\nM8GeOsWjjQk7gaEuXktvTmUK7Hltx4a4OPEkM+1S0dJ0AZFc9GvlzvyuMQTwVl8a\r\nCdk8sBSssS4Q47c3VYqKEQ1wF2xyIN/fYwk6lReCUt+/3/fMQ86oRGM4n7eV6a2l\r\naIuES63ogIbNCPxawv8DnEj0ojJ1F8KeDGu+F3u1bnINWTJDhIP0gvsNAy12R0kc\r\nLEzq/XQ=\r\n=vcsO\r\n-----END PGP MESSAGE-----\r\n','security.write'),
('key.8f1950ef-3c86-40b9-ab21-db78968478de','security.read','-----BEGIN PGP MESSAGE-----\r\nVersion: BCPG v1.69\r\n\r\nhQEMA6Nw6GrTY6BpAQf9HRH0NxjH6ULbHe5+Vn2RFCSeu4d7b923kXBdaWV+5VUW\r\nWL21vLBjGsPILVHqowfPzf3iEvtKKVEaMOhx+aVjxcq7Y/bXj4tQXXfPtKRSw5Zp\r\nc3Lg2v81KX/lwUWxE25Sy1ch8hcZLA3JW4oHtCcw0CWWb42vE05sS9xwmlBc1aWE\r\nED/rAYAY5ByLBELrspq2WXcMV02sSP7rT82/lfOw8339hI6ZJocSOLFf9ZVC2HHQ\r\nBsyWReYMVVwX1eti1fkcS8a0b/y2wXp7TiCdowAuZy/KNObRfop0RTXy314pnebI\r\nzOKjLLxXUlSks8iLRJUvao3vpyPQEzgh36aR8DjRydJ0AT0AXn0bRI5q8DFZ5CLj\r\nlzDPiGPkAwzjG4Xf4dXjgyez4VV+o6DFz8Gt1pwmgLDV1Zqo7ScDlnjkCfBNnoAx\r\nILKHLTbKg3tt8tCd3k1oAXkorDb2a5aFxTvmw3/GeSNvbmoam6pnT8+bgJUNCyi0\r\ntps6918=\r\n=DJhg\r\n-----END PGP MESSAGE-----\r\n','security.write'),
('key.911e4532-2b53-49e1-80b8-bc84a20fa007','security.read','-----BEGIN PGP MESSAGE-----\r\nVersion: BCPG v1.69\r\n\r\nhQEMA6Nw6GrTY6BpAQf+K5s45Az5uNj6j34ZQ83rw+ryfcaJ4NyNWu1JdR0VVp1f\r\n3dz+cHHKmWCN60LoyuVuO+lPJJZEmwxzp/9EYWnsm+l7dRWyWWfUnpMzTokPivCl\r\ngw2TldQg8KibSv7N8cmpXVEJMG7uoAPIxTWaD2r2M4bKSVhPyBuPx4Ga9onJysjI\r\nRyV4pECUC30W6eK20oMreQxT0qBcrNy9pGo/JOXdKzJtdna1CaWTxZzhaPuSQAkC\r\nZ7SCBYHW3knxpi9O6abG5OMw9+vxzDXY0yGAcBZoee7x7OrgAcKTD8ToaBjpE8Qi\r\nWCSqZEL7XjMkdGzyL7pDFyVlCADUwjyqL2/wrGzLvtJ0AXH5brB5C7zBrhVazNLg\r\ns/ejyUzha0ES4+8YYL6WZQauyXdvOjRNgz77MlhwAkI9DqqupnDpslWIwOFOKaS3\r\nSwaQ9Om2Y9NiFT2z1u6PiKbRE9glq+gBgbdSk+hZwHeiZINnlSxbxynai+/pwhjk\r\nyvSK7jw=\r\n=y7Z2\r\n-----END PGP MESSAGE-----\r\n','security.write'),
('key.9232042d-42b5-40f4-a57b-95672087cd06','security.read','-----BEGIN PGP MESSAGE-----\r\nVersion: BCPG v1.69\r\n\r\nhQEMA6Nw6GrTY6BpAQf/ZfFLVY0nucYzDKUISOuxm1u/iMrzHLSLYq9ieHoXf+Uo\r\njWHPH/Z0X/9mcUT5Jzdc0W/FwvSKdRYeuac+O3jSZL9FuZHzf5x2j9pJM+qwWiui\r\n8C6K8LInmUksEAaE0Hn4JGH+TonGf58Xzkwx26DUa+W121tKA3MuhD4ljoVjFaDA\r\nToncwF0HUHfjNBu8611h5TZ+NXHFym91iPFTs1iyZZs2yKZwNuK6ucHD6d8uRr13\r\nc5+WwjMuONyH0fS/HrTXxfZixZC6Ng6nt7G7TMdkXIWw/eDWL6tDaohYkQEYtlOl\r\nK3FieO/K003RzjGXtNqG7FEe4aKuy2XkTGYbi6z/XNJ0AQMiQEBGfCkBJU4K0xd5\r\nUb9i71qUVd2at7JQs6ZuM9Ldub9ATnu1Y/O8ZXD890qx9dlCb+NQMFSZpXZlqwS0\r\nZzUJIJ5xNuenmml1aCWIxJt7uHXhzShEAhNDCKqIwzalTCm8Jx2xotcou27V8C/2\r\naeKWg9s=\r\n=Tj4A\r\n-----END PGP MESSAGE-----\r\n','security.write'),
('key.9480fbbf-9ee5-462e-b7d3-c0ef89bf724c','security.read','-----BEGIN PGP MESSAGE-----\r\nVersion: BCPG v1.69\r\n\r\nhQEMA6Nw6GrTY6BpAQgAs98hXrEVyNEvcNkiKRW/NohtSWEWRBZkrMC96twUbTU9\r\nwPW7owBHisbLsjKy2zrzMvgYl1mVqfvxmDwG7iDJA/LP6MiuTp0zOS7NMd9yzcUB\r\n81egdqfcunt824qDP+hp+i2PWSqNXnNQUXNwVrWIeKrDJE+1KaqMmEKwkhKLM+qP\r\n7rVspcG+/sNOld1iDVBHeQK9wxZXwt9H52wjP9l8U+jT0SiswKNFcJwKQxSin7Ly\r\nOPsAP7ppUOimsAk6axE6M+eAOljQs6012V6Kfb59UtrOt8C6O+R8AnO+A8KWEiPS\r\nXH9p6+X4UHKwofQN05JS1C6LB18HqBW0AEI8nsMD0tJyAfCXcnLTk6Ite3drrYVD\r\nKcriD+wnHgHb/jFy1gZEb9PqCwV0UJh4W5ouMN1Wpt63oL0IWc7QE4DWBn7rbfsg\r\nXOODUbxG+91Un6Q8FZn9DmUdut9achf1W4PqVfUkIIaoTP+LZQd3lFVVVMhc27C6\r\nNH/Q\r\n=vcrG\r\n-----END PGP MESSAGE-----\r\n','security.write'),
('key.98d91832-60f5-454c-9c32-b7962196ab71','security.read','-----BEGIN PGP MESSAGE-----\r\nVersion: BCPG v1.69\r\n\r\nhQEMA6Nw6GrTY6BpAQgAhK0aXosM0j6/yGEuGl1g2Vp6vt/vhw8WoIi1ffPADhx+\r\nJi+buOz3OBlAyc/zQvNFEIwOpLE5XlLtIU7/+e+rayKlFMWwrZX1w0PjFSEdfAaJ\r\n92r7ywSUU8dBJv4LUEtGbl1QxEp6un7asWx+TpYkVeG7EoZsLJwyK5Y06Jet0794\r\nlE6OExBpXNcJysVZOVT2L8E3Ix9TGJLjuqX7BkHeY00nzJWhiy23KMNP0id7RMfg\r\n69G2UioWTM/oF2yQAw+SIOEQ3z2RgRB/qyXcvr/ojuBPoAC3CxN+lmF1Iwz0ObQq\r\nesLS4wsooz6xFqGMaPPJomDQvMLX3YPWEnQEgUHyO9JxAVrJ3/GQySjLutcxwe37\r\n2RG8KDoG7gEh5w2JO4lLJBO36xGAg8HsWFx1nHKUFu0HEnhh3FVZ5WISirka3tHo\r\nX9CYM/sGddgatz4SYQQ/ARyAATLtQJ1iy6bmN92yKsyVwAuGnsXQ60QrGnaz3L11\r\nMp8=\r\n=oWJf\r\n-----END PGP MESSAGE-----\r\n','security.write'),
('key.9a3cb2f8-bcf2-4b27-8d77-f8859be16fc4','security.read','-----BEGIN PGP MESSAGE-----\r\nVersion: BCPG v1.69\r\n\r\nhQEMA6Nw6GrTY6BpAQf+Pbyqaw1TLEvHwF7hexoxj3eeMXb/b2JNMWejmVgXtSKB\r\nSZ9/wkDtYOjelJbgOu5e6Bf+c0mXo2D2h/+fLXYl9SZKXMtl/qyJRRJTyjMj5NgZ\r\n0PGvCS+rHYP2YTFKiOMpxu6CAwRYwklMM+EqG6k/NzlI8Qsmej25ukXEI5LG9Jqe\r\nhWFqi1E/DequzGy3xg0317vIwN5RCLhQTvry6jWMaQwEop/CYvRhws7/s+yNeJ9V\r\n9/AsabPEpyrIB3Nk4GoD0dB6gSob5MCg0AgDBnYbxPHxBWF/chIFajkkrGxyfvoY\r\nGty30c0oggmW3yCSojbAxIDkuC3oPPeZbHuOvK5jVNJ0AThstEWtlUO5KlD4s7h2\r\nkl35EGzdLLh2Sft5snJo3x1gpa91Gj+fEwCtA4EXcVfJ5EXkGpLeiZtaLcdW+kNw\r\nKvN8v8Kr9T4ByARMYI2yBz4S5srrcTUCHzBv0ZNxOz49INJwmU9FarSt7xarNpNz\r\nwFGisy4=\r\n=o65u\r\n-----END PGP MESSAGE-----\r\n','security.write'),
('key.9f291f91-42eb-482f-956f-51c067c3041c','security.read','-----BEGIN PGP MESSAGE-----\r\nVersion: BCPG v1.69\r\n\r\nhQEMA6Nw6GrTY6BpAQf/bEYhJqxpXT1+/TGAbwhwARWYqBgL6AZfxfM+yDjSIzLw\r\nz0381VW2/8bD9BM54b0snD5GQuPE3TruMHkEzM+Va68yYfu+8evaFOx/qA5j7/Rr\r\n/lxT3ZoEBO10lKVFhffOezeURxLrJKdJaNKwmhXxrUbwfWCCqoc3Rl9G/yLypmkY\r\n6AoDjRBgnqvCxxuS1ousYBW7DUaynvpC4iKYKaPLrRSwODN4nQp4S0eheMpXRizH\r\nRe+5XuWsCFtgt8AyzHo8WlOMmEX0GiP7sBoje4apdncDbkFLNVH/JcT5/YNyYGdH\r\nxUyKP/v+Vad+nLHkjdUzmmN5PlINl9xiK75aCFh+VNJyAZN1zna2DfQSC+/WLqa9\r\nZ4MGokyOnyETMN3k61MKapv6GdOSLSwIUHOy+7K5ffQEcar3XmJdE4h3mID1p67K\r\npOxXJ+3HTJOA8vxpJmpiIbjNsbLHez7wkGMLMFp0QqHCQe/iEgSGUFdeXHOlHpnd\r\nkkNl\r\n=n4Um\r\n-----END PGP MESSAGE-----\r\n','security.write'),
('key.a030dd84-0f01-4c47-8d88-84fecd8982cc','security.read','-----BEGIN PGP MESSAGE-----\r\nVersion: BCPG v1.69\r\n\r\nhQEMA6Nw6GrTY6BpAQf/U1Y4/nOpq2+w7cn+FttyEFKymTdyRgw1RypdFXyZRWYX\r\n1C+6Lq7yK0qSme2CKelfXrhPC1M4h9tdkOkMfKJHh0/UxbdGa+Epju+ge481kNMC\r\n5G4TqQwZlTudDw9pSoWV5Dei+QWf2e2ARG6P6BkfxAwyrWFAoXnSbzxy3JzTZH4M\r\nGxrypIVSe5TSR8wUhAx6Hn+iBzRctwU8Rh2N9HQmH+T/9LEVVEpNjNxRANU0UFJN\r\nM9qh9s3Kwsq/XTuRLPJxREfWXsSfWt3uU2Pxh42U/fpC0AaFllo8AamSQX1yu/6i\r\n2sDF45a3oLiNkWGCxTUdJtEqqnZZjn4cYTaqr3bmStJyAVcSKjD4wDm8LYyTG4Qy\r\nLq3iGigp7F91zyDVTANbJzKsL50l5nlo+QK/tXjVV2bT/saaAem0t/k5aDwbR/wv\r\nQ2qzu9WPEUNRU7K94JP7feFsNiguVPJOe02cGxx1DNtV1FuTwTeQd+TDw0mb3aJp\r\nfGUv\r\n=yC7L\r\n-----END PGP MESSAGE-----\r\n','security.write'),
('key.a13b278e-cf16-480a-a9ae-a1099c5aeb6f','security.read','-----BEGIN PGP MESSAGE-----\r\nVersion: BCPG v1.69\r\n\r\nhQEMA6Nw6GrTY6BpAQf/e8IbftuWtgOsbtk7TBlForPVgCDvqBtJNIdvNxSB7f91\r\nzvhJsFdzpJkKRVCsXaGzdx4gh1G4ownLatbQTdthGWsw70n/dWIWyiLHohUMFGCS\r\n5Es/4XUZ6q+OXOOBbgtPmMiUT9kb+8sWe9B9qfh3gm4mWayNWBgGmluog55u+r6t\r\nrfbvhOwqjbPii1BO5VMLZ4cAksZN4kYdO0K0Qaw1pNFHJR6cPPnRiWdGIGs1sRcV\r\nkpWDY0e/juTTTnAw9mp+fYucE2NI4C3xB2V4d2lLWFhfkXuWkwg53hwkryspjAl0\r\nLigF5tlq7mToSnW4tXUBcP5VSr8G0ENwobeTUlqkXtJyAWtms+dTZNXDMirNQcc1\r\n0M4V1ldnB07n7HD2cTL7Txg+WN82u5eIhQzgkX0qX77ab97Kv9S7j768Ejdeq6vg\r\nLBm4Ngrm56oWjwwAjbpkD/OWe9m01YjI9y2HDzNuLi+8r2EVKGxDqOEu7BdQuNdC\r\nlF6l\r\n=otze\r\n-----END PGP MESSAGE-----\r\n','security.write'),
('key.a1ea3568-257f-4bd4-8832-3f69c606ad46','security.read','-----BEGIN PGP MESSAGE-----\r\nVersion: BCPG v1.69\r\n\r\nhQEMA6Nw6GrTY6BpAQf+J2KyLW1YXwzqgRZDmBSfubjhbwzJ89CMF7mJscOMQoQg\r\nSCY8D9bX6a1fg8vyV2l3Ip+W0pRY+zsnHduZ5ww3HyQm63+UQYzpCwUJSCTaGBxs\r\noadyr8wcJ+98NfImBEzvBiNB9XPGPMDkV8tWMxWiUUx5yh3qR4BkPSS2jPTzrlJe\r\nmjO02ZfLDa2aGeKMHhIHET4btrbbkAD5HeEvKU9u5tutjD7JW0xg4P8mktKznxli\r\n0VfRvEtUUTsp2jFASUtrBJvwgDiyACTqGEC5NHzgh1E8nu7jg6uO2zG0zWIQ12v6\r\ngNhswLEOYDUXOdNQBOYpcBXI0Aq+3QVlZ8hJl/Cn6dJyATCu0pM65oQ28QTNIffv\r\nsHoVwEaH47+93daNwFOqhOxbomJEnAoO5KkZlKR1EJArkYY1Y7gPXw0Abfnrzw0B\r\nRXr0ZH+qcr4+6x3LZ9hi2zBeb8E1C4J3wTrqKJxqc/WHtZTfLxbAkvs46wFzRbZh\r\nfav9\r\n=XNDh\r\n-----END PGP MESSAGE-----\r\n','security.write'),
('key.a67051be-4dc4-4194-a1f6-ade85421d7af','security.read','-----BEGIN PGP MESSAGE-----\r\nVersion: BCPG v1.69\r\n\r\nhQEMA6Nw6GrTY6BpAQf/cA3GekL0Go7havvpfdt/pvditgloCyxeukWVGshzvJSY\r\n8aBxSrZnYDN+QV7Ptnb+73LBsCS/rNm6P4BykCjeoYucYFJzjcwK3aU6mO2nRJFp\r\nbYdUEBgJFGqNBt/+a3Ornf5Gq8+ca+tVqRCaf3HnhgaA3/cDIuatm7eAElhnG6Oh\r\npFVc3Y8nw6wADdA7ac/IvbmQdXZP0b/wkHICr72yBqNZqTHHe0N04pKc9ls5MLCy\r\nIfDgWNiapUiMFSoAEvyJtgl48kB52juCXLXuKc5lUv8YSUgerRFqBZJOXNbxkiq6\r\nOxKyPclKzTJl3Q1CdVVA4987EyUUi0973KiHlipph9J0AbZ/bbdHLojwkA2vnuDt\r\nhE1itLGR77Zlj0SjfTYlH3mgScvM9O0VfKjtaNZykE5Nv3HcU7BzMTc5EMLiR3YJ\r\n/qodW9WF1kSSM40Oh4OHJ7qanOTTO1EJsW80JhHJoIvQqaxQyu7XoJJHW8viwY5f\r\nX7pjjo0=\r\n=LLIA\r\n-----END PGP MESSAGE-----\r\n','security.write'),
('key.a832ec76-abf8-4bec-bb0b-4875388823d0','security.read','-----BEGIN PGP MESSAGE-----\r\nVersion: BCPG v1.69\r\n\r\nhQEMA6Nw6GrTY6BpAQgAouixCskt4qDawojeKS3YHodAYL6N01av0syuNETNLLSi\r\nH9DdlFsHrwG1q2REDPdpjDITWrF5Fj6N57+RK4/i0PLzwaoZTxO5qC7x9R73x1bm\r\n+kkq+7SUsd0H191ULy/jDeroUPz4vDhJazJvmTmCVzuhOBSFAljb6uWRzUUh3xPp\r\n/dQ5V+NpKFeKzEakp8TKCao5Z8UDxA1t2/r8gbQJYF53yrqV7XEINqQlcitWJH/t\r\npJT6z/d4hfnUByWOV5rNk/uxwp5IwsvxIckTfyoGQhoSgB4TusPmCseRyA4YNjE7\r\nG3prS8zBwTG5Xa8ZUJJ+swuloCf7wtfkek7C+xHT7dJyAUI+OhgAi9uQ9iQr6cvC\r\nof91UU65w3ht63RQRq/0tmpihHVJKySETS/SNmcbQFTeaABHaSUSv8EFHCLpdMan\r\nuSXUIn1Q2zqZzEyp2Z7SvSbRhz1471oMVlmHUzch5/zYac3J1m5s644s3wq0B+wD\r\npAJr\r\n=YVBo\r\n-----END PGP MESSAGE-----\r\n','security.write'),
('key.a8625ced-fb51-4c95-ba5c-6c9bed69a2f0','security.read','-----BEGIN PGP MESSAGE-----\r\nVersion: BCPG v1.69\r\n\r\nhQEMA6Nw6GrTY6BpAQf9HX80GY87fryPNXCy+QYQdujz1eWbRjaGNlNtcYXEBe/w\r\naWV/uIK4ywObfOP4Z+jtdSjO5rlnBVtmtqOJkyipNkdFeznjqBanZYhXcy/niVqC\r\nfhQb9l/+iT0DVqU94dV/oSE4pYaaROfDrM9Vfa/2ksT0qPwssOb3NYYcsHu55Cbk\r\n3L4IJv1L2y+jCuBA6A2IyGGrP7cun1wwORzxVkoL3FqyHnEQ4/xkHyS4G70vrOM5\r\nQZd1ml769ErsmV8UVzMonag2EdH9tJlXN3Z7JbB49PJP5qGTEwvIjp3hJUD0Rsgw\r\nXWL2RUhCsTo2Wi0I9BrAJy0w4OcLeb4QLnXZ52QgCNJ0AYaDKjDkx4MhQk+TylV/\r\nU1BHtzr+0RSthDX8MkAUhyo2lycnP82FcrgO5A8FCREuGlKx8/yQMpWqS9R0lz4/\r\nDdgkdEB2FHYsa8P1cd4pg6c9geznLwJ3SRFXZX2DL1ygC3gCnQUPwtP/0qRPOXjR\r\nkhuFOP4=\r\n=OWn1\r\n-----END PGP MESSAGE-----\r\n','security.write'),
('key.ab85faee-0b8e-464d-993e-34c18a12b2e1','security.read','-----BEGIN PGP MESSAGE-----\r\nVersion: BCPG v1.69\r\n\r\nhQEMA6Nw6GrTY6BpAQgAqvl5/QH3GrrWG1A1CDEhC4BGYSIA+F8KZt87vLSjcHg1\r\nieh3yU6jnvQAAuOOIk4q5cuVMdvvgI6uZelM+owrWYMnflq+hhPGaMGcXZee2vDr\r\nzJPChebJR7oFaoGyH/t00McGXnLWrsTIOPk0fs3ANIQOnBqJhPC9aX+vAlw90oFi\r\njiOcVym/a3jq510+BWV6I/6oi30BhoDS0FdmtpBN+Ds99ruf1RCQ4shjlIgwmULq\r\nxLO4xpevgiGk0tW9pLMBKiyMRK3pxlNaQQfn5WHOJgBdWFL2KwdRWTD9HmyUKpjn\r\nMPV0IOs0a166h5ToU58Sbrh8tOZUMUB2M0caCFuOGtJ0ATsbUlFDJVvJunVZx/Pv\r\nFXBSj8aNDe+EAABhg41mkuauuui5P6xU6QG/5FEqVG3GLxvRd6nRnVbzXx+O9NQB\r\nH7d1rwjY107c05fU+tS7L9K2nS5XZQwLwgBUQe9gLU5IyvevEJzw7dRnnuTDPYtT\r\nLHeEB94=\r\n=K0ia\r\n-----END PGP MESSAGE-----\r\n','security.write'),
('key.adfd0551-46de-4c1e-804f-85fa98565d8e','security.read','-----BEGIN PGP MESSAGE-----\r\nVersion: BCPG v1.69\r\n\r\nhQEMA6Nw6GrTY6BpAQf+J5tmUHbTT5G1MjAjv3BEy4Q0d9woS1T6GKklGMQe8mJG\r\nvbFD2sd+vcluzy2d9WdINn572BPovxLZY4sLJLdEdoPoi7UsZlgkDUunavRoY6x/\r\nkqntBBTrUTP3ONm3sf1DLPXfDWintVgH3fMLt9e1GpPTT0r0gwSpCc+K/cx2IQvW\r\nO1lDLQcB9UdlQotBXiJPdDNzDXqkOoeckV01ztdzPcIavn+RIZdYa/Tajo52HEtQ\r\n76Ly7lIzGVDN7kAZI3+8HqsBltpwe3ZXhYFNeidSyNHmA+KaU7yOUq6FUDGEBYuQ\r\ne9hrJ2AMtoFkFG/zEYEcl8Tw5w4+ayI1KSC5yfBrINJ0AY3519+OGUh2zWn+2Wpw\r\n/pGjdu7+qOJmMeLmP6eVITp3qFkfX8udxq5n//pS5d0R1vG+fTq5xmij7d/9t/2y\r\nLXaDEPBPz3E4MYX3XlQm0iJARV4C/2/1OBpdKw4iqJO84MAswYMFq3RPmduzoVzn\r\nAAga98c=\r\n=3HkV\r\n-----END PGP MESSAGE-----\r\n','security.write'),
('key.b09102dc-e184-497e-bdb7-a063d374e3c8','security.read','-----BEGIN PGP MESSAGE-----\r\nVersion: BCPG v1.69\r\n\r\nhQELA6Nw6GrTY6BpAQf3bFNRwn4YpUzFjzqqLY9cGcoxgAGhOOSBtU48HwrubrRO\r\n7y01Esy4SEL8014d33rq9azs5x0z3NpuXI8wAAHZZOhxWEnsNVDN6v1RyD2eooCq\r\naLGkgOsBvBOSub4pQgoWOnbVDpg/8lRposjA1ixv6YzepqnfsHAGtB0h+nrtnIBD\r\nQ9rfEB2BQwXEyQvqKGkxiEKZrtQnkvsDClwa3LZ719ooX8vgWJmrsGlAfIkpZiO3\r\n88JSMA4tVCKp35bp5QSBHYibAackkxfXaCFoiCsYz1MACX4EwQ6wev+nzNRroSf+\r\nQEjDIvYRfNiMOF79/YKD19E4S6hQOuH96IEgUEki0nQBQZeB7Cypp1/5+Aoplf7A\r\nINC9KDeOa0y3b3OfxXbSiFKT3YedvXUj54rcgjrk1kj2NKqu/OOZZuidulxwUpfM\r\naGK1LhOt2l8D66KaKpsMQt/5d0HiAqlk54/BCG6njhbCWLRjlJBMvdhpGF6O+kmn\r\n/+/qqQ==\r\n=gQaE\r\n-----END PGP MESSAGE-----\r\n','security.write'),
('key.b441d79c-17a2-4634-9563-fc92ddd95f6e','security.read','-----BEGIN PGP MESSAGE-----\r\nVersion: BCPG v1.69\r\n\r\nhQEMA6Nw6GrTY6BpAQf9Fyro58u1+S4pPuvUTc+hsmgPr6JT0+TGVFSicTuM9ZfP\r\n82pemXOoKpLcWZfkF8EP6V+GB3Qxx0JOk0wf+wrLOwQOanJHZo5fdtXmZK+Mxl6K\r\nYFFzg1eld6hC/oX+W9C8S6GvXPGVklPNUCzDu6u26xSRz4R8/Kci9DLHo7FK/R46\r\nlf6QX+ZxdCIfVoL6PtPy6kEKczm4VobQ58fEUuG6y97CMWdB+9u4GYtXqyCrDLe8\r\nuRfFQD5d13qyVMmr71PsRhBOQZcIRAw16c8bo8t9vp1JZJPCKFuYzXA2U0p9/FD4\r\nDZ1x2iysWjWx1eC5ZPJ5KfFQ3C1FnyfbCOPqBMit+dJyAQSd0EtN356EKNjnULdx\r\nsIS1Pkt0p5MCvMfw/lktkFA4a7HTgsO38S6lH9y/D5kwzPf0AOkQ+hcBs81lWCri\r\nfCAC+5jbyd/B67ud5qE/A2sS3LMFAnyLkzJ82wRfR2lUEImGZ7cxXX/YJW4uaJYA\r\nVZKD\r\n=Vmq3\r\n-----END PGP MESSAGE-----\r\n','security.write'),
('key.b64a8180-2df2-4303-8373-2fd2d99fdfc9','security.read','-----BEGIN PGP MESSAGE-----\r\nVersion: BCPG v1.69\r\n\r\nhQEMA6Nw6GrTY6BpAQgAnV+epHdfBNdkvTMQCCQthDxnSujrK8YputpUCAUVAmg9\r\nc5Z+v4sUBtpAxsfcMjpC2d5Exyv4nRrldHnk68i/JGpdHwO4Lo0d8Dmk8+bqKFJu\r\nJhdbjdfI1Vg9X+AkgP8/PJiWLaWjI4sD2/Iv64WdMLMbmitPnxkFHP9g6kBkt1lj\r\nJ2XGv1fGuWBIaYK1gd55wO6AGRPh+Te/K4BpSES+ZH3ABJqwoij9HIwtEots/NXn\r\nYeRsM0FrjpuutxbyyvjzxFF3EM0LQBDVqKIg43MFE99C41jjMLRpm/+uYQTvTtTe\r\norUlAXnrZrvM1UUG3JuXt0rQeojRK4oTSAsngH86RNJyAWNTJhC7YoPNW/88MjH8\r\nkki+NmvV57ARIr/H2eBMX7w70Bt+rdnNgpWqnaVCXkmpfzRwFF6ExpzmUpX7Ttz5\r\nIl46cK5Vsi2vkRKvx/yLZQycrboXOrVocA3dC0iS5JmVUUWde1/yAUygvhQ9QOvs\r\nbSmk\r\n=/jNZ\r\n-----END PGP MESSAGE-----\r\n','security.write'),
('key.b87b42a1-3956-4834-ab77-29e4e04c3ebd','security.read','-----BEGIN PGP MESSAGE-----\r\nVersion: BCPG v1.69\r\n\r\nhQEMA6Nw6GrTY6BpAQgAj2ikEruT9zIiepMqCIc1znk5lKXYWHn+5yYqAswGUjov\r\nYvog4NKdTJonQ7y5KHVW00LnLUrIdcAZ6+Wj4iMAN34U73d9X3IQyh+cFFjpC8xw\r\n3RCd5HavWjVwklzB42qoi67MigWe3Vyca+fkXb5nVX7AGYHZM9A0OHTPxPUvXgeb\r\nF+cjtmVMRCYbOAGuttCe2LnQeD/5llXRWBTHBdqYsFseJB5T2NaqhJjJkVrvCaap\r\n9Vug1kiH2KqnckK4WRUIThByu9CRPJaY5/kY0xCP46q2Wh9lH8FzrWGZkF1a8Qum\r\nSx7LM8VI7lk2hzuD8SBPt9pHxReeFWJuH3Dhdz7a09JyAbazazvvsOOJZKEbJ1ZW\r\nuSVPCSWoxNpiBVSMLoIqLTInDBm8+DMzEhO47jNxhQargx1pP/YdSYRiyNFpH2QH\r\nVR2jgmX8wEQr7MuyyvM1oSfBVKxvjV+YuHLdASMwraefspxCfHzYlLZQb+6p59Bd\r\nGqkT\r\n=itc0\r\n-----END PGP MESSAGE-----\r\n','security.write'),
('key.ba50d457-46d9-4069-b7c4-cfcf06f4f62b','security.read','-----BEGIN PGP MESSAGE-----\r\nVersion: BCPG v1.69\r\n\r\nhQEMA6Nw6GrTY6BpAQf+IXwpc/xpIUkwvaogvTkXXL1gb0vr4+HKveg9FuwYmmCl\r\n7w0x8OaHC0cncEy1O7yuOcFl0srAGHtN22DcSxuiMWTIULYg+Whkc8pkIAVBBsEB\r\n9RoH0sNhL93DmjrWKoFW5rMoYZzpwVG0gO5F3ilRuuOcdpwKaVAp0Ejx7wNrcLvU\r\nhJhQQ65s6ZbTCoh5D14c/nL+laJH5tPK4StanYKjBlyErqRc2fCiAKbHkEaA11mi\r\nVkCko/GqgRHw82jrGEvD8L9hdr996mNUb8OAUoBxILScm7EtFcyaTn5MO6dusAQy\r\n1hyvQgExnDXz7bBtb+BoYGZGTAxOXEJpQ+qhucBJVdJyAREvicyrZTusAPTr2Oib\r\naltMwjoEx9kIg8MZZxVuJzc7tCtW8t4nhDF2+BVXgu9DHiaHa2cijJEbfharblz0\r\ngRnjAG2ynFNWhgrCczAwKVW/hlAIlOVeIbLdGAAC5IDTMl6Ig1zWGhmPuSLDCd/n\r\noKVw\r\n=ZhCT\r\n-----END PGP MESSAGE-----\r\n','security.write'),
('key.bc5860d6-8f85-4ef6-906f-340b2dbf4257','security.read','-----BEGIN PGP MESSAGE-----\r\nVersion: BCPG v1.69\r\n\r\nhQEMA6Nw6GrTY6BpAQf/eVANumQ+AhqCofXr/JM2dBfIf55BCgYkEDo9ntoL8Jsc\r\nwXjNTMJeWDZOswQX4BDjO5vNk777vB0S7zfRhS6G39NKwb+nVw28WfeJu7q/sNWS\r\ncXMN/GvxUubt2bxqfiDy3GL7ynsuzPkeWkPC32zFNbCwP+zFkA92i6kyhzNGU6eP\r\neG2g7O7O+MXuV+FYkZzSsEDchb6OQongzKlZWXMUHZhBdP9gPdvOwCjMzAxK9TZY\r\nc2XI6UBT+a4sBCj4EXhqBaHAYSd8qVV71sVstnC7CHjdxQyEPT0U3IBl6ik05k1V\r\nEttk+T6p4+xuMZCJOs9PeJ+7H+AijQsXZpnY1Gs8LtJ0ARFQgnV10KvsWwxu0BH0\r\ntQ4ODGkbgztWJrRK5gWo6CNJu/zhtFwInP7wxOPDAY1XABWQWQkIZMMABHKUPasf\r\nt+lstszkmOGs1/XuqaLBgHe1H2DMjRNr4XfHOwQgcxfL+BYU4EPJrDNT3vm/HyuW\r\nhR7ZBhA=\r\n=G90F\r\n-----END PGP MESSAGE-----\r\n','security.write'),
('key.beb7cbbc-1c66-4d52-b12d-f5909da0136d','security.read','-----BEGIN PGP MESSAGE-----\r\nVersion: BCPG v1.69\r\n\r\nhQEMA6Nw6GrTY6BpAQgAuKfEVyCGLbwyb4yb2fCpTh6XPPOSNAPwBh1uXlxiPYCF\r\nrspB1l4Qcl8y3iL15XN2mJlKwBiXCBDXO/qSOEnPyxk9dZrZxtXKGbvfu9b/c/3e\r\nh1Rst83upzhnB5IRQq4bIpjk+447QasI1x68DHilpjkE8SzXINPVsUfAZjjv8HOh\r\nmPLk+ElUenpVBAG7+ug1oOChjJLQ6EH6GJaZTa3kjOrO5dTiE1j7WLrenkXHEOkZ\r\nd8DEiRpgJnPj97phUIg+r7MmyXTB+QBENzl5u8wQvjS+Qmg7g/Gsy/qBAbxccwn5\r\njj0ZvTvuIppk3hEJAlyM5z9OkAcuH9asGzOLxTCDz9J0AabgDF1/xHdkqN1Z1VPc\r\n7nR0j/X1wtWybHoYc29fRas3jsc7sQ9XFxUgZcyZDmkVu0tbXGlAFv5WxC64WN5L\r\nQhmjg3o2MuB8KnHqQiJ5IZOTfQbrWjoswNADsN36WYFGynqhwZKLaCJhxHW8wUNh\r\nDK/BYao=\r\n=bEPr\r\n-----END PGP MESSAGE-----\r\n','security.write'),
('key.bf00864f-64e7-44f4-ae80-b97091ea97f2','security.read','-----BEGIN PGP MESSAGE-----\r\nVersion: BCPG v1.69\r\n\r\nhQEMA6Nw6GrTY6BpAQgAtRO3rlQW6HKGQDoTZqn/YInLaXJ/n02W6tVAm/nwe/5a\r\nAcZ4CQUONQbxOHflOh76EbaYKcRHzrhnJ5fvv0W8BJ8dxfvR1dQcb0SxeaFd5wgt\r\nCRlQ3Xa7MWdgJR2LlKMVXYMpWHjyrywOHK1SObl1oxhCmTBL5pwABAxVlFVfv8vR\r\nkP32BPxmeC872McTO0XksC4gtsmy7N2DT6v5WnAtaykpK014vDGlFR9RtuZVsaHk\r\nUgxf2XKlgA/nVWPlyRNH2BaCXCyJQYFwESkxBTcKSOPL+LTQfn101ER4QlnI4OFn\r\nrD3izYbpcbLq9Pfk9Ya6fQ4rUUvusAHWOA2lGK9NM9J0AYTzvMdDkRKqhdikzKQH\r\nWIcyoC9wUIEBGm7Z4fpX0GDb2DbssDE/CF5iXo4KZ5VJtdZGwYRiwtA19sjvqx7x\r\nonbxIYNOU++W2c5sU+ABJuIBgKYUnYfJE30lobvTxiBOZzzzkITjvA0hw0oWa1JO\r\nMaYpniQ=\r\n=N5QD\r\n-----END PGP MESSAGE-----\r\n','security.write'),
('key.bfac48f2-b24b-4bb4-ae5e-2e001e70da50','security.read','-----BEGIN PGP MESSAGE-----\r\nVersion: BCPG v1.69\r\n\r\nhQELA6Nw6GrTY6BpAQf1GqwmRs5lsemrSFymoC7BJhrpXTtWVfFsB9anexmabAPg\r\n9l4zAuHFBk5mhikMtutH9/bJfOQ3egQq0whbZhU7+Q86lY1/H458jFX0sYwml2cb\r\neoXmZVM1ZUklLmGZaqPX/CXOiO8vNI2k7VgPg8yBr/wdPv5PEwhbHagn+iqcicMH\r\n3p42roNnRKho83T62pdkenmSCGXyQ8NKUWManvZQM2qOB8r7r2wxjg/UGY+7jPGz\r\nWdaVIAwN85fVW4RrKdMRl6HjyvdgTKH0jtHT/L3DlSr4Wv3lsDP4QkZguO+i64BD\r\ncy5OP1mNBxgzh5gFuej+GlsgKlpnWdbwERawZ7Fr0nQBfdnppaMTv5mDTGv83Hn8\r\nEL0iGGkTaWXxqaN7hP37KFHqOXNpjvSrzWZn7t0yG/vAK4vbxCgefalDEjCfgh4R\r\nU41xbgLN0zlzZODCAZw7txHkDL08WWwpznss3GJvE91tFfY4qP/CbKiQwprWXBza\r\nraDXzg==\r\n=8UwY\r\n-----END PGP MESSAGE-----\r\n','security.write'),
('key.bffec76e-7014-4b75-a04a-f6d5e615011e','security.read','-----BEGIN PGP MESSAGE-----\r\nVersion: BCPG v1.69\r\n\r\nhQEMA6Nw6GrTY6BpAQgAg0XOk4B7nfIf0Ona0wsi2bm7R47950kja3XvuKSMy5Uf\r\nrQcm9it5lnehiH9ZoAUlI80lFfVTfosqXzR2sD5FyvZLZpMMwzubzGMThmy7UleG\r\nVtNtSXdpw4DwG2h7cm7TJ+nLgzruP/b2xMBXBA2vhHdCZAjAzNkH+NB41qqd3Isf\r\nh0cC2tUQXtasxYGiT/msZ63QBaREIWj4sTeCvyuyKNlG2te6+LJ7vxF0U5a5vTNX\r\n+kn3N7kCryvu25/LPJBS0CRADK3gAdwT49k57esHIRE9KiTJhg5q82UvChL0vvp5\r\nrgq4IeL5lPzUiNfKJ/zoGXm4lFz9mwMjplo2UmDC79JyAYeQHhWSNwMmoMgEJVoi\r\nD18dDlFM6TWVYbcfkMde/w751Ml1JXNdCtsrEe0qvDtd3WbjCP4glk4H5UlOft29\r\n2iRdzBfjPtLXZPOwIS7oCImBdgkrZWpQ/dMEmv2iTDn0y/yIx6QrIvvbK5K4Tl31\r\neer8\r\n=THOo\r\n-----END PGP MESSAGE-----\r\n','security.write'),
('key.c48afb29-6dbb-4365-ae0f-2217234c3e4c','security.read','-----BEGIN PGP MESSAGE-----\r\nVersion: BCPG v1.69\r\n\r\nhQEMA6Nw6GrTY6BpAQgAsaJtKCdOQcibdpCFCzC4fTaFrq4ofRg6io2bWBWaGV7C\r\n3uLSG8ixix94+kKX7zvpMnw5qBYBYbNkPM6gKMSWct1xJJVSAtAaLUCvIlPiH4eF\r\nSKRbTw33zjleaPTEcnTDegIk1FYSCx1ioKpbWj+p9r09RweuB/gKT1FMusHBSp++\r\nT6knhOgEwsfe3Hdu0q/yv9H1fhjVtTEvydNeydyia2D7FUOiAIh5nTVINOfFDK2C\r\nzsbgqaHBk7pGsuhYQdQnzzEbv1hH5K11/oozxForRj9p1aOE71L4IdJjYWYzczQS\r\n4m/eEbWKvmqhNs08owQ1KUiNnh01UrOE6Q+ZetW189J0ARnz2CpYvlBNqt5z7izu\r\nJuwLepjVjXl02voObzRRaNrx9mTvjGVmTBd4ZsKaDu+7HndKas/C7U5aa6aTyItv\r\nImmuUx7mGI+/Hx9yjxK2OP+S8cegBrjMRaOD+58sjKnUMCPKuRh2wh51fUq5jfiW\r\nsQJ91Fc=\r\n=29Hw\r\n-----END PGP MESSAGE-----\r\n','security.write'),
('key.c4a8136b-1304-4db9-8827-707c49f31030','security.read','-----BEGIN PGP MESSAGE-----\r\nVersion: BCPG v1.69\r\n\r\nhQEMA6Nw6GrTY6BpAQf8DP5zibKw1TjiKEiasyOY/s28Vy3OPUEyr407JbDkyWyh\r\n3G37uyrfaQF2sL4WOAwFCMskH05oJM4qE8c8Fxa6fCZSz16NuiHOEmaf+cJKqyDl\r\nesYJCJmsMTV/sbK/0FK/O51XgkE8cIr/sUfQeW/iJimi3tVW5bL4mrmcToIw6hw2\r\nZwOr/qoJTzdtcM58kliwV9udrTfXMdfMMNa8JYkCuSVaMgpU6R7kqYkJpChMvD08\r\n9pxwHtYsOwHYpoXmNaRu5Aes/+wi1y2ZnH5yqk6rCyuMZ/HpIPy0erYTZaqzar1C\r\n1U0QJg7vri9aeZVupEoOFG09tDa/cwwtHGwKl9BrvtJ0AZ4vcQvofM8w6I14jPrF\r\nGv0VJXjaG0DWmFh7vhrlIlQpGmwQx5bTW9U6lvpzgBn9EUiC8xBCEVGDIRf05Vms\r\nG0e99VwHZQYa6G0MbspefGYB6E2+Jv8+VtYk8Dgfvb3J4UzZfMF7wK5158T9xFVu\r\n/BnraHY=\r\n=HBeK\r\n-----END PGP MESSAGE-----\r\n','security.write'),
('key.c53b9599-474a-4ff5-9e21-5e92a619431d','security.read','-----BEGIN PGP MESSAGE-----\r\nVersion: BCPG v1.69\r\n\r\nhQEMA6Nw6GrTY6BpAQgAzF+gpYQY7MOWUCXRydMRNAcONqPSV36garYxUPgVtkEs\r\nd/g1BTUDYSsnRGihpTX7nY3j5Vc5GEZKueoRR1ZyoxhsOKaW/ojv/gLnSpxe2a+a\r\nuO1nPeIO9o6/LKaK4UMx1mCZKVwP6iqFw45XaOYrPDlgSUYjimm0kqR8SuoKiZTh\r\nd5Fun48TrrV2+zdlTGYVn/+VMpifS0Q1FULRNoVeYPrFmR3hbH7XXAqJLhurf4iI\r\nJ6Zs9o3OEfmUEtKikBlKDF+CwSx3RUGR+lyFY1IhX70U1PD4REZwZlFF1K63T+13\r\n8WDuR0CwPtfiryvWUR9q+Prmk2iAA1nEDfE+plGVMNJ0AdGs7jMvY059aToJbRGm\r\nWMhO9BeVCDYyHC0PaFzKtQoWsH5yTm3JpwBEBqBgn9o3A4rWeDEhg6NFLlkkG982\r\nH8IxJte6iLizOCA/qHFmVh4NjAwfCEslV5O1S3IB8gkqCACMyVnktiEe8RXI2cyi\r\nsfgxhgw=\r\n=P/YQ\r\n-----END PGP MESSAGE-----\r\n','security.write'),
('key.c5902525-f112-4048-952d-abac198f3df9','security.read','-----BEGIN PGP MESSAGE-----\r\nVersion: BCPG v1.69\r\n\r\nhQEMA6Nw6GrTY6BpAQf9H7LstWiYPm7kiYSiY6+JaEggFsgX1qM8+VIPLGs49Tt6\r\n49k5PQ6xRDYbS7dxmbDp3TnWkKXXwLgevesTOi21QYikVWFR+Z4CfgyGNyAwjWl3\r\nDkLCiIl3Cb5zPol6hwpeqD9L6l8aHlbqF5Pjeo1aBrZZre50EhC6RUR+1pFyoBUO\r\nLvGAt8ubIV7mLGVdpqlHSRmoE/X1eA9KOWHfOQ4AcOE6d4WcoIzLSqVxIfbR5e3y\r\nXC2ggfeYBCCqfUf8CC9bYAfMRuCahdvD44kM/3Ryqk2CnNvf6jCBr3RKY4z+H5JN\r\nuDGyLRzxahMLrztvW+ABO4f2pYB4rfUKtHbzIx61WNJyAV3hkYU42H+84yvkWuWJ\r\ng1xEwT2Ray+cBMoybnmpoEpJY6jAgdNvGnkRzV0/p8YLtRgbgk/kwE+fORWH86pe\r\nyVEySVj5ePv7Tpi5MHzcmrTNWIS5J+H1Qqg2qFP67LJik5vYMwQHVZEIb2K2HvJZ\r\nFq20\r\n=bav9\r\n-----END PGP MESSAGE-----\r\n','security.write'),
('key.c6d277c7-0f1a-4718-9086-3b1967136825','security.read','-----BEGIN PGP MESSAGE-----\r\nVersion: BCPG v1.69\r\n\r\nhQEMA6Nw6GrTY6BpAQgAnwzBLNMtKhAoKIR3RKn3SmAwpeq22yMUjYuJPZVg6iWk\r\nHNuuwS3SCFN3bzV/0owFUhNaEUb4eSKF6tq/gdwjd8RzDPvvP8pYnIpyzZyG3Ndu\r\nEbveldYjkOe0ppFJxWCH/hriY+G1yBh71slTuoNCpufP7fkEwnUpEfI/JzSMSIKB\r\nG3T6625jvdmBq/X3Z3AIDyZ6I5MtP8ZlaNBwdI2ALgp07Z5rj342/Ty8upAOWQQ1\r\nyZ4g8fPgdcd6XF3Wl+2kVxKbLHcfKeAmnGzRDvVJ8b7al7OELu4P2MSYO9ZA1kpF\r\n1zKlUP+F4I22B/S6YAbqwO+PxQpEPBmb0ZcLWckAmNJyAWwdIT8k6RE1H2k0TwQr\r\nx4hn5L70IkxD/2kf0Cg3c0K0no/LzfUcJcwfEoWnm8ZWAI1DQixFC8DAvrvO2EHA\r\nLEqfTq3B6iUPz1Jik+Nwk/HwLbnvwMsF8rLh4CmAwH9OWzJWzD36XXXS57vk7fNK\r\niTqx\r\n=85ug\r\n-----END PGP MESSAGE-----\r\n','security.write'),
('key.ccf67f45-0846-46f9-ad46-c0fb09df9c25','security.read','-----BEGIN PGP MESSAGE-----\r\nVersion: BCPG v1.69\r\n\r\nhQEMA6Nw6GrTY6BpAQf/SmAXHuferQaSKqL7SMvxRKazQk8rqHB7DDx2t3R/3FK9\r\nHTpxY+bdJsA02nNuZPxvuw/vZMINa/UNS0a/KtN5vssi5rSEzXBSlfX/LyTbyf/c\r\nhfoJpn0h1cmxS1+/iHpoKVQGHvku+mCPJwTKi+zINQO6bGZdyg4XsAFrDeplEp4P\r\nGJ55zf6YtjW+2UeeDVTZTM2bbqOlzS6shETzLnaPv9Dq45C1Yb7AHU4Ms858DFjs\r\nNX/acuWDBXYVHFtPYVVAktqTxfe8hO9mLTk2tydmrx/qDuE8K++3pZef+oO7d+Ms\r\n8NaS+PM1AWlYh833AcqX/BG3pfqObDFk92nbEp/az9JyAaiUwMDDJsP0YPLZqi+M\r\nO+T0VKPrIvMeXDJqG6TSWBv9eENzXVidMcFD8lpqYt9fl4K5bq66RyKGzwJTcPn0\r\ndRtSN4M+cnt3T0DtuJuVQ2/ds082Ik+XgGi59L7NHld9gaoiIP9ROgHG95os6rDM\r\nO/SQ\r\n=DCLB\r\n-----END PGP MESSAGE-----\r\n','security.write'),
('key.cf457465-71e5-437d-863e-ee297c7e08bb','security.read','-----BEGIN PGP MESSAGE-----\r\nVersion: BCPG v1.69\r\n\r\nhQEMA6Nw6GrTY6BpAQf/R9s+sZTRmlMuzJodb/sVcAi+WnNYCb0H2x6lVMlgaMzq\r\nYA/4WqqgrZIHND/KXGLtekchYF/Bk0LTazc1fpSAIYb5FefTfinESL2WZRyFhtyK\r\nReENukQUX4KVluoIRmV2q2n4+tLGx6hilr15O49tSdo6UCWDLnfL2pfWtMpuihsC\r\nro28XxBqvqv+QbIrJ92wOZkj8mlbjB5djjhq1EHxrTW/oOFjEvGZ+I04AA1EGKk/\r\noq22n7SaCRqC6zxFXvCGKNQOJHe8qf7QIxAaUEWGBDQXfxo+CORcPuuBWPbZiYwA\r\nn9AeNN1PXSYqYKcF15z6QdOZRF34Kl7AD6rivDM7qdJyAeBUFs2u+b3SyR7U51TH\r\nnpjuPtK7UeCFgbuUfPgAdzAy9uAxHzsnEDxmhm7ksg8yhXuYp5kHGnm3hB7blUU8\r\n/hYkjoBnNXc/PY2xLF2YCDwVU17wxoaSgb5UTwLjEPhir2KMXHZ1XJmlRCSsgQSh\r\nQgOU\r\n=SOij\r\n-----END PGP MESSAGE-----\r\n','security.write'),
('key.cf944249-c5e6-4e71-979d-295520830664','security.read','-----BEGIN PGP MESSAGE-----\r\nVersion: BCPG v1.69\r\n\r\nhQEMA6Nw6GrTY6BpAQgAj2fh5jGA9iOKvzvF4KQMUdX98Pt9q/T95UrxptWSFDbx\r\ni0UGD+harHN6TM4fhmfEzkNH6hBHFhS+UkqGiTLkbf8DI230CnVFTvJu9dlFU1eG\r\nswtHN7cEiy8261N5iVe+0IgYoEgokskwyBAko4CqN3dQsF/MTg4OLcCCk9kBlzqw\r\n8CqB2zNtyy82CvY4klEcLkwr1uoZeoySPJVWCKuGxHsMILKHzDe7AhnNhwDW4QGb\r\nBvEy8kqhHo5wuTIGqrS8xsj+BSmmrSkcqqkcxCtAIGTppA5MTYuOBFAxhEsqrUSL\r\nNcvn1GCBjIF7HW9GdNC4opfitLQ+nZXzCfkOpUOqBdJyAdDD1HWGxmpqkpsdEpvo\r\neMjLWJEVPc+o6X/UawM/QaGr4BxdGaCZcajLyZ/klG/3Rgy+8CcBuTpEnj4l9ZW0\r\nPMoP1Xe1GxUSpLtRPDjwTi9GQabFythlEhPbiPolIMSVwyEpIYb1dxw6j1DbwNaQ\r\nkiY3\r\n=I5hc\r\n-----END PGP MESSAGE-----\r\n','security.write'),
('key.d341fe0b-df3a-44b8-8542-beb2f6742e33','security.read','-----BEGIN PGP MESSAGE-----\r\nVersion: BCPG v1.69\r\n\r\nhQEMA6Nw6GrTY6BpAQgAggyGAjTc7qZ/bi4DLwmufLb2j2PJI2kN3FehSjLeV1lu\r\nQQIVRHo0K6G/tJ2BQI47139ueoJa5e1Zye0Ri8sldM1xN509gECEotuZNPiLFswt\r\ny5+om7jctF2GNWLAJqkIVxe67qZMDIlJog2mL1NTLWfTuLK5pu6SZdcmcXGGeDCK\r\nnJRZQu+bHsomxyWR68Kwe38lCyONx0YsAd7SUIj4MV9+dDMddaUhk4Db1SRUvMun\r\np8cRf0L6X8boJq0E9LHyM/EA+GNuieUvwK0kHI5diBixCTbKqqGcEBHAULXGk5Jx\r\nG6eLQjfTbO1Ngdto/GL4InwGyumBQpRczmMq0g5NE9J0AUNLapR11+Z4KosA+e0z\r\nch2XV2BXEZN1dxBnV5nhG2FtUS85U/bpqLRQ/dgDhPe4qzfvv/EmvEqWCpcU4Ocg\r\nw+4FsSyPhoaRJIo49O5+RCSHSu8XMgjLmCdH9SLTuPZztCi7bqOIKhYxKDey2kre\r\n+ITgDgU=\r\n=M/KI\r\n-----END PGP MESSAGE-----\r\n','security.write'),
('key.d4887fa2-8115-41a8-be2e-2f7655fadc38','security.read','-----BEGIN PGP MESSAGE-----\r\nVersion: BCPG v1.69\r\n\r\nhQEMA6Nw6GrTY6BpAQgAnKz4/OlEP8YZ/3on8wFSqk0WHui6TFpHntdPwfKBpukS\r\n9+R9meotHYy3G3jaBclHRFG0SK8bINdQhNVT5LBgckzi61Yfqf8DD4cIz+9Fzk3p\r\n6nHYaz0gZ+lPey45sW5FSCzSPpKsjUgWAYjB7OfUY40neXKRF/rBx1JAoEdcjNme\r\nBt8hJg7h8hkTjttg2gBYrLKcH12cdyzpMClOBCC8Ciq6l9r8wDZLm0gi1HTexXik\r\nU/q++vpd1oc/BIU284VseM1yOKoVVW5lVt8BfDgkGkuL2yuF5fsvnNdm119UgsRt\r\nwNaCahSCKo4jEjaXVXXEXU47e3EQgQnO/cQmCzz6QtJ0AS8GYCdAUI9F+X9NYfRJ\r\nAtzPwhBvFYKtEBh/1i96Pw1WjkyKNbg941SSikeP9RgFNtgcvu42y2xHaBj7F8q6\r\nJPNi2gAZDX8P4U6zIYhwdfWSJW5WJzRFGbxT3GUK85uHP2gqCzc0K725RyTkId8d\r\nDI04arU=\r\n=O2pm\r\n-----END PGP MESSAGE-----\r\n','security.write'),
('key.db6a23fb-3488-444e-9b18-699b7106b5da','security.read','-----BEGIN PGP MESSAGE-----\r\nVersion: BCPG v1.69\r\n\r\nhQEMA6Nw6GrTY6BpAQf8Cr9VU3Dna6QJQ6CTXtfx8SJ3soRxrmGddAtN9NXhNaMB\r\n+S0+k3PPD6SE8lJSBUiJOBe5l8b0eTeNQIDpVfJDhgV7wtMnd0usUYsAudkJW7R0\r\nXON2X0X+v/3vV4qtvrOCZCWfemvy+ZyC4wLhJqZV8GTo7xPXubPUjygjtVf3meJ5\r\n3m//Yl1uET2bYgpyvodWb7vFnVKxji3zgu3mclbAjNa8gNXgF09s2rbi13E7H92M\r\n88o5UOIBycB9DrxCZbZPilPM10RNPKQ0oO8vqp2KS/2RHjOZLvaoj/Fn7RBh/jDZ\r\nRUvKdW/Q+CS3OJQ/sbsn6UyqxI2vhbyHa8hzwvcAPdJ0AXGpdg3o5AT5XiF24ruI\r\nTo2fXTy2ACuOtCKZ7Ck4wSSk7Az1XE+ZGSROHSk1Q29yA0cyNZuC+VloI5xkCs1U\r\nIbynxTSFwbgEmKl4nSPfQhiBQs8wOG8H7BT+YuvLCYhU4B0eFWyA+C4LR3OfaB2O\r\nFkC3bdI=\r\n=qc/J\r\n-----END PGP MESSAGE-----\r\n','security.write'),
('key.e01a8da5-3832-44f9-a031-ed5ba26f5022','security.read','-----BEGIN PGP MESSAGE-----\r\nVersion: BCPG v1.69\r\n\r\nhQEMA6Nw6GrTY6BpAQf/RT33OO3Xu50nJY7gkciFL5IxthqjvGuWgeelDm+RlM4l\r\nzow95hJSAjeHecwL5hRfl/R3mPfjJ0TkFlmYp4nhU+JjgWN/MJvA/N5lZ8LaoRR+\r\nQhB/qRIfhKXG1odCp6/hkoJPxWyDWt4jrYGhz29yVLWA9qB+2AhfwncF76lrdt0c\r\nWzKD/UIYIGljTbePNIgUtSmROXnT5iFZohez6JFMpn1qCl1gk0C6d0qmKezqw06H\r\ns3YCw6Tpf57Jz+aS3+RjVMJX2kVLnygj8tXQhP/fRw5upnFD9EpEFN6s0Ooki12s\r\nI5k3+eguS+y7FM0ov4ovO1wKW8vA9Opk08KNjYDdz9J0AedJs9+IVB9iirtLI3it\r\nsHXr1v8PuzR2dCXIArnZez1WDOBB57A5wAt1a3WCfuxrFhWMhoPjX4hjnn3UeDcz\r\nZB4ly8fD90uAa7vpCfb2yANw9KKZ0qj1vFik0tQIVfbIsL2u1exHr3+IvUizCi22\r\nH/zZYTM=\r\n=K2J6\r\n-----END PGP MESSAGE-----\r\n','security.write'),
('key.e1ac7a71-bc8a-419d-8bc3-da7cda1d0eee','security.read','-----BEGIN PGP MESSAGE-----\r\nVersion: BCPG v1.69\r\n\r\nhQEMA6Nw6GrTY6BpAQf9HEgztlU9djiX4CW2ZS2CmV+/vR9dblOJ7kMFHYigGEFQ\r\n3uEHJ9aTlP14SRAwQmWtwxR6iNNiIIIotN/Gx4xAERldzXfQTtbu26V/Xynept0k\r\nVuKiEWHpuC53uXK5uFIc9yzsgTFjR0IyWMvnXRZq+ac3qjfkpUqssMKk1BbeJB5E\r\nG/JAyCQ4dqA5p5/nhOkwQRexLDxGCTjs3csC185Z8fwbkZkszDAvgoEHQJ9mkci7\r\nFPkZe8yn7NgxC5sTskDzsgpIKQXiF1TFmyr81bfvRUt9SP0DutmK4F0kvlIRxZS9\r\nF7JQmaTo0Y7X1gGeiRCurXJW3MUMrsNymV1XDI8U5tJ0Aci6cixq2vSMJWZglDUp\r\nmPWtRRpM6soSlg3V9TZrsLur2zBu4Q5RTtcDNvkqTdmV4+NLGF/AjSyWkoDpHt/a\r\nLCLzxLUjSXIDr4fZtpO0PV+3jbSlARKSB7SOmUrsEE78bfmTNvQPyO8/IVQ3vRip\r\nFctjpOQ=\r\n=PpML\r\n-----END PGP MESSAGE-----\r\n','security.write'),
('key.e2a854c6-c486-422c-9565-b9026ef4872a','security.read','-----BEGIN PGP MESSAGE-----\r\nVersion: BCPG v1.69\r\n\r\nhQEMA6Nw6GrTY6BpAQgAix/6cctiWNN+3mLEKyxR4fKDp/fAYXpFezT0Rq9mYxGT\r\nvoJ/3vdYv4o40EgFYb6qYYOABb7JvG2z2s4mjtCWize6d3zzbb+Yp1MGXlsvoezY\r\n2UO6ELYY+LR6SXyCIAmydMsra5T9idUOIhuRkUoDG1t5cKxDYH6pDbSNHUeh/tM1\r\nsOAd5ZRbXpiZ/lMp6CSpsLptJboaFPjpgaA978dSAkBfcaiRllaC9ltCMZdpzrqw\r\nUMEtjmAT7DuXI6wMCN+Rs/Vb70JMRiZ+NJ8/jf31//J89TbRvM0RsWlfF0qdqwMu\r\nUux4i6Esdy++lb/dc96dGFlGnwSvhp3Kka0u4n9eatJyAVGDa3yhz950zyi9pWHp\r\nRG6GsC29sTku9yTXFvAfGwvuLi1YVQrF5kL8QkYiEAPibB0edWQtr1Cd0CAdNboC\r\nZAJSBX+tkugJcNX5cpbnGcsvbWBU8HMIY1Xsrze127OgsmYNDlNCohcOFcLGGSZX\r\nIb2a\r\n=6b1h\r\n-----END PGP MESSAGE-----\r\n','security.write'),
('key.e4903880-c61c-4369-bbb6-8870098ef989','security.read','-----BEGIN PGP MESSAGE-----\r\nVersion: BCPG v1.69\r\n\r\nhQEMA6Nw6GrTY6BpAQf/VeSBOaQfhuwlNnhiqekXP3PaOi1qUA7nkQSYn7bMgCnN\r\nQ6QucNIun4SzjUu9U8Kr2kJ2f+Q3ucHuFkG43mUd5JAeBoormaaKkCMklib9s4+8\r\nipMpJgG3hMh+57TsiSU7hrdBA8ecVXrf8jQeDXbT7B1Z5lw+XKRMl4II+kPgybJz\r\nGMEi/bebLiLM3ddA+hrD54uEtVdxqy/MS+CHdw1yJfy+DqMfXHPxrsaHwVl3u9uc\r\nCiCbEjJf8KWQhNxnEyd9RIiCMWC58f8049y3yBeht+tUJmDUbESx4kl9ZOVgNZpZ\r\n5CL61iHoPsTLwBSTayk128sI05usqIS32WrZ4SBvytJyAWt5b8pmv+W+8fDR8YDd\r\nJVdzKs1Z5+72LWlgKGxNnvlNyXYhbbdUFedNZdcI8sZNWV3kjRoS9QtpC+zW0fhU\r\nFXI/vUJfAuawNvjE0e96fzZD1tGDmHaR7L60swNH2+dpv2ssM+0fkW9Ohe1a5KnN\r\nRnBm\r\n=Wsgh\r\n-----END PGP MESSAGE-----\r\n','security.write'),
('key.e9b72779-0735-4e62-9e28-d22cf3ecc858','security.read','-----BEGIN PGP MESSAGE-----\r\nVersion: BCPG v1.69\r\n\r\nhQEMA6Nw6GrTY6BpAQgAzwqMMiWv2upo19DYIqPRFN1tDZX5uOPXD0+50sx3laAi\r\nDjSishul8/P66VlTuv/LJMVzKvXtjPtT1TujEiMSmgFunzS49xLxsi/iyAG/loJ/\r\nWOzVIHf8hwe9in9wxjM8RII/UI+0nLuIWxaAMKWbM6YqHzJx9ZVuKkbSOpUsX9fV\r\nxbRMke/iwvBybrzuYEbClTWGtIBm2CGZYScs5M8gkJMNQhBDGhd657crfD60d21S\r\nlLgQ40bo3QwgefEftNwvCc0fAWkzWAA8Lu/q/s+boAzE6oCe/Vkcc/DwUGLZQ45J\r\n6N9Tdl1fGlRpQnpAxjxFeVW88hrCzs0+KsoZ/+vCy9JyASbTPQ9cXUCbmZUGkT4F\r\nC8z42kKBYM6Fwd1dhAyWgFnCKzgLNtpfrLfXE7x8KRW43OL8+8i93byAj4zBTvE4\r\n/aLW54RfU0ZZJiZV10oUsWAJGcsGzu5pUDnHCmSqF7Ftc+SuYNZweuUBSJK1sUbX\r\nWAWy\r\n=fjyF\r\n-----END PGP MESSAGE-----\r\n','security.write'),
('key.e9cf9f34-698e-4f53-906f-8aaf7b9e38f2','security.read','-----BEGIN PGP MESSAGE-----\r\nVersion: BCPG v1.69\r\n\r\nhQEMA6Nw6GrTY6BpAQf/Y/GLdTt+3+46gSYr6vEpv7WGykoXxia18eTpBYNUKbPh\r\nP/yrW/QvQnWZ4KcojKsvTCE21avJiCZBrI6wkf3LH4n/h/knLSamSgcSqySo7g2u\r\nQIOG5Q0WQq/IRBKew+cAecubpwmvQiy5Az+H9/rkvi3flZADOFRg3zj5ajW0k5X3\r\nd3tks2EqbczO7Voqvk3Ie8tPGI0WOtAKN69M/IR5JFOIexPXrcGsnEZ7PEE+HpQp\r\nysvlZ2zj1IGw7ul2/nxZ7ZfM2scZKq2QJ1/vDD8b37DzCAb9Lc3/W0OeV49BpSx+\r\n+UUEJXILrMGtsbElM8eJCbpuzi+zSDZp6M19vnhdsNJ0AYvsjO5UTp9ugyuOOKcV\r\noIthS0t6jHv/LGhEHbxzhMt8vRmCzquOHCdzAzJXmQ1IZXOFbWx1BlmKSJLTIxGI\r\nKQYLPDo1NAvjkuZPYz2HSALmXVT4xNuVNELJDHg3qec3epyiLKJcuPHsUqyBtwui\r\n21w1UIE=\r\n=SFyP\r\n-----END PGP MESSAGE-----\r\n','security.write'),
('key.eafe43f1-8a35-4281-b609-7136d1e0950e','security.read','-----BEGIN PGP MESSAGE-----\r\nVersion: BCPG v1.69\r\n\r\nhQEMA6Nw6GrTY6BpAQgAgIb565Tb6tJ841E01sED9nwCsR/tuLmExuQKHC+fmjih\r\npRZbn2Nqpw0LxptOlE4hRwP4PzoZvkUV3Z/1RIQQOJbsb4+Rgm8cUsjrkQ42hMU/\r\njw6+UkqnvoYxR6hHmVme09cKczYD/3FgPb3d7Stu3AxHz+GHv3r0NIGoBwW7Xjpq\r\nz2tNGl9sLAf1vNk56YiZYt1wFBG0cYjRrTiU0tc+FM8iGGbE53MeOlkJtQOuHVF0\r\nncI8wrKAl7br/XTVvc9qWsKEMs4gse+BNbduiTCbJaAmZe05dvWXxm7G9fUyfZP+\r\nr6muqKf+j/AD0/YY02ZPaODDb0we1GYkyBvHNDTs/dJ0AbuaQGnYqrbRXnlUWqPc\r\nNnyhxIZrUt0wGWxPz5+tS3I1dWudXZIVeE7ggfg1pVhZVIGBCABKyQq3ZvDWFOnL\r\nD8q9Yoi5A8aof23BF3G4019ud1UVnLv9Ymh/eIH5M8Jfk38r30X/u/xjXaG/7TwO\r\nn/13YU0=\r\n=n1iU\r\n-----END PGP MESSAGE-----\r\n','security.write'),
('key.edf71fca-2062-4694-8997-68f06219464a','security.read','-----BEGIN PGP MESSAGE-----\r\nVersion: BCPG v1.69\r\n\r\nhQEMA6Nw6GrTY6BpAQf+KUw7V+7ubPu3W3xvAeLchvlh1lHpuNiFipErfvsF41z1\r\ntUiYZ9vzLneMKFSeLY/0MzpiE9rtIFlaU6f75Qqomm6Myx5abOvwASrL+RUUT+wT\r\nPOHpVv7r4lWbnmoYnLsH2KNgqFJiYL23d1kvD+ADEYw7oAs328fUJLWhz4nKElVL\r\nGo36eDPX88C7WpERzlExfjcZ+s6gjF59/yfl2ti7w4YgI6TJXl143LEZ96/LTG7r\r\nA3cwYwtsYsQDehwPckGPnFyVJVvBz2pecg4sRU317s6sfNMy19k26NveyNs9G6EA\r\n+2BqQvO8CmznI/9G3tX6+zR7gT8RCTz7dX5fEPQ+NdJxAaXEDf03cyoT7V3wa17l\r\nHx1FUXmfSG09BZjCKLl3WqOdPHkq8yl9E5glgJr/BdVLulrPQtDTWe0m7qelDst4\r\nXB/bwixm3Yf0HcMXHMHnK/UqZyqz085bHvEWTeef3f237YrcTG12vcA+5iq+6HkF\r\n7As=\r\n=bX5T\r\n-----END PGP MESSAGE-----\r\n','security.write'),
('key.f32f493e-0b8e-4daf-b717-96dfd6c95897','security.read','-----BEGIN PGP MESSAGE-----\r\nVersion: BCPG v1.69\r\n\r\nhQEMA6Nw6GrTY6BpAQf7BwLDqpVr3Psrm5GDLCv/x0cNv99QCCuER6qyIjN680Uj\r\nBwEOc1/s+MD9G8Lmc6mUdIJkuI3QKNB+lISAyCbdyVhI5oYiMQGKJdXiPbRXFfJB\r\nbgdp8Yyocse1KjJR3rhrTl0XOJUOfNckJKFM/IvfSk5ivq5noYytY/8jeM2DLL75\r\nVP3ubohWY2+BhHnAYkytL9eNwWzktHzOK6YzjpPWW1Es9ytY3r8+oEVxc8wD4KVm\r\n6POq3SnJAxvNnbvKiB5lYhHNwxbYAVJhRBEUxap0+KcfqxCLs87GQvYlaeMrlQG0\r\n161wJq/kXOmrrA9sQoeQmye0mblCPagHZrrEzqfEP9JyAcX3uC2+8QCkL/SuV0ZP\r\n8OzQ0ntpapr7Ao79Jcq+0JfvFt89TPmix4CxeFZvt6x3551vEnocjyigT3tRiWQS\r\nR0HaBzd7YcRZmYvBFBxr+kedmXSIHWa5hyYCTvUuVIufWX7DUymhkuYRCeh63N4p\r\nsZPF\r\n=VHsn\r\n-----END PGP MESSAGE-----\r\n','security.write'),
('key.f4565dd0-ceff-441c-bb22-f6aad28745b4','security.read','-----BEGIN PGP MESSAGE-----\r\nVersion: BCPG v1.69\r\n\r\nhQEMA6Nw6GrTY6BpAQf9Hw/MjbJA8nneTV1mBGrz2h61d8t9+JYO7KMdSLxb1MSF\r\n/w9U6wHKUeBy9Xm26Ai+mr/dVfBeAD31/kyWunHVOKUWTWGKPQrIYsK+Z+Q+dN7f\r\n694dYopKCuKCdXM8gByyoKGFfFZRXByf50Rj0CKOsStLKA/BlVjEdgEWuL/wKX1G\r\nUyYLNiNvwzoRxrP/AW2XlWtRkHzA1anRgbCev2Hm6JHtKR5PnPJze/tIlxmCz6Cj\r\n7KjHb/+gx8biQjS3TnD4sAQE0Lx0iKI9LLra66uecRANEbKPyXrKpzmz7Axg5oBU\r\nn2AUjsdGbxGq/0B2DVrvDjnbKRd560cPiae8b4qsnNJyARarAqmTKE72+otCJjV2\r\nTcyjF6ZiNnVTVMo1iWN7mmXMRBBbHLbYI/nrTh3WouufQ8jvXH/Nt6+09zkNZ/x8\r\nZT4V2FfGOGfzbhK4hxxxWuoidHn5C+noBaB/MrxZ/F/c69Nsg5/REX3eDs5T5m1/\r\nD7MU\r\n=EQgK\r\n-----END PGP MESSAGE-----\r\n','security.write'),
('key.f72f7884-d248-4c96-962b-21ee5f5aa075','security.read','-----BEGIN PGP MESSAGE-----\r\nVersion: BCPG v1.69\r\n\r\nhQEMA6Nw6GrTY6BpAQf/eaC/1cPMX2yQrltQySyhtoHMHrMyUZabD5g+Dwj4ieus\r\nEacIAjHS3kLUHI7Y09IZqaViwxZbZdGr57C7GwOKEYsK3Q2INngzL0J8GbNQEJe1\r\nQWh4p1qN5UPQRqDG9GGYPEMPVUlyEc8R5OWQRLyGqjKMhNPOG6gGUwYmGpDK08Zz\r\n+sr2ZXMf2q3D/HL3LVjBOy3H1TnjfUGWJnaL78cn/3SkF6L9W4FcQzO4cZy76FBs\r\natF0fDBkRr+TzYgDpy0B6SYpx4XQ3xb9vPH8roL7EE4drXXZq7hrqcaBA+1IXZEO\r\nOm4ZovJvRMUCgdGhL9rx/WaaEE7qxkQsfvkf+vZt3tJ0AerBXsCHd7gTznE4oqh1\r\niQSFuA3h0r5dwUg53CZ94LoKyo+W6sn2MheAScQbnFNCVyamwQnyjgCFqkn+BzFl\r\nAT2B0iBdkXRjGD9760v3JIoPsh0czecMLfLnUxL//IoBAIIaDDgOcD9a4KYUVSZr\r\nUmdP1TQ=\r\n=KI3r\r\n-----END PGP MESSAGE-----\r\n','security.write'),
('key.f841ac03-c5d9-4ea2-be65-97da06e3ddc2','security.read','-----BEGIN PGP MESSAGE-----\r\nVersion: BCPG v1.69\r\n\r\nhQEMA6Nw6GrTY6BpAQgAkZ9VXqZ5GeA3fHUDJNZWbjeX093QX4YgPEsg2J7XkyZh\r\nODB/94qbQb6Su4kGISkwX5sWqC8TkIz4PYRPuBPBH2uEjM4Loow+/7ZFz78yCRZE\r\nH530NGojlV4eAyBr2KuIiayTy7o1TemeTslJXKs7tiUR/WIeei2unMRmFPlFM5js\r\nkf+HW67RTrFem8l8LqnK8HgQHsS+pvdSRMYgJD7rZb6+GKtx/OJXsM7F4Flrno+u\r\nqzVZyV08hil/H7Yk7+poQOJaJWYpCkTSabAEQVVcaYlMEzDvKaKCmTftvI5oPIel\r\noC4q649ZiAa3CM8b3+2omfuABlp8i92vfzUny2d+ztJyARVzWkjqGk0+U5CQKwT1\r\n6qWX6FY1Tx3r72j6P/bIONu2xeOL261TmaekdIPGdiU0Naz6y1dED4609NCW6wZe\r\n9Lt+CHW2sXE5x/VoWdwsVwU3tiHteysYD+ABuGvbzbM/aAscPA0k1YsfnAKyANwb\r\nd66U\r\n=sX5Z\r\n-----END PGP MESSAGE-----\r\n','security.write'),
('key.fc33ab08-1828-402a-9f57-4e8600970879','security.read','-----BEGIN PGP MESSAGE-----\r\nVersion: BCPG v1.69\r\n\r\nhQEMA6Nw6GrTY6BpAQf+IGrEaXvg+uJoaokAWUkQRrvNlUgBxdj+qOmRmIrCBx3/\r\nN+vuiPgylz5w2c6hi5MJbDbbuoNeNzHwmV6rvm0YHmVXhfJ4Yk74gfzpFZeqS+oW\r\nu4wXo2cqX+9kwzf/bDGuvWVHFMuDrlIK1PNZ/9KUQ8jxyOs1TgM7lTjD75h7VpXf\r\n4Gvnesz/Xd8uiV2XNdIYAx8nTFCGB6g2f5+goyBnXjX56u0eHN4Z86zcHLk9+1qf\r\nW4IURODq3XtYtxnmQC/VsmQYOXPndSBHLy1Zk5jmHkvH1dFlVG1JP1pEoB4eivdW\r\nu0LT29uRGS81G3Q2ekUbM2iW4qVGKdR4VkW0AWcWQ9J0AbMzPyM1+/K4o6omHdtc\r\ngOB00OhPGQxSvrOkdk1FHqSi4Qivs4oCrp3rK91cDaW64HfDfU/rPimAktG+h8SM\r\nokZai5oV/URdVTlksfQmu/2H9xb0qDK746JNao3kkRRcU9npDvYvGSSqdmWtxcYm\r\nhx6zh9o=\r\n=7gUv\r\n-----END PGP MESSAGE-----\r\n','security.write'),
('perm.login','sysconfig.read','Can log into the system','sysconfig.write'),
('perm.sysadmin','sysconfig.read','Has administrator privileges','sysconfig.write'),
('perm.sysconfig','sysconfig.read','Has access to sysconfig records','sysconfig.write'),
('sys.MAX_LOGIN_ATTEMPTS','sysconfig.read','5','sysconfig.write'),
('sys.PASSWORD_AGE','sysconfig.read','90','sysconfig.write'),
('sys.REMEMBER_PASSWORD_ENABLED','sysconfig.read','true','sysconfig.write');

/*Table structure for table `syslog` */

DROP TABLE IF EXISTS `syslog`;

CREATE TABLE `syslog` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `date` datetime DEFAULT NULL,
  `deleted` char(1) DEFAULT NULL,
  `source` varchar(64) DEFAULT NULL,
  `type` varchar(32) DEFAULT NULL,
  `severity` int(11) DEFAULT NULL,
  `summary` varchar(255) DEFAULT NULL,
  `detail` varchar(4000) DEFAULT NULL,
  `trace` varchar(4000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=173 DEFAULT CHARSET=utf8mb4;

/*Data for the table `syslog` */

insert  into `syslog`(`id`,`date`,`deleted`,`source`,`type`,`severity`,`summary`,`detail`,`trace`) values 
(1,'2022-03-28 17:53:48','N','testbed','SYSTEM',2,'Start',NULL,NULL),
(2,'2022-03-28 18:34:57','N','testbed','SYSTEM',2,'Stop (uptime 41m10s)',NULL,NULL),
(3,'2022-03-29 09:18:29','N','testbed','SYSTEM',2,'Start',NULL,NULL),
(4,'2022-03-29 09:20:19','N','testbed','SYSTEM',2,'Stop (uptime 1m52s)',NULL,NULL),
(5,'2022-03-29 09:21:02','N','testbed','SYSTEM',2,'Start',NULL,NULL),
(6,'2022-03-29 09:23:30','N','testbed','SYSTEM',2,'Stop (uptime 2m29s)',NULL,NULL),
(7,'2022-03-29 09:24:45','N','testbed','SYSTEM',2,'Start',NULL,NULL),
(8,'2022-03-29 09:25:45','N','testbed','SYSTEM',2,'Stop (uptime 1m2s)',NULL,NULL),
(9,'2022-03-29 09:33:41','N','testbed','SYSTEM',2,'Start',NULL,NULL),
(10,'2022-03-29 09:40:03','N','testbed','SYSTEM',2,'Stop (uptime 6m23s)',NULL,NULL),
(11,'2022-03-29 09:40:55','N','testbed','SYSTEM',2,'Start',NULL,NULL),
(12,'2022-03-29 09:42:48','N','testbed','SYSTEM',2,'Stop (uptime 1m55s)',NULL,NULL),
(13,'2022-03-29 09:44:08','N','testbed','SYSTEM',2,'Start',NULL,NULL),
(14,'2022-03-29 09:45:03','N','testbed','SYSTEM',2,'Stop (uptime 57s)',NULL,NULL),
(15,'2022-03-29 09:54:21','N','testbed','SYSTEM',2,'Start',NULL,NULL),
(16,'2022-03-29 09:57:55','N','testbed','SYSTEM',2,'Stop (uptime 3m35s)',NULL,NULL),
(17,'2022-03-29 09:58:53','N','testbed','SYSTEM',2,'Start',NULL,NULL),
(18,'2022-03-29 09:59:01','N','testbed','SYSTEM',2,'Stop (uptime 9s)',NULL,NULL),
(19,'2022-03-29 10:02:01','N','testbed','SYSTEM',2,'Start',NULL,NULL),
(20,'2022-03-29 10:07:19','N','testbed','SYSTEM',2,'Stop (uptime 5m20s)',NULL,NULL),
(21,'2022-03-29 10:19:58','N','testbed','SYSTEM',2,'Start',NULL,NULL),
(22,'2022-03-29 11:17:04','N','testbed','SYSTEM',2,'Stop (uptime 57m7s)',NULL,NULL),
(23,'2022-03-29 11:24:29','N','testbed','SYSTEM',2,'Start',NULL,NULL),
(24,'2022-03-29 11:27:29','N','testbed','SYSTEM',2,'Stop (uptime 3m1s)',NULL,NULL),
(25,'2022-03-29 11:28:57','N','testbed','SYSTEM',2,'Start',NULL,NULL),
(26,'2022-03-29 11:31:14','N','testbed','SYSTEM',2,'Stop (uptime 2m18s)',NULL,NULL),
(27,'2022-03-29 11:33:43','N','testbed','SYSTEM',2,'Start',NULL,NULL),
(28,'2022-03-29 11:43:49','N','testbed','SYSTEM',2,'Stop (uptime 10m7s)',NULL,NULL),
(29,'2022-03-29 11:44:03','N','testbed','SYSTEM',2,'Start',NULL,NULL),
(30,'2022-03-29 11:48:17','N','testbed','SYSTEM',2,'Stop (uptime 4m16s)',NULL,NULL),
(31,'2022-03-29 11:49:01','N','testbed','SYSTEM',2,'Start',NULL,NULL),
(32,'2022-03-29 11:51:56','N','testbed','SYSTEM',2,'Stop (uptime 2m56s)',NULL,NULL),
(33,'2022-03-29 11:53:14','N','testbed','SYSTEM',2,'Start',NULL,NULL),
(34,'2022-03-29 11:53:21','N','testbed','SYSTEM',2,'Stop (uptime 8s)',NULL,NULL),
(35,'2022-03-29 11:58:34','N','testbed','SYSTEM',2,'Start',NULL,NULL),
(36,'2022-03-29 12:16:02','N','testbed','SYSTEM',2,'Stop (uptime 17m29s)',NULL,NULL),
(37,'2022-03-29 12:16:09','N','testbed','SYSTEM',2,'Start',NULL,NULL),
(38,'2022-03-29 12:20:17','N','testbed','SYSTEM',2,'Stop (uptime 4m9s)',NULL,NULL),
(39,'2022-03-29 12:20:23','N','testbed','SYSTEM',2,'Start',NULL,NULL),
(40,'2022-03-29 12:22:15','N','testbed','SYSTEM',2,'Stop (uptime 1m53s)',NULL,NULL),
(41,'2022-03-29 12:22:21','N','testbed','SYSTEM',2,'Start',NULL,NULL),
(42,'2022-03-29 12:24:23','N','testbed','SYSTEM',2,'Stop (uptime 2m3s)',NULL,NULL),
(43,'2022-03-29 12:24:28','N','testbed','SYSTEM',2,'Start',NULL,NULL),
(44,'2022-03-29 12:27:42','N','testbed','SYSTEM',2,'Stop (uptime 3m15s)',NULL,NULL),
(45,'2022-03-29 12:27:45','N','testbed','SYSTEM',2,'Start',NULL,NULL),
(46,'2022-03-29 12:27:49','N','testbed','SYSTEM',2,'Stop (uptime 5s)',NULL,NULL),
(47,'2022-03-29 12:28:14','N','testbed','SYSTEM',2,'Start',NULL,NULL),
(48,'2022-03-29 12:31:06','N','testbed','SYSTEM',2,'Stop (uptime 2m53s)',NULL,NULL),
(49,'2022-03-29 12:32:32','N','testbed','SYSTEM',2,'Start',NULL,NULL),
(50,'2022-03-29 12:44:24','N','testbed','SYSTEM',2,'Stop (uptime 11m54s)',NULL,NULL),
(51,'2022-03-29 12:44:31','N','testbed','SYSTEM',2,'Start',NULL,NULL),
(52,'2022-03-29 12:45:52','N','testbed','SYSTEM',2,'Stop (uptime 1m23s)',NULL,NULL),
(53,'2022-03-29 12:45:58','N','testbed','SYSTEM',2,'Start',NULL,NULL),
(54,'2022-03-29 12:52:43','N','testbed','SYSTEM',2,'Stop (uptime 6m46s)',NULL,NULL),
(55,'2022-03-29 12:52:52','N','testbed','SYSTEM',2,'Start',NULL,NULL),
(56,'2022-03-29 12:54:57','N','testbed','SYSTEM',2,'Stop (uptime 2m8s)',NULL,NULL),
(57,'2022-03-29 12:55:08','N','testbed','SYSTEM',2,'Start',NULL,NULL),
(58,'2022-03-29 12:56:58','N','testbed','SYSTEM',2,'Stop (uptime 1m51s)',NULL,NULL),
(59,'2022-03-29 12:57:22','N','testbed','SYSTEM',2,'Start',NULL,NULL),
(60,'2022-03-29 12:58:20','N','testbed','SYSTEM',2,'Stop (uptime 59s)',NULL,NULL),
(61,'2022-03-29 12:58:25','N','testbed','SYSTEM',2,'Start',NULL,NULL),
(62,'2022-03-29 12:59:01','N','testbed','SYSTEM',2,'Stop (uptime 38s)',NULL,NULL),
(63,'2022-03-29 13:01:22','N','testbed','SYSTEM',2,'Start',NULL,NULL),
(64,'2022-03-29 13:51:54','N','testbed','SYSTEM',2,'Stop (uptime 50m33s)',NULL,NULL),
(65,'2022-03-29 13:53:06','N','testbed','SYSTEM',2,'Start',NULL,NULL),
(66,'2022-03-29 13:55:14','N','testbed','SYSTEM',2,'Stop (uptime 2m10s)',NULL,NULL),
(67,'2022-03-29 13:55:15','N','testbed','SYSTEM',2,'Start',NULL,NULL),
(68,'2022-03-29 13:55:29','N','testbed','SYSTEM',2,'Stop (uptime 14s)',NULL,NULL),
(69,'2022-03-29 13:55:29','N','testbed','SYSTEM',2,'Start',NULL,NULL),
(70,'2022-03-29 13:55:59','N','testbed','SYSTEM',2,'Stop (uptime 30s)',NULL,NULL),
(71,'2022-03-29 13:55:59','N','testbed','SYSTEM',2,'Start',NULL,NULL),
(72,'2022-03-29 13:56:12','N','testbed','SYSTEM',2,'Stop (uptime 12s)',NULL,NULL),
(73,'2022-03-29 13:56:34','N','testbed','SYSTEM',2,'Start',NULL,NULL),
(74,'2022-03-29 14:10:14','N','testbed','SYSTEM',2,'Stop (uptime 13m41s)',NULL,NULL),
(75,'2022-03-29 14:10:14','N','testbed','SYSTEM',2,'Start',NULL,NULL),
(76,'2022-03-29 14:10:27','N','testbed','SYSTEM',2,'Stop (uptime 12s)',NULL,NULL),
(77,'2022-03-29 14:11:02','N','testbed','SYSTEM',2,'Start',NULL,NULL),
(78,'2022-03-29 14:13:49','N','testbed','SYSTEM',2,'Stop (uptime 2m48s)',NULL,NULL),
(79,'2022-03-29 14:14:10','N','testbed','SYSTEM',2,'Start',NULL,NULL),
(80,'2022-03-29 14:16:12','N','testbed','SYSTEM',2,'Stop (uptime 2m3s)',NULL,NULL),
(81,'2022-03-29 14:16:35','N','testbed','SYSTEM',2,'Start',NULL,NULL),
(82,'2022-03-29 14:17:24','N','testbed','SYSTEM',2,'Stop (uptime 50s)',NULL,NULL),
(83,'2022-03-29 14:17:45','N','testbed','SYSTEM',2,'Start',NULL,NULL),
(84,'2022-03-29 14:20:16','N','testbed','SYSTEM',2,'Stop (uptime 2m33s)',NULL,NULL),
(85,'2022-03-29 14:20:41','N','testbed','SYSTEM',2,'Start',NULL,NULL),
(86,'2022-03-29 14:25:48','N','testbed','SYSTEM',2,'Stop (uptime 5m9s)',NULL,NULL),
(87,'2022-03-29 14:37:52','N','testbed','SYSTEM',2,'Start',NULL,NULL),
(88,'2022-03-29 14:40:44','N','testbed','SYSTEM',2,'Stop (uptime 2m53s)',NULL,NULL),
(89,'2022-03-29 14:43:17','N','testbed','SYSTEM',2,'Start',NULL,NULL),
(90,'2022-03-29 14:48:52','N','testbed','SYSTEM',2,'Stop (uptime 5m37s)',NULL,NULL),
(91,'2022-03-29 14:49:12','N','testbed','SYSTEM',2,'Start',NULL,NULL),
(92,'2022-03-29 15:02:47','N','testbed','SYSTEM',2,'Stop (uptime 13m36s)',NULL,NULL),
(93,'2022-03-29 15:02:47','N','testbed','SYSTEM',2,'Start',NULL,NULL),
(94,'2022-03-29 15:03:03','N','testbed','SYSTEM',2,'Stop (uptime 15s)',NULL,NULL),
(95,'2022-03-29 15:03:19','N','testbed','SYSTEM',2,'Start',NULL,NULL),
(96,'2022-03-29 15:05:59','N','testbed','SYSTEM',2,'Stop (uptime 2m41s)',NULL,NULL),
(97,'2022-03-29 15:07:03','N','testbed','SYSTEM',2,'Start',NULL,NULL),
(98,'2022-03-29 15:07:19','N','testbed','SYSTEM',2,'Stop (uptime 18s)',NULL,NULL),
(99,'2022-03-29 15:07:34','N','testbed','SYSTEM',2,'Start',NULL,NULL),
(100,'2022-03-29 15:08:03','N','testbed','SYSTEM',2,'Stop (uptime 31s)',NULL,NULL),
(101,'2022-03-29 15:08:19','N','testbed','SYSTEM',2,'Start',NULL,NULL),
(102,'2022-03-29 15:08:23','N','testbed','SYSTEM',2,'Stop (uptime 6s)',NULL,NULL),
(103,'2022-03-29 15:08:40','N','testbed','SYSTEM',2,'Start',NULL,NULL),
(104,'2022-03-29 15:09:34','N','testbed','SYSTEM',2,'Stop (uptime 56s)',NULL,NULL),
(105,'2022-03-29 15:10:31','N','testbed','SYSTEM',2,'Start',NULL,NULL),
(106,'2022-03-29 15:13:06','N','testbed','SYSTEM',2,'Stop (uptime 2m36s)',NULL,NULL),
(107,'2022-03-29 15:13:49','N','testbed','SYSTEM',2,'Start',NULL,NULL),
(108,'2022-03-29 15:14:36','N','testbed','SYSTEM',2,'Stop (uptime 48s)',NULL,NULL),
(109,'2022-03-29 15:15:08','N','testbed','SYSTEM',2,'Start',NULL,NULL),
(110,'2022-03-29 15:16:28','N','testbed','SYSTEM',2,'Stop (uptime 1m21s)',NULL,NULL),
(111,'2022-03-29 15:17:02','N','testbed','SYSTEM',2,'Start',NULL,NULL),
(112,'2022-03-29 15:19:01','N','testbed','SYSTEM',2,'Stop (uptime 2m0s)',NULL,NULL),
(113,'2022-03-29 15:19:01','N','testbed','SYSTEM',2,'Start',NULL,NULL),
(114,'2022-03-29 15:19:12','N','testbed','SYSTEM',2,'Stop (uptime 10s)',NULL,NULL),
(115,'2022-03-29 15:19:37','N','testbed','SYSTEM',2,'Start',NULL,NULL),
(116,'2022-03-29 15:21:54','N','testbed','SYSTEM',2,'Stop (uptime 2m18s)',NULL,NULL),
(117,'2022-03-29 15:22:03','N','testbed','SYSTEM',2,'Start',NULL,NULL),
(118,'2022-03-29 15:26:34','N','testbed','SYSTEM',2,'Stop (uptime 4m34s)',NULL,NULL),
(119,'2022-03-29 15:26:59','N','testbed','SYSTEM',2,'Start',NULL,NULL),
(120,'2022-03-29 15:29:46','N','testbed','SYSTEM',2,'Stop (uptime 2m48s)',NULL,NULL),
(121,'2022-03-29 15:33:37','N','testbed','SYSTEM',2,'Start',NULL,NULL),
(122,'2022-03-29 15:37:27','N','testbed','SYSTEM',2,'Stop (uptime 3m52s)',NULL,NULL),
(123,'2022-03-29 15:37:50','N','testbed','SYSTEM',2,'Start',NULL,NULL),
(124,'2022-03-29 15:39:27','N','testbed','SYSTEM',2,'Stop (uptime 1m38s)',NULL,NULL),
(125,'2022-03-29 15:39:47','N','testbed','SYSTEM',2,'Start',NULL,NULL),
(126,'2022-03-29 15:43:05','N','testbed','SYSTEM',2,'Stop (uptime 3m19s)',NULL,NULL),
(127,'2022-03-29 15:43:26','N','testbed','SYSTEM',2,'Start',NULL,NULL),
(128,'2022-03-29 16:15:43','N','testbed','SYSTEM',2,'Stop (uptime 32m19s)',NULL,NULL),
(129,'2022-03-29 16:17:38','N','testbed','SYSTEM',2,'Start',NULL,NULL),
(130,'2022-03-29 16:21:30','N','testbed','SYSTEM',2,'Stop (uptime 3m54s)',NULL,NULL),
(131,'2022-03-29 16:21:54','N','testbed','SYSTEM',2,'Start',NULL,NULL),
(132,'2022-03-29 16:24:41','N','testbed','SYSTEM',2,'Stop (uptime 2m49s)',NULL,NULL),
(133,'2022-03-29 16:24:50','N','testbed','SYSTEM',2,'Start',NULL,NULL),
(134,'2022-03-29 16:28:44','N','testbed','SYSTEM',2,'Stop (uptime 3m57s)',NULL,NULL),
(135,'2022-03-29 16:31:56','N','testbed','SYSTEM',2,'Start',NULL,NULL),
(136,'2022-03-29 16:34:08','N','testbed','SYSTEM',2,'Stop (uptime 2m14s)',NULL,NULL),
(137,'2022-03-29 16:34:16','N','testbed','SYSTEM',2,'Start',NULL,NULL),
(138,'2022-03-29 17:17:31','N','testbed','SYSTEM',2,'Stop (uptime 43m16s)',NULL,NULL),
(139,'2022-03-29 17:18:22','N','testbed','SYSTEM',2,'Start',NULL,NULL),
(140,'2022-03-29 17:35:20','N','testbed','SYSTEM',2,'Stop (uptime 16m59s)',NULL,NULL),
(141,'2022-03-29 17:35:40','N','testbed','SYSTEM',2,'Start',NULL,NULL),
(142,'2022-03-29 17:40:35','N','testbed','SYSTEM',2,'Stop (uptime 4m57s)',NULL,NULL),
(143,'2022-03-29 17:41:05','N','testbed','SYSTEM',2,'Start',NULL,NULL),
(144,'2022-03-29 17:52:56','N','testbed','SYSTEM',2,'Stop (uptime 11m53s)',NULL,NULL),
(145,'2022-03-29 17:53:14','N','testbed','SYSTEM',2,'Start',NULL,NULL),
(146,'2022-03-29 18:21:12','N','testbed','SYSTEM',2,'Stop (uptime 27m59s)',NULL,NULL),
(147,'2022-03-29 18:21:35','N','testbed','SYSTEM',2,'Start',NULL,NULL),
(148,'2022-03-29 18:47:37','N','testbed','SYSTEM',2,'Stop (uptime 26m4s)',NULL,NULL),
(149,'2022-03-29 18:48:00','N','testbed','SYSTEM',2,'Start',NULL,NULL),
(150,'2022-03-29 18:55:53','N','testbed','SYSTEM',2,'Stop (uptime 7m55s)',NULL,NULL),
(151,'2022-03-29 18:56:15','N','testbed','SYSTEM',2,'Start',NULL,NULL),
(152,'2022-03-29 18:57:19','N','testbed','SYSTEM',2,'Stop (uptime 1m6s)',NULL,NULL),
(153,'2022-03-30 04:07:21','N','testbed','SYSTEM',2,'Start',NULL,NULL),
(154,'2022-03-30 04:13:06','N','testbed','SYSTEM',2,'Stop (uptime 5m47s)',NULL,NULL),
(155,'2022-03-30 04:13:55','N','testbed','SYSTEM',2,'Start',NULL,NULL),
(156,'2022-03-30 04:14:47','N','testbed','SYSTEM',2,'Stop (uptime 53s)',NULL,NULL),
(157,'2022-03-30 04:15:09','N','testbed','SYSTEM',2,'Start',NULL,NULL),
(158,'2022-03-30 04:19:21','N','testbed','SYSTEM',2,'Stop (uptime 4m13s)',NULL,NULL),
(159,'2022-03-30 04:19:21','N','testbed','SYSTEM',2,'Start',NULL,NULL),
(160,'2022-03-30 04:19:33','N','testbed','SYSTEM',2,'Stop (uptime 12s)',NULL,NULL),
(161,'2022-03-30 04:21:43','N','testbed','SYSTEM',2,'Start',NULL,NULL),
(162,'2022-03-30 04:36:13','N','testbed','SYSTEM',2,'Stop (uptime 14m31s)',NULL,NULL),
(163,'2022-03-30 04:36:41','N','testbed','SYSTEM',2,'Start',NULL,NULL),
(164,'2022-03-30 04:51:23','N','testbed','SYSTEM',2,'Stop (uptime 14m44s)',NULL,NULL),
(165,'2022-03-30 04:52:03','N','testbed','SYSTEM',2,'Start',NULL,NULL),
(166,'2022-03-30 05:06:54','N','testbed','SYSTEM',2,'Stop (uptime 14m53s)',NULL,NULL),
(167,'2022-03-30 05:06:54','N','testbed','SYSTEM',2,'Start',NULL,NULL),
(168,'2022-03-30 05:07:09','N','testbed','SYSTEM',2,'Stop (uptime 15s)',NULL,NULL),
(169,'2022-03-30 05:07:33','N','testbed','SYSTEM',2,'Start',NULL,NULL),
(170,'2022-03-30 05:09:42','N','testbed','SYSTEM',2,'Stop (uptime 2m11s)',NULL,NULL),
(171,'2022-03-30 05:10:09','N','testbed','SYSTEM',2,'Start',NULL,NULL),
(172,'2022-03-30 05:37:09','N','testbed','SYSTEM',2,'Stop (uptime 27m1s)',NULL,NULL);

/*Table structure for table `trndefs` */

DROP TABLE IF EXISTS `trndefs`;

CREATE TABLE `trndefs` (
  `trndef_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `trndef_config_id` varchar(255) NOT NULL,
  `trndef_atm_trn_code` varchar(255) NOT NULL,
  `trndef_mti` varchar(255) NOT NULL,
  `trndef_description` varchar(255) NOT NULL,
  `trndef_currency_code` varchar(3) NOT NULL,
  `trndef_language3166` varchar(255) NOT NULL,
  `trndef_language639` varchar(255) NOT NULL,
  `trndef_procesingcode` varchar(255) NOT NULL,
  `trndef_receipt_code` varchar(255) NOT NULL,
  `trndef_respMaskScr` varchar(255) NOT NULL,
  `trndef_screen` varchar(255) NOT NULL,
  `trndef_screen_update` varchar(255) NOT NULL,
  `trndef_state` varchar(255) NOT NULL,
  PRIMARY KEY (`trndef_id`),
  UNIQUE KEY `trndef` (`trndef_config_id`,`trndef_atm_trn_code`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;

/*Data for the table `trndefs` */

insert  into `trndefs`(`trndef_id`,`trndef_config_id`,`trndef_atm_trn_code`,`trndef_mti`,`trndef_description`,`trndef_currency_code`,`trndef_language3166`,`trndef_language639`,`trndef_procesingcode`,`trndef_receipt_code`,`trndef_respMaskScr`,`trndef_screen`,`trndef_screen_update`,`trndef_state`) values 
(1,'0870','AIA     ','0200','BALANCE INQUIRY SAVINGS','840','840','eng','301000','00000001','FN${vars.balance} ','171','172','171'),
(2,'0870','BIA     ','0200','BALANCE INQUIRY SAVINGS','840','360','ind','301000','00000001','FN${vars.balance} ','171','572','171'),
(3,'0850','  C     ','0200','BALANCE INQUIRY','840','840','eng','301000','','','052','','470');

/*Table structure for table `visitor` */

DROP TABLE IF EXISTS `visitor`;

CREATE TABLE `visitor` (
  `id` varchar(36) NOT NULL,
  `lastUpdate` datetime DEFAULT NULL,
  `eeuser` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKdhw90brojvr9qrfv3lvvxnf8` (`eeuser`),
  CONSTRAINT `FKdhw90brojvr9qrfv3lvvxnf8` FOREIGN KEY (`eeuser`) REFERENCES `eeuser` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `visitor` */

insert  into `visitor`(`id`,`lastUpdate`,`eeuser`) values 
('921432ee-4324-4ba8-91a6-9bdf87f7a36f','2022-05-04 15:10:26',NULL),
('aa9fd97a-8332-4cac-bad0-6935385ffb27','2022-05-14 18:01:19',NULL);

/*Table structure for table `visitor_props` */

DROP TABLE IF EXISTS `visitor_props`;

CREATE TABLE `visitor_props` (
  `id` varchar(36) NOT NULL,
  `propName` varchar(32) NOT NULL,
  `propValue` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`,`propName`),
  CONSTRAINT `FK6tpnppnhia9pvreurkhechuv6` FOREIGN KEY (`id`) REFERENCES `visitor` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `visitor_props` */

insert  into `visitor_props`(`id`,`propName`,`propValue`) values 
('921432ee-4324-4ba8-91a6-9bdf87f7a36f','HOST','[0:0:0:0:0:0:0:1]'),
('921432ee-4324-4ba8-91a6-9bdf87f7a36f','IP','[0:0:0:0:0:0:0:1]'),
('aa9fd97a-8332-4cac-bad0-6935385ffb27','HOST','127.0.0.1'),
('aa9fd97a-8332-4cac-bad0-6935385ffb27','IP','127.0.0.1'),
('aa9fd97a-8332-4cac-bad0-6935385ffb27','USERS','admin(1)');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
