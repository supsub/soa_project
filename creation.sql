SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

DROP SCHEMA IF EXISTS test_jaas ;
CREATE SCHEMA IF NOT EXISTS test_jaas;
USE test_jaas;


-- -----------------------------------------------------
-- Table `zones`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `zones` (
  `idzone` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idzone`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `users` (
  `login` VARCHAR(64) NOT NULL,
  `passwd` VARCHAR(64) NOT NULL,
  `role` VARCHAR(45) NOT NULL,
  `idzone` INT NULL,
  PRIMARY KEY (`login`),
  INDEX `fk_users_zone_idx` (`idzone` ASC) VISIBLE,
  CONSTRAINT `fk_users_zone`
    FOREIGN KEY (`idzone`)
    REFERENCES `mydb`.`zones` (`idzone`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `streets`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `streets` (
  `idstreet` INT NOT NULL AUTO_INCREMENT,
  `idzone` INT NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idstreet`),
  INDEX `fk_street_zone1_idx` (`idzone` ASC) VISIBLE,
  CONSTRAINT `fk_street_zone1`
    FOREIGN KEY (`idzone`)
    REFERENCES `zones` (`idzone`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `parkometers`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `parkometers` (
  `idparkometer` INT NOT NULL AUTO_INCREMENT,
  `ordinal_number` INT NOT NULL,
  `idstreet` INT NOT NULL,
  PRIMARY KEY (`idparkometer`),
  INDEX `fk_parkometers_streets1_idx` (`idstreet` ASC) VISIBLE,
  CONSTRAINT `fk_parkometers_streets1`
    FOREIGN KEY (`idstreet`)
    REFERENCES `streets` (`idstreet`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `parking_places`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `parking_places` (
  `idparking_place` INT NOT NULL,
  `ordinal_number` INT NOT NULL,
  `idparkometer` INT NOT NULL,
  PRIMARY KEY (`idparking_place`),
  INDEX `fk_parking_places_parkometers1_idx` (`idparkometer` ASC) VISIBLE,
  CONSTRAINT `fk_parking_places_parkometers1`
    FOREIGN KEY (`idparkometer`)
    REFERENCES `parkometers` (`idparkometer`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `tickets`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tickets` (
  `idticket` INT NOT NULL AUTO_INCREMENT,
  `expiration_time` DATETIME NOT NULL,
  `login` VARCHAR(64) NOT NULL,
  `idparking_place` INT NOT NULL,
  PRIMARY KEY (`idticket`),
  INDEX `fk_ticket_users_idx` (`login` ASC) VISIBLE,
  INDEX `fk_tickets_parking_places1_idx` (`idparking_place` ASC) VISIBLE,
  CONSTRAINT `fk_ticket_users`
    FOREIGN KEY (`login`)
    REFERENCES `test_jaas`.`users` (`login`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_tickets_parking_places1`
    FOREIGN KEY (`idparking_place`)
    REFERENCES `parking_places` (`idparking_place`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;




INSERT into USERS values('admin', md5('admin'), 'Manager', null);
INSERT into USERS values('user', md5('user'), 'User', null);

