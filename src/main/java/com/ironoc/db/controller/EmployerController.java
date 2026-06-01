package com.ironoc.db.controller;

import com.ironoc.db.dto.EmployerDto;
import com.ironoc.db.model.Employer;
import com.ironoc.db.model.Person;
import com.ironoc.db.service.EmployerService;
import com.ironoc.db.service.PersonService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
public class EmployerController {

    private final EmployerService employerService;
    private final PersonService personService;

    public EmployerController(@Autowired EmployerService employerService,
                              @Autowired PersonService personService) {
        this.employerService = employerService;
        this.personService = personService;
    }

    @GetMapping("/job-history/{personId}")
    public String showJobHistory(@PathVariable("personId") Long personId, Model model) {
        log.info("Entering employerController.showJobHistory: personId={}", personId);
        Optional<Person> person = personService.findPersonById(personId);
        if (person.isPresent()) {
            List<Employer> employers = employerService.getEmployersByPersonId(personId);
            model.addAttribute("person", person.get());
            model.addAttribute("employers", employers);
            model.addAttribute("employer", EmployerDto.builder().build());
        } else {
            log.error("No person found for personId={}", personId);
            return "redirect:/";
        }
        return "job-history";
    }

    @PostMapping("/job-history/add/{personId}")
    public String addJobHistory(@PathVariable("personId") Long personId,
                                @Valid @ModelAttribute("employer") EmployerDto employerDto,
                                BindingResult result, Model model) {
        log.info("Entering employerController.addJobHistory: personId={}, employer={}", personId, employerDto);
        if (result.hasErrors()) {
            Optional<Person> person = personService.findPersonById(personId);
            if (person.isPresent()) {
                model.addAttribute("person", person.get());
                model.addAttribute("employers", employerService.getEmployersByPersonId(personId));
            }
            model.addAttribute("employer", employerDto);
            return "job-history";
        }
        Optional<Person> person = personService.findPersonById(personId);
        if (person.isPresent()) {
            Employer employer = Employer.builder()
                    .person(person.get())
                    .title(employerDto.getTitle())
                    .employerName(employerDto.getEmployerName())
                    .startYear(employerDto.getStartYear())
                    .build();
            employerService.addEmployer(employer);
        }
        return "redirect:/job-history/" + personId;
    }

    @GetMapping("/job-history/edit/{employerId}")
    public String showEditJobHistory(@PathVariable("employerId") Long employerId, Model model) {
        log.info("Entering employerController.showEditJobHistory: employerId={}", employerId);
        Optional<Employer> employer = employerService.findEmployerById(employerId);
        if (employer.isPresent()) {
            Employer emp = employer.get();
            EmployerDto employerDto = EmployerDto.builder()
                    .employerId(emp.getEmployerId())
                    .personId(emp.getPerson().getId())
                    .title(emp.getTitle())
                    .employerName(emp.getEmployerName())
                    .startYear(emp.getStartYear())
                    .build();
            model.addAttribute("employer", employerDto);
        } else {
            log.error("No job history record found for employerId={}", employerId);
            return "redirect:/";
        }
        return "edit-job-history";
    }

    @PostMapping("/job-history/update/{employerId}")
    public String updateJobHistory(@PathVariable("employerId") Long employerId,
                                   @Valid @ModelAttribute("employer") EmployerDto employerDto,
                                   BindingResult result, Model model) {
        log.info("Entering employerController.updateJobHistory: employerId={}, employer={}", employerId, employerDto);
        if (result.hasErrors()) {
            employerDto.setEmployerId(employerId);
            model.addAttribute("employer", employerDto);
            return "edit-job-history";
        }
        Optional<Employer> existingEmployer = employerService.findEmployerById(employerId);
        if (existingEmployer.isPresent()) {
            Employer emp = existingEmployer.get();
            emp.setTitle(employerDto.getTitle());
            emp.setEmployerName(employerDto.getEmployerName());
            emp.setStartYear(employerDto.getStartYear());
            employerService.addEmployer(emp);
            return "redirect:/job-history/" + emp.getPerson().getId();
        }
        return "redirect:/";
    }

    @DeleteMapping("/job-history/delete/{employerId}")
    public String deleteJobHistory(@PathVariable("employerId") Long employerId,
                                   @RequestParam("personId") Long personId) {
        log.info("Entering employerController.deleteJobHistory: employerId={}, personId={}", employerId, personId);
        Optional<Employer> employer = employerService.findEmployerById(employerId);
        if (employer.isPresent()) {
            employerService.deleteEmployerById(employerId);
        }
        return "redirect:/job-history/" + personId;
    }
}
