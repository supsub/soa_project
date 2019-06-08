SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0;
SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0;
SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE =
    'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

DROP SCHEMA IF EXISTS test_jaas;
CREATE SCHEMA IF NOT EXISTS test_jaas;
USE test_jaas;


-- -----------------------------------------------------
-- Table `users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `users`
(
  `login`  VARCHAR(64) NOT NULL,
  `passwd` VARCHAR(64) NOT NULL,
  `role`   VARCHAR(45) NOT NULL,
  PRIMARY KEY (`login`)
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `zones`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `zones`
(
  `idzone` INT(11)     NOT NULL AUTO_INCREMENT,
  `name`   VARCHAR(45) NOT NULL,
  `login`  VARCHAR(64) NOT NULL,
  PRIMARY KEY (`idzone`),
  INDEX `fk_zones_users1_idx` (`login` ASC) VISIBLE,
  CONSTRAINT `fk_zones_users1`
    FOREIGN KEY (`login`)
      REFERENCES `users` (`login`)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `streets`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `streets`
(
  `idstreet` INT(11)     NOT NULL AUTO_INCREMENT,
  `idzone`   INT(11)     NOT NULL,
  `name`     VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idstreet`),
  INDEX `fk_street_zone1_idx` (`idzone` ASC) VISIBLE,
  CONSTRAINT `fk_street_zone1`
    FOREIGN KEY (`idzone`)
      REFERENCES `zones` (`idzone`)
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `parkometers`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `parkometers`
(
  `idparkometer`   INT(11) NOT NULL AUTO_INCREMENT,
  `ordinal_number` INT(11) NOT NULL,
  `idstreet`       INT(11) NOT NULL,
  PRIMARY KEY (`idparkometer`),
  INDEX `fk_parkometers_streets1_idx` (`idstreet` ASC) VISIBLE,
  CONSTRAINT `fk_parkometers_streets1`
    FOREIGN KEY (`idstreet`)
      REFERENCES `streets` (`idstreet`)
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `parking_places`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `parking_places`
(
  `idparking_place` INT(11) NOT NULL auto_increment,
  `ordinal_number`  INT(11) NOT NULL,
  `idparkometer`    INT(11) NOT NULL,
  `taken`  boolean NOT NULL,
  `last_taken_time` DATETIME,
  PRIMARY KEY (`idparking_place`),
  INDEX `fk_parking_places_parkometers1_idx` (`idparkometer` ASC) VISIBLE,
  CONSTRAINT `fk_parking_places_parkometers1`
    FOREIGN KEY (`idparkometer`)
      REFERENCES `parkometers` (`idparkometer`)
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `tickets`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tickets`
(
  `idticket`        INT(11)     NOT NULL AUTO_INCREMENT,
  `expiration_time` DATETIME    NOT NULL,
  `login`           VARCHAR(64) NOT NULL,
  `idparking_place` INT(11) NOT NULL,
  PRIMARY KEY (`idticket`),
  INDEX `fk_ticket_users_idx` (`login` ASC) VISIBLE,
  INDEX `fk_tickets_parking_places1_idx` (`idparking_place` ASC) VISIBLE,
  CONSTRAINT `fk_ticket_users`
    FOREIGN KEY (`login`)
      REFERENCES `users` (`login`),
  CONSTRAINT `fk_tickets_parking_places1`
    FOREIGN KEY (`idparking_place`)
      REFERENCES `parking_places` (`idparking_place`)
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE = @OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS;



INSERT into USERS
values ('admin', md5('admin'), 'Manager');
INSERT into USERS
values ('user', md5('user'), 'User');
INSERT into USERS
values ('workerA', md5('workerA'), 'User');
INSERT into USERS
values ('workerB', md5('workerB'), 'User');

Insert into ZONES(`name`, `login`)
values ('Strefa A', 'workerA'),
       ('Strefa B', 'workerB');
Insert into STREETS(`idzone`, `name`)
values (1, 'Akacjowa'),
       (1, 'Aptekarska'),
       (2, 'Bosaków'),
       (2, 'Bińczycka');
Insert into PARKOMETERS(`ordinal_number`, `idstreet`)
values (1, 1),
       (2, 1),
       (1, 2),
       (2, 2),
       (1, 3),
       (2, 3),
       (1, 4),
       (2, 4);


Insert into PARKING_PLACES(`ordinal_number`, `idparkometer`,`taken`)
values (1, 1, False),
       (2, 1, False),
       (1, 2, False),
       (2, 2, False),
       (1, 3, False),
       (2, 3, False),
       (1, 4, False),
       (2, 4, False),
       (1, 5, False),
       (2, 5, False),
       (1, 6, False),
       (2, 6, False),
       (1, 7, False),
       (2, 7, False),
       (1, 8, False),
       (2, 8, False);
