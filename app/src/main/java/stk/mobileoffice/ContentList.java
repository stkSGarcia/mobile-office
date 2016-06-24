package stk.mobileoffice;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Author: stk
 * Date: 2016/6/18
 * Time: 21:58
 */
public class ContentList extends Fragment implements SwipeRefreshLayout.OnRefreshListener, RefreshLoadListView.OnRefreshListener {
    protected View view;
    protected SwipeRefreshLayout swipeRefreshLayout;
    protected RefreshLoadListView refreshLoadListView;
    protected SimpleAdapter adapter;
    protected List<Map<String, Object>> data;
    protected int currentpage;
    protected int pagecount;
    protected Button leftButton;
    protected Button rightButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.content_list, container, false);
        init();
        return view;
    }

    protected void init() {
        leftButton = (Button) view.findViewById(R.id.button_left);
        rightButton = (Button) view.findViewById(R.id.button_right);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh);
        refreshLoadListView = (RefreshLoadListView) view.findViewById(R.id.content_list);
        swipeRefreshLayout.setOnRefreshListener(this);
        refreshLoadListView.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(R.color.firstColor, R.color.secondColor, R.color.thirdColor, R.color.forthColor);
        data = new ArrayList<>();
        currentpage = 0;
        set();
        refreshLoadListView.setAdapter(adapter);
        refreshLoadListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                showDetail(data.get(i));
            }
        });
        showData();
    }

    @Override
    public void onRefresh() {
        currentpage = 0;
        data = new ArrayList<>();
        showData();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onLoadingMore() {
        if (currentpage < pagecount)
            showData();
        refreshLoadListView.loadMoreComplete();
    }

    protected void set(){}
    protected void showDetail(Map<String, Object> i){}
    protected void showData(){}
}
