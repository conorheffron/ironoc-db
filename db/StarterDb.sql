DROP TABLE IF EXISTS `test`.`person`;
DROP SCHEMA IF EXISTS `test`;

CREATE SCHEMA `test`;

CREATE TABLE `test`.`person` (
  `id` INT NULL AUTO_INCREMENT,
  `title` VARCHAR(45) NOT NULL,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC));

INSERT INTO `test`.`person` (`id`, `title`) VALUES ("1000", "Mr Conor");
INSERT INTO `test`.`person` (`id`, `title`) VALUES ("1001", "Mr John");
INSERT INTO `test`.`person` (`id`, `title`) VALUES ("1002", "Joe Bloggs");

ALTER TABLE `test`.`person` 
ADD COLUMN `first_name` VARCHAR(45) NULL AFTER `title`;
ADD COLUMN `surname` VARCHAR(45) NULL AFTER `first_name`;
ADD COLUMN `age` INT NULL AFTER `surname`;

UPDATE `test`.`person` SET `title`="Mr", `first_name`="Conor", `surname`="Heffron", `age`="30" WHERE `id`="1";
UPDATE `test`.`person` SET `title`="Mr", `first_name`="Joe", `surname`="Bloggs", `age`="40" WHERE `id`="2";
UPDATE `test`.`person` SET `title`="Dr", `first_name`="John", `surname`="Jones", `age`="20" WHERE `id`="3";

ALTER USER 'root' IDENTIFIED WITH mysql_native_password BY 'mypassword';
