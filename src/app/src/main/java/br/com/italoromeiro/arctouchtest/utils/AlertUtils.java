package br.com.italoromeiro.arctouchtest.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * Created by italo on 10/08/16.
 */
public class AlertUtils {
    private static final String TAG = AlertUtils.class.getSimpleName();

    public static AlertDialog alert(Context context, int titleId, int messageId) {
        return alert(context, titleId, messageId, 0, null, 0, null, 0);
    }

    public static synchronized AlertDialog alert(Context context, int titleId, int messageId, int okButtonId, DialogInterface.OnClickListener okListener, int noButtonId, DialogInterface.OnClickListener noListener, int icon) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        if (okButtonId > 0 && okListener != null) {
            builder.setPositiveButton(okButtonId, okListener);
        }

        if (noButtonId > 0 && noListener != null) {
            builder.setNegativeButton(noButtonId, noListener);
        }

        builder.setTitle(context.getString(titleId))
                .setMessage(context.getString(messageId));

        return builder.create();
    }
}
