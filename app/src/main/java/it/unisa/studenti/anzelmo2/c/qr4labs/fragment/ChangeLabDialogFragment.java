package it.unisa.studenti.anzelmo2.c.qr4labs.fragment;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;

import it.unisa.studenti.anzelmo2.c.qr4labs.MainActivity;

public class ChangeLabDialogFragment extends DialogFragment {


	public ChangeLabDialogFragment() {
	}

	@NonNull
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		final String[] array={"LabGIS", "LabIUM"};

		AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
		builder.setTitle("Scegli laboratorio")
				 .setItems(array, new DialogInterface.OnClickListener() {
					 @Override
					 public void onClick(DialogInterface dialog, int which) {

						 SharedPreferences sharedPreferences=getActivity().getPreferences(Context.MODE_PRIVATE);
						 SharedPreferences.Editor editor=sharedPreferences.edit();
						 editor.clear();
						 editor.putString("lid", array[which]);
						 editor.apply();

						 ((MainActivity) getActivity()).showAlertOfLab(array[which]);
					 }
				 });

		return builder.create();
	}
}
