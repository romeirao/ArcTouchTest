package br.com.italoromeiro.arctouchtest.views.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import br.com.italoromeiro.arctouchtest.R;
import br.com.italoromeiro.arctouchtest.models.Route;

/**
 * Created by italo on 10/08/16.
 */
@EViewGroup(R.layout.item_view_route)
public class RouteItemView extends RelativeLayout {

    @ViewById(R.id.tv_route_short_name)
    TextView mRouteShortName;

    @ViewById(R.id.tv_route_long_name)
    TextView mRouteLongName;

    private Route mRoute;

    public RouteItemView(Context context) {
        super(context);
    }

    public RouteItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RouteItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(21)
    public RouteItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void bind(Route route) {
        mRoute = route;
        mRouteShortName.setText(route.getShortName());
        mRouteLongName.setText(route.getLongName());
    }
}
