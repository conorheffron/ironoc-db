package com.hibernate.test.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hibernate.test.model.Person;

@Repository
public class PersonDaoImpl implements
		PersonDao {
	
	private static final Logger logger = LoggerFactory.getLogger(PersonDaoImpl.class);
	
    @Autowired
    private SessionFactory sessionFactory;

	@Override
	public void addMobilePushMessagesLookup(
			Person mobilePushMessagesLookupEntity) {
		this.sessionFactory.getCurrentSession().save(mobilePushMessagesLookupEntity);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Person> getAllMobilePushMessagesLookups() {
		return this.sessionFactory.getCurrentSession().createQuery("from MobilePushMessagesLookupEntity").list();
	}

	@Override
	public void deleteMobilePushMessagesLookup(
			Integer mobilePushMessagesLookupId) {
		Person mobilePushMessagesLookup = (Person) sessionFactory.getCurrentSession().load(
                Person.class, mobilePushMessagesLookupId);
        if (null != mobilePushMessagesLookup) {
            this.sessionFactory.getCurrentSession().delete(mobilePushMessagesLookup);
        }
	}

	/**
	 * This look up is required to replace existing lookup query in CRM project in review mapping
	 * @param should pass two string parameters (title and content) - hard coded for isolated testing
	 */
	@Override
	public List<Person> findMobilePushMessagesLookupsByTitleAndContent() {
		logger.debug("Entering class MobilePushMessagesLookupDaoImpl method findMobilePushMessagesLookupsByTitleAndContent");
		
		Query query = this.sessionFactory.getCurrentSession().createQuery("from MobilePushMessagesLookupEntity where title = :title and content = :content");
		
		// set parameters
		query.setString("title", "new");// ** hard coded for testing **
		query.setString("content", "new");// ** hard coded for testing **
		
		@SuppressWarnings("unchecked")
		List<Person> mobilePushMessagesLookupList = query.list();
		
//		for (MobilePushMessagesLookupEntity mobilePushMessagesLookup : mobilePushMessagesLookupList) {
//			logger.debug("\n" + "ID: " + mobilePushMessagesLookup.getId() + "\n" 
//							+ "Title: " + mobilePushMessagesLookup.getTitle() + "\n"
//							+ "Content: " + mobilePushMessagesLookup.getContent() + "\n"
//							+ "URL: " + mobilePushMessagesLookup.getUrl() + "\n"
//							+ "Omniture Campaign ID: " + mobilePushMessagesLookup.getOmnitureCampaignId() + "\n"
//							+ "Development Message?: " + mobilePushMessagesLookup.getIsDevTest());
//		}
		
		logger.debug("Leaving class MobilePushMessagesLookupDaoImpl method findMobilePushMessagesLookupsByTitleAndContent");
		return mobilePushMessagesLookupList;
	}

}
