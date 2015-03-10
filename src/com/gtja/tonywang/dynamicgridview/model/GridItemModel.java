package com.gtja.tonywang.dynamicgridview.model;

public class GridItemModel {

	private String title;
	private int icon;
	private boolean enableDelete;

	public void setTitle(String title) {
		this.title = title;
	}

	public void setIcon(int icon) {
		this.icon = icon;
	}

	public String getTitle() {
		return title;
	}

	public int getIcon() {
		return icon;
	}

	public boolean isEnableDelete() {
		return enableDelete;
	}

	public void setEnableDelete(boolean enableDelete) {
		this.enableDelete = enableDelete;
	}

}
