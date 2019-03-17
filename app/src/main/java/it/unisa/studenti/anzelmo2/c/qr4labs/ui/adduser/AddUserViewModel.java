package it.unisa.studenti.anzelmo2.c.qr4labs.ui.adduser;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;

import it.unisa.studenti.anzelmo2.c.qr4labs.database.ManagerRepository;
import it.unisa.studenti.anzelmo2.c.qr4labs.database.user.User;

public class AddUserViewModel extends AndroidViewModel {

	private ManagerRepository mManagerRepository;

	public AddUserViewModel(Application app) {
		super(app);
		mManagerRepository=new ManagerRepository(app);
	}

	public void insertUser(User user) {
		mManagerRepository.insertUser(user);
	}
}
