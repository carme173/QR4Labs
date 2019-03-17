package it.unisa.studenti.anzelmo2.c.qr4labs.database.alert;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface AlertDao {

	@Query("SELECT * FROM Alert WHERE lab=:lid")
	LiveData<List<Alert>> getAlertsOfLab(String lid);

	@Query("SELECT * FROM Alert")
	LiveData<List<Alert>> getAllAlerts();

	@Query("DELETE FROM Alert")
	void deleteAll();

	@Delete
	void delete(Alert alert);

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	void insertAlert(Alert alert);

	@Insert
	void insertAlerts(List<Alert> alerts);

	@Query("SELECT * FROM Alert WHERE aid=:aid")
	LiveData<Alert> getAlert(int aid);

	@Update
	void update(Alert alert);
}
