package view.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import presenter.base.BasePresenter;

/**
 * Created by kuaX on 2017/4/21.
 */

public abstract class BaseMvpActivity<V,T extends BasePresenter<V>> extends AppCompatActivity {
    public T presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = initPresenter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.attach((V)this);
    }

    @Override
    protected void onDestroy() {
        presenter.dettach();
        super.onDestroy();
    }

    // 实例化presenter
    public abstract T initPresenter();
}
