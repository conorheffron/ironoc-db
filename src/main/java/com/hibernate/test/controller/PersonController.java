package com.hibernate.test.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.hibernate.test.model.Person;
import com.hibernate.test.service.PersonService;

@Controller
public class PersonController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PersonController.class);
	
	@Autowired
    private PersonService personService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(ModelMap map) {
		LOGGER.info("Entering personController method home");
		
		List<Person> personslist = personService.getAllPersons();
        map.addAttribute("personsList", personslist);
        map.addAttribute("person", new Person());

        return "personList";
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addperson(ModelMap map, @Valid @ModelAttribute("person") Person person,
			BindingResult result) {
		if (result.hasErrors()) {
			List<Person> personslist = personService.getAllPersons();
	        map.addAttribute("personsList", personslist);
			return "personList";
		}

		personService.addPerson(person);

        return this.home(map);
	}
    
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String deletePersonBySurname(ModelMap map, @RequestParam String surname) {

		List<Person> persons = personService.findPersonBySurname(surname);
		if (!persons.isEmpty()) {
			personService.deletePersonBySurname(surname);
		} else {
			map.addAttribute("deleteError", "There are no matching entries to delete");
		}

        return this.home(map);
	}

}
