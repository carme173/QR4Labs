package it.unisa.studenti.anzelmo2.c.qr4labs.database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;


import it.unisa.studenti.anzelmo2.c.qr4labs.database.alert.Alert;
import it.unisa.studenti.anzelmo2.c.qr4labs.database.alert.AlertDao;
import it.unisa.studenti.anzelmo2.c.qr4labs.database.cache.Cache;
import it.unisa.studenti.anzelmo2.c.qr4labs.database.cache.CacheDao;
import it.unisa.studenti.anzelmo2.c.qr4labs.database.project.Project;
import it.unisa.studenti.anzelmo2.c.qr4labs.database.project.ProjectDao;
import it.unisa.studenti.anzelmo2.c.qr4labs.database.user.User;
import it.unisa.studenti.anzelmo2.c.qr4labs.database.user.UserDao;
import it.unisa.studenti.anzelmo2.c.qr4labs.database.user.UserLabDao;
import it.unisa.studenti.anzelmo2.c.qr4labs.network.Webservice;
import it.unisa.studenti.anzelmo2.c.qr4labs.utility.ServiceLocator;
import it.unisa.studenti.anzelmo2.c.qr4labs.utility.WebResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {

	private final Webservice webservice;
	private final UserDao userDao;
	private final AlertDao alertDao;
	private final CacheDao cacheDao;
	private final UserLabDao userLabDao;
	private final ProjectDao projectDao;
	private final Executor executor;
	private ServiceLocator serviceLocator;

	public UserRepository(Application app) {
		AppDatabase appDatabase=AppDatabase.getDatabase(app);
		userDao=appDatabase.utenteDao();
		alertDao=appDatabase.alertDao();
		cacheDao=appDatabase.cacheDao();
		userLabDao=appDatabase.userLabDao();
		projectDao=appDatabase.projectDao();
		serviceLocator=ServiceLocator.getInstance();
		webservice=ServiceLocator.getWebservice();
		executor=ServiceLocator.getExecutor();
	}

	LiveData<List<User>> getUsersOfLab(String lid) {
		refreshUsers(lid);

		return userDao.getUsersOfLab(lid);
	}

	private void refreshUsers(final String lid) {
		executor.execute(new Runnable() {
			@Override
			public void run() {
				Calendar now=Calendar.getInstance();
				Calendar update=Calendar.getInstance();

				Date lastUpdate=cacheDao.lastUpdateUser();

				update.setTime(lastUpdate);

				int yearNow=now.get(Calendar.YEAR);
				int monthNow=now.get(Calendar.MONTH);
				int dayNow=now.get(Calendar.DAY_OF_MONTH);

				int yearUpdate=update.get(Calendar.YEAR);
				int monthUpdate=update.get(Calendar.MONTH);
				int dayUpdate=update.get(Calendar.DAY_OF_MONTH);

				// se la data di oggi è maggiore della data dell'ultimo aggiornamento
				// cioè se la lista utenti deve essere aggiornata
				if (yearNow>yearUpdate || monthNow>monthUpdate || dayNow>dayUpdate) {
					try {
						retrofit2.Response<List<User>> response=webservice.getUsersOfLab(lid).execute();
						List<User> fromNetwork=response.body();

						userDao.deleteAll();
						userDao.insertUsers(fromNetwork);
						cacheDao.update(new Cache("utenti"+lid, now.getTime()));
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}


	LiveData<List<User>> getUsersOfLab(String lid, boolean update) {
		refreshUsers(lid, update);

		return userDao.getUsersOfLab(lid);
	}

	private void refreshUsers(final String lid, boolean update) {
		executor.execute(new Runnable() {
			@Override
			public void run() {
				Calendar now=Calendar.getInstance();
				Calendar update=Calendar.getInstance();

				Date lastUpdate=cacheDao.lastUpdateUser();

				update.setTime(lastUpdate);

				int yearNow=now.get(Calendar.YEAR);
				int monthNow=now.get(Calendar.MONTH);
				int dayNow=now.get(Calendar.DAY_OF_MONTH);

				int yearUpdate=update.get(Calendar.YEAR);
				int monthUpdate=update.get(Calendar.MONTH);
				int dayUpdate=update.get(Calendar.DAY_OF_MONTH);

				// se la data di oggi è maggiore della data dell'ultimo aggiornamento
				// cioè se la lista utenti deve essere aggiornata
				if (yearNow>yearUpdate || monthNow>monthUpdate || dayNow>dayUpdate) {
					try {
						retrofit2.Response<List<User>> response=webservice.getUsersOfLab(lid).execute();
						List<User> fromNetwork=response.body();

						userDao.deleteAll();
						userDao.insertUsers(fromNetwork);
						cacheDao.update(new Cache("utenti"+lid, now.getTime()));
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}

	LiveData<User> getUser(String username) {
		return userDao.getUser(username);
	}

	LiveData<List<Alert>> getAlertsOfLab(String lid) {
		refreshAlerts(lid);

		return alertDao.getAlertsOfLab(lid);
	}

	private void refreshAlerts(final String lid) {
		executor.execute(new Runnable() {
			@Override
			public void run() {
				try {
					Response<List<Alert>> response=webservice.getAlertsOfLab(lid).execute();
					List<Alert> alerts=response.body();

					alertDao.deleteAll();
					alertDao.insertAlerts(alerts);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}

	LiveData<Alert> getAlert(int aid) {
		return alertDao.getAlert(aid);
	}

	void insertAlert(Alert alert) {
		webservice.addAlert(alert).enqueue(new Callback<WebResponse>() {
			@Override
			public void onResponse(Call<WebResponse> call, Response<WebResponse> response) {
			}

			@Override
			public void onFailure(Call<WebResponse> call, Throwable t) {
				t.getCause();
			}
		});
		new insertAlertAsyncTask(alertDao).execute(alert);
	}

	private static class insertAlertAsyncTask extends AsyncTask<Alert, Void, Void> {
		private AlertDao mAsyncAlertTaskDao;

		insertAlertAsyncTask(AlertDao dao) {
			mAsyncAlertTaskDao=dao;
		}

		@Override
		protected Void doInBackground(final Alert... params) {
				mAsyncAlertTaskDao.insertAlert(params[0]);
			return null;
		}
	}

	LiveData<List<Project>> getProjectsOfLab(String lid) {
		refreshProjects();

		return projectDao.getProjectsOfLab(lid);
	}

	private void refreshProjects() {
		executor.execute(new Runnable() {
			@Override
			public void run() {
				Calendar now=Calendar.getInstance();
				Calendar update=Calendar.getInstance();

				Date lastUpdate=cacheDao.lastUpdateProject();
				update.setTime(lastUpdate);

				int yearNow=now.get(Calendar.YEAR);
				int monthNow=now.get(Calendar.MONTH);
				int dayNow=now.get(Calendar.DAY_OF_MONTH);

				int yearUpdate=update.get(Calendar.YEAR);
				int monthUpdate=update.get(Calendar.MONTH);
				int dayUpdate=update.get(Calendar.DAY_OF_MONTH);

				// se la data di oggi è maggiore della data dell'ultimo aggiornamento
				// cioè se la lista progetti deve essere aggiornata
				if (yearNow>yearUpdate || monthNow>monthUpdate || dayNow>dayUpdate) {
					try {
						retrofit2.Response<List<Project>> response=webservice.getProjectsOfLab("LabGIS").execute();
						List<Project> fromNetwork=response.body();

						projectDao.deleteAll();
						projectDao.insertProjects(fromNetwork);

						cacheDao.update(new Cache("progetti", now.getTime()));
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}

	public void updateUser(User user) {
		new updateUserAsyncTask(userDao).execute(user);
	}

	private static class updateUserAsyncTask extends AsyncTask<User, Void, Void> {
		private UserDao mAsyncUserTaskDao;

		updateUserAsyncTask(UserDao dao) {
			mAsyncUserTaskDao=dao;
		}

		@Override
		protected Void doInBackground(final User... params) {
			mAsyncUserTaskDao.update(params[0]);
			return null;
		}
	}

	public void deleteUser(User user) {
		new deleteUserAsyncTask(userDao).execute(user);

		webservice.deleteUser(user).enqueue(new Callback<WebResponse>() {
			@Override
			public void onResponse(Call<WebResponse> call, Response<WebResponse> response) {
			}

			@Override
			public void onFailure(Call<WebResponse> call, Throwable t) {
			}
		});
	}

	private static class deleteUserAsyncTask extends AsyncTask<User, Void, Void> {
		private UserDao mAsyncUserTaskDao;

		deleteUserAsyncTask(UserDao dao) {
			mAsyncUserTaskDao=dao;
		}

		@Override
		protected Void doInBackground(User... users) {
			mAsyncUserTaskDao.delete(users[0]);
			return null;
		}
	}

	public void deleteAlert(Alert alert) {
		new deleteAlertAsyncTask(alertDao).execute(alert);

		webservice.deleteAlert(alert).enqueue(new Callback<WebResponse>() {
			@Override
			public void onResponse(Call<WebResponse> call, Response<WebResponse> response) {
			}

			@Override
			public void onFailure(Call<WebResponse> call, Throwable t) {
			}
		});
	}

	private static class deleteAlertAsyncTask extends AsyncTask<Alert, Void, Void> {
		private AlertDao mAsyncAlertTaskDao;

		deleteAlertAsyncTask(AlertDao dao) {
			mAsyncAlertTaskDao=dao;
		}

		@Override
		protected Void doInBackground(Alert... alerts) {
			mAsyncAlertTaskDao.delete(alerts[0]);
			return null;
		}
	}

	public void updateAlert(Alert alert) {
		new updateAlertAsyncTask(alertDao).execute(alert);

		webservice.updateAlert(alert).enqueue(new Callback<WebResponse>() {
			@Override
			public void onResponse(Call<WebResponse> call, Response<WebResponse> response) {
			}

			@Override
			public void onFailure(Call<WebResponse> call, Throwable t) {
			}
		});
	}

	private static class updateAlertAsyncTask extends AsyncTask<Alert, Void, Void> {
		private AlertDao mAsyncAlertTaskDao;

		updateAlertAsyncTask(AlertDao dao) {
			mAsyncAlertTaskDao=dao;
		}

		@Override
		protected Void doInBackground(Alert... alerts) {
			mAsyncAlertTaskDao.update(alerts[0]);
			return null;
		}
	}
}
