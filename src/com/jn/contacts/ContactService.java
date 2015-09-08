package com.jn.contacts;

import java.io.Serializable;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jn.contacts.models.Contact;
import com.mongodb.*;

import org.apache.commons.beanutils.BeanUtils;


public class ContactService implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private static ContactService instance;
    private static ContactsDatabase db = new ContactsDatabase("localhost");
    
    public static ContactService createDemoService() {
    	instance = new ContactService();
        return instance;
    }

    public synchronized List<Contact> findAll(String stringFilter) {
    	if(stringFilter == null || stringFilter == "") {
    		return db.findAll();
    	}

    	return db.queryByName(stringFilter);
    }

    public synchronized long count() {
    	return 0;
    }

    public synchronized void delete(Contact value) {
   
    }

    public synchronized void save(Contact entry) {
    	db.saveContact(entry);
    }	
}
