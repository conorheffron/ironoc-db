package com.hibernate.test.dao;

import java.util.List;

import com.hibernate.test.model.Person;

public interface PersonDao {

    void addMobilePushMessagesLookup(Person mobilePushMessagesLookupEntity);
    
    List<Person> getAllMobilePushMessagesLookups();
    
    void deleteMobilePushMessagesLookup(Integer mobilePushMessagesLookupId);
    
    List<Person> findMobilePushMessagesLookupsByTitleAndContent();
	
}
