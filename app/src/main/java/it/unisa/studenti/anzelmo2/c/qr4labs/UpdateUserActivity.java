package it.unisa.studenti.anzelmo2.c.qr4labs;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import it.unisa.studenti.anzelmo2.c.qr4labs.database.UserViewModel;
import it.unisa.studenti.anzelmo2.c.qr4labs.database.user.User;

public class UpdateUserActivity extends AppCompatActivity {

	private UserViewModel mUserViewModel;
	private EditText firstName;
	private EditText lastName;
	private EditText email;
	private RadioButton student;
	private RadioButton member;
	private RadioButton manager;
	private String lid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_user_fragment);

		firstName=(EditText) findViewById(R.id.user_firstname);
		lastName=(EditText) findViewById(R.id.user_lastname);
		email=(EditText) findViewById(R.id.user_email);
		student=(RadioButton) findViewById(R.id.student);
		member=(RadioButton) findViewById(R.id.member);
		manager=(RadioButton) findViewById(R.id.manager);

		final String uid=getIntent().getStringExtra("uid");

		mUserViewModel= ViewModelProviders.of(this).get(UserViewModel.class);
		mUserViewModel.getUser(getIntent().getStringExtra("uid")).observe(this, new Observer<User>() {
			@Override
			public void onChanged(@Nullable User user) {
				firstName.setText(user.getFirstName(), TextView.BufferType.EDITABLE);
				lastName.setText(user.getLastName(), TextView.BufferType.EDITABLE);
				email.setText(user.getEmail(), TextView.BufferType.EDITABLE);

				String role=user.getRole();

				if (role.compareTo((student.getText()).toString())==0)
					student.setChecked(true);
				else if (role.compareTo((member.getText()).toString())==0)
					member.setChecked(true);
				else if (role.compareTo((manager.getText()).toString())==0) {
					manager.setChecked(true);
					Group managerGroup=(Group) findViewById(R.id.manager_group);
					managerGroup.setVisibility(View.VISIBLE);
				}

				lid=user.getLid();
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
				updateUser();
				return true;

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

	private void updateUser() {
		String fName=firstName.getText().toString();
		String lName=lastName.getText().toString();
		String em=email.getText().toString();
		String role=null;
		String password=null;

		boolean checkedStudent=student.isChecked();
		boolean checkedMember=member.isChecked();
		boolean checkedManager=manager.isChecked();

		if (checkedStudent)
			role=student.getText().toString();
		else if (checkedMember)
			role=member.getText().toString();
		else if (checkedManager) {
			role=manager.getText().toString();
			password=((EditText) findViewById(R.id.user_password)).getText().toString();
		}

		String username=((fName.toLowerCase()).substring(0, 1)+"."+(lName.toLowerCase()));
		User updatedUser=new User(username, password, fName, lName, em, lid, role);

		mUserViewModel.updateUser(updatedUser);

		finish();
	}
}
