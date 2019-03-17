package it.unisa.studenti.anzelmo2.c.qr4labs.fragment;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import it.unisa.studenti.anzelmo2.c.qr4labs.AddAlertActivity;
import it.unisa.studenti.anzelmo2.c.qr4labs.AlertDetailActivity;
import it.unisa.studenti.anzelmo2.c.qr4labs.R;
import it.unisa.studenti.anzelmo2.c.qr4labs.UpdateAlertActivity;
import it.unisa.studenti.anzelmo2.c.qr4labs.database.AlertViewModel;
import it.unisa.studenti.anzelmo2.c.qr4labs.database.alert.Alert;

public class AlertFragment extends Fragment {
	static AlertViewModel mAlertViewModel;
	static boolean logged;

	public AlertFragment() {
	}

	public static AlertFragment newInstance(String lid) {
		AlertFragment fragment = new AlertFragment();
		Bundle args = new Bundle();
		args.putString("lid", lid);
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
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
									 Bundle savedInstanceState) {
		SharedPreferences sharedPreferences=getActivity().getPreferences(Context.MODE_PRIVATE);
		final String lid=sharedPreferences.getString("lid", "0");

		View view=inflater.inflate(R.layout.fragment_alert, container, false);
		((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(lid);

		FloatingActionButton fab = view.findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent=new Intent(getActivity().getApplicationContext(), AddAlertActivity.class);
				intent.putExtra("lid", lid);
				startActivity(intent);
			}
		});

		SharedPreferences sharedPref=getActivity().getSharedPreferences(
				 "it.unisa.studenti.anzelmo2.c.qr4labs.USER_FILE_KEY", Context.MODE_PRIVATE);

		if(sharedPref.contains("username"))
			fab.setVisibility(View.VISIBLE);
		else
			fab.setVisibility(View.GONE);

		if (sharedPref.contains("logged"))
			logged=sharedPref.getBoolean("logged", false);

		final ProgressBar progressBar=(ProgressBar) view.findViewById(R.id.progress_bar);

		Context context=view.getContext();
		final RecyclerView recyclerView=view.findViewById(R.id.alert_list);
		assert recyclerView!=null;
		//
		LinearLayoutManager layoutManager=new LinearLayoutManager(context);
		recyclerView.setLayoutManager(layoutManager);
		final AlertItemRecyclerViewAdapter mAdapter=new AlertItemRecyclerViewAdapter();
		recyclerView.setAdapter(mAdapter);

		DividerItemDecoration mDividerItemDecoration=new DividerItemDecoration(recyclerView.getContext(),
				 layoutManager.getOrientation());
		recyclerView.addItemDecoration(mDividerItemDecoration);

		mAlertViewModel= ViewModelProviders.of(this).get(AlertViewModel.class);
		mAlertViewModel.getAlertsOfLab(lid).observe(this, new Observer<List<Alert>>() {
			@Override
			public void onChanged(@Nullable List<Alert> alerts) {
				if (alerts!=null)
					Collections.sort(alerts, new Comparator<Alert>() {
						@Override
						public int compare(Alert a1, Alert a2) {
							return (-((a1.getExpirationDate()).compareTo(a2.getExpirationDate())));
						}
					});

				mAdapter.mValues=alerts;
				mAdapter.notifyDataSetChanged();
				progressBar.setVisibility(View.GONE);
				recyclerView.setVisibility(View.VISIBLE);
			}
		});
		return view;
	}

	public static class AlertItemRecyclerViewAdapter
			 extends RecyclerView.Adapter<AlertItemRecyclerViewAdapter.ViewHolder> {

		private List<Alert> mValues;
		private final View.OnClickListener mOnClickListener= new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Alert item=(Alert) view.getTag();
				Context context=view.getContext();
				Intent intent=new Intent(context, AlertDetailActivity.class);
				intent.putExtra("aid", item.getAid());
				context.startActivity(intent);
			}
		};

		public AlertItemRecyclerViewAdapter(List<Alert> mValues) {
			this.mValues = mValues;
		}

		public AlertItemRecyclerViewAdapter() {

		}

		@NonNull
		@Override
		public AlertItemRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
			View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_alert_list_content, parent, false);

			return new ViewHolder(view);
		}

		@Override
		public void onBindViewHolder(@NonNull final AlertItemRecyclerViewAdapter.ViewHolder holder, int position) {
			holder.mContentView.setText(mValues.get(position).getTitle());
			holder.mAuthor.setText(mValues.get(position).getAuthor());
			holder.mExpirationDate.setText(mValues.get(position).getExpirationString());

			if (logged) {
				final int i = holder.getAdapterPosition();
				holder.alert = mValues.get(i);
				holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
					@Override
					public boolean onLongClick(View v) {
						final Context context = v.getContext();
						PopupMenu popup = new PopupMenu(context, holder.mContentView);
						popup.inflate(R.menu.update_delete);
						popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
							@Override
							public boolean onMenuItemClick(MenuItem item) {
								switch (item.getItemId()) {
									case R.id.update:
										Intent intentUpdate = new Intent(context, UpdateAlertActivity.class);
										intentUpdate.putExtra("aid", mValues.get(i).getAid());
										context.startActivity(intentUpdate);
										return true;

									case R.id.delete:
										mAlertViewModel.deleteAlert(mValues.get(i));
										mValues.remove(i);
										notifyItemRemoved(i);
										return true;

									default:
										return false;
								}
							}
						});
						popup.show();

						return true;
					}
				});
			}

			if (mValues.get(position).isExpired()) {
				holder.mExpired.setVisibility(View.VISIBLE);
				holder.mIcon.setImageResource(R.drawable.ic_chat_bubble_outline_blue_24dp);
			}
			else {
				holder.mExpired.setVisibility(View.INVISIBLE);
				holder.mIcon.setImageResource(R.drawable.ic_chat_bubble_black_24dp);
			}

			holder.itemView.setTag(mValues.get(position));
			holder.itemView.setOnClickListener(mOnClickListener);
		}

		@Override
		public int getItemCount() {
			if (mValues!=null) {
				return mValues.size();
			}

			else
				return 0;
		}

		class ViewHolder extends RecyclerView.ViewHolder {
			final TextView mContentView;
			final TextView mAuthor;
			final TextView mExpirationDate;
			final TextView mExpired;
			final ImageView mIcon;
			Alert alert;

			ViewHolder(View view) {
				super(view);
				mContentView = (TextView) view.findViewById(R.id.content);
				mAuthor = (TextView) view.findViewById(R.id.author);
				mExpirationDate = (TextView) view.findViewById(R.id.date_to);
				mExpired = (TextView) view.findViewById(R.id.expired);
				mIcon = (ImageView) view.findViewById(R.id.alert_img);
			}
		}
	}
}
