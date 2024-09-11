package com.ironoc.db.controller;

import com.ironoc.db.model.Person;
import com.ironoc.db.model.Person.PersonBuilder;
import com.ironoc.db.service.PersonService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@Controller
@Slf4j
public class PersonController {

	@Autowired
    private PersonService personService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(ModelMap map) {
		log.info("Entering personController.home: map={}", map);
		
		List<Person> personslist = personService.getAllPersons();
        map.addAttribute("personsList", personslist);
        map.addAttribute("person", new PersonBuilder().build());

        return "personList";
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addPerson(ModelMap map, @Valid @ModelAttribute("person") Person person,
							BindingResult result) {
		log.info("Entering personController.addPerson: map={}, person={}", map, person);
		
		// validation error handling
		if (result.hasErrors()) {			
			List<Person> personslist = personService.getAllPersons();
	        map.addAttribute("personsList", personslist);
			return "personList";
		}

		personService.addPerson(person);

        return this.home(map);
	}
    
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String deletePersonBySurname(ModelMap map, @RequestParam("surname") String surname) {
		log.info("Entering personController.deletePersonBySurname: map={}, surname={}", map, surname);
		List<Person> persons = personService.findPersonBySurname(surname);
		
		if (!persons.isEmpty()) {
			personService.deletePersonBySurname(surname);
		} else {
			// no matching entries to delete
			map.addAttribute("deleteError", "There are no matching entries to delete");
		}

        return this.home(map);
	}
}
