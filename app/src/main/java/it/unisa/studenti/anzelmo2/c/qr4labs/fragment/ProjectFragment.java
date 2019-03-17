package it.unisa.studenti.anzelmo2.c.qr4labs.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.unisa.studenti.anzelmo2.c.qr4labs.R;
import it.unisa.studenti.anzelmo2.c.qr4labs.database.ProjectViewModel;
import it.unisa.studenti.anzelmo2.c.qr4labs.database.project.Project;

import java.util.List;


public class ProjectFragment extends Fragment {

	private int mColumnCount = 1;

	public ProjectFragment() {
	}

	public static ProjectFragment newInstance() {
		ProjectFragment fragment=new ProjectFragment();

		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
									 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_project_list, container, false);

		// Set the adapter
		if (view instanceof RecyclerView) {
			Context context = view.getContext();
			RecyclerView recyclerView = (RecyclerView) view;
			if (mColumnCount <= 1) {
				recyclerView.setLayoutManager(new LinearLayoutManager(context));
			} else {
				recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
			}

			SharedPreferences sharedPreferences=getActivity().getPreferences(Context.MODE_PRIVATE);
			String lid=sharedPreferences.getString("lid", "0");

			((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(lid);

			final ProjectRecyclerViewAdapter adapter=new ProjectRecyclerViewAdapter();
			recyclerView.setAdapter(adapter);

			ProjectViewModel mProjectViewModel= ViewModelProviders.of(this).get(ProjectViewModel.class);
			mProjectViewModel.getProjectsOfLab(lid).observe(this, new Observer<List<Project>>() {
				@Override
				public void onChanged(@Nullable List<Project> projects) {
					adapter.setValues(projects);
				}
			});
		}
		return view;
	}
}
