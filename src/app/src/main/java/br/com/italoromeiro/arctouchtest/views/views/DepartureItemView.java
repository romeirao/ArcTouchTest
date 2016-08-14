package br.com.italoromeiro.arctouchtest.views.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import br.com.italoromeiro.arctouchtest.R;
import br.com.italoromeiro.arctouchtest.models.Departure;

/**
 * Created by italo on 10/08/16.
 */
@EViewGroup(R.layout.item_view_departure)
public class DepartureItemView extends RelativeLayout {

    @ViewById(R.id.tv_departure_calendar)
    TextView mDepartureCalendarTv;

    private Departure mDeparture;

    public DepartureItemView(Context context) {
        super(context);
    }

    public DepartureItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DepartureItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(21)
    public DepartureItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void bind(Departure departure) {
        mDeparture = departure;
        mDepartureCalendarTv.setText(departure.getTime());
    }
}
