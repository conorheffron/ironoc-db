-- DROPS
DROP TABLE IF EXISTS IRONOC_DB.PERSON;
DROP SCHEMA IF EXISTS IRONOC_DB;
-- CREATE TABLES
CREATE SCHEMA IRONOC_DB;
CREATE TABLE IRONOC_DB.PERSON (
  age INT NOT NULL,
  id INT NULL AUTO_INCREMENT,
  first_name VARCHAR(30) NOT NULL,
  surname VARCHAR(30) NOT NULL,
  title VARCHAR(5) NOT NULL,
  UNIQUE (id ASC)
);
-- *** Enter passw as ENV VAR for docker image or alter here later via SQL client
-- ALTER USER 'root' IDENTIFIED WITH mysql_native_password BY 'mypassword';
-- *** View Data
-- SELECT p1.age, -- p1.id, p1.title, p1.first_name, p1.surname AS p_surname
--  FROM IRONOC_DB.PERSON AS p1;
-- *** from H2 console
-- SELECT * FROM IRONOC_DB.PERSON where title LIKE 'M%';
