package it.unisa.studenti.anzelmo2.c.qr4labs.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.support.annotation.NonNull;

import it.unisa.studenti.anzelmo2.c.qr4labs.database.alert.Alert;
import it.unisa.studenti.anzelmo2.c.qr4labs.database.alert.AlertDao;
import it.unisa.studenti.anzelmo2.c.qr4labs.database.alert.DateConverter;
import it.unisa.studenti.anzelmo2.c.qr4labs.database.cache.Cache;
import it.unisa.studenti.anzelmo2.c.qr4labs.database.cache.CacheDao;
import it.unisa.studenti.anzelmo2.c.qr4labs.database.lab.Lab;
import it.unisa.studenti.anzelmo2.c.qr4labs.database.lab.LabDao;
import it.unisa.studenti.anzelmo2.c.qr4labs.database.project.Project;
import it.unisa.studenti.anzelmo2.c.qr4labs.database.project.ProjectDao;
import it.unisa.studenti.anzelmo2.c.qr4labs.database.user.User;
import it.unisa.studenti.anzelmo2.c.qr4labs.database.user.UserDao;
import it.unisa.studenti.anzelmo2.c.qr4labs.database.user.UserLab;
import it.unisa.studenti.anzelmo2.c.qr4labs.database.user.UserLabDao;

@Database(entities = {User.class, Lab.class, Alert.class, Cache.class, Project.class, UserLab.class}, version=19)
@TypeConverters({DateConverter.class})
public abstract class AppDatabase extends RoomDatabase {
	public abstract UserDao utenteDao();
	public abstract LabDao labDao();
	public abstract AlertDao alertDao();
	public abstract CacheDao cacheDao();
	public abstract UserLabDao userLabDao();
	public abstract ProjectDao projectDao();

	private static volatile AppDatabase INSTANCE;


	public static AppDatabase getDatabase(final Context context) {
		if (INSTANCE==null ) {
			synchronized (AppDatabase.class) {
				if (INSTANCE==null) {
					INSTANCE=Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class,
							 "app_db")
//							 .addCallback(sAppDatabaseCallback)
							 .addCallback(new Callback() {
								 @Override
								 public void onCreate(@NonNull SupportSQLiteDatabase db) {
									 super.onCreate(db);

									 new PopulateDbAsync(INSTANCE).execute();
								 }
							 })
							 .fallbackToDestructiveMigration()
							 .build();
				}
			}
		}
		return INSTANCE;
	}

	private static RoomDatabase.Callback sAppDatabaseCallback=new RoomDatabase.Callback() {

		@Override
		public void onCreate(@NonNull SupportSQLiteDatabase db) {
			super.onCreate(db);

			new PopulateDbAsync(INSTANCE).execute();
		}

		@Override
		public void onOpen(@NonNull SupportSQLiteDatabase db) {
			super.onCreate(db);

//			new PopulateDbAsync(INSTANCE).execute();
		}
	};
}
