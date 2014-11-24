package com.hibernate.test.service;

import java.util.List;

import com.hibernate.test.model.Person;

public interface PersonService {

	void addMobilePushMessagesLookup(Person mobilePushMessagesLookupEntity);
	
    List<Person> getAllMobilePushMessagesLookups();
    
    void deleteMobilePushMessagesLookup(Integer mobilePushMessagesLookupId);
    
    void findMobilePushMessagesLookupsByTitleAndContent();
	
}
