package application.model.dao;

import java.util.List;

import application.model.ContactModel;
import application.model.repository.ContactRepository;

public class ContactDAO {

	private static ContactDAO instance;

	private ContactRepository contactRepository;
	
	public static ContactDAO getInstance() {
		return instance == null ? instance = new ContactDAO() : instance;
	}

	private ContactDAO() {
		super();
		contactRepository = new ContactRepository();
	}

	public List<ContactModel> getAllContacts() {
		return contactRepository.findAll();
	}

	public void removeContact(ContactModel contact) {
		contactRepository.delete(contact);
	}
	
	public void updateContact(ContactModel contact) {
		contactRepository.update(contact);
	}
	
	public void saveContact(ContactModel contact) {
		contactRepository.insert(contact);
	}
}
