package it.unisa.studenti.anzelmo2.c.qr4labs.database;

import android.os.AsyncTask;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import it.unisa.studenti.anzelmo2.c.qr4labs.database.alert.AlertDao;
import it.unisa.studenti.anzelmo2.c.qr4labs.database.cache.Cache;
import it.unisa.studenti.anzelmo2.c.qr4labs.database.cache.CacheDao;
import it.unisa.studenti.anzelmo2.c.qr4labs.database.lab.Lab;
import it.unisa.studenti.anzelmo2.c.qr4labs.database.lab.LabDao;
import it.unisa.studenti.anzelmo2.c.qr4labs.database.project.ProjectDao;
import it.unisa.studenti.anzelmo2.c.qr4labs.database.user.UserDao;
import it.unisa.studenti.anzelmo2.c.qr4labs.database.user.UserLabDao;

public class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

	private final UserDao uDao;
	private final LabDao lDao;
	private final AlertDao aDao;
	private final CacheDao cDao;
	private final ProjectDao pDao;
	private final UserLabDao ulDao;

	PopulateDbAsync(AppDatabase db) {
		uDao=db.utenteDao();
		lDao=db.labDao();
		aDao=db.alertDao();
		cDao=db.cacheDao();
		pDao=db.projectDao();
		ulDao=db.userLabDao();
	}

	@Override
	protected Void doInBackground(final Void... voids) {
		uDao.deleteAll();
		aDao.deleteAll();
		lDao.deleteAll();
		cDao.deleteAll();
		pDao.deleteAll();
		ulDao.deleteAll();

		lDao.insertLab(new Lab("LabGIS", "LabGIS", "Info LabGIS", "www"));
		lDao.insertLab(new Lab("LabIUM", "Laboratorio IUM", "Info Lab IUM", "www 2"));

		Calendar exp=Calendar.getInstance();
		exp.set(Calendar.YEAR, 2018);
		exp.set(Calendar.MONTH, Calendar.JANUARY);
		exp.set(Calendar.DAY_OF_MONTH, 1);

		cDao.insertLastUpdate(new Cache("utentiLabGIS", exp.getTime()));
		cDao.insertLastUpdate(new Cache("utentiLabIUM", exp.getTime()));
		cDao.insertLastUpdate(new Cache("progetti", exp.getTime()));

		return null;
	}
}
