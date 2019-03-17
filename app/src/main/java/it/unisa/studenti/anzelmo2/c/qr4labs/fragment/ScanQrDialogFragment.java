package it.unisa.studenti.anzelmo2.c.qr4labs.fragment;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;

import com.google.zxing.integration.android.IntentIntegrator;

import it.unisa.studenti.anzelmo2.c.qr4labs.MainActivity;

public class ScanQrDialogFragment extends DialogFragment {


	public ScanQrDialogFragment() {
	}

	@NonNull
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		super.onCreateDialog(savedInstanceState);

		AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
		builder.setMessage("Avviare scansione QR?")
				 .setPositiveButton("Avvia", new DialogInterface.OnClickListener() {
					 @Override
					 public void onClick(DialogInterface dialog, int which) {
						 IntentIntegrator integrator = new IntentIntegrator(getActivity());
						 integrator.initiateScan();
					 }
				 })
				 .setNegativeButton("Annulla", new DialogInterface.OnClickListener() {
					 @Override
					 public void onClick(DialogInterface dialog, int which) {

					 }
				 });

		return builder.create();
	}

	@Override
	public void onCancel(DialogInterface dialog) {
		super.onCancel(dialog);

		((MainActivity) getActivity()).cancelDialog();
	}
}
