package it.unisa.studenti.anzelmo2.c.qr4labs;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import it.unisa.studenti.anzelmo2.c.qr4labs.database.AlertViewModel;
import it.unisa.studenti.anzelmo2.c.qr4labs.database.alert.Alert;

public class AlertDetailActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alert_detail);

		AlertViewModel mAlertViewModel = ViewModelProviders.of(this).get(AlertViewModel.class);
		mAlertViewModel.getAlert(getIntent().getIntExtra("aid", 0)).observe(this, new Observer<Alert>() {
			@Override
			public void onChanged(@Nullable Alert alert) {
				TextView alertDetail=(TextView) findViewById(R.id.alert_detail_textview);
				alertDetail.setText(alert.getText());

				CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
				if (appBarLayout != null) {
					appBarLayout.setTitle(alert.getTitle());
				}
			}
		});
	}
}
