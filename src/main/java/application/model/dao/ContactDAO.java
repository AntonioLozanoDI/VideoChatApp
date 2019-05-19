package application.model.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import application.model.ContactModel;
import application.model.repository.ContactRepository;

public class ContactDAO {

	public interface ContactDataChanged {
		void notifyObserver(ContactModel contact);
	}

	private List<ContactDataChanged> listenersCreate;
	private List<ContactDataChanged> listenersUpdate;
	private List<ContactDataChanged> listenersDelete;

	private static ContactDAO instance;

	private ContactRepository contactRepository;

	public static ContactDAO getInstance() {
		return instance == null ? instance = new ContactDAO() : instance;
	}

	private ContactDAO() {
		super();
		contactRepository = new ContactRepository();
		listenersCreate = new ArrayList<>();
		listenersUpdate = new ArrayList<>();
		listenersDelete = new ArrayList<>();
	}

	public List<ContactModel> getAllContacts() {
		return contactRepository.findAll();
	}

	public void saveContact(ContactModel contact) {
		contactRepository.insert(contact);
		listenersCreate.forEach(listener -> listener.notifyObserver(contact));
	}

	public void updateContact(ContactModel contact) {
		contactRepository.update(contact);
		listenersUpdate.forEach(listener -> listener.notifyObserver(contact));
	}

	public void removeContact(ContactModel contact) {
		contactRepository.delete(contact);
		listenersDelete.forEach(listener -> listener.notifyObserver(contact));
	}

	public void setOnContactCreated(ContactDataChanged listener) {
		listenersCreate.add(Objects.requireNonNull(listener));
	}

	public void setOnContactUpdated(ContactDataChanged listener) {
		listenersUpdate.add(Objects.requireNonNull(listener));
	}

	public void setOnContactDeleted(ContactDataChanged listener) {
		listenersDelete.add(Objects.requireNonNull(listener));
	}
}
