package com.hibernate.test.controller;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hibernate.test.model.Person;
import com.hibernate.test.service.PersonService;

@Controller
public class PersonController {
	
	private static final Logger logger = LoggerFactory.getLogger(PersonController.class);
	
	@Autowired
    private PersonService mobilePushMessagesLookupService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, ModelMap map) {
		logger.info("Entering MobilePushMessagesLookupController method home");
		
		map.addAttribute("mobilePushMessagesLookup", new Person());
        map.addAttribute("mobilePushMessagesLookupList", mobilePushMessagesLookupService.getAllMobilePushMessagesLookups());
        
		mobilePushMessagesLookupService.findMobilePushMessagesLookupsByTitleAndContent();
		
        return "editMobilePushMessagesLookupList";
	}
	
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addMobilePushMessagesLookup(@ModelAttribute(value="mobilePushMessagesLookup") Person mobilePushMessagesLookupEntity, BindingResult result) {
        mobilePushMessagesLookupService.addMobilePushMessagesLookup(mobilePushMessagesLookupEntity);
        return "redirect:/";
    }
    
    @RequestMapping("/delete/{mobilePushMessagesLookupId}")
    public String deleteMobilePushMessagesLookup(@PathVariable("mobilePushMessagesLookupId") Integer mobilePushMessagesLookupId) {
        mobilePushMessagesLookupService.deleteMobilePushMessagesLookup(mobilePushMessagesLookupId);
        return "redirect:/";
    }
    
	public void setMobilePushMessagesLookupService(
			PersonService mobilePushMessagesLookupService) {
		this.mobilePushMessagesLookupService = mobilePushMessagesLookupService;
	}

}
