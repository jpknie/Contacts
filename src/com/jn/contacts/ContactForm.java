package com.jn.contacts;

import com.jn.contacts.models.Contact;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.Button;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

public class ContactForm extends FormLayout {
	Button save = new Button("Save", this::save);
	Button cancel = new Button("Cancel", this::cancel);
	TextField firstName = new TextField("First name");
    TextField lastName = new TextField("Last name");
    TextField phone = new TextField("Phone");
    TextField email = new TextField("Email");
    DateField birthDate = new DateField("Birth date");
    
    Contact contact;

    BeanFieldGroup<Contact> formFieldBindings;
	
	public void buildComponents() {
		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
		setVisible(false);
	}
	
	public void buildLayout() {
		setSizeUndefined();
		setMargin(true);
		
		HorizontalLayout actions = new HorizontalLayout(save, cancel);
		actions.setSpacing(true);
		addComponents(actions, firstName, lastName, phone, email, birthDate);
	}
	
	public ContactForm() {
		buildComponents();
		buildLayout();
	}
	
	public void save(Button.ClickEvent event) {
		try {
			formFieldBindings.commit();
			getUI().service.save(contact);
		} catch(FieldGroup.CommitException e) {
			
		}
	}
	
	public void cancel(Button.ClickEvent event) {
		getUI().contactList.select(null);
	}
	
	public void edit(Contact contact) {
		this.contact = contact;
		if(contact != null) {
			formFieldBindings = BeanFieldGroup.bindFieldsBuffered(contact, this);
			firstName.focus();
		}
		setVisible(contact != null);
	}
	
	@Override
	public ContactsUI getUI() {
		return (ContactsUI)super.getUI();
	}
}
