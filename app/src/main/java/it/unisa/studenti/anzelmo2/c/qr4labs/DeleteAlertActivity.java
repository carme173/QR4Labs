package it.unisa.studenti.anzelmo2.c.qr4labs;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import it.unisa.studenti.anzelmo2.c.qr4labs.database.AlertViewModel;
import it.unisa.studenti.anzelmo2.c.qr4labs.database.alert.Alert;

public class DeleteAlertActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		int aid=getIntent().getIntExtra("aid", 0);

		final AlertViewModel mAlertViewModel= ViewModelProviders.of(this).get(AlertViewModel.class);
		mAlertViewModel.getAlert(aid).observe(this, new Observer<Alert>() {
			@Override
			public void onChanged(@Nullable Alert alert) {
				mAlertViewModel.deleteAlert(alert);
			}
		});


		finish();
	}
}
