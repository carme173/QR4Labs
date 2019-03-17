package it.unisa.studenti.anzelmo2.c.qr4labs.database.user;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface UserLabDao {
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	void insert(List<UserLab> userLabs);

	@Query("DELETE FROM UserLab")
	void deleteAll();

	@Update(onConflict = OnConflictStrategy.REPLACE)
	void update(UserLab userLab);
}
