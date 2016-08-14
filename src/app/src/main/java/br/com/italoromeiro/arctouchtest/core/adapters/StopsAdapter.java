package br.com.italoromeiro.arctouchtest.core.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;
import java.util.List;

import br.com.italoromeiro.arctouchtest.models.Stop;
import br.com.italoromeiro.arctouchtest.views.activities.BaseActivity;
import br.com.italoromeiro.arctouchtest.views.views.StopItemView;
import br.com.italoromeiro.arctouchtest.views.views.StopItemView_;

/**
 * Created by italo on 10/08/16.
 */
@EBean
public class StopsAdapter extends RecyclerView.Adapter<StopsAdapter.StopHolder> {

    @RootContext
    BaseActivity mActivity;

    private List<Stop> mStops = new ArrayList<>();

    @Override
    public StopHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new StopHolder(StopItemView_.build(mActivity));
    }

    @Override
    public void onBindViewHolder(StopHolder holder, int position) {
        ((StopItemView) holder.itemView).bind(mStops.get(position), position, getItemCount());
    }

    @Override
    public int getItemCount() {
        return mStops.size();
    }

    public class StopHolder extends RecyclerView.ViewHolder {
        private StopItemView mStopItemView;

        public StopHolder(View itemView) {
            super(itemView);
            mStopItemView = (StopItemView) itemView;
        }
    }

    public void setStops(List<Stop> stops) {
        mStops.clear();
        mStops.addAll(stops);
    }
}
