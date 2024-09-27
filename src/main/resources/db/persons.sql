-- DROPS
DROP TABLE IF EXISTS IRONOC_DB.PERSON;
DROP SCHEMA IF EXISTS IRONOC_DB;
-- CREATE TABLES
CREATE SCHEMA IRONOC_DB;
CREATE TABLE IRONOC_DB.PERSON (
  `age` INT NOT NULL,
  `id` INT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(30) NOT NULL,
  `surname` VARCHAR(30) NOT NULL,
  `title` VARCHAR(5) NOT NULL,
  UNIQUE (`id` ASC)
);
-- Enter passw as ENV VAR for docker image or alter here later via SQL client
-- ALTER USER 'root' IDENTIFIED WITH mysql_native_password BY 'mypassword';
-- View Data
-- SELECT `person`.`age`,
--    `person`.`id`,
--    `person`.`title`,
--    `person`.`first_name`,
--    `person`.`surname`
-- FROM `ironoc_db`.`person`;
-- SELECT * FROM IRONOC_DB.PERSON where title LIKE 'M%'; from H2 console
