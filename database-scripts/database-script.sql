SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema MyJotsDB
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `MyJotsDB` ;
CREATE SCHEMA IF NOT EXISTS `MyJotsDB` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
SHOW WARNINGS;
USE `MyJotsDB` ;

-- -----------------------------------------------------
-- Table `MyJotsDB`.`jot`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `MyJotsDB`.`jot` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `MyJotsDB`.`jot` (
  `pkjot` INT NOT NULL,
  `title` VARCHAR(128) NOT NULL,
  `body` TEXT NOT NULL,
  `mimetype` VARCHAR(45) NULL DEFAULT 'text/plain',
  `createtime` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updatetime` DATETIME NULL,
  PRIMARY KEY (`pkjot`))
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `MyJotsDB`.`tag`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `MyJotsDB`.`tag` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `MyJotsDB`.`tag` (
  `idtag` INT NOT NULL AUTO_INCREMENT,
  `value` VARCHAR(30) NOT NULL,
  `description` VARCHAR(255) NULL,
  `category` VARCHAR(30) NULL,
  PRIMARY KEY (`idtag`),
  UNIQUE INDEX `value_UNIQUE` (`value` ASC))
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `MyJotsDB`.`jot_has_tag`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `MyJotsDB`.`jot_has_tag` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `MyJotsDB`.`jot_has_tag` (
  `jot_pkjot` INT NOT NULL,
  `tag_idtag` INT NOT NULL,
  PRIMARY KEY (`jot_pkjot`, `tag_idtag`),
  INDEX `fk_jot_has_tag_tag1_idx` (`tag_idtag` ASC),
  INDEX `fk_jot_has_tag_jot1_idx` (`jot_pkjot` ASC),
  CONSTRAINT `fk_jot_has_tag_jot1`
    FOREIGN KEY (`jot_pkjot`)
    REFERENCES `MyJotsDB`.`jot` (`pkjot`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_jot_has_tag_tag1`
    FOREIGN KEY (`tag_idtag`)
    REFERENCES `MyJotsDB`.`tag` (`idtag`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `MyJotsDB`.`appuser`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `MyJotsDB`.`appuser` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `MyJotsDB`.`appuser` (
  `idappuser` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NOT NULL,
  `name` VARCHAR(45) NULL,
  `surname` VARCHAR(45) NULL,
  `email` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idappuser`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC))
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `MyJotsDB`.`approle`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `MyJotsDB`.`approle` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `MyJotsDB`.`approle` (
  `idrole` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  PRIMARY KEY (`idrole`))
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `MyJotsDB`.`appuser_has_jot`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `MyJotsDB`.`appuser_has_jot` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `MyJotsDB`.`appuser_has_jot` (
  `appuser_idappuser` INT NOT NULL,
  `jot_pkjot` INT NOT NULL,
  `isOwner` TINYINT(1) NULL DEFAULT 0,
  PRIMARY KEY (`appuser_idappuser`, `jot_pkjot`),
  INDEX `fk_appuser_has_jot_jot1_idx` (`jot_pkjot` ASC),
  INDEX `fk_appuser_has_jot_appuser1_idx` (`appuser_idappuser` ASC),
  CONSTRAINT `fk_appuser_has_jot_appuser1`
    FOREIGN KEY (`appuser_idappuser`)
    REFERENCES `MyJotsDB`.`appuser` (`idappuser`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_appuser_has_jot_jot1`
    FOREIGN KEY (`jot_pkjot`)
    REFERENCES `MyJotsDB`.`jot` (`pkjot`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `MyJotsDB`.`appuser_has_approle`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `MyJotsDB`.`appuser_has_approle` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `MyJotsDB`.`appuser_has_approle` (
  `appuser_idappuser` INT NOT NULL,
  `approle_idrole` INT NOT NULL,
  PRIMARY KEY (`appuser_idappuser`, `approle_idrole`),
  INDEX `fk_appuser_has_approle_approle1_idx` (`approle_idrole` ASC),
  INDEX `fk_appuser_has_approle_appuser1_idx` (`appuser_idappuser` ASC),
  CONSTRAINT `fk_appuser_has_approle_appuser1`
    FOREIGN KEY (`appuser_idappuser`)
    REFERENCES `MyJotsDB`.`appuser` (`idappuser`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_appuser_has_approle_approle1`
    FOREIGN KEY (`approle_idrole`)
    REFERENCES `MyJotsDB`.`approle` (`idrole`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `MyJotsDB`.`jot_refer_jot`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `MyJotsDB`.`jot_refer_jot` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `MyJotsDB`.`jot_refer_jot` (
  `pkjot` INT NOT NULL,
  `pkjot_referred` INT NOT NULL,
  `weight` INT NULL,
  `comment` VARCHAR(255) NULL,
  PRIMARY KEY (`pkjot`, `pkjot_referred`),
  INDEX `fk_jot_has_jot_jot2_idx` (`pkjot_referred` ASC),
  INDEX `fk_jot_has_jot_jot1_idx` (`pkjot` ASC),
  CONSTRAINT `fk_jot_has_jot_jot1`
    FOREIGN KEY (`pkjot`)
    REFERENCES `MyJotsDB`.`jot` (`pkjot`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_jot_has_jot_jot2`
    FOREIGN KEY (`pkjot_referred`)
    REFERENCES `MyJotsDB`.`jot` (`pkjot`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `MyJotsDB`.`link`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `MyJotsDB`.`link` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `MyJotsDB`.`link` (
  `idlink` INT NOT NULL AUTO_INCREMENT,
  `URL` TEXT NOT NULL,
  `name` VARCHAR(45) NULL,
  `createdate` DATETIME NOT NULL,
  `verificationtime` VARCHAR(45) NULL,
  `jot_pkjot` INT NOT NULL,
  PRIMARY KEY (`idlink`),
  INDEX `fk_link_jot1_idx` (`jot_pkjot` ASC),
  CONSTRAINT `fk_link_jot1`
    FOREIGN KEY (`jot_pkjot`)
    REFERENCES `MyJotsDB`.`jot` (`pkjot`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `MyJotsDB`.`jot_history`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `MyJotsDB`.`jot_history` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `MyJotsDB`.`jot_history` (
  `pkjot_history` INT NOT NULL AUTO_INCREMENT,
  `jot_pkjot` INT NOT NULL,
  `object` BLOB NULL,
  `savedate` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`pkjot_history`, `jot_pkjot`),
  INDEX `fk_jot_history_jot1_idx` (`jot_pkjot` ASC),
  CONSTRAINT `fk_jot_history_jot1`
    FOREIGN KEY (`jot_pkjot`)
    REFERENCES `MyJotsDB`.`jot` (`pkjot`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;
SET SQL_MODE = '';
GRANT USAGE ON *.* TO jolab;
 DROP USER jolab;
SET SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';
SHOW WARNINGS;
CREATE USER 'jolab' IDENTIFIED BY 'apocalisse';

GRANT ALL ON `MyJotsDB`.* TO 'jolab';
SHOW WARNINGS;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

