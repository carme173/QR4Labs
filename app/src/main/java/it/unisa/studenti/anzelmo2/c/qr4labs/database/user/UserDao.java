package it.unisa.studenti.anzelmo2.c.qr4labs.database.user;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface UserDao {
	@Query("SELECT * FROM User")
	LiveData<List<User>> getAll();

	@Query("SELECT * FROM User WHERE lid=:lid")
	LiveData<List<User>> getUsersOfLab(String lid);

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	void insertUser(User user);

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	void insertUsers(List<User> users);

	@Query("DELETE FROM User")
	void deleteAll();

	@Query("SELECT * FROM User WHERE uid=:username")
	LiveData<User> getUser(String username);

	@Update(onConflict = OnConflictStrategy.REPLACE)
	void update(User user);

	@Delete
	void delete(User user);
}
