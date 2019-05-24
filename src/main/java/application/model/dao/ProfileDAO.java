package application.model.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import application.model.PersonData;
import application.model.ProfileModel;
import application.model.repository.PersonDataRepository;
import application.model.repository.ProfileRepository;

public class ProfileDAO {
	
	private static ProfileDAO instance;
	
	private ProfileRepository profileRepository;
	
	private PersonDataRepository personDataRepository;

	private ProfileDAO() {
		profileRepository = new ProfileRepository();
		personDataRepository = new PersonDataRepository();
	}

	public static ProfileDAO getInstance() {
		return instance == null ? instance = new ProfileDAO() : instance;
	}
	
	public List<ProfileModel> readAllProfiles() {
		List<PersonData> data = personDataRepository.findAll();
		List<ProfileModel> profiles = profileRepository.findAll();
		Map<Integer,PersonData> mapData = new HashMap<>();
		for(PersonData d : data) 
			mapData.put(d.getDataId(), d);
		
		profiles.forEach(prof -> prof.setPersonData(mapData.get(prof.getDataId())));
		return profiles;
	}

	public boolean saveProfile(ProfileModel profile) {
		ProfileModel foundProfile = findByLogin(profile.getLogin());
		boolean notRegistered = foundProfile == null;
		if(notRegistered) {
			personDataRepository.insert(profile.getPersonData());
			profile.setDataId(personDataRepository.findLastPersonId());
			profileRepository.insert(profile);
			profile.setProfileId(profileRepository.findLastProfileId());
		} else {
			fillData(profile, foundProfile);
		}
		return notRegistered;
	}

	private void fillData(ProfileModel profile, ProfileModel foundProfile) {
		profile.setDataId(foundProfile.getDataId());
		profile.setLogin(foundProfile.getLogin());
		profile.setNombre(foundProfile.getNombre());
		profile.setPersonData(foundProfile.getPersonData());
		profile.setPrimerApellido(foundProfile.getPrimerApellido());
		profile.setSegundoApellido(foundProfile.getSegundoApellido());
		profile.setProfileId(foundProfile.getProfileId());
	}

	public void editProfile(ProfileModel profile) {
		if(!profile.getPersonData().isNew())
			personDataRepository.update(profile.getPersonData());
		else {
			personDataRepository.insert(profile.getPersonData());
			profile.setDataId(personDataRepository.findLastPersonId());
		}
		if(!profile.isNew())
			profileRepository.update(profile);
		else {
			profileRepository.insert(profile);
			profile.setProfileId(profileRepository.findLastProfileId());
		}
	}		

	public void removeProfile(ProfileModel profile) {
		profileRepository.delete(profile);
		personDataRepository.delete(profile.getPersonData());
	}

	public ProfileModel findByLogin(String login) {
		ProfileModel profile = null;
		List<PersonData> data = personDataRepository.findByLogin(login);
		if(!data.isEmpty()) {
			int i = 0;
			while(profile == null && i < data.size()) {
				PersonData d = data.get(i);
				profile = profileRepository.findByDataId(d.getDataId());
				if(profile != null)
					profile.setPersonData(d);
				
				i++;
			}
		}
		return profile;
	}
}
