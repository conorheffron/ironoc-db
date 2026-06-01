package com.ironoc.db.controller;

import java.util.List;
import java.util.Optional;

import com.ironoc.db.dto.PersonDto;
import com.ironoc.db.mapper.PersonMapper;
import com.ironoc.db.model.Person;
import com.ironoc.db.service.PersonService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
public class PersonController {

    private static final int PAGE_SIZE = 5;

    private final PersonService personService;
    private final PersonMapper personMapper;

    public PersonController(@Autowired PersonService personService, @Autowired PersonMapper personMapper) {
        this.personService = personService;
        this.personMapper = personMapper;
    }

    @GetMapping(value = "/")
<<<<<<< HEAD
    public String home(ModelMap map, @RequestParam(value = "surname", required = false) String surnameFilter) {
        log.info("Entering personController.home: map={}", map);

        List<Person> personslist;
        if (surnameFilter != null && !surnameFilter.isBlank()) {
            String sanitizedSurnameFilter = surnameFilter.trim();
            personslist = personService.findPersonBySurname(sanitizedSurnameFilter);
            map.addAttribute("surnameFilter", sanitizedSurnameFilter);
        } else {
            personslist = personService.getAllPersons();
            map.addAttribute("surnameFilter", "");
        }
        map.addAttribute("personsList", personslist);
=======
    public String home(ModelMap map, @RequestParam(defaultValue = "0") int page) {
        log.info("Entering personController.home: map={}", map);

        Page<Person> personsPage = personService.getPersonsPage(page, PAGE_SIZE);
        map.addAttribute("personsList", personsPage.getContent());
        map.addAttribute("currentPage", personsPage.getNumber());
        map.addAttribute("totalPages", personsPage.getTotalPages());
>>>>>>> origin/main
        map.addAttribute("person", PersonDto.builder().build());

        return "index";
    }

    @PostMapping(value = "/add")
    public String addPerson(ModelMap map, @Valid @ModelAttribute("person") PersonDto person,
                            BindingResult result) {
        log.info("Entering personController.addPerson: map={}, person={}", map, person);

        // validation error handling
        if (result.hasErrors()) {
            Page<Person> personsPage = personService.getPersonsPage(0, PAGE_SIZE);
            map.addAttribute("personsList", personsPage.getContent());
            map.addAttribute("currentPage", personsPage.getNumber());
            map.addAttribute("totalPages", personsPage.getTotalPages());
            map.addAttribute("person", person);
            return "index";
        }

        personService.addPerson(personMapper.toPerson(person));
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
            model.addAttribute("person", personMapper.toPersonDto(person.get()));
        } else {
            // no matching entries to delete
            log.error("There are no matching entries to delete for id={}", id);
        }
        return "edit-person";
    }

    @PostMapping("/update/{id}")
    public String updatePerson(ModelMap map, @PathVariable("id") Integer id, @Valid @ModelAttribute("person") PersonDto person,
                               BindingResult result) {
        log.info("Entering personController.updatePerson: ID={}, person={}", id, person);
        if (result.hasErrors()) {
            person.setId(id.longValue());
            map.addAttribute("person", person);
            return "edit-person";
        }
        personService.addPerson(personMapper.toPerson(id.longValue(), person));
        return "redirect:/";
    }
}
