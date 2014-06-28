/* GRANT ALL PRIVILEGES ON shafts.* TO iadmin@"%" IDENTIFIED BY "1234qwer";
 GRANT ALL PRIVILEGES ON shafts.* TO iadmin@localhost IDENTIFIED BY "1234qwer"; */

DROP TABLE IF EXISTS `shafts_jobs`;
DROP TABLE IF EXISTS `shafts_users`;
DROP TABLE IF EXISTS `shafts_counters`;
DROP TABLE IF EXISTS `shafts_cevents`;

CREATE TABLE `shafts_users` (
  `id` INT UNSIGNED PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `userid` VARCHAR(255) NOT NULL UNIQUE,
  `password` CHAR(32) NOT NULL,
  `email` VARCHAR(255) NOT NULL DEFAULT "",
  `unit` VARCHAR(255) NOT NULL DEFAULT "",
  `group` VARCHAR(255) NOT NULL DEFAULT "",
  `phone` VARCHAR(255) NOT NULL DEFAULT "",
  `priv` VARCHAR(255) NOT NULL DEFAULT ""
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `shafts_jobs` (
  `id` INT UNSIGNED PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `uid` INT UNSIGNED NOT NULL,
  `title` VARCHAR(255) NOT NULL,
  `status` TINYINT NOT NULL DEFAULT 0
  /*`subtitle` VARCHAR(255) NOT NULL DEFAULT "",
  `isenabled` TINYINT NOT NULL DEFAULT 0,
  `type` TINYINT NOT NULL DEFAULT 0,
  `cata` VARCHAR(255) NOT NULL DEFAULT "",
  `price` DECIMAL(9,2) NOT NULL DEFAULT 0,
  `popu` INT UNSIGNED NOT NULL DEFAULT 0,
  `poputotal` INT UNSIGNED NOT NULL DEFAULT 0,
  `starttime` DATETIME NOT NULL,
  `sigdeadline` DATETIME NOT NULL,
  `media` TEXT NOT NULL,
  `brief` TEXT NOT NULL,
  `text_route` TEXT NOT NULL,
  `text_highlight` TEXT NOT NULL,
  `text_arrengement` TEXT NOT NULL,
  `text_causion` TEXT NOT NULL,
  `text_fee` TEXT NOT NULL*/
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*CREATE TABLE `shafts_counters` (
  `id` INT UNSIGNED PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(80) NOT NULL UNIQUE,
  `count` INT UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `shafts_cevents` (
  `id` INT UNSIGNED PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `sender` VARCHAR(80) NOT NULL UNIQUE,
  `etime` DATETIME NOT NULL,
  `msg` VARCHAR(255) NOT NULL DEFAULT ""
) ENGINE=InnoDB DEFAULT CHARSET=utf8;*/


INSERT INTO `shafts_users` VALUES
(NULL,'t0','1234','email','unit','group','phone','priv'),
(NULL,'t1','1234','email','unit','group','phone','priv'),
(NULL,'t2','1234','email','unit','group','phone','priv'),
(NULL,'t3','1234','email','unit','group','phone','priv'),
(NULL,'t4','1234','email','unit','group','phone','priv');

