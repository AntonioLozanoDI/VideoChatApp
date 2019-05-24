package application.model.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import application.model.ContactModel;
import application.model.PersonData;
import application.model.ProfileModel;
import application.model.repository.ContactRepository;
import application.model.repository.PersonDataRepository;

public class ContactDAO {

	public interface ContactDataChanged {
		void onChange(ContactModel contact);
	}

	private List<ContactDataChanged> listenersCreate;
	private List<ContactDataChanged> listenersUpdate;
	private List<ContactDataChanged> listenersDelete;

	private static ContactDAO instance;

	private ContactRepository contactRepository;
	
	private PersonDataRepository personDataRepository;

	public static ContactDAO getInstance() {
		return instance == null ? instance = new ContactDAO() : instance;
	}

	private ContactDAO() {
		super();
		contactRepository = new ContactRepository();
		personDataRepository = new PersonDataRepository();
		listenersCreate = new ArrayList<>();
		listenersUpdate = new ArrayList<>();
		listenersDelete = new ArrayList<>();
	}

	public List<ContactModel> readAllContacts() {
		List<PersonData> data = personDataRepository.findAll();
		List<ContactModel> contacts = contactRepository.findAll();
		Map<Integer,PersonData> mapData = new HashMap<>();
		for(PersonData d : data) 
			mapData.put(d.getDataId(), d);
		
		contacts.forEach(contact -> contact.setPersonData(mapData.get(contact.getDataId())));
		return contacts;
	}
	
	public List<ContactModel> readAllContactsFromLoggedUser(ProfileModel logged) {
		List<ContactModel> contacts = contactRepository.findByProfileId(logged.getProfileId());
		List<PersonData> data = personDataRepository.findAll();
		Map<Integer,PersonData> mapData = new HashMap<>();
		for(PersonData d : data) 
			mapData.put(d.getDataId(),d);
		
		contacts.forEach(contact -> contact.setPersonData(mapData.get(contact.getDataId())));
		return contacts;
	}

	public void saveContact(ContactModel contact) {
		Optional<PersonData> data = personDataRepository.exists(contact.getLogin());
		if(!data.isPresent()) {
			personDataRepository.insert(contact.getPersonData());
			contact.setDataId(personDataRepository.findLastPersonId());
		} else 
			contact.setPersonData(data.get());
			
		contactRepository.insert(contact);
		contact.setContactId(contactRepository.findLastContactId());
		listenersCreate.forEach(listener -> listener.onChange(contact));
	}

	public void editContact(ContactModel contact) {
		if(!contact.getPersonData().isNew())
			personDataRepository.update(contact.getPersonData());
		else {
			personDataRepository.insert(contact.getPersonData());
			contact.setDataId(personDataRepository.findLastPersonId());
		}
		if(!contact.isNew())
			contactRepository.update(contact);
		else {
			contactRepository.insert(contact);
			contact.setContactId(contactRepository.findLastContactId());
		}
		listenersUpdate.forEach(listener -> listener.onChange(contact));
	}

	public void removeContact(ContactModel contact) {
		if(!contact.isNew()) {
			contactRepository.delete(contact);
			personDataRepository.delete(contact.getPersonData());
		}
		listenersDelete.forEach(listener -> listener.onChange(contact));
	}

	public void addOnContactCreated(ContactDataChanged listener) {
		listenersCreate.add(Objects.requireNonNull(listener));
	}

	public void addOnContactUpdated(ContactDataChanged listener) {
		listenersUpdate.add(Objects.requireNonNull(listener));
	}

	public void addOnContactDeleted(ContactDataChanged listener) {
		listenersDelete.add(Objects.requireNonNull(listener));
	}
}
