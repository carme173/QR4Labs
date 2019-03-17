package it.unisa.studenti.anzelmo2.c.qr4labs;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import it.unisa.studenti.anzelmo2.c.qr4labs.database.AlertViewModel;
import it.unisa.studenti.anzelmo2.c.qr4labs.database.alert.Alert;

public class UpdateAlertActivity extends AppCompatActivity {
	private EditText title;
	private EditText text;
	private EditText pubDate;
	private EditText expDate;
	private AlertViewModel mAlertViewModel;
	private String lid;
	private static Alert alert;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_alert);

		title=(EditText) findViewById(R.id.alert_title);
		text=(EditText) findViewById(R.id.alert_text);
		pubDate=(EditText) findViewById(R.id.date_from);
		expDate=(EditText) findViewById(R.id.date_to);

		final int aid=getIntent().getIntExtra("aid", 0);

		mAlertViewModel= ViewModelProviders.of(this).get(AlertViewModel.class);
		mAlertViewModel.getAlert(aid).observe(this, new Observer<Alert>() {
			@Override
			public void onChanged(@Nullable Alert alert) {
				title.setText(alert.getTitle());
				text.setText(alert.getText());
				pubDate.setText(alert.getPublicationString());
				expDate.setText(alert.getExpirationString());

				lid=alert.getLab();
				UpdateAlertActivity.alert=alert;
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater=getMenuInflater();
		inflater.inflate(R.menu.add, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.add:
				saveAlert();
				return true;

			default:
				return super.onOptionsItemSelected(item);
		}
	}

	private void saveAlert() {
		String titleString=title.getText().toString();
		String textString=text.getText().toString();
		String pubDateString=pubDate.getText().toString();
		String expDateString=expDate.getText().toString();

		SharedPreferences sharedPref=getApplicationContext().getSharedPreferences(
				 "it.unisa.studenti.anzelmo2.c.qr4labs.USER_FILE_KEY", Context.MODE_PRIVATE);
		String username=sharedPref.getString("username", "admin");


		if (!lid.equals("0")) {
			alert.update(titleString, textString, pubDateString, expDateString, username);
//			Alert newAlert = new Alert(titleString, textString, pubDateString, null, expDateString, null, username, lid);
			mAlertViewModel.updateAlert(alert);
		}

		finish();
	}

	public void showDatePickerDialog(View v) {
		DialogFragment newFragment=new AddAlertActivity.DatePickerFragment();
		Bundle args=new Bundle();
		args.putInt("et_id", v.getId());
		newFragment.setArguments(args);
		newFragment.show(getSupportFragmentManager(), "datePicker");
	}
}
