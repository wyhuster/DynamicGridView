package com.gtja.tonywang.dynamicgridview.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.gtja.tonywang.dynamicgridview.R;
import com.gtja.tonywang.dynamicgridview.adapter.GtjaDynamicGridViewAdapter;
import com.gtja.tonywang.dynamicgridview.model.GridItemModel;
import com.gtja.tonywang.dynamicgridview.widget.DynamicGridView;

public class GridActivity extends Activity {

	private static final String TAG = GridActivity.class.getName();
	public String[] sCheeseStrings = { "沪深市场", "港股行情", "天汇宝2号", "融资融券", "新股发行",
			"手机开户", "全部行情", "自选资讯", "港股通", "场内基金" };
	public int[] sCheeseIcons = { R.drawable.ht_iggt, R.drawable.ht_ihssc,
			R.drawable.ht_iqbhq, R.drawable.ht_iqqhq, R.drawable.ht_irwz,
			R.drawable.ht_irzrj, R.drawable.ht_isjkh, R.drawable.ht_ithbeh,
			R.drawable.ht_iwdzx, R.drawable.ht_ixgsg };

	private DynamicGridView gridView;
	private GtjaDynamicGridViewAdapter adapter;
	private GridItemModel lastItem;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_grid);

		List<GridItemModel> models = new ArrayList<GridItemModel>();
		for (int i = 0; i < sCheeseStrings.length; i++) {
			GridItemModel model = new GridItemModel();
			model.setTitle(sCheeseStrings[i]);
			model.setIcon(sCheeseIcons[i]);
			if (i >= 2) {
				model.setEnableDelete(true);
			}
			models.add(model);
		}

		lastItem = new GridItemModel();
		lastItem.setEnableDelete(false);
		lastItem.setIcon(R.drawable.add_func);
		lastItem.setTitle("");
		models.add(lastItem);

		gridView = (DynamicGridView) findViewById(R.id.dynamic_grid);
		adapter = new GtjaDynamicGridViewAdapter(this, models, 4);
		gridView.setAdapter(adapter);
		// add callback to stop edit mode if needed
		// gridView.setOnDropListener(new DynamicGridView.OnDropListener()
		// {
		// @Override
		// public void onActionDrop()
		// {
		// gridView.stopEditMode();
		// }
		// });
		gridView.setOnDragListener(new DynamicGridView.OnDragListener() {
			@Override
			public void onDragStarted(int position) {
				Log.d(TAG, "drag started at position " + position);
			}

			@Override
			public void onDragPositionsChanged(int oldPosition, int newPosition) {
				Log.d(TAG, String.format(
						"drag item position changed from %d to %d",
						oldPosition, newPosition));
			}
		});
		gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (position != adapter.getCount() - 1) {
					adapter.remove(lastItem);
					adapter.setShowDelete(true);
					gridView.startEditMode(position);
				}
				return true;
			}
		});

		gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				GridItemModel model = (GridItemModel) adapter.getItem(position);
				if ("".equals(model.getTitle())) {
					GridItemModel addModel = new GridItemModel();
					addModel.setTitle("test");
					addModel.setIcon(R.drawable.icon);
					addModel.setEnableDelete(true);
					Log.i("wy", position + "");
					adapter.add(position, addModel);
				} else {
					Toast.makeText(GridActivity.this, model.getTitle(),
							Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	private boolean resetGridView() {
		if (gridView.isEditMode()) {
			gridView.stopEditMode();
			adapter.add(lastItem);
			adapter.setShowDelete(false);
			return true;
		}
		return false;
	}

	@Override
	public void onBackPressed() {
		if (!resetGridView()) {
			super.onBackPressed();
		}
	}
}
