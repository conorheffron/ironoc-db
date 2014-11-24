package com.hibernate.test.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hibernate.test.dao.PersonDao;
import com.hibernate.test.model.Person;

@Service
public class PersonServiceImpl implements
		PersonService {
	
	private static final Logger logger = LoggerFactory.getLogger(PersonServiceImpl.class);
	
	@Autowired
    private PersonDao mobilePushMessagesLookupDao;

	@Override
	@Transactional
	public void addMobilePushMessagesLookup(
			Person mobilePushMessagesLookupEntity) {
		logger.debug("Entering class MobilePushMessagesLookupServiceImpl method addMobilePushMessagesLookup");
		
		mobilePushMessagesLookupDao.addMobilePushMessagesLookup(mobilePushMessagesLookupEntity);
		
		logger.debug("Leaving class MobilePushMessagesLookupServiceImpl method addMobilePushMessagesLookup");
	}

	@Override
	@Transactional
	public List<Person> getAllMobilePushMessagesLookups() {
		return mobilePushMessagesLookupDao.getAllMobilePushMessagesLookups();
	}

	@Override
	@Transactional
	public void deleteMobilePushMessagesLookup(
			Integer mobilePushMessagesLookupId) {
		logger.debug("Entering class MobilePushMessagesLookupServiceImpl method deleteMobilePushMessagesLookup");
		
		mobilePushMessagesLookupDao.deleteMobilePushMessagesLookup(mobilePushMessagesLookupId);
		
		logger.debug("Leaving class MobilePushMessagesLookupServiceImpl method deleteMobilePushMessagesLookup");
	}

	@Override
	@Transactional
	public void findMobilePushMessagesLookupsByTitleAndContent() {
		logger.debug("Entering class MobilePushMessagesLookupServiceImpl method findMobilePushMessagesLookupsByTitleAndContent");
		
		mobilePushMessagesLookupDao.findMobilePushMessagesLookupsByTitleAndContent();
		
		logger.debug("Leaving class MobilePushMessagesLookupServiceImpl method findMobilePushMessagesLookupsByTitleAndContent");
	}

	public void setMobilePushMessagesLookupDao(
			PersonDao mobilePushMessagesLookupDao) {
		this.mobilePushMessagesLookupDao = mobilePushMessagesLookupDao;
	}

}
