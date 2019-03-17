package it.unisa.studenti.anzelmo2.c.qr4labs.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import it.unisa.studenti.anzelmo2.c.qr4labs.R;

public class InfoFragment extends Fragment {

	public InfoFragment() {
	}

	public static InfoFragment newInstance() {
		InfoFragment fragment = new InfoFragment();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			String lid = getArguments().getString("lid");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
									 Bundle savedInstanceState) {
		SharedPreferences sharedPreferences=getActivity().getPreferences(Context.MODE_PRIVATE);
		String lid=sharedPreferences.getString("lid", "0");

		View view= inflater.inflate(R.layout.fragment_info, container, false);

		((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(lid);

		WebView mWebView = (WebView) view.findViewById(R.id.info_lab);
		mWebView.setWebViewClient(new WebViewClient());

		if (lid.equals("LabGIS")) {
			mWebView.loadUrl("http://www.di.unisa.it/dipartimento/strutture?id=135");
		} else if (lid.equals("LabIUM")) {
			mWebView.loadUrl("http://www.di.unisa.it/dipartimento/strutture?id=143");
		}

		return view;
	}
}
