package br.com.italoromeiro.arctouchtest.views.activities;

import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.androidannotations.annotations.AfterTextChange;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import br.com.italoromeiro.arctouchtest.R;
import br.com.italoromeiro.arctouchtest.models.Result;
import br.com.italoromeiro.arctouchtest.presenters.MainPresenter;
import br.com.italoromeiro.arctouchtest.rest.RestClient;
import br.com.italoromeiro.arctouchtest.utils.FormatterUtil;
import retrofit2.Call;

@EActivity(R.layout.activity_main)
public class MainActivity extends BaseActivity implements RestClient.OnRestListener<Result> {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Bean
    MainPresenter presenter;

    @ViewById(R.id.et_search)
    EditText mEtSearch;

    @ViewById(R.id.btn_search)
    Button mBtnSearch;

    @ViewById(R.id.result)
    TextView mResult;

    @Click(R.id.btn_search)
    public void btnSearchClick() {
        presenter.findByStopName(FormatterUtil.cleanString(mEtSearch.getText().toString()));
    }

    @AfterTextChange(R.id.et_search)
    public void afterTextChange(TextView affectedView) {
        if (affectedView.getText().toString().isEmpty()) {
            mBtnSearch.setEnabled(false);
            return;
        }
        mBtnSearch.setEnabled(true);
    }

    @Override
    public void onIncomeSuccess(Result result, Call call) {
        mResult.setText(result.toString());
    }

    @Override
    public void onIncomeFailure(Throwable t) {
        Log.e(TAG, t.getMessage(), t);
    }
}
