package br.com.italoromeiro.arctouchtest.core.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;
import java.util.List;

import br.com.italoromeiro.arctouchtest.models.Departure;
import br.com.italoromeiro.arctouchtest.views.activities.BaseActivity;
import br.com.italoromeiro.arctouchtest.views.views.DepartureItemView;
import br.com.italoromeiro.arctouchtest.views.views.DepartureItemView_;

/**
 * Created by italo on 10/08/16.
 */
@EBean
public class DeparturesAdapter extends RecyclerView.Adapter<DeparturesAdapter.DepartureHolder> {

    @RootContext
    BaseActivity mActivity;

    private List<Departure> mDepartures = new ArrayList<>();

    @Override
    public DepartureHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DepartureHolder(DepartureItemView_.build(mActivity));
    }

    @Override
    public void onBindViewHolder(DepartureHolder holder, int position) {
        ((DepartureItemView) holder.itemView).bind(mDepartures.get(position));
    }

    @Override
    public int getItemCount() {
        return mDepartures.size();
    }

    public class DepartureHolder extends RecyclerView.ViewHolder {
        private DepartureItemView mDepartureItemView;

        public DepartureHolder(View itemView) {
            super(itemView);
            mDepartureItemView = (DepartureItemView) itemView;
        }
    }

    public void setDepartures(List<Departure> departures) {
        mDepartures.clear();
        mDepartures.addAll(departures);
    }
}
