package com.hibernate.test.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
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
import org.springframework.web.servlet.view.RedirectView;

import com.hibernate.test.model.Person;
import com.hibernate.test.service.PersonService;

@Controller
public class PersonController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PersonController.class);
	
	@Autowired
    private PersonService personService;
	
	@RequestMapping(value = "/*")
	public RedirectView catchAll(HttpServletRequest httpServletRequest) {
		LOGGER.info("Invaid request URL " + httpServletRequest.getRequestURI());
        return new RedirectView("/", false);
	}

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
		LOGGER.info("Entering personController method addPerson");
		
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
		LOGGER.info("Entering personController method deletePersonBySurname");
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
