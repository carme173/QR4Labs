package it.unisa.studenti.anzelmo2.c.qr4labs.database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

import it.unisa.studenti.anzelmo2.c.qr4labs.database.user.User;

public class UserViewModel extends AndroidViewModel {

	private UserRepository mRepository;

	public UserViewModel(Application application) {
		super(application);
		mRepository=new UserRepository(application);
	}

	public LiveData<User> getUser(String username) {
		return mRepository.getUser(username);
	}

	public LiveData<List<User>> getUsersOfLab(String lid) {
 		return mRepository.getUsersOfLab(lid);
	}

	public LiveData<List<User>> getUsersOfLab(String lid, boolean update) {
		return mRepository.getUsersOfLab(lid, update);
	}

	public void updateUser(User user) {
		mRepository.updateUser(user);
	}

	public void deleteUser(User user) {
		mRepository.deleteUser(user);
	}
}
