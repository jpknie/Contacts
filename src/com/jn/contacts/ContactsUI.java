package com.jn.contacts;

import javax.servlet.annotation.WebServlet;

import com.jn.contacts.models.Contact;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@Theme("valo")
public class ContactsUI extends UI {

	TextField filter = new TextField();
	Grid contactList = new Grid();
	Button newContact = new Button("Add Contact");
	ContactForm contactForm = new ContactForm();
	
	ContactService service = ContactService.createDemoService();
	
	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = ContactsUI.class)
	public static class Servlet extends VaadinServlet {
	}

	@Override
	protected void init(VaadinRequest request) {
		buildComponents();
		buildLayout();
	}
	
	private void buildComponents() {
		   newContact.addClickListener(e -> contactForm.edit(new Contact()));

	        filter.setInputPrompt("Filter contacts...");
	        filter.addTextChangeListener(e -> refresh(e.getText()));

	        contactList.setContainerDataSource(new BeanItemContainer<>(Contact.class));
	        contactList.setColumnOrder("firstName", "lastName", "email");
	        contactList.removeColumn("birthDate");
	        contactList.removeColumn("phone");
	        contactList.setSelectionMode(Grid.SelectionMode.SINGLE);
	        contactList.addSelectionListener(e
	                -> contactForm.edit((Contact) contactList.getSelectedRow()));
	        refresh();
	}
	
	private void buildLayout() {
		HorizontalLayout actions = new HorizontalLayout(filter, newContact);
		actions.setWidth("100%");
		filter.setWidth("100%");
		actions.setExpandRatio(filter, 1);
		
		VerticalLayout leftLayout = new VerticalLayout(actions, contactList);
		leftLayout.setSizeFull();
		contactList.setSizeFull();
		leftLayout.setExpandRatio(contactList, 1);
		
		HorizontalLayout mainLayout = new HorizontalLayout(leftLayout, contactForm);
		mainLayout.setSizeFull();
		mainLayout.setExpandRatio(leftLayout, 1);
		
		setContent(mainLayout);
		
	}
	
	void refresh() {
		refresh(filter.getValue());
	}
	
	private	void refresh(String filter) {
		contactList.setContainerDataSource(new BeanItemContainer<>(Contact.class, service.findAll(filter)));
		contactForm.setVisible(false);
	}
}