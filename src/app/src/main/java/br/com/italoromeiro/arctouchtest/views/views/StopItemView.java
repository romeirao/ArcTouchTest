package br.com.italoromeiro.arctouchtest.views.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import br.com.italoromeiro.arctouchtest.R;
import br.com.italoromeiro.arctouchtest.models.Stop;

/**
 * Created by italo on 10/08/16.
 */
@EViewGroup(R.layout.item_view_stop)
public class StopItemView extends RelativeLayout {

    @ViewById(R.id.tv_stop_name)
    TextView mStopName;

    private Stop mStop;

    public StopItemView(Context context) {
        super(context);
    }

    public StopItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StopItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(21)
    public StopItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void bind(Stop stop, int position, final int total) {
        int auxPosition = position + 1;

        mStop = stop;
        mStopName.setText(auxPosition + ") " + stop.getName());
    }
}
