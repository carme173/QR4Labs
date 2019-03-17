package it.unisa.studenti.anzelmo2.c.qr4labs.fragment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import it.unisa.studenti.anzelmo2.c.qr4labs.R;
import it.unisa.studenti.anzelmo2.c.qr4labs.database.project.Project;

import java.util.List;

public class ProjectRecyclerViewAdapter extends RecyclerView.Adapter<ProjectRecyclerViewAdapter.ViewHolder> {

	private List<Project> mValues;

	public ProjectRecyclerViewAdapter() {
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext())
				 .inflate(R.layout.recycler_view_tmp, parent, false);
		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(final ViewHolder holder, int position) {
		holder.mItem = mValues.get(position);
		holder.mIdView.setText(mValues.get(position).getName());
		holder.mContentView.setText(mValues.get(position).getDescription());
	}

	@Override
	public int getItemCount() {
		if (mValues!=null)
			return mValues.size();

		else
			return 0;

	}

	public void setValues(List<Project> projects) {
		mValues=projects;
		notifyDataSetChanged();
	}

	public class ViewHolder extends RecyclerView.ViewHolder {
		public final View mView;
		public final TextView mIdView;
		public final TextView mContentView;
		public Project mItem;

		public ViewHolder(View view) {
			super(view);
			mView = view;
			mIdView = (TextView) view.findViewById(R.id.textView);
			mContentView = (TextView) view.findViewById(R.id.textView2);
		}

		@Override
		public String toString() {
			return super.toString() + " '" + mContentView.getText() + "'";
		}
	}
}
