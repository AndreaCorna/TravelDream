SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

CREATE SCHEMA IF NOT EXISTS `TravelDreamDB` DEFAULT CHARACTER SET latin1 ;
USE `TravelDreamDB` ;

-- -----------------------------------------------------
-- Table `TravelDreamDB`.`Anagrafica`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `TravelDreamDB`.`Anagrafica` (
  `CF` VARCHAR(16) NOT NULL ,
  `Nome` VARCHAR(45) NOT NULL ,
  `Cognome` VARCHAR(45) NOT NULL ,
  `Data_Nascita` DATE NOT NULL ,
  `Luogo_Nascita` VARCHAR(45) NOT NULL ,
  `Residenza` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`CF`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `TravelDreamDB`.`Amministratore`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `TravelDreamDB`.`Amministratore` (
  `id` INT NOT NULL ,
  `Username` VARCHAR(45) NOT NULL ,
  `Password` VARCHAR(32) NOT NULL ,
  `id_Anagrafica` VARCHAR(16) NOT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_Amministratore_Anagrafica1` (`id_Anagrafica` ASC) ,
  UNIQUE INDEX `Username_UNIQUE` (`Username` ASC) ,
  CONSTRAINT `fk_Amministratore_Anagrafica1`
    FOREIGN KEY (`id_Anagrafica` )
    REFERENCES `TravelDreamDB`.`Anagrafica` (`CF` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `TravelDreamDB`.`Dipendente`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `TravelDreamDB`.`Dipendente` (
  `id` INT NOT NULL ,
  `Username` VARCHAR(45) NOT NULL ,
  `Password` VARCHAR(32) NOT NULL ,
  `Email` VARCHAR(45) NOT NULL ,
  `Telefono` INT NOT NULL ,
  `id_Anagrafica` VARCHAR(16) NOT NULL ,
  `id_Amministratore` INT NOT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_Dipendente_Anagrafica1` (`id_Anagrafica` ASC) ,
  UNIQUE INDEX `Username_UNIQUE` (`Username` ASC) ,
  INDEX `fk_Dipendente_Amministratore` (`id_Amministratore` ASC) ,
  CONSTRAINT `fk_Dipendente_Anagrafica1`
    FOREIGN KEY (`id_Anagrafica` )
    REFERENCES `TravelDreamDB`.`Anagrafica` (`CF` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Dipendente_Amministratore`
    FOREIGN KEY (`id_Amministratore` )
    REFERENCES `TravelDreamDB`.`Amministratore` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `TravelDreamDB`.`Utente_Registrato`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `TravelDreamDB`.`Utente_Registrato` (
  `id` INT NOT NULL ,
  `Username` VARCHAR(45) NOT NULL ,
  `Password` VARCHAR(32) NOT NULL ,
  `Email` VARCHAR(45) NOT NULL ,
  `Telefono` INT NOT NULL ,
  `id_Anagrafica` VARCHAR(16) NOT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_Utente_Registrato_Anagrafica` (`id_Anagrafica` ASC) ,
  UNIQUE INDEX `Username_UNIQUE` (`Username` ASC) ,
  CONSTRAINT `fk_Utente_Registrato_Anagrafica`
    FOREIGN KEY (`id_Anagrafica` )
    REFERENCES `TravelDreamDB`.`Anagrafica` (`CF` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `TravelDreamDB`.`Escursione`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `TravelDreamDB`.`Escursione` (
  `id` INT NOT NULL ,
  `Luogo` VARCHAR(45) NOT NULL ,
  `Prezzo` INT NOT NULL ,
  `Data` DATETIME NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `TravelDreamDB`.`Aereo`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `TravelDreamDB`.`Aereo` (
  `id` INT NOT NULL ,
  `Decollo` VARCHAR(45) NOT NULL ,
  `Atterraggio` VARCHAR(45) NOT NULL ,
  `Data` DATETIME NOT NULL ,
  `Posti_Disponibili` INT NOT NULL DEFAULT '0' ,
  `Costo` INT NOT NULL DEFAULT '0' ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `TravelDreamDB`.`Hotel`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `TravelDreamDB`.`Hotel` (
  `id` INT NOT NULL ,
  `Città` VARCHAR(45) NOT NULL ,
  `Camere_Disponibili` INT NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `TravelDreamDB`.`Pacchetto`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `TravelDreamDB`.`Pacchetto` (
  `id` INT NOT NULL ,
  `Destinazione` VARCHAR(45) NOT NULL ,
  `Inizio_Validità` DATE NOT NULL ,
  `Fine_Validità` INT NOT NULL ,
  `id_Dipendente` INT NOT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_Pacchetto_Dipendente1` (`id_Dipendente` ASC) ,
  CONSTRAINT `fk_Pacchetto_Dipendente1`
    FOREIGN KEY (`id_Dipendente` )
    REFERENCES `TravelDreamDB`.`Dipendente` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `TravelDreamDB`.`Prenotazione_Pacchetto`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `TravelDreamDB`.`Prenotazione_Pacchetto` (
  `id` INT NOT NULL ,
  `Data` DATETIME NOT NULL ,
  `id_Aereo_Andata` INT NOT NULL ,
  `id_Aereo_Ritorno` INT NOT NULL ,
  `id_Hotel` INT NOT NULL ,
  `id_Utente` INT NOT NULL ,
  `id_Pacchetto` INT NOT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_Prenotazione_Utente_Registrato1` (`id_Utente` ASC) ,
  INDEX `fk_Prenotazione_Dipendente1` (`id_Utente` ASC) ,
  INDEX `fk_Prenotazione_Aereo_Andata` (`id_Aereo_Andata` ASC) ,
  INDEX `fk_Prenotazione_Aereo_Ritorno` (`id_Aereo_Ritorno` ASC) ,
  INDEX `fk_Prenotazione_Hotel` (`id_Hotel` ASC) ,
  UNIQUE INDEX `Utente_id_UNIQUE` (`id_Utente` ASC) ,
  INDEX `fk_Prenotazione_Pacchetto_Pacchetto1` (`id_Pacchetto` ASC) ,
  CONSTRAINT `fk_Prenotazione_Utente_Registrato1`
    FOREIGN KEY (`id_Utente` )
    REFERENCES `TravelDreamDB`.`Utente_Registrato` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Prenotazione_Dipendente1`
    FOREIGN KEY (`id_Utente` )
    REFERENCES `TravelDreamDB`.`Dipendente` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Prenotazione_Aereo_Andata`
    FOREIGN KEY (`id_Aereo_Andata` )
    REFERENCES `TravelDreamDB`.`Aereo` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Prenotazione_Aereo_Ritorno`
    FOREIGN KEY (`id_Aereo_Ritorno` )
    REFERENCES `TravelDreamDB`.`Aereo` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Prenotazione_Hotel`
    FOREIGN KEY (`id_Hotel` )
    REFERENCES `TravelDreamDB`.`Hotel` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Prenotazione_Pacchetto_Pacchetto1`
    FOREIGN KEY (`id_Pacchetto` )
    REFERENCES `TravelDreamDB`.`Pacchetto` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `TravelDreamDB`.`Camera`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `TravelDreamDB`.`Camera` (
  `id` INT NOT NULL ,
  `Data_Checkin` DATETIME NOT NULL ,
  `Data_Checkout` DATETIME NOT NULL ,
  `Costo` INT NOT NULL ,
  `Posti` INT NOT NULL ,
  `Occupata` TINYINT(1) NOT NULL ,
  `id_Hotel` INT NOT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_Camera_Hotel` (`id_Hotel` ASC) ,
  CONSTRAINT `fk_Camera_Hotel`
    FOREIGN KEY (`id_Hotel` )
    REFERENCES `TravelDreamDB`.`Hotel` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `TravelDreamDB`.`Condivisione`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `TravelDreamDB`.`Condivisione` (
  `id` INT NOT NULL ,
  `Link` VARCHAR(100) NOT NULL ,
  `Data` DATETIME NOT NULL ,
  `id_Utente` INT NOT NULL ,
  `id_Prenotazione` INT NOT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_Condivisione_Pacchetto` (`id_Prenotazione` ASC) ,
  INDEX `fk_Condivisione_Utente_Registrato1` (`id_Utente` ASC) ,
  CONSTRAINT `fk_Condivisione_Pacchetto`
    FOREIGN KEY (`id_Prenotazione` )
    REFERENCES `TravelDreamDB`.`Prenotazione_Pacchetto` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Condivisione_Utente_Registrato1`
    FOREIGN KEY (`id_Utente` )
    REFERENCES `TravelDreamDB`.`Utente_Registrato` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `TravelDreamDB`.`Prenotazione_Viaggio`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `TravelDreamDB`.`Prenotazione_Viaggio` (
  `id` INT NOT NULL ,
  `Data` DATETIME NOT NULL ,
  `id_Aereo_Andata` INT NULL ,
  `id_Aereo_Ritorno` INT NULL ,
  `id_Hotel` INT NULL ,
  `id_Utente` INT NOT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_Prenotazione_Viaggio_Utente` (`id_Utente` ASC) ,
  INDEX `fk_Prenotazione_Viaggio_Dipendente` (`id_Utente` ASC) ,
  INDEX `fk_Prenotazione_Viaggio_Aereo_Andata` (`id_Aereo_Andata` ASC) ,
  INDEX `fk_Prenotazione_Viaggio_Aereo_Ritorno` (`id_Aereo_Ritorno` ASC) ,
  INDEX `fk_Prenotazione_Viaggio_Hotel` (`id_Hotel` ASC) ,
  CONSTRAINT `fk_Prenotazione_Viaggio_Utente`
    FOREIGN KEY (`id_Utente` )
    REFERENCES `TravelDreamDB`.`Utente_Registrato` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Prenotazione_Viaggio_Dipendente`
    FOREIGN KEY (`id_Utente` )
    REFERENCES `TravelDreamDB`.`Dipendente` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Prenotazione_Viaggio_Aereo_Andata`
    FOREIGN KEY (`id_Aereo_Andata` )
    REFERENCES `TravelDreamDB`.`Aereo` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Prenotazione_Viaggio_Aereo_Ritorno`
    FOREIGN KEY (`id_Aereo_Ritorno` )
    REFERENCES `TravelDreamDB`.`Aereo` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Prenotazione_Viaggio_Hotel`
    FOREIGN KEY (`id_Hotel` )
    REFERENCES `TravelDreamDB`.`Hotel` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `TravelDreamDB`.`Escursioni_in_Prenotazione`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `TravelDreamDB`.`Escursioni_in_Prenotazione` (
  `id_Prenotazione` INT NOT NULL ,
  `id_Escursione` INT NOT NULL ,
  PRIMARY KEY (`id_Prenotazione`, `id_Escursione`) ,
  INDEX `fk_Prenotazione_Pacchetto_has_Escursione_Escursione1` (`id_Escursione` ASC) ,
  INDEX `fk_Prenotazione_Pacchetto_has_Escursione_Prenotazione_Pacchet1` (`id_Prenotazione` ASC) ,
  INDEX `fk_Escursioni_Prenotazione_Prenotazione_Viaggio1` (`id_Prenotazione` ASC) ,
  CONSTRAINT `fk_Prenotazione_Pacchetto_has_Escursione_Prenotazione_Pacchet1`
    FOREIGN KEY (`id_Prenotazione` )
    REFERENCES `TravelDreamDB`.`Prenotazione_Pacchetto` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Prenotazione_Pacchetto_has_Escursione_Escursione1`
    FOREIGN KEY (`id_Escursione` )
    REFERENCES `TravelDreamDB`.`Escursione` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Escursioni_Prenotazione_Prenotazione_Viaggio1`
    FOREIGN KEY (`id_Prenotazione` )
    REFERENCES `TravelDreamDB`.`Prenotazione_Viaggio` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `TravelDreamDB`.`Escursione_in_Pacchetto`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `TravelDreamDB`.`Escursione_in_Pacchetto` (
  `id_Escursione` INT NOT NULL ,
  `id_Pacchetto` INT NOT NULL ,
  PRIMARY KEY (`id_Escursione`, `id_Pacchetto`) ,
  INDEX `fk_Escursione_has_Pacchetto_Pacchetto1` (`id_Pacchetto` ASC) ,
  INDEX `fk_Escursione_has_Pacchetto_Escursione1` (`id_Escursione` ASC) ,
  CONSTRAINT `fk_Escursione_has_Pacchetto_Escursione1`
    FOREIGN KEY (`id_Escursione` )
    REFERENCES `TravelDreamDB`.`Escursione` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Escursione_has_Pacchetto_Pacchetto1`
    FOREIGN KEY (`id_Pacchetto` )
    REFERENCES `TravelDreamDB`.`Pacchetto` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `TravelDreamDB`.`Hotel_in_Pacchetto`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `TravelDreamDB`.`Hotel_in_Pacchetto` (
  `id_Hotel` INT NOT NULL ,
  `id_Pacchetto` INT NOT NULL ,
  PRIMARY KEY (`id_Hotel`, `id_Pacchetto`) ,
  INDEX `fk_Hotel_has_Pacchetto_Pacchetto1` (`id_Pacchetto` ASC) ,
  INDEX `fk_Hotel_has_Pacchetto_Hotel1` (`id_Hotel` ASC) ,
  CONSTRAINT `fk_Hotel_has_Pacchetto_Hotel1`
    FOREIGN KEY (`id_Hotel` )
    REFERENCES `TravelDreamDB`.`Hotel` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Hotel_has_Pacchetto_Pacchetto1`
    FOREIGN KEY (`id_Pacchetto` )
    REFERENCES `TravelDreamDB`.`Pacchetto` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `TravelDreamDB`.`Aereo_in_Pacchetto`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `TravelDreamDB`.`Aereo_in_Pacchetto` (
  `id_Volo` INT NOT NULL ,
  `id_Pacchetto` INT NOT NULL ,
  PRIMARY KEY (`id_Volo`, `id_Pacchetto`) ,
  INDEX `fk_Aereo_has_Pacchetto_Pacchetto1` (`id_Pacchetto` ASC) ,
  INDEX `fk_Aereo_has_Pacchetto_Aereo1` (`id_Volo` ASC) ,
  CONSTRAINT `fk_Aereo_has_Pacchetto_Aereo1`
    FOREIGN KEY (`id_Volo` )
    REFERENCES `TravelDreamDB`.`Aereo` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Aereo_has_Pacchetto_Pacchetto1`
    FOREIGN KEY (`id_Pacchetto` )
    REFERENCES `TravelDreamDB`.`Pacchetto` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
