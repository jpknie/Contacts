package com.jn.contacts;

import java.util.List;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;

import com.jn.contacts.models.Contact;
import com.mongodb.MongoClient;

public class ContactsDatabase {
	final Morphia morphia = new Morphia();
	final Datastore dataStore;

	public ContactsDatabase(String hostAddress) {
		morphia.mapPackage("com.jn.contacts.models");
		dataStore = morphia.createDatastore(new MongoClient(hostAddress), "contactsDB");
	}

	public void saveContact(Contact contact) {
		dataStore.save(contact);
	}
	
	public List<Contact> findAll() {
		final Query<Contact> query = dataStore.createQuery(Contact.class);
		final List<Contact> contacts = query.asList();
		return contacts;
	}
	
	public List<Contact> queryByName(String name) {
		Query<Contact> query =
		dataStore.createQuery(Contact.class).field("firstName").contains(name);
		return query.asList();
	}
}
