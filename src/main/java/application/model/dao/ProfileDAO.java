package application.model.dao;

import java.util.List;
import java.util.Optional;

import com.diproject.commons.model.User;

import application.model.ProfileModel;
import application.model.repository.ProfileRepository;

public class ProfileDAO {

	private static ProfileDAO instance;

	private ProfileRepository profileRepository;

	private ProfileDAO() {
		super();
		profileRepository = new ProfileRepository();
	}

	public static ProfileDAO getInstance() {
		return instance == null ? instance = new ProfileDAO() : instance;
	}

	public List<ProfileModel> getAllProfile() {
		return profileRepository.findAll();
	}
	
	public Optional<ProfileModel> findUser(String s) {
		Optional<ProfileModel> profile = profileRepository.findAll().stream().filter(prof -> prof.getUserId().equals(s)).findFirst();
		return profile;
	}

	public void saveProfile(User user) {
		ProfileModel profile = new ProfileModel();
		profile.setUserId(user.getLogin());
		String[] params = user.getUsername().split(" ");
		profile.setNombre(params[0]);
		profile.setApellido1(params[1]);
		profile.setApellido2(params[2]);
		profileRepository.insert(profile);
	}
	
	public void saveProfile(ProfileModel profile) {
		profileRepository.insert(profile);
	}

	public void updateProfile(ProfileModel profile) {
		profileRepository.update(profile);
	}

	public void removeProfile(ProfileModel profile) {
		profileRepository.delete(profile);
	}
}
