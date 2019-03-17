package it.unisa.studenti.anzelmo2.c.qr4labs.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import it.unisa.studenti.anzelmo2.c.qr4labs.DeleteUserActivity;
import it.unisa.studenti.anzelmo2.c.qr4labs.R;
import it.unisa.studenti.anzelmo2.c.qr4labs.UpdateUserActivity;
import it.unisa.studenti.anzelmo2.c.qr4labs.database.user.User;

public class UserRecyclerViewAdapter extends RecyclerView.Adapter<UserRecyclerViewAdapter.ViewHolder>
		 implements Filterable {

	private List<User> mValues;
	private List<User> originalValues;
	private Context context;
	private boolean logged;

	public UserRecyclerViewAdapter() {
	}

	UserRecyclerViewAdapter(final Context context) {
		this.context=context;

		SharedPreferences sharedPref=context.getSharedPreferences(
				 "it.unisa.studenti.anzelmo2.c.qr4labs.USER_FILE_KEY", Context.MODE_PRIVATE);

		if (sharedPref.contains("logged"))
			logged=sharedPref.getBoolean("logged", false);

	}

	@NonNull
	@Override
	public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext())
				 .inflate(R.layout.fragment_user_card, parent, false);
		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(final ViewHolder holder, int position) {
		holder.mItem = mValues.get(position);
		holder.mNameView.setText(mValues.get(position).getFullName());
		holder.mContentView.setText(mValues.get(position).getRole());
		holder.mEmailView.setText(mValues.get(position).getEmail());

		if (logged) {
			final int i=holder.getAdapterPosition();
			holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
				@Override
				public boolean onLongClick(View v) {
					PopupMenu popup=new PopupMenu(context, holder.mContentView);
					popup.inflate(R.menu.update_delete);
					popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
						@Override
						public boolean onMenuItemClick(MenuItem item) {
							switch (item.getItemId()) {
								case R.id.update:
									Intent intent=new Intent(context, UpdateUserActivity.class);
									intent.putExtra("uid", mValues.get(i).getUid());
									context.startActivity(intent);
									return true;

								case R.id.delete:
									String uid=mValues.get(i).getUid();
									deleteUser(i);
									Intent intentDelete=new Intent(context, DeleteUserActivity.class);
									intentDelete.putExtra("uid", uid);
									context.startActivity(intentDelete);
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
	}

	@Override
	public int getItemCount() {
		if (mValues!=null)
			return mValues.size();

		else
			return 0;
	}

	@Override
	public Filter getFilter() {
		return new Filter() {
			@Override
			protected FilterResults performFiltering(CharSequence constraint) {
				FilterResults filterResults=new FilterResults();
				List<User> filteredUserList=new ArrayList<User>();

				if (constraint==null || constraint.length()==0) {
					filterResults.values=originalValues;
					filterResults.count=originalValues.size();

					mValues=originalValues;
				}

				else {
					for (User u:originalValues) {
						if (u.getUserDetails().toLowerCase().contains(constraint.toString().toLowerCase()))
							filteredUserList.add(u);
					}

					mValues=filteredUserList;

					filterResults.values=filteredUserList;
					filterResults.count=filteredUserList.size();
				}

				return filterResults;
			}

			@Override
			protected void publishResults(CharSequence constraint, FilterResults results) {
				mValues=(List<User>) results.values;
				notifyDataSetChanged();
			}
		};
	}

	public class ViewHolder extends RecyclerView.ViewHolder {
		final View mView;
		final TextView mNameView;
		final TextView mContentView;
		final TextView mEmailView;
		User mItem;

		ViewHolder(View view) {
			super(view);
			mView = view;
			mNameView = (TextView) view.findViewById(R.id.name);
			mContentView = (TextView) view.findViewById(R.id.role);
			mEmailView = (TextView) view.findViewById(R.id.email);
		}

		@Override
		public String toString() {
			return super.toString() + " '" + mContentView.getText() + "'";
		}
	}

	void setUsers(List<User> users) {
		mValues=originalValues=users;
		notifyDataSetChanged();
	}

	private void deleteUser(int i) {
		mValues.remove(i);
		notifyItemRemoved(i);
	}
}
