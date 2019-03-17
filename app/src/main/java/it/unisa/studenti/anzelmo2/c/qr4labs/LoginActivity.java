package it.unisa.studenti.anzelmo2.c.qr4labs;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import it.unisa.studenti.anzelmo2.c.qr4labs.database.UserViewModel;
import it.unisa.studenti.anzelmo2.c.qr4labs.database.user.User;

public class LoginActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		getSupportActionBar().setTitle("Accedi");
	}

	public void login(View v) {
		EditText usernameEdit=findViewById(R.id.username);
		EditText passwordEdit=findViewById(R.id.password);

		String username=usernameEdit.getText().toString();
		String password=passwordEdit.getText().toString();

		checkUser(username, password);
	}

	private void checkUser(final String username, final String password) {
		final Context context=getApplicationContext();

		UserViewModel mUserViewModel= ViewModelProviders.of(this).get(UserViewModel.class);
		mUserViewModel.getUser(username).observe(this, new Observer<User>() {
			@Override
			public void onChanged(@Nullable User user) {
				if (user!=null && (user.getRole().compareTo("Responsabile")==0)) {
					Intent intent=getIntent();

					if (password.equals(user.getPassword())) {
						SharedPreferences sharedPref=context.getSharedPreferences(
								 "it.unisa.studenti.anzelmo2.c.qr4labs.USER_FILE_KEY", Context.MODE_PRIVATE);
						SharedPreferences.Editor editor=sharedPref.edit();
						editor.putString("username", username);
						editor.putBoolean("logged", true);
						editor.apply();

						intent.putExtra("username", username);
						intent.putExtra("logged", true);
						setResult(Activity.RESULT_OK, intent);
						finish();
					}

					else {
						setResult(Activity.RESULT_CANCELED);

						Snackbar.make(findViewById(R.id.coordinator), "Nome utente/Password errata",
								 Snackbar.LENGTH_LONG).setAction("Ok", null).show();
					}

				}
				Snackbar.make(findViewById(R.id.coordinator), "Accesso negato",
						 Snackbar.LENGTH_LONG).setAction("Ok", null).show();
			}
		});

	}
}
