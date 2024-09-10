-- DROPS
DROP TABLE IF EXISTS `ironoc_db`.`person`;
DROP SCHEMA IF EXISTS `ironoc_db`;
-- CREATE TABLES
CREATE SCHEMA `ironoc_db`;
CREATE TABLE `ironoc_db`.`person` (
  `age` INT NOT NULL,
  `id` INT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(30) NOT NULL,
  `surname` VARCHAR(30) NOT NULL,
  `title` VARCHAR(5) NOT NULL,
  UNIQUE (`id` ASC)
);
-- ** Test Inserts for DEBUG ONLY **
INSERT INTO `ironoc_db`.`person` (`id`, `title`, `first_name`, `surname`, `age`) VALUES (1001, "Mr", "Conor", "Heffron", 43);
INSERT INTO `ironoc_db`.`person` (`id`, `title`, `first_name`, `surname`, `age`) VALUES (1002, "Mr", "Joe", "Bloggs", 44);
INSERT INTO `ironoc_db`.`person` (`id`, `title`, `first_name`, `surname`, `age`) VALUES (1003, "Dr", "John", "Jones", 29);
-- Enter passw as ENV VAR for docker image or alter here later via SQL client
-- ALTER USER 'root' IDENTIFIED WITH mysql_native_password BY 'mypassword';
-- View Data
--SELECT `person`.`age`,
--    `person`.`id`,
--    `person`.`title`,
--    `person`.`first_name`,
--    `person`.`surname`
--FROM `ironoc_db`.`person`;
