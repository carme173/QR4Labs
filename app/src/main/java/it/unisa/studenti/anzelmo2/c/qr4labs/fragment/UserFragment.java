package it.unisa.studenti.anzelmo2.c.qr4labs.fragment;


import android.app.Activity;
import android.app.SearchManager;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.List;

import it.unisa.studenti.anzelmo2.c.qr4labs.AddUserActivity;
import it.unisa.studenti.anzelmo2.c.qr4labs.R;
import it.unisa.studenti.anzelmo2.c.qr4labs.database.UserViewModel;
import it.unisa.studenti.anzelmo2.c.qr4labs.database.user.User;

public class UserFragment extends Fragment {

	private static UserViewModel mUserViewModel;
	private UserRecyclerViewAdapter mAdapter;

	public UserFragment() {
	}

	public static UserFragment newInstance(Activity activity, UserViewModel mUserViewModel) {
		UserFragment uf=new UserFragment();
		UserFragment.mUserViewModel=mUserViewModel;

		return uf;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
									 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_user, container, false);

		final Context context=view.getContext();
		final RecyclerView recyclerView=view.findViewById(R.id.list);
		LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context);
		recyclerView.setLayoutManager(linearLayoutManager);
		recyclerView.requestFocus();

		final ProgressBar progressBar=view.findViewById(R.id.progress_bar);

		SharedPreferences sharedPreferences=getActivity().getPreferences(Context.MODE_PRIVATE);
		final String lid=sharedPreferences.getString("lid", "0");

		FloatingActionButton fab=(FloatingActionButton) view.findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(context, AddUserActivity.class);
				intent.putExtra("lid", lid);
				startActivity(intent);
			}
		});

		SharedPreferences sharedPref=getActivity().getSharedPreferences(
				 "it.unisa.studenti.anzelmo2.c.qr4labs.USER_FILE_KEY", Context.MODE_PRIVATE);
		if (sharedPref.contains("username"))
			fab.setVisibility(View.VISIBLE);
		else
			fab.setVisibility(View.GONE);

		((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(lid);

		mAdapter=new UserRecyclerViewAdapter(this.getContext());
		recyclerView.setAdapter(mAdapter);

		DividerItemDecoration mDividerItemDecoration=new DividerItemDecoration(recyclerView.getContext(),
				 linearLayoutManager.getOrientation());
		recyclerView.addItemDecoration(mDividerItemDecoration);

		mUserViewModel= ViewModelProviders.of(this).get(UserViewModel.class);
		mUserViewModel.getUsersOfLab(lid).observe(this, new Observer<List<User>>() {
			@Override
			public void onChanged(@Nullable final List<User> users) {
				mAdapter.setUsers(users);

				progressBar.setVisibility(View.GONE);
				recyclerView.setVisibility(View.VISIBLE);
				}
		});

		SearchManager searchManager=(SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
		SearchView searchView=(SearchView) view.findViewById(R.id.search_view);
		searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
		searchView.setIconifiedByDefault(false);

		searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
			@Override
			public boolean onQueryTextSubmit(String query) {
				return true;
			}

			@Override
			public boolean onQueryTextChange(String newText) {
				mAdapter.getFilter().filter(newText);

				return true;
			}
		});

		searchView.setQueryHint("Cerca utenti o ruolo");

		return view;
	}
}
