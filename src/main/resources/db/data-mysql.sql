SET NOCOUNT ON;
SET QUOTED_IDENTIFIER ON;
SET ANSI_NULLS ON;
SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED;

-- ** MySQL Test INSERTS for DEBUG & TESTING PURPOSES ONLY **
INSERT INTO IRONOC_DB.PERSON (id, title, first_name, surname, age) VALUES (1000, 'Ms', 'Sade', 'Song', 47);
INSERT INTO IRONOC_DB.PERSON (id, title, first_name, surname, age) VALUES (2000, 'Mrs', 'Nora', 'Jones', 29);
INSERT INTO IRONOC_DB.PERSON (id, title, first_name, surname, age) VALUES (3000, 'Ms', 'Halle', 'Movie', 47);

INSERT INTO IRONOC_DB.EMPLOYER (employer_id, employee_id, title, employer_name, start_year) VALUES (2001, 1000, 'Software / Data Engineer', 'Morgan Stanley', 2017);
INSERT INTO IRONOC_DB.EMPLOYER (employer_id, employee_id, title, employer_name, start_year) VALUES (2002, 1000, 'Software Developer', 'BSkyB', 2014);
INSERT INTO IRONOC_DB.EMPLOYER (employer_id, employee_id, title, employer_name, start_year) VALUES (2003, 2000, 'Software Engineer', 'Spotify', 2024);
INSERT INTO IRONOC_DB.EMPLOYER (employer_id, employee_id, title, employer_name, start_year) VALUES (2004, 3000, 'Data Engineer', 'Google', 2025);
