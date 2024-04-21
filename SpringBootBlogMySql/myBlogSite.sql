-- MySQL dump 10.13  Distrib 8.0.27, for Win64 (x86_64)
--
-- Host: localhost    Database: sg_blog
-- ------------------------------------------------------
-- Server version	8.0.27

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `sg_article`
--

DROP TABLE IF EXISTS `sg_article`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sg_article` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `title` varchar(256) DEFAULT NULL COMMENT '鏍囬',
  `content` longtext COMMENT '鏂囩珷鍐呭',
  `summary` varchar(1024) DEFAULT NULL COMMENT '鏂囩珷鎽樿',
  `category_id` bigint DEFAULT NULL COMMENT '鎵€灞炲垎绫籭d',
  `thumbnail` varchar(256) DEFAULT NULL COMMENT '缂╃暐鍥?,
  `is_top` char(1) DEFAULT '0' COMMENT '鏄惁缃《锛?鍚︼紝1鏄級',
  `status` char(1) DEFAULT '1' COMMENT '鐘舵€侊紙0宸插彂甯冿紝1鑽夌锛?,
  `view_count` bigint DEFAULT '0' COMMENT '璁块棶閲?,
  `is_comment` char(1) DEFAULT '1' COMMENT '鏄惁鍏佽璇勮 1鏄紝0鍚?,
  `create_by` bigint DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` bigint DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `del_flag` int DEFAULT '0' COMMENT '鍒犻櫎鏍囧織锛?浠ｈ〃鏈垹闄わ紝1浠ｈ〃宸插垹闄わ級',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='鏂囩珷琛?;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sg_article`
--

