-- DROPS
DROP TABLE IF EXISTS IRONOC_DB.PERSON;
DROP TABLE IF EXISTS IRONOC_DB.EMPLOYER;
DROP SCHEMA IF EXISTS IRONOC_DB;
-- CREATE TABLES
CREATE SCHEMA IRONOC_DB;
CREATE TABLE IRONOC_DB.PERSON (
  age INT NOT NULL,
  id INT AUTO_INCREMENT PRIMARY KEY,
  first_name VARCHAR(30) NOT NULL,
  surname VARCHAR(30) NOT NULL,
  title VARCHAR(5) NOT NULL,
  UNIQUE (id ASC)
);
CREATE TABLE IRONOC_DB.EMPLOYER (
  employer_id INT AUTO_INCREMENT PRIMARY KEY,
  employee_id INT NOT NULL,
  title VARCHAR(45) NOT NULL,
  name VARCHAR(45) NOT NULL,
  year INT NOT NULL,
  FOREIGN KEY (employee_id) REFERENCES IRONOC_DB.PERSON(id)
);

-- *** Enter passw as ENV VAR for docker image or alter here later via SQL client
-- ALTER USER 'root' IDENTIFIED WITH mysql_native_password BY 'mypassword';
-- *** View Data
-- SELECT p1.age, -- p1.id, p1.title, p1.first_name, p1.surname AS p_surname
--  FROM IRONOC_DB.PERSON AS p1;
-- *** from H2 console
-- SELECT * FROM IRONOC_DB.PERSON where title LIKE 'M%';
-- *** View Data from both tables by Inner Join
-- SELECT * FROM IRONOC_DB.PERSON INNER JOIN IRONOC_DB.EMPLOYER ON id = employee_id;
-- SELECT a.id, b.employee_id, b.title
--    FROM IRONOC_DB.PERSON AS a
--    INNER JOIN IRONOC_DB.EMPLOYER AS b
--    ON id = employee_id
--    WHERE a.id = 1001;
