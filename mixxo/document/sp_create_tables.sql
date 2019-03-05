DROP TABLE IF EXISTS `mixxo_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mixxo_user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `open_id` varchar(50) NOT NULL,
  `telephone` varchar(15) NOT NULL,
  `nickname` varchar(50),
  `create_time` datetime NOT NULL,
  `status` int NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;



DROP TABLE IF EXISTS `mixxo_serial_number`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mixxo_serial_number` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `telephone` varchar(15) NOT NULL,
  `serial_number` varchar(250) NOT NULL,
  `receip_path` varchar(250),
  `create_time` datetime NOT NULL,
  `valid_status` tinyint(4) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;



DROP TABLE IF EXISTS `mixxo_draw_card`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mixxo_draw_card` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `telephone` varchar(15) NOT NULL,
  `card_number` int(4) NOT NULL,
  `friend_openid` varchar(50) NOT NULL,
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;


DROP TABLE IF EXISTS `mixxo_user_coupon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mixxo_user_coupon` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `telephone` varchar(15) NOT NULL,
  `coupon_number` varchar(250) NOT NULL,
  `status` tinyint(4) NOT NULL,
  `create_time` datetime NOT NULL,
  `expire_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;


DROP TABLE IF EXISTS `mixxo_access_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mixxo_access_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `openid` varchar(50) NOT NULL,
  `nickname` varchar(50),
  `access_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;


DROP TABLE IF EXISTS `mixxo_win_prize`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mixxo_win_prize` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `openid` varchar(50) NOT NULL,
  `telephone` varchar(15) NOT NULL,
  `nickname` varchar(50),
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;


DROP TABLE IF EXISTS `mixxo_sendsms_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mixxo_sendsms_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `openid` varchar(50) NOT NULL,
  `user_ip` varchar(200) NOT NULL,
  `telephone` varchar(15) NOT NULL,
  `status` int not null,
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;