package it.unisa.studenti.anzelmo2.c.qr4labs.database.project;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ProjectDao {
	@Insert
	void insert(Project project);

	@Query("SELECT * FROM Project WHERE uid=:uid")
	LiveData<Project> getProjectsOfUser(String uid);

	@Query("SELECT * FROM Project WHERE lid=:lid")
	LiveData<List<Project>> getProjectsOfLab(String lid);

	@Query("DELETE FROM Project")
	void deleteAll();

	@Insert
	void insertProjects(List<Project> projects);
}
