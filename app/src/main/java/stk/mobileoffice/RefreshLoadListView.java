package stk.mobileoffice;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

/**
 * Author: stk
 * Date: 2016/6/18
 * Time: 14:59
 */
public class RefreshLoadListView extends ListView implements AbsListView.OnScrollListener {
    private View footerView;
    private int footerViewHeight;
    private boolean isScrollToButtom;
    private boolean isLoadingMore = false;
    private OnRefreshListener myOnRefreshListener;

    public RefreshLoadListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        footerView = View.inflate(getContext(), R.layout.list_footer, null);
        footerView.measure(0, 0);
        footerViewHeight = footerView.getMeasuredHeight();
        footerView.setPadding(0, -footerViewHeight, 0, 0);
        this.addFooterView(footerView);
        this.setOnScrollListener(this);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == SCROLL_STATE_IDLE || scrollState == SCROLL_STATE_FLING) {
            if (isScrollToButtom && !isLoadingMore) {
                isLoadingMore = true;
                footerView.setPadding(0, 0, 0, 0);
                this.setSelection(this.getCount());
                if (myOnRefreshListener != null)
                    myOnRefreshListener.onLoadingMore();
            }
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (getLastVisiblePosition() == (totalItemCount - 1))
            isScrollToButtom = true;
        else
            isScrollToButtom = false;
    }

    public void setOnRefreshListener(OnRefreshListener listener) {
        myOnRefreshListener = listener;
    }

    public void loadMoreComplete() {
        footerView.setPadding(0, -footerViewHeight, 0, 0);
        isLoadingMore = false;
    }

    public interface OnRefreshListener {
        void onLoadingMore();
    }
}
