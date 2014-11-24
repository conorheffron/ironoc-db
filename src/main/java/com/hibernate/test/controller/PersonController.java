package com.hibernate.test.controller;

import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hibernate.test.model.Person;
import com.hibernate.test.service.PersonService;

@Controller
public class PersonController {
	
	private static final Logger logger = LoggerFactory.getLogger(PersonController.class);
	
	@Autowired
    private PersonService personService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, ModelMap map) {
		logger.info("Entering personController method home");
		
		List<Person> personslist = personService.getAllPersons();
        map.addAttribute("personsList", personslist);

        return "personList";
	}
    
	public void setMobilePushMessagesLookupService(
			PersonService mobilePushMessagesLookupService) {
		this.personService = mobilePushMessagesLookupService;
	}

}
