package kale.db.simple;

import com.google.gson.Gson;

import com.facebook.drawee.backends.pipeline.Fresco;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import kale.adapter.CommonRcvAdapter;
import kale.adapter.RcvAdapterWrapper;
import kale.adapter.item.AdapterItem;
import kale.db.simple.base.BaseActivity;
import kale.db.simple.databinding.ActivityMainBinding;
import kale.db.simple.model.Result;
import kale.db.simple.model.Root;
import kale.db.simple.service.ApiService;
import kale.db.simple.util.OnRcvScrollListener;
import kale.dbinding.util.ObservableArrayList;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class MainActivity extends BaseActivity<ActivityMainBinding> {

    private ObservableArrayList<Result> list = new ObservableArrayList<>();

    private int date = 2; // default = 2

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void beforeSetViews() {
        Fresco.initialize(this);
    }

    @Override
    protected void setViews() {
        RecyclerView recycleView = b.recycleView;
        recycleView.setLayoutManager(new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false));
        CommonRcvAdapter<Result> adapter = new CommonRcvAdapter<Result>(list) {
            @NonNull
            @Override
            public AdapterItem<Result> createItem(Object o) {
                return new PicItem(getActivity());
            }
        };
        RcvAdapterWrapper wrapper = new RcvAdapterWrapper(adapter, recycleView.getLayoutManager());
        wrapper.setFooterView(View.inflate(this, R.layout.main_loading_footer, null));
        recycleView.setAdapter(wrapper);
        recycleView.addOnScrollListener(new OnRcvScrollListener(0) {
            @Override
            public void onBottom() {
                super.onBottom();
                date++;
                if (date > 31) {
                    Toast.makeText(MainActivity.this, "end", Toast.LENGTH_SHORT).show();
                    return;
                }
                loadData(date).subscribe(new Action1<List<Result>>() {
                    @Override
                    public void call(List<Result> results) {
                        list.addAll(results);
                    }
                });
            }
        });
        b.refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData(2).subscribe(new Action1<List<Result>>() {
                    @Override
                    public void call(List<Result> results) {
                        list.reset(results);
                        b.refreshLayout.setRefreshing(false);
                    }
                });
            }
        });
        b.refreshLayout.setColorSchemeResources(R.color.colorAccent);
    }

    @Override
    protected void doTransaction() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.show();
        loadData(2).subscribe(new Action1<List<Result>>() {
            @Override
            public void call(List<Result> results) {
                progressDialog.dismiss();
                list.reset(results);
            }
        });
    }

    private Observable<List<Result>> loadData(final int date) {
        return Observable.create(new Observable.OnSubscribe<List<Result>>() {
            @Override
            public void call(final Subscriber<? super List<Result>> subscriber) {
                new ApiService().doGet("http://gank.io/api/data/福利/10/" + date).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        subscriber.onError(e);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Root root = new Gson().fromJson(response.body().string(), Root.class);
                        if (root.error) {
                            subscriber.onError(new Throwable("service error"));
                        } else {
                            subscriber.onNext(root.results);
                        }
                    }
                });
            }
        }).observeOn(AndroidSchedulers.mainThread());
    }
}
