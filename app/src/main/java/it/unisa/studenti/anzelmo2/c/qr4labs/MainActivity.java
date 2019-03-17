package it.unisa.studenti.anzelmo2.c.qr4labs;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import it.unisa.studenti.anzelmo2.c.qr4labs.database.UserViewModel;
import it.unisa.studenti.anzelmo2.c.qr4labs.fragment.AlertFragment;
import it.unisa.studenti.anzelmo2.c.qr4labs.fragment.ChangeLabDialogFragment;
import it.unisa.studenti.anzelmo2.c.qr4labs.fragment.InfoFragment;
import it.unisa.studenti.anzelmo2.c.qr4labs.fragment.ProjectFragment;
import it.unisa.studenti.anzelmo2.c.qr4labs.fragment.ScanQrDialogFragment;
import it.unisa.studenti.anzelmo2.c.qr4labs.fragment.UserFragment;

public class MainActivity extends AppCompatActivity {

	private UserViewModel mUserViewModel;
	private String lid;
	private DrawerLayout drawerLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Navigation Drawer: inizio
		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		ActionBar actionbar = getSupportActionBar();
		actionbar.setDisplayHomeAsUpEnabled(true);
		actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

		toolbar.setTitleTextColor(Color.WHITE);
		toolbar.getOverflowIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);

		drawerLayout=findViewById(R.id.drawer_layout);

		NavigationView navigationView=findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
			@Override
			public boolean onNavigationItemSelected(@NonNull MenuItem item) {
				item.setChecked(true);
				drawerLayout.closeDrawers();

				switch (item.getItemId()) {
					case R.id.alerts:
						setAlertView();
						return true;

					case R.id.users:
						setUserView("user");
						return true;

					case R.id.timetables:
						return true;

					case R.id.projects:
						setProjectView();
						return true;

					case R.id.info:
						setOtherView();
						return true;
				}
				return true;
			}
		});
		// Navigation Drawer: fine

		Intent intent=getIntent();

		String action=intent.getAction();

		if (action.equals("android.intent.action.VIEW")) {
			lid=intent.getData().getQueryParameter("name");
			Snackbar.make(findViewById(R.id.coordinator), lid+" trovato", Snackbar.LENGTH_LONG).show();
			updateSharedPreference(lid);
		}

		SharedPreferences sharedPreferences=getPreferences(Context.MODE_PRIVATE);

		if (action.equals("android.intent.action.MAIN") && !sharedPreferences.contains("lid")) {
			DialogFragment newFragment=new ScanQrDialogFragment();
			newFragment.show(getSupportFragmentManager(), "Scan QR");
		}

		if (sharedPreferences.contains("lid")) {
			lid=sharedPreferences.getString("lid", "0");
			UserViewModel userViewModel= ViewModelProviders.of(this).get(UserViewModel.class);
			userViewModel.getUsersOfLab(lid, true);
			showAlertOfLab(lid);
		}

		SharedPreferences sharedPref=getSharedPreferences(
				 "it.unisa.studenti.anzelmo2.c.qr4labs.USER_FILE_KEY", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor=sharedPref.edit();
		editor.clear();
		editor.apply();

	}

	public void cancelDialog() {
		updateSharedPreference(null);
	}

	private void updateSharedPreference(String lid) {
		SharedPreferences sharedPreferences=getPreferences(Context.MODE_PRIVATE);
		SharedPreferences.Editor editor=sharedPreferences.edit();
		editor.putString("lid", lid);
		editor.apply();
	}

	private void setUserView(String s) {
		UserFragment uf=UserFragment.newInstance(this, mUserViewModel);
		getSupportFragmentManager().beginTransaction().replace(R.id.fragment_holder, uf).commit();

		NavigationView navigationView=findViewById(R.id.nav_view);
		navigationView.setCheckedItem(R.id.users);
	}

	private void setAlertView() {
		AlertFragment info=AlertFragment.newInstance(lid);
		getSupportFragmentManager().beginTransaction().replace(R.id.fragment_holder, info).commit();

		NavigationView navigationView=findViewById(R.id.nav_view);
		navigationView.setCheckedItem(R.id.alerts);
	}

	public void showAlertOfLab(String lid) {
		NavigationView navigationView=findViewById(R.id.nav_view);
		navigationView.setCheckedItem(R.id.alerts);

		this.lid=lid;

		AlertFragment info=AlertFragment.newInstance(lid);
		getSupportFragmentManager().beginTransaction().replace(R.id.fragment_holder, info).commit();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);

		if ((requestCode==1227) && (resultCode==Activity.RESULT_OK)) {

			invalidateOptionsMenu();
			setAlertView();

			Snackbar.make(findViewById(R.id.coordinator_alert), "Accesso effettuato", Snackbar.LENGTH_LONG).show();

			return;
		}

		IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);

		if (scanResult != null) {

			String scanString=scanResult.getContents();
			String resultString=scanString.substring(17);

			Snackbar.make(findViewById(R.id.coordinator), resultString+" trovato", Snackbar.LENGTH_LONG).show();

			updateSharedPreference(resultString);
			showAlertOfLab(resultString);

			return;
		}
	}

	private void setOtherView() {
		InfoFragment info=InfoFragment.newInstance();
		getSupportFragmentManager().beginTransaction().replace(R.id.fragment_holder, info).commit();
	}

	private void setProjectView() {
		ProjectFragment pf= ProjectFragment.newInstance();
		getSupportFragmentManager().beginTransaction().replace(R.id.fragment_holder, pf).commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.options, menu);

		SharedPreferences sharedPref=getSharedPreferences(
				 "it.unisa.studenti.anzelmo2.c.qr4labs.USER_FILE_KEY", Context.MODE_PRIVATE);

		if (sharedPref.getBoolean("logged", false)) {
			(menu.findItem(R.id.login)).setVisible(false);
			(menu.findItem(R.id.logout)).setVisible(true);
		}

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
			case R.id.change_lab:
				changeLab();
				return true;

			case R.id.scan_qr:
				startScanQR();
				return true;

			case R.id.login:
				startLogin();
				return true;

			case R.id.logout:
				logout();
				return true;

			case android.R.id.home:
				drawerLayout.openDrawer(GravityCompat.START);
				return true;

			default:
				return super.onOptionsItemSelected(item);
		}
	}

	private void changeLab() {
		DialogFragment changeLabDialog=new ChangeLabDialogFragment();
		changeLabDialog.show(getSupportFragmentManager(), "chageLab");
	}

	private void startScanQR() {
		IntentIntegrator integrator = new IntentIntegrator(this);
		integrator.initiateScan();
	}

	private void startLogin() {
		Intent intent=new Intent(this, LoginActivity.class);
		startActivityForResult(intent, 1227);
	}

	private void logout() {
		SharedPreferences sharedPref=getSharedPreferences(
				 "it.unisa.studenti.anzelmo2.c.qr4labs.USER_FILE_KEY", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor=sharedPref.edit();
		editor.clear();
		editor.apply();

		setAlertView();
		invalidateOptionsMenu();
	}
}
