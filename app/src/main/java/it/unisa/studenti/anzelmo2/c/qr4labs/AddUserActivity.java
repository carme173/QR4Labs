package it.unisa.studenti.anzelmo2.c.qr4labs;

import android.support.constraint.Group;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;

import it.unisa.studenti.anzelmo2.c.qr4labs.ui.adduser.AddUserFragment;

public class AddUserActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_user_activity);
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					 .replace(R.id.container, AddUserFragment.newInstance())
					 .commitNow();
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
				return false;

			default:
				return super.onOptionsItemSelected(item);
		}
	}

	public void onRadioButtonClicked(View view) {
		Group managerGroup=(Group) findViewById(R.id.manager_group);

		boolean checked=((RadioButton) view).isChecked();

		switch (view.getId()) {
			case R.id.student:
				if (checked) {
					managerGroup.setVisibility(View.GONE);
				}
				break;

			case R.id.member:
				if (checked) {
					managerGroup.setVisibility(View.GONE);
				}
				break;

			case R.id.manager:
				if (checked) {
					managerGroup.setVisibility(View.VISIBLE);
				}
				break;
		}
	}
}
