package com.car.service.listmode;

import android.widget.AbsListView;
import com.car.service.utils.WLog;


/**
 * Created by r.savuschuk on 11/5/2014.
 */
public class ListScrollListener implements AbsListView.OnScrollListener {
	private String TAG = getClass().getSimpleName();
	private boolean isScrolled = false;
	ListFinish listFinishListener;

	public void setScrolled(boolean b) {
		isScrolled = b;
	}

	private boolean isScrolled() {
		return isScrolled;
	}

	@Override
	public void onScrollStateChanged(final AbsListView absListView, final int i) {
		//WLog.d(TAG, "onScrollStateChanged");
		if (!isScrolled()) {
			setScrolled(true);
		}
	}

	@Override
	public void onScroll(AbsListView lw, final int firstVisibleItem, final int visibleItemCount, final int totalItemCount) {
		WLog.d(TAG, "onScroll"+ " lastvisibleitem ="+(firstVisibleItem+visibleItemCount)+" totalItemCount ="+totalItemCount+" isScrolled " +
			"="+isScrolled);
		boolean isLastItemShown = (firstVisibleItem + visibleItemCount == totalItemCount);
		if (isLastItemShown && isScrolled && (lw.getFirstVisiblePosition() != 0)) {
			setScrolled(false);
			ItemModel item = (ItemModel) lw.getAdapter().getItem(totalItemCount-1);
			WLog.d(TAG, "Last parameters " + "firstVisibleItem " + firstVisibleItem + " totalItemCount " + totalItemCount + "  visibleItemCount " +  visibleItemCount);
			WLog.d(TAG, "Last " + "Number " + (totalItemCount-1) + " Data " + item.getDataInt() + " Q: " + item.getPrice());
			listFinishListener.listFinished(item.getDataInt());
		}
	}

	public interface ListFinish {
		public void listFinished(long data_str);
	}

	public void addListFinishListener(ListFinish listener) {
		listFinishListener = listener;
	}

	public void removeListFinishListener(ListFinish listener) {
		listFinishListener = null;
	}

}
