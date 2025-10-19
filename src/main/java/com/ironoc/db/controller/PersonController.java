package com.ironoc.db.controller;

import module java.base;

import com.ironoc.db.model.Person;
import com.ironoc.db.service.PersonService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
public class PersonController {

    private final PersonService personService;

    public PersonController(@Autowired PersonService personService) {
        this.personService = personService;
    }

	@GetMapping(value = "/")
	public String home(ModelMap map) {
		log.info("Entering personController.home: map={}", map);
		
		List<Person> personslist = personService.getAllPersons();
        map.addAttribute("personsList", personslist);
        map.addAttribute("person", Person.builder().build());

        return "index";
	}
	
	@PostMapping(value = "/add")
	public String addPerson(ModelMap map, @Valid @ModelAttribute("person") Person person,
							BindingResult result) {
		log.info("Entering personController.addPerson: map={}, person={}", map, person);
		
		// validation error handling
		if (result.hasErrors()) {			
			List<Person> personslist = personService.getAllPersons();
	        map.addAttribute("personsList", personslist);
			return "index";
		}

		personService.addPerson(person);
		return "redirect:/";
	}
    
	@DeleteMapping(value = "/delete/{id}")
	public String deletePersonById(ModelMap map, @PathVariable("id") Integer id) {
		log.info("Entering personController.deletePersonBySurname: map={}, id={}", map, id.longValue());
		Optional<Person> person = personService.findPersonById(id.longValue());
		if (person.isPresent()) {
			personService.deletePersonById(id.longValue());
		} else {
			// no matching entries to delete
			map.addAttribute("deleteError", "There are no matching entries to delete");
		}
		return "redirect:/";
	}

	@GetMapping("/edit/{id}")
	public String showEditView(@PathVariable("id") Integer id, Model model) {
		log.info("Entering personController.showEditView: ID={}, model={}", id, model.asMap());
		Optional<Person> person = personService.findPersonById(id.longValue());
		if (person.isPresent()) {
			model.addAttribute("person", person.get());
		} else {
			// no matching entries to delete
			log.error("There are no matching entries to delete for id={}", id);
		}
		return "edit-person";
	}

	@PostMapping("/update/{id}")
	public String updatePerson(ModelMap map, @PathVariable("id") Integer id, @Valid Person person,
							   BindingResult result) {
		log.info("Entering personController.updatePerson: ID={}, person={}", id, person.toString());
		if (result.hasErrors()) {
			person.setId(id.longValue());
			map.addAttribute("person", person);
			return "edit-person";
		}
		personService.addPerson(person);
		return "redirect:/";
	}
}
