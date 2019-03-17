package it.unisa.studenti.anzelmo2.c.qr4labs;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import it.unisa.studenti.anzelmo2.c.qr4labs.database.UserViewModel;
import it.unisa.studenti.anzelmo2.c.qr4labs.database.user.User;

public class DeleteUserActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		String uid=getIntent().getStringExtra("uid");

		final UserViewModel mUserViewModel= ViewModelProviders.of(this).get(UserViewModel.class);
		mUserViewModel.getUser(uid).observe(this, new Observer<User>() {
			@Override
			public void onChanged(@Nullable User user) {
				if (user!=null) {
					mUserViewModel.deleteUser(user);
					finish();
				}
			}
		});
	}
}
