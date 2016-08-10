package br.com.italoromeiro.arctouchtest.views.activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;

import br.com.italoromeiro.arctouchtest.R;

/**
 * Created by italo on 08/08/16.
 */
@EActivity
public abstract class BaseActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;

    @UiThread
    public void showDialogProgress() {
        if (progressDialog != null && progressDialog.isShowing()) {
            return;
        }

        progressDialog = ProgressDialog.show(this, null,
                getString(R.string.dialog_loading_waiting), true, true, new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                    }
                });

        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
    }

    @UiThread
    public void dismissDialogProgress() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dismissDialogProgress();
    }
}
