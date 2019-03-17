package it.unisa.studenti.anzelmo2.c.qr4labs.database.cache;

import android.app.SearchManager;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.Date;

@Dao
public interface CacheDao {
	@Query("SELECT lastUpdate FROM Cache WHERE cid='utentiLabGIS'")
	Date lastUpdateUser();

	@Query("SELECT lastUpdate FROM Cache WHERE cid='avvisi'")
	Date lastUpdateAlert();

	@Query("SELECT lastUpdate FROM Cache WHERE cid='progetti'")
	Date lastUpdateProject();

	@Insert(onConflict=OnConflictStrategy.REPLACE)
	void insertLastUpdate(Cache cache);

	@Update
	void update(Cache cache);

	@Query("DELETE FROM Cache")
	void deleteAll();
}
