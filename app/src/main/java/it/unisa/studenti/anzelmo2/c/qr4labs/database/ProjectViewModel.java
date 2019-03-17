package it.unisa.studenti.anzelmo2.c.qr4labs.database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import it.unisa.studenti.anzelmo2.c.qr4labs.database.project.Project;

public class ProjectViewModel extends AndroidViewModel {

	private UserRepository repository;

	public ProjectViewModel(@NonNull Application application) {
		super(application);
		repository=new UserRepository(application);
	}

	public LiveData<List<Project>> getProjectsOfLab(String lid) {
		return repository.getProjectsOfLab(lid);
	}
}
