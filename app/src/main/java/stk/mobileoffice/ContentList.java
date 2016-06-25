package stk.mobileoffice;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.SimpleAdapter;

import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Author: stk
 * Date: 2016/6/18
 * Time: 21:58
 */
public class ContentList extends Fragment implements SwipeRefreshLayout.OnRefreshListener, RefreshLoadListView.OnRefreshListener {
    protected View view;
    protected MHandler handler = new MHandler(this);
    protected Thread thread;
    protected Runnable runnable;
    protected SwipeRefreshLayout swipeRefreshLayout;
    protected RefreshLoadListView refreshLoadListView;
    protected SimpleAdapter adapter;
    protected List<Map<String, Object>> data;
    protected int currentpage;
    protected int pagecount;
    protected Button leftButton;
    protected Button rightButton;
    protected int mode;
    protected boolean isChanged;

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
        data = new LinkedList<>();
        set();
        refreshLoadListView.setAdapter(adapter);
        refreshLoadListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                showDetail(data.get(i));
            }
        });
        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mode = 0;
                isChanged = true;
                leftButton.setBackgroundResource(R.drawable.button_left_focus);
                leftButton.setTextColor(Color.rgb(223, 223, 223));
                rightButton.setBackgroundResource(R.drawable.button_right);
                rightButton.setTextColor(Color.rgb(110, 110, 110));
                showData();
            }
        });
        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mode = 1;
                isChanged = true;
                leftButton.setBackgroundResource(R.drawable.button_left);
                leftButton.setTextColor(Color.rgb(110, 110, 110));
                rightButton.setBackgroundResource(R.drawable.button_right_focus);
                rightButton.setTextColor(Color.rgb(223, 223, 223));
                showData();
            }
        });
        currentpage = 0;
        mode = 0;
        isChanged = false;
        showData();
    }

    @Override
    public void onRefresh() {
        currentpage = 0;
        data.clear();
        showData();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onLoadingMore() {
        if (currentpage < pagecount)
            showData();
        refreshLoadListView.loadMoreComplete();
    }

    protected void showData(){
        thread = new Thread(runnable);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected void dataClear() {
        data.clear();
        isChanged = false;
        currentpage = 0;
        pagecount = 0;
    }

    protected void set(){}
    protected void showDetail(Map<String, Object> i){}

    protected static class MHandler extends Handler {
        private final WeakReference<ContentList> outer;
        MHandler(ContentList target) {
            outer = new WeakReference<>(target);
        }
        @Override
        public void handleMessage(Message msg) {
            ContentList target = outer.get();
            if (target != null) {
                switch (msg.what) {
                    case 0:
                        target.adapter.notifyDataSetChanged();
                        break;
                    default:
                        break;
                }
            }
        }
    }
}