LOCK TABLES `sg_article` WRITE;
/*!40000 ALTER TABLE `sg_article` DISABLE KEYS */;
INSERT INTO `sg_article` VALUES (1,'SpringSecurity浠庡叆闂ㄥ埌绮鹃€?,'## 璇剧▼浠嬬粛\n![image20211219121555979.png](https://sg-blog-oss.oss-cn-beijing.aliyuncs.com/2022/01/31/e7131718e9e64faeaf3fe16404186eb4.png)\n\n## 0. 绠€浠?\n\n鈥?**Spring Security** 鏄?Spring 瀹舵棌涓殑涓€涓畨鍏ㄧ鐞嗘鏋躲€傜浉姣斾笌鍙﹀涓€涓畨鍏ㄦ鏋?*Shiro**锛屽畠鎻愪緵浜嗘洿涓板瘜鐨勫姛鑳斤紝绀惧尯璧勬簮涔熸瘮Shiro涓板瘜銆俓n\n鈥?涓€鑸潵璇翠腑澶у瀷鐨勯」鐩兘鏄娇鐢?*SpringSecurity** 鏉ュ仛瀹夊叏妗嗘灦銆傚皬椤圭洰鏈塖hiro鐨勬瘮杈冨锛屽洜涓虹浉姣斾笌SpringSecurity锛孲hiro鐨勪笂鎵嬫洿鍔犵殑绠€鍗曘€俓n\n鈥? 涓€鑸琖eb搴旂敤鐨勯渶瑕佽繘琛?*璁よ瘉**鍜?*鎺堟潈**銆俓n\n鈥?	**璁よ瘉锛氶獙璇佸綋鍓嶈闂郴缁熺殑鏄笉鏄湰绯荤粺鐨勭敤鎴凤紝骞朵笖瑕佺‘璁ゅ叿浣撴槸鍝釜鐢ㄦ埛**\n\n鈥?	**鎺堟潈锛氱粡杩囪璇佸悗鍒ゆ柇褰撳墠鐢ㄦ埛鏄惁鏈夋潈闄愯繘琛屾煇涓搷浣?*\n\n鈥?鑰岃璇佸拰鎺堟潈涔熸槸SpringSecurity浣滀负瀹夊叏妗嗘灦鐨勬牳蹇冨姛鑳姐€俓n\n\n\n## 1. 蹇€熷叆闂╘n\n### 1.1 鍑嗗宸ヤ綔\n\n鈥?鎴戜滑鍏堣鎼缓涓€涓畝鍗曠殑SpringBoot宸ョ▼\n\n鈶?璁剧疆鐖跺伐绋?娣诲姞渚濊禆\n\n~~~~\n    <parent>\n        <groupId>org.springframework.boot</groupId>\n        <artifactId>spring-boot-starter-parent</artifactId>\n        <version>2.5.0</version>\n    </parent>\n    <dependencies>\n        <dependency>\n            <groupId>org.springframework.boot</groupId>\n            <artifactId>spring-boot-starter-web</artifactId>\n        </dependency>\n        <dependency>\n            <groupId>org.projectlombok</groupId>\n            <artifactId>lombok</artifactId>\n            <optional>true</optional>\n        </dependency>\n    </dependencies>\n~~~~\n\n鈶?鍒涘缓鍚姩绫籠n\n~~~~\n@SpringBootApplication\npublic class SecurityApplication {\n\n    public static void main(String[] args) {\n        SpringApplication.run(SecurityApplication.class,args);\n    }\n}\n\n~~~~\n\n鈶?鍒涘缓Controller\n\n~~~~java\n\nimport org.springframework.web.bind.annotation.RequestMapping;\nimport org.springframework.web.bind.annotation.RestController;\n\n@RestController\npublic class HelloController {\n\n    @RequestMapping(\"/hello\")\n    public String hello(){\n        return \"hello\";\n    }\n}\n\n~~~~\n\n\n\n### 1.2 寮曞叆SpringSecurity\n\n鈥?鍦⊿pringBoot椤圭洰涓娇鐢⊿pringSecurity鎴戜滑鍙渶瑕佸紩鍏ヤ緷璧栧嵆鍙疄鐜板叆闂ㄦ渚嬨€俓n\n~~~~xml\n        <dependency>\n            <groupId>org.springframework.boot</groupId>\n            <artifactId>spring-boot-starter-security</artifactId>\n        </dependency>\n~~~~\n\n鈥?寮曞叆渚濊禆鍚庢垜浠湪灏濊瘯鍘昏闂箣鍓嶇殑鎺ュ彛灏变細鑷姩璺宠浆鍒颁竴涓猄pringSecurity鐨勯粯璁ょ櫥闄嗛〉闈紝榛樿鐢ㄦ埛鍚嶆槸user,瀵嗙爜浼氳緭鍑哄湪鎺у埗鍙般€俓n\n鈥?蹇呴』鐧婚檰涔嬪悗鎵嶈兘瀵规帴鍙ｈ繘琛岃闂€俓n\n\n\n## 2. 璁よ瘉\n\n### 2.1 鐧婚檰鏍￠獙娴佺▼\n![image20211215094003288.png](https://sg-blog-oss.oss-cn-beijing.aliyuncs.com/2022/01/31/414a87eeed344828b5b00ffa80178958.png)','SpringSecurity妗嗘灦鏁欑▼-Spring Security+JWT瀹炵幇椤圭洰绾у墠绔垎绂昏璇佹巿鏉?,1,'https://sg-blog-oss.oss-cn-beijing.aliyuncs.com/2022/01/31/948597e164614902ab1662ba8452e106.png','1','0',130,'0',NULL,'2022-01-23 23:20:11',-1,'2024-03-21 17:04:00',0),(2,'weq','adadaeqe','adad',2,'https://sg-blog-oss.oss-cn-beijing.aliyuncs.com/2022/01/15/fd2e9460c58a4af3bbeae5d9ed581688.png','1','0',22,'0',NULL,'2022-01-21 14:58:30',NULL,NULL,1),(3,'dad','asdasda','sadad',1,'https://sg-blog-oss.oss-cn-beijing.aliyuncs.com/2022/01/15/737a0ed0b8ea430d8700a12e76aa1cd1.png','1','0',33,'0',NULL,'2022-01-18 14:58:34',NULL,NULL,1),(5,'sdad','![Snipaste_20220115_165812.png](https://sg-blog-oss.oss-cn-beijing.aliyuncs.com/2022/01/15/1d9d283f5d874b468078b183e4b98b71.png)\r\n\r\n## sda \r\n\r\n222\r\n### sdasd newnewnew',NULL,2,'','1','0',44,'0',NULL,'2022-01-17 14:58:37',-1,'2024-03-21 17:04:00',0),(8,'娴嬭瘯鏂囩珷','test','娴嬭瘯',1,'http://sa6j1nmec.hn-bkt.clouddn.com/2024/03/21/e59e4f5a-90a3-442f-acec-ff9255ca6a81.png','1','0',7,'1',1,'2024-03-21 16:40:55',1,'2024-03-26 18:43:45',1),(9,'','',NULL,NULL,'','1','1',0,'0',1,'2024-03-26 17:31:44',1,'2024-03-26 17:31:44',1),(10,'娴嬭瘯鏂囩珷','test','娴嬭瘯',1,'http://sa6j1nmec.hn-bkt.clouddn.com/2024/03/21/e59e4f5a-90a3-442f-acec-ff9255ca6a81.png','0','0',0,'0',1,'2024-03-26 18:45:46',1,'2024-03-26 18:45:46',0);
/*!40000 ALTER TABLE `sg_article` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sg_article_tag`
--

DROP TABLE IF EXISTS `sg_article_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sg_article_tag` (
  `article_id` bigint NOT NULL AUTO_INCREMENT COMMENT '鏂囩珷id',
  `tag_id` bigint NOT NULL DEFAULT '0' COMMENT '鏍囩id',
  PRIMARY KEY (`article_id`,`tag_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='鏂囩珷鏍囩鍏宠仈琛?;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sg_article_tag`
--

LOCK TABLES `sg_article_tag` WRITE;
/*!40000 ALTER TABLE `sg_article_tag` DISABLE KEYS */;
INSERT INTO `sg_article_tag` VALUES (1,4),(2,1),(2,4),(3,4),(3,5),(8,1),(8,5),(10,1),(10,5);
/*!40000 ALTER TABLE `sg_article_tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sg_category`
--

DROP TABLE IF EXISTS `sg_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sg_category` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(128) DEFAULT NULL COMMENT '鍒嗙被鍚?,
  `pid` bigint DEFAULT '-1' COMMENT '鐖跺垎绫籭d锛屽鏋滄病鏈夌埗鍒嗙被涓?1',
  `description` varchar(512) DEFAULT NULL COMMENT '鎻忚堪',
  `status` char(1) DEFAULT '0' COMMENT '鐘舵€?:姝ｅ父,1绂佺敤',
  `create_by` bigint DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` bigint DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `del_flag` int DEFAULT '0' COMMENT '鍒犻櫎鏍囧織锛?浠ｈ〃鏈垹闄わ紝1浠ｈ〃宸插垹闄わ級',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='鍒嗙被琛?;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sg_category`
--

LOCK TABLES `sg_category` WRITE;
/*!40000 ALTER TABLE `sg_category` DISABLE KEYS */;
INSERT INTO `sg_category` VALUES (1,'java',-1,'wsd','0',NULL,NULL,NULL,NULL,0),(2,'PHP',-1,'wsd','0',NULL,NULL,NULL,NULL,0),(15,'娴嬭瘯鍒嗙被',-1,'321','0',1,'2024-03-21 20:09:07',9,'2024-03-30 15:33:01',1),(16,'娴嬭瘯鍒嗙被2',-1,'娴嬭瘯鍒嗙被2','0',1,'2024-03-21 20:09:46',1,'2024-03-21 20:09:46',1);
/*!40000 ALTER TABLE `sg_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sg_comment`
--

DROP TABLE IF EXISTS `sg_comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sg_comment` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `type` char(1) DEFAULT '0' COMMENT '璇勮绫诲瀷锛?浠ｈ〃鏂囩珷璇勮锛?浠ｈ〃鍙嬮摼璇勮锛?,
  `article_id` bigint DEFAULT NULL COMMENT '鏂囩珷id',
  `root_id` bigint DEFAULT '-1' COMMENT '鏍硅瘎璁篿d',
  `content` varchar(512) DEFAULT NULL COMMENT '璇勮鍐呭',
  `to_comment_user_id` bigint DEFAULT '-1' COMMENT '鎵€鍥炲鐨勭洰鏍囪瘎璁虹殑userid',
  `to_comment_id` bigint DEFAULT '-1' COMMENT '鍥炲鐩爣璇勮id',
  `create_by` bigint DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` bigint DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `del_flag` int DEFAULT '0' COMMENT '鍒犻櫎鏍囧織锛?浠ｈ〃鏈垹闄わ紝1浠ｈ〃宸插垹闄わ級',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='璇勮琛?;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sg_comment`
--

LOCK TABLES `sg_comment` WRITE;
/*!40000 ALTER TABLE `sg_comment` DISABLE KEYS */;
INSERT INTO `sg_comment` VALUES (1,'0',1,-1,'asS',-1,-1,1,'2022-01-29 07:59:22',1,'2022-01-29 07:59:22',0),(2,'0',1,-1,'[鍝堝搱]SDAS',-1,-1,1,'2022-01-29 08:01:24',1,'2022-01-29 08:01:24',0),(3,'0',1,-1,'鏄ぇ澶氭暟',-1,-1,1,'2022-01-29 16:07:24',1,'2022-01-29 16:07:24',0),(4,'0',1,-1,'鎾掑ぇ澹板湴',-1,-1,1,'2022-01-29 16:12:09',1,'2022-01-29 16:12:09',0),(5,'0',1,-1,'浣犲啀璇翠粈涔?,-1,-1,1,'2022-01-29 18:19:56',1,'2022-01-29 18:19:56',0),(6,'0',1,-1,'hffd',-1,-1,1,'2022-01-29 22:13:52',1,'2022-01-29 22:13:52',0),(9,'0',1,2,'浣犺浠€涔?,1,2,1,'2022-01-29 22:18:40',1,'2022-01-29 22:18:40',0),(10,'0',1,2,'鍝堝搱鍝堝搱[鍝堝搱]',1,9,1,'2022-01-29 22:29:15',1,'2022-01-29 22:29:15',0),(11,'0',1,2,'we鍏ㄦ枃',1,10,3,'2022-01-29 22:29:55',1,'2022-01-29 22:29:55',0),(12,'0',1,-1,'鐜嬩紒楣?,-1,-1,1,'2022-01-29 22:30:20',1,'2022-01-29 22:30:20',0),(13,'0',1,-1,'浠€涔堥樋鏄?,-1,-1,1,'2022-01-29 22:30:56',1,'2022-01-29 22:30:56',0),(14,'0',1,-1,'鏂板钩椤跺北',-1,-1,1,'2022-01-29 22:32:51',1,'2022-01-29 22:32:51',0),(15,'0',1,-1,'2222',-1,-1,1,'2022-01-29 22:34:38',1,'2022-01-29 22:34:38',0),(16,'0',1,2,'3333',1,11,1,'2022-01-29 22:34:47',1,'2022-01-29 22:34:47',0),(17,'0',1,2,'鍥炲weqedadsd',3,11,1,'2022-01-29 22:38:00',1,'2022-01-29 22:38:00',0),(18,'0',1,-1,'sdasd',-1,-1,1,'2022-01-29 23:18:19',1,'2022-01-29 23:18:19',0),(19,'0',1,-1,'111',-1,-1,1,'2022-01-29 23:22:23',1,'2022-01-29 23:22:23',0),(20,'0',1,1,'浣犺鍟ワ紵',1,1,1,'2022-01-30 10:06:21',1,'2022-01-30 10:06:21',0),(21,'0',1,-1,'鍙嬮摼娣诲姞涓憲',-1,-1,1,'2022-01-30 10:06:50',1,'2022-01-30 10:06:50',0),(22,'1',1,-1,'鍙嬮摼璇勮2',-1,-1,1,'2022-01-30 10:08:28',1,'2022-01-30 10:08:28',0),(23,'1',1,22,'鍥炲鍙嬮摼璇勮3',1,22,1,'2022-01-30 10:08:50',1,'2022-01-30 10:08:50',0),(24,'1',1,-1,'鍙嬮摼璇勮4444',-1,-1,1,'2022-01-30 10:09:03',1,'2022-01-30 10:09:03',0),(25,'1',1,22,'鏀跺埌鐨?,1,22,1,'2022-01-30 10:13:28',1,'2022-01-30 10:13:28',0),(26,'0',1,-1,'sda',-1,-1,1,'2022-01-30 10:39:05',1,'2022-01-30 10:39:05',0),(27,'0',1,1,'璇翠綘鍜嬪湴',1,20,14787164048662,'2022-01-30 17:19:30',14787164048662,'2022-01-30 17:19:30',0),(28,'0',1,1,'sdad',1,1,14787164048662,'2022-01-31 11:11:20',14787164048662,'2022-01-31 11:11:20',0),(29,'0',1,-1,'浣犺鏄殑ad',-1,-1,14787164048662,'2022-01-31 14:10:11',14787164048662,'2022-01-31 14:10:11',0),(30,'0',1,1,'鎾掑ぇ澹板湴',1,1,14787164048662,'2022-01-31 20:19:18',14787164048662,'2022-01-31 20:19:18',0),(31,'0',1,-1,'浣犲湪骞插槢',-1,-1,4,'2024-03-10 16:32:26',4,'2024-03-10 16:32:26',0),(32,'0',1,-1,'浣犲湪骞蹭粈涔?,-1,-1,4,'2024-03-10 16:32:48',4,'2024-03-10 16:32:48',0),(33,'0',1,-1,'楦′綘澶編',-1,-1,4,'2024-03-10 16:39:12',4,'2024-03-10 16:39:12',0),(34,'0',1,-1,'66',-1,-1,4,'2024-03-10 16:39:17',4,'2024-03-10 16:39:17',0),(35,'0',1,33,'灏忛粦瀛?,4,33,4,'2024-03-10 16:39:24',4,'2024-03-10 16:39:24',0),(36,'0',1,33,'?',4,35,4,'2024-03-10 16:39:29',4,'2024-03-10 16:39:29',0),(37,'1',1,-1,'楦′綘澶編',-1,-1,4,'2024-03-10 16:40:49',4,'2024-03-10 16:40:49',0),(38,'1',1,37,'灏忛粦瀛?,4,37,4,'2024-03-10 16:40:58',4,'2024-03-10 16:40:58',0),(39,'1',1,-1,'123',-1,-1,4,'2024-03-10 16:41:03',4,'2024-03-10 16:41:03',0),(40,'1',1,-1,'555',-1,-1,4,'2024-03-11 10:21:24',4,'2024-03-11 10:21:24',0);
/*!40000 ALTER TABLE `sg_comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sg_link`
--

DROP TABLE IF EXISTS `sg_link`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sg_link` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(256) DEFAULT NULL,
  `logo` varchar(256) DEFAULT NULL,
  `description` varchar(512) DEFAULT NULL,
  `address` varchar(128) DEFAULT NULL COMMENT '缃戠珯鍦板潃',
  `status` char(1) DEFAULT '2' COMMENT '瀹℃牳鐘舵€?(0浠ｈ〃瀹℃牳閫氳繃锛?浠ｈ〃瀹℃牳鏈€氳繃锛?浠ｈ〃鏈鏍?',
  `create_by` bigint DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` bigint DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `del_flag` int DEFAULT '0' COMMENT '鍒犻櫎鏍囧織锛?浠ｈ〃鏈垹闄わ紝1浠ｈ〃宸插垹闄わ級',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='鍙嬮摼';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sg_link`
--

LOCK TABLES `sg_link` WRITE;
/*!40000 ALTER TABLE `sg_link` DISABLE KEYS */;
INSERT INTO `sg_link` VALUES (1,'sda','https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fn1.itc.cn%2Fimg8%2Fwb%2Frecom%2F2016%2F05%2F10%2F146286696706220328.PNG&refer=http%3A%2F%2Fn1.itc.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1646205529&t=f942665181eb9b0685db7a6f59d59975','sda','https://www.baidu.com','0',NULL,'2022-01-13 08:25:47',NULL,'2022-01-13 08:36:14',0),(2,'sda','https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fn1.itc.cn%2Fimg8%2Fwb%2Frecom%2F2016%2F05%2F10%2F146286696706220328.PNG&refer=http%3A%2F%2Fn1.itc.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1646205529&t=f942665181eb9b0685db7a6f59d59975','dada','https://www.qq.com','0',NULL,'2022-01-13 09:06:10',NULL,'2022-01-13 09:07:09',0),(3,'sa','https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fn1.itc.cn%2Fimg8%2Fwb%2Frecom%2F2016%2F05%2F10%2F146286696706220328.PNG&refer=http%3A%2F%2Fn1.itc.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1646205529&t=f942665181eb9b0685db7a6f59d59975','da','https://www.taobao.com','0',NULL,'2022-01-13 09:23:01',NULL,'2022-01-13 09:23:01',0),(4,'test',NULL,'鐢ㄤ簬娴嬭瘯鍙嬮摼','test','0',9,'2024-03-30 15:16:17',9,'2024-03-30 15:23:29',1),(5,'test3',NULL,NULL,'test3','0',9,'2024-03-30 15:23:51',9,'2024-03-30 15:26:41',1);
/*!40000 ALTER TABLE `sg_link` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sg_tag`
--

DROP TABLE IF EXISTS `sg_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sg_tag` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(128) DEFAULT NULL COMMENT '鏍囩鍚?,
  `create_by` bigint DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` bigint DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `del_flag` int DEFAULT '0' COMMENT '鍒犻櫎鏍囧織锛?浠ｈ〃鏈垹闄わ紝1浠ｈ〃宸插垹闄わ級',
  `remark` varchar(500) DEFAULT NULL COMMENT '澶囨敞',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='鏍囩';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sg_tag`
--

LOCK TABLES `sg_tag` WRITE;
/*!40000 ALTER TABLE `sg_tag` DISABLE KEYS */;
INSERT INTO `sg_tag` VALUES (1,'Mybatis',NULL,NULL,NULL,'2022-01-11 09:20:50',0,'weqwe'),(2,'asdas',NULL,'2022-01-11 09:20:55',NULL,'2022-01-11 09:20:55',1,'weqw'),(3,'weqw',NULL,'2022-01-11 09:21:07',NULL,'2022-01-11 09:21:07',1,'qweqwe'),(4,'Java',NULL,'2022-01-13 15:22:43',NULL,'2022-01-13 15:22:43',0,'sdad'),(5,'WAD',NULL,'2022-01-13 15:22:47',NULL,'2022-01-13 15:22:47',0,'ASDAD'),(8,'娴嬭瘯鏍囩2',1,'2024-03-21 15:05:41',1,'2024-03-21 15:05:50',0,'娴嬭瘯2');
/*!40000 ALTER TABLE `sg_tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_menu`
--

DROP TABLE IF EXISTS `sys_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_menu` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '鑿滃崟ID',
  `menu_name` varchar(50) NOT NULL COMMENT '鑿滃崟鍚嶇О',
  `parent_id` bigint DEFAULT '0' COMMENT '鐖惰彍鍗旾D',
  `order_num` int DEFAULT '0' COMMENT '鏄剧ず椤哄簭',
  `path` varchar(200) DEFAULT '' COMMENT '璺敱鍦板潃',
  `component` varchar(255) DEFAULT NULL COMMENT '缁勪欢璺緞',
  `is_frame` int DEFAULT '1' COMMENT '鏄惁涓哄閾撅紙0鏄?1鍚︼級',
  `menu_type` char(1) DEFAULT '' COMMENT '鑿滃崟绫诲瀷锛圡鐩綍 C鑿滃崟 F鎸夐挳锛?,
  `visible` char(1) DEFAULT '0' COMMENT '鑿滃崟鐘舵€侊紙0鏄剧ず 1闅愯棌锛?,
  `status` char(1) DEFAULT '0' COMMENT '鑿滃崟鐘舵€侊紙0姝ｅ父 1鍋滅敤锛?,
  `perms` varchar(100) DEFAULT NULL COMMENT '鏉冮檺鏍囪瘑',
  `icon` varchar(100) DEFAULT '#' COMMENT '鑿滃崟鍥炬爣',
  `create_by` bigint DEFAULT NULL COMMENT '鍒涘缓鑰?,
  `create_time` datetime DEFAULT NULL COMMENT '鍒涘缓鏃堕棿',
  `update_by` bigint DEFAULT NULL COMMENT '鏇存柊鑰?,
  `update_time` datetime DEFAULT NULL COMMENT '鏇存柊鏃堕棿',
  `remark` varchar(500) DEFAULT '' COMMENT '澶囨敞',
  `del_flag` char(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2038 DEFAULT CHARSET=utf8mb3 COMMENT='鑿滃崟鏉冮檺琛?;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_menu`
--

LOCK TABLES `sys_menu` WRITE;
/*!40000 ALTER TABLE `sys_menu` DISABLE KEYS */;
INSERT INTO `sys_menu` VALUES (1,'绯荤粺绠＄悊',0,1,'system',NULL,1,'M','0','0','','system',0,'2021-11-12 10:46:19',0,NULL,'绯荤粺绠＄悊鐩綍','0'),(100,'鐢ㄦ埛绠＄悊',1,1,'user','system/user/index',1,'C','0','0','system:user:list','user',0,'2021-11-12 10:46:19',1,'2022-07-31 15:47:58','鐢ㄦ埛绠＄悊鑿滃崟','0'),(101,'瑙掕壊绠＄悊',1,2,'role','system/role/index',1,'C','0','0','system:role:list','peoples',0,'2021-11-12 10:46:19',0,NULL,'瑙掕壊绠＄悊鑿滃崟','0'),(102,'鑿滃崟绠＄悊',1,3,'menu','system/menu/index',1,'C','0','0','system:menu:list','tree-table',0,'2021-11-12 10:46:19',0,NULL,'鑿滃崟绠＄悊鑿滃崟','0'),(1001,'鐢ㄦ埛鏌ヨ',100,1,'','',1,'F','0','0','system:user:query','#',0,'2021-11-12 10:46:19',0,NULL,'','0'),(1002,'鐢ㄦ埛鏂板',100,2,'','',1,'F','0','0','system:user:add','#',0,'2021-11-12 10:46:19',0,NULL,'','0'),(1003,'鐢ㄦ埛淇敼',100,3,'','',1,'F','0','0','system:user:edit','#',0,'2021-11-12 10:46:19',0,NULL,'','0'),(1004,'鐢ㄦ埛鍒犻櫎',100,4,'','',1,'F','0','0','system:user:remove','#',0,'2021-11-12 10:46:19',0,NULL,'','0'),(1005,'鐢ㄦ埛瀵煎嚭',100,5,'','',1,'F','0','0','system:user:export','#',0,'2021-11-12 10:46:19',0,NULL,'','0'),(1006,'鐢ㄦ埛瀵煎叆',100,6,'','',1,'F','0','0','system:user:import','#',0,'2021-11-12 10:46:19',0,NULL,'','0'),(1007,'閲嶇疆瀵嗙爜',100,7,'','',1,'F','0','0','system:user:resetPwd','#',0,'2021-11-12 10:46:19',0,NULL,'','0'),(1008,'瑙掕壊鏌ヨ',101,1,'','',1,'F','0','0','system:role:query','#',0,'2021-11-12 10:46:19',0,NULL,'','0'),(1009,'瑙掕壊鏂板',101,2,'','',1,'F','0','0','system:role:add','#',0,'2021-11-12 10:46:19',0,NULL,'','0'),(1010,'瑙掕壊淇敼',101,3,'','',1,'F','0','0','system:role:edit','#',0,'2021-11-12 10:46:19',0,NULL,'','0'),(1011,'瑙掕壊鍒犻櫎',101,4,'','',1,'F','0','0','system:role:remove','#',0,'2021-11-12 10:46:19',0,NULL,'','0'),(1012,'瑙掕壊瀵煎嚭',101,5,'','',1,'F','0','0','system:role:export','#',0,'2021-11-12 10:46:19',0,NULL,'','0'),(1013,'鑿滃崟鏌ヨ',102,1,'','',1,'F','0','0','system:menu:query','#',0,'2021-11-12 10:46:19',0,NULL,'','0'),(1014,'鑿滃崟鏂板',102,2,'','',1,'F','0','0','system:menu:add','#',0,'2021-11-12 10:46:19',0,NULL,'','0'),(1015,'鑿滃崟淇敼',102,3,'','',1,'F','0','0','system:menu:edit','#',0,'2021-11-12 10:46:19',0,NULL,'','0'),(1016,'鑿滃崟鍒犻櫎',102,4,'','',1,'F','0','0','system:menu:remove','#',0,'2021-11-12 10:46:19',0,NULL,'','0'),(2017,'鍐呭绠＄悊',0,4,'content',NULL,1,'M','0','0',NULL,'table',NULL,'2022-01-08 02:44:38',1,'2022-07-31 12:34:23','','0'),(2018,'鍒嗙被绠＄悊',2017,1,'category','content/category/index',1,'C','0','0','content:category:list','example',NULL,'2022-01-08 02:51:45',NULL,'2022-01-08 02:51:45','','0'),(2019,'鏂囩珷绠＄悊',2017,0,'article','content/article/index',1,'C','0','0','content:article:list','build',NULL,'2022-01-08 02:53:10',NULL,'2022-01-08 02:53:10','','0'),(2021,'鏍囩绠＄悊',2017,6,'tag','content/tag/index',1,'C','0','0','content:tag:index','button',NULL,'2022-01-08 02:55:37',NULL,'2022-01-08 02:55:50','','0'),(2022,'鍙嬮摼绠＄悊',2017,4,'link','content/link/index',1,'C','0','0','content:link:list','404',NULL,'2022-01-08 02:56:50',NULL,'2022-01-08 02:56:50','','0'),(2023,'鍐欏崥鏂?,0,0,'write','content/article/write/index',1,'C','0','0','content:article:writer','build',NULL,'2022-01-08 03:39:58',1,'2022-07-31 22:07:05','','0'),(2024,'鍙嬮摼鏂板',2022,0,'',NULL,1,'F','0','0','content:link:add','#',NULL,'2022-01-16 07:59:17',NULL,'2022-01-16 07:59:17','','0'),(2025,'鍙嬮摼淇敼',2022,1,'',NULL,1,'F','0','0','content:link:edit','#',NULL,'2022-01-16 07:59:44',NULL,'2022-01-16 07:59:44','','0'),(2026,'鍙嬮摼鍒犻櫎',2022,1,'',NULL,1,'F','0','0','content:link:remove','#',NULL,'2022-01-16 08:00:05',NULL,'2022-01-16 08:00:05','','0'),(2027,'鍙嬮摼鏌ヨ',2022,2,'',NULL,1,'F','0','0','content:link:query','#',NULL,'2022-01-16 08:04:09',NULL,'2022-01-16 08:04:09','','0'),(2028,'瀵煎嚭鍒嗙被',2018,1,'',NULL,1,'F','0','0','content:category:export','#',NULL,'2022-01-21 07:06:59',NULL,'2022-01-21 07:06:59','','0');
/*!40000 ALTER TABLE `sys_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role`
--

DROP TABLE IF EXISTS `sys_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_role` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '瑙掕壊ID',
  `role_name` varchar(30) NOT NULL COMMENT '瑙掕壊鍚嶇О',
  `role_key` varchar(100) NOT NULL COMMENT '瑙掕壊鏉冮檺瀛楃涓?,
  `role_sort` int NOT NULL COMMENT '鏄剧ず椤哄簭',
  `status` char(1) NOT NULL COMMENT '瑙掕壊鐘舵€侊紙0姝ｅ父 1鍋滅敤锛?,
  `del_flag` char(1) DEFAULT '0' COMMENT '鍒犻櫎鏍囧織锛?浠ｈ〃瀛樺湪 1浠ｈ〃鍒犻櫎锛?,
  `create_by` bigint DEFAULT NULL COMMENT '鍒涘缓鑰?,
  `create_time` datetime DEFAULT NULL COMMENT '鍒涘缓鏃堕棿',
  `update_by` bigint DEFAULT NULL COMMENT '鏇存柊鑰?,
  `update_time` datetime DEFAULT NULL COMMENT '鏇存柊鏃堕棿',
  `remark` varchar(500) DEFAULT NULL COMMENT '澶囨敞',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb3 COMMENT='瑙掕壊淇℃伅琛?;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role`
--

LOCK TABLES `sys_role` WRITE;
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;
INSERT INTO `sys_role` VALUES (1,'瓒呯骇绠＄悊鍛?,'admin',1,'0','0',0,'2021-11-12 10:46:19',1,'2024-03-28 19:30:45','瓒呯骇绠＄悊鍛?),(2,'鏅€氳鑹?,'common',2,'0','0',0,'2021-11-12 10:46:19',1,'2024-03-28 14:41:16','鏅€氳鑹?),(11,'鍢庡槑鍢?,'aggag',5,'0','0',NULL,'2022-01-06 14:07:40',1,'2024-03-28 14:41:19','鍢庡槑鍢?),(12,'鍙嬮摼瀹℃牳鍛?,'link',1,'0','0',NULL,'2022-01-16 06:49:30',1,'2024-03-28 14:41:13',NULL),(16,'寮€鍙戣€?,'admin',10,'0','0',1,'2024-03-28 15:39:18',1,'2024-03-28 16:39:31','寮€鍙戜汉鍛?);
/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role_menu`
--

DROP TABLE IF EXISTS `sys_role_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_role_menu` (
  `role_id` bigint NOT NULL COMMENT '瑙掕壊ID',
  `menu_id` bigint NOT NULL COMMENT '鑿滃崟ID',
  PRIMARY KEY (`role_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='瑙掕壊鍜岃彍鍗曞叧鑱旇〃';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role_menu`
--

LOCK TABLES `sys_role_menu` WRITE;
/*!40000 ALTER TABLE `sys_role_menu` DISABLE KEYS */;
INSERT INTO `sys_role_menu` VALUES (0,0),(1,1),(1,100),(1,101),(1,102),(1,1001),(1,1002),(1,1003),(1,1004),(1,1005),(1,1006),(1,1007),(1,1008),(1,1009),(1,1010),(1,1011),(1,1012),(1,1013),(1,1014),(1,1015),(1,1016),(1,2017),(1,2018),(1,2019),(1,2021),(1,2022),(1,2023),(1,2024),(1,2025),(1,2026),(1,2027),(1,2028),(2,1),(2,102),(2,1013),(2,1014),(2,1015),(2,1016),(2,2000),(3,2),(3,3),(3,4),(3,100),(3,101),(3,103),(3,104),(3,105),(3,106),(3,107),(3,108),(3,109),(3,110),(3,111),(3,112),(3,113),(3,114),(3,115),(3,116),(3,500),(3,501),(3,1001),(3,1002),(3,1003),(3,1004),(3,1005),(3,1006),(3,1007),(3,1008),(3,1009),(3,1010),(3,1011),(3,1012),(3,1017),(3,1018),(3,1019),(3,1020),(3,1021),(3,1022),(3,1023),(3,1024),(3,1025),(3,1026),(3,1027),(3,1028),(3,1029),(3,1030),(3,1031),(3,1032),(3,1033),(3,1034),(3,1035),(3,1036),(3,1037),(3,1038),(3,1039),(3,1040),(3,1041),(3,1042),(3,1043),(3,1044),(3,1045),(3,1046),(3,1047),(3,1048),(3,1049),(3,1050),(3,1051),(3,1052),(3,1053),(3,1054),(3,1055),(3,1056),(3,1057),(3,1058),(3,1059),(3,1060),(3,2000),(11,1),(11,100),(11,101),(11,102),(11,103),(11,104),(11,105),(11,106),(11,107),(11,108),(11,500),(11,501),(11,1001),(11,1002),(11,1003),(11,1004),(11,1005),(11,1006),(11,1007),(11,1008),(11,1009),(11,1010),(11,1011),(11,1012),(11,1013),(11,1014),(11,1015),(11,1016),(11,1017),(11,1018),(11,1019),(11,1020),(11,1021),(11,1022),(11,1023),(11,1024),(11,1025),(11,1026),(11,1027),(11,1028),(11,1029),(11,1030),(11,1031),(11,1032),(11,1033),(11,1034),(11,1035),(11,1036),(11,1037),(11,1038),(11,1039),(11,1040),(11,1041),(11,1042),(11,1043),(11,1044),(11,1045),(11,2000),(11,2003),(11,2004),(11,2005),(11,2006),(11,2007),(11,2008),(11,2009),(11,2010),(11,2011),(11,2012),(11,2013),(11,2014),(12,2017),(12,2022),(12,2024),(12,2025),(12,2026),(12,2027),(16,1),(16,100),(16,101),(16,102),(16,1001),(16,1002),(16,1003),(16,1004),(16,1005),(16,1006),(16,1007),(16,1008),(16,1009),(16,1010),(16,1011),(16,1012),(16,1013),(16,1014),(16,1015),(16,1016),(16,2017),(16,2018),(16,2019),(16,2021),(16,2022),(16,2023),(16,2024),(16,2025),(16,2026),(16,2027),(16,2028);
/*!40000 ALTER TABLE `sys_role_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user`
--

DROP TABLE IF EXISTS `sys_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_name` varchar(64) NOT NULL DEFAULT 'NULL' COMMENT '鐢ㄦ埛鍚?,
  `nick_name` varchar(64) NOT NULL DEFAULT 'NULL' COMMENT '鏄电О',
  `password` varchar(64) NOT NULL DEFAULT 'NULL' COMMENT '瀵嗙爜',
  `type` char(1) DEFAULT '0' COMMENT '鐢ㄦ埛绫诲瀷锛?浠ｈ〃鏅€氱敤鎴凤紝1浠ｈ〃绠＄悊鍛?,
  `status` char(1) DEFAULT '0' COMMENT '璐﹀彿鐘舵€侊紙0姝ｅ父 1鍋滅敤锛?,
  `email` varchar(64) DEFAULT NULL COMMENT '閭',
  `phonenumber` varchar(32) DEFAULT NULL COMMENT '鎵嬫満鍙?,
  `sex` char(1) DEFAULT NULL COMMENT '鐢ㄦ埛鎬у埆锛?鐢凤紝1濂筹紝2鏈煡锛?,
  `avatar` varchar(128) DEFAULT NULL COMMENT '澶村儚',
  `create_by` bigint DEFAULT NULL COMMENT '鍒涘缓浜虹殑鐢ㄦ埛id',
  `create_time` datetime DEFAULT NULL COMMENT '鍒涘缓鏃堕棿',
  `update_by` bigint DEFAULT NULL COMMENT '鏇存柊浜?,
  `update_time` datetime DEFAULT NULL COMMENT '鏇存柊鏃堕棿',
  `del_flag` int DEFAULT '0' COMMENT '鍒犻櫎鏍囧織锛?浠ｈ〃鏈垹闄わ紝1浠ｈ〃宸插垹闄わ級',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='鐢ㄦ埛琛?;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user`
--

LOCK TABLES `sys_user` WRITE;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT INTO `sys_user` VALUES (1,'huanfqc','鐒曞彂@闈掓槬','$2a$10$VcIamfDZIvkRP1JJZKYAHOZpsb4Z3LZptJACS9wur9mZoOpTMpsAO','1','0','228675@huanfqc.cn','18888888888','1','https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fi0.hdslb.com%2Fbfs%2Farticle%2F3bf9c263bc0f2ac5c3a7feb9e218d07475573ec8.gi',NULL,'2022-01-05 09:01:56',1,'2022-01-30 15:37:03',0),(3,'HFadmin','娴嬭瘯绠＄悊鍛?,'$2a$10$v9QNSgh6rMgjwuvmgwWJG.wxPWfARyBk/c8oDDQU4JbXI2ufIOyXu','1','0','123456@huanfqc.cn',NULL,'0',NULL,NULL,'2022-01-05 13:28:43',NULL,'2022-01-05 13:28:43',0),(4,'鐢ㄦ埛01','涓夊摜','$2a$10$Cjxu8UwfmUYvgzy7VJexke3suuKNM9bwy8ENHj4UEzBmMZX5p.OBm','1','0','123@huanfqc.cn','19098790742','0','http://sa6j1nmec.hn-bkt.clouddn.com/2024/03/11/26afb606-0a5c-4ed7-aa63-752c89c946b6.png',NULL,NULL,NULL,'2024-03-11 21:52:28',0),(5,'鐢ㄦ埛02','鏉庡洓','$2a$10$Cjxu8UwfmUYvgzy7VJexke3suuKNM9bwy8ENHj4UEzBmMZX5p.OBm','1','0','123@huanfqc.cn','18246845873','1',NULL,NULL,'2022-01-06 03:51:13',NULL,'2022-01-06 07:00:50',0),(6,'鐢ㄦ埛03','鐜嬩簲','$2a$10$Cjxu8UwfmUYvgzy7VJexke3suuKNM9bwy8ENHj4UEzBmMZX5p.OBm','1','0','123@huanfqc.cn','17777777777','0',NULL,NULL,'2022-01-16 06:54:26',NULL,'2022-01-16 07:06:34',0),(7,'Ankh','Ankh','$2a$10$E61Px2.tLVlaR2x6uixk3.pgqIS/GoSSNBTxIHifD1cgUh16Q2OTK','0','0','1214959101@qq.com',NULL,'0',NULL,NULL,'2024-03-12 20:10:29',NULL,'2024-03-13 14:06:22',0),(8,'123123','123123','$2a$10$j6.puAdlyoTpsaWlq19h7OmBP4rEZd85fRBOQ84iZR/llagtbJWc2','0','0','123123@qq.com',NULL,'0',NULL,NULL,'2024-03-12 20:25:40',NULL,'2024-03-12 20:26:46',0),(9,'test','寮€鍙戣€?,'$2a$10$j0iT2U4WT5pBhsEKlUQtEeL1nDuVCiuvoBDnlJsZpJm7FZHJgblPC','0','0','testtest@123.com','18757082251','2',NULL,1,'2024-03-28 19:32:13',1,'2024-03-28 20:58:10',0);
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user_role`
--

DROP TABLE IF EXISTS `sys_user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_user_role` (
  `user_id` bigint NOT NULL COMMENT '鐢ㄦ埛ID',
  `role_id` bigint NOT NULL COMMENT '瑙掕壊ID',
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='鐢ㄦ埛鍜岃鑹插叧鑱旇〃';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user_role`
--

LOCK TABLES `sys_user_role` WRITE;
/*!40000 ALTER TABLE `sys_user_role` DISABLE KEYS */;
INSERT INTO `sys_user_role` VALUES (1,1),(2,2),(5,2),(6,12),(9,16);
/*!40000 ALTER TABLE `sys_user_role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-04-09 16:07:51
