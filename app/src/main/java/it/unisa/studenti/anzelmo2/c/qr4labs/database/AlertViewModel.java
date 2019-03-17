package it.unisa.studenti.anzelmo2.c.qr4labs.database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

import it.unisa.studenti.anzelmo2.c.qr4labs.database.alert.Alert;

public class AlertViewModel extends AndroidViewModel {

	public UserRepository mRepository;

	public AlertViewModel(Application application) {
		super(application);
		mRepository=new UserRepository(application);
	}

	public LiveData<List<Alert>> getAlertsOfLab(String lid) {
		return mRepository.getAlertsOfLab(lid);
	}

	public LiveData<Alert> getAlert(int aid) {
		return mRepository.getAlert(aid);
	}

	public void insertAlert(Alert alert) {
		mRepository.insertAlert(alert);
	}

	public void updateAlert(Alert alert) {
		mRepository.updateAlert(alert);
	}

	public void deleteAlert(Alert alert) {
		mRepository.deleteAlert(alert);
	}
}
