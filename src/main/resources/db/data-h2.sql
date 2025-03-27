-- h2 initial data load INSERTS
INSERT INTO IRONOC_DB.PERSON (id, title, first_name, surname, age) VALUES (1001, 'Mr', 'Conor', 'Heffron', 45);
INSERT INTO IRONOC_DB.PERSON (id, title, first_name, surname, age) VALUES (1002, 'Mrs.', 'Nora', 'Jones', 29);
INSERT INTO IRONOC_DB.PERSON (id, title, first_name, surname, age) VALUES (1003, 'Dr', 'Joe', 'Bloggs', 43);

INSERT INTO IRONOC_DB.EMPLOYER (employer_id, employee_id, title, employer_name, start_year) VALUES (2001, 1001, 'Software / Data Engineer', 'Moragn Stanley', 2017);
INSERT INTO IRONOC_DB.EMPLOYER (employer_id, employee_id, title, employer_name, start_year) VALUES (2002, 1001, 'Software Developer', 'BSkyB', 2014);
INSERT INTO IRONOC_DB.EMPLOYER (employer_id, employee_id, title, employer_name, start_year) VALUES (2003, 1002, 'Software Engineer', 'Spotify', 2024);
INSERT INTO IRONOC_DB.EMPLOYER (employer_id, employee_id, title, employer_name, start_year) VALUES (2004, 1003, 'Data Engineer', 'Google', 2025);
