package it.unisa.studenti.anzelmo2.c.qr4labs.database;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import it.unisa.studenti.anzelmo2.c.qr4labs.database.user.User;
import it.unisa.studenti.anzelmo2.c.qr4labs.database.user.UserDao;
import it.unisa.studenti.anzelmo2.c.qr4labs.network.Webservice;
import it.unisa.studenti.anzelmo2.c.qr4labs.utility.ServiceLocator;
import it.unisa.studenti.anzelmo2.c.qr4labs.utility.WebResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManagerRepository {

	private final Webservice webservice;
	private UserRepository userRepository;
	private UserDao userDao;

	public ManagerRepository(Application app) {
		userRepository=new UserRepository(app);
		AppDatabase appDatabase=AppDatabase.getDatabase(app);
		userDao=appDatabase.utenteDao();
		webservice=ServiceLocator.getWebservice();
	}

	public void insertUser(User user) {
		webservice.addUser(user).enqueue(new Callback<WebResponse>() {
			@Override
			public void onResponse(Call<WebResponse> call, Response<WebResponse> response) {
			}

			@Override
			public void onFailure(Call<WebResponse> call, Throwable t) {
			}
		});

		new insertUserAsyncTask(userDao).execute(user);
	}

	private static class insertUserAsyncTask extends AsyncTask<User, Void, Void> {
		private UserDao userDao;

		insertUserAsyncTask(UserDao userDao) {
			this.userDao=userDao;
		}

		@Override
		protected Void doInBackground(User... users) {
			userDao.insertUser(users[0]);
			return null;
		}
	}
}
