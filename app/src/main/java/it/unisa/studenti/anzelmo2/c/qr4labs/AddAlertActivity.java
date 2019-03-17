package it.unisa.studenti.anzelmo2.c.qr4labs;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

import it.unisa.studenti.anzelmo2.c.qr4labs.database.AlertViewModel;
import it.unisa.studenti.anzelmo2.c.qr4labs.database.alert.Alert;

public class AddAlertActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_alert);
	}

	public void showDatePickerDialog(View v) {
		DialogFragment newFragment=new AddAlertActivity.DatePickerFragment();
		Bundle args=new Bundle();
		args.putInt("et_id", v.getId());
		newFragment.setArguments(args);
		newFragment.show(getSupportFragmentManager(), "datePicker");
	}

	public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

		@NonNull
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			final Calendar c=Calendar.getInstance();
			int year=c.get(Calendar.YEAR);
			int month=c.get(Calendar.MONTH);
			int day=c.get(Calendar.DAY_OF_MONTH);

			return new DatePickerDialog(getActivity(), this, year, month, day);
		}

		@Override
		public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
			Bundle args=getArguments();
			EditText et=(EditText) getActivity().findViewById(args.getInt("et_id"));
			month++;
			String text=dayOfMonth+"/"+month+"/"+year;
			et.setText(text);
		}
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
		AlertViewModel mAlertViewModel= ViewModelProviders.of(this).get(AlertViewModel.class);
		String title=((EditText) findViewById(R.id.alert_title)).getText().toString();
		String text=((EditText) findViewById(R.id.alert_text)).getText().toString();
		String pub_date=((EditText) findViewById(R.id.date_from)).getText().toString();
		String exp_date=((EditText) findViewById(R.id.date_to)).getText().toString();

		Bundle bundle=getIntent().getExtras();
		String lid=bundle.getString("lid", "0");

		SharedPreferences sharedPref=getApplicationContext().getSharedPreferences(
				 "it.unisa.studenti.anzelmo2.c.qr4labs.USER_FILE_KEY", Context.MODE_PRIVATE);
		String username=sharedPref.getString("username", "admin");

		Calendar cal=Calendar.getInstance();
		cal.set(Calendar.YEAR, 2018);
		cal.set(Calendar.MONTH, Calendar.DECEMBER);
		cal.set(Calendar.DAY_OF_MONTH, 3);

		if (!lid.equals("0")) {
			Alert newAlert = new Alert(title, text, pub_date, null, exp_date, null, username, lid);
			mAlertViewModel.insertAlert(newAlert);
		}

		finish();
	}
}
