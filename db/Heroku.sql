DROP TABLE `heroku_8f523a8540f591b`.`PERSON`;

CREATE TABLE `heroku_8f523a8540f591b`.`PERSON` (
  `id` INT NULL AUTO_INCREMENT,
  `title` VARCHAR(45) NOT NULL,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci
COMMENT = 'test PERSON table';


INSERT INTO `heroku_8f523a8540f591b`.`PERSON` (`id`, `title`) VALUES ("1", "Mr Conor");

INSERT INTO `heroku_8f523a8540f591b`.`PERSON` (`id`, `title`) VALUES ("2", "Mr John");

INSERT INTO `heroku_8f523a8540f591b`.`PERSON` (`id`, `title`) VALUES ("3", "Joe Bloggs");

ALTER TABLE `heroku_8f523a8540f591b`.`PERSON` 
ADD COLUMN `first_name` VARCHAR(45) NULL AFTER `title`,
ADD COLUMN `surname` VARCHAR(45) NULL AFTER `first_name`,
ADD COLUMN `age` INT NULL AFTER `surname`;

UPDATE `heroku_8f523a8540f591b`.`PERSON` SET `title`="Mr", `first_name`="Conor", `surname`="Heffron", `age`="30" WHERE `id`="1";
UPDATE `heroku_8f523a8540f591b`.`PERSON` SET `title`="Mr", `first_name`="Joe", `surname`="Bloggs", `age`="40" WHERE `id`="2";
UPDATE `heroku_8f523a8540f591b`.`PERSON` SET `title`="Dr", `first_name`="John", `surname`="Jones", `age`="20" WHERE `id`="3";