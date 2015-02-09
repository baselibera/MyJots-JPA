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
-- Table `MyJotsDB`.`Jot`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `MyJotsDB`.`Jot` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `MyJotsDB`.`Jot` (
  `idJot` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(128) NOT NULL,
  `body` TEXT NOT NULL,
  `status` INT(1) NOT NULL DEFAULT 0,
  `mimeType` VARCHAR(45) NULL DEFAULT 'text/plain',
  `createTime` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updateTime` DATETIME NULL,
  PRIMARY KEY (`idJot`))
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `MyJotsDB`.`tag`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `MyJotsDB`.`tag` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `MyJotsDB`.`tag` (
  `idTag` INT NOT NULL AUTO_INCREMENT,
  `value` VARCHAR(30) NOT NULL,
  `description` VARCHAR(255) NULL,
  `category` VARCHAR(30) NULL,
  PRIMARY KEY (`idTag`),
  UNIQUE INDEX `value_UNIQUE` (`value` ASC))
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `MyJotsDB`.`Jot_Tag`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `MyJotsDB`.`Jot_Tag` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `MyJotsDB`.`Jot_Tag` (
  `idJot` INT NOT NULL,
  `idTag` INT NOT NULL,
  PRIMARY KEY (`idJot`, `idTag`),
  INDEX `fk_jot_has_tag_tag1_idx` (`idTag` ASC),
  INDEX `fk_jot_has_tag_jot1_idx` (`idJot` ASC),
  CONSTRAINT `fk_jot_has_tag_jot1`
    FOREIGN KEY (`idJot`)
    REFERENCES `MyJotsDB`.`Jot` (`idJot`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_jot_has_tag_tag1`
    FOREIGN KEY (`idTag`)
    REFERENCES `MyJotsDB`.`tag` (`idTag`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `MyJotsDB`.`User`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `MyJotsDB`.`User` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `MyJotsDB`.`User` (
  `idAppUser` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NOT NULL,
  `name` VARCHAR(45) NULL,
  `surname` VARCHAR(45) NULL,
  `email` VARCHAR(45) NOT NULL,
  `enabled` TINYINT(1) NOT NULL DEFAULT 1,
  PRIMARY KEY (`idAppUser`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC))
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `MyJotsDB`.`Role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `MyJotsDB`.`Role` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `MyJotsDB`.`Role` (
  `idRole` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  PRIMARY KEY (`idRole`))
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `MyJotsDB`.`Jot_User`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `MyJotsDB`.`Jot_User` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `MyJotsDB`.`Jot_User` (
  `idAppUser` INT NOT NULL,
  `idJot` INT NOT NULL,
  `isOwner` TINYINT(1) NULL DEFAULT 0,
  PRIMARY KEY (`idAppUser`, `idJot`),
  INDEX `fk_appuser_has_jot_jot1_idx` (`idJot` ASC),
  INDEX `fk_appuser_has_jot_appuser1_idx` (`idAppUser` ASC),
  CONSTRAINT `fk_appuser_has_jot_appuser1`
    FOREIGN KEY (`idAppUser`)
    REFERENCES `MyJotsDB`.`User` (`idAppUser`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_appuser_has_jot_jot1`
    FOREIGN KEY (`idJot`)
    REFERENCES `MyJotsDB`.`Jot` (`idJot`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `MyJotsDB`.`User_Role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `MyJotsDB`.`User_Role` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `MyJotsDB`.`User_Role` (
  `idAppUser` INT NOT NULL,
  `idRole` INT NOT NULL,
  PRIMARY KEY (`idAppUser`, `idRole`),
  INDEX `fk_appuser_has_approle_approle1_idx` (`idRole` ASC),
  INDEX `fk_appuser_has_approle_appuser1_idx` (`idAppUser` ASC),
  CONSTRAINT `fk_appuser_has_approle_appuser1`
    FOREIGN KEY (`idAppUser`)
    REFERENCES `MyJotsDB`.`User` (`idAppUser`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_appuser_has_approle_approle1`
    FOREIGN KEY (`idRole`)
    REFERENCES `MyJotsDB`.`Role` (`idRole`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `MyJotsDB`.`JotReferJot`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `MyJotsDB`.`JotReferJot` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `MyJotsDB`.`JotReferJot` (
  `idJot` INT NOT NULL,
  `idJotReferred` INT NOT NULL,
  `weight` INT NULL,
  `comment` VARCHAR(255) NULL,
  PRIMARY KEY (`idJot`, `idJotReferred`),
  INDEX `fk_jot_has_jot_jot2_idx` (`idJotReferred` ASC),
  INDEX `fk_jot_has_jot_jot1_idx` (`idJot` ASC),
  CONSTRAINT `fk_jot_has_jot_jot1`
    FOREIGN KEY (`idJot`)
    REFERENCES `MyJotsDB`.`Jot` (`idJot`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_jot_has_jot_jot2`
    FOREIGN KEY (`idJotReferred`)
    REFERENCES `MyJotsDB`.`Jot` (`idJot`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `MyJotsDB`.`Link`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `MyJotsDB`.`Link` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `MyJotsDB`.`Link` (
  `idLink` INT NOT NULL AUTO_INCREMENT,
  `idJot` INT NOT NULL,
  `URL` TEXT NOT NULL,
  `name` VARCHAR(45) NULL,
  `createDate` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `verificatioDate` DATETIME NULL,
  PRIMARY KEY (`idLink`),
  INDEX `fk_link_jot1_idx` (`idJot` ASC),
  CONSTRAINT `fk_link_jot1`
    FOREIGN KEY (`idJot`)
    REFERENCES `MyJotsDB`.`Jot` (`idJot`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `MyJotsDB`.`JotHistory`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `MyJotsDB`.`JotHistory` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `MyJotsDB`.`JotHistory` (
  `idJotHistory` INT NOT NULL AUTO_INCREMENT,
  `idJot` INT NOT NULL,
  `object` BLOB NULL,
  `savedate` VARCHAR(45) NOT NULL DEFAULT 'CURRENT_TIMESTAMP',
  PRIMARY KEY (`idJotHistory`),
  INDEX `fk_jot_history_jot1_idx` (`idJot` ASC),
  CONSTRAINT `fk_jot_history_jot1`
    FOREIGN KEY (`idJot`)
    REFERENCES `MyJotsDB`.`Jot` (`idJot`)
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
