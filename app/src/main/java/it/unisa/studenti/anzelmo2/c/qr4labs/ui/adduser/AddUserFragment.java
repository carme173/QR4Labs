package it.unisa.studenti.anzelmo2.c.qr4labs.ui.adduser;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;

import it.unisa.studenti.anzelmo2.c.qr4labs.R;
import it.unisa.studenti.anzelmo2.c.qr4labs.database.user.User;

public class AddUserFragment extends Fragment {

	private AddUserViewModel mViewModel;

	public static AddUserFragment newInstance() {
		return new AddUserFragment();
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
									 @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.add_user_fragment, container, false);
		setHasOptionsMenu(true);

		Group managerGroup=(Group) view.findViewById(R.id.manager_group);
		managerGroup.setVisibility(View.GONE);

		return view;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mViewModel = ViewModelProviders.of(this).get(AddUserViewModel.class);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.add:
				saveUser();
				return true;

			default:
				return super.onOptionsItemSelected(item);
		}
	}

	private void saveUser() {
		View view=getView();

		String firstname=((EditText) view.findViewById(R.id.user_firstname)).getText().toString();
		String lastname=((EditText) view.findViewById(R.id.user_lastname)).getText().toString();
		String email=((EditText) view.findViewById(R.id.user_email)).getText().toString();
		String role=null;
		String password=null;

		RadioButton student=(RadioButton) view.findViewById(R.id.student);
		RadioButton member=(RadioButton) view.findViewById(R.id.member);
		RadioButton manager=(RadioButton) view.findViewById(R.id.manager);

		boolean checkedStudent=student.isChecked();
		boolean checkedMember=member.isChecked();
		boolean checkedManager=manager.isChecked();

		if (checkedStudent)
			role=student.getText().toString();
		else if (checkedMember)
			role=member.getText().toString();
		else if (checkedManager) {
			role=manager.getText().toString();
			password=((EditText) view.findViewById(R.id.user_password)).getText().toString();
		}

		Bundle bundle=getActivity().getIntent().getExtras();
		String lid=bundle.getString("lid", "0");

		String username=((firstname.toLowerCase()).substring(0, 1)+"."+(lastname.toLowerCase()));
		User newUser=new User(username, password, firstname, lastname, email, lid, role);

		mViewModel.insertUser(newUser);

		getActivity().finish();
	}
}
