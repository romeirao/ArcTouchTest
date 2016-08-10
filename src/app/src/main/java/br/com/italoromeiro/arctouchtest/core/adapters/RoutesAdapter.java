package br.com.italoromeiro.arctouchtest.core.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;
import java.util.List;

import br.com.italoromeiro.arctouchtest.models.Route;
import br.com.italoromeiro.arctouchtest.views.activities.BaseActivity;
import br.com.italoromeiro.arctouchtest.views.activities.DetailActivity_;
import br.com.italoromeiro.arctouchtest.views.views.RouteItemView;
import br.com.italoromeiro.arctouchtest.views.views.RouteItemView_;

/**
 * Created by italo on 10/08/16.
 */
@EBean
public class RoutesAdapter extends RecyclerView.Adapter<RoutesAdapter.RouteHolder> {

    @RootContext
    BaseActivity mActivity;

    private List<Route> mRoutes = new ArrayList<>();

    @Override
    public RouteHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RouteHolder(RouteItemView_.build(mActivity));
    }

    @Override
    public void onBindViewHolder(RouteHolder holder, int position) {
        ((RouteItemView) holder.itemView).bind(mRoutes.get(position));
    }

    @Override
    public int getItemCount() {
        return mRoutes.size();
    }

    public class RouteHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private RouteItemView mRouteItemView;

        public RouteHolder(View itemView) {
            super(itemView);
            mRouteItemView = (RouteItemView) itemView;
            mRouteItemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            DetailActivity_.intent(mActivity).mRoute(mRoutes.get(getAdapterPosition())).start();
        }
    }

    public void setRoutes(List<Route> routes) {
        mRoutes.clear();
        mRoutes.addAll(routes);
    }
}
