package it.unisa.studenti.anzelmo2.c.qr4labs.database.lab;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import it.unisa.studenti.anzelmo2.c.qr4labs.database.user.User;

@Dao
public interface LabDao {

	@Insert
	void insertLab(Lab lab);

	@Query("SELECT * FROM Lab WHERE lid=:lid")
	LiveData<Lab> getLab(String lid);

	@Query("DELETE FROM Lab")
	void deleteAll();
}
