HibernateExample
================

Hibernate Example - Basic hibernate set up to view a person attributes

MySql Script 

CREATE TABLE `test`.`person` (
  `id` INT NULL AUTO_INCREMENT,
  `title` VARCHAR(45) NOT NULL,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci
COMMENT = 'test person table';


INSERT INTO `test`.`person` (`id`, `title`) VALUES ('1', 'Mr Conor');

INSERT INTO `test`.`person` (`id`, `title`) VALUES ('2', 'Mr John');

INSERT INTO `test`.`person` (`id`, `title`) VALUES ('3', 'Joe Bloggs');

