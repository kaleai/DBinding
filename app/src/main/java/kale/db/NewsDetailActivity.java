package kale.db;

import org.kale.vd.NewsViewData;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import kale.db.databinding.NewsDetailBinding;
import kale.dbinding.DBinding;

/**
 * @author Kale
 * @date 2016/1/27
 */
public class NewsDetailActivity extends AppCompatActivity {
    
    private static final String KEY = "view_data";

    public static Intent withIntent(Activity activity, NewsViewData viewData) {
        return new Intent(activity, NewsDetailActivity.class)
                .putExtra(KEY, viewData.toSerializable());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final NewsDetailBinding binding = DBinding.bind(this, R.layout.news_detail);

        final NewsViewData viewData = NewsViewData.toViewData(getIntent().getSerializableExtra(KEY));
        if (viewData == null) {
            return;
        }
        DBinding.setVariables(binding, viewData);
        
        binding.likeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewData.setIsLikeText("已赞");
            }
        });
    }
}
