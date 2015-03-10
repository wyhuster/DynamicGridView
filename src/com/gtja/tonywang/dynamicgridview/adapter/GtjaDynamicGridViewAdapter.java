package com.gtja.tonywang.dynamicgridview.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import com.gtja.tonywang.dynamicgridview.R;
import com.gtja.tonywang.dynamicgridview.model.GridItemModel;

public class GtjaDynamicGridViewAdapter extends BaseDynamicGridAdapter {
	private boolean showDelete;

	public GtjaDynamicGridViewAdapter(Context context, List<?> items, int columnCount) {
		super(context, items, columnCount);
	}

	public void setShowDelete(boolean show) {
		showDelete = show;
		notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		CheeseViewHolder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(
					R.layout.item_grid, null);
			holder = new CheeseViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (CheeseViewHolder) convertView.getTag();
		}
		holder.build(position);
		return convertView;
	}

	private class CheeseViewHolder {
		private TextView titleText;
		private ImageView image;
		private ImageView delete;

		private CheeseViewHolder(View view) {
			titleText = (TextView) view.findViewById(R.id.item_title);
			image = (ImageView) view.findViewById(R.id.item_img);
			delete = (ImageView) view.findViewById(R.id.item_delete);
		}

		void build(final int position) {
			GridItemModel model = (GridItemModel) getItem(position);
			final String title = model.getTitle();
			titleText.setText(title);
			image.setImageResource(model.getIcon());
			if (showDelete) {
				if (model.isEnableDelete()) {
					delete.setVisibility(View.VISIBLE);
					setDeleteListener(title, position);
				} else {
					delete.setVisibility(View.INVISIBLE);
				}
			} else {
				delete.setVisibility(View.INVISIBLE);
			}
		}

		private void setDeleteListener(final String title, final int position) {
			delete.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					AlertDialog.Builder builder = new AlertDialog.Builder(
							getContext());
					builder.setTitle("确认")
							.setMessage("是否删除 " + title)
							.setPositiveButton("是",
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											remove(getItem(position));
										}
									})
							.setNegativeButton("否",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog, int id) {
											dialog.cancel();
										}
									}).create().show();

				}

			});
		}
	}
}